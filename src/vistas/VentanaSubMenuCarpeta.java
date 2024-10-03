package vistas;

import paqueteMain.Usuario;
import paqueteMain.Profesor;
import paqueteMain.Curso;
import java.util.List;
import paqueteMain.Carpeta;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaSubMenuCarpeta extends JPanel implements ActionListener{
    private CardLayout cardLayout;
    private JPanel panelContenedor; // Panel principal con CardLayout
    
    // Menú
    private JMenuBar menuBar;
    private JMenu menuRevisarCurso;
    private JMenuItem regresarMenuItem, salirMenuItem;
    private JMenuItem cargarRecursosMenuItem, eliminarRecursosMenuItem, mostrarRecursosMenuItem, buscarRecursosMenuItem, cambiarNombreCarpetaMenuItem, moverRecursoMenuItem;

    private MainApp mainApp;
    private Curso curso;
    private Carpeta carpeta;
    
    // Constructor
    public VentanaSubMenuCarpeta(MainApp mainApp, Curso curso, Carpeta carpeta) {
        this.mainApp = mainApp;  // Guardar referencia a MainApp
        this.curso = curso;
        this.carpeta = carpeta;
        initialize();  // Inicializar el contenido
    }

    private void initialize() {
    	// Menú Barra
        setLayout(new BorderLayout());

        JLabel cursoNombreLabel = new JLabel("Submenú de Carpeta: " + carpeta.getNombre() + " - " + carpeta.getId());
        this.add(cursoNombreLabel);
        menuBar = new JMenuBar();
        menuRevisarCurso = new JMenu("Submenú Carpeta: " + carpeta.getNombre() + " - " + carpeta.getId());

        // Crear JMenuItems para cada opción
        mostrarRecursosMenuItem = new JMenuItem("Mostrar Recursos");
        buscarRecursosMenuItem = new JMenuItem("Buscar Recursos");

        // Opciones para profesores
        cambiarNombreCarpetaMenuItem = new JMenuItem("Cambiar Nombre Carpeta");
        cargarRecursosMenuItem = new JMenuItem("Cargar Recursos");
        eliminarRecursosMenuItem = new JMenuItem("Eliminar Recurso");
        moverRecursoMenuItem = new JMenuItem("Mover Recurso");

        regresarMenuItem = new JMenuItem("Regresar");
        salirMenuItem = new JMenuItem("Salir");

        // Listeners
        mostrarRecursosMenuItem.addActionListener(this);
        buscarRecursosMenuItem.addActionListener(this);
        cambiarNombreCarpetaMenuItem.addActionListener(this);
        cargarRecursosMenuItem.addActionListener(this);
        eliminarRecursosMenuItem.addActionListener(this);
        moverRecursoMenuItem.addActionListener(this);
        regresarMenuItem.addActionListener(this);
        salirMenuItem.addActionListener(e -> System.exit(0));

        // Añadir items al menú
        menuRevisarCurso.add(mostrarRecursosMenuItem);
        menuRevisarCurso.add(buscarRecursosMenuItem);
        menuRevisarCurso.addSeparator();

        // Opciones solo visibles para profesores
        if (mainApp.getUsuarioActual() instanceof Profesor) {
            menuRevisarCurso.add(cambiarNombreCarpetaMenuItem);
            menuRevisarCurso.add(cargarRecursosMenuItem);
            menuRevisarCurso.add(eliminarRecursosMenuItem);
            menuRevisarCurso.add(moverRecursoMenuItem);
        }

        menuRevisarCurso.addSeparator();
        menuRevisarCurso.add(regresarMenuItem);
        menuRevisarCurso.add(salirMenuItem);

        menuBar.add(menuRevisarCurso);
        this.add(menuBar, BorderLayout.BEFORE_FIRST_LINE);

        // Crear el layout y panel contenedor
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        // Crear paneles para cada opción
        JPanel mostrarRecursosPanel = crearPanelMostrarRecursos();
        JPanel buscarRecursosPanel = crearPanelBuscarRecursos();
        JPanel cargarRecursosPanel = crearPanelCargarRecursos();
        JPanel eliminarRecursosPanel = crearPanelEliminarRecursos();
        JPanel cambiarNombreCarpetaPanel = crearPanelCambiarNombreCarpeta();
        JPanel moverRecursoPanel = crearPanelMoverRecurso();

        // Añadir los paneles al CardLayout
        panelContenedor.add(mostrarRecursosPanel, "MostrarRecursos");
        panelContenedor.add(buscarRecursosPanel, "BuscarRecursos");
        panelContenedor.add(cambiarNombreCarpetaPanel, "CambiarNombreCarpeta");
        panelContenedor.add(cargarRecursosPanel, "CargarRecursos");
        panelContenedor.add(eliminarRecursosPanel, "EliminarRecurso");
        panelContenedor.add(moverRecursoPanel, "MoverRecurso");

        // Añadir el panel de contenido al panel principal
        this.add(panelContenedor, BorderLayout.CENTER);
    }
    
    //
    // PANELES
    //
    
    private JPanel crearPanelMostrarRecursos() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Mostrar Recursos - Aquí se mostrarán los recursos de la carpeta.");
        panel.add(label);
        return panel;
    }

    private JPanel crearPanelBuscarRecursos() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Buscar Recursos - Aquí se podrá buscar recursos en la carpeta.");
        panel.add(label);
        return panel;
    }

    private JPanel crearPanelCargarRecursos() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Cargar Recursos - Aquí se podrán cargar nuevos recursos en la carpeta.");
        panel.add(label);
        return panel;
    }

    private JPanel crearPanelEliminarRecursos() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Eliminar Recurso - Aquí se podrán eliminar recursos de la carpeta.");
        panel.add(label);
        return panel;
    }

    private JPanel crearPanelCambiarNombreCarpeta() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Cambiar Nombre Carpeta - Aquí se podrá cambiar el nombre de la carpeta.");
        panel.add(label);
        return panel;
    }

    private JPanel crearPanelMoverRecurso() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Mover Recurso - Aquí se podrán mover recursos entre carpetas.");
        panel.add(label);
        return panel;
    }
 
    
    //
    // Acciones
    //
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mostrarRecursosMenuItem) {
            cardLayout.show(panelContenedor, "MostrarRecursos");
        } else if (e.getSource() == buscarRecursosMenuItem) {
            cardLayout.show(panelContenedor, "BuscarRecursos");
        } else if (e.getSource() == cambiarNombreCarpetaMenuItem) {
            cardLayout.show(panelContenedor, "CambiarNombreCarpeta");
        } else if (e.getSource() == cargarRecursosMenuItem) {
            cardLayout.show(panelContenedor, "CargarRecursos");
        } else if (e.getSource() == eliminarRecursosMenuItem) {
            cardLayout.show(panelContenedor, "EliminarRecurso");
        } else if (e.getSource() == moverRecursoMenuItem) {
            cardLayout.show(panelContenedor, "MoverRecurso");
        } else if (e.getSource() == regresarMenuItem) {
            mainApp.mostrarVentanaSubmenuCurso(this.curso.getId()); // Volver al submenú del curso
        }
    }
}

