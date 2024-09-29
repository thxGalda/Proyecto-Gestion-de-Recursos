package vistas;

import javax.swing.*;
import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class Launcher {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Configurar el Look and Feel Nimbus
                for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        try {
                            UIManager.setLookAndFeel(info.getClassName());
                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                                | UnsupportedLookAndFeelException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }

                // Crear y mostrar la VentanaMenu
                new VentanaMenu();  // No es necesario llamar a `mostrar()`, ya que el JFrame se muestra en el constructor
            }
        });
    }
}
