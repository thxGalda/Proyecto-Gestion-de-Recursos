package vistas;

import paqueteMain.Usuario;
import paqueteMain.Estudiante;
import paqueteMain.Profesor;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class VentanaMenuPrincipal extends JPanel implements ActionListener {
    private MainApp mainApp;  // Referencia a la clase MainApp para controlar las vistas
    private CardLayout cardLayout;  // Usaremos CardLayout de MainApp
    private JPanel panelContenedor; // Panel principal con CardLayout

    // Menú
    private JMenuBar menuBar;
    private JMenu menuPrincipal;
    private JMenuItem configMenuItem, fichaMenuItem, revisarMenuItem, modificarMenuItem, salirMenuItem;
    private Usuario usuarioActual;
    private boolean revisarCurso;

    // Constructor que acepta la instancia de MainApp y el usuario
    public VentanaMenuPrincipal(MainApp mainApp, Usuario usuario) {
        this.mainApp = mainApp;  // Guardar referencia a MainApp
        this.usuarioActual = usuario;  // Guardar el usuario actual
        this.revisarCurso = true;
        initialize();  // Inicializar el contenido
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

        // Listeners
        configMenuItem.addActionListener(this);
        fichaMenuItem.addActionListener(this);
        revisarMenuItem.addActionListener(this);
        modificarMenuItem.addActionListener(this);
        salirMenuItem.addActionListener(this);

        // Añadir items al menú
        menuPrincipal.add(configMenuItem);
        menuPrincipal.add(fichaMenuItem);
        menuPrincipal.add(revisarMenuItem);

        // Solo añadir "Modificar Curso" si es un profesor
        if (usuarioActual instanceof Profesor) {
            menuPrincipal.add(modificarMenuItem);
        }

        menuPrincipal.addSeparator();
        menuPrincipal.add(salirMenuItem);
        menuBar.add(menuPrincipal);

        // Crear el layout y panel contenedor
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);  // Panel donde cambiarán las vistas

        // Crear los paneles de ficha personal y configuración
        JPanel panelFichaPersonal = crearPanelFichaPersonal();
        JPanel panelConfiguracion = crearPanelConfiguracion();
        // Crear dos instancias del panel de selección de curso
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

    private JPanel crearPanelFichaPersonal() {
        JPanel panelFicha = new JPanel(new GridLayout(3, 1)); // Un layout simple con 3 filas

        // Añadir la información del usuario
        JLabel nombreLabel = new JLabel("Nombre: " + usuarioActual.getNombre());
        JLabel emailLabel = new JLabel("Email: " + usuarioActual.getEmail());

        panelFicha.add(nombreLabel);
        panelFicha.add(emailLabel);

        if (usuarioActual instanceof Estudiante) {
            Estudiante estudiante = (Estudiante) usuarioActual;
            JLabel paraleloLabel = new JLabel("Paralelo: " + estudiante.getParalelo());
            panelFicha.add(paraleloLabel);
        } else if (usuarioActual instanceof Profesor) {
            Profesor profesor = (Profesor) usuarioActual;
            JLabel departamentoLabel = new JLabel("Departamento: " + profesor.getDepartamento());
            panelFicha.add(departamentoLabel);
        }
        

        return panelFicha; // Retornar el panel para añadirlo al CardLayout
    }

    private JPanel crearPanelConfiguracion() {
        JPanel panelConfig = new JPanel(new GridLayout(4, 2)); // Layout con 4 filas y 2 columnas

        JLabel nombreLabel = new JLabel("Nombre:");
        JTextField nombreField = new JTextField(usuarioActual.getNombre());

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(usuarioActual.getEmail());

        JButton cambiarNombreBtn = new JButton("Cambiar Nombre");
        cambiarNombreBtn.addActionListener(e -> {
            usuarioActual.setNombre(nombreField.getText());
            JOptionPane.showMessageDialog(null, "Nombre actualizado!");
        });

        JButton cambiarEmailBtn = new JButton("Cambiar Email");
        cambiarEmailBtn.addActionListener(e -> {
            usuarioActual.setEmail(emailField.getText());
            JOptionPane.showMessageDialog(null, "Email actualizado!");
        });

        // Añadir todos los componentes al panel
        panelConfig.add(nombreLabel);
        panelConfig.add(nombreField);
        panelConfig.add(emailLabel);
        panelConfig.add(emailField);
        panelConfig.add(cambiarNombreBtn);
        panelConfig.add(cambiarEmailBtn);

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
        JComboBox<String> cursoComboBox = new JComboBox<>(new String[]{"1001", "1002", "2121"}); // IDs de curso de ejemplo
        cursoPanel.add(cursoComboBox);

        // Agregar el panel al layout
        panelSeleccionCurso.add(cursoPanel, BorderLayout.NORTH);

        // Crear el botón para confirmar la selección
        JButton confirmarBtn = new JButton("Confirmar selección");
        confirmarBtn.addActionListener(e -> {
            String cursoSeleccionado = (String) cursoComboBox.getSelectedItem();
            System.out.println("Curso seleccionado: " + cursoSeleccionado); // Imprimir el valor seleccionado

            // Convertir el curso seleccionado a un ID (asumido como Integer)
            int idCurso = Integer.parseInt(cursoSeleccionado);

            // Determinar si se trata de revisar o modificar el curso
            if (esRevisar) {
                System.out.println("Paso: Seleccion Curso, llamada a metodo mostrar SubmenuCurso con id: " + idCurso);
                mainApp.mostrarVentanaSubmenuCurso(idCurso); // Mostrar la ventana de revisión
            } else {
                System.out.println("Paso: Seleccion Curso, llamada a metodo mostrar editarCurso con id: " + idCurso);
                mainApp.mostrarVentanaEditarCurso(idCurso); // Mostrar la ventana de edición
            }
        });

        // Crear el botón para volver al menú principal
        JButton volverBtn = new JButton("Volver al Menú Principal");
        volverBtn.addActionListener(e -> mainApp.mostrarVentana("MenuPrincipal"));

        // Crear el panel inferior con los botones
        JPanel botonesPanel = new JPanel(new FlowLayout());
        botonesPanel.add(confirmarBtn);
        botonesPanel.add(volverBtn);

        // Agregar el panel de botones al layout principal
        panelSeleccionCurso.add(botonesPanel, BorderLayout.SOUTH);

        return panelSeleccionCurso; // Retornar el panel para integrarlo en la ventana principal
    }
    
    

    // Método que gestiona las acciones del menú
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == configMenuItem) {
            // Mostrar la pantalla de configuración
            cardLayout.show(panelContenedor, "Configuración");
        } else if (e.getSource() == fichaMenuItem) {
            // Mostrar la ficha personal
            cardLayout.show(panelContenedor, "Ficha Personal");
        } else if (e.getSource() == revisarMenuItem) {
            // Marcar que se va a revisar un curso
            revisarCurso = true;  // Variable global para identificar la acción
            cardLayout.show(panelContenedor, "Revisar Curso");
        } else if (e.getSource() == modificarMenuItem && usuarioActual instanceof Profesor) {
            // Marcar que se va a modificar un curso
            revisarCurso = false;  // Variable global para identificar la acción
            cardLayout.show(panelContenedor, "Editar Curso");
        } else if (e.getSource() == salirMenuItem) {
            // Cerrar la aplicación
            System.exit(0);
        }
    }
}