import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Menu {
	private Usuario usuarioActual;
    private Scanner scanner;
    private List<Curso> cursos;  // Lista de cursos disponibles
    private List <Estudiante> estudiantes; // Lista de estudiantes auxiliares

    // Constructor
 // Constructor
    public Menu() {
        scanner = new Scanner(System.in);
        this.cursos = new ArrayList<>();  // Inicializamos la lista de cursos
        this.estudiantes = new ArrayList<>();   // Inicializamos la lista de estudiantes
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
    // Método para iniciar el menú principal
    public void iniciar() {
        System.out.println("Bienvenido al Sistema de Gestión de Recursos Educativos Digitales");
        System.out.println("Por favor, ingrese sus datos para continuar.");

        // Recoger datos del usuario y determinar si es Estudiante o Profesor
        usuarioActual = ingresarDatosUsuario();
        cambiarContraseña(); // metodo para establecer contraseña

        // Menú principal
        while (true) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Configuración");
            System.out.println("2. Ficha Personal");
            System.out.println("3. Revisar Cursos");

            if (usuarioActual instanceof Profesor) {
                System.out.println("4. Modificar Curso");
            }

            System.out.println("0. Salir");
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
                    if (usuarioActual instanceof Profesor) {
                        editarCurso();
                    } else {
                        System.out.println("Opción no válida.");
                    }
                    break;
                case 0:
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
    private void cambiarContraseña(){
        while (true) {
            // Verificar si el usuario no tiene contraseña establecida
            String contraseñaActual = usuarioActual.getContrasena();
            if (contraseñaActual == null) {
                // Caso donde no hay contraseña establecida
                System.out.println("No tiene una contraseña establecida. Ingrese una nueva contraseña: ");
                String nuevaContraseña = scanner.nextLine();
                usuarioActual.setContrasena(nuevaContraseña);
                System.out.println("Contraseña establecida correctamente.");
                break;
            } else {
                // Caso donde ya hay una contraseña establecida
                System.out.println("Ingrese la contraseña actual: ");
                String contraseñaIngresada = scanner.nextLine();

                if (contraseñaIngresada.equals(contraseñaActual)) {
                    System.out.println("Ingrese la nueva contraseña: ");
                    String nuevaContraseña = scanner.nextLine();
                    usuarioActual.setContrasena(nuevaContraseña);
                    System.out.println("Contraseña cambiada correctamente.");
                    break;
                } else {
                    System.out.println("La contraseña actual no es correcta. Intente nuevamente.");
                }
            }
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
        System.out.println("\n--- Lista de Cursos ---\n");
            for (int i = 0; i < cursos.size(); i++) {
                Curso curso = cursos.get(i);
                System.out.println((i + 1) + ". " + curso.getNombre() + " - " + curso.getDescripcion());
            }
	    System.out.print("Selecciona un curso (número): \n");
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
        System.out.println("Seleccione el curso que desea editar:\n");
        
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
	        System.out.println("1. Actualizar Descripción del Curso");
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
                    System.out.print("Ingrese el nombre del estudiante: ");
                    String nombreEstudiante = scanner.nextLine();
                    
                    System.out.print("Ingrese el rut del estudiante: ");
                    String rutEstudiante = scanner.nextLine();
                    
                    System.out.print("Ingrese el correo electrónico del estudiante: ");
                    String correoEstudiante = scanner.nextLine();
            
                    System.out.print("Seleccione el paralelo donde quiera agregar al estudiante: ");
                    int paraleloEstudiante = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer del scanner
                    // Generar un ID aleatorio para el usuario
                    int idEstudiante = generarIdUsuario();
                    // Constructor 3:
                    Estudiante nuevoEstudiante = new Estudiante(nombreEstudiante, idEstudiante, rutEstudiante, correoEstudiante, paraleloEstudiante);
                    curso.agregarEstudiante(nuevoEstudiante);
	            	break;
	            case 6 : // Eliminar Estudiante por ID o Nombre
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
	            case 7: // Cargar Recurso (Llama al método sobrecargado cargar recurso correspondiente de clase curso)
                    System.out.println("¿Desea cargar un solo recurso o una lista de recursos?");
                    System.out.println("1. Un solo recurso");
                    System.out.println("2. Lista de recursos");
                    int opcionRecurso = scanner.nextInt();
                    scanner.nextLine(); // limpiar el buffer

                    System.out.println("Ingrese el nombre o id de la carpeta:");
                    String entradaCarpeta = scanner.nextLine();
                    // Intentar convertir la entrada a un número
                    try {
                        int idCarpetaAgregar = Integer.parseInt(entradaCarpeta);
                        if (opcionRecurso == 1) {
                            Recurso recurso = Recurso.crearRecurso(); // Método para crear un recurso
                            if (recurso != null) {
                                curso.cargarRecurso(idCarpetaAgregar, recurso); // Llama al método sobrecargado de clase curso
                            } else {
                                System.out.println("Error al crear el recurso.");
                            }
                        } else if (opcionRecurso == 2) {
                            List<Recurso> listaRecursos = Carpeta.crearListaRecursos(); // Método para crear multiples recursos
                            if (listaRecursos != null && !listaRecursos.isEmpty()) {
                                curso.cargarRecurso(idCarpetaAgregar, listaRecursos);// Llama al método sobrecargado de clase curso
                            } else {
                                System.out.println("Error al crear la lista de recursos.");
                            }
                        } else {
                            System.out.println("Opción no válida.");
                        } 
                    } catch (NumberFormatException e) {
                        if (opcionRecurso == 1) {
                            Recurso recurso = Recurso.crearRecurso(); // Método para crear un recurso
                            if (recurso != null) {
                                curso.cargarRecurso(entradaCarpeta, recurso); // Llama al método sobrecargado de clase carpeta
                            } else {
                                System.out.println("Error al crear el recurso.");
                            }
                        } else if (opcionRecurso == 2) {
                            List<Recurso> listaRecursos = Carpeta.crearListaRecursos();  // Método para crear multiples recursos
                            if (listaRecursos != null && !listaRecursos.isEmpty()) {
                                curso.cargarRecurso(entradaCarpeta, listaRecursos); // Llama al método sobrecargado de clase curso
                            } else {
                                System.out.println("Error al crear la lista de recursos.");
                            }
                        } else {
                            System.out.println("Opción no válida.");
                        } 
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
    // -------------------------------------------------- SUBMENU ----------------------------------
    // Metodo para el submenu del curso
    public void mostrarSubmenuCurso(Curso curso) {
        int opcion;
        
        do {
            System.out.println("\n=== Menú del Curso: " + curso.getNombre() + " ===\n");
            System.out.println("1. Revisar Carpetas");
            System.out.println("2. Mostrar Estudiantes");
            System.out.println("3. Buscar Estudiante");
            System.out.println("4. Ordenar Carpetas");
            System.out.println("5. Buscar Recursos");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Selecciona una opción: \n");
            opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea
            
            switch (opcion) {
                case 1:
                    mostrarSubmenuCarpetas(curso);
                    break;
                case 2: // Mostrar Estudiantes
                    curso.mostrarEstudiantesInscritos();
                    break;
                case 3: // Buscar Estudiante
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
                case 4: // Ordenar Carpetas
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
                case 5: // Buscar recursos 
                    System.out.println("¿Cómo desea buscar el recurso?\n");
                        System.out.println("1. Por fecha");
                        System.out.println("2. Por categoría");
                        System.out.println("3. Por autor");
                        System.out.println("4. Por nombre");
                        System.out.println("Seleccione una opción: \n");
                        int opcionBusqueda = scanner.nextInt();
                        scanner.nextLine(); // Limpiar el buffer
                    
                        // Variable para guardar el criterio de búsqueda
                        String criterioBusqueda = "";
                    
                        switch(opcionBusqueda) {
                            case 1:
                                System.out.println("Ingrese la fecha (formato: yyyy-MM-dd):");
                                criterioBusqueda = scanner.nextLine();
                                break;
                            case 2:
                                System.out.println("Ingrese la categoría:");
                                criterioBusqueda = scanner.nextLine();
                                break;
                            case 3:
                                System.out.println("Ingrese el autor:");
                                criterioBusqueda = scanner.nextLine();
                                break;
                            case 4:
                                System.out.println("Ingrese el nombre del recurso:");
                                criterioBusqueda = scanner.nextLine();
                                break;
                            default:
                                System.out.println("Opción no válida.");
                                return;
                        }
                    curso.buscarRecursos(opcionBusqueda, criterioBusqueda); // Llama al método sobrecargado de clase curso, la cual llama a la de clase carpeta
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);
    }
    // -------------------------------------------------- SUBMENU ----------------------------------
    // Metodo para el submenu de carpeta
    public void mostrarSubmenuCarpetas(Curso curso){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSeleccione una carpeta para acceder:\n");
        
        List<Carpeta> carpetas = curso.getCarpetas(); // Mostrar todas las carpetas del objeto curso
        for (int i = 0; i < carpetas.size(); i++) {
            System.out.println((i + 1) + ". " + carpetas.get(i).getNombre());
        }
        
        int opcionCarpeta = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        
        if (opcionCarpeta < 1 || opcionCarpeta > carpetas.size()) {
            System.out.println("Opción inválida.");
            return;
        }
        Carpeta carpetaSeleccionada = carpetas.get(opcionCarpeta - 1);
        int opcion;

        do{
            System.out.println("\n--- Submenú de Carpeta ---\n");
            System.out.println("1. Mostrar Recursos");
            System.out.println("2. Buscar Recursos");
            if (usuarioActual instanceof Profesor) {
                System.out.println("3. Cargar Recurso");
                System.out.println("4. Eliminar Recurso");
                System.out.println("5. Cambiar nombre de la carpeta");
                System.out.println("6. Mover recurso a otra carpeta");
            }
            System.out.println("0. Regresar al curso");
            System.out.println("Seleccione una opción: \n");
    
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer
    
            switch (opcion) {   
                case 1: // Listar recursos
                    carpetaSeleccionada.listarRecursos();
                    break;
                case 2: // Buscar recurso por tipo
                    System.out.println("¿Cómo desea buscar el recurso?\n");
                        System.out.println("1. Por fecha");
                        System.out.println("2. Por categoría");
                        System.out.println("3. Por autor");
                        System.out.println("4. Por nombre");
                        System.out.println("Seleccione una opción: \n");
                        int opcionBusqueda = scanner.nextInt();
                        scanner.nextLine(); // Limpiar el buffer
                    
                        // Variable para guardar el criterio de búsqueda
                        String criterioBusqueda = "";
                    
                        switch(opcionBusqueda) {
                            case 1:
                                System.out.println("Ingrese la fecha (formato: yyyy-MM-dd):");
                                criterioBusqueda = scanner.nextLine();
                                break;
                            case 2:
                                System.out.println("Ingrese la categoría:");
                                criterioBusqueda = scanner.nextLine();
                                break;
                            case 3:
                                System.out.println("Ingrese el autor:");
                                criterioBusqueda = scanner.nextLine();
                                break;
                            case 4:
                                System.out.println("Ingrese el nombre del recurso:");
                                criterioBusqueda = scanner.nextLine();
                                break;
                            default:
                                System.out.println("Opción no válida.");
                                return;
                        }
                    carpetaSeleccionada.buscarRecursos(opcionBusqueda, criterioBusqueda); // Llama al método sobrecargado de búsqueda de clase carpeta
                    break;
                case 3: // Cargar Recurso
                    if (usuarioActual instanceof Profesor) {
                        System.out.println("¿Desea cargar un solo recurso o una lista de recursos?");
                        System.out.println("1. Un solo recurso");
                        System.out.println("2. Lista de recursos");
                        int opcionRecurso = scanner.nextInt();
                        scanner.nextLine(); // limpiar el buffer

                        if (opcionRecurso == 1) {
                            Recurso recurso = Recurso.crearRecurso(); // Método para crear un recurso
                            if (recurso != null) {
                                carpetaSeleccionada.agregarRecurso(recurso); // Llama al método sobrecargado de clase carpeta
                            } else {
                                System.out.println("Error al crear el recurso.");
                            }
                        } else if (opcionRecurso == 2) {
                            List<Recurso> listaRecursos = Carpeta.crearListaRecursos();  
                            if (listaRecursos != null && !listaRecursos.isEmpty()) {
                                carpetaSeleccionada.agregarRecurso(listaRecursos); // Llama al método sobrecargado de clase carpeta
                            } else {
                                System.out.println("Error al crear la lista de recursos.");
                            }
                        } else {
                            System.out.println("Opción no válida.");
                        }
                    } else {
                        System.out.println("Opción no válida.");
                    }
                    break;
                case 4: // Eliminar recurso
                    if (usuarioActual instanceof Profesor){
                        System.out.println("Ingrese el título del recurso a eliminar:");
                        String tituloEliminar = scanner.nextLine();
                        carpetaSeleccionada.eliminarRecurso(tituloEliminar);
                    }else{
                        System.out.println("Opción no válida.");
                    }
                    break;
                case 5:// Cambiar nombre de carpeta
                    if (usuarioActual instanceof Profesor){
                        System.out.println("Ingrese el nuevo nombre de la carpeta: \n");
                        String nuevoTitulo = scanner.nextLine();
                        carpetaSeleccionada.actualizarNombre(nuevoTitulo);
                    }else{
                        System.out.println("Opción no válida."); 
                    }
                    break;
                case 6: // Mover recurso a otra carpeta
                    if (usuarioActual instanceof Profesor){
                        System.out.println("Ingrese el título del recurso a mover:");
                        String tituloMover = scanner.nextLine();

                        System.out.println("Seleccione la carpeta destino para mover el recurso:\n"); // Ya tenemos acceso a las carpetas del curso
                        for (int i = 0; i < carpetas.size(); i++) { // Se recorren las carpetas
                            if (!carpetas.get(i).equals(carpetaSeleccionada)) { // Se verifica el nombre
                                System.out.println((i + 1) + ". " + carpetas.get(i).getNombre());
                            }
                        }
                        int opcionDestino = scanner.nextInt();
                        scanner.nextLine(); // Limpiar el buffer
                        if (opcionDestino < 1 || opcionDestino > carpetas.size() || carpetas.get(opcionDestino - 1).equals(carpetaSeleccionada)) {
                            System.out.println("Opción inválida.");
                            break;
                        }
                        Carpeta carpetaDestino = carpetas.get(opcionDestino - 1);
                        carpetaSeleccionada.moverRecurso(tituloMover, carpetaDestino);
                    }else{
                        System.out.println("Opción no válida."); 
                    }
                    break;
                case 0:
                    System.out.println("Volviendo al menú del curso...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }while(opcion != 0);
    }
    // -------------------------------------------------- SUBMENU ----------------------------------
   // Método para la configuración del usuario
   private void configurarUsuario() {
    System.out.println("\n--- Configuración ---");
    System.out.println("1. Cambiar nombre");
    System.out.println("2. Cambiar correo electrónico");
    System.out.println("3. Cambiar contraseña");
    System.out.println("0. Regresar al menú principal");
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
            cambiarContraseña();
        case 0:
            System.out.println("Volviendo al Menú Principal...");
            break;
        default:
            System.out.println("Opción no válida.");
            break;
    }
}
}
