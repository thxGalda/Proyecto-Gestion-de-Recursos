// Clase hub de todos los paneles y submenus, maneja el flujo de las ventanas
package vistas;

import paqueteMain.Usuario;
import paqueteMain.Profesor;
import paqueteMain.Estudiante;
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
        VentanaEditarCurso ventanaEditarCurso = new VentanaEditarCurso(this); // pasar referencia de MainApp
        
        cardsPanel.add(ventanaEditarCurso, "EditarCurso");
        
        // Añadir las ventanas al panel principal
        cardsPanel.add(ventanaLogin, "Login");
        
        // Inicialmente, la ventana de cambio de contraseña será nula
        cardsPanel.add(new JPanel(), "CambioContraseña"); // Placeholder para VentanaContrasena

        // Añadir el panel contenedor al frame
        frame.add(cardsPanel);
        frame.setVisible(true);
    }

 // Método para mostrar una ventana específica
    public void mostrarVentana(String ventana) {
        cardLayout.show(cardsPanel, ventana);  // Cambiar de ventana según el nombre proporcionado
    }

 // Método llamado cuando el login se completa
    public void loginCompleto(Usuario usuario) {
        // Asegúrate de que usuario no sea nulo aquí
    	if (usuarioActual != null) {

        	// Crear e instanciar el menú principal ahora que el usuario está disponible
            VentanaMenuPrincipal ventanaMenuPrincipal = new VentanaMenuPrincipal(this, usuario);
            cardsPanel.add(ventanaMenuPrincipal, "MenuPrincipal");
         // Mostrar la ventana del menú principal
            mostrarMenuPrincipal();
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
    
    // Método para mostrar la ventana de edición de curso
    public void mostrarMenuEditarCurso() {
        mostrarVentana("EditarCurso");
    }
    // Método par mostrar menu principal
    public void mostrarMenuPrincipal() {
    	mostrarVentana("MenuPrincipal");
    }
    
    public JFrame getFrame() {
        return frame;  // Para acceder al frame desde VentanaMenuPrincipal
    }
    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

}