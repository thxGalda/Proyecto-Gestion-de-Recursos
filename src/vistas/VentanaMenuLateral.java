package vistas;
import paqueteMain.Usuario;
import paqueteMain.Profesor;
import paqueteMain.Estudiante;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class VentanaMenuLateral extends JPanel implements ActionListener{
	 private MainApp mainApp;  // Referencia a la clase MainApp para controlar las vistas
	 private CardLayout cardLayout;
	 private JPanel panelContenedor; // Panel principal con CardLayout
	 private JPanel panelCentral; // Panel donde se cargarán los submenús
	 private Usuario usuarioActual;

	 // Menú lateral
	 private JMenuBar menuLateralBar;
	 private JMenu menuLateral;
	 private JMenuItem loginMenuItem, saveMenuItem, exitMenuItem, cambiarContraseñaMenuItem;

	 // Menú principal (central)
	 private JMenuBar menuCentralBar;
	 private JMenu menuPrincipal;
	 private JMenuItem configMenuItem, fichaMenuItem, revisarMenuItem, modificarMenuItem, salirMenuItem;

	 public VentanaMenuLateral(MainApp mainApp, Usuario usuarioActual) {
	        this.mainApp = mainApp;  // Guardar referencia a MainApp
	        this.usuarioActual = usuarioActual;  // Guardar el usuario actual
	        initialize();
	    }
	
	public void initialize() {
		// Cambiar fuente para menu e item de menu
		Font f = new Font("Roboto", Font.PLAIN, 20);
		UIManager.put("Menu.font", f);
		UIManager.put("MenuItem.font", f);
		
		//--------------------------------------------------- MENU BARRA LATERAL
		
		menuLateralBar = new JMenuBar();
        menuLateral = new JMenu("Navegación");

        loginMenuItem = new JMenuItem("Login");
        saveMenuItem = new JMenuItem("Guardar");
        cambiarContraseñaMenuItem = new JMenuItem("Cambiar Contraseña");
        exitMenuItem = new JMenuItem("Salir");

        loginMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        cambiarContraseñaMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);

        menuLateral.add(loginMenuItem);
        menuLateral.add(saveMenuItem);
        menuLateral.add(cambiarContraseñaMenuItem);
        menuLateral.addSeparator();
        menuLateral.add(exitMenuItem);
        menuLateralBar.add(menuLateral);

        // Panel principal (central)
        panelContenedor = new JPanel();
        cardLayout = new CardLayout();
        panelContenedor.setLayout(cardLayout);

        // Crear los paneles para los submenús del menú principal
        panelContenedor.add(new JLabel("Login (Por implementar)"), "Login");
        panelContenedor.add(new JLabel("Guardar (Por implementar)"), "Guardar");
        panelContenedor.add(new JLabel("Cambiar Contraseña (Por implementar)"), "Cambiar Contraseña");

        // Menú central
        menuCentralBar = new JMenuBar();
        menuPrincipal = new JMenu("Menú Principal");

        configMenuItem = new JMenuItem("Configuración");
        fichaMenuItem = new JMenuItem("Ficha Personal");
        revisarMenuItem = new JMenuItem("Revisar Cursos");
        modificarMenuItem = new JMenuItem("Modificar Curso");
        salirMenuItem = new JMenuItem("Salir");

        configMenuItem.addActionListener(this);
        fichaMenuItem.addActionListener(this);
        revisarMenuItem.addActionListener(this);
        modificarMenuItem.addActionListener(this);
        salirMenuItem.addActionListener(this);

        menuPrincipal.add(configMenuItem);
        menuPrincipal.add(fichaMenuItem);
        menuPrincipal.add(revisarMenuItem);

        // Solo añadir "Modificar Curso" si es un profesor
        if (usuarioActual instanceof Profesor) {
            menuPrincipal.add(modificarMenuItem);
        }

        menuPrincipal.addSeparator();
        menuPrincipal.add(salirMenuItem);
        menuCentralBar.add(menuPrincipal);
        
        // Añadir componentes al panel principal
        this.setLayout(new BorderLayout()); // Layout del panel
        this.add(menuLateralBar, BorderLayout.WEST); // Menú lateral en la izquierda
        this.add(panelContenedor, BorderLayout.CENTER); // Panel contenedor central
	}
	//
	// Acciones
	//
	@Override
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginMenuItem) {
            cardLayout.show(panelContenedor, "Login");
        } else if (e.getSource() == saveMenuItem) {
            cardLayout.show(panelContenedor, "Guardar");
        } else if (e.getSource() == cambiarContraseñaMenuItem) {
            mainApp.mostrarVentanaContrasena(usuarioActual);
        } else if (e.getSource() == configMenuItem) {
            cardLayout.show(panelContenedor, "Configuración");
        } else if (e.getSource() == fichaMenuItem) {
            cardLayout.show(panelContenedor, "Ficha Personal");
        } else if (e.getSource() == revisarMenuItem) {
            cardLayout.show(panelContenedor, "Revisar Cursos");
        } else if (e.getSource() == modificarMenuItem && usuarioActual instanceof Profesor) {
            cardLayout.show(panelContenedor, "Modificar Curso");
        } else if (e.getSource() == salirMenuItem || e.getSource() == exitMenuItem) {
            System.exit(0);
        }
    }

}

