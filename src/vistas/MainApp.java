// Clase hub de todos los paneles y submenus, maneja el flujo de las ventanas
package vistas;

import controladores.*;
import modelo.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainApp {
	    // Método main que inicializa la aplicación
	    public static void main(String[] args)  throws excepcionContrasena, excepcionArchivoInvalido, excepcionCursoCompleto, excepcionDuplicacionRecurso, excepcionAccesoNoAutorizado, excepcionFormatoString{
	         // Inicializar el lector de CSV
                LectorCSV lectorCSV = new LectorCSV();
	            String[] archivosCSV = new String[] {
	            		"src\\Profesores.csv",
	            	    "src\\Carpetas.csv",
	            	    "src\\Cursos.csv",
	            	    "src\\Estudiantes.csv",
	            	    "src\\Recursos.csv"
	                };
	            
	            
	            for (String archivoCSV : archivosCSV) {
				    lectorCSV.leerArchivoCSV(archivoCSV); // Lee cada archivo CSV
				}
				lectorCSV.cargarArchivosCSV(archivosCSV); // Carga los datos en las listas
	                
	            List<Profesor> profesores = lectorCSV.getProfesores();
	            List<Curso> cursos = lectorCSV.getCursos();
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

	               try {
	            	   System.out.println("Cursos cargados:");
	            	   for (Curso curso : cursos) {
	            	       System.out.println(curso.getNombre()); // Asegúrate de que getNombre() devuelva el nombre correcto
	            	   }
					MainController controller = new MainController(cursos, profesores); // Crear el controlador
				} catch (IOException e) {
					e.printStackTrace();
	                JOptionPane.showMessageDialog(null, "Error al inicializar la aplicación", "Error", JOptionPane.ERROR_MESSAGE);
				} 
	    
	        });
	    }

}
