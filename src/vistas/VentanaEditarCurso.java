package vistas;

import javax.swing.*;


import controladores.*;
import modelo.Curso;

import java.awt.*;


public class VentanaEditarCurso extends JPanel{
    private CardLayout cardLayout;
    private JPanel panelContenedor; // Panel principal con CardLayout
    private CursoController controlador;
    private Curso curso;
    
    // Menú
    private JMenuBar menuBar;
    private JMenu menuEditarCurso;
    private JMenuItem regresarMenuItem, salirMenuItem;
    private JMenuItem descripcionMenuItem, asignarProfesorMenuItem, agregarCarpetaMenuItem, eliminarCarpetaMenuItem;
    private JMenuItem agregarEstudianteMenuItem, eliminarEstudianteMenuItem, cargarRecursoMenuItem;
    
    // Componentes
    private JTextArea descripcionArea;
    private JComboBox<String> comboBoxOpcion;
    private JTextField nombreField, rutField, emailField, especialidadField, campoNombre, campoID, campoEsPublica, campoEliminar, campoNombreEstudiante, campoRutEstudiante, campoCorreoEstudiante, campoParalelo, campoEliminarEstudiante, campoCarpeta;
    private JTextField campoTitulo, campoFormato, campoAutor, campoCategoria, campoFecha, campoCurso, campoVisibilidad;
    private JButton btnActualizar, btnAsignarProfesor, btnEliminar, btnAgregarCarpeta, btnEliminarCarpeta, btnAgregarEstudiante, btnCargar, btnEliminarEstudiante, btnBack;

    
    // Constructor
    public VentanaEditarCurso(CursoController controlador, Curso curso) {
        this.controlador = controlador;  
        this.curso = curso; // Objeto curso para identificar a cual curso le pertenece ventana
        initialize();  // Inicializar el contenido
    }

    public void initialize() {
    	// Menú Barra
    	
    	setLayout(new BorderLayout());

    	JLabel cursoNombreLabel = new JLabel("Submenú de Edición: " + curso.getNombre() + " - " + curso.getId());
        this.add(cursoNombreLabel);
        
        menuBar = new JMenuBar();
        menuEditarCurso = new JMenu("Editar Curso: "  + curso.getNombre() + " - " + curso.getId());

        this.add(cursoNombreLabel);
        
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
        menuEditarCurso.add(salirMenuItem);

        menuBar.add(menuEditarCurso);
       
        // Añadir la barra de menú en la parte superior (también puedes añadir en BorderLayout.NORTH si prefieres)
        this.add(menuBar, BorderLayout.BEFORE_FIRST_LINE);
        
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
        this.add(panelContenedor, BorderLayout.CENTER);
    }
    
