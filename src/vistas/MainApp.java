// Clase hub de todos los paneles y submenus, maneja el flujo de las ventanas
package vistas;

import controladores.*;
import modelo.*;

import javax.swing.*;
import java.awt.*;

public class MainApp {
	    // Método main que inicializa la aplicación
	    public static void main(String[] args)  throws excepcionContrasena, excepcionArchivoInvalido, excepcionCursoCompleto, excepcionDuplicacionRecurso, excepcionAccesoNoAutorizado, excepcionFormatoString{
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
	            MainController controlador = new MainController();  // Crear el controlador
	        });
	    }

}