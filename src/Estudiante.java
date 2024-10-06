package paqueteMain;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Estudiante extends Usuario {
    // Atributos privados
    private int paralelo;
    private List<Curso> cursos;

    // Constructor
    public Estudiante(String nombre, int id, String rut, String email, String contrasena, boolean permisoEspecial, int paralelo) {
        super(nombre, id, rut, email, contrasena, permisoEspecial); // llama a la clase padre
        setParalelo(paralelo);
    }
    // Constructor CSV
    public Estudiante(String nombre, int id, String rut, String email, String contrasena, boolean permisoEspecial, List<Curso> cursos, int paralelo) {
        super(nombre, id, rut, email, contrasena, permisoEspecial); // llama a la clase padre
        setParalelo(paralelo);
        setCursos(cursos);
    }
    // Constructor 2: Incluye atributos de la clase padre, id 
    public Estudiante(String nombre, int id, String rut, String email) {
        super(nombre, id, rut, email);
    }
    // Constructor 3: Incluye atributos de la clase padre, id y paralelo
    public Estudiante(String nombre, int id, String rut, String email, int paralelo) {
        super(nombre, id, rut, email);
        setParalelo(paralelo);
    }
    // Constructor 4: Incluye atributos de la clase padre y paralelo
    public Estudiante(String nombre, String rut, String email, int paralelo) {
        super(nombre, rut, email);
        setParalelo(paralelo);
    }
    // Constructor 5: Incluye atributos sin datos de permiso o seguridad e identificacion
    public Estudiante(String nombre, String rut, String email) {
        super(nombre, rut, email);
    }
    // Constructor 7: Incluye solo atributos basicos
    public Estudiante(String nombre, String rut) {
        super(nombre, rut);
    }
    // Lector CSV
    public static List<Estudiante> cargarEstudiantesDesdeCSV(String archivoCSV, List<Curso> cursos) {
        List<Estudiante> estudiantes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            br.readLine(); // Saltar la primera línea (encabezados)
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";");
                List<Curso> cursosEstudiante = new ArrayList<>();
                String[] cursosArray = campos[6].split(",");
                for (String cursoNombre : cursosArray) {
                    for (Curso curso : cursos) {
                        if (curso.getNombre().equals(cursoNombre)) {
                            cursosEstudiante.add(curso);
                            break;
                        }
                    }
                }
                Estudiante estudiante = new Estudiante(campos[0], Integer.parseInt(campos[1]), campos[2], campos[3], campos[4], Boolean.parseBoolean(campos[5]), cursosEstudiante, Integer.parseInt(campos[7]));
                estudiantes.add(estudiante);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + archivoCSV);
        }
        return estudiantes;
    }
    // Metodos
     @Override
     public void mostrarInformacion() {
     super.mostrarInformacion(); // Muestra información de Usuario
     System.out.println("Paralelo: " + paralelo);
     }
     @Override
     public boolean isPermisoEspecial() {
     return false; // Estudiantes no tienen acceso especial por defecto
     }
     @Override
     public boolean esIgual(Usuario otro) {
     return super.esIgual(otro) && this.paralelo == ((Estudiante) otro).pa
     }
     @Override
     public String getTipoUsuario() {
     return "Estudiante";
     }
    //
    // Setters y Getters
    //
    public int getParalelo() {
        return paralelo;
    }
    public List<Curso> getCursos() {
        return this.cursos;
    }
    public void setParalelo(int paralelo) {
         if (paralelo >= 0) { // Asumiendo que el paralelo no puede ser negativo
            this.paralelo = paralelo;
         } else {
            System.out.println("Advertencia: El paralelo debe ser un número positivo.");
         }
    }
    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }
}