    //
    // PANELES
    //
    private JPanel crearPanelDescripcion() {
        JPanel panelDescripcion = new JPanel(new GridLayout(7, 2, 10, 10));
        panelDescripcion.add(new JLabel("Panel de Descripción del Curso"));
        JLabel label = new JLabel("Descripción del Curso", JLabel.CENTER);
        descripcionArea = new JTextArea(5, 20);
        btnActualizar = new JButton("Actualizar");
        btnBack = new JButton("Regresar a Menú Principal");

        panelDescripcion.add(label, BorderLayout.NORTH);
        panelDescripcion.add(new JScrollPane(descripcionArea), BorderLayout.CENTER);
        panelDescripcion.add(btnActualizar, BorderLayout.SOUTH);
        panelDescripcion.add(btnBack);

        return panelDescripcion;
    }
    private JPanel crearPanelAsignarProfesor() {
    	JPanel panelProfesor = new JPanel(new GridLayout(7, 2, 10, 10)); // Utiliza GridLayout para organizar los campos
    	panelProfesor.add(new JLabel("Panel de Asignar Profesor del Curso"));
        
        // Campos de texto para los datos del profesor
        nombreField = new JTextField(20);
        rutField = new JTextField(20);
        emailField = new JTextField(20);
        especialidadField = new JTextField(20);

        // Botón para asignar el profesor
        btnAsignarProfesor = new JButton("Asignar");
        btnBack = new JButton("Regresar a Menú Principal");
        
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
        panelProfesor.add(btnAsignarProfesor, BorderLayout.SOUTH); // Botón en la última fila
        panelProfesor.add(btnBack, BorderLayout.SOUTH); // Botón en la última fila
        
       return panelProfesor;// Retornar el panel para añadirlo al CardLayout
    }
    private JPanel crearPanelAgregarCarpeta() {
        JPanel panelAgregarCarpeta = new JPanel(new GridLayout(4, 2)); // Usamos GridLayout para organizar el formulario
        panelAgregarCarpeta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        // Etiquetas y campos de texto para nombre, ID y visibilidad de la carpeta
        JLabel labelNombre = new JLabel("Nombre de la Carpeta:");
        campoNombre = new JTextField(20);
        
        JLabel labelID = new JLabel("ID de la Carpeta:");
        campoID = new JTextField(20);
        
        JLabel labelEsPublica = new JLabel("¿Es pública? (true/false):");
        campoEsPublica = new JTextField(20);
    
        // Botón para agregar la carpeta
        btnAgregarCarpeta = new JButton("Agregar Carpeta");
        btnBack = new JButton("Regresar a Menú Principal");
    
        // Agregar componentes al panel
        panelAgregarCarpeta.add(labelNombre);
        panelAgregarCarpeta.add(campoNombre);
        panelAgregarCarpeta.add(labelID);
        panelAgregarCarpeta.add(campoID);
        panelAgregarCarpeta.add(labelEsPublica);
        panelAgregarCarpeta.add(campoEsPublica);
        panelAgregarCarpeta.add(btnAgregarCarpeta);
        panelAgregarCarpeta.add(btnBack);
   
    
        return panelAgregarCarpeta; // Retornar el panel para añadirlo al CardLayout
    }
    private JPanel crearPanelEliminarCarpeta() {
        JPanel panelEliminarCarpeta = new JPanel(new GridLayout(3, 1)); // Usamos GridLayout para organizar el formulario
        panelEliminarCarpeta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Etiqueta y campo de texto para ingresar el nombre o ID de la carpeta a eliminar
        JLabel labelEliminar = new JLabel("Ingrese el nombre o ID de la carpeta a eliminar:");
        campoEliminar = new JTextField(20);

        // Botones para eliminar y regresar
        btnEliminarCarpeta = new JButton("Eliminar Carpeta");
        btnBack = new JButton("Regresar a Menú Principal");

        // Agregar componentes al panel
        panelEliminarCarpeta.add(labelEliminar);
        panelEliminarCarpeta.add(campoEliminar);
        panelEliminarCarpeta.add(btnEliminarCarpeta);
        panelEliminarCarpeta.add(btnBack);

        // Acción del botón eliminar carpeta
        btnEliminar.addActionListener(e -> {
            String entradaEliminarCarpeta = campoEliminar.getText();
            try {
                // Intentar convertir la entrada a un número (eliminar por ID)
                int idCarpetaEliminar = Integer.parseInt(entradaEliminarCarpeta);
                
                JOptionPane.showMessageDialog(panelEliminarCarpeta, "Carpeta con ID " + idCarpetaEliminar + " eliminada.");
            } catch (NumberFormatException ex) {
                // Si no es un número, eliminar por nombre
                
                JOptionPane.showMessageDialog(panelEliminarCarpeta, "Carpeta con nombre '" + entradaEliminarCarpeta + "' eliminada.");
            }
        });

        return panelEliminarCarpeta; // Retornar el panel para añadirlo al CardLayout
    }
    private JPanel crearPanelAgregarEstudiante() {
        JPanel panelAgregarEstudiante = new JPanel(new GridLayout(6, 2, 10, 10)); // Usamos GridLayout para organizar los campos
        panelAgregarEstudiante.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Etiquetas y campos de texto para cada dato del estudiante
        JLabel labelNombre = new JLabel("Nombre del Estudiante:");
        campoNombre = new JTextField(20);
        
        JLabel labelRut = new JLabel("RUT del Estudiante:");
        campoRutEstudiante = new JTextField(20);
        
        JLabel labelCorreo = new JLabel("Correo Electrónico:");
        campoCorreoEstudiante = new JTextField(20);
        
        JLabel labelParalelo = new JLabel("Paralelo:");
        campoParalelo = new JTextField(5);

        // Botones para agregar y regresar
        btnAgregarEstudiante = new JButton("Agregar Estudiante");
        btnBack = new JButton("Regresar a Menú Principal");

        // Agregar todos los componentes al panel
        panelAgregarEstudiante.add(labelNombre);
        panelAgregarEstudiante.add(campoNombre);
        panelAgregarEstudiante.add(labelRut);
        panelAgregarEstudiante.add(campoRutEstudiante);
        panelAgregarEstudiante.add(labelCorreo);
        panelAgregarEstudiante.add(campoCorreoEstudiante);
        panelAgregarEstudiante.add(labelParalelo);
        panelAgregarEstudiante.add(campoParalelo);
        panelAgregarEstudiante.add(btnAgregarEstudiante);
        panelAgregarEstudiante.add(btnBack);

        return panelAgregarEstudiante; // Retornar el panel para añadirlo al CardLayout
    }
    private JPanel crearPanelEliminarEstudiante() {
        JPanel panelEliminarEstudiante = new JPanel(new GridLayout(3, 2, 10, 10)); // Usamos GridLayout para organizar los campos
        panelEliminarEstudiante.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Etiqueta y campo de texto para ingresar el nombre o ID del estudiante
        JLabel labelEliminar = new JLabel("Nombre o ID del Estudiante:");
        campoEliminar = new JTextField(20);

        // Botones para eliminar y regresar
        btnEliminarEstudiante = new JButton("Eliminar Estudiante");
        btnBack = new JButton("Regresar a Menú Principal");

        // Agregar todos los componentes al panel
        panelEliminarEstudiante.add(labelEliminar);
        panelEliminarEstudiante.add(campoEliminar);
        panelEliminarEstudiante.add(btnEliminarEstudiante);
        panelEliminarEstudiante.add(btnBack);

        // Acción del botón eliminar estudiante
        btnEliminar.addActionListener(e -> {
            String entradaEliminarEstudiante = campoEliminar.getText();

            // Intentar eliminar por ID o nombre
            try {
                int idEstudianteEliminar = Integer.parseInt(entradaEliminarEstudiante);
                
                JOptionPane.showMessageDialog(panelEliminarEstudiante, "Estudiante con ID " + idEstudianteEliminar + " eliminado.");
            } catch (NumberFormatException ex) {
                
                JOptionPane.showMessageDialog(panelEliminarEstudiante, "Estudiante con nombre " + entradaEliminarEstudiante + " eliminado.");
            }
        });


        return panelEliminarEstudiante; // Retornar el panel para añadirlo al CardLayout
    }
    private JPanel crearPanelCargarRecurso() {
        JPanel panelCargarRecurso = new JPanel(new GridLayout(7, 2, 10, 10)); // Usamos GridLayout para organizar los componentes
        panelCargarRecurso.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Etiqueta y campo para seleccionar la opción de cargar un solo recurso o una lista
        JLabel labelOpcion = new JLabel("Seleccione una opción:");
        String[] opciones = {"Un solo recurso", "Lista de recursos"};
        comboBoxOpcion = new JComboBox<>(opciones);

        // Etiqueta y campo de texto para ingresar el nombre o ID de la carpeta
        JLabel labelCarpeta = new JLabel("Nombre o ID de la Carpeta:");
        JTextField campoCarpeta = new JTextField(20);

        // Nuevos campos para detalles del recurso
        JLabel labelTitulo = new JLabel("Título del Recurso:");
        campoTitulo = new JTextField(20);

        JLabel labelFormato = new JLabel("Formato del Recurso:");
        campoFormato = new JTextField(20);

        JLabel labelAutor = new JLabel("Autor del Recurso:");
        campoAutor = new JTextField(20);

        JLabel labelCategoria = new JLabel("Categoría del Recurso:");
        campoCategoria = new JTextField(20);

        JLabel labelFecha = new JLabel("Fecha de Creación:");
        campoFecha = new JTextField(20);

        JLabel labelCurso = new JLabel("Curso Relacionado:");
        campoCurso = new JTextField(20);

        JLabel labelVisibilidad = new JLabel("Visibilidad del Recurso:");
        campoVisibilidad = new JTextField(20);

        // Botones
        JButton btnCargar = new JButton("Cargar Recurso(s)");
        JButton btnBack = new JButton("Regresar a Menú Principal");

        // Agregar todos los componentes al panel
        panelCargarRecurso.add(labelOpcion);
        panelCargarRecurso.add(comboBoxOpcion);
        panelCargarRecurso.add(labelCarpeta);
        panelCargarRecurso.add(campoCarpeta);
        panelCargarRecurso.add(labelTitulo);
        panelCargarRecurso.add(campoTitulo);
        panelCargarRecurso.add(labelFormato);
        panelCargarRecurso.add(campoFormato);
        panelCargarRecurso.add(labelAutor);
        panelCargarRecurso.add(campoAutor);
        panelCargarRecurso.add(labelCategoria);
        panelCargarRecurso.add(campoCategoria);
        panelCargarRecurso.add(labelFecha);
        panelCargarRecurso.add(campoFecha);
        panelCargarRecurso.add(labelCurso);
        panelCargarRecurso.add(campoCurso);
        panelCargarRecurso.add(labelVisibilidad);
        panelCargarRecurso.add(campoVisibilidad);
        panelCargarRecurso.add(btnCargar);
        panelCargarRecurso.add(btnBack);

        return panelCargarRecurso; // Retornar el panel para añadirlo al CardLayout
    }
    
    
    // Getters para los JMenuItems adicionales en VentanaEditarCurso
    
