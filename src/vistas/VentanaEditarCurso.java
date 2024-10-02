package vistas;

import paqueteMain.Usuario;
import paqueteMain.Profesor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaEditarCurso extends JPanel implements ActionListener{
    private CardLayout cardLayout;
    private JPanel panelContenedor; // Panel principal con CardLayout

    private JComboBox<String> cursoComboBox;
    private JTextArea descripcionArea;
    private JTextField profesorField;
    
    // Menú
    private JMenuBar menuBar;
    private JMenu menuEditarCurso;
    private JMenuItem regresarMenuItem, salirMenuItem;
    private JMenuItem descripcionMenuItem, asignarProfesorMenuItem, agregarCarpetaMenuItem, eliminarCarpetaMenuItem;
    private JMenuItem agregarEstudianteMenuItem, eliminarEstudianteMenuItem, cargarRecursoMenuItem;

    private MainApp mainApp;
    private Profesor profesorActual;
    private Usuario usuarioActual;
    
    // Constructor
    public VentanaEditarCurso(MainApp mainApp) {
        this.mainApp = mainApp;  // Guardar referencia a MainApp
        initialize();  // Inicializar el contenido
    }

    private void initialize() {
    	// Menú Barra
        menuBar = new JMenuBar();
        menuEditarCurso = new JMenu("Editar Curso");
        
        // Crear JMenuItems para cada opción
        descripcionMenuItem = new JMenuItem("Descripción del Curso");
        asignarProfesorMenuItem = new JMenuItem("Asignar Profesor");
        agregarCarpetaMenuItem = new JMenuItem("Agregar Carpeta");
        eliminarCarpetaMenuItem = new JMenuItem("Eliminar Carpeta");
        agregarEstudianteMenuItem = new JMenuItem("Agregar Estudiante");
        eliminarEstudianteMenuItem = new JMenuItem("Eliminar Estudiante");
        cargarRecursoMenuItem = new JMenuItem("Cargar Recurso");
        regresarMenuItem = new JMenuItem("Regresar");
        salirMenuItem = new JMenuItem("Salir");
        
        // Listeners
        descripcionMenuItem.addActionListener(this);
        asignarProfesorMenuItem.addActionListener(this);
        agregarCarpetaMenuItem.addActionListener(this);
        eliminarCarpetaMenuItem.addActionListener(this);
        agregarEstudianteMenuItem.addActionListener(this);
        eliminarEstudianteMenuItem.addActionListener(this);
        cargarRecursoMenuItem.addActionListener(this);
        regresarMenuItem.addActionListener(this);
        salirMenuItem.addActionListener(e -> System.exit(0));

        // Añadir items al menú
        menuEditarCurso.add(descripcionMenuItem);
        menuEditarCurso.add(asignarProfesorMenuItem);
        menuEditarCurso.add(agregarCarpetaMenuItem);
        menuEditarCurso.add(eliminarCarpetaMenuItem);
        menuEditarCurso.add(agregarEstudianteMenuItem);
        menuEditarCurso.add(eliminarEstudianteMenuItem);
        menuEditarCurso.add(cargarRecursoMenuItem);
        menuEditarCurso.addSeparator();
        menuEditarCurso.add(regresarMenuItem);

        menuBar.add(menuEditarCurso);
       
        // Crear el layout y panel contenedor
        
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);  // Panel donde cambiarán las vistas

        // Crear los paneles
        JPanel panelDescripcion = crearPanelDescripcion();
        JPanel panelAsignarProfesor = crearPanelAsignarProfesor();
        JPanel panelAgregarCarpeta = crearPanelAgregarCarpeta();
        JPanel panelEliminarCarpeta = crearPanelEliminarCarpeta();
        JPanel panelAgregarEstudiante = crearPanelAgregarEstudiante();
        JPanel panelEliminarEstudiante = crearPanelEliminarEstudiante();
        JPanel panelCargarRecurso = crearPanelCargarRecurso();


        // Añadir los paneles al CardLayout
        panelContenedor.add(panelDescripcion, "Descripción");
        panelContenedor.add(panelAsignarProfesor, "Asignar Profesor");
        panelContenedor.add(panelAgregarCarpeta, "Agregar Carpeta");
        panelContenedor.add(panelEliminarCarpeta, "Eliminar Carpeta");
        panelContenedor.add(panelAgregarEstudiante, "Agregar Estudiante");
        panelContenedor.add(panelEliminarEstudiante, "Eliminar Estudiante");
        panelContenedor.add(panelCargarRecurso, "Cargar Recurso");

        // Añadir el panel de contenido al panel principal
        setLayout(new BorderLayout());
        this.add(menuBar, BorderLayout.NORTH);
        this.add(panelContenedor, BorderLayout.CENTER);

        /* Configurar el panel superior con la selección de cursos
        JPanel cursoPanel = new JPanel();
        cursoPanel.setLayout(new FlowLayout());
        cursoPanel.add(new JLabel("Seleccione el curso a editar:"));

        cursoComboBox = new JComboBox<>(new String[]{"Curso 1", "Curso 2", "Curso 3"});
        cursoPanel.add(cursoComboBox);

        add(cursoPanel, BorderLayout.NORTH);

        // Botón para volver al menú principal
        JButton volverBtn = new JButton("Volver al Menú Principal");
        volverBtn.addActionListener(e -> volverAlMenuPrincipal());
        add(volverBtn, BorderLayout.SOUTH);
        */


       
    }
    
    //
    // PANELES
    //

    // Método para crear el panel de "Actualizar Descripción del Curso"
    private JPanel crearPanelDescripcion() {
    	 JPanel panelDescripcion = new JPanel(new GridLayout(7, 2, 10, 10)); // Utiliza);
    	 panelDescripcion.add(new JLabel("Panel de Descripción del Curso"));
         JLabel label = new JLabel("Descripción del Curso", JLabel.CENTER);
         descripcionArea = new JTextArea(5, 20);
         JButton btnActualizar = new JButton("Actualizar");
         JButton btnBack = new JButton("Regresar");

         panelDescripcion.add(label, BorderLayout.NORTH);
         panelDescripcion.add(new JScrollPane(descripcionArea), BorderLayout.CENTER);
         panelDescripcion.add(btnActualizar, BorderLayout.SOUTH);
         panelDescripcion.add(btnBack, BorderLayout.SOUTH);

         btnActualizar.addActionListener(e -> actualizarDescripcion());
         btnBack.addActionListener(this);

         return panelDescripcion; // Retornar el panel para añadirlo al CardLayout
    }
    private JPanel crearPanelAsignarProfesor() {
    	JPanel panelProfesor = new JPanel(new GridLayout(7, 2, 10, 10)); // Utiliza GridLayout para organizar los campos
        //JLabel label = new JLabel("Asignar Profesor", JLabel.CENTER);
        
        // Campos de texto para los datos del profesor
        JTextField nombreField = new JTextField(20);
        JTextField rutField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField especialidadField = new JTextField(20);

        // Botón para asignar el profesor
        JButton btnAsignar = new JButton("Asignar");
        JButton btnBack = new JButton("Regresar");
        
        // Agregar los componentes al panel
        //panelProfesor.add(label, BorderLayout.NORTH);
        panelProfesor.add(new JLabel("Nombre del Profesor:"));
        panelProfesor.add(nombreField);
        panelProfesor.add(new JLabel("RUT del Profesor:"));
        panelProfesor.add(rutField);
        panelProfesor.add(new JLabel("Email del Profesor:"));
        panelProfesor.add(emailField);
        panelProfesor.add(new JLabel("Especialidad del Profesor:"));
        panelProfesor.add(especialidadField);
        panelProfesor.add(new JLabel()); // Espacio vacío
        panelProfesor.add(btnAsignar, BorderLayout.SOUTH); // Botón en la última fila
        panelProfesor.add(btnBack, BorderLayout.SOUTH); // Botón en la última fila

        // Añadir el ActionListener al botón
        btnAsignar.addActionListener(e -> asignarProfesor(nombreField.getText(), rutField.getText(), emailField.getText(), especialidadField.getText()));
        btnBack.addActionListener(this);
        
       return panelProfesor;// Retornar el panel para añadirlo al CardLayout
    }
    private JPanel crearPanelAgregarCarpeta() {
        JPanel panelAgregarCarpeta = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Agregar Carpeta", JLabel.CENTER);
        JButton btnAgregar = new JButton("Agregar Carpeta");
        JButton btnBack = new JButton("Regresar");

        panelAgregarCarpeta.add(label, BorderLayout.NORTH);
        panelAgregarCarpeta.add(btnAgregar, BorderLayout.SOUTH);
        panelAgregarCarpeta.add(btnBack, BorderLayout.SOUTH);


        btnAgregar.addActionListener(e -> agregarCarpeta());
        btnBack.addActionListener(this);

        return panelAgregarCarpeta; // Retornar el panel para añadirlo al CardLayout
    }
    private JPanel crearPanelEliminarCarpeta() {
    	JPanel panelEliminarCarpeta = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Eliminar Carpeta", JLabel.CENTER);
        JButton btnEliminar = new JButton("Eliminar Carpeta");
        JButton btnBack = new JButton("Regresar");

        panelEliminarCarpeta.add(label, BorderLayout.NORTH);
        panelEliminarCarpeta.add(btnEliminar, BorderLayout.SOUTH);
        panelEliminarCarpeta.add(btnBack, BorderLayout.SOUTH);

        btnEliminar.addActionListener(e -> eliminarCarpeta());
        btnBack.addActionListener(this);

        return panelEliminarCarpeta;// Retornar el panel para añadirlo al CardLayout
    }
    private JPanel crearPanelAgregarEstudiante() {
    	JPanel panelAgregarEstudiante = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Agregar Estudiante", JLabel.CENTER);
        JButton btnAgregar = new JButton("Agregar Estudiante");
        JButton btnBack = new JButton("Regresar");

        panelAgregarEstudiante.add(label, BorderLayout.NORTH);
        panelAgregarEstudiante.add(btnAgregar, BorderLayout.SOUTH);
        panelAgregarEstudiante.add(btnBack, BorderLayout.SOUTH);


        btnAgregar.addActionListener(e -> agregarEstudiante());
        btnBack.addActionListener(this);

        return panelAgregarEstudiante;// Retornar el panel para añadirlo al CardLayout
    }
    private JPanel crearPanelEliminarEstudiante() {
    	JPanel panelEliminarEstudiante = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Eliminar Estudiante", JLabel.CENTER);
        JButton btnEliminar = new JButton("Eliminar Estudiante");

        panelEliminarEstudiante.add(label, BorderLayout.NORTH);
        panelEliminarEstudiante.add(btnEliminar, BorderLayout.SOUTH);

        btnEliminar.addActionListener(e -> eliminarEstudiante());

        return panelEliminarEstudiante; // Retornar el panel para añadirlo al CardLayout
    }
    private JPanel crearPanelCargarRecurso() {
    	JPanel panelCargarRecurso = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Cargar Recurso", JLabel.CENTER);
        JButton btnCargar = new JButton("Cargar Recurso");

        panelCargarRecurso.add(label, BorderLayout.NORTH);
        panelCargarRecurso.add(btnCargar, BorderLayout.SOUTH);

        btnCargar.addActionListener(e -> cargarRecurso());

        return panelCargarRecurso; // Retornar el panel para añadirlo al CardLayout
    }
    //
    // Métodos de acción para cada botón
    //
    private void actualizarDescripcion() {
        String descripcion = descripcionArea.getText();
        JOptionPane.showMessageDialog(this, "Descripción actualizada: " + descripcion);
    }

    // Método para manejar la asignación del profesor
    private void asignarProfesor(String nombre, String rut, String email, String especialidad) {
        // Crear nuevo objeto Profesor con los datos ingresados
        Profesor nuevoProfesor = new Profesor(nombre, rut, email, especialidad);
        // Asumir que tienes un método en el curso para asignar el profesor
        //curso.asignarProfesor(nuevoProfesor);
        JOptionPane.showMessageDialog(this, "Profesor asignado con éxito.");
    }

    private void agregarCarpeta() {
        String nuevaCarpeta = JOptionPane.showInputDialog("Ingrese el nombre de la nueva carpeta:");
        if (nuevaCarpeta != null && !nuevaCarpeta.trim().isEmpty()) {
            System.out.println("Carpeta agregada: " + nuevaCarpeta);
        }
    }

    private void eliminarCarpeta() {
        String carpetaSeleccionada = JOptionPane.showInputDialog("Ingrese el nombre de la carpeta a eliminar:");
        System.out.println("Carpeta eliminada: " + carpetaSeleccionada);
    }

    private void agregarEstudiante() {
        String nuevoEstudiante = JOptionPane.showInputDialog("Ingrese el nombre del nuevo estudiante:");
        if (nuevoEstudiante != null && !nuevoEstudiante.trim().isEmpty()) {
            System.out.println("Estudiante agregado: " + nuevoEstudiante);
        }
    }

    private void eliminarEstudiante() {
        String estudianteSeleccionado = JOptionPane.showInputDialog("Ingrese el nombre del estudiante a eliminar:");
        System.out.println("Estudiante eliminado: " + estudianteSeleccionado);
    }

    private void cargarRecurso() {
        System.out.println("Recurso cargado");
    }

    
 // Método que gestiona las acciones del menú
    @Override
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == descripcionMenuItem) {
            cardLayout.show(panelContenedor, "Descripción");
        } else if (e.getSource() == asignarProfesorMenuItem) {
            cardLayout.show(panelContenedor, "Asignar Profesor");
        } else if (e.getSource() == agregarCarpetaMenuItem) {
            cardLayout.show(panelContenedor, "Agregar Carpeta");
        } else if (e.getSource() == eliminarCarpetaMenuItem) {
            cardLayout.show(panelContenedor, "Eliminar Carpeta");
        } else if (e.getSource() == agregarEstudianteMenuItem) {
            cardLayout.show(panelContenedor, "Agregar Estudiante");
        } else if (e.getSource() == eliminarEstudianteMenuItem) {
            cardLayout.show(panelContenedor, "Eliminar Estudiante");
        } else if (e.getSource() == cargarRecursoMenuItem) {
            cardLayout.show(panelContenedor, "Cargar Recurso");
        } else if (e.getSource() == regresarMenuItem) {
            mainApp.mostrarMenuPrincipal(); // Método en MainApp para volver al menú principal
        }
        else {
        	mainApp.mostrarMenuPrincipal(); // Método en MainApp para volver al menú principal
        }
    }
    
}