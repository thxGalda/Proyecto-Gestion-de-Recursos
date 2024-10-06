package paqueteMain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectorCSV {
    private String[] archivosCSV;

    public LectorCSV(String[] archivosCSV) {
        this.archivosCSV = archivosCSV;
    }

    public void leerCSVs() {
        for (String archivoCSV : archivosCSV) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] campos = linea.split(";");
                    // Aquí puedes procesar los campos si lo deseas
                    System.out.println("Archivo: " + archivoCSV + ", Campos: " + String.join(", ", campos));
                }
            } catch (IOException e) {
                System.err.println("Error leyendo archivo: " + archivoCSV);
                // Maneja la excepción si es necesario
            }
        }
    }

    public static void main(String[] args) {
        String[] archivosCSV = new String[] {
            "C:\\Users\\Amaro Fibla\\Desktop\\Proyecto\\Proyecto-Gestion-de-Recursos\\src\\Profesores.csv",
            "C:\\Users\\Amaro Fibla\\Desktop\\Proyecto\\Proyecto-Gestion-de-Recursos\\src\\Carpetas.csv",
            "C:\\Users\\Amaro Fibla\\Desktop\\Proyecto\\Proyecto-Gestion-de-Recursos\\src\\Cursos.csv",
            "C:\\Users\\Amaro Fibla\\Desktop\\Proyecto\\Proyecto-Gestion-de-Recursos\\src\\Estudiantes.csv",
            "C:\\Users\\Amaro Fibla\\Desktop\\Proyecto\\Proyecto-Gestion-de-Recursos\\src\\Recursos.csv"
        };

        LectorCSV lector = new LectorCSV(archivosCSV);
        lector.leerCSVs();
    }
}
