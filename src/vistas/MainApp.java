// Clase hub de todos los paneles y submenus, maneja el flujo de las ventanas
package vistas;

import paqueteMain.*;

import javax.swing.*;
import java.awt.*;

public class MainApp {
	private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardsPanel; // Panel contenedor con CardLayout
    private Usuario usuarioActual; // Usuario actual

    // Método main que inicializa la aplicación
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            // Configurar el Look and Feel Nimbus
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    try {
                        UIManager.setLookAndFeel(info.getClassName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

            // Crear una instancia de MainApp y mostrar la ventana inicial
            MainApp app = new MainApp();  // Crear una instancia de MainApp
            app.initialize();  // Inicializar la aplicación
            app.mostrarVentana("Login");  // Mostrar ventana inicial (Login)
        });
    }
    // Método para inicializar la aplicación
    public void initialize() {
        frame = new JFrame("Sistema de Gestión de Recursos Virtuales");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        
        // Crear un usuario por defecto
        this.usuarioActual = new Usuario("Default", "00000000-0", "default@example.com", "default123");
        
        // Crear el menú lateral
        VentanaMenuLateral ventanaMenuLateral = new VentanaMenuLateral(this, usuarioActual);
        frame.add(ventanaMenuLateral, BorderLayout.WEST); // Anclar el menú lateral a la izquierda

        // Crear el panel central que cambiará entre las diferentes vistas
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        // Crear instancias de las ventanas
        VentanaLogin ventanaLogin = new VentanaLogin(this, usuarioActual);  // Pasar referencia de MainApp
      
        // Añadir las ventanas al panel principal
        cardsPanel.add(ventanaLogin, "Login");
        /* Ventanas a implementar
         * 
         * 
         */
        
        // Inicialmente, la ventana de cambio de contraseña será nula
        cardsPanel.add(new JPanel(), "CambioContraseña"); // Placeholder para VentanaContrasena

        // Añadir el panel contenedor al frame
        frame.add(cardsPanel);
        frame.setVisible(true);
    }
   
 //
 // LLAMADAS A VENTANAS
 //

 // Método para mostrar una ventana específica
    public void mostrarVentana(String ventana) {
        cardLayout.show(cardsPanel, ventana);  // Cambiar de ventana según el nombre proporcionado
    }

 // Método llamado cuando el login se completa y crear ventana menu principal
    public void loginCompleto(Usuario usuario) {
        // Asegúrate de que usuario no sea nulo aquí
    	if (usuarioActual != null) {

        	// Crear e instanciar el menú principal ahora que el usuario está disponible
            VentanaMenuPrincipal ventanaMenuPrincipal = new VentanaMenuPrincipal(this, usuario);
            cardsPanel.add(ventanaMenuPrincipal, "MenuPrincipal");
         // Mostrar la ventana del menú principal
            mostrarVentana("MenuPrincipal");
    	 } else {
             // Manejar el caso de usuario nulo
             JOptionPane.showMessageDialog(frame, "El usuario no está disponible.");
         }
        
    }
    // Método para mostrar la ventana de cambio de contraseña
    public void mostrarVentanaContrasena(Usuario usuarioActual) {
    	if (usuarioActual != null) {
            // Crear la ventana de contraseña solo si no se ha creado antes
            String contraseñaActual = usuarioActual.getContrasena(); // Supongamos que tienes un método para obtener la contraseña actual
            VentanaContrasena ventanaContrasena = new VentanaContrasena(contraseñaActual, usuarioActual, this);
            cardsPanel.add(ventanaContrasena, "CambioContraseña");

            // Mostrar la ventana de cambio de contraseña
            mostrarVentana("CambioContraseña");
        } else {
            // Manejar el caso de usuario nulo
            JOptionPane.showMessageDialog(frame, "El usuario no está disponible.");
        }
    }
    // Método para crear ventanas editar curso y revisar curso dinamicamente
    public void crearPanelCurso(int cursoId, String ventana) {
        // Dependiendo del curso seleccionado, crear el panel adecuado
    	Curso curso = new Curso("Curso", cursoId);  // Retorna un curso simulado por ahora;
    	String panelKey = ventana.equals("EditarCurso") ? "EditarCurso" + cursoId : "SubMenuCurso" + cursoId;


    	if (cursoId >= 0) {
            // Crear las ventanas asociadas al curso
    		if(ventana.equals("EditarCurso")) {
    			VentanaEditarCurso ventanaEditarCurso = new VentanaEditarCurso(this, curso);
    			ventanaEditarCurso.setName(panelKey); // Establece el nombre para identificarlo
    			cardsPanel.add(ventanaEditarCurso, panelKey);
    			System.out.println("Se creó la ventana EditarCurso "+ panelKey);
    			
    		} else if(ventana.equals("SubMenuCurso")) {
    			VentanaSubMenuCurso ventanaSubMenuCurso = new VentanaSubMenuCurso(this, curso);
    			ventanaSubMenuCurso.setName(panelKey); // Establece el nombre para identificarlo
    			cardsPanel.add(ventanaSubMenuCurso, panelKey);
    			System.out.println("Se creó la ventana SubMenuCurso "+ panelKey);
    		}
    		else
    			JOptionPane.showMessageDialog(frame, "Error al mostrar ventana");
    		
        } else {
            JOptionPane.showMessageDialog(frame, "El curso con ID " + cursoId + " no existe.");
        }
	
    }
    // Método para mostrar la ventana de edición o revisión del curso
 // Método para mostrar la ventana de edición del curso
    public void mostrarVentanaEditarCurso(int cursoId) {
    	 System.out.println("Intentando mostrar EditarCurso con ID: " + cursoId);
        // Verificar si el panel de edición de curso ya fue creado
        if (existePanel("EditarCurso" + cursoId)) {
            mostrarVentana("EditarCurso" + cursoId);
        } else {
            crearPanelCurso(cursoId, "EditarCurso");
            mostrarVentana("EditarCurso" + cursoId);
        }
    }
    // Método para mostrar el submenú del curso
    public void mostrarVentanaSubmenuCurso(int cursoId) {
    	 System.out.println("Intentando mostrar SubMenuCurso con ID: " + cursoId);
        // Verificar si el panel del submenú de curso ya fue creado
        if (existePanel("SubMenuCurso" + cursoId)) {
            mostrarVentana("SubMenuCurso" + cursoId);
        } else {
            crearPanelCurso(cursoId, "SubMenuCurso");
            mostrarVentana("SubMenuCurso" + cursoId);
        }
    }
    public void crearPanelCarpeta(int cursoId, int carpetaId) {
        // Crear la clave única para la ventana de la carpeta
    	Curso curso = new Curso("Curso", cursoId);  // Retorna un curso simulado por ahora;
    	// Crear carpetas si no existen
        if (curso.getCarpetas().isEmpty()) {
            curso.agregarCarpeta(new Carpeta("Carpeta 1", 1001));
            curso.agregarCarpeta(new Carpeta("Carpeta 2", 1002));
            curso.agregarCarpeta(new Carpeta("Carpeta 3", 2121));
        }
        // Crear una carpeta simulada por ahora (usar una ya existente del curso si es necesario)
        Carpeta carpeta = new Carpeta("Carpeta", carpetaId);
        String panelKey = "SubMenuCarpeta" + cursoId + "_" + carpetaId;
        
        // Verificar si ya se ha creado la ventana para esta carpeta
        if (cursoId >= 0 && carpetaId >= 0) {
            // Crear la ventana de carpeta asociada al curso
            VentanaSubMenuCarpeta ventanaSubMenuCarpeta = new VentanaSubMenuCarpeta(this, curso, carpeta);
            ventanaSubMenuCarpeta.setName(panelKey); // Establecer nombre único para la ventana
            cardsPanel.add(ventanaSubMenuCarpeta, panelKey);
            System.out.println("Se creó la ventana SubMenuCarpeta " + panelKey);
        } else {
            JOptionPane.showMessageDialog(frame, "El curso o la carpeta no existen.");
        }
    }
    public void mostrarVentanaSubmenuCarpeta(int cursoId, int carpetaId) {
        System.out.println("Intentando mostrar SubMenuCarpeta con ID Curso: " + cursoId + " y Carpeta ID: " + carpetaId);
        
        // Crear la clave única para la ventana de la carpeta
        String panelKey = "SubMenuCarpeta" + cursoId + "_" + carpetaId;
        
        // Verificar si el panel de la carpeta ya fue creado
        if (existePanel(panelKey)) {
            mostrarVentana(panelKey);
        } else {
            // Si no existe, creamos el panel y luego lo mostramos
            crearPanelCarpeta(cursoId, carpetaId);
            mostrarVentana(panelKey);
        }
    }
    
    public void mostrarVentanaBusqueda(Curso curso) {
    	 if (curso == null) {
    	        JOptionPane.showMessageDialog(frame, "El curso no es válido.");
    	        return;
    	    }
    	String panelName = "VentanaBusqueda" + curso.getId();
        // Verificar si el panel de búsqueda ya fue creado
        if (existePanel(panelName)) {
            mostrarVentana(panelName);
        } else { // Crear nuevo panel de busqueda
            VentanaBusqueda ventanaBusqueda = new VentanaBusqueda(this, curso);
            cardsPanel.add(ventanaBusqueda, panelName);
            mostrarVentana(panelName);
        }
    }
    
    // MÉTODOS EXTRAS
    
    // Verifica si un panel ya existe en el CardLayout
    private boolean existePanel(String nombrePanel) {
        for (Component comp : cardsPanel.getComponents()) {
            if (comp.getName() != null && comp.getName().equals(nombrePanel)) {
                return true;
            }
        }
        return false;
    }

    public JFrame getFrame() {
        return frame;  // Para acceder al frame desde VentanaMenuPrincipal
    }
    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

}