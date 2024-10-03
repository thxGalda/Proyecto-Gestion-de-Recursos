package vistas;

import paqueteMain.Usuario;
import paqueteMain.Profesor;
import paqueteMain.Estudiante;
import paqueteMain.Curso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaBusqueda extends JPanel implements ActionListener{
    private CardLayout cardLayout;
    private JPanel panelContenedor; // Panel principal con CardLayout
    private JMenuBar menuBar;
    private JMenu menuPrincipal;
    private JMenuItem salirMenuItem, regresarMenuItem;

    private MainApp mainApp;
    private Curso curso;
    
    
    // Constructor
    public VentanaBusqueda(MainApp mainApp, Curso curso) {
        this.mainApp = mainApp;  // Guardar referencia a MainApp
        this.curso = curso;
        setName("VentanaBusqueda" + curso.getId());
        initialize();  // Inicializar el contenido
    }
    
    public void initialize() {
    	// Crear el menú barra
    	setLayout(new BorderLayout());
        menuBar = new JMenuBar();
        menuPrincipal = new JMenu("Búsqueda");
        
        // Crear JMenuItems para cada opción
        
        regresarMenuItem = new JMenuItem("Regresar");
        salirMenuItem = new JMenuItem("Salir");

        // Listeners del menú
        regresarMenuItem.addActionListener(this);
        salirMenuItem.addActionListener(this);

        // Añadir los items al menú

        menuPrincipal.addSeparator();
        menuPrincipal.add(regresarMenuItem);
        menuPrincipal.add(salirMenuItem);
        menuBar.add(menuPrincipal);

        // Crear el layout y panel contenedor
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);  // Panel donde cambiarán las vistas

        // Crear y añadir los paneles
        panelContenedor.add(crearPanelSeleccion(), "Seleccion");
        panelContenedor.add(crearPanelBuscarEstudiante(), "BuscarEstudiante");
        panelContenedor.add(crearPanelBuscarRecurso(), "BuscarRecurso");

        // Configurar el layout principal
        
        this.add(menuBar, BorderLayout.NORTH);
        this.add(panelContenedor, BorderLayout.CENTER);
    }
    
    
    //
    // PANELES
    //
    private JPanel crearPanelSeleccion() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Seleccione el tipo de búsqueda:", JLabel.CENTER);

        JButton btnBuscarEstudiante = new JButton("Buscar Estudiante");
        btnBuscarEstudiante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarPanel("BuscarEstudiante");
            }
        });

        JButton btnBuscarRecurso = new JButton("Buscar Recurso");
        btnBuscarRecurso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarPanel("BuscarRecurso");
            }
        });

        JPanel botonesPanel = new JPanel();
        botonesPanel.add(btnBuscarEstudiante);
        botonesPanel.add(btnBuscarRecurso);

        panel.add(label, BorderLayout.NORTH);
        panel.add(botonesPanel, BorderLayout.CENTER);

        return panel;
    }
    
    private JPanel crearPanelBuscarEstudiante() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Buscar Estudiante (por nombre o ID):", JLabel.CENTER);
        JTextField campoBusqueda = new JTextField(20);
        JTextArea areaResultado = new JTextArea(5, 30);
        areaResultado.setEditable(false);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String entrada = campoBusqueda.getText();
                Estudiante estudiante = null;

                // Intentar convertir a número para ID
                try {
                    int idEstudiante = Integer.parseInt(entrada);
                    estudiante = curso.buscarEstudiante(idEstudiante); // Buscar por ID
                } catch (NumberFormatException ex) {
                    estudiante = curso.buscarEstudiante(entrada); // Buscar por nombre
                }

                // Mostrar el resultado de la búsqueda
                if (estudiante != null) {
                    areaResultado.setText("Estudiante encontrado: " + estudiante.toString());
                } else {
                    areaResultado.setText("Estudiante no encontrado.");
                }
            }
        });

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarPanel("Seleccion");
            }
        });

        JPanel panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("Nombre o ID:"));
        panelBusqueda.add(campoBusqueda);
        panelBusqueda.add(btnBuscar);

        panel.add(label, BorderLayout.NORTH);
        panel.add(panelBusqueda, BorderLayout.CENTER);
        panel.add(new JScrollPane(areaResultado), BorderLayout.SOUTH);
        panel.add(btnVolver, BorderLayout.PAGE_END);

        return panel;
    }
    
    private JPanel crearPanelBuscarRecurso() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Buscar Recurso:", JLabel.CENTER);

        String[] opcionesBusqueda = {"Por fecha", "Por categoría", "Por autor", "Por nombre"};
        JComboBox<String> comboOpciones = new JComboBox<>(opcionesBusqueda);

        JTextField campoCriterio = new JTextField(20);
        JTextArea areaResultado = new JTextArea(5, 30);
        areaResultado.setEditable(false);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int opcionSeleccionada = comboOpciones.getSelectedIndex() + 1;
                String criterioBusqueda = campoCriterio.getText();

                curso.buscarRecursos(opcionSeleccionada, criterioBusqueda);
                areaResultado.setText("Resultados de búsqueda para la opción: " + opcionesBusqueda[opcionSeleccionada - 1] + "\n");
            }
        });

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarPanel("Seleccion");
            }
        });

        JPanel panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("Criterio de Búsqueda:"));
        panelBusqueda.add(comboOpciones);
        panelBusqueda.add(campoCriterio);
        panelBusqueda.add(btnBuscar);

        panel.add(label, BorderLayout.NORTH);
        panel.add(panelBusqueda, BorderLayout.CENTER);
        panel.add(new JScrollPane(areaResultado), BorderLayout.SOUTH);
        panel.add(btnVolver, BorderLayout.PAGE_END);

        return panel;
    }
    //
    // Acciones
    //
    @Override
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == regresarMenuItem) {
            mainApp.mostrarVentanaSubmenuCurso(this.curso.getId()); // Método en MainApp para volver al submenu curso
        } else if (e.getSource() == salirMenuItem) {
            System.exit(0);
        }
    }
    // Cambiar de panel
    private void mostrarPanel(String nombrePanel) {
        cardLayout.show(panelContenedor, nombrePanel);
    }
}