    public JMenuItem getDescripcionMenuItem() {
        return descripcionMenuItem;
    }

    public JMenuItem getAsignarProfesorMenuItem() {
        return asignarProfesorMenuItem;
    }

    public JMenuItem getAgregarCarpetaMenuItem() {
        return agregarCarpetaMenuItem;
    }

    public JMenuItem getEliminarCarpetaMenuItem() {
        return eliminarCarpetaMenuItem;
    }

    public JMenuItem getAgregarEstudianteMenuItem() {
        return agregarEstudianteMenuItem;
    }

    public JMenuItem getEliminarEstudianteMenuItem() {
        return eliminarEstudianteMenuItem;
    }

    public JMenuItem getCargarRecursoMenuItem() {
        return cargarRecursoMenuItem;
    }
    public JMenuItem getRegresarMenuItem() {
        return regresarMenuItem;
    }
    public JMenuItem getSalirMenuItem() {
        return salirMenuItem;
    }

    // Getters para acceder a los datos de los componentes
    public String getDescripcion() {
        return descripcionArea.getText();
    }

    public String getNombreProfesor() {
        return nombreField.getText();
    }

    public String getRutProfesor() {
        return rutField.getText();
    }

    public String getEmailProfesor() {
        return emailField.getText();
    }

    public String getEspecialidadProfesor() {
        return especialidadField.getText();
    }

