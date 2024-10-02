package paqueteMain;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        // Crear instancia de Menu
        Menu menu = new Menu();
        
        // Llamar al método inicializarCursos
        inicializarCursos(menu);
        
        // Iniciar el menú principal
        menu.iniciar();
    }
    public static void inicializarCursos(Menu menu) {
    	List<Profesor> profesores = new ArrayList<>();
        List<Curso> cursos = new ArrayList<>();
        List<Carpeta> carpetas = new ArrayList<>();
        List<Recurso> recursos = new ArrayList<>();
        // Profesores
        Profesor profesorMatematica = new Profesor("Dr. Juan Pérez", "12345678-9", "juan.perez@universidad.edu", "Matemáticas");
        Profesor profesorFisica = new Profesor("Dr. Laura Gómez", "98765432-1", "laura.gomez@universidad.edu", "Física");
        Profesor profesorProgramacion = new Profesor("Ing. Carlos Ruiz", "45678901-2", "carlos.ruiz@universidad.edu", "Programación");

        // Crear estudiantes
        Estudiante estudiante1 = new Estudiante("Ana Pérez", 1, "12345678-9", "ana@example.com");
        Estudiante estudiante2 = new Estudiante("Luis Soto", 2, "87654321-0", "luis@example.com");
        Estudiante estudiante3 = new Estudiante("Maria Diaz", 3, "11223344-5", "maria@example.com");
        Estudiante estudiante4 = new Estudiante("Carlos Rojas", 4, "55667788-6", "carlos@example.com");
        Estudiante estudiante5 = new Estudiante("Laura Gomez", 5, "99887766-1", "laura@example.com");

        // Crear los cursos
        Curso cursoMatematica = new Curso("Matemática 1", "Curso de matemáticas avanzadas", profesorMatematica);
        Curso cursoFisica = new Curso("Física 2", "Curso de física moderna", profesorFisica);
        Curso cursoProgramacion = new Curso("Programación Avanzada", "Curso avanzado de programación en Java", profesorProgramacion);

        Carpeta carpeta1 = new Carpeta("MAT 1001-A Ayudantía", 10001, true);
        Carpeta carpeta2 = new Carpeta("MAT 1001-A Control", 10051, true);
        Carpeta carpeta3 = new Carpeta("MAT 1001-A Clases Unidad 3", 10383);
        Carpeta carpeta4 = new Carpeta("FIS-1002-2/A Ejercicios Prueba 2", 10022, false);
        Carpeta carpeta5 = new Carpeta("FIS-1002-2/A Certamenes", 10328, false);
        Carpeta carpeta6 = new Carpeta("FIS-1002-2/A Clases 2021 2do Semestre", 10327, true);
        Carpeta carpeta7 = new Carpeta("FIS-1002-2/A Clases 2024 1er Semestre", 10346, true);
        Carpeta carpeta8 = new Carpeta("ICI-2121-3 Proyectos 2023", 20032, false);
        Carpeta carpeta9 = new Carpeta("ICI-2121-3 Controles Ayudantía", 65702, true);
        Carpeta carpeta10 = new Carpeta("ICI-2121-3 Guias 5", 10001);

        // Carpeta 1: "MAT 1001-A Ayudantía"
        Recurso recurso1_1 = new Recurso("Ayudantía 1", "Profesor A", "Matemáticas", "2023-05-10", "PDF", "MAT 1001-A", true);
        Recurso recurso1_2 = new Recurso("Ayudantía 2", "Profesor A", "Matemáticas", "2023-05-17", "DOCX", "MAT 1001-A", true);
        Recurso recurso1_3 = new Recurso("Resumen Unidad 1", "Profesor B", "Matemáticas", "2023-05-20", "PPTX");

        // Carpeta 2: "MAT 1001-A Control"
        Recurso recurso2_1 = new Recurso("Control 1", "Profesor A", "Matemáticas", "2023-06-10", "PDF");
        Recurso recurso2_2 = new Recurso("Control 2", "Profesor B", "2023-06-24", "DOCX");
        Recurso recurso2_3 = new Recurso("Solución Control 1", "Profesor A", "PDF");

        // Carpeta 3: "MAT 1001-A Clases Unidad 3"
        Recurso recurso3_1 = new Recurso("Clase 3.1", "Profesor A", "Matemáticas", "2023-07-01", "PDF", "MAT 1001-A", true);
        Recurso recurso3_2 = new Recurso("Clase 3.2", "Profesor A", "2023-07-08", "VIDEO");
        Recurso recurso3_3 = new Recurso("Material Adicional Unidad 3", "PPTX");

        // Carpeta 4: "FIS-1002-2/A Ejercicios Prueba 2"
        Recurso recurso4_1 = new Recurso("Ejercicios Prueba 2", "Profesor C", "Física", "2023-08-15", "PDF", "FIS-1002-2/A", false);
        Recurso recurso4_2 = new Recurso("Solución Ejercicios", "Profesor C", "Física", "2023-08-22", "DOCX", "FIS-1002-2/A", false);
        Recurso recurso4_3 = new Recurso("Ejercicios Extra", "PDF");

        // Carpeta 5: "FIS-1002-2/A Certamenes"
        Recurso recurso5_1 = new Recurso("Certamen 1", "Profesor C", "Física", "2023-09-01", "PDF");
        Recurso recurso5_2 = new Recurso("Certamen 2", "Profesor C", "Física", "2023-09-15", "DOCX");
        Recurso recurso5_3 = new Recurso("Certamen 3", "Profesor C", "Física", "2023-09-29", "PDF", "FIS-1002-2/A", false);

        // Carpeta 6: "FIS-1002-2/A Clases 2021 2do Semestre"
        Recurso recurso6_1 = new Recurso("Clase 2.1", "Profesor D", "Física", "2021-10-01", "PDF", "FIS-1002-2/A", true);
        Recurso recurso6_2 = new Recurso("Clase 2.2", "Profesor D", "2021-10-08", "PPTX");
        Recurso recurso6_3 = new Recurso("Notas de Clase 2", "PDF");

        // Carpeta 7: "FIS-1002-2/A Clases 2024 1er Semestre"
        Recurso recurso7_1 = new Recurso("Clase 1.1", "Profesor E", "Física", "2024-03-01", "PDF", "FIS-1002-2/A", true);
        Recurso recurso7_2 = new Recurso("Clase 1.2", "Profesor E", "2024-03-08", "DOCX");
        Recurso recurso7_3 = new Recurso("Resumen Unidad 1", "PPTX");

        // Carpeta 8: "ICI-2121-3 Proyectos 2023"
        Recurso recurso8_1 = new Recurso("Proyecto 1", "Profesor F", "Ingeniería", "2023-11-01", "PDF", "ICI-2121-3", false);
        Recurso recurso8_2 = new Recurso("Proyecto 2", "Profesor F", "Ingeniería", "2023-11-15", "DOCX", "ICI-2121-3", false);
        Recurso recurso8_3 = new Recurso("Proyecto Final", "PPTX");

        // Carpeta 9: "ICI-2121-3 Controles Ayudantía"
        Recurso recurso9_1 = new Recurso("Control Ayudantía 1", "Profesor G", "Ingeniería", "2023-09-01", "PDF");
        Recurso recurso9_2 = new Recurso("Control Ayudantía 2", "Profesor G", "Ingeniería", "2023-09-15", "DOCX");
        Recurso recurso9_3 = new Recurso("Solución Control Ayudantía", "Profesor G", "PDF");

        // Carpeta 10: "ICI-2121-3 Guias 5"
        Recurso recurso10_1 = new Recurso("Guía 1", "Profesor H", "Ingeniería", "2023-07-01", "PDF");
        Recurso recurso10_2 = new Recurso("Guía 2", "Profesor H", "2023-07-15", "DOCX");
        Recurso recurso10_3 = new Recurso("Guía 3", "PPTX");

        // Añadir recursos a las carpetas
        saveProfesoresToCSV(profesores);
        saveCursosToCSV(cursos);
        saveCarpetasToCSV(carpetas);
        saveRecursosToCSV(recursos);

        // Agregar cursos al menú
        menu.agregarCurso(cursoMatematica);
        menu.agregarCurso(cursoFisica);
        menu.agregarCurso(cursoProgramacion);
    }
    public static void saveProfesoresToCSV(List<Profesor> profesores) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("profesores.csv"))) {
            writer.write("Nombre,RUT,Email,Asignatura\n");
            for (Profesor p : profesores) {
                writer.write(p.nombre + "," + p.rut + "," + p.email + "," + p.asignatura + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveCursosToCSV(List<Curso> cursos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cursos.csv"))) {
            writer.write("Nombre,Descripcion,Profesor\n");
            for (Curso c : cursos) {
                writer.write(c.nombre + "," + c.descripcion + "," + c.profesor.nombre + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveCarpetasToCSV(List<Carpeta> carpetas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("carpetas.csv"))) {
            writer.write("Nombre,Codigo,Habilitada\n");
            for (Carpeta c : carpetas) {
                writer.write(c.nombre + "," + c.codigo + "," + c.habilitada + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveRecursosToCSV(List<Recurso> recursos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("recursos.csv"))) {
            writer.write("Nombre,Autor,Materia,Fecha,Tipo,Carpeta,Habilitado\n");
            for (Recurso r : recursos) {
                writer.write(r.nombre + "," + r.autor + "," + r.materia + "," + r.fecha + "," + r.tipo + "," + r.carpeta + "," + r.habilitado + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
