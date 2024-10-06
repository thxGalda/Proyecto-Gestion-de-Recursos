package paqueteMain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Carpeta {
    // Atributos privados
    private String nombre;
    private Map<String, Recurso> recursos;
    private boolean esVisible;
    private int id;
    
    // Constructor
    public Carpeta(String nombre, int id, Map <String, Recurso> recursos, boolean esVisible) {
        setNombre(nombre);
        setId(id);
        setRecursos(recursos);
        setEsVisible(esVisible);
    }
    // Constructor 2: Sin recursos
    public Carpeta(String nombre, int id, boolean esVisible) {
        setNombre(nombre);
        setId(id);
        setEsVisible(esVisible);
        this.recursos = new HashMap<>();
    }
    // Constructor 3: Carpeta sin recursos o limites de visibilidad
    public Carpeta(String nombre, int id) {
        setNombre(nombre);
        setId(id);
        this.recursos = new HashMap<>();
    }
    // Lector CSV
    public static List<Carpeta> cargarCarpetasDesdeCSV(String archivoCSV) {
        List<Carpeta> carpetas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            br.readLine(); // Saltar la primera línea (encabezados)
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";");
                Carpeta carpeta = new Carpeta(campos[0], Integer.parseInt(campos[2]), Boolean.parseBoolean(campos[1]));
                carpetas.add(carpeta);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + archivoCSV);
        }
        return carpetas;
    }
    // Metodos
    // Método toString() para mostrar la información de la carpeta
    @Override
    public String toString() {
        return "Carpeta{" +
                "Nombre='" + nombre + '\'' + "| Id: " + id +
                "| Visibilidad=" + (esVisible ? "Visible" : "Oculta") +
                "| Cantidad de Recursos=" + recursos.size() +
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
    public List<Recurso> listarRecursos() {
        // Convertir la colección de valores del mapa a una lista
        List<Recurso> listaRecursos = new ArrayList<>(recursos.values());
        if (listaRecursos.isEmpty()) {
            System.out.println("No hay recursos en la carpeta '" + nombre + "'.");
        } else {
            // Imprimir la información de cada recurso
            System.out.println("Recursos encontrados: \n");
            for (Recurso recurso : listaRecursos) {
                System.out.println(recurso.toString());
            }
        }
        return listaRecursos; // Devuelve la lista de recursos si es necesario para otros usos
    }
     // Método para poblar lista de recursos
     public static List<Recurso> crearListaRecursos() {
        // Implementación para crear una lista de recursos
        List<Recurso> listaRecursos = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String opcion;
        do {
            Recurso recurso = Recurso.crearRecurso(); // Puedes reutilizar el método crearRecurso()
            if (recurso != null) {
                listaRecursos.add(recurso);
            }
            System.out.println("¿Desea agregar otro recurso? (s/n)");
            opcion = scanner.nextLine();
        } while (opcion.equalsIgnoreCase("s"));
        scanner.close();
        return listaRecursos;
    }
    // Método para agregar un recurso a la carpeta (SOBRECARGADO)
    @SuppressWarnings("unlikely-arg-type")
    public void agregarRecurso(Recurso recurso) throws excepcionDuplicacionRecurso {
        if (recursos.containsKey(recurso)) {
            throw new excepcionDuplicacionRecurso("El recurso ya existe en la carpeta.");
        }
         if (recurso != null && recurso.getTitulo() != null) {
            recursos.put(recurso.getTitulo(), recurso);
            System.out.println("Recurso '"+ recurso.getTitulo() + "' agregado a '" + this.getNombre() + "'." );
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
    // Método para mover un recurso de una carpeta a otra
    public boolean moverRecurso(String titulo, Carpeta destino) throws excepcionDuplicacionRecurso {
        if (titulo == null || destino == null) {
            System.out.println("Error: El título o la carpeta destino son nulos.");
            return false;
        }
        if (!recursos.containsKey(titulo)) {
            System.out.println("Error: El recurso no existe en la carpeta actual.");
            return false;
        }
        Recurso recurso = recursos.remove(titulo);
        destino.agregarRecurso(recurso);
        System.out.println("Recurso movido correctamente a la carpeta: " + destino.getNombre());
        return true;
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
    // Método para buscar recursos por nombre
    public List<Recurso> buscarPorNombre(String nombre) {
        if (nombre == null) {
            System.out.println("Error: El nombre no puede ser null.");
            return new ArrayList<>();
        }
        return recursos.values().stream()
                .filter(recurso -> nombre.equals(recurso.getTitulo()))
                .collect(Collectors.toList());
    }
    // Método para buscar una carpeta por nombre (SOBRECARGADO)
    public void buscarRecursos(int opcionBusqueda, String criterioBusqueda){
        List<Recurso> recursosEncontrados = new ArrayList<>();
        switch(opcionBusqueda) {
            case 1:
                recursosEncontrados.addAll(this.buscarPorFecha(criterioBusqueda));
                break;
            case 2:
                recursosEncontrados.addAll(this.buscarPorCategoria(criterioBusqueda));
                break;
            case 3:
                recursosEncontrados.addAll(this.buscarPorAutor(criterioBusqueda));
                break;
            case 4:
                recursosEncontrados.addAll(this.buscarPorNombre(criterioBusqueda));
                break;
            }

        this.mostrarRecursos(recursosEncontrados); // Mostrar los recursos encontrados
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
    //
    // Setters y Getters
    //
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
    public int getId() {
        return id;
    }
    public void setId(int id) {
        if (id >= 0) { // Asumiendo que el id no puede ser negativo
           this.id = id;
        } else {
           System.out.println("Advertencia: El id debe ser un número positivo.");
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
