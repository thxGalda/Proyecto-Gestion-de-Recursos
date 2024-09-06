import java.util.ArrayList;
import java.util.List;

public class Curso {
    // Atributos privados
    private String nombre;
    private String descripcion;
    private List<Carpeta> carpetas;
    private Profesor profesor;
    private int numEstudiantes;
    private List<Estudiante> estudiantes;

    // Constructor
    public Curso(String nombre, String descripcion, Profesor profesor) {
        setNombre(nombre);
        setDescripcion(descripcion);
        setProfesor(profesor);
        this.carpetas = new ArrayList<>();
        this.estudiantes = new ArrayList<>();
        this.numEstudiantes = 0; // Inicialmente no hay estudiantes inscritos
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        } else {
            System.out.println("Advertencia: El nombre del curso no puede ser null o vacío.");
        }
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        if (descripcion != null && !descripcion.trim().isEmpty()) {
            this.descripcion = descripcion;
        } else {
            System.out.println("Advertencia: La descripción del curso no puede ser null o vacía.");
        }
    }
    public List<Carpeta> getCarpetas() {
        return carpetas;
    }
    public void setCarpetas(List<Carpeta> carpetas) {
        if (carpetas != null) {
            this.carpetas = carpetas;
        } else {
            System.out.println("Advertencia: La lista de carpetas no puede ser null.");
        }
    }
    public Profesor getProfesor() {
        return profesor;
    }
    public void setProfesor(Profesor profesor) {
        if (profesor != null) {
            this.profesor = profesor;
        } else {
            System.out.println("Advertencia: El profesor no puede ser null.");
        }
    }
    public int getNumEstudiantes() {
        return numEstudiantes;
    }
    public void setNumEstudiantes(int numEstudiantes) {
        if (numEstudiantes >= 0) {
            this.numEstudiantes = numEstudiantes;
        } else {
            System.out.println("Advertencia: El número de estudiantes no puede ser negativo.");
        }
    }
    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }
    public void setEstudiantes(List<Estudiante> estudiantes) {
        if (estudiantes != null) {
            this.estudiantes = estudiantes;
            this.numEstudiantes = estudiantes.size(); // Actualiza el número de estudiantes
        } else {
            System.out.println("Advertencia: La lista de estudiantes no puede ser null.");
        }
    }

    // Método para agregar una carpeta al curso
    public void agregarCarpeta(Carpeta nuevaCarpeta) {
        if (nuevaCarpeta == null) {
            System.out.println("Error: La carpeta no puede ser null.");
            return;
        }
        carpetas.add(nuevaCarpeta);
        System.out.println("Carpeta '" + nuevaCarpeta.getNombre() + " (" + nuevaCarpeta.getId() + ")' agregada al curso.");
    }

    // Método para eliminar una carpeta del curso (SOBRECARGADO)
    public void eliminarCarpeta(int idCarpeta) {
        if (idCarpeta < 0) {
            System.out.println("Error: El id de la carpeta no puede ser negativo.");
            return;
        }
        boolean eliminada = carpetas.removeIf(carpeta -> idCarpeta == carpeta.getId());
        if (eliminada) {
            System.out.println("Carpeta (" + idCarpeta + ") eliminada del curso.");
        } else {
            System.out.println("Error: No se encontró la carpeta con la id (" + idCarpeta + ").");
        }
    }

    // Método para eliminar una carpeta del curso (SOBRECARGADO)
    public void eliminarCarpeta(String nombreCarpeta) {
        if (nombreCarpeta == null) {
            System.out.println("Error: El nombre de la carpeta no puede ser null.");
            return;
        }
        boolean eliminada = carpetas.removeIf(carpeta -> nombreCarpeta.equals(carpeta.getNombre()));
        if (eliminada) {
            System.out.println("Carpeta '" + nombreCarpeta + "' eliminada del curso.");
        } else {
            System.out.println("Error: No se encontró la carpeta con el nombre '" + nombreCarpeta + "'.");
        }
    }

    // Método para agregar un recurso a una carpeta específica (SOBRECARGADO)
    public void cargarRecurso(String nombreCarpeta, Recurso recurso) {
        if (nombreCarpeta == null || recurso == null) {
            System.out.println("Error: El nombre de la carpeta o el recurso no pueden ser null.");
            return;
        }
        for (Carpeta carpeta : carpetas) {
            if (carpeta.getNombre().equals(nombreCarpeta)) {
                carpeta.agregarRecurso(recurso);
                System.out.println("Recurso '" + recurso.getTitulo() + "' agregado a la carpeta '" + nombreCarpeta + "'.");
                return;
            }
        }
        System.out.println("Error: No se encontró la carpeta con el nombre '" + nombreCarpeta + "'.");
    }

    // Método para agregar una lista de recursos a una carpeta específica (SOBRECARGADO)
    public void cargarRecurso(String nombreCarpeta, List<Recurso> recursos) {
        if (nombreCarpeta == null || recursos == null || recursos.isEmpty()) {
            System.out.println("Error: El nombre de la carpeta o la lista de recursos no pueden ser null o vacíos.");
            return;
        }
        for (Carpeta carpeta : carpetas) {
            if (carpeta.getNombre().equals(nombreCarpeta)) {
                for (Recurso recurso : recursos) {
                    carpeta.agregarRecurso(recurso);
                }
                System.out.println("Se agregaron " + recursos.size() + " recursos a la carpeta '" + nombreCarpeta + "'.");
                return;
            }
        }
        System.out.println("Error: No se encontró la carpeta con el nombre '" + nombreCarpeta + "'.");
    }

    // Método para agregar un recurso a una carpeta específica por ID (SOBRECARGADO)
    public void cargarRecurso(int idCarpeta, Recurso recurso) {
        if (recurso == null) {
            System.out.println("Error: El recurso no puede ser null.");
            return;
        }
        for (Carpeta carpeta : carpetas) {
            if (carpeta.getId() == idCarpeta) {
                carpeta.agregarRecurso(recurso);
                System.out.println("Recurso '" + recurso.getTitulo() + "' agregado a la carpeta con ID '" + idCarpeta + "'.");
                return;
            }
        }
        System.out.println("Error: No se encontró la carpeta con el ID '" + idCarpeta + "'.");
    }

    // Método para agregar una lista de recursos a una carpeta específica por ID (SOBRECARGADO)
    public void cargarRecurso(int idCarpeta, List<Recurso> recursos) {
        if (recursos == null || recursos.isEmpty()) {
            System.out.println("Error: La lista de recursos no puede ser null o vacía.");
            return;
        }
        for (Carpeta carpeta : carpetas) {
            if (carpeta.getId() == idCarpeta) {
                for (Recurso recurso : recursos) {
                    carpeta.agregarRecurso(recurso);
                }
                System.out.println("Se agregaron " + recursos.size() + " recursos a la carpeta con ID '" + idCarpeta + "'.");
                return;
            }
        }
        System.out.println("Error: No se encontró la carpeta con el ID '" + idCarpeta + "'.");
    }

    // Método para buscar una carpeta por nombre (SOBRECARGADO)
    public Carpeta buscarCarpeta(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            System.out.println("Error: El nombre de la carpeta no puede ser null o vacío.");
            return null;
        }
        for (Carpeta carpeta : carpetas) {
            if (carpeta.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Carpeta con nombre '" + nombre + "' encontrada.");
                return carpeta;
            }
        }
        System.out.println("Error: No se encontró ninguna carpeta con el nombre '" + nombre + "'.");
        return null;
    }

    // Método sobrecargado para buscar una carpeta por ID (SOBRECARGADO)
    public Carpeta buscarCarpeta(int id) {
        for (Carpeta carpeta : carpetas) {
            if (carpeta.getId() == id) {
                System.out.println("Carpeta con ID '" + id + "' encontrada.");
                return carpeta;
            }
        }
        System.out.println("Error: No se encontró ninguna carpeta con el ID '" + id + "'.");
        return null;
    }

    // Método para ordenar carpetas por nombre
    public void ordenarCarpetasPorNombre() {
        Collections.sort(carpetas, Comparator.comparing(Carpeta::getNombre));
        System.out.println("Carpetas ordenadas por nombre.");
    }

    // Método para ordenar carpetas por visibilidad
    public void ordenarCarpetasPorVisibilidad() {
        Collections.sort(carpetas, Comparator.comparing(Carpeta::isEsVisible).reversed());
        System.out.println("Carpetas ordenadas por visibilidad.");
    }

    // Método para mostrar todas las carpetas
    public void mostrarCarpetas() {
        if (carpetas.isEmpty()) {
            System.out.println("No hay carpetas en el curso.");
        } else {
            for (Carpeta carpeta : carpetas) {
                System.out.println("Carpeta: " + carpeta.getNombre() + " | Visible: " + carpeta.isEsVisible());
            }
        }
    }

    // Método para modificar la descripción del curso
    public void actualizarDescripcion(String nuevaDescripcion) {
        if (nuevaDescripcion == null || nuevaDescripcion.isEmpty()) {
            System.out.println("Error: La descripción no puede ser null o vacía.");
        } else {
            this.descripcion = nuevaDescripcion;
            System.out.println("Descripción actualizada correctamente.");
        }
    }

    public void agregarEstudiante(Estudiante estudiante) {
        if (estudiante != null) {
            estudiantes.add(estudiante);
            numEstudiantes++;
        } else {
            System.out.println("Advertencia: No se puede agregar un estudiante null.");
        }
    }

    // Método para eliminar un estudiante del curso por nombre. (SOBRECARGADO)
    public void eliminarEstudiante(Estudiante estudiante) {
        if (estudiante != null) {
            estudiantes.remove(estudiante);
            numEstudiantes--;
        } else {
            System.out.println("Advertencia: No se puede eliminar un estudiante null.");
        }
    }

    // Método para eliminar un estudiante del curso por id. (SOBRECARGADO)
    public boolean eliminarEstudiante(int id) {
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getId() == id) {
                estudiantes.remove(estudiante);
                numEstudiantes--;
                System.out.println("Estudiante eliminado correctamente.");
                return true;
            }
        }
        System.out.println("Error: No se encontró al estudiante con ID: " + id);
        return false;
    }

    // Método para eliminar un estudiante del curso por id. (SOBRECARGADO)
    public boolean eliminarEstudiante(String nombre) {
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getNombre().equals(nombre)) {
                estudiantes.remove(estudiante);
                numEstudiantes--;
                System.out.println("Estudiante eliminado correctamente.");
                return true;
            }
        }
        System.out.println("Error: No se encontró al estudiante con Nombre: '" + nombre + "'.");
        return false;
    }

    // Metodo para buscar un estudiante inscrito en el curso por su nombre (SOBRECARGADO)
    public Estudiante buscarEstudiante(String nombre) {
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getNombre().equalsIgnoreCase(nombre)) {
                return estudiante;
            }
        }
        return null;
    }

    // Metodo para buscar un estudiante inscrito en el curso por su ID (SOBRECARGADO)
    public Estudiante buscarEstudiante(int id) {
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getId() == id) {
                return estudiante;
            }
        }
        return null;
    }

    // Metodo para listar los nombres de los estudiantes inscritos en el curso
    public List<String> obtenerEstudiantesInscritos() {
        List<String> nombresEstudiantes = new ArrayList<>();
        for (Estudiante estudiante : estudiantes) {
            nombresEstudiantes.add(estudiante.getNombre());
        }
        return nombresEstudiantes;
    }

    // Método para mostrar todos los estudiantes encontrados
    public void mostrarEstudiantesInscritos(List<Recurso> listaRecursos) {
        if (listaRecursos.isEmpty()) {
            System.out.println("No se encontraron estudiantes que coincidan con los criterios de búsqueda.");
        } else {
            for (Estudiante estudiante : estudiantes) {
                System.out.println(estudiante.toString());
            }
        }
    }

    // Metodo para asignar o cambiar el profesor encargado del curso
    public void asignarProfesor(Profesor nuevoProfesor) {
        if (nuevoProfesor == null) {
            System.out.println("Error: El profesor no puede ser null.");
        } else {
            this.profesor = nuevoProfesor;
            System.out.println("Profesor asignado correctamente.");
        }
    }

}