package controladores;
import modelo.*;
import vistas.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.*;

import modelo.Carpeta;
import modelo.Curso;
import modelo.Menu;
import modelo.Usuario;
import modelo.Recurso;
import modelo.Profesor;
import modelo.Estudiante;

import java.awt.*;
import java.awt.event.*;

public class CarpetaController {
	private Carpeta carpeta;
    private Curso curso;
    private VentanaSubMenuCarpeta ventanaCarpeta;
    private JPanel cardsPanel;
    private VentanaBusqueda ventanaBusqueda;
    private Map<String, JPanel> ventanas;
    private CursoController cursoController; // Controlador de curso
    private Usuario usuarioActual; // Agregar esta variable para almacenar el usuario actual

    // Constructor que recibe todas las ventanas necesarias
    public CarpetaController(CursoController cursoController, Carpeta carpeta, Curso curso, JPanel cardsPanel, Map<String, JPanel> ventanas, Usuario usuarioActual) {
        this.carpeta = carpeta;
        this.curso = curso;
        this.ventanaBusqueda = cursoController.getVentanaBusqueda();
        this.cursoController = cursoController; // Almacena la referencia al controlador de curso
        this.cardsPanel = cardsPanel;
        this.ventanas = ventanas;
        this.usuarioActual = usuarioActual; // Inicializa el usuario actual

        // Inicializa las ventanas
        this.ventanaCarpeta = (VentanaSubMenuCarpeta) ventanas.get("SubMenuCarpeta" + curso.getId() + "_" + carpeta.getId());

        // Configurar los listeners
        initializeListeners();
        cargarRecursosEnLista();
    }
   
    // Método para inicializar listeners (agrega aquí la lógica necesaria)
    private void initializeListeners() {
        llenarComboCarpetas();

        if (ventanaCarpeta != null) {
            // Siempre accesibles
            ventanaCarpeta.getMostrarRecursosMenuItem().addActionListener(e -> ventanaCarpeta.mostrarPanel("MostrarRecursos"));
            ventanaCarpeta.getBuscarRecursosMenuItem().addActionListener(e -> ventanaCarpeta.mostrarPanel("BuscarRecursos"));
            ventanaCarpeta.getRegresarMenuItem().addActionListener(e -> cursoController.mostrarVentana("SubMenuCurso" + curso.getId()));
            ventanaCarpeta.getSalirMenuItem().addActionListener(e -> System.exit(0));

            // Opciones restringidas a usuarios del tipo Profesor
            if (usuarioActual instanceof Profesor) {
                ventanaCarpeta.getCambiarNombreCarpetaMenuItem().addActionListener(e -> ventanaCarpeta.mostrarPanel("CambiarNombreCarpeta"));
                ventanaCarpeta.getCargarRecursosMenuItem().addActionListener(e -> ventanaCarpeta.mostrarPanel("CargarRecursos"));
                ventanaCarpeta.getEliminarRecursosMenuItem().addActionListener(e -> ventanaCarpeta.mostrarPanel("EliminarRecurso"));
                ventanaCarpeta.getMoverRecursoMenuItem().addActionListener(e -> ventanaCarpeta.mostrarPanel("MoverRecurso"));
            }

            // Botones en la ventana
            ventanaCarpeta.getBtnCargar().addActionListener(e -> cargarRecurso());
            ventanaCarpeta.getBtnEliminar().addActionListener(e -> eliminarRecurso());
            ventanaCarpeta.getBtnCambiarNombre().addActionListener(e -> cambiarNombreCarpeta());
            ventanaCarpeta.getBtnMoverRecurso().addActionListener(e -> moverRecurso());
            ventanaCarpeta.getBtnBack().addActionListener(e -> cursoController.mostrarVentana("SubMenuCurso" + curso.getId()));
        }

        // Configuración de listeners para ventanaBusqueda
        if (ventanaBusqueda != null) {
            ventanaBusqueda.getBuscarRecursoMenuItem().addActionListener(e -> mostrarPanelBusquedaRecurso());
            ventanaBusqueda.getRegresarMenuItem().addActionListener(e -> cursoController.mostrarVentana("SubMenuCarpeta" + curso.getId() + "_" + carpeta.getId())); // Corrección aquí
            ventanaBusqueda.getSalirMenuItem().addActionListener(e -> System.exit(0));

            ventanaBusqueda.getBtnBuscarRecurso().addActionListener(e -> buscarRecurso());
            ventanaBusqueda.getBtnCambiarBusqueda().addActionListener(e -> cambiarBusqueda());
        }
    }
    
