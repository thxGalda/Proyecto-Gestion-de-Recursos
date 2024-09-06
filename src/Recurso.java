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

    // Metodo para cambiar la visibilidad del recurso, ocultar o mostra
    public void cambiarVisibilidad(boolean esVisible) {
        setEsVisible(esVisible);
        System.out.println("Visibilidad del recurso actualizada.");
    }

    // Metodo para comparar dos recursos por título
    public boolean compararPorTitulo(Recurso otro) {
        return this.titulo.equals(otro.getTitulo());
    }

    // Metodo para verificar si dos recursos tienen el mismo autor
    public boolean esMismoAutor(Recurso otro) {
        return this.autor.equals(otro.getAutor());
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

    // Metodo para actualizar categoria
    public void actualizarCategoria(String nuevaCategoria) {
        if (nuevaCategoria == null || nuevaCategoria.isEmpty()) {
            System.out.println("Error: La fecha no puede ser null o vacía.");
        } else {
            setCategoria(nuevaCategoria);
            System.out.println("Fecha actualizada correctamente.");
        }
    }
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