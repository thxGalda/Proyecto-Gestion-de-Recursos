public class Usuario {
    // Atributos privados
    private String nombre;
    private int id;
    private String rut;
    private String email;
    private String contrasena; // O puedes usar una clase Password para manejar esto
    private boolean permisoEspecial; // Para el acceso a contenido restringido

    // Constructor
    public Usuario(String nombre, int id, String rut, String email, String contrasena, boolean permisoEspecial) {
        setNombre(nombre);
        setId(id);
        setRut(rut);
        setEmail(email);
        setContrasena(contrasena);
        this.permisoEspecial = permisoEspecial; // no es necesario verificar null para boolean
    }
    // Constructor 2: Incluye atributos sin datos de permiso o seguridad
    public Usuario(String nombre, int id, String rut, String email) {
        setNombre(nombre);
        setId(id);
        setRut(rut);
        setEmail(email);
    }
    // Constructor 3: Incluye atributos sin datos de permiso o seguridad e identificacion
    public Usuario(String nombre, String rut, String email) {
        setNombre(nombre);
        setRut(rut);
        setEmail(email);
    }
    // Constructor 4: Incluye solo atributos basicos
    public Usuario(String nombre, String rut) {
        setNombre(nombre);
        setRut(rut);
    }
    // Método toString() para mostrar la información del usuario
    @Override
    public String toString() {
        return "Usuario: " + nombre + "\nID: " + id + "\nRUT: " + rut + "\nEmail: " + email +
                "\nPermiso Especial: " + (permisoEspecial ? "Sí" : "No");
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        if (nombre != null) {
            this.nombre = nombre;
        } else {
            System.out.println("Advertencia: El nombre no puede ser null.");
        }
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        if (id < 0) {
            this.id = id;
        } else {
            System.out.println("Advertencia: El ID no puede ser negativo.");
        }
    }
    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        if (rut != null) {
            this.rut = rut;
        } else {
            System.out.println("Advertencia: El rut no puede ser null.");
        }
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null) {
            this.email = email;
        } else {
            System.out.println("Advertencia: El email no puede ser null.");
        }
    }
    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        if (contrasena != null) {
            this.contrasena = contrasena;
        } else {
            System.out.println("Advertencia: La contraseña no puede ser null.");
        }
    }
    public boolean isPermisoEspecial() {
        return permisoEspecial;
    }
    public void setPermisoEspecial(boolean permisoEspecial) {
        this.permisoEspecial = permisoEspecial;
    }
}
