// Ventana hub de todos los paneles y submenus

package vistas;
import paqueteMain.Usuario;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class VentanaMenu implements ActionListener{
	private JFrame frame;
    private CardLayout cardLayout;
    private JPanel panelContenedor; // Panel principal con CardLayout

    // Menú
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem loginMenuItem, saveMenuItem, exitMenuItem, cambiarContraseñaMenuItem;
    private Usuario usuarioActual;

	public VentanaMenu() {
		// Simulación de un usuario existente para pruebas
        usuarioActual = new Usuario("Ignacio Araya", "21273283-1"); 

        // Establecer una contraseña de prueba
        usuarioActual.setContrasena("1234");

		initialize();
		
	}
	public void initialize() {
		frame = new JFrame("Sistema de Gestión de Recursos Educativos");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Cambiar fuente para menu e item de menu
		Font f = new Font("Roboto", Font.PLAIN, 20);
		UIManager.put("Menu.font", f);
		UIManager.put("MenuItem.font", f);
		
		//--------------------------------------------------- MENU BARRA LATERAL
		
		menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        loginMenuItem = new JMenuItem("Login");
        saveMenuItem = new JMenuItem("Save");
        exitMenuItem = new JMenuItem("Exit");
        cambiarContraseñaMenuItem = new JMenuItem("Cambiar Contraseña"); 

        loginMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);
        cambiarContraseñaMenuItem.addActionListener(this); 

        fileMenu.add(loginMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(cambiarContraseñaMenuItem); 
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
        
        // Panel principal con CardLayout
        panelContenedor = new JPanel();
        cardLayout = new CardLayout();
        panelContenedor.setLayout(cardLayout);

        // Añadir submenús al CardLayout
        panelContenedor.add(new VentanaLogin(), "Login"); // Panel Login
        panelContenedor.add(new JPanel(), "Save"); // Panel vacío para "Save", se puede implementar después
        panelContenedor.add(new VentanaContrasena(usuarioActual.getContrasena()), "CambiarContraseña"); // Panel de cambio de contraseña

        // Añadir el panel principal al frame
        frame.add(panelContenedor);

        // Configurar posición y visibilidad
        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true);
	}
	//
	// Acciones
	//
	@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginMenuItem) {
            // Cambiar al submenú de login
            cardLayout.show(panelContenedor, "Login");
        } else if (e.getSource() == saveMenuItem) {
            // Cambiar al submenú de save
            cardLayout.show(panelContenedor, "Save");
        } else if (e.getSource() == exitMenuItem) {
            System.exit(0); // Salir de la aplicación
        } else if (e.getSource() == cambiarContraseñaMenuItem) {
            // Cambiar al submenú de cambiar contraseña
            cardLayout.show(panelContenedor, "CambiarContraseña");
        }
    }

	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> new VentanaMenu());
	    }

}

