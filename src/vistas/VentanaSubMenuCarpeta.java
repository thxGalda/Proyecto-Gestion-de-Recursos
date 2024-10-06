package vistas;

import java.util.List;
import controladores.CarpetaController;

import javax.swing.*;

import modelo.Carpeta;
import modelo.Curso;
import modelo.Profesor;
import modelo.Recurso;
import modelo.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaSubMenuCarpeta extends JPanel{
    private CardLayout cardLayout;
    private JPanel panelContenedor; // Panel principal con CardLayout
    private JList<String> listaRecursos; // JList para mostrar los recursos
    
    // Menú
    private JMenuBar menuBar;
    private JMenu menuRevisarCurso;
    private JMenuItem regresarMenuItem, salirMenuItem;
    private JMenuItem cargarRecursosMenuItem, eliminarRecursosMenuItem, mostrarRecursosMenuItem, buscarRecursosMenuItem, cambiarNombreCarpetaMenuItem, moverRecursoMenuItem;

    private CarpetaController carpetaController; // Controlador para la carpeta
    private Curso curso;
    private Carpeta carpeta;
    
    private JButton btnBack, btnMoverRecurso, btnCargar, btnEliminar, btnCambiarNombre;
    private JTextField campoTitulo, campoFormato, campoAutor, campoFecha, campoCategoria, campoCurso, campoVisibilidad, campoNuevoNombre, campoTituloRecurso;
    private JComboBox<String> comboBoxOpcion, comboCarpetas;

    
    // Constructor
    public VentanaSubMenuCarpeta(CarpetaController carpetaController, Curso curso, Carpeta carpeta) {
    	this.carpetaController = carpetaController;  // Guardar referencia al CarpetaController
        this.curso = curso;
        this.carpeta = carpeta;
        initialize();  // Inicializar el contenido
        carpetaController.configurarMenu(this);
    }

    public void initialize() {
        // Menú Barra
        setLayout(new BorderLayout());

        JLabel cursoNombreLabel = new JLabel("Submenú de Carpeta: " + carpeta.getNombre() + " - " + carpeta.getId());
        this.add(cursoNombreLabel);
        menuBar = new JMenuBar();
        menuRevisarCurso = new JMenu("Submenú Carpeta: " + carpeta.getNombre() + " - " + carpeta.getId());

        // Crear JMenuItems para cada opción
        mostrarRecursosMenuItem = new JMenuItem("Mostrar Recursos");
        buscarRecursosMenuItem = new JMenuItem("Buscar Recursos");
        cambiarNombreCarpetaMenuItem = new JMenuItem("Cambiar Nombre Carpeta");
        cargarRecursosMenuItem = new JMenuItem("Cargar Recursos");
        eliminarRecursosMenuItem = new JMenuItem("Eliminar Recurso");
        moverRecursoMenuItem = new JMenuItem("Mover Recurso");
        regresarMenuItem = new JMenuItem("Regresar");
        salirMenuItem = new JMenuItem("Salir");

        // Añadir items al menú
        menuRevisarCurso.add(mostrarRecursosMenuItem);
        menuRevisarCurso.add(buscarRecursosMenuItem);
        menuRevisarCurso.addSeparator();
        
        menuRevisarCurso.add(cambiarNombreCarpetaMenuItem);
        menuRevisarCurso.add(cargarRecursosMenuItem);
        menuRevisarCurso.add(eliminarRecursosMenuItem);
        menuRevisarCurso.add(moverRecursoMenuItem);


        menuRevisarCurso.addSeparator();
        menuRevisarCurso.add(regresarMenuItem);
        menuRevisarCurso.add(salirMenuItem);

        menuBar.add(menuRevisarCurso);
        this.add(menuBar, BorderLayout.BEFORE_FIRST_LINE);

        // Crear el layout y panel contenedor
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        // Crear paneles para cada opción
        JPanel mostrarRecursosPanel = crearPanelMostrarRecursos();
        JPanel cargarRecursosPanel = crearPanelCargarRecurso();
        JPanel eliminarRecursosPanel = crearPanelEliminarRecurso();
        JPanel cambiarNombreCarpetaPanel = crearPanelCambiarNombreCarpeta();
        JPanel moverRecursoPanel = crearPanelMoverRecurso();

        // Añadir los paneles al CardLayout
        panelContenedor.add(mostrarRecursosPanel, "MostrarRecursos");
        panelContenedor.add(cambiarNombreCarpetaPanel, "CambiarNombreCarpeta");
        panelContenedor.add(cargarRecursosPanel, "CargarRecursos");
        panelContenedor.add(eliminarRecursosPanel, "EliminarRecurso");
        panelContenedor.add(moverRecursoPanel, "MoverRecurso");

        // Añadir el panel de contenido al panel principal
        this.add(panelContenedor, BorderLayout.CENTER);
    }
    
    public void activarOpcionesProfesor() {
        cambiarNombreCarpetaMenuItem.setEnabled(true);
        cargarRecursosMenuItem.setEnabled(true);
        eliminarRecursosMenuItem.setEnabled(true);
        moverRecursoMenuItem.setEnabled(true);
    }

    public void desactivarOpcionesProfesor() {
        cambiarNombreCarpetaMenuItem.setEnabled(false);
        cargarRecursosMenuItem.setEnabled(false);
        eliminarRecursosMenuItem.setEnabled(false);
        moverRecursoMenuItem.setEnabled(false);
    }

    
    //
    // PANELES
    //
    
    private JPanel crearPanelMostrarRecursos() {
        JPanel panel = new JPanel(new BorderLayout()); // Utiliza BorderLayout para mejor control
        JLabel label = new JLabel("Mostrar Recursos - Aquí se mostrarán los recursos de la carpeta.");
        
        // Crear la lista de recursos vacía (luego se llenará dinámicamente desde el controlador)
        String[] recursos = {}; 
        
        // Crear el JList que contendrá los nombres de los recursos
        listaRecursos = new JList<>(recursos);
        
        // Añadir la lista dentro de un JScrollPane
        JScrollPane scrollPane = new JScrollPane(listaRecursos);
        
        // Botón para regresar
        btnBack = new JButton("Regresar al submenu de curso");
        
        // Añadir los componentes al panel
        panel.add(label, BorderLayout.NORTH);  // Etiqueta en la parte superior
        panel.add(scrollPane, BorderLayout.CENTER);  // JScrollPane en el centro
        panel.add(btnBack, BorderLayout.SOUTH);  // Botón en la parte inferior
        
        return panel;
    }

    private JPanel crearPanelCargarRecurso() {
	    JPanel panelCargarRecurso = new JPanel(new GridLayout(4, 2, 10, 10)); // GridLayout ajustado
	    panelCargarRecurso.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    
	    // Crear y agregar los campos de texto
        campoTitulo = new JTextField(20);
        campoFormato = new JTextField(20);
        campoAutor = new JTextField(20);
        campoCategoria = new JTextField(20);
        campoFecha = new JTextField(20);
        campoCurso = new JTextField(20);
        campoVisibilidad = new JTextField(20);
	    // Etiquetas
        JLabel labelTitulo = new JLabel("Título:");
        JLabel labelFormato = new JLabel("Formato:");
        JLabel labelAutor = new JLabel("Autor:");
        JLabel labelCategoria = new JLabel("Categoría:");
        JLabel labelFecha = new JLabel("Fecha:");
        JLabel labelCurso = new JLabel("Curso relacionado:");
        JLabel labelVisibilidad = new JLabel("Visibilidad:");
	
	    // Etiqueta y campo para seleccionar la opción de cargar un solo recurso o una lista
	    JLabel labelOpcion = new JLabel("Seleccione una opción:");
	    String[] opciones = {"Un solo recurso", "Lista de recursos"};
	    comboBoxOpcion = new JComboBox<>(opciones);
	
	    // Botón para cargar recurso(s)
	    btnCargar = new JButton("Cargar Recurso(s)");
	    btnBack = new JButton("Regresar al submenu de curso");
	
	 // Agregar componentes al panel en orden
	    panelCargarRecurso.add(labelTitulo);        // Fila 1, Columna 1: Etiqueta Título
	    panelCargarRecurso.add(campoTitulo);        // Fila 1, Columna 2: Campo de texto Título
	    panelCargarRecurso.add(labelFormato);       // Fila 2, Columna 1: Etiqueta Formato
	    panelCargarRecurso.add(campoFormato);       // Fila 2, Columna 2: Campo de texto Formato
	    panelCargarRecurso.add(labelAutor);         // Fila 3, Columna 1: Etiqueta Autor
	    panelCargarRecurso.add(campoAutor);         // Fila 3, Columna 2: Campo de texto Autor
	    panelCargarRecurso.add(labelCategoria);     // Fila 4, Columna 1: Etiqueta Categoría
	    panelCargarRecurso.add(campoCategoria);     // Fila 4, Columna 2: Campo de texto Categoría
	    panelCargarRecurso.add(labelFecha);         // Fila 5, Columna 1: Etiqueta Fecha
	    panelCargarRecurso.add(campoFecha);         // Fila 5, Columna 2: Campo de texto Fecha
	    panelCargarRecurso.add(labelCurso);         // Fila 6, Columna 1: Etiqueta Curso
	    panelCargarRecurso.add(campoCurso);         // Fila 6, Columna 2: Campo de texto Curso
	    panelCargarRecurso.add(labelVisibilidad);   // Fila 7, Columna 1: Etiqueta Visibilidad
	    panelCargarRecurso.add(campoVisibilidad);   // Fila 7, Columna 2: Campo de texto Visibilidad

	    // Agregar el ComboBox y el botón de acción
	    panelCargarRecurso.add(labelOpcion);        // Fila 8, Columna 1: Etiqueta Opción
	    panelCargarRecurso.add(comboBoxOpcion);     // Fila 8, Columna 2: ComboBox para seleccionar la opción
	    panelCargarRecurso.add(btnCargar);          // Botón "Cargar Recurso(s)" (puede estar debajo si deseas más separación)
	    panelCargarRecurso.add(btnBack);            // Botón "Regresar al submenu de curso"

	    return panelCargarRecurso; // Retornar el panel completo para añadirlo al CardLayout
    }
    private JPanel crearPanelEliminarRecurso() {
    JPanel panelEliminarRecurso = new JPanel(new GridLayout(3, 2, 10, 10)); // GridLayout para organizar los componentes
    panelEliminarRecurso.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Etiqueta y campo de texto para ingresar el título del recurso a eliminar
    JLabel labelTitulo = new JLabel("Ingrese el título del recurso a eliminar:");
    JTextField campoTitulo = new JTextField(20);

    // Botón para eliminar recurso
    btnEliminar = new JButton("Eliminar Recurso");
    btnBack = new JButton("Regresar al submenu de curso");

    // Agregar los componentes al panel
    panelEliminarRecurso.add(labelTitulo);
    panelEliminarRecurso.add(campoTitulo);
    panelEliminarRecurso.add(btnEliminar);
    panelEliminarRecurso.add(btnBack);

    return panelEliminarRecurso; // Retornar el panel para añadirlo al CardLayout
}

    private JPanel crearPanelCambiarNombreCarpeta() {
	    JPanel panelCambiarNombreCarpeta = new JPanel(new GridLayout(3, 2, 10, 10)); // Usamos GridLayout para organizar los componentes
	    panelCambiarNombreCarpeta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	
	    // Etiqueta y campo de texto para ingresar el nuevo nombre de la carpeta
	    JLabel labelNuevoNombre = new JLabel("Ingrese el nuevo nombre de la carpeta:");
	    campoNuevoNombre = new JTextField(20);
	
	    // Botón para confirmar el cambio de nombre
	    btnCambiarNombre = new JButton("Cambiar Nombre");
	    btnBack = new JButton("Regresar al submenu de curso");
	
	    // Agregar los componentes al panel
	    panelCambiarNombreCarpeta.add(labelNuevoNombre);
	    panelCambiarNombreCarpeta.add(campoNuevoNombre);
	    panelCambiarNombreCarpeta.add(btnCambiarNombre);
	    panelCambiarNombreCarpeta.add(btnBack);
	
	    return panelCambiarNombreCarpeta; // Retornar el panel para añadirlo al CardLayout
    }

 // Método para crear el panel de mover recurso
    public JPanel crearPanelMoverRecurso() {
        JPanel panelMoverRecurso = new JPanel(new GridLayout(4, 2, 10, 10)); // Usamos GridLayout para organizar los componentes
        panelMoverRecurso.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Etiqueta y campo de texto para ingresar el título del recurso a mover
        JLabel labelTituloRecurso = new JLabel("Ingrese el título del recurso a mover:");
        campoTituloRecurso = new JTextField(20);

        // Etiqueta y JComboBox para seleccionar la carpeta destino
        JLabel labelCarpetaDestino = new JLabel("Seleccione la carpeta destino:");
        comboCarpetas = new JComboBox<>();

        // Botón para mover el recurso
        btnMoverRecurso = new JButton("Mover Recurso");
        JButton btnBack = new JButton("Regresar al submenu de curso");

        // Agregar los componentes al panel
        panelMoverRecurso.add(labelTituloRecurso);
        panelMoverRecurso.add(campoTituloRecurso);
        panelMoverRecurso.add(labelCarpetaDestino);
        panelMoverRecurso.add(comboCarpetas);
        panelMoverRecurso.add(btnMoverRecurso);
        panelMoverRecurso.add(btnBack);

        return panelMoverRecurso; // Retornar el panel para añadirlo al CardLayout
    }
    
    public void mostrarPanel(String nombrePanel) {
        cardLayout.show(panelContenedor, nombrePanel);
    }

    // Getters para los JMenuItems
    public JMenuItem getMostrarRecursosMenuItem() {
        return mostrarRecursosMenuItem;
    }

    public JMenuItem getBuscarRecursosMenuItem() {
        return buscarRecursosMenuItem;
    }

    public JMenuItem getCambiarNombreCarpetaMenuItem() {
        return cambiarNombreCarpetaMenuItem;
    }

    public JMenuItem getCargarRecursosMenuItem() {
        return cargarRecursosMenuItem;
    }

    public JMenuItem getEliminarRecursosMenuItem() {
        return eliminarRecursosMenuItem;
    }

    public JMenuItem getMoverRecursoMenuItem() {
        return moverRecursoMenuItem;
    }

    public JMenuItem getRegresarMenuItem() {
        return regresarMenuItem;
    }

    public JMenuItem getSalirMenuItem() {
        return salirMenuItem;
    }
    
    // Getter para obtener el JList
    public JList<String> getListaRecursos() {
        return listaRecursos;
    }
    
    public JComboBox<String> getComboBoxOpcion() {
        return comboBoxOpcion;
    }
    
 // Getters para los campos de texto
    public String getCampoTitulo() {
        return campoTitulo.getText();
    }

    public String getCampoFormato() {
        return campoFormato.getText();
    }

    public String getCampoAutor() {
        return campoAutor.getText();
    }

    public String getCampoCategoria() {
        return campoCategoria.getText();
    }

    public String getCampoFecha() {
        return campoFecha.getText();
    }

    public String getCampoCurso() {
        return campoCurso.getText();
    }

    public String getCampoVisibilidad() {
        return campoVisibilidad.getText();
    }
    public String getCampoNuevoNombre() {
        return campoNuevoNombre.getText();
    }

    public JButton getBtnCargar() {
        return btnCargar;
    }
    public JButton getBtnEliminar() {
        return btnEliminar;
    }
    public JButton getBtnCambiarNombre() {
        return btnCambiarNombre;
    }
    public JButton getBtnMoverRecurso() {
        return btnMoverRecurso;
    }
    public JTextField getCampoTituloRecurso() {
        return campoTituloRecurso;
    }
    public JComboBox<String> getComboCarpetas() {
        return comboCarpetas;
    }

    // Getter para el botón "Regresar"
    public JButton getBtnBack() {
        return btnBack;
    }

    // Método para actualizar los recursos mostrados en el JList
    public void actualizarListaRecursos(String[] recursos) {
        listaRecursos.setListData(recursos);
    }


}

