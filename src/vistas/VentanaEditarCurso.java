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
        JPanel panelAgregarCarpeta = new JPanel(new GridLayout(4, 2)); // Usamos GridLayout para organizar el formulario
        panelAgregarCarpeta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        // Etiquetas y campos de texto para nombre, ID y visibilidad de la carpeta
        JLabel labelNombre = new JLabel("Nombre de la Carpeta:");
        JTextField campoNombre = new JTextField(20);
        
        JLabel labelID = new JLabel("ID de la Carpeta:");
        JTextField campoID = new JTextField(20);
        
        JLabel labelEsPublica = new JLabel("¿Es pública? (true/false):");
        JTextField campoEsPublica = new JTextField(20);
    
        // Botón para agregar la carpeta
        JButton btnAgregar = new JButton("Agregar Carpeta");
        JButton btnBack = new JButton("Regresar");
    
        // Agregar componentes al panel
        panelAgregarCarpeta.add(labelNombre);
        panelAgregarCarpeta.add(campoNombre);
        panelAgregarCarpeta.add(labelID);
        panelAgregarCarpeta.add(campoID);
        panelAgregarCarpeta.add(labelEsPublica);
        panelAgregarCarpeta.add(campoEsPublica);
        panelAgregarCarpeta.add(btnAgregar);
        panelAgregarCarpeta.add(btnBack);
    
        // Acción del botón agregar carpeta
        btnAgregar.addActionListener(e -> {
            String nombreCarpeta = campoNombre.getText();
            int idCarpeta = Integer.parseInt(campoID.getText());
            boolean esPublica = Boolean.parseBoolean(campoEsPublica.getText());
    
            // Aquí puedes agregar lógica para crear y agregar la carpeta al sistema
            Carpeta nuevaCarpeta = new Carpeta(nombreCarpeta, idCarpeta, esPublica);
            sistema.agregarCarpeta(nuevaCarpeta); // Asumiendo que hay un sistema que gestiona carpetas
    
            JOptionPane.showMessageDialog(panelAgregarCarpeta, "Carpeta agregada con éxito.");
        });
    
        // Acción del botón regresar
        btnBack.addActionListener(this);
    
        return panelAgregarCarpeta; // Retornar el panel para añadirlo al CardLayout
    }

    private JPanel crearPanelEliminarCarpeta() {
    JPanel panelEliminarCarpeta = new JPanel(new GridLayout(3, 1)); // Usamos GridLayout para organizar el formulario
    panelEliminarCarpeta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Etiqueta y campo de texto para ingresar el nombre o ID de la carpeta a eliminar
    JLabel labelEliminar = new JLabel("Ingrese el nombre o ID de la carpeta a eliminar:");
    JTextField campoEliminar = new JTextField(20);

    // Botones para eliminar y regresar
    JButton btnEliminar = new JButton("Eliminar Carpeta");
    JButton btnBack = new JButton("Regresar");

    // Agregar componentes al panel
    panelEliminarCarpeta.add(labelEliminar);
    panelEliminarCarpeta.add(campoEliminar);
    panelEliminarCarpeta.add(btnEliminar);
    panelEliminarCarpeta.add(btnBack);

    // Acción del botón eliminar carpeta
    btnEliminar.addActionListener(e -> {
        String entradaEliminarCarpeta = campoEliminar.getText();
        try {
            // Intentar convertir la entrada a un número (eliminar por ID)
            int idCarpetaEliminar = Integer.parseInt(entradaEliminarCarpeta);
            curso.eliminarCarpeta(idCarpetaEliminar); // Asumiendo que "curso" es el objeto que gestiona las carpetas
            JOptionPane.showMessageDialog(panelEliminarCarpeta, "Carpeta con ID " + idCarpetaEliminar + " eliminada.");
        } catch (NumberFormatException ex) {
            // Si no es un número, eliminar por nombre
            curso.eliminarCarpeta(entradaEliminarCarpeta);
            JOptionPane.showMessageDialog(panelEliminarCarpeta, "Carpeta con nombre '" + entradaEliminarCarpeta + "' eliminada.");
        }
    });
    // Acción del botón regresar
    btnBack.addActionListener(this);

    return panelEliminarCarpeta; // Retornar el panel para añadirlo al CardLayout
}
    private JPanel crearPanelAgregarEstudiante() {
    JPanel panelAgregarEstudiante = new JPanel(new GridLayout(6, 2, 10, 10)); // Usamos GridLayout para organizar los campos
    panelAgregarEstudiante.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Etiquetas y campos de texto para cada dato del estudiante
    JLabel labelNombre = new JLabel("Nombre del Estudiante:");
    JTextField campoNombre = new JTextField(20);
    
    JLabel labelRut = new JLabel("RUT del Estudiante:");
    JTextField campoRut = new JTextField(20);
    
    JLabel labelCorreo = new JLabel("Correo Electrónico:");
    JTextField campoCorreo = new JTextField(20);
    
    JLabel labelParalelo = new JLabel("Paralelo:");
    JTextField campoParalelo = new JTextField(5);

    // Botones para agregar y regresar
    JButton btnAgregar = new JButton("Agregar Estudiante");
    JButton btnBack = new JButton("Regresar");

    // Agregar todos los componentes al panel
    panelAgregarEstudiante.add(labelNombre);
    panelAgregarEstudiante.add(campoNombre);
    panelAgregarEstudiante.add(labelRut);
    panelAgregarEstudiante.add(campoRut);
    panelAgregarEstudiante.add(labelCorreo);
    panelAgregarEstudiante.add(campoCorreo);
    panelAgregarEstudiante.add(labelParalelo);
    panelAgregarEstudiante.add(campoParalelo);
    panelAgregarEstudiante.add(btnAgregar);
    panelAgregarEstudiante.add(btnBack);

    // Acción del botón agregar estudiante
    btnAgregar.addActionListener(e -> {
        String nombreEstudiante = campoNombre.getText();
        String rutEstudiante = campoRut.getText();
        String correoEstudiante = campoCorreo.getText();
        int paraleloEstudiante;
        
        try {
            paraleloEstudiante = Integer.parseInt(campoParalelo.getText());
            // Generar un ID aleatorio para el estudiante
            int idEstudiante = generarIdUsuario(); // Método que debes tener implementado
            // Crear el nuevo estudiante
            Estudiante nuevoEstudiante = new Estudiante(nombreEstudiante, idEstudiante, rutEstudiante, correoEstudiante, paraleloEstudiante);
            curso.agregarEstudiante(nuevoEstudiante); // Asumiendo que curso es el objeto que gestiona los estudiantes
            // Mostrar confirmación
            JOptionPane.showMessageDialog(panelAgregarEstudiante, "Estudiante agregado exitosamente.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panelAgregarEstudiante, "Por favor ingrese un número válido para el paralelo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    // Acción del botón regresar
    btnBack.addActionListener(this);
    return panelAgregarEstudiante; // Retornar el panel para añadirlo al CardLayout
}
    private JPanel crearPanelEliminarEstudiante() {
    JPanel panelEliminarEstudiante = new JPanel(new GridLayout(3, 2, 10, 10)); // Usamos GridLayout para organizar los campos
    panelEliminarEstudiante.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Etiqueta y campo de texto para ingresar el nombre o ID del estudiante
    JLabel labelEliminar = new JLabel("Nombre o ID del Estudiante:");
    JTextField campoEliminar = new JTextField(20);

    // Botones para eliminar y regresar
    JButton btnEliminar = new JButton("Eliminar Estudiante");
    JButton btnBack = new JButton("Regresar");

    // Agregar todos los componentes al panel
    panelEliminarEstudiante.add(labelEliminar);
    panelEliminarEstudiante.add(campoEliminar);
    panelEliminarEstudiante.add(btnEliminar);
    panelEliminarEstudiante.add(btnBack);

    // Acción del botón eliminar estudiante
    btnEliminar.addActionListener(e -> {
        String entradaEliminarEstudiante = campoEliminar.getText();

        // Intentar eliminar por ID o nombre
        try {
            int idEstudianteEliminar = Integer.parseInt(entradaEliminarEstudiante);
            curso.eliminarEstudiante(idEstudianteEliminar); // Eliminar por ID
            JOptionPane.showMessageDialog(panelEliminarEstudiante, "Estudiante con ID " + idEstudianteEliminar + " eliminado.");
        } catch (NumberFormatException ex) {
            curso.eliminarEstudiante(entradaEliminarEstudiante); // Eliminar por nombre
            JOptionPane.showMessageDialog(panelEliminarEstudiante, "Estudiante con nombre " + entradaEliminarEstudiante + " eliminado.");
        }
    });

    // Acción del botón regresar
    btnBack.addActionListener(this);

    return panelEliminarEstudiante; // Retornar el panel para añadirlo al CardLayout
}

    private JPanel crearPanelCargarRecurso() {
    JPanel panelCargarRecurso = new JPanel(new GridLayout(5, 2, 10, 10)); // Usamos GridLayout para organizar los componentes
    panelCargarRecurso.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Etiqueta y campo para seleccionar la opción de cargar un solo recurso o una lista
    JLabel labelOpcion = new JLabel("Seleccione una opción:");
    String[] opciones = {"Un solo recurso", "Lista de recursos"};
    JComboBox<String> comboBoxOpcion = new JComboBox<>(opciones);

    // Etiqueta y campo de texto para ingresar el nombre o ID de la carpeta
    JLabel labelCarpeta = new JLabel("Nombre o ID de la Carpeta:");
    JTextField campoCarpeta = new JTextField(20);

    // Botón para cargar recurso(s)
    JButton btnCargar = new JButton("Cargar Recurso(s)");
    JButton btnBack = new JButton("Regresar");

    // Agregar todos los componentes al panel
    panelCargarRecurso.add(labelOpcion);
    panelCargarRecurso.add(comboBoxOpcion);
    panelCargarRecurso.add(labelCarpeta);
    panelCargarRecurso.add(campoCarpeta);
    panelCargarRecurso.add(btnCargar);
    panelCargarRecurso.add(btnBack);

    // Acción del botón para cargar recurso(s)
    btnCargar.addActionListener(e -> {
        String entradaCarpeta = campoCarpeta.getText();
        int opcionSeleccionada = comboBoxOpcion.getSelectedIndex(); // 0 = Un solo recurso, 1 = Lista de recursos

        try {
            int idCarpeta = Integer.parseInt(entradaCarpeta); // Intentar convertir la entrada a un número (ID)

            if (opcionSeleccionada == 0) { // Un solo recurso
                Recurso recurso = Recurso.crearRecurso(); // Método para crear un recurso
                if (recurso != null) {
                    curso.cargarRecurso(idCarpeta, recurso); // Llama al método de la clase Curso
                    JOptionPane.showMessageDialog(panelCargarRecurso, "Recurso cargado en la carpeta con ID " + idCarpeta);
                } else {
                    JOptionPane.showMessageDialog(panelCargarRecurso, "Error al crear el recurso.");
                }
            } else { // Lista de recursos
                List<Recurso> listaRecursos = Carpeta.crearListaRecursos(); // Método para crear una lista de recursos
                if (listaRecursos != null && !listaRecursos.isEmpty()) {
                    curso.cargarRecurso(idCarpeta, listaRecursos); // Llama al método de la clase Curso
                    JOptionPane.showMessageDialog(panelCargarRecurso, "Lista de recursos cargada en la carpeta con ID " + idCarpeta);
                } else {
                    JOptionPane.showMessageDialog(panelCargarRecurso, "Error al crear la lista de recursos.");
                }
            }
        } catch (NumberFormatException ex) { // Si no es un número, tratar como nombre de carpeta
            if (opcionSeleccionada == 0) { // Un solo recurso
                Recurso recurso = Recurso.crearRecurso(); // Método para crear un recurso
                if (recurso != null) {
                    curso.cargarRecurso(entradaCarpeta, recurso); // Llama al método de la clase Curso
                    JOptionPane.showMessageDialog(panelCargarRecurso, "Recurso cargado en la carpeta " + entradaCarpeta);
                } else {
                    JOptionPane.showMessageDialog(panelCargarRecurso, "Error al crear el recurso.");
                }
            } else { // Lista de recursos
                List<Recurso> listaRecursos = Carpeta.crearListaRecursos(); // Método para crear una lista de recursos
                if (listaRecursos != null && !listaRecursos.isEmpty()) {
                    curso.cargarRecurso(entradaCarpeta, listaRecursos); // Llama al método de la clase Curso
                    JOptionPane.showMessageDialog(panelCargarRecurso, "Lista de recursos cargada en la carpeta " + entradaCarpeta);
                } else {
                    JOptionPane.showMessageDialog(panelCargarRecurso, "Error al crear la lista de recursos.");
                }
            }
        }
    });

    // Acción del botón regresar
    btnBack.addActionListener(this);

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
