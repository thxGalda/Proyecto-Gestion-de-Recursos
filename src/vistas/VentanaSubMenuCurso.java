package vistas;

import paqueteMain.Usuario;
import paqueteMain.Profesor;
import paqueteMain.Curso;
import paqueteMain.Carpeta;
import java.util.List;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaSubMenuCurso extends JPanel implements ActionListener{
    private CardLayout cardLayout;
    private JPanel panelContenedor; // Panel principal con CardLayout
    
    // Menú
    private JMenuBar menuBar;
    private JMenu menuRevisarCurso;
    private JMenuItem regresarMenuItem, salirMenuItem;
    private JMenuItem revisarCarpetasMenuItem, mostrarEstudiantesMenuItem, buscarEstudianteMenuItem, ordenarCarpetasMenuItem, buscarRecursosMenuItem;

    private MainApp mainApp;
    private Curso curso;
    
    // Constructor
    public VentanaSubMenuCurso(MainApp mainApp, Curso curso) {
        this.mainApp = mainApp;  // Guardar referencia a MainApp
        this.curso = curso;
        initialize();  // Inicializar el contenido
    }

    private void initialize() {
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
        revisarCarpetasMenuItem.addActionListener(this);
        mostrarEstudiantesMenuItem.addActionListener(this);
        buscarEstudianteMenuItem.addActionListener(this);
        ordenarCarpetasMenuItem.addActionListener(this);
        buscarRecursosMenuItem.addActionListener(this);
        regresarMenuItem.addActionListener(this);
        salirMenuItem.addActionListener(e -> System.exit(0));

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
        JTextArea estudiantesArea = new JTextArea(10, 30);
        estudiantesArea.setEditable(false); // Hacemos el área de texto no editable
        
        // Obtenemos los nombres de los estudiantes inscritos y los mostramos
        List<String> nombresEstudiantes = curso.obtenerEstudiantesInscritos();
        if (nombresEstudiantes.isEmpty()) {
            estudiantesArea.setText("No hay estudiantes inscritos.");
        } else {
            StringBuilder listaEstudiantes = new StringBuilder();
            for (String nombre : nombresEstudiantes) {
                listaEstudiantes.append(nombre).append("\n"); // Añadimos cada nombre a la lista
            }
            estudiantesArea.setText(listaEstudiantes.toString());
        }
        
        // Añadimos el área de texto a un JScrollPane para poder hacer scroll si hay muchos estudiantes
        JScrollPane scrollPane = new JScrollPane(estudiantesArea);
        
        // Agregar boton regresar
        JButton volverBtn = new JButton("Volver al Menú Principal");
        volverBtn.addActionListener(this);
        
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
        JButton botonOrdenar = new JButton("Ordenar");
        
        // Listener para el botón
        botonOrdenar.addActionListener(e -> {
            // Obtener la opción seleccionada
            String seleccion = (String) comboBoxOrden.getSelectedItem();
            
            // Ordenar según la selección
            if (seleccion.equals("Ordenar por Nombre")) {
                curso.ordenarCarpetasPorNombre();
                carpetasArea.setText("Carpetas ordenadas por nombre.");
            } else if (seleccion.equals("Ordenar por Visibilidad")) {
                curso.ordenarCarpetasPorVisibilidad();
                carpetasArea.setText("Carpetas ordenadas por visibilidad.");
            }
        });
        
        // Crear el botón para regresar al menú de curso
        JButton volverBtn = new JButton("Volver al Menú Principal");
        volverBtn.addActionListener(this);
        
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
    private JPanel crearPanelSeleccionCarpeta() {
        // Crear el panel principal
        JPanel panelSeleccionCarpeta = new JPanel(new BorderLayout());
        panelSeleccionCarpeta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Crear el panel superior con la selección de carpetas
        JPanel carpetaPanel = new JPanel(new FlowLayout());
        carpetaPanel.add(new JLabel("Seleccione la carpeta:"));
        

        // Obtener las carpetas asociadas al curso actual
        List<Carpeta> carpetas = curso.getCarpetas();  // Obtener las carpetas del curso actual
        JComboBox<Integer> carpetaComboBox = new JComboBox<>();  // JComboBox que contiene solo IDs de carpetas

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
                mainApp.mostrarVentanaSubmenuCarpeta(curso.getId(), idCarpeta);
            }
        });

        // Crear el botón para regresar al menú de curso
        JButton volverBtn = new JButton("Volver al Menú Principal");
        volverBtn.addActionListener(this);

        // Crear el panel inferior con los botones
        JPanel botonesPanel = new JPanel(new FlowLayout());
        botonesPanel.add(confirmarBtn);
        botonesPanel.add(volverBtn);

        // Agregar el panel de botones al layout principal
        panelSeleccionCarpeta.add(botonesPanel, BorderLayout.SOUTH);

        return panelSeleccionCarpeta;  // Retornar el panel para integrarlo en la ventana
    }
    
    //
    // Acciones
    //
    
    // Método que gestiona las acciones del menú
    @Override
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == mostrarEstudiantesMenuItem) {
            cardLayout.show(panelContenedor, "MostrarEstudiantes");
            panelContenedor.revalidate(); // Actualiza el layout
            panelContenedor.repaint();    // Refresca la interfaz gráfica
        } else if (e.getSource() == ordenarCarpetasMenuItem) {
            cardLayout.show(panelContenedor, "OrdenarCarpetas");
            panelContenedor.revalidate();
            panelContenedor.repaint();
        } else if (e.getSource() == revisarCarpetasMenuItem) {
            cardLayout.show(panelContenedor, "SeleccionCarpeta");
            panelContenedor.revalidate();
            panelContenedor.repaint();
            
        } else if (e.getSource() == buscarRecursosMenuItem) {
        	mainApp.mostrarVentanaBusqueda(this.curso); // Llama al método en MainApp
        } else if (e.getSource() == buscarEstudianteMenuItem) {
        	mainApp.mostrarVentanaBusqueda(this.curso); // Llama al método en MainAp
       
        } else if (e.getSource() == regresarMenuItem) {
            mainApp.mostrarVentana("MenuPrincipal"); // Método en MainApp para volver al menú principal
        } else if (e.getSource() == salirMenuItem) {
            // Cerrar la aplicación
            System.exit(0);
        }
        else {
        	mainApp.mostrarVentana("MenuPrincipal");
        }
    }
}
