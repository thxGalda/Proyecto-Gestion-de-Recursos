package vistas;

import javax.swing.*;
import modelo.Usuario;
import controladores.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaContrasena extends JPanel {
    private JPasswordField contraseñaActualField;
    private JPasswordField nuevaContraseñaField;
    private JButton btnConfirmar;
    private MainController mainController;

    public VentanaContrasena(MainController mainController) {

        this.mainController = mainController; // Cambia MainApp por MainController
        initialize();
    }
    
    public void reiniciarVentana() {
        // Limpiar el panel
        removeAll();
        // Volver a inicializar la ventana
        initialize();
        
        revalidate();
        repaint();
 
    }

    public void initialize() {
        // Configurar el layout
        setLayout(new GridLayout(3, 2, 10, 10));

        if (mainController.debeMostrarContraseñaActual()) {
        	System.out.println("El usuario tiene contraseña establecida");
            add(new JLabel("Ingrese la contraseña actual:"));
            contraseñaActualField = new JPasswordField();
            add(contraseñaActualField);
        } else {
            // Si no tiene contraseña previa, inicializa el campo como vacío para evitar errores
            contraseñaActualField = null; // Se inicializa, pero no se añade al panel
        }

        // Mostrar campo para la nueva contraseña
        add(new JLabel("Ingrese la nueva contraseña:"));
        nuevaContraseñaField = new JPasswordField();
        add(nuevaContraseñaField);

        // Botón para confirmar el cambio de contraseña
        btnConfirmar = new JButton("Confirmar");
        add(btnConfirmar);
        
        btnConfirmar.addActionListener(e -> mainController.handleConfirm());
    }
    // Método para obtener el botón confirmar
    public JButton getBtnConfirmar() {
        return btnConfirmar;
    }

    // Métodos para obtener las contraseñas ingresadas
    public String getContraseñaActual() {
        return contraseñaActualField != null ? new String(contraseñaActualField.getPassword()) : "";
    }

    public String getNuevaContraseña() {
        return new String(nuevaContraseñaField.getPassword());
    }
    
    // Método para saber si se debe mostrar el campo de contraseña actual
    public boolean isContraseñaActualVisible() {
        return contraseñaActualField != null;
    }
	
}