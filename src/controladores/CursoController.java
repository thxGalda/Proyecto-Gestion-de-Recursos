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

public class CursoController {
	 private Curso curso;
	    private VentanaEditarCurso ventanaEditarCurso;
	    private VentanaSubMenuCurso ventanaSubMenuCurso;
	    private VentanaBusqueda ventanaBusqueda;
	    private JPanel cardsPanel;
	    private Map<String, JPanel> ventanas;
	    private MainController mainController;
	    private List<Carpeta> listaCarpetas; // Lista de carpetas
	    private Map<Integer, CarpetaController> controladorCarpetas; // Mapa de controladores de carpetas
	    private Usuario usuarioActual; // Variable para el usuario actual


	 // Constructor modificado para recibir todas las ventanas necesarias y la lista de carpetas
	    public CursoController(MainController mainController, Curso curso, Usuario usuarioActual, JPanel cardsPanel, Map<String, JPanel> ventanas) {
	        this.curso = curso;
	        this.mainController = mainController;
	        this.usuarioActual = usuarioActual; // Inicializa el usuario actual
	        this.cardsPanel = cardsPanel;
	        this.ventanas = ventanas;
	        this.ventanaEditarCurso = (VentanaEditarCurso) ventanas.get("EditarCurso" + curso.getId());
	        this.ventanaSubMenuCurso = (VentanaSubMenuCurso) ventanas.get("SubMenuCurso" + curso.getId());
	        this.listaCarpetas = curso.getCarpetas();
	        this.controladorCarpetas = new HashMap<>(); // Inicializa el mapa de controladores de carpetas

	        // Crear controladores de carpeta para cada carpeta en la lista
	        crearVentanasCarpetas(); // Crea las ventanas de carpetas al inicializas
	        // Configurar los listeners
	        initializeListeners();
	        // Ajustar la visibilidad del menú según el tipo de usuario
	        ajustarVisibilidadMenu();
	    }
	    
	// Método para ajustar la visibilidad del menú
	   private void ajustarVisibilidadMenu() {
	        if (usuarioActual instanceof Profesor) {
	            // Si el usuario es un profesor, mostrar todas las opciones
	            ventanaEditarCurso.setVisible(true); // Asegúrate de que la ventana esté visible
	            ventanaSubMenuCurso.setVisible(true);
	        } else {
	            // Si el usuario no es un profesor, ocultar las opciones
	            ventanaEditarCurso.setVisible(false);
	            ventanaSubMenuCurso.setVisible(false);
	            // Puedes también deshabilitar o eliminar items del menú si es necesario
	        }
	    }
	   

