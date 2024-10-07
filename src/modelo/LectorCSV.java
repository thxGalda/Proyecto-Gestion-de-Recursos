package modelo;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.net.URL;

public class LectorCSV {
	private List<Profesor> profesores;
    private List<Curso> cursos;

    public LectorCSV() {
        profesores = new ArrayList<>();
        cursos = new ArrayList<>();
    }
    
    public void leerArchivoCSV(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Procesa la línea leída
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
    }
    
    public void cargarArchivosCSV(String[] archivosCSV) {
        // Crear listas para almacenar los objetos leídos
        List<Carpeta> carpetas = new ArrayList<>();
        List<Estudiante> estudiantes = new ArrayList<>();
        List<Recurso> recursos = new ArrayList<>();
    
        // Leer archivos CSV y agregar objetos a las listas
        for (String archivoCSV : archivosCSV) {
            if (archivoCSV.contains("Profesores")) {
                profesores.addAll(cargarProfesoresDesdeCSV(archivoCSV));
                if(profesores.isEmpty()) System.out.println("No se cargaron los profesores");
            } else if (archivoCSV.contains("Carpetas")) {
                carpetas.addAll(cargarCarpetasDesdeCSV(archivoCSV));
            } else if (archivoCSV.contains("Cursos")) {
                cursos.addAll(cargarCursosDesdeCSV(archivoCSV, profesores));
            } else if (archivoCSV.contains("Estudiantes")) {
                estudiantes.addAll(cargarEstudiantesDesdeCSV(archivoCSV, cursos));
            } else if (archivoCSV.contains("Recursos")) {
                recursos.addAll(cargarRecursosDesdeCSV(archivoCSV));
            }
        }
    
        // Agregar objetos a la lista de cursos
        for (Curso curso : cursos) {
        	System.out.println("Se está procesando el curso: " + curso.getNombre());
            curso.setCarpetas(carpetas);
            // recorrer profesores y elegir que le corresponde
            
            // crear lista solo los estudiante que coinciden con curso
            curso.setEstudiantes(estudiantes);
        }
    
        // Agregar objetos a la lista de carpetas
        for (Carpeta carpeta : carpetas) {
            carpeta.agregarRecurso(recursos);
        }

    }
    
    public List<Curso> cargarCursosDesdeCSV(String archivoCSV, List<Profesor> profesores) {
        List<Curso> cursos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            br.readLine(); // Saltar la primera línea (encabezados)
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";"); // Dividir la línea en campos
                Profesor profesor = null;
                for (Profesor p : profesores) {
                    if (p.getNombre().equals(campos[2])) {
                        profesor = p;
                        break;
                    }
                }
                Curso curso = new Curso(campos[0], campos[1], profesor, Integer.parseInt(campos[3]));
                cursos.add(curso);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + archivoCSV);
        }
        return cursos;
    }
    public List<Profesor> cargarProfesoresDesdeCSV(String archivoCSV) {
        List<Profesor> profesores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            br.readLine(); // Saltar la primera línea (encabezados)
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";"); // Dividir la línea en campos
                Profesor profesor = new Profesor(campos[0], Integer.parseInt(campos[1]), campos[2], campos[3], campos[4], Boolean.parseBoolean(campos[5]), campos[6]);
                profesores.add(profesor);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + archivoCSV);
        }
        return profesores;
    }
    public List<Estudiante> cargarEstudiantesDesdeCSV(String archivoCSV, List<Curso> cursos) {
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
                Estudiante estudiante = new Estudiante(campos[0], Integer.parseInt(campos[1]), campos[2], campos[3], campos[4], Boolean.parseBoolean(campos[5]), Integer.parseInt(campos[7]));
                estudiantes.add(estudiante);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + archivoCSV);
        }
        return estudiantes;
    }
    public List<Recurso> cargarRecursosDesdeCSV(String archivoCSV) {
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
    public List<Carpeta> cargarCarpetasDesdeCSV(String archivoCSV) {
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

    
    public List<Profesor> getProfesores() {
        return profesores;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

}
