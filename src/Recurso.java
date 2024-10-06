package paqueteMain;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Recurso {
    // Atributos privados
    private String titulo;
    private String autor;
    private String categoria;
    private String fecha;
    private String formato;
    private String curso;
    private boolean esVisible;

    // Constructor
    public Recurso(String titulo, String autor, String categoria, String fecha, String formato, String curso, boolean esVisible) {
        setTitulo(titulo);
        setAutor(autor);
        setCategoria(categoria);
        setFecha(fecha);
        setFormato(formato);
        setCurso(curso);
        this.esVisible = esVisible;
    }

    // Constructor 2: Incluye atributos sin curso o visibilidad (Recurso publico)
    public Recurso(String titulo, String autor, String categoria, String fecha, String formato) {
        setTitulo(titulo);
        setAutor(autor);
        setCategoria(categoria);
        setFecha(fecha);
        setFormato(formato);
    }

    // Constructor 3: Incluye atributos sin curso, visibilidad, ni categoria
    public Recurso(String titulo, String autor, String fecha, String formato) {
        setTitulo(titulo);
        setAutor(autor);
        setFecha(fecha);
        setFormato(formato);
    }

    // Constructor 4: Incluye atributos basicos con autor
    public Recurso(String titulo, String autor, String formato) {
        setTitulo(titulo);
        setAutor(autor);
        setFormato(formato);
    }

    // Constructor 5: Incluye atributos basicos
    public Recurso(String titulo, String formato) {
        setTitulo(titulo);
        setFormato(formato);
    }
    // Lector CSV
    public static List<Recurso> cargarRecursosDesdeCSV(String archivoCSV) {
        List<Recurso> recursos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            br.readLine(); // Saltar la primera línea (encabezados)
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";");
                Recurso recurso = new Recurso(campos[0], campos[1], campos[2], campos[3], campos[4], campos[5], Boolean.parseBoolean(campos[6]));
                recursos.add(recurso);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + archivoCSV);
        }
        return recursos;
    }
    // Método toString() para mostrar la información del recurso
    @Override
    public String toString() {
        return "Recurso{" +
                "Título='" + titulo + '\'' +
                ", Autor='" + autor + '\'' +
                ", Categoría='" + categoria + '\'' +
                ", Fecha='" + fecha + '\'' +
                ", Formato='" + formato + '\'' +
                ", Curso='" + curso + '\'' +
                ", Visibilidad=" + (esVisible ? "Visible" : "Oculto") +
                '}';
    }
    public int compareTo(Recurso otro) {
        return this.titulo.compareTo(otro.getTitulo());
    }

    public boolean compararPorTitulo(Recurso otro) {
        return this.titulo.equals(otro.getTitulo());
    }

    public boolean esMismoAutor(Recurso otro) {
        return this.autor.equals(otro.getAutor());
    }
    // Metodo para crear objeto recurso
    public static Recurso crearRecurso() { // Static para permitir invocarlo sin crear una instancia de clase Recurso
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el título del recurso:");
        String titulo = scanner.nextLine();

        System.out.println("Ingrese el formato del recurso (ej. PDF, Video, etc.):");
        String formato = scanner.nextLine();

        System.out.println("Ingrese el autor del recurso (opcional):");
        String autor = scanner.nextLine();

        System.out.println("Ingrese la categoría del recurso (opcional):");
        String categoria = scanner.nextLine();

        System.out.println("Ingrese la fecha del recurso (opcional):");
        String fecha = scanner.nextLine();

        System.out.println("Ingrese el curso al que pertenece el recurso (opcional):");
        String curso = scanner.nextLine();

        System.out.println("¿Es el recurso visible para estudiantes? (true/false) (opcional):");
        String esVisibleStr = scanner.nextLine();
        Boolean esVisible = esVisibleStr.isEmpty() ? null : Boolean.parseBoolean(esVisibleStr);

        // Lógica para seleccionar el constructor adecuado
        if (!curso.isEmpty() && esVisible != null) {
            return new Recurso(titulo, autor.isEmpty() ? null : autor,
                    categoria.isEmpty() ? null : categoria,
                    fecha.isEmpty() ? null : fecha,
                    formato.isEmpty() ? null : formato,
                    curso, esVisible);
        } else if (!curso.isEmpty()) {
            return new Recurso(titulo, autor.isEmpty() ? null : autor,
                    categoria.isEmpty() ? null : categoria,
                    fecha.isEmpty() ? null : fecha,
                    formato.isEmpty() ? null : formato);
        } else if (!categoria.isEmpty()) {
            return new Recurso(titulo, autor.isEmpty() ? null : autor,
                    fecha.isEmpty() ? null : fecha,
                    formato.isEmpty() ? null : formato);
        } else if (!autor.isEmpty()) {
            return new Recurso(titulo, autor.isEmpty() ? null : autor,
                    formato.isEmpty() ? null : formato);
        } else {
            return new Recurso(titulo, formato);
        }
    }

    // Metodo para cambiar la visibilidad del recurso, ocultar o mostra
    public void cambiarVisibilidad(boolean esVisible) {
        setEsVisible(esVisible);
        System.out.println("Visibilidad del recurso actualizada.");
    }

    // Metodo para actualizar titulo
    public void actualizarTitulo(String nuevoTitulo) {
        if (nuevoTitulo == null || nuevoTitulo.isEmpty()) {
            System.out.println("Error: El título no puede ser null o vacío.");
        } else {
            setTitulo(nuevoTitulo);
            System.out.println("Título actualizado correctamente.");
        }
    }
    // Metodo para actualizar autor
    public void actualizarAutor(String nuevoAutor) {
        if (nuevoAutor == null || nuevoAutor.isEmpty()) {
            System.out.println("Error: El autor no puede ser null o vacío.");
        } else {
            setAutor(nuevoAutor);
            System.out.println("Autor actualizado correctamente.");
        }
    }
    // Metodo para actualizar fecha
    public void actualizarFecha(String nuevaFecha) {
        if (nuevaFecha == null || nuevaFecha.isEmpty()) {
            System.out.println("Error: La fecha no puede ser null o vacía.");
        } else {
            setFecha(nuevaFecha);
            System.out.println("Fecha actualizada correctamente.");
        }
    }
    public void actualizarCategoria(String nuevaCategoria) {
        if (nuevaCategoria == null || nuevaCategoria.trim().isEmpty()) {
            System.out.println("Error: La categoría no puede ser null o vacía.");
        } else {
            setCategoria(nuevaCategoria);
            System.out.println("Categoría actualizada correctamente.");
        }
    }
    //
    // Setters y Getters
    //
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        if (titulo != null && !titulo.trim().isEmpty()) {
            this.titulo = titulo;
        } else {
            System.out.println("Advertencia: El título no puede ser null o vacío.");
        }
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        if (autor != null && !autor.trim().isEmpty()) {
            this.autor = autor;
        } else {
            System.out.println("Advertencia: El autor no puede ser null o vacío.");
        }
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        if (categoria != null && !categoria.trim().isEmpty()) {
            this.categoria = categoria;
        } else {
            System.out.println("Advertencia: La categoría no puede ser null o vacía.");
        }
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        if (fecha != null && !fecha.trim().isEmpty()) {
            this.fecha = fecha;
        } else {
            System.out.println("Advertencia: fecha no puede ser null o vacío.");
        }
    }
    public String getFormato() {
        return formato;
    }
    public void setFormato(String formato) {
        if (formato != null && !formato.trim().isEmpty()) {
            this.formato = formato;
        } else {
            System.out.println("Advertencia: El formato no puede ser null o vacío.");
        }
    }
    public String getCurso() {
        return curso;
    }
    public void setCurso(String curso) {
        if (curso != null && !curso.trim().isEmpty()) {
            this.curso = curso;
        } else {
            System.out.println("Advertencia: El curso no puede ser null o vacío.");
        }
    }
    public boolean isEsVisible() {
        return esVisible;
    }
    public void setEsVisible(boolean esVisible) {
        this.esVisible = esVisible;
    }
}
