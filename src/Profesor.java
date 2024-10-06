package paqueteMain;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Profesor extends Usuario {
    // Atributos privados
    private List<Curso> cursosImpartidos;
    private static CLAVE_SECRETA clave1234;
    private String departamento;

    // Constructor
    public Profesor(String nombre, int id, String rut, String email, String contrasena, boolean permisoEspecial, List<Curso> cursosImpartidos, String departamento) {
        super(nombre, id, rut, email, contrasena, permisoEspecial); // llama a la clase padre
        setCursosImpartidos(cursosImpartidos);
        setDepartamento(departamento);
    }
    // Constructor Lectura CSV
    public Profesor(String nombre, int id, String rut, String email, String contrasena, boolean permisoEspecial, String departamento) {
        super(nombre, id, rut, email, contrasena, permisoEspecial); // llama a la clase padre
        setDepartamento(departamento);
    }
    // Constructor 2: Incluye atributos de la clase padre y departamento
    public Profesor(String nombre, String rut, String email, String departamento) {
        super(nombre, rut, email);
        setDepartamento(departamento);
    }

    // Constructor 3: Incluye atributos de la clase padre, departamento y lista de cursos impartidos
    public Profesor(String nombre, String rut, String email, String departamento, List<Curso> cursosImpartidos) {
        super(nombre, rut, email);
        setCursosImpartidos(cursosImpartidos);
    }

    // Constructor 4: Incluye solo atributos de la clase padre
    public Profesor(String nombre, String rut, String email) {
        super(nombre, rut, email);
    }

    // Constructor 5: Incluye solo atributos basicos
    public Profesor(String nombre, String rut) {
        super(nombre, rut);
    }
    // Lector CSV
    public static List<Profesor> cargarProfesoresDesdeCSV(String archivoCSV) {
        List<Profesor> profesores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            br.readLine(); // Saltar la primera línea (encabezados)
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";");
                Profesor profesor = new Profesor(campos[0], Integer.parseInt(campos[1]), campos[2], campos[3], campos[4], Boolean.parseBoolean(campos[5]), campos[6]);
                profesores.add(profesor);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + archivoCSV);
        }
        return profesores;
    }
    // Metodos

     @Override
     public void mostrarInformacion() {
     super.mostrarInformacion(); // Muestra información de Usuario
     System.out.println("Departamento: " + departamento);
     }
     @Override
     public boolean isPermisoEspecial() {
     return true; // Profesores tienen acceso especial
     }
     @Override
     public void cambiarContrasena(String nuevaContrasena) {
         Scanner scanner = new Scanner(System.in);
         // Pedir clave extra para verificar
         String contrasenaExtra = scanner.nextLine();
         
         // Comparar la contraseña extra con la constante
         
         if (CLAVE_SECRETA.equals(contrasenaExtra)) {
             super.cambiarContrasena(nuevaContrasena); // Llama al método de l
         } else {
             System.out.println("La clave es incorrecta. No se pudo cambiar la
         }
     }
     @Override
     public boolean esIgual(Usuario otro) {
     // Comparar si son profesores (puedes añadir más criterios)
     return super.esIgual(otro) && this.departamento.equals(((Profesor) otr
     }
     @Override
     public String getTipoUsuario() {
     return "Profesor";
     }
    
    //
    // Setters y Getters
    //
    public List<Curso> getCursosImpartidos() {
        return cursosImpartidos;
    }
    public void setCursosImpartidos(List<Curso> cursosImpartidos) {
        if (cursosImpartidos != null) {
            this.cursosImpartidos = cursosImpartidos;
        } else {
            System.out.println("Advertencia: La lista de cursos impartidos no puede ser null.");
        }
    }
    public String getDepartamento() {
        return departamento;
    }
    public void setDepartamento(String departamento) {
        if (departamento != null && !departamento.trim().isEmpty()) {
            this.departamento = departamento;
        } else {
            System.out.println("Advertencia: El nombre del departamento no puede ser null o vacío.");
        }
    }
}