public class Estudiante extends Usuario {
    // Atributos privados
    private int paralelo;

    // Constructor
    public Estudiante(String nombre, int id, String rut, String email, String contrasena, boolean permisoEspecial, int paralelo) {
        super(nombre, id, rut, email, contrasena, permisoEspecial); // llama a la clase padre
        setParalelo(paralelo);
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

    // Metodos
    @Override
    public String toString() {
        return super.toString() + "\nParalelo: " + paralelo + "\n";
    }
    //
    // Setters y Getters
    //
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