    public void configurarMenu(VentanaSubMenuCarpeta ventanaSubMenuCarpeta) {
        // Habilitar o deshabilitar las opciones del menú según el tipo de usuario
        if (usuarioActual instanceof Profesor) {
            ventanaSubMenuCarpeta.activarOpcionesProfesor();
        } else {
            ventanaSubMenuCarpeta.desactivarOpcionesProfesor();
        }
    }
    
    //-------------- Interacción con el modelo --------------------
    
    private void cargarRecursosEnLista() {
        // Obtener los recursos desde el modelo Carpeta
        List<Recurso> recursos = carpeta.listarRecursos();

        // Convertir la lista de recursos a un arreglo de Strings con los nombres
        String[] nombresRecursos = recursos.stream()
                                           .map(Recurso::getTitulo)
                                           .toArray(String[]::new);

        // Actualizar la lista en la vista
        ventanaCarpeta.actualizarListaRecursos(nombresRecursos);
    }
    
    // Lógica del método cargarRecurso basado en la selección del ComboBox
    private void cargarRecurso() {
        int opcionSeleccionada = ventanaCarpeta.getComboBoxOpcion().getSelectedIndex(); // 0 = Un solo recurso, 1 = Lista de recursos
        
        if (opcionSeleccionada == 0) {
            // Cargar un solo recurso
            Recurso recurso = crearRecursoDesdeCampos(); // Crear recurso con la información de los campos
            if (recurso != null) {
                carpeta.agregarRecurso(recurso); // Agregar el recurso al modelo Carpeta
                JOptionPane.showMessageDialog(null, "Recurso cargado en la carpeta.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al crear el recurso.");
            }
        } else {
            // Cargar una lista de recursos
            cargarListaRecursos(); // Utilizar el método para cargar múltiples recursos
        }
    }
    