    // Método que configura los listeners para todas las ventanas
    private void initializeListeners() {
    	// Listeners ventanaEditarCurso
    	
    	if(ventanaEditarCurso != null) {
    	
	    	ventanaEditarCurso.getDescripcionMenuItem().addActionListener(e -> ventanaEditarCurso.mostrarPanel("Descripción"));
	        ventanaEditarCurso.getAsignarProfesorMenuItem().addActionListener(e -> ventanaEditarCurso.mostrarPanel("Asignar Profesor"));
	        ventanaEditarCurso.getAgregarCarpetaMenuItem().addActionListener(e -> ventanaEditarCurso.mostrarPanel("Agregar Carpeta"));
	        ventanaEditarCurso.getEliminarCarpetaMenuItem().addActionListener(e -> ventanaEditarCurso.mostrarPanel("Eliminar Carpeta"));
	        ventanaEditarCurso.getAgregarEstudianteMenuItem().addActionListener(e -> ventanaEditarCurso.mostrarPanel("Agregar Estudiante"));
	        ventanaEditarCurso.getEliminarEstudianteMenuItem().addActionListener(e -> ventanaEditarCurso.mostrarPanel("Eliminar Estudiante"));
	        ventanaEditarCurso.getCargarRecursoMenuItem().addActionListener(e -> ventanaEditarCurso.mostrarPanel("Cargar Recurso"));
	        ventanaEditarCurso.getRegresarMenuItem().addActionListener(e -> regresar());
	        ventanaEditarCurso.getSalirMenuItem().addActionListener(e -> mainController.salirAplicacion());
	
	        // Otros listeners para botones
	        ventanaEditarCurso.getActualizarButton().addActionListener(e -> actualizarDescripcion());
	        ventanaEditarCurso.getAsignarProfesorButton().addActionListener(e -> asignarProfesor());
	        ventanaEditarCurso.getAgregarCarpetaButton().addActionListener(e -> agregarCarpeta());
	        ventanaEditarCurso.getEliminarCarpetaButton().addActionListener(e -> eliminarCarpeta());
	        ventanaEditarCurso.getAgregarEstudianteButton().addActionListener(e -> agregarEstudiante());
	        ventanaEditarCurso.getEliminarEstudianteButton().addActionListener(e -> eliminarEstudiante());
	        ventanaEditarCurso.getCargarRecursoButton().addActionListener(e -> cargarRecurso());
	        ventanaEditarCurso.getBackButton().addActionListener(e -> regresar());
    	}
        
        // Listeners ventanaSubMenuCurso
    	if(ventanaSubMenuCurso != null) {
       
	        ventanaSubMenuCurso.getRevisarCarpetasMenuItem().addActionListener(e -> ventanaSubMenuCurso.mostrarPanel("RevisarCarpetas"));
	        ventanaSubMenuCurso.getMostrarEstudiantesMenuItem().addActionListener(e -> ventanaSubMenuCurso.mostrarPanel("MostrarEstudiantes"));
	        ventanaSubMenuCurso.getBuscarEstudianteMenuItem().addActionListener(e -> ventanaSubMenuCurso.mostrarPanel("BuscarEstudiante"));
	        ventanaSubMenuCurso.getOrdenarCarpetasMenuItem().addActionListener(e -> ventanaSubMenuCurso.mostrarPanel("OrdenarCarpetas"));
	        ventanaSubMenuCurso.getBuscarRecursosMenuItem().addActionListener(e -> ventanaSubMenuCurso.mostrarPanel("BuscarRecursos"));
	        ventanaSubMenuCurso.getRegresarMenuItem().addActionListener(e -> regresar());
	        ventanaSubMenuCurso.getSalirMenuItem().addActionListener(e ->mainController.salirAplicacion());
	        
	        // Otros listeners para botones
	        ventanaSubMenuCurso.getBotonOrdenar().addActionListener(e -> manejarOrdenar());
	        ventanaSubMenuCurso.getConfirmarBtn().addActionListener(e-> mostrarVentanaCarpeta());
	        ventanaSubMenuCurso.getVolverBtn().addActionListener(e -> regresar());
    	}
        
        if(ventanaBusqueda != null) {
	        ventanaBusqueda.getBtnBuscarRecurso().addActionListener(e -> buscarRecursoEnCurso());
	        ventanaBusqueda.getBtnBuscarEstudiante().addActionListener(e -> buscarEstudiante());
	        ventanaBusqueda.getBtnCambiarBusqueda().addActionListener(e -> cambiarBusqueda());
        }

 
    }
    
    
    public void regresar() {
    	mainController.mostrarMenuPrincipal();
    }
    
    public JPanel crearVentana(String tipoVentana) {
        String panelKey = tipoVentana + curso.getId();

        if (ventanas.containsKey(panelKey)) {
            return ventanas.get(panelKey);
        }

        JPanel nuevaVentana;

        switch (tipoVentana) {
            case "EditarCurso":
                ventanaEditarCurso = new VentanaEditarCurso(this, curso);
                nuevaVentana = ventanaEditarCurso;
                break;

            case "SubMenuCurso":
                ventanaSubMenuCurso = new VentanaSubMenuCurso(this, curso);
                nuevaVentana = ventanaSubMenuCurso;
                break;

            default:
                throw new IllegalArgumentException("Tipo de ventana no soportado: " + tipoVentana);
        }

        ventanas.put(panelKey, nuevaVentana); // Añadir al mapa de ventanas
        return nuevaVentana;
    }
    
    
  //-------------- Interacción con el modelo --------------------