    public String getNombreCarpeta() {
        return campoNombre.getText();
    }

    public String getIDCarpeta() {
        return campoID.getText();
    }

    public String getEsPublica() {
        return campoEsPublica.getText();
    }
  

    public String getNombreEliminar() {
        return campoEliminar.getText();
    }

    public String getNombreEstudiante() {
        return campoNombreEstudiante.getText();
    }

    public String getRutEstudiante() {
        return campoRutEstudiante.getText();
    }

    public String getCorreoEstudiante() {
        return campoCorreoEstudiante.getText();
    }

    public String getParaleloEstudiante() {
        return campoParalelo.getText();
    }

    public String getNombreEliminarEstudiante() {
        return campoEliminarEstudiante.getText();
    }
    
    public String getOpcionSeleccionada() {
        return (String) comboBoxOpcion.getSelectedItem();
    }

    public String getNombreCarpetaRecurso() {
        return campoCarpeta.getText();
    }
    
    public String getCampoTitulo() {
        return campoTitulo.getText().trim();
    }

    public String getCampoFormato() {
        return campoFormato.getText().trim();
    }

    public String getCampoAutor() {
        return campoAutor.getText().trim();
    }

    public String getCampoCategoria() {
        return campoCategoria.getText().trim();
    }

    public String getCampoFecha() {
        return campoFecha.getText().trim();
    }

    public String getCampoCurso() {
        return campoCurso.getText().trim();
    }

    public String getCampoVisibilidad() {
        return campoVisibilidad.getText().trim();
    }
    
    public JButton getActualizarButton() {
        return btnActualizar;
    }

    public JButton getAsignarProfesorButton() {
        return btnAsignarProfesor;
    }

    public JButton getEliminarButton() {
        return btnEliminar;
    }

    public JButton getAgregarCarpetaButton() {
        return btnAgregarCarpeta;
    }

    public JButton getEliminarCarpetaButton() {
        return btnEliminarCarpeta;
    }

    public JButton getAgregarEstudianteButton() {
        return btnAgregarEstudiante;
    }

    public JButton getEliminarEstudianteButton() {
        return btnEliminarEstudiante;
    }
    
    public JButton getCargarRecursoButton() {
    	return btnCargar;
    }

    public JButton getBackButton() {
        return btnBack;
    }
    // Método para mostrar un panel específico en el CardLayout
    public void mostrarPanel(String nombrePanel) {
        cardLayout.show(panelContenedor, nombrePanel);
    }

    
}