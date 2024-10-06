package vistas;

import java.awt.*;
import javax.swing.*;

import controladores.*;

import java.awt.event.*;

public class VentanaMenuPrincipal extends JPanel{
	private CardLayout cardLayout;  // Usaremos CardLayout de MainApp
    private JPanel panelContenedor; // Panel principal con CardLayout
    private JMenuBar menuBar;
    private JMenu menuPrincipal;
    private JMenuItem configMenuItem, fichaMenuItem, revisarMenuItem, modificarMenuItem, salirMenuItem;
    private JButton confirmarBtn, volverBtn, cambiarNombreBtn, cambiarEmailBtn, cambiarContrasenaBtn;
    private JTextField emailField, nombreField;
    private JComboBox<String> cursoComboBox;
    private MainController controller;
    
    // Constructor que acepta la instancia del controlador
    public VentanaMenuPrincipal(MainController controller) {
    	this.controller = controller;
        initialize();  // Inicializar el contenido
        controller.configurarMenu(this);
    }

    public void initialize() {
    	// Menú Barra
        menuBar = new JMenuBar();
        menuPrincipal = new JMenu("Menú Principal");

        configMenuItem = new JMenuItem("Configuración");
        fichaMenuItem = new JMenuItem("Ficha Personal");
        revisarMenuItem = new JMenuItem("Revisar Curso");
        modificarMenuItem = new JMenuItem("Modificar Curso");
        salirMenuItem = new JMenuItem("Salir");

        // Añadir items al menú
        menuPrincipal.add(configMenuItem);
        menuPrincipal.add(fichaMenuItem);
        menuPrincipal.add(revisarMenuItem);
        menuPrincipal.add(modificarMenuItem);
        menuPrincipal.addSeparator();
        menuPrincipal.add(salirMenuItem);
        menuBar.add(menuPrincipal);

        // Crear el layout y panel contenedor
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);  // Panel donde cambiarán las vistas

        // Crear los paneles
        JPanel panelFichaPersonal = crearPanelFichaPersonal(controller);
        JPanel panelConfiguracion = crearPanelConfiguracion(controller);
        JPanel panelRevisarCurso = crearPanelSeleccionCurso(true);
        JPanel panelEditarCurso = crearPanelSeleccionCurso(false);

        // Añadir los paneles al CardLayout
        panelContenedor.add(panelFichaPersonal, "Ficha Personal");
        panelContenedor.add(panelConfiguracion, "Configuración");
        panelContenedor.add(panelRevisarCurso, "Revisar Curso");
        panelContenedor.add(panelEditarCurso, "Editar Curso");

