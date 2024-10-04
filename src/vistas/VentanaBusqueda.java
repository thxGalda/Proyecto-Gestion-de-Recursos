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
    private JMenuItem salirMenuItem, regresarMenuItem, buscarRecursoMenuItem, buscarEstudianteMenuItem;

    private MainApp mainApp;
    private Curso curso;
    private boolean busquedaEstudiante;
    
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
        buscarEstudianteMenuItem = new JMenuItem("Buscar Estudiante");
        buscarRecursoMenuItem = new JMenuItem("Buscar Recurso");
        
        // Listeners del menú
        regresarMenuItem.addActionListener(this);
        salirMenuItem.addActionListener(this);

        // Listeners para cambiar el tipo de búsqueda
        // Listeners para cambiar entre tipos de búsqueda
        buscarEstudianteMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cambiarTipoBusqueda(true);  // Cambia a búsqueda de estudiante
            }
        });

        buscarRecursoMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cambiarTipoBusqueda(false);  // Cambia a búsqueda de recurso
            }
        });

        
        // Añadir las opciones al menú
        menuPrincipal.add(buscarEstudianteMenuItem);
        menuPrincipal.add(buscarRecursoMenuItem);
        menuPrincipal.addSeparator();
        menuPrincipal.add(regresarMenuItem);
        menuPrincipal.add(salirMenuItem);
        menuBar.add(menuPrincipal);

        // Crear el layout y panel contenedor
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);  // Panel donde cambiarán las vistas
        
        
   

        // Crear y añadir los dos paneles (siempre presentes)
        panelContenedor.add(crearPanelBuscarEstudiante(), "BuscarEstudiante");
        panelContenedor.add(crearPanelBuscarRecurso(), "BuscarRecurso");


        // Configurar el layout principal
        this.add(menuBar, BorderLayout.NORTH);
        this.add(panelContenedor, BorderLayout.CENTER);

        // Mostrar por defecto el panel de buscar recurso (puedes cambiar esto si prefieres mostrar el de estudiantes primero)
        mostrarPanel("BuscarRecurso");
        
    }
    
    
    //
    // PANELES
    //
    
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

                try {
                    int idEstudiante = Integer.parseInt(entrada);
                    estudiante = curso.buscarEstudiante(idEstudiante); // Buscar por ID
                } catch (NumberFormatException ex) {
                    estudiante = curso.buscarEstudiante(entrada); // Buscar por nombre
                }

                if (estudiante != null) {
                    areaResultado.setText("Estudiante encontrado: " + estudiante.toString());
                } else {
                    areaResultado.setText("Estudiante no encontrado.");
                }
            }
        });

        JButton btnCambiarBusqueda = new JButton("Cambiar a Buscar Recurso");
        btnCambiarBusqueda.setPreferredSize(new Dimension(200, 30));
        btnCambiarBusqueda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cambiarTipoBusqueda(false); // Cambia a búsqueda de recurso
            }
        });

        // Usar GridBagLayout para mayor control
        JPanel panelBusqueda = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelBusqueda.add(new JLabel("Nombre o ID:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelBusqueda.add(campoBusqueda, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Ocupa toda la fila
        panelBusqueda.add(btnBuscar, gbc);

        // Añadir el área de resultados al panel de búsqueda
        gbc.gridy = 2; // Cambia la fila para colocar el área de resultados
        gbc.gridwidth = 2; // Ocupa toda la fila
        panelBusqueda.add(new JScrollPane(areaResultado), gbc); // Agregar el área de resultados


        panel.add(label, BorderLayout.NORTH);
        panel.add(btnCambiarBusqueda, BorderLayout.BEFORE_FIRST_LINE);
        panel.add(panelBusqueda, BorderLayout.CENTER);
        
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

        // Botón para cambiar a búsqueda de estudiantes
        JButton btnCambiarBusqueda = new JButton("Cambiar a Buscar Estudiante");
        btnCambiarBusqueda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cambiarTipoBusqueda(true);  // Cambia a búsqueda de estudiante
            }
        });
        btnCambiarBusqueda.setPreferredSize(new Dimension(200, 30));  // Asegurar tamaño adecuado


     // Usar GridBagLayout para ordenar los componentes
        JPanel panelBusqueda = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Añadir la etiqueta "Criterio de Búsqueda"
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panelBusqueda.add(new JLabel("Criterio de Búsqueda:"), gbc);

        // Añadir el combo de opciones
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelBusqueda.add(comboOpciones, gbc);

        // Añadir el campo de criterio de búsqueda
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelBusqueda.add(new JLabel("Buscar por:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelBusqueda.add(campoCriterio, gbc);

        // Añadir el botón de búsqueda
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Ocupa toda la fila
        panelBusqueda.add(btnBuscar, gbc);
        
        // Añadir el área de resultados al panel de búsqueda
        gbc.gridy = 2; // Cambia la fila para colocar el área de resultados
        gbc.gridwidth = 2; // Ocupa toda la fila
        panelBusqueda.add(new JScrollPane(areaResultado), gbc); // Agregar el área de resultados
        
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(btnCambiarBusqueda, BorderLayout.BEFORE_FIRST_LINE);
        panel.add(panelBusqueda, BorderLayout.CENTER);
        

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
    public void cambiarTipoBusqueda(boolean tipoBusqueda) {
        this.busquedaEstudiante = tipoBusqueda;
        
        // Cambiar el panel que se muestra según el nuevo valor de `busquedaEstudiante`
        if (busquedaEstudiante) {
            mostrarPanel("BuscarEstudiante");
        } else {
            mostrarPanel("BuscarRecurso");
        }
    }
    // Cambiar de panel
    private void mostrarPanel(String nombrePanel) {
        cardLayout.show(panelContenedor, nombrePanel);
    }
}
