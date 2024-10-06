package modelo;
import java.util.List;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
public class Profesor extends Usuario {
    // Atributos privados
    private List<Curso> cursosImpartidos;
    private String departamento;
    private static final String CLAVE_SECRETA = "ClaveSecreta123"; // Cambia esto por la clave deseada

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
        // Pedir clave extra usando un JOptionPane
        String contrasenaExtra = JOptionPane.showInputDialog(null, "Ingrese la clave asignada a su departamento para cambiar la contraseña:");

        // Comparar la contraseña extra con la constante
        if (CLAVE_SECRETA.equals(contrasenaExtra)) {
        	System.out.println("Comparando clave, siguiente paso, llamar cambiarContraseña Usuario");
            super.cambiarContrasena(nuevaContrasena); // Llama al método de la clase base
        } else {
            JOptionPane.showMessageDialog(null, "La clave es incorrecta. No se pudo cambiar la contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    @Override
    public boolean esIgual(Usuario otro) {
       // Comparar si son profesores (puedes añadir más criterios)
       return super.esIgual(otro) && this.departamento.equals(((Profesor) otro).departamento);
    }
    @Override
    public String getTipoUsuario() {
        return "Profesor";
    }
    
    // Método para cambiar la visibilidad de la carpeta
    public void cambiarVisibilidad(Carpeta carpeta, String clave) {
        if (CLAVE_SECRETA.equals(clave)) {
            carpeta.setEsVisible(!carpeta.isEsVisible()); // Cambia la visibilidad
            System.out.println("La visibilidad de la carpeta '" + carpeta.getNombre() + "' ha sido cambiada.");
        } else {
            System.out.println("Clave incorrecta. No se puede cambiar la visibilidad.");
        }
    }
    
    //
    // Setters y Getters
    //
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
    public static String getClaveSecreta() {
        return CLAVE_SECRETA;
    }
}
