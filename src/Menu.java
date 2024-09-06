import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Menu {
	private Usuario usuarioActual;
    private Scanner scanner;
    private List<Curso> cursos;  // Lista de cursos disponibles
    private List <Estudiante> estudiantes; // Lista de estudiantes

    // Constructor
 // Constructor
    public Menu() {
        scanner = new Scanner(System.in);
        cursos = new ArrayList<>();  // Inicializamos la lista de cursos
        estudiantes = new ArrayList<>();   // Inicializamos la lista de estudiantes
        inicializarCursos();  // Cargar cursos iniciales
    }

    // Método para iniciar el menú principal
    public void iniciar() {
        System.out.println("Bienvenido al Sistema de Gestión de Recursos Educativos Digitales");
        System.out.println("Por favor, ingrese sus datos para continuar.");

        // Recoger datos del usuario y determinar si es Estudiante o Profesor
        usuarioActual = ingresarDatosUsuario();

        // Menú principal
        while (true) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Configuración");
            System.out.println("2. Ficha Personal");
            System.out.println("3. Revisar Cursos");
            System.out.println("4. Buscar Recursos");

            if (usuarioActual instanceof Profesor) {
                System.out.println("5. Añadir o Editar Curso");
            }

            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del scanner

            switch (opcion) {
                case 1:
                    configurarUsuario();
                    break;
                case 2:
                    mostrarFichaPersonal();
                    break;
                case 3:
                    revisarCursos();
                    break;
                case 4:
                    buscarRecursos();
                    break;
                case 5:
                    if (usuarioActual instanceof Profesor) {
                        editarCurso();
                    } else {
                        System.out.println("Opción no válida.");
                    }
                    break;
                case 6:
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
                    break;
            }
        }
    }
    // Método auxiliar para generar un ID de usuario aleatorio
    private int generarIdUsuario() {
        // Usar Random para generar un entero aleatorio de hasta 6 dígitos
        Random random = new Random();
        return random.nextInt(999999);  // Genera un número entre 0 y 999999
    }

    // Método para ingresar los datos del usuario
    private Usuario ingresarDatosUsuario() {
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Ingrese su rut: ");
        String rut = scanner.nextLine();
        
        System.out.print("Ingrese su correo electrónico: ");
        String correo = scanner.nextLine();

        System.out.print("Seleccione su tipo de usuario (1. Estudiante / 2. Profesor): ");
        int tipoUsuario = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer del scanner

        // Generar un ID aleatorio para el usuario
        int idUsuario = generarIdUsuario();

        if (tipoUsuario == 1) {
            System.out.print("Ingrese su paralelo (Si no tiene, ingrese 0): ");
            int paralelo = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del scanner

            // Crear un nuevo Estudiante con la ID generada
            Estudiante estudiante = new Estudiante(nombre, rut, correo, paralelo);
            estudiante.setId(idUsuario);  // Asignar ID
            return estudiante;
        } else if (tipoUsuario == 2) {
            System.out.print("Ingrese su área de especialización: ");
            String departamento = scanner.nextLine();

            // Crear un nuevo Profesor con la ID generada y asignar permiso especial
            Profesor profesor = new Profesor(nombre, rut, correo, departamento);
            profesor.setId(idUsuario);  // Asignar ID
            profesor.setPermisoEspecial(true);  // Otorgar permiso especial automáticamente
            return profesor;
        } else {
            System.out.println("Opción no válida, se creará un usuario por defecto como Estudiante.");
            Estudiante estudiantePorDefecto = new Estudiante(nombre, rut, correo);
            estudiantePorDefecto.setId(idUsuario);  // Asignar ID
            return estudiantePorDefecto;
        }
    }

    // Método para la configuración del usuario
    private void configurarUsuario() {
        System.out.println("\n--- Configuración ---");
        System.out.println("1. Cambiar nombre");
        System.out.println("2. Cambiar correo electrónico");
        System.out.println("3. Cambiar contraseña");
        System.out.println("4. Regresar");
        System.out.print("Seleccione una opción: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer del scanner

        switch (opcion) {
            case 1:
                System.out.print("Ingrese el nuevo nombre: ");
                String nuevoNombre = scanner.nextLine();
                usuarioActual.setNombre(nuevoNombre);
                break;
            case 2:
                System.out.print("Ingrese el nuevo correo electrónico: ");
                String nuevoCorreo = scanner.nextLine();
                usuarioActual.setEmail(nuevoCorreo);
                break;
            case 3:
            	while(true) {
	            	System.out.println("Ingrese la contraseña actual: ");
	                String contraseña = scanner.nextLine();
	                if(contraseña == usuarioActual.getContrasena()) {
	                	System.out.println("Ingrese la contraseña nueva: ");
	                	String nuevaContraseña = scanner.nextLine();
	                    usuarioActual.setContrasena(nuevaContraseña);
	                    break;
	                }else {
	                	System.out.println("La contraseña no coincide");
	                }
            	}
            default:
                System.out.println("Opción no válida.");
                break;
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
    // Método para buscar recursos en carpeta
    private void buscarRecursos() {
        System.out.println("Buscar por: 1. Autor 2. Categoría 3. Fecha");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        System.out.print("Ingrese el término de búsqueda: ");
        String terminoBusqueda = scanner.nextLine();

        switch (opcion) {
            case 1:
                // Método para buscar por autor
                break;
            case 2:
                // Método para buscar por categoría
                break;
            case 3:
                // Método para buscar por fecha
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }
    
    // Método para mostrar y revisar cursos disponibles
    private void revisarCursos() {
        
        // Suponiendo que tenemos una lista de cursos ya existente
        //List<Curso> cursos = obtenerListaDeCursos(); // Método que obtiene la lista de cursos
        if (cursos.isEmpty()) {
            System.out.println("No hay cursos disponibles.");
            return;
        }
        System.out.println("\n--- Lista de Cursos ---");
            for (int i = 0; i < cursos.size(); i++) {
                Curso curso = cursos.get(i);
                System.out.println((i + 1) + ". " + curso.getNombre() + " - " + curso.getDescripcion());
            }
	    System.out.print("Selecciona un curso (número): ");
	    int opcionCurso = scanner.nextInt();
	    
	    if (opcionCurso < 1 || opcionCurso > cursos.size()) {
	        System.out.println("Opción no válida.");
	        return;
	    }
	    
	    // Selecciona el curso correspondiente
	    Curso cursoSeleccionado = cursos.get(opcionCurso - 1);
	    
	    // Mostrar submenú del curso seleccionado
	    mostrarSubmenuCurso(cursoSeleccionado);
    }
    //
    // -------------------------------------------------- SUBMENUS ----------------------------------
    //
    // Método exclusivo para profesores
    private void editarCurso() {
        System.out.println("Seleccione el curso que desea editar:");
        
        // Aquí listarías los cursos para que el profesor seleccione cuál editar
        for (int i = 0; i < cursos.size(); i++) {
            System.out.println((i + 1) + ". " + cursos.get(i).getNombre());
        }
        
        int opcionCurso = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        Curso curso = cursos.get(opcionCurso - 1);  // Obtener el curso seleccionado
        int opcion;
        do {
	        System.out.println("Opciones de edición del curso:");
	        System.out.println("1. Actualizar descripción del curso");
	        System.out.println("2. Asignar Profesor");
	        System.out.println("3. Agregar Carpeta");
	        System.out.println("4. Eliminar Carpeta");
	        System.out.println("5. Agregar Estudiante");
	        System.out.println("6. Eliminar Estudiante");
	        System.out.println("7. Cargar Recurso");
	        System.out.println("0. Volver al menú principal");
	        System.out.print("Selecciona una opción: ");
	
	        opcion = scanner.nextInt();
	        scanner.nextLine();  // Consumir el salto de línea
	
	        switch (opcion) {
	            case 1:  // Actualizar descripción del curso
	                System.out.print("Ingrese la nueva descripción del curso: ");
	                String nuevaDescripcion = scanner.nextLine();
	                curso.actualizarDescripcion(nuevaDescripcion);
	                System.out.println("Descripción actualizada.");
	                break;
	            
	            case 2:  // Asignar Profesor
	                System.out.print("Ingrese el nombre del nuevo profesor: ");
	                String nombreProfesor = scanner.nextLine();
	                System.out.print("Ingrese el RUT del profesor: ");
	                String rutProfesor = scanner.nextLine();
	                System.out.print("Ingrese el email del profesor: ");
	                String emailProfesor = scanner.nextLine();
	                System.out.print("Ingrese la especialidad del profesor: ");
	                String especialidadProfesor = scanner.nextLine();
	                
	                Profesor nuevoProfesor = new Profesor(nombreProfesor, rutProfesor, emailProfesor, especialidadProfesor);
	                curso.asignarProfesor(nuevoProfesor);
	                System.out.println("Profesor asignado con éxito.");
	                break;
	            
	            case 3:  // Agregar Carpeta
	                System.out.print("Ingrese el nombre de la nueva carpeta: ");
	                String nombreCarpeta = scanner.nextLine();
	                System.out.print("Ingrese el ID de la nueva carpeta: ");
	                int idCarpeta = scanner.nextInt();
	                System.out.print("¿Es la carpeta pública? (true/false): ");
	                boolean esPublica = scanner.nextBoolean();
	                scanner.nextLine();  // Consumir el salto de línea
	                
	                Carpeta nuevaCarpeta = new Carpeta(nombreCarpeta, idCarpeta, esPublica);
	                curso.agregarCarpeta(nuevaCarpeta);
	                System.out.println("Carpeta agregada con éxito.");
	                break;
	            
	            case 4:  // Eliminar Carpeta
	                System.out.print("Ingrese el nombre o el ID de la carpeta a eliminar: ");
	                String entradaEliminarCarpeta = scanner.nextLine();
	                try {
	                    // Intentar convertir la entrada a un número
	                    int idCarpetaEliminar = Integer.parseInt(entradaEliminarCarpeta);
	                    curso.eliminarCarpeta(idCarpetaEliminar);
	                    System.out.println("Carpeta con ID " + idCarpetaEliminar + " eliminada.");
	                } catch (NumberFormatException e) {
	                    // Si no es un número, eliminar por nombre
	                    curso.eliminarCarpeta(entradaEliminarCarpeta);
	                }
	                break;
	            case 5: // Agregar Estudiante
	            	break;
	            case 6 : // Eliminar Estudiante
	            	System.out.print("Ingrese el nombre o el ID del estudiante a eliminar: ");
	                String entradaEliminarEstudiante = scanner.nextLine();
	                try {
	                    // Intentar convertir la entrada a un número
	                    int idEstudianteEliminar = Integer.parseInt(entradaEliminarEstudiante);
	                    curso.eliminarEstudiante(idEstudianteEliminar);
	                } catch (NumberFormatException e) {
	                    // Si no es un número, eliminar por nombre
	                    curso.eliminarEstudiante(entradaEliminarEstudiante);
	                }
	                break;
	            case 7: // Cargar Recurso
	            	// pide un id de carpeta y un objeto tipo recurso y se llama a la funcion cargarRecurso de la clase curso
	            	// tiene 4 distintos metodos sobrecargados, subir un solo objeto recurso por nombre de carpeta o id de carpeta, o subir una lista de recursos por nombre de carpeta o id de carpeta
	            	break;
	            case 0:
	                System.out.println("Volviendo al Menú Principal...");
	                break;
	            default:
	                System.out.println("Opción no válida. Inténtalo de nuevo.");
	        	}
	    	} while (opcion != 0);
    }
    public void mostrarSubmenuCurso(Curso curso) {
        int opcion;
        
        do {
            System.out.println("\n=== Menú del Curso: " + curso.getNombre() + " ===");
            System.out.println("1. Revisar Carpetas");
            System.out.println("2. Mostrar Estudiantes");
            System.out.println("3. Buscar Estudiante");
            System.out.println("4. Ordenar Carpetas");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea
            
            switch (opcion) {
                case 1:
                    curso.mostrarCarpetas();
                    break;
                case 2:
                    curso.mostrarEstudiantesInscritos();
                    break;
                case 3:
                    System.out.print("Ingrese el nombre o el id del estudiante: ");
                    String entradaEstudiante = scanner.nextLine();
                    Estudiante estudianteAux;
                    // Intentar convertir la entrada a un número
                    try {
                        int idEstudiante = Integer.parseInt(entradaEstudiante);
                        // Si la conversión es exitosa, buscar por ID
                        estudianteAux = curso.buscarEstudiante(idEstudiante);
                    } catch (NumberFormatException e) {
                        // Si no es un número, buscar por nombre
                    	estudianteAux = curso.buscarEstudiante(entradaEstudiante);
                    }
                    
                    if (estudianteAux != null) {
                        System.out.println("\nEstudiante encontrado: " + estudianteAux);
                    } else {
                        System.out.println("\nEstudiante no encontrado.");
                    }
                    break;
                case 4:
                    System.out.println("1. Ordenar por nombre");
                    System.out.println("2. Ordenar por visibilidad");
                    int opcionOrden = scanner.nextInt();
                    if (opcionOrden == 1) {
                        curso.ordenarCarpetasPorNombre();
                    } else if (opcionOrden == 2) {
                        curso.ordenarCarpetasPorVisibilidad();
                    } else {
                        System.out.println("Opción no válida.");
                    }
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);
    }
   
}