        // Añadir el panel de contenido al panel principal
        setLayout(new BorderLayout());
        this.add(menuBar, BorderLayout.NORTH);
        this.add(panelContenedor, BorderLayout.CENTER);
    }
    
    public void activarOpcionesProfesor() {
    	modificarMenuItem.setEnabled(true);
    }

    public void desactivarOpcionesProfesor() {
        modificarMenuItem.setEnabled(false);
    }

    private JPanel crearPanelFichaPersonal(MainController controller) {
        JPanel panelFicha = new JPanel(new GridLayout(3, 1)); // Un layout simple con 3 filas

        // Solicitar información del usuario al controlador
        String nombre = controller.getUsuarioActual().getNombre();
        String rut = controller.getUsuarioActual().getRut();
        String email = controller.getUsuarioActual().getEmail();

        // Añadir la información del usuario
        JLabel nombreLabel = new JLabel("Nombre: " + nombre);
        JLabel emailLabel = new JLabel("Email: " + email);
        JLabel rutLabel = new JLabel("Rut: " + rut);

        panelFicha.add(nombreLabel);
        panelFicha.add(emailLabel);
        panelFicha.add(rutLabel);

     // Información adicional dependiendo del tipo de usuario
        if (controller.esEstudiante()) {
            int paralelo = controller.getParaleloEstudiante();
            JLabel paraleloLabel = new JLabel("Paralelo: " + paralelo);
            panelFicha.add(paraleloLabel);
        } else if (controller.esProfesor()) {
            String departamento = controller.getDepartamentoProfesor();
            JLabel departamentoLabel = new JLabel("Departamento: " + departamento);
            panelFicha.add(departamentoLabel);
        }

        return panelFicha; // Retornar el panel para añadirlo al CardLayout
    }

    private JPanel crearPanelConfiguracion(MainController controller) {
        JPanel panelConfig = new JPanel(new GridLayout(4, 2)); // Layout con 4 filas y 2 columnas

        // Solicitar información actual al controlador
        String nombre = controller.getUsuarioActual().getNombre();
        String email = controller.getUsuarioActual().getEmail();

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreField = new JTextField(nombre);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(email);

        cambiarNombreBtn = new JButton("Cambiar Nombre");
        cambiarEmailBtn = new JButton("Cambiar Email");
        cambiarContrasenaBtn = new JButton("Cambiar Contraseña");

     

        // Añadir todos los componentes al panel
        panelConfig.add(nombreLabel);
        panelConfig.add(nombreField);
        panelConfig.add(emailLabel);
        panelConfig.add(emailField);
        panelConfig.add(cambiarNombreBtn);
        panelConfig.add(cambiarEmailBtn);
        panelConfig.add(cambiarContrasenaBtn);

        return panelConfig;
    }
    
    private JPanel crearPanelSeleccionCurso(boolean esRevisar) {
        // Crear el panel principal
        JPanel panelSeleccionCurso = new JPanel(new BorderLayout());
        panelSeleccionCurso.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Crear el panel superior con la selección de cursos
        JPanel cursoPanel = new JPanel(new FlowLayout());
        cursoPanel.add(new JLabel(esRevisar ? "Seleccione el curso a revisar:" : "Seleccione el curso a editar:"));
        
        // Crear una nueva instancia del ComboBox para cada panel
        cursoComboBox = new JComboBox<>(new String[]{"1001", "1002", "2121"}); // IDs de curso de ejemplo
        cursoPanel.add(cursoComboBox);

        // Crear el botón para confirmar la selección
        confirmarBtn = new JButton("Confirmar selección");


        // Crear el panel inferior con los botones
        JPanel botonesPanel = new JPanel(new FlowLayout());
        botonesPanel.add(confirmarBtn);

        // Agregar el panel de botones al layout principal
        panelSeleccionCurso.add(cursoPanel, BorderLayout.NORTH);
        panelSeleccionCurso.add(botonesPanel, BorderLayout.SOUTH);

        return panelSeleccionCurso; // Retornar el panel para integrarlo en la ventana principal
    }
    

    // Getters para los JMenuItems adicionales en MenuPrincipal
    
    public JMenuItem getConfigMenuItem() {
        return configMenuItem;
    }

    public JMenuItem getFichaMenuItem() {
        return fichaMenuItem;
    }

    public JMenuItem getRevisarMenuItem() {
        return revisarMenuItem;
    }

    public JMenuItem getModificarMenuItem() {
        return modificarMenuItem;
    }

    public JMenuItem getSalirMenuItem() {
        return salirMenuItem;
    }
    
    // Getters para acceder a los componentes

    public JButton getConfirmarBtn() {
        return confirmarBtn;
    }

    public JButton getVolverBtn() {
        return volverBtn;
    }
    
    public JButton getContrasenaBtn() {
        return cambiarContrasenaBtn;
    }

    public JComboBox<String> getCursoComboBox() {
        return cursoComboBox;
    }
    
    public JTextField getNombreField() {
        return nombreField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JButton getCambiarNombreBtn() {
        return cambiarNombreBtn;
    }

    public JButton getCambiarEmailBtn() {
        return cambiarEmailBtn;
    }

    // Método para mostrar un panel específico
    public void mostrarPanel(String panel) {
        cardLayout.show(panelContenedor, panel);
    }
}