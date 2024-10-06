package vistas;

import java.util.List;
import javax.swing.*;
import controladores.*;
import modelo.Curso;
import modelo.Carpeta;
import java.awt.*;


public class VentanaSubMenuCurso extends JPanel{
    private CardLayout cardLayout;
    private JPanel panelContenedor; // Panel principal con CardLayout
    private CursoController controlador;
    private Curso curso;
    
    // Menú
    private JMenuBar menuBar;
    private JMenu menuRevisarCurso;
    private JMenuItem regresarMenuItem, salirMenuItem;
    private JMenuItem revisarCarpetasMenuItem, mostrarEstudiantesMenuItem, buscarEstudianteMenuItem, ordenarCarpetasMenuItem, buscarRecursosMenuItem;
    private JComboBox<Integer> carpetaComboBox;
    private JComboBox<String> comboBoxOrden;  
    private JTextArea estudiantesArea, carpetasArea;
    private JButton confirmarBtn, botonOrdenar, volverBtn;
    
    

    
    // Constructor
    public VentanaSubMenuCurso(CursoController controlador, Curso curso) {
        this.controlador= controlador;  
        this.curso = curso;
        initialize();  // Inicializar el contenido
    }

    public void initialize() {
    	// Menú Barra
    	 setLayout(new BorderLayout());

    	
    	JLabel cursoNombreLabel = new JLabel("Submenú de Revisión: " + curso.getNombre() + " - " + curso.getId());
        this.add(cursoNombreLabel);
        menuBar = new JMenuBar();
        menuRevisarCurso= new JMenu("Submenu Curso: " + curso.getNombre() + " - " + curso.getId());
       
        
        // Crear JMenuItems para cada opción
        
        // Crear JMenuItems para cada opción
        revisarCarpetasMenuItem = new JMenuItem("Revisar Carpetas");
        mostrarEstudiantesMenuItem = new JMenuItem("Mostrar Estudiantes");
        buscarEstudianteMenuItem = new JMenuItem("Buscar Estudiante");
        ordenarCarpetasMenuItem = new JMenuItem("Ordenar Carpetas");
        buscarRecursosMenuItem = new JMenuItem("Buscar Recursos");
        regresarMenuItem = new JMenuItem("Regresar");
        salirMenuItem = new JMenuItem("Salir");

        // Listeners

        // Añadir items al menú
        menuRevisarCurso.add(revisarCarpetasMenuItem);
        menuRevisarCurso.add(mostrarEstudiantesMenuItem);
        menuRevisarCurso.add(buscarEstudianteMenuItem);
        menuRevisarCurso.add(ordenarCarpetasMenuItem);
        menuRevisarCurso.add(buscarRecursosMenuItem);
        menuRevisarCurso.addSeparator();
        menuRevisarCurso.add(regresarMenuItem);
        menuRevisarCurso.add(salirMenuItem);

        menuBar.add(menuRevisarCurso);
        
        // Añadir la barra de menú en la parte superior (también puedes añadir en BorderLayout.NORTH si prefieres)
        this.add(menuBar, BorderLayout.BEFORE_FIRST_LINE);
       
        // Crear el layout y panel contenedor
        
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);  // Panel donde cambiarán las vistas

        // Crear los paneles
        
        JPanel mostrarEstudiantesPanel = crearPanelMostrarEstudiantes();
        JPanel ordenarCarpetasPanel = crearPanelOrdenarCarpetas();
        JPanel seleccionCarpetaPanel = crearPanelSeleccionCarpeta();


        // Añadir los paneles al CardLayout

        panelContenedor.add(mostrarEstudiantesPanel, "MostrarEstudiantes");
        panelContenedor.add(ordenarCarpetasPanel, "OrdenarCarpetas");
        panelContenedor.add(seleccionCarpetaPanel, "SeleccionCarpeta");

        // Añadir el panel de contenido al panel principal
        this.add(panelContenedor, BorderLayout.CENTER);
    }
    //
    // PANELES
    //
    private JPanel crearPanelMostrarEstudiantes() {
        JPanel panel = new JPanel(new BorderLayout()); // Usamos BorderLayout para organizar mejor los elementos

        // Etiqueta para el título
        JLabel label = new JLabel("Lista de Estudiantes:");
        label.setHorizontalAlignment(JLabel.CENTER);

        // Área de texto para mostrar los nombres de los estudiantes
        estudiantesArea = new JTextArea(10, 30);
        estudiantesArea.setEditable(false); // Hacemos el área de texto no editable

        // Obtenemos los nombres de los estudiantes inscritos a través del controlador
        String listaEstudiantes = controlador.obtenerNombresEstudiantes();
        estudiantesArea.setText(listaEstudiantes); // Configuramos el área de texto con la lista

        // Añadimos el área de texto a un JScrollPane para poder hacer scroll si hay muchos estudiantes
        JScrollPane scrollPane = new JScrollPane(estudiantesArea);

        // Agregar botón regresar
        volverBtn = new JButton("Volver al Menú Principal");
        // Añadimos los componentes al panel
        panel.add(label, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(volverBtn, BorderLayout.SOUTH);

        return panel;
    }
    private JPanel crearPanelOrdenarCarpetas() {
        JPanel panel = new JPanel(new BorderLayout()); // Usamos BorderLayout para una mejor organización
        
        // Etiqueta para el título
        JLabel label = new JLabel("Ordenar Carpetas:");
        label.setHorizontalAlignment(JLabel.CENTER);
        
        // Área de texto para mostrar el estado o las carpetas (simulado)
        JTextArea carpetasArea = new JTextArea(10, 30);
        carpetasArea.setText("Aquí puedes ordenar las carpetas...");
        carpetasArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(carpetasArea);
        
        // JComboBox para las opciones de ordenamiento
        String[] opcionesOrden = {"Ordenar por Nombre", "Ordenar por Visibilidad"};
        JComboBox<String> comboBoxOrden = new JComboBox<>(opcionesOrden);
        
        // Botón para realizar la ordenación
        botonOrdenar = new JButton("Ordenar");
        volverBtn = new JButton("Volver al Menú Principal");
        
        // Panel para contener la selección de ordenamiento y el botón
        JPanel panelOpciones = new JPanel();
        panelOpciones.add(comboBoxOrden);
        panelOpciones.add(botonOrdenar);
        panelOpciones.add(volverBtn);
        
        // Añadir componentes al panel principal
        panel.add(label, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelOpciones, BorderLayout.SOUTH);
        
        return panel;
    }
    private JPanel crearPanelSeleccionCarpeta() { // Metodo en clase submenu, permite elegir una carpeta
        // Crear el panel principal
        JPanel panelSeleccionCarpeta = new JPanel(new BorderLayout());
        panelSeleccionCarpeta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Crear el panel superior con la selección de carpetas
        JPanel carpetaPanel = new JPanel(new FlowLayout());
        carpetaPanel.add(new JLabel("Seleccione la carpeta:"));
        

        // Obtener las carpetas asociadas al curso actual
        List<Carpeta> carpetas = curso.getCarpetas();  // Obtener las carpetas del curso actual
        carpetaComboBox = new JComboBox<>();  // JComboBox que contiene solo IDs de carpetas

        // Verificar si hay carpetas en el curso
        if (carpetas.isEmpty()) {
            // Si no hay carpetas, agregar IDs simulados
            carpetaComboBox.addItem(1001);
            carpetaComboBox.addItem(1002);
            carpetaComboBox.addItem(2121);
        } else {
            // Llenar el ComboBox con los IDs de las carpetas
            for (Carpeta carpeta : carpetas) {
                carpetaComboBox.addItem(carpeta.getId());  // Guardar directamente el ID de la carpeta
            }
        }
        carpetaPanel.add(carpetaComboBox);

        // Agregar el panel de selección de carpetas al layout
        panelSeleccionCarpeta.add(carpetaPanel, BorderLayout.NORTH);

        // Crear el botón para confirmar la selección
        JButton confirmarBtn = new JButton("Confirmar selección");
        confirmarBtn.addActionListener(e -> {
            // Obtener el ID de la carpeta seleccionada directamente del JComboBox
            Integer idCarpeta = (Integer) carpetaComboBox.getSelectedItem();
            
            if (idCarpeta != null) {
                // Mostrar la ventana de la carpeta seleccionada usando el ID
            }
        });

        // Crear el botón para regresar al menú de curso
        volverBtn = new JButton("Volver al Menú Principal");

        // Crear el panel inferior con los botones
        JPanel botonesPanel = new JPanel(new FlowLayout());
        botonesPanel.add(confirmarBtn);
        botonesPanel.add(volverBtn);

        // Agregar el panel de botones al layout principal
        panelSeleccionCarpeta.add(botonesPanel, BorderLayout.SOUTH);

        return panelSeleccionCarpeta;  // Retornar el panel para integrarlo en la ventana
    }
    
    //
    // Getters de componentes
    //
    
 // Getters para los componentes
    public JMenuItem getRegresarMenuItem() {
        return regresarMenuItem;
    }

    public JMenuItem getSalirMenuItem() {
        return salirMenuItem;
    }

    public JMenuItem getRevisarCarpetasMenuItem() {
        return revisarCarpetasMenuItem;
    }

    public JMenuItem getMostrarEstudiantesMenuItem() {
        return mostrarEstudiantesMenuItem;
    }

    public JMenuItem getBuscarEstudianteMenuItem() {
        return buscarEstudianteMenuItem;
    }

    public JMenuItem getOrdenarCarpetasMenuItem() {
        return ordenarCarpetasMenuItem;
    }

    public JMenuItem getBuscarRecursosMenuItem() {
        return buscarRecursosMenuItem;
    }

    public JPanel getPanelContenedor() {
        return panelContenedor;
    }

    public void mostrarPanel(String nombrePanel) {
        cardLayout.show(panelContenedor, nombrePanel);
    }
    
    // Getters para componentes importantes
    public JComboBox<Integer> getCarpetaComboBox() {
        return carpetaComboBox;
    }

    public JButton getConfirmarBtn() {
        return confirmarBtn;
    }

    public JButton getVolverBtn() {
        return volverBtn;
    }
    
    public JTextArea getEstudiantesArea() {
        return estudiantesArea;  // Asegúrate de tener el campo de instancia correspondiente
    }

    public JTextArea getCarpetasArea() {
        return carpetasArea;
    }

    public JComboBox<String> getComboBoxOrden() {
        return comboBoxOrden;
    }

    public JButton getBotonOrdenar() {
        return botonOrdenar;
    }



    
   
}