    // Métodos específicos para manejar la lógica de las ventanas
    private void actualizarDescripcion() {
        String nuevaDescripcion = ventanaEditarCurso.getDescripcion(); // Obtiene el texto de la descripción desde la vista
        // Lógica para actualizar la descripción en el modelo
        if (curso != null) {
            curso.actualizarDescripcion(nuevaDescripcion); 
            JOptionPane.showMessageDialog(ventanaEditarCurso, "Descripción actualizada con éxito."); // Mensaje de confirmación
        } else {
            JOptionPane.showMessageDialog(ventanaEditarCurso, "Error al actualizar la descripción."); // Manejo de errores
        }
    }

    private void asignarProfesor() {
        String nombre = ventanaEditarCurso.getNombreProfesor(); // Obtiene el nombre del campo de texto
        String rut = ventanaEditarCurso.getRutProfesor(); // Obtiene el RUT del campo de texto
        String email = ventanaEditarCurso.getEmailProfesor(); // Obtiene el email del campo de texto
        String especialidad = ventanaEditarCurso.getEspecialidadProfesor(); // Obtiene la especialidad del campo de texto

        // Lógica para asignar el profesor en el modelo
        if (curso != null) {
            curso.asignarProfesor(nombre, rut, email, especialidad); // Método que debes implementar en tu modelo
            JOptionPane.showMessageDialog(ventanaEditarCurso, "Profesor asignado con éxito."); // Mensaje de confirmación
        } else {
            JOptionPane.showMessageDialog(ventanaEditarCurso, "Error al asignar el profesor."); // Manejo de errores
        }
    }


    private void agregarCarpeta() {
        String nombreCarpeta = ventanaEditarCurso.getNombreCarpeta(); // Obtiene el nombre de la carpeta
        String idCarpetaStr = ventanaEditarCurso.getIDCarpeta(); // Obtiene el ID de la carpeta
        String esPublicaStr = ventanaEditarCurso.getEsPublica(); // Obtiene la visibilidad de la carpeta
        
        // Convertir el ID de String a int
        int idCarpeta;
        try {
            idCarpeta = Integer.parseInt(idCarpetaStr); // Convierte el String a int
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(ventanaEditarCurso, "El ID debe ser un número entero.");
            return; // Termina el método si hay un error
        }

        // Validar si es pública (convertir el texto a booleano)
        
        boolean esPublica;
        try {
            esPublica = Boolean.parseBoolean(esPublicaStr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventanaEditarCurso, "El campo de visibilidad debe ser 'true' o 'false'.");
            return; // Termina el método si hay un error
        }

        // Crear una nueva instancia de Carpeta
        Carpeta nuevaCarpeta = new Carpeta(nombreCarpeta, idCarpeta, esPublica);

        // Lógica para agregar la carpeta en el modelo
        if (curso != null) {
            curso.agregarCarpeta(nuevaCarpeta); // Llamar al método del modelo con la nueva carpeta
            JOptionPane.showMessageDialog(ventanaEditarCurso, "Carpeta agregada con éxito."); // Mensaje de confirmación
        } else {
            JOptionPane.showMessageDialog(ventanaEditarCurso, "Error al agregar la carpeta."); // Manejo de errores
        }
    }
    
    private void eliminarCarpeta() {
        String nombreEliminar = ventanaEditarCurso.getNombreEliminar(); // Obtiene el nombre o ID de la carpeta a eliminar

        // Verifica si el input es un número (ID) o un texto (nombre de la carpeta)
        try {
            int idCarpeta = Integer.parseInt(nombreEliminar);
            // Si se puede convertir a int, llamamos al método de eliminar por ID
            if (curso != null) {
                curso.eliminarCarpeta(idCarpeta); // Llama al método sobrecargado que recibe un ID
                JOptionPane.showMessageDialog(ventanaEditarCurso, "Carpeta con ID '" + idCarpeta + "' eliminada con éxito.");
            } else {
                JOptionPane.showMessageDialog(ventanaEditarCurso, "Error al eliminar la carpeta."); // Manejo de errores
            }
        } catch (NumberFormatException e) {
            // Si no es un número, tratamos de eliminar por nombre
            if (curso != null) {
                curso.eliminarCarpeta(nombreEliminar); // Llama al método sobrecargado que recibe un nombre
                JOptionPane.showMessageDialog(ventanaEditarCurso, "Carpeta '" + nombreEliminar + "' eliminada con éxito.");
            } else {
                JOptionPane.showMessageDialog(ventanaEditarCurso, "Error al eliminar la carpeta."); // Manejo de errores
            }
        }
    }

    private void agregarEstudiante() {
        String nombreEstudiante = ventanaEditarCurso.getNombreEstudiante();
        String rutEstudiante = ventanaEditarCurso.getRutEstudiante();
        String correoEstudiante = ventanaEditarCurso.getCorreoEstudiante();
        String paraleloEstudiante = ventanaEditarCurso.getParaleloEstudiante();

        if (curso != null) {
            // Aquí debes crear el objeto Estudiante y agregarlo al curso
            curso.agregarEstudiante(nombreEstudiante, rutEstudiante, correoEstudiante, paraleloEstudiante);
            JOptionPane.showMessageDialog(ventanaEditarCurso, "Estudiante agregado con éxito.");
        } else {
            JOptionPane.showMessageDialog(ventanaEditarCurso, "Error al agregar el estudiante.");
        }
    }
    
    private void eliminarEstudiante() {
        String nombreEliminarEstudiante = ventanaEditarCurso.getNombreEliminar();

     // Intentamos eliminar por ID primero
        try {
            int idEstudiante = Integer.parseInt(nombreEliminarEstudiante);
            curso.eliminarEstudiante(idEstudiante);
            JOptionPane.showMessageDialog(ventanaEditarCurso, "Estudiante con ID " + idEstudiante + " eliminado con éxito.");
        } catch (NumberFormatException e) {
            // Si la conversión a int falla, intentamos eliminar por nombre
            curso.eliminarEstudiante(nombreEliminarEstudiante);
            JOptionPane.showMessageDialog(ventanaEditarCurso, "Estudiante '" + nombreEliminarEstudiante + "' eliminado con éxito.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventanaEditarCurso, "Error al eliminar el estudiante: " + e.getMessage());
        }
    }

    private void cargarRecurso() {
        String nombreCarpeta = ventanaEditarCurso.getNombreCarpetaRecurso();
        String opcionSeleccionada = ventanaEditarCurso.getOpcionSeleccionada(); 
        
        String titulo = ventanaEditarCurso.getCampoTitulo();
        String formato = ventanaEditarCurso.getCampoFormato();
        String autor = ventanaEditarCurso.getCampoAutor();
        String categoria = ventanaEditarCurso.getCampoCategoria();
        String fecha = ventanaEditarCurso.getCampoFecha();
        String cursoRel = ventanaEditarCurso.getCampoCurso();
        String visibilidadStr = ventanaEditarCurso.getCampoVisibilidad();
        
        boolean visibilidad = Boolean.parseBoolean(visibilidadStr);

        if (nombreCarpeta.isEmpty()) {
            JOptionPane.showMessageDialog(ventanaEditarCurso, "Error: El nombre o ID de la carpeta no puede estar vacío.");
            return;
        }

        try {
            int idCarpeta = Integer.parseInt(nombreCarpeta);

            // Ejecutar lógica según la opción seleccionada
            if ("Un solo recurso".equals(opcionSeleccionada)) {
                Recurso recurso = new Recurso(titulo, formato, autor, categoria, fecha, cursoRel, visibilidad);
                curso.cargarRecurso(idCarpeta, recurso);
                JOptionPane.showMessageDialog(ventanaEditarCurso, "Recurso '" + titulo + "' cargado en la carpeta con ID: " + idCarpeta);
            } else if ("Lista de recursos".equals(opcionSeleccionada)) {
                cargarListaRecursos(nombreCarpeta);
            } else {
                JOptionPane.showMessageDialog(ventanaEditarCurso, "Error: Opción seleccionada no válida.");
            }
        } catch (NumberFormatException e) {
            // Manejar el caso de nombre como String
            if ("Un solo recurso".equals(opcionSeleccionada)) {
                String nombreCarpetaString = nombreCarpeta.trim();
                Recurso recurso = new Recurso(titulo, formato, autor, categoria, fecha, cursoRel, visibilidad);
                curso.cargarRecurso(nombreCarpetaString, recurso);
                JOptionPane.showMessageDialog(ventanaEditarCurso, "Recurso '" + titulo + "' cargado en la carpeta con nombre: " + nombreCarpetaString);
            } else if ("Lista de recursos".equals(opcionSeleccionada)) {
                cargarListaRecursos(nombreCarpeta);
            } else {
                JOptionPane.showMessageDialog(ventanaEditarCurso, "Error: Opción seleccionada no válida.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventanaEditarCurso, "Error al cargar el recurso: " + e.getMessage());
        }
     
    }
    
    private void cargarListaRecursos(String nombreCarpeta) {
        List<Recurso> listaRecursos = new ArrayList<>(); // Crear una lista para almacenar los recursos

        while (true) {
            String tituloAux = JOptionPane.showInputDialog(ventanaEditarCurso, "Ingrese el título del recurso (o 'salir' para terminar):");
            
            if (tituloAux == null || tituloAux.trim().equalsIgnoreCase("salir")) {
                break; // Salir del ciclo si el usuario ingresa "salir"
            }

            String formatoAux = JOptionPane.showInputDialog(ventanaEditarCurso, "Ingrese el formato del recurso:");
            String autorAux = JOptionPane.showInputDialog(ventanaEditarCurso, "Ingrese el autor del recurso:");
            String categoriaAux = JOptionPane.showInputDialog(ventanaEditarCurso, "Ingrese la categoría del recurso:");
            String fechaAux = JOptionPane.showInputDialog(ventanaEditarCurso, "Ingrese la fecha del recurso:");
            String cursoRelAux = JOptionPane.showInputDialog(ventanaEditarCurso, "Ingrese el curso relacionado:");
            String visibilidadStrAux = JOptionPane.showInputDialog(ventanaEditarCurso, "Ingrese la visibilidad (true/false):");
            
            boolean visibilidadAux = Boolean.parseBoolean(visibilidadStrAux);
            
            Recurso recurso = new Recurso(tituloAux, formatoAux, autorAux, categoriaAux, fechaAux, cursoRelAux, visibilidadAux);
            listaRecursos.add(recurso);
        }

        // Intentar convertir nombreCarpeta a un ID y cargar recursos
        try {
            int idCarpeta = Integer.parseInt(nombreCarpeta); // Convertir el nombre a ID
            if (!listaRecursos.isEmpty()) {
                curso.cargarRecurso(idCarpeta, listaRecursos);
                JOptionPane.showMessageDialog(ventanaEditarCurso, "Recursos cargados en la carpeta con ID: " + idCarpeta);
            } else {
                JOptionPane.showMessageDialog(ventanaEditarCurso, "Error: No se han ingresado recursos.");
            }
        } catch (NumberFormatException e) {
            // Manejar el caso en que no se puede convertir nombreCarpeta a un ID
            JOptionPane.showMessageDialog(ventanaEditarCurso, "Error: El nombre de la carpeta no es un ID válido.");
            if (!listaRecursos.isEmpty()) {
                curso.cargarRecurso(nombreCarpeta, listaRecursos);
                JOptionPane.showMessageDialog(ventanaEditarCurso, "Recursos cargados en la carpeta con nombre: " + nombreCarpeta);
            } else {
                JOptionPane.showMessageDialog(ventanaEditarCurso, "Error: No se han ingresado recursos.");
            }
        }
    }
    
    public String obtenerNombresEstudiantes() {
        List<String> nombresEstudiantes = curso.obtenerEstudiantesInscritos();
        if (nombresEstudiantes.isEmpty()) {
            return "No hay estudiantes inscritos.";
        } else {
            StringBuilder listaEstudiantes = new StringBuilder();
            for (String nombre : nombresEstudiantes) {
                listaEstudiantes.append(nombre).append("\n"); // Añadimos cada nombre a la lista
            }
            return listaEstudiantes.toString();
        }
    }
    
    // Método para ordenar carpetas
    public void manejarOrdenar() {
    	String criterioSeleccionado = (String) ventanaSubMenuCurso.getComboBoxOrden().getSelectedItem();

        switch (criterioSeleccionado) {
            case "Ordenar por Nombre":
                curso.ordenarCarpetasPorNombre(); // Llama al método que ordena por nombre
                break;
            case "Ordenar por Visibilidad":
                curso.ordenarCarpetasPorVisibilidad(); // Llama al método que ordena por visibilidad
                break;
            default:
                throw new IllegalArgumentException("Criterio de ordenación no válido: " + criterioSeleccionado);
        }
        
        ventanaSubMenuCurso.getCarpetasArea().setText(curso.mostrarCarpetas());
    }
    
    private void buscarRecursoEnCurso() {
	    String criterio = ventanaBusqueda.getCampoCriterio().getText(); // Obtener el criterio de búsqueda
	    int opcionSeleccionada = obtenerOpcionSeleccionada(ventanaBusqueda.getComboOpciones()); // Obtener opción seleccionada
	    StringBuilder resultados = new StringBuilder(); // Para almacenar los resultados de búsqueda

	    // Lógica para buscar en todas las carpetas del curso
	    for (Carpeta carpeta : listaCarpetas) {
	        List<Recurso> recursosEncontrados = carpeta.buscarRecursos(opcionSeleccionada, criterio);
	        for (Recurso recurso : recursosEncontrados) {
	            resultados.append(recurso.toString()).append("\n"); // Agrega los recursos encontrados
	        }
	    }

	    ventanaBusqueda.getAreaResultado().setText(resultados.toString()); // Mostrar resultados en el área de texto
	}
    
    // Método para obtener la opción seleccionada y convertirla en un entero
    private int obtenerOpcionSeleccionada(JComboBox<String> comboOpciones) {
        return comboOpciones.getSelectedIndex() + 1; // Retorna el índice seleccionado (ajustado a 1)
    }

    
    public void buscarEstudiante() {
        // Asegurarse de que la ventana de búsqueda esté disponible
        if (ventanaBusqueda == null) {
            ventanaBusqueda = getVentanaBusqueda();  // Obtener la ventana desde el controlador
        }

        String criterioBusqueda = ventanaBusqueda.getCampoCriterio().getText().trim();

        if (criterioBusqueda.isEmpty()) {
            ventanaBusqueda.getAreaResultado().setText("El criterio de búsqueda no puede estar vacío.");
            return;
        }
        Estudiante estudianteEncontrado = null;

        // Intentar buscar como ID (int)
        try {
            int id = Integer.parseInt(criterioBusqueda);
            estudianteEncontrado = curso.buscarEstudiante(id);  // Llama al método del modelo para buscar por ID
        } catch (NumberFormatException e) {
            // Si no es un número, buscar por nombre
            estudianteEncontrado = curso.buscarEstudiante(criterioBusqueda);  // Llama al método del modelo para buscar por nombre
        }

        // Mostrar resultados
        if (estudianteEncontrado == null) {
            ventanaBusqueda.getAreaResultado().setText("No se encontró ningún estudiante con ese criterio.");
        } else {
            ventanaBusqueda.getAreaResultado().setText("Estudiante encontrado: " + estudianteEncontrado.getNombre() + " (ID: " + estudianteEncontrado.getId() + ")");
        }
    }
    
    
    

    //-------------------------------------------------------------
    
    //--------------- Interacción con la vista --------------------
    
    // Método para mostrar una ventana específica (por ejemplo, editar curso o submenú)
    public void mostrarVentana(String tipoVentana) {
        String panelKey = tipoVentana + curso.getId();
        if (ventanas.containsKey(panelKey)) {
            CardLayout cl = (CardLayout) cardsPanel.getLayout();
            cl.show(cardsPanel, panelKey);
        } else {
            throw new IllegalArgumentException("Tipo de ventana no soportado o no creada: " + tipoVentana);
        }
    }
    
    // Método para crear ventanas de carpeta dinámicamente (Controlador curso)
    public void crearVentanaCarpeta(Curso curso, Carpeta carpeta) {
        String panelKey = "SubMenuCarpeta" + curso.getId() + "_" + carpeta.getId();

        if (!ventanas.containsKey(panelKey)) {
            // Crear el controlador de carpeta
            CarpetaController controladorCarpeta = new CarpetaController(this, carpeta, curso, cardsPanel, ventanas, usuarioActual);
            controladorCarpetas.put(carpeta.getId(), controladorCarpeta); // Guarda el controlador en el mapa

            // Crear la ventana SubMenuCarpeta, asegurando que se pasa el controlador correcto
            VentanaSubMenuCarpeta ventanaSubMenuCarpeta = new VentanaSubMenuCarpeta(controladorCarpeta, curso, carpeta);
            ventanas.put(panelKey, ventanaSubMenuCarpeta);
            cardsPanel.add(ventanaSubMenuCarpeta, panelKey);
            System.out.println("Se creó la ventana SubMenuCarpeta para la carpeta " + carpeta.getId() + " del curso " + curso.getId());
        } else {
            System.out.println("La ventana SubMenuCarpeta para la carpeta " + carpeta.getId() + " ya existe.");
        }
    }
    
    // Método para mostrar las ventanas de carpeta, creando la ventana directamente en el CursoController
    public void mostrarVentanaCarpeta() {
        // Buscar la carpeta por su ID
        Integer idCarpeta = (Integer) ventanaSubMenuCurso.getCarpetaComboBox().getSelectedItem();
        
        Carpeta carpetaSeleccionada = curso.buscarCarpeta(idCarpeta);
        if (carpetaSeleccionada != null) {
            // Crear la ventana de la carpeta directamente en este controlador
            crearVentanaCarpeta(curso, carpetaSeleccionada); // Método que ya tenemos
        } else {
            System.out.println("Carpeta no encontrada con ID: " + idCarpeta);
        }
    }

    // Método para crear todas las ventanas de carpetas al inicializar
    private void crearVentanasCarpetas() {
        for (Carpeta carpeta : curso.getCarpetas()) {
            crearVentanaCarpeta(curso, carpeta); // Crea cada ventana de carpeta
        }
    }
    // Método para obtener el controlador de una carpeta específica, si es necesario
    public CarpetaController getControladorCarpeta(Integer idCarpeta) {
        return controladorCarpetas.get(idCarpeta);
    }
    
    public VentanaBusqueda getVentanaBusqueda() {
        if (ventanaBusqueda == null) {
            ventanaBusqueda = new VentanaBusqueda(curso, this); // No pasa el controlador de carpeta aquí
        }
        return ventanaBusqueda;
    }
    
    // Método para cambiar entre los paneles de búsqueda
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

    
}
