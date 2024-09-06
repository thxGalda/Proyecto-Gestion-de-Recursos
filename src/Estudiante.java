import java.util.List;
public class Estudiante extends Usuario {
    // Atributos privados
    private List<Curso> cursos;
    private int paralelo;

    // Constructor
    public Estudiante(String nombre, String id, String rut, String email, String contrasena, boolean permisoEspecial, List<Curso> cursos, int paralelo) {
        super(nombre, id, rut, email, contrasena, permisoEspecial); // llama a la clase padre
        setCursos(cursos);
        setParalelo(paralelo);
    }

    // Constructor 2: Incluye atributos de la clase padre y paralelo
    public Estudiante(String nombre, String rut, String email, int paralelo) {
        super(nombre, rut, email);
        setParalelo(paralelo);
    }

    // Constructor 3: Incluye atributos de la clase padre, paralelo y lista de cursos
    public Estudiante(String nombre, String rut, String email, int paralelo, List<Curso> cursos) {
        this(nombre, rut, email, paralelo); // Llama al constructor anterior
        setCursos(cursos);
    }

    // Constructor 4: Incluye solo atributos de la clase padre
    public Estudiante(String nombre, String rut, String email) {
        super(nombre, rut, email);
    }

    // Constructor 5: Incluye solo atributos basicos
    public Estudiante(String nombre, String rut) {
        super(nombre, rut);
    }
    public List<Curso> getCursos() {
        return cursos;
    }
    public void setCursos(List<Curso> cursos) {
        if (cursos != null) {
            this.cursos = cursos;
        } else {
            System.out.println("Advertencia: La lista de cursos no puede ser null.");
        }
    }
    public int getParalelo() {
        return paralelo;
    }
    public void setParalelo(int paralelo) {
        if (paralelo >= 0) { // Asumiendo que el paralelo no puede ser negativo
            this.paralelo = paralelo;
        } else {
            System.out.println("Advertencia: El paralelo debe ser un número positivo.");
        }
    }
}