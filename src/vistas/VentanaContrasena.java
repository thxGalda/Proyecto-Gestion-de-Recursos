package vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaContrasena extends JPanel implements ActionListener {
    private JPasswordField contraseñaActualField;
    private JPasswordField nuevaContraseñaField;
    private JButton btnConfirmar;

    private String contraseñaActual; 
    private boolean tieneContraseñaEstablecida;  // Indica si el usuario ya tiene una contraseña establecida

    public VentanaContrasena(String contraseñaActual) {
        this.contraseñaActual = contraseñaActual;
        this.tieneContraseñaEstablecida = (contraseñaActual != null);

        // Configurar el layout
        setLayout(new GridLayout(3, 2, 10, 10));

        // Si ya tiene contraseña establecida, mostrar campo para la contraseña actual
        if (tieneContraseñaEstablecida) {
            add(new JLabel("Ingrese la contraseña actual:"));
            contraseñaActualField = new JPasswordField();
            add(contraseñaActualField);
        }

        // Mostrar campo para la nueva contraseña
        add(new JLabel("Ingrese la nueva contraseña:"));
        nuevaContraseñaField = new JPasswordField();
        add(nuevaContraseñaField);

        // Botón para confirmar el cambio de contraseña
        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(this);
        add(btnConfirmar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConfirmar) {
            if (tieneContraseñaEstablecida) {
                // Verificar la contraseña actual
                String contraseñaIngresada = new String(contraseñaActualField.getPassword());
                if (!contraseñaIngresada.equals(contraseñaActual)) {
                    JOptionPane.showMessageDialog(this, "La contraseña actual no es correcta.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Establecer la nueva contraseña
            String nuevaContraseña = new String(nuevaContraseñaField.getPassword());
            if (nuevaContraseña.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La nueva contraseña no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar la contraseña (esto se conectaría a tu modelo de usuario)
            contraseñaActual = nuevaContraseña;
            JOptionPane.showMessageDialog(this, "Contraseña cambiada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Aquí puedes hacer la conexión con el modelo o cerrar el panel
        }
    }
}