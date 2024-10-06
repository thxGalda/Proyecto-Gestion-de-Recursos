package modelo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
public class Menu {
	private Usuario usuarioActual;
    private Scanner scanner;
    private List<Curso> cursos;  // Lista de cursos disponibles
    private List <Estudiante> estudiantes; // Lista de estudiantes auxiliares
    private List<Profesor> profesores;

    // Constructor
 // Constructor
    public Menu() {
        scanner = new Scanner(System.in);
        this.cursos = new ArrayList<>();  // Inicializamos la lista de cursos
        this.estudiantes = new ArrayList<>();   // Inicializamos la lista de estudiantes
        this.profesores = new ArrayList<>();
        
        String[] archivosCSV = new String[] {
                "https://github.com/thxGalda/Proyecto-Gestion-de-Recursos/blob/main/src/Profesores.csv",
                "https://github.com/thxGalda/Proyecto-Gestion-de-Recursos/blob/main/src/Carpetas.csv",
                "https://github.com/thxGalda/Proyecto-Gestion-de-Recursos/blob/main/src/Cursos.csv",
                "https://github.com/thxGalda/Proyecto-Gestion-de-Recursos/blob/main/src/Estudiantes.csv",
                "https://github.com/thxGalda/Proyecto-Gestion-de-Recursos/blob/main/src/Recursos.csv"
            };
            cargarArchivosCSV(archivosCSV);
        
        cargarCursos();
        cargarProfesores();
    }
    
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
    
    public void cargarCursos(){
    	// llama a un metodo de un csv para crear lista
    	// this.cursos = // rellenar lista
    	
    }
    public void cargarProfesores() {
    	// llama a un metodo de un csv para crear lista
    	// this.profesores = // rellenar lista
    }
    
    public void pasarCursos(List<Curso> cursosExterna) {
        cursosExterna.addAll(this.cursos); // Agrega todos los cursos al listado proporcionado
    }

    // Método para cargar los profesores en la lista proporcionada
    public void pasarProfesores(List<Profesor> profesoresExterna) {
        profesoresExterna.addAll(this.profesores); // Agrega todos los profesores al listado proporcionado
    }
    
 // Metodo para agregar cursos en la carga inicial de datos
    public void agregarCurso(Curso curso) {
        if (curso != null) {
            cursos.add(curso);
            System.out.println("Curso agregado: " + curso.getNombre());
        } else {
            System.out.println("No se puede agregar un curso nulo.");
        }
    }

    // Método auxiliar para generar un ID de usuario aleatorio
    public int generarIdUsuario() {
        // Usar Random para generar un entero aleatorio de hasta 6 dígitos
        Random random = new Random();
        return random.nextInt(999999);  // Genera un número entre 0 y 999999
    }

    // Método para ingresar los datos del usuario
    public Usuario ingresarDatosUsuario(String nombre, String rut, String correo, int tipoUsuario, int paralelo, String departamento) {
        // Generar un ID aleatorio para el usuario
        int idUsuario = generarIdUsuario();
        if (tipoUsuario == 1) {
            // Crear un nuevo Estudiante con la ID generada
            Estudiante estudiante = new Estudiante(nombre, rut, correo, paralelo);
            estudiante.setId(idUsuario);  // Asignar ID
            return estudiante;
        } else if (tipoUsuario == 2) {
            // Crear un nuevo Profesor con la ID generada y asignar permiso especial
            Profesor profesor = new Profesor(nombre, rut, correo, departamento);
            profesor.setId(idUsuario);  // Asignar ID
            profesor.setPermisoEspecial(true);  // Otorgar permiso especial automáticamente
            return profesor;
        } else {
            // Opción no válida, se creará un usuario por defecto como Estudiante.
            Estudiante estudiantePorDefecto = new Estudiante(nombre, rut, correo);
            estudiantePorDefecto.setId(idUsuario);  // Asignar ID
            return estudiantePorDefecto;
        }
    }
    // Método para mostrar la ficha personal del usuario
    private void mostrarFichaPersonal() {
    	System.out.println(usuarioActual.toString());

        if (usuarioActual instanceof Estudiante) {
            Estudiante estudiante = (Estudiante) usuarioActual;
            System.out.println("Paralelo: " + estudiante.getParalelo());
        } else if (usuarioActual instanceof Profesor) {
            Profesor profesor = (Profesor) usuarioActual;
            System.out.println("Departamento " + profesor.getDepartamento());
        }
    }
    // Método para cambiar contraseña modelo
    public void cambiarContraseña(String contraseñaActualIngresada, String nuevaContrasena) {
    	
    	System.out.println("Paso actual: metodo cambiar contraseña menu, siguiente paso, verificar que usuario no sea nulo");
    	if (usuarioActual == null) {
            throw new IllegalStateException("No hay un usuario autenticado.");
        }
    	// Verificar si el usuario actual es un profesor
        if (usuarioActual instanceof Profesor) {
        	System.out.println("Verificar usuario profesor");
            Profesor profesorActual = (Profesor) usuarioActual;
            System.out.println("Convertir a profesor, llamando metodo de profesor");
            // Llamar al método sobreescrito de Profesor, que maneja la clave extra internamente
            profesorActual.cambiarContrasena(nuevaContrasena);
            
        } else {
            // Si es otro tipo de usuario, se sigue la lógica estándar
            String contraseñaActual = usuarioActual.getContrasena();

            // Verificar si el usuario no tiene contraseña establecida
            if (contraseñaActual == null) {
                // Caso donde no hay contraseña establecida, se establece la nueva
                usuarioActual.cambiarContrasena(nuevaContrasena);
            } else {
                // Caso donde ya hay una contraseña establecida
                if (contraseñaActualIngresada.equals(contraseñaActual)) {
                    // Se establece la nueva contraseña
                    usuarioActual.cambiarContrasena(nuevaContrasena);
                } else {
                    throw new IllegalArgumentException("La contraseña actual no es correcta.");
                }
            }
        }
    }
    // Método para mostrar y revisar cursos disponibles
    
    // -------------------------------------------------- SUBMENU ----------------------------------
    // Metodo para el submenu de carpeta
    
    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }
   

}