    // Método para crear un recurso a partir de los campos de la vista
    private Recurso crearRecursoDesdeCampos() {
        String titulo = ventanaCarpeta.getCampoTitulo();
        String formato = ventanaCarpeta.getCampoFormato();
        String autor = ventanaCarpeta.getCampoAutor();
        String categoria = ventanaCarpeta.getCampoCategoria();
        String fecha = ventanaCarpeta.getCampoFecha();
        String cursoRelacionado = ventanaCarpeta.getCampoCurso();
        String visibilidadStr = ventanaCarpeta.getCampoVisibilidad();

        // Validar los campos obligatorios
        if (titulo.isEmpty() || formato.isEmpty() || autor.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: Los campos Título, Formato y Autor son obligatorios.");
            return null;
        }

        // Convertir la visibilidad a boolean
        boolean visibilidad = Boolean.parseBoolean(visibilidadStr);

        // Crear un nuevo recurso con la información de los campos
        return new Recurso(titulo, formato, autor, categoria, fecha, cursoRelacionado, visibilidad);
    }
    // Método para cargar una lista de recursos
    private void cargarListaRecursos() {
        List<Recurso> listaRecursos = new ArrayList<>(); // Crear una lista para almacenar los recursos

        while (true) {
            String tituloAux = JOptionPane.showInputDialog(ventanaCarpeta, "Ingrese el título del recurso (o 'salir' para terminar):");
            
            if (tituloAux == null || tituloAux.trim().equalsIgnoreCase("salir")) {
                break; // Salir del ciclo si el usuario ingresa "salir"
            }

            String formatoAux = JOptionPane.showInputDialog(ventanaCarpeta, "Ingrese el formato del recurso:");
            String autorAux = JOptionPane.showInputDialog(ventanaCarpeta, "Ingrese el autor del recurso:");
            String categoriaAux = JOptionPane.showInputDialog(ventanaCarpeta, "Ingrese la categoría del recurso:");
            String fechaAux = JOptionPane.showInputDialog(ventanaCarpeta, "Ingrese la fecha del recurso:");
            String cursoRelAux = JOptionPane.showInputDialog(ventanaCarpeta, "Ingrese el curso relacionado:");
            String visibilidadStrAux = JOptionPane.showInputDialog(ventanaCarpeta, "Ingrese la visibilidad (true/false):");
            
            boolean visibilidadAux = Boolean.parseBoolean(visibilidadStrAux);
            
            Recurso recurso = new Recurso(tituloAux, formatoAux, autorAux, categoriaAux, fechaAux, cursoRelAux, visibilidadAux);
            listaRecursos.add(recurso);
        }

        if (!listaRecursos.isEmpty()) {
            carpeta.agregarRecurso(listaRecursos); // Agregar la lista de recursos a la carpeta
            JOptionPane.showMessageDialog(ventanaCarpeta, "Lista de recursos cargada en la carpeta.");
        } else {
            JOptionPane.showMessageDialog(ventanaCarpeta, "Error: No se han ingresado recursos.");
        }
    }
    // Método para eliminar un recurso
    private void eliminarRecurso() {
        String tituloEliminar = ventanaCarpeta.getCampoTitulo();

        if (!tituloEliminar.isEmpty()) {
            // Llamar al método de Carpeta para eliminar el recurso
            boolean exito = carpeta.eliminarRecurso(tituloEliminar); // Asumiendo que devuelve booleano si se eliminó correctamente
            if (exito) {
                JOptionPane.showMessageDialog(ventanaCarpeta, "Recurso eliminado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(ventanaCarpeta, "Error: No se encontró un recurso con ese título.");
            }
        } else {
            JOptionPane.showMessageDialog(ventanaCarpeta, "Por favor, ingrese un título válido.");
        }
    }
    
    private void cambiarNombreCarpeta() {
        String nuevoTitulo = ventanaCarpeta.getCampoNuevoNombre();

        if (!nuevoTitulo.isEmpty()) {
            carpeta.actualizarNombre(nuevoTitulo); // Método de Carpeta para cambiar el nombre
            JOptionPane.showMessageDialog(ventanaCarpeta, "Nombre de la carpeta actualizado a: " + nuevoTitulo);
        } else {
            JOptionPane.showMessageDialog(ventanaCarpeta, "Por favor, ingrese un nombre válido.");
        }
    }
    
    private void llenarComboCarpetas() {
        // Limpiamos el combo antes de llenarlo
        ventanaCarpeta.getComboCarpetas().removeAllItems();

        // Accedemos a la lista de carpetas del curso y llenamos el JComboBox (excepto la carpeta actual)
        List<Carpeta> carpetas = curso.getCarpetas();
        for (Carpeta c : carpetas) {
            if (!c.equals(carpeta)) { // Excluir la carpeta seleccionada actual
                ventanaCarpeta.getComboCarpetas().addItem(c.getId() + " - " + c.getNombre());
            }
        }
    }
    
    // Método para mover el recurso
    private void moverRecurso() {
        String tituloRecurso = ventanaCarpeta.getCampoTituloRecurso().getText();
        Carpeta carpetaDestino = obtenerCarpetaDestinoSeleccionada(); // Obtener la carpeta destino

        if (!tituloRecurso.isEmpty() && carpetaDestino != null) {
            // Lógica para mover el recurso utilizando el método del modelo
            boolean exito = carpeta.moverRecurso(tituloRecurso, carpetaDestino);
            if (exito) {
                JOptionPane.showMessageDialog(ventanaCarpeta, "Recurso movido a la carpeta: " + carpetaDestino.getNombre());
            } else {
                JOptionPane.showMessageDialog(ventanaCarpeta, "Error al mover el recurso. Verifique los detalles.");
            }
        } else {
            JOptionPane.showMessageDialog(ventanaCarpeta, "Por favor, ingrese un título válido y seleccione una carpeta destino.");
        }
    }

    // Método para obtener la carpeta destino seleccionada desde el combo
    private Carpeta obtenerCarpetaDestinoSeleccionada() {
        String carpetaSeleccionada = (String) ventanaCarpeta.getComboCarpetas().getSelectedItem();
        if (carpetaSeleccionada != null) {
            String[] partes = carpetaSeleccionada.split(" - ");
            String idCarpetaDestino = partes[0]; // Asumiendo que la primera parte es el ID
            return curso.buscarCarpeta(idCarpetaDestino); // Suponiendo que tienes un método para obtener la carpeta por ID
        }
        return null;
    }
    
    // Método para buscar un recurso según el criterio seleccionado
    public void buscarRecurso() {
        // Asegurarse de que la ventana de búsqueda esté disponible
        if (ventanaBusqueda == null) {
            ventanaBusqueda = cursoController.getVentanaBusqueda();  // Obtener la ventana desde el controlador de curso
        }
        
        String criterioBusqueda = ventanaBusqueda.getCampoCriterio().getText();
        int opcionSeleccionada = obtenerOpcionSeleccionada(ventanaBusqueda.getComboOpciones()); // Obtener opción seleccionada (por autor, fecha, etc.)

        if (criterioBusqueda == null || criterioBusqueda.isEmpty()) {
            ventanaBusqueda.getAreaResultado().setText("El criterio de búsqueda no puede estar vacío.");
            return;
        }

        // Llamar al método sobrecargado del modelo según la opción de búsqueda
        List<Recurso> resultados = carpeta.buscarRecursos(opcionSeleccionada, criterioBusqueda);
        
        if (resultados.isEmpty()) {
            ventanaBusqueda.getAreaResultado().setText("No se encontraron recursos con ese criterio.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Recurso recurso : resultados) {
                sb.append(recurso.getTitulo()).append("\n");
            }
            ventanaBusqueda.getAreaResultado().setText(sb.toString());
        }
    }
    // Método para obtener la opción seleccionada y convertirla en un entero
    private int obtenerOpcionSeleccionada(JComboBox<String> comboOpciones) {
        return comboOpciones.getSelectedIndex() + 1; // Retorna el índice seleccionado (ajustado a 1)
    }

    
    //-------------------------------------------------------------
    
    //--------------- Interacción con la vista --------------------
    
    
    // Método para mostrar el panel de la carpeta
    public void mostrarVentana(String tipoVentana) {
        String panelKey = tipoVentana + curso.getId();
        if (ventanas.containsKey(panelKey)) {
            CardLayout cl = (CardLayout) cardsPanel.getLayout();
            cl.show(cardsPanel, panelKey);
        } else {
            throw new IllegalArgumentException("Tipo de ventana no soportado o no creada: " + tipoVentana);
        }
    }
    
    // Método para mostrar la ventana de búsqueda por curso
    public void mostrarVentanaBusqueda() {
        // Asegurarse de que la ventana de búsqueda esté disponible
        if (ventanaBusqueda == null) {
            ventanaBusqueda = cursoController.getVentanaBusqueda();  // Obtener la ventana desde el controlador de curso
        }

        // Clave única para la ventana de búsqueda según curso y carpeta
        String panelKey = "Busqueda" + curso.getId();  
        
        if (ventanaBusqueda != null) {
            mostrarVentana(panelKey);  // Mostrar la ventana
        }
    }
    public void cambiarBusqueda() {
        // Determina qué panel está actualmente activo
        String panelActual = ventanaBusqueda.getPanelActivo();
        
        // Alterna entre los paneles: de Buscar Recurso a Buscar Estudiante o viceversa
        if (panelActual.equals("BuscarRecurso")) {
            ventanaBusqueda.mostrarPanel("BuscarEstudiante"); // Cambia al panel de "Buscar Estudiante"
            System.out.println("Cambiando a panel de búsqueda de estudiante.");
        } else if (panelActual.equals("BuscarEstudiante")) {
            ventanaBusqueda.mostrarPanel("BuscarRecurso"); // Cambia al panel de "Buscar Recurso"
            System.out.println("Cambiando a panel de búsqueda de recurso.");
        }
    }
    
    
    private void mostrarPanelBusquedaRecurso() {
        if (ventanaBusqueda == null) {
            ventanaBusqueda = cursoController.getVentanaBusqueda();  // Obtener la ventana desde el controlador de curso
        }
        if (ventanaBusqueda != null) {
            ventanaBusqueda.mostrarPanel("BuscarRecurso");  // Cambia al panel de búsqueda de recursos
        }
    }

    private void mostrarPanelBusquedaEstudiante() {
        if (ventanaBusqueda == null) {
            ventanaBusqueda = cursoController.getVentanaBusqueda();  // Obtener la ventana desde el controlador de curso
        }
        if (ventanaBusqueda != null) {
            ventanaBusqueda.mostrarPanel("BuscarEstudiante");  // Cambia al panel de búsqueda de estudiantes
        }
    }


}
