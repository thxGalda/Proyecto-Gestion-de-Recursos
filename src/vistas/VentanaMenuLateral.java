package vistas;

import java.awt.*;
import javax.swing.*;

import modelo.Estudiante;
import modelo.Profesor;
import modelo.Usuario;

import java.awt.event.*;

public class VentanaMenuLateral extends JPanel implements ActionListener {
	private MainApp mainApp;  // Referencia a la clase MainApp para controlar las vistas
    private Usuario usuarioActual;

    // Menú lateral
    private JMenuBar menuLateralBar;
    private JMenu menuLateral;
    private JMenuItem loginMenuItem, cambiarContraseñaMenuItem, exitMenuItem;

    // Menú principal
    private JMenu menuPrincipal;
    private JMenuItem configMenuItem, modificarCursoMenuItem, revisarCursoMenuItem;

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

        // Crear elementos del menú lateral
        loginMenuItem = new JMenuItem("Login");
        cambiarContraseñaMenuItem = new JMenuItem("Cambiar Contraseña");
        exitMenuItem = new JMenuItem("Salir");

        loginMenuItem.addActionListener(this);
        cambiarContraseñaMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);

        // Agregar items al menú lateral
        menuLateral.add(loginMenuItem);
        menuLateral.add(cambiarContraseñaMenuItem);
        menuLateral.addSeparator();
        menuLateral.add(exitMenuItem);
        

        // Crear el menú principal con un submenú
        menuPrincipal = new JMenu("Menú Principal");

        configMenuItem = new JMenuItem("Configuración");
        modificarCursoMenuItem = new JMenuItem("Modificar Curso");
        revisarCursoMenuItem = new  JMenuItem(" Revisar Curso");

        configMenuItem.addActionListener(this);
        modificarCursoMenuItem.addActionListener(this);
        revisarCursoMenuItem.addActionListener(this);
        
        menuPrincipal.add(configMenuItem);

        // Añadir un submenú revisar curso a "Menú Principal"
        JMenu subMenuCurso = new JMenu("Revisar Curso");
       
        
        
     // Añadir un submenú editar curso a "Menú Principal"
        JMenu subMenuEditar = new JMenu("Revisar Curso");
        // Solo añadir "Modificar Curso" si es un profesor
        if (usuarioActual instanceof Profesor) {
            subMenuEditar.add(modificarCursoMenuItem);
            menuPrincipal.add(subMenuEditar);
        }

        menuPrincipal.add(subMenuCurso);
        menuPrincipal.addSeparator();
        
        menuLateral.add(menuPrincipal);
        
        

        // Agregar submenus a la barra lateral
        menuLateralBar.add(menuLateral);

        // Establecer el layout del panel
        this.setLayout(new BorderLayout());
        this.add(menuLateralBar, BorderLayout.WEST); // Menú lateral en la izquierda
    }

    //
    // Acciones
    //
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginMenuItem) {
            mainApp.mostrarVentana("Login");
        } else if (e.getSource() == cambiarContraseñaMenuItem) {
            mainApp.mostrarVentanaContrasena(usuarioActual);
        } else if (e.getSource() == configMenuItem) {
            mainApp.mostrarVentana("Configuración");
        } else if (e.getSource() == modificarCursoMenuItem && usuarioActual instanceof Profesor) {
            //mainApp.mostrarVentanaEditarCurso()
        } else if (e.getSource() == exitMenuItem) {
            System.exit(0);
        }
    }
}
