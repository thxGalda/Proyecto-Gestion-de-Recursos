package vistas;

import javax.swing.*;
import controladores.*;

import modelo.Curso;
import modelo.Carpeta;
import modelo.Estudiante;
import modelo.Profesor;
import modelo.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaBusqueda extends JPanel{
    private CardLayout cardLayout;
    private JPanel panelContenedor; // Panel principal con CardLayout
    private JMenuBar menuBar;
    private JMenu menuPrincipal;
    private JMenuItem salirMenuItem, regresarMenuItem, buscarRecursoMenuItem, buscarEstudianteMenuItem;
    private JComboBox<String> comboOpciones;
    private JTextField campoCriterio, campoBusqueda;
    private JTextArea areaResultado;
    private JButton btnBuscarEstudiante, btnBuscarRecurso, btnCambiarBusqueda;

    private CursoController controladorCurso;
    private CarpetaController controladorCarpeta;
    private Curso curso;
    private boolean busquedaEstudiante;
    private String panelActivo;
    
    // Constructor
    public VentanaBusqueda(Curso curso, CursoController controladorCurso) {
        this.curso = curso;
        this.controladorCurso = controladorCurso; // Guardar el controlador de curso
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
    
    private JPanel crearPanelBuscarRecurso() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Buscar Recurso:", JLabel.CENTER);

        String[] opcionesBusqueda = {"Por fecha", "Por categoría", "Por autor", "Por nombre"};
        comboOpciones = new JComboBox<>(opcionesBusqueda);

        campoCriterio = new JTextField(20);
        areaResultado = new JTextArea(5, 30);
        areaResultado.setEditable(false);

        btnBuscarRecurso = new JButton("Buscar Recurso");
        // Botón para cambiar a búsqueda de estudiantes
        btnCambiarBusqueda = new JButton("Cambiar a Buscar Estudiante");
        btnCambiarBusqueda.setPreferredSize(new Dimension(200, 30));

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
        panelBusqueda.add(btnBuscarRecurso, gbc);

        // Añadir el área de resultados
        gbc.gridy = 3;
        panelBusqueda.add(new JScrollPane(areaResultado), gbc);

        panel.add(label, BorderLayout.NORTH);
        panel.add(panelBusqueda, BorderLayout.CENTER);
        panel.add(btnCambiarBusqueda, BorderLayout.SOUTH);

        return panel;
    }
    
    private JPanel crearPanelBuscarEstudiante() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Buscar Estudiante (por nombre o por ID):", JLabel.CENTER);

        campoCriterio = new JTextField(20);
        areaResultado = new JTextArea(5, 30);
        areaResultado.setEditable(false);

        btnBuscarEstudiante = new JButton("Buscar Estudiante");
        btnCambiarBusqueda = new JButton("Cambiar a Buscar Recurso");

        JPanel panelBusqueda = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Añadir el campo de criterio de búsqueda
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelBusqueda.add(new JLabel("Criterio de Búsqueda:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelBusqueda.add(campoCriterio, gbc);

        // Añadir el botón de búsqueda
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Ocupa toda la fila
        panelBusqueda.add(btnBuscarEstudiante, gbc);

        // Añadir el área de resultados
        gbc.gridy = 2;
        panelBusqueda.add(new JScrollPane(areaResultado), gbc);

        panel.add(label, BorderLayout.NORTH);
        panel.add(panelBusqueda, BorderLayout.CENTER);
        panel.add(btnCambiarBusqueda, BorderLayout.SOUTH);

        return panel;
    }
    
   
    // Getters para los componentes que necesitarán listeners desde el controlador

    public JMenuItem getSalirMenuItem() {
        return salirMenuItem;
    }

    public JMenuItem getRegresarMenuItem() {
        return regresarMenuItem;
    }

    public JMenuItem getBuscarEstudianteMenuItem() {
        return buscarEstudianteMenuItem;
    }

    public JMenuItem getBuscarRecursoMenuItem() {
        return buscarRecursoMenuItem;
    }

    public JComboBox<String> getComboOpciones() {
        return comboOpciones;
    }

    public JTextField getCampoCriterio() {
        return campoCriterio;
    }

    public JTextField getCampoBusqueda() {
        return campoBusqueda;
    }

    public JTextArea getAreaResultado() {
        return areaResultado;
    }

    public JButton getBtnBuscarEstudiante() {
        return btnBuscarEstudiante;
    }

    public JButton getBtnBuscarRecurso() {
        return btnBuscarRecurso;
    }

    public JButton getBtnCambiarBusqueda() {
        return btnCambiarBusqueda;
    }

    public String getPanelActivo() {
        // Asume que tienes un sistema para rastrear el panel visible, por ejemplo, con una variable activa
        return panelActivo;  // Variable que almacena el nombre del panel actualmente visible
    }
    // Cambiar de panel
    public void mostrarPanel(String nombrePanel) {
        CardLayout cl = (CardLayout) this.getLayout();
        cl.show(this, nombrePanel);
        panelActivo = nombrePanel;  // Actualiza el panel activo
    }
    public void setCarpetaController(CarpetaController carpetaController) {
        this.controladorCarpeta = carpetaController;
    }
}
