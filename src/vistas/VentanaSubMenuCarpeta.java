package vistas;

import paqueteMain.Usuario;
import paqueteMain.Profesor;
import paqueteMain.Curso;
import java.util.List;
import paqueteMain.Carpeta;
import paqueteMain.Recurso;


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
        JPanel cargarRecursosPanel = crearPanelCargarRecurso();
        JPanel eliminarRecursosPanel = crearPanelEliminarRecurso();
        JPanel cambiarNombreCarpetaPanel = crearPanelCambiarNombreCarpeta();
        JPanel moverRecursoPanel = crearPanelMoverRecurso();

        // Añadir los paneles al CardLayout
        panelContenedor.add(mostrarRecursosPanel, "MostrarRecursos");
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
        JPanel panel = new JPanel(new BorderLayout()); // Utiliza BorderLayout para mejor control
        JLabel label = new JLabel("Mostrar Recursos - Aquí se mostrarán los recursos de la carpeta.");
        
        // Crear una lista de recursos vacía para el ejemplo (luego puedes llenarla con datos reales)
        String[] recursos = {}; // Este arreglo deberá ser dinámico y llenarse con los nombres de recursos.
        
        // Crear el JList que contendrá los nombres de los recursos
        JList<String> listaRecursos = new JList<>(recursos);
        
        // Añadir la lista dentro de un JScrollPane para que tenga scroll si hay muchos elementos
        JScrollPane scrollPane = new JScrollPane(listaRecursos);
        
        // Botón para regresar
        JButton btnBack = new JButton("Regresar al submenu de curso");
        btnBack.addActionListener(e -> mainApp.mostrarVentanaSubmenuCurso(curso.getId()));
        
        // Añadir los componentes al panel
        
        panel.add(label, BorderLayout.NORTH);  // Etiqueta en la parte superior
        panel.add(scrollPane, BorderLayout.CENTER);  // JScrollPane en el centro, ocupa el resto del espacio
        panel.add(btnBack, BorderLayout.SOUTH);
        
        
        return panel;
}

    private JPanel crearPanelCargarRecurso() {
    JPanel panelCargarRecurso = new JPanel(new GridLayout(4, 2, 10, 10)); // GridLayout ajustado
    panelCargarRecurso.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Etiqueta y campo para seleccionar la opción de cargar un solo recurso o una lista
    JLabel labelOpcion = new JLabel("Seleccione una opción:");
    String[] opciones = {"Un solo recurso", "Lista de recursos"};
    JComboBox<String> comboBoxOpcion = new JComboBox<>(opciones);

    // Botón para cargar recurso(s)
    JButton btnCargar = new JButton("Cargar Recurso(s)");
    JButton btnBack = new JButton("Regresar al submenu de curso");
    btnBack.addActionListener(e -> mainApp.mostrarVentanaSubmenuCurso(curso.getId()));

    // Agregar componentes al panel
    panelCargarRecurso.add(labelOpcion);
    panelCargarRecurso.add(comboBoxOpcion);
    panelCargarRecurso.add(btnCargar);
    panelCargarRecurso.add(btnBack);

    // Acción del botón para cargar recurso(s)
    btnCargar.addActionListener(e -> {
        int opcionSeleccionada = comboBoxOpcion.getSelectedIndex(); // 0 = Un solo recurso, 1 = Lista de recursos

        if (opcionSeleccionada == 0) { // Un solo recurso
            Recurso recurso = Recurso.crearRecurso(); // Método para crear un recurso
            if (recurso != null) {
                this.carpeta.agregarRecurso(recurso); // Cargar el recurso directamente en la carpeta
                JOptionPane.showMessageDialog(panelCargarRecurso, "Recurso cargado en la carpeta.");
            } else {
                JOptionPane.showMessageDialog(panelCargarRecurso, "Error al crear el recurso.");
            }
        } else { // Lista de recursos
            List<Recurso> listaRecursos = Carpeta.crearListaRecursos(); // Método para crear una lista de recursos
            if (listaRecursos != null && !listaRecursos.isEmpty()) {
                this.carpeta.agregarRecurso(listaRecursos); // Cargar la lista de recursos en la carpeta
                JOptionPane.showMessageDialog(panelCargarRecurso, "Lista de recursos cargada en la carpeta.");
            } else {
                JOptionPane.showMessageDialog(panelCargarRecurso, "Error al crear la lista de recursos.");
            }
        }
    });

    return panelCargarRecurso; // Retornar el panel para añadirlo al CardLayout
}
    private JPanel crearPanelEliminarRecurso() {
    JPanel panelEliminarRecurso = new JPanel(new GridLayout(3, 2, 10, 10)); // GridLayout para organizar los componentes
    panelEliminarRecurso.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Etiqueta y campo de texto para ingresar el título del recurso a eliminar
    JLabel labelTitulo = new JLabel("Ingrese el título del recurso a eliminar:");
    JTextField campoTitulo = new JTextField(20);

    // Botón para eliminar recurso
    JButton btnEliminar = new JButton("Eliminar Recurso");
    JButton btnBack = new JButton("Regresar al submenu de curso");
    btnBack.addActionListener(e -> mainApp.mostrarVentanaSubmenuCurso(curso.getId()));

    // Agregar los componentes al panel
    panelEliminarRecurso.add(labelTitulo);
    panelEliminarRecurso.add(campoTitulo);
    panelEliminarRecurso.add(btnEliminar);
    panelEliminarRecurso.add(btnBack);

    // Acción del botón para eliminar el recurso
    btnEliminar.addActionListener(e -> {
        String tituloEliminar = campoTitulo.getText();

        if (!tituloEliminar.isEmpty()) {
            this.carpeta.eliminarRecurso(tituloEliminar); // Método de Carpeta para eliminar recurso
          
        } else {
            JOptionPane.showMessageDialog(panelEliminarRecurso, "Por favor, ingrese un título válido.");
        }
    });

    return panelEliminarRecurso; // Retornar el panel para añadirlo al CardLayout
}

    private JPanel crearPanelCambiarNombreCarpeta() {
	    JPanel panelCambiarNombreCarpeta = new JPanel(new GridLayout(3, 2, 10, 10)); // Usamos GridLayout para organizar los componentes
	    panelCambiarNombreCarpeta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	
	    // Etiqueta y campo de texto para ingresar el nuevo nombre de la carpeta
	    JLabel labelNuevoNombre = new JLabel("Ingrese el nuevo nombre de la carpeta:");
	    JTextField campoNuevoNombre = new JTextField(20);
	
	    // Botón para confirmar el cambio de nombre
	    JButton btnCambiarNombre = new JButton("Cambiar Nombre");
	    JButton btnBack = new JButton("Regresar al submenu de curso");
        btnBack.addActionListener(e -> mainApp.mostrarVentanaSubmenuCurso(curso.getId()));
	
	    // Agregar los componentes al panel
	    panelCambiarNombreCarpeta.add(labelNuevoNombre);
	    panelCambiarNombreCarpeta.add(campoNuevoNombre);
	    panelCambiarNombreCarpeta.add(btnCambiarNombre);
	    panelCambiarNombreCarpeta.add(btnBack);
	
	    // Acción del botón para cambiar el nombre de la carpeta
	    btnCambiarNombre.addActionListener(e -> {
	        String nuevoTitulo = campoNuevoNombre.getText();
	
	        if (!nuevoTitulo.isEmpty()) {
	            this.carpeta.actualizarNombre(nuevoTitulo); // Método para cambiar el nombre de la carpeta
	            JOptionPane.showMessageDialog(panelCambiarNombreCarpeta, "Nombre de la carpeta actualizado a: " + nuevoTitulo);
	        } else {
	            JOptionPane.showMessageDialog(panelCambiarNombreCarpeta, "Por favor, ingrese un nombre válido.");
	        }
	    });
	
	    return panelCambiarNombreCarpeta; // Retornar el panel para añadirlo al CardLayout
}

    private JPanel crearPanelMoverRecurso() {
	    JPanel panelMoverRecurso = new JPanel(new GridLayout(4, 2, 10, 10)); // Usamos GridLayout para organizar los componentes
	    panelMoverRecurso.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	
	    // Etiqueta y campo de texto para ingresar el título del recurso
	    JLabel labelTituloRecurso = new JLabel("Ingrese el título del recurso a mover:");
	    JTextField campoTituloRecurso = new JTextField(20);
	
	    // Etiqueta y JComboBox para seleccionar la carpeta destino
	    JLabel labelCarpetaDestino = new JLabel("Seleccione la carpeta destino:");
	    JComboBox<String> comboCarpetas = new JComboBox<>();
	
	    // Accedemos a la lista de carpetas del curso y llenamos el JComboBox (excepto la carpeta actual)
	    List<Carpeta> carpetas = this.curso.getCarpetas(); // Obtenemos la lista de carpetas desde el curso
	    for (Carpeta carpeta : carpetas) {
	        if (!carpeta.equals(this.carpeta)) { // Excluir la carpeta seleccionada actual
	            comboCarpetas.addItem(carpeta.getId() + " - " + carpeta.getNombre());
	        }
	    }
	
	    // Botón para mover el recurso
	    JButton btnMoverRecurso = new JButton("Mover Recurso");
	    JButton btnBack = new JButton("Regresar al submenu de curso");
        btnBack.addActionListener(e -> mainApp.mostrarVentanaSubmenuCurso(curso.getId()));
        
	
	    // Agregar los componentes al panel
	    panelMoverRecurso.add(labelTituloRecurso);
	    panelMoverRecurso.add(campoTituloRecurso);
	    panelMoverRecurso.add(labelCarpetaDestino);
	    panelMoverRecurso.add(comboCarpetas);
	    panelMoverRecurso.add(btnMoverRecurso);
	    panelMoverRecurso.add(btnBack);
	    
	    
	
	    // Acción del botón para mover el recurso
	    btnMoverRecurso.addActionListener(e -> {
	        String tituloRecurso = campoTituloRecurso.getText();
	        int indexCarpetaDestino = comboCarpetas.getSelectedIndex();
	
	        if (!tituloRecurso.isEmpty() && indexCarpetaDestino >= 0) {
	            Carpeta carpetaDestino = carpetas.get(indexCarpetaDestino);
	
	            // Mover recurso a la carpeta seleccionada
	            boolean exito = this.carpeta.moverRecurso(tituloRecurso, carpetaDestino);
	            if (exito) {
	                JOptionPane.showMessageDialog(panelMoverRecurso, "Recurso '" + tituloRecurso + "' movido exitosamente a la carpeta: " + carpetaDestino.getNombre());
	            } else {
	                JOptionPane.showMessageDialog(panelMoverRecurso, "No se pudo mover el recurso. Verifique el título ingresado.");
	            }
	        } else {
	            JOptionPane.showMessageDialog(panelMoverRecurso, "Por favor, ingrese un título válido y seleccione una carpeta destino.");
	        }
	    });
	
	    return panelMoverRecurso;
	}

 
    
    //
    // Acciones
    //
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mostrarRecursosMenuItem) {
            cardLayout.show(panelContenedor, "MostrarRecursos");
        } else if (e.getSource() == buscarRecursosMenuItem) {
             mainApp.mostrarVentanaBusqueda(curso); // Llamar al método en MainApp para mostrar la ventana de búsqueda
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
        } else if (e.getSource() == salirMenuItem) {
            // Cerrar la aplicación
            System.exit(0);
        }
    }
}

