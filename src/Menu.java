import java.util.Scanner;

public class Menu {
    private Usuario usuarioActual;
    private Scanner scanner;

    // Constructor
    public Menu() {
        scanner = new Scanner(System.in);
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
            System.out.println("4. Salir");
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
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
                    break;
            }
        }
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

        if (tipoUsuario == 1) {
            System.out.print("Ingrese su paralelo: ");
            int paralelo = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del scanner

            return new Estudiante(nombre, 1, rut, correo, paralelo);
        } else if (tipoUsuario == 2) {
            System.out.print("Ingrese su área de especialización: ");
            String departamento = scanner.nextLine();

            return new Profesor(nombre, 1, rut, correo, departamento);
        } else {
            System.out.println("Opción no válida, se creará un usuario por defecto como Estudiante.");
            return new Estudiante(nombre, correo, 0);
        }
    }

    // Método para la configuración del usuario
    private void configurarUsuario() {
        System.out.println("\n--- Configuración ---");
        System.out.println("1. Cambiar nombre");
        System.out.println("2. Cambiar correo electrónico");
        System.out.println("3. Mostrar información");
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
            case 2:
                System.out.println(usuarioActual.toString());
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    // Método para mostrar la ficha personal del usuario
    private void mostrarFichaPersonal() {
        System.out.println("\n--- Ficha Personal ---");
        System.out.println("Nombre: " + usuarioActual.getNombre());
        System.out.println("Correo Electrónico: " + usuarioActual.getCorreo());

        if (usuarioActual instanceof Estudiante) {
            Estudiante estudiante = (Estudiante) usuarioActual;
            System.out.println("Paralelo: " + estudiante.getParalelo());
        } else if (usuarioActual instanceof Profesor) {
            Profesor profesor = (Profesor) usuarioActual;
            System.out.println("Área de Especialización: " + profesor.getAreaEspecializacion());
        }
    }

    // Método para revisar los cursos disponibles
    private void revisarCursos() {
        System.out.println("\n--- Revisar Cursos ---");
        // Aquí se listarán y se gestionarán los cursos del usuario actual
        // (esta parte se desarrollará más adelante)
        System.out.println("Funcionalidad en desarrollo...");
    }
}