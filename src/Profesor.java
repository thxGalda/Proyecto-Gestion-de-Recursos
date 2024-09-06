import java.util.List;
public class Profesor extends Usuario {
    // Atributos privados
    private List<Curso> cursosImpartidos;
    private String departamento;

    // Constructor
    public Profesor(String nombre, int id, String rut, String email, String contrasena, boolean permisoEspecial, List<Curso> cursosImpartidos, String departamento) {
        super(nombre, id, rut, email, contrasena, permisoEspecial); // llama a la clase padre
        setCursosImpartidos(cursosImpartidos);
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