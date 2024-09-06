import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Carpeta {
    // Atributos privados
    private String nombre;
    private Map<String, Recurso> recursos;
    private boolean esVisible;
    private int id;

    // Constructor
    public Carpeta(String nombre, int id, boolean esVisible) {
        setNombre(nombre);
        setId(id);
        this.recursos = new HashMap<>();
        setEsVisible(esVisible);
    }

    public Carpeta(String nombre, int id) {
        setNombre(nombre);
        setId(id);
        this.recursos = new HashMap<>();
    }

    // Método toString() para mostrar la información de la carpeta
    @Override
    public String toString() {
        return "Carpeta{" +
                "Nombre='" + nombre + '\'' +
                ", Visibilidad=" + (esVisible ? "Visible" : "Oculta") +
                ", Cantidad de Recursos=" + recursos.size() +
                '}';
    }

    public void actualizarNombre(String nuevoNombre) {
        if (nuevoNombre == null || nuevoNombre.isEmpty()) {
            System.out.println("Error: El nombre no puede ser null o vacío.");
        } else {
            this.nombre = nuevoNombre;
            System.out.println("Nombre de la carpeta actualizado.");
        }
    }

    // Método para listar todos los recursos de la carpeta
    public List<String> listarRecursos() {
        List<String> listaRecursos = new ArrayList<>();
        if (!recursos.isEmpty()) {
            for (Recurso recurso : recursos.values()) {
                listaRecursos.add(recurso.getTitulo());
            }
        } else {
            System.out.println("No hay recursos en la carpeta '" + nombre + "'.");
        }
        return listaRecursos;

    }

    // Método para agregar un recurso a la carpeta (SOBRECARGADO)
    public void agregarRecurso(Recurso recurso) {
        if (recurso != null && recurso.getTitulo() != null) {
            recursos.put(recurso.getTitulo(), recurso);
            System.out.println("Recurso agregado: " + recurso.getTitulo());
        } else {
            System.out.println("Advertencia: No se puede agregar un recurso null o sin título.");
        }
    }

    // Método agregarRecurso para una lista de recursos (SOBRECARGADO)
    public void agregarRecurso(List<Recurso> listaRecursos) {
        if (listaRecursos != null && !listaRecursos.isEmpty()) {
            for (Recurso recurso : listaRecursos) {
                if (recurso != null && recurso.getTitulo() != null) {
                    this.recursos.put(recurso.getTitulo(), recurso);
                    System.out.println("Recurso agregado: " + recurso.getTitulo());
                } else {
                    System.out.println("Error: Uno de los recursos o su título es null.");
                }
            }
        } else {
            System.out.println("Error: La lista de recursos no puede ser null o estar vacía.");
        }
    }

    // Método para eliminar un recurso por su título
    public void eliminarRecurso(String titulo) {
        if (titulo != null && recursos.containsKey(titulo)) {
            recursos.remove(titulo);
            System.out.println("Recurso eliminado: " + titulo);
        } else {
            System.out.println("Error: No se puede eliminar. El título es null o no existe en la carpeta.");
        }
    }

    // Método para obtener un recurso por su título
    public Recurso obtenerRecurso(String titulo) {
        if (titulo != null && !titulo.trim().isEmpty()) {
            return recursos.get(titulo);
        } else {
            System.out.println("Advertencia: El título no puede ser null o vacío.");
            return null;
        }
    }

    // Método para mover un recurso de una carpeta a otra
    public boolean moverRecurso(String titulo, Carpeta destino) {
        if (!recursos.containsKey(titulo)) {
            System.out.println("Error: El recurso no existe en la carpeta actual.");
            return false;
        }
        Recurso recurso = recursos.remove(titulo);
        destino.agregarRecurso(recurso);
        System.out.println("Recurso movido correctamente.");
        return true;
    }

    // Método para buscar recursos por autor
    public List<Recurso> buscarPorAutor(String autor) {
        if (autor == null) {
            System.out.println("Error: El autor no puede ser null.");
            return new ArrayList<>();
        }
        return recursos.values().stream()
                .filter(recurso -> autor.equals(recurso.getAutor()))
                .collect(Collectors.toList());
    }

    // Método para buscar recursos por categoría
    public List<Recurso> buscarPorCategoria(String categoria) {
        if (categoria == null) {
            System.out.println("Error: La categoría no puede ser null.");
            return new ArrayList<>();
        }
        return recursos.values().stream()
                .filter(recurso -> categoria.equals(recurso.getCategoria()))
                .collect(Collectors.toList());
    }

    // Método para buscar recursos por fecha
    public List<Recurso> buscarPorFecha(String fecha) {
        if (fecha == null) {
            System.out.println("Error: La fecha no puede ser null.");
            return new ArrayList<>();
        }
        return recursos.values().stream()
                .filter(recurso -> fecha.equals(recurso.getFecha()))
                .collect(Collectors.toList());
    }

    // Método para mostrar todos los recursos encontrados
    public void mostrarRecursos(List<Recurso> listaRecursos) {
        if (listaRecursos.isEmpty()) {
            System.out.println("No se encontraron recursos que coincidan con los criterios de búsqueda.");
        } else {
            for (Recurso recurso : listaRecursos) {
                System.out.println(recurso.toString());
            }
        }
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        } else {
            System.out.println("Advertencia: El nombre de la carpeta no puede ser null o vacío.");
        }
    }
    public Map<String, Recurso> getRecursos() {
        return recursos;
    }
    public void setRecursos(Map<String, Recurso> recursos) {
        if (recursos != null) {
            this.recursos = recursos;
        } else {
            System.out.println("Advertencia: La colección de recursos no puede ser null.");
        }
    }
    public boolean isEsVisible() {
        return esVisible;
    }
    public void setEsVisible(boolean esVisible) {
        this.esVisible = esVisible;
    }
}