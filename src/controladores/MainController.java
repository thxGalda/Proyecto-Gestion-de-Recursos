package controladores;

import modelo.*;
import vistas.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.*;

import modelo.Carpeta;
import modelo.Curso;
import modelo.Menu;
import modelo.Usuario;
import modelo.Recurso;
import modelo.Profesor;
import modelo.Estudiante;

import java.awt.*;
import java.awt.event.*;

public class MainController{
	private Map<String, JPanel> ventanas; // Mapa para almacenar todas las ventanas
	private Map<String, CursoController> cursoControllers = new HashMap<>();
    
	// Instancias del modelo y las vistas
    private CardLayout cardLayout; // CardLayout para el cambio de paneles
    private JFrame frame; // Frame de la aplicación
    private Usuario usuarioActual; // Usuario actual
    private JPanel cardsPanel; // Panel contenedor con CardLayout
    private List<Curso> cursos; // Lista de cursos que viene del modelo
    private List<Profesor> profesores; // Lista de profesores que viene del modelo
    private Menu menu; // Suponiendo que tienes un modelo para obtener los cursos

    private VentanaMenuPrincipal ventanaMenuPrincipal;
    private VentanaContrasena ventanaContrasena;
    private VentanaLogin ventanaLogin;

    public MainController() { // Recibir usuarioActual como parámetro
        this.ventanas = new HashMap<>(); // Inicializamos el mapa para las ventanas
        this.menu = new Menu();
        this.cursos = new ArrayList<>();
        this.profesores = new ArrayList<>();
        initialize(); // Inicializa el frame y componentes aquí
    }
    
    
    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }
    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }
   public void initialize() {
	   
	   menu.pasarCursos(cursos); // Carga los cursos en la lista del controlador
       menu.pasarProfesores(profesores); // Carga los profesores en la lista del controlador
   
     // Inicializar el frame y otros componentes aquí
        frame = new JFrame("Sistema de Gestión de Recursos Virtuales");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        
        // Inicializamos el panel de cartas
        cardsPanel = new JPanel();
        cardLayout = new CardLayout();
        cardsPanel.setLayout(cardLayout);
        
        // Añadimos el panel de cartas al frame de MainApp
        frame.add(cardsPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        
        // Crear solo la ventana de login inicialmente
        ventanaLogin = new VentanaLogin(this);
        ventanas.put("Login", ventanaLogin);
        cardsPanel.add(ventanaLogin, "Login");
        
        mostrarVentanaLogin();
        frame.setVisible(true); // Mover esta línea al final
    }
   
    
    public void configurarMenu(VentanaMenuPrincipal ventanaMenuPrincipal) {
        // Obtener el usuario actual
        Usuario usuarioActual = getUsuarioActual();
        // Habilitar o deshabilitar las opciones del menú según el tipo de usuario
        if (usuarioActual instanceof Profesor) {
        	ventanaMenuPrincipal.activarOpcionesProfesor();
        } else {
        	ventanaMenuPrincipal.desactivarOpcionesProfesor();
        }
    }

    //-------------------------------------------------------------
    
    //--------------- Interacción con la vista --------------------
    
 // Método para mostrar una ventana por su nombre
    public void mostrarVentana(String nombreVentana) {
        JPanel panel = ventanas.get(nombreVentana);
        if (panel != null) {
            cardLayout.show(cardsPanel, nombreVentana);
            frame.revalidate();
            frame.repaint();
        } else {
            System.out.println("La ventana " + nombreVentana + " no existe.");
        }
    }
    
    // Función para inicializar todas las ventanas pero no mostrarlas de inmediato
    private void createAditionalWindows() {
    	ventanaContrasena = new VentanaContrasena(this);
    	ventanaMenuPrincipal = new VentanaMenuPrincipal(this);
    	    
    	// Añadimos las ventanas al mapa y al panel de cartas
    	ventanas.put("Contrasena", ventanaContrasena);
    	ventanas.put("MenuPrincipal", ventanaMenuPrincipal);
   
    	cardsPanel.add(ventanaContrasena, "Contrasena");
    	cardsPanel.add(ventanaMenuPrincipal, "MenuPrincipal");
    	
        for (Curso curso : cursos) {
            crearVentanaCurso(curso, "EditarCurso");
            crearVentanaCurso(curso, "SubMenuCurso");
        }
    	
    	// Configurar listeners
        setupListeners();
    }
   
    public void handleLogin(String nombre, String rut, String correo, String tipoUsuario, String paralelo, String departamento) {
    	try {
            // Llama al método del modelo para crear el usuario
            crearUsuarioEnModelo(nombre, rut, correo, tipoUsuario, paralelo, departamento);
            createAditionalWindows();
            
         // Ahora el usuario actual ya está establecido en el modelo
            if (usuarioActual != null) {
                 mostrarVentanaContrasena(usuarioActual);
            } else {
                // Manejar el caso en que el usuarioActual sea nulo (esto no debería suceder si todo está bien)
                System.err.println("Error: usuarioActual es nulo después de crear el usuario.");
            }
            
        } catch (Exception ex) {
            // Puedes registrar el error, mostrar un mensaje a la vista, etc.
            System.err.println("Error al crear el usuario: " + ex.getMessage());
        }
      }
    
    private void crearUsuarioEnModelo(String nombre, String rut, String correo, String tipoUsuario, String paralelo, String departamento) {
        // Método para crear el usuario en el modelo
        try {
            int tipoUsuarioInt = tipoUsuario.equals("Estudiante") ? 1 : 2; // Convertir tipo de usuario a entero
            int paraleloInt = tipoUsuario.equals("Estudiante") ? Integer.parseInt(paralelo) : 0; // Asignar paralelo solo si es estudiante

            // Llama al método del modelo para crear el usuario
            usuarioActual = menu.ingresarDatosUsuario(nombre, rut, correo, tipoUsuarioInt, paraleloInt, departamento);

            // Actualiza el usuario en MainController
            setUsuarioActual(usuarioActual);
            System.out.println("Se creo usuario informacion : " + nombre  + rut + correo + tipoUsuario + paralelo + departamento);
            menu.setUsuarioActual(usuarioActual); // Asegúrate de que esto esté en el lugar correcto
            System.out.println("Se actualizo en el menu  usuario informacion : " + nombre  + rut + correo + tipoUsuario + paralelo + departamento);

        } catch (NumberFormatException ex) {
            // Manejar el error si el paralelo no es un número válido
            throw new IllegalArgumentException("El paralelo debe ser un número entero.", ex);
        }
    }
    
    public boolean debeMostrarContraseñaActual() {
        return usuarioActual != null && usuarioActual.getContrasena() != null;
    }
    
 // Método para mostrar la ventana de cambio de contraseña
    public void mostrarVentanaContrasena(Usuario usuarioActual) {
    	if (usuarioActual != null) {
            mostrarVentana("Contrasena");
        } else {
            JOptionPane.showMessageDialog(frame, "El usuario no está disponible.");
        }
    }
    public void mostrarVentanaContrasenaNueva(Usuario usuarioActual) {
 
    	if (usuarioActual != null) {
    		ventanaContrasena.reiniciarVentana(); 
            mostrarVentana("Contrasena");
        } else {
            JOptionPane.showMessageDialog(frame, "El usuario no está disponible.");
        }
    }
    
    public void handleConfirm() {
    	System.out.println("Handle confirm llamado");
    	String contrasenaActual = ventanaContrasena.getContraseñaActual();
        String nuevaContrasena = ventanaContrasena.getNuevaContraseña();
        
        
        if (ventanaContrasena.isContraseñaActualVisible() && contrasenaActual.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Asegúrate de que no se continúe si los campos están vacíos
        }
        if (nuevaContrasena.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete la nueva contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Asegúrate de que no se continúe si los campos están vacíos
        }
        if (menu == null) {
            throw new IllegalStateException("El objeto 'menu' no está inicializado.");
        }
        System.out.println("Tratando de cambiar contraseña. Actual: " + contrasenaActual + "| Nueva : " + nuevaContrasena);
        menu.setUsuarioActual(usuarioActual); // Asegúrate de que esto esté en el lugar correcto
        
        try {
            // Verificar si el usuario actual es un Profesor
            if (usuarioActual instanceof Profesor) {
            	 menu.cambiarContraseña(contrasenaActual, nuevaContrasena);
            	 System.out.println("Contraseña actual: " + usuarioActual.getContrasena());
                
            } else {
                // Si no es Profesor, realizar el cambio de contraseña normalmente
                menu.cambiarContraseña(contrasenaActual, nuevaContrasena);
                JOptionPane.showMessageDialog(null, "Contraseña cambiada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            System.out.println("Contraseña actual después de cambio: " + usuarioActual.getContrasena());
            mostrarVentana("MenuPrincipal");
        } catch (IllegalArgumentException e) {
            // Manejar el caso donde la contraseña actual no es correcta
            JOptionPane.showMessageDialog(null, "La contraseña actual no es correcta", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            // Manejar cualquier otro tipo de error
            JOptionPane.showMessageDialog(null, "Ocurrió un error al cambiar la contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void mostrarMenuPrincipal() {
        mostrarVentana("MenuPrincipal");
    }
   
    public void mostrarVentanaLogin() {
        mostrarVentana("Login");
    }
    
    
    private void setupListeners() {
    	ventanaMenuPrincipal.getConfigMenuItem().addActionListener(e -> ventanaMenuPrincipal.mostrarPanel("Configuración"));
        ventanaMenuPrincipal.getFichaMenuItem().addActionListener(e -> ventanaMenuPrincipal.mostrarPanel("Ficha Personal"));
        ventanaMenuPrincipal.getRevisarMenuItem().addActionListener(e -> ventanaMenuPrincipal.mostrarPanel("Revisar Curso"));
        ventanaMenuPrincipal.getModificarMenuItem().addActionListener(e -> ventanaMenuPrincipal.mostrarPanel("Editar Curso"));
        ventanaMenuPrincipal.getSalirMenuItem().addActionListener(e -> salirAplicacion());
        
        ventanaMenuPrincipal.getConfirmarBtn().addActionListener(e -> confirmarSeleccion());
        ventanaMenuPrincipal.getCambiarNombreBtn().addActionListener(e ->cambiarNombreUsuario());
        ventanaMenuPrincipal.getCambiarEmailBtn().addActionListener(e ->cambiarEmailUsuario());
        ventanaMenuPrincipal.getContrasenaBtn().addActionListener(e -> mostrarVentanaContrasenaNueva(usuarioActual));
        ventanaMenuPrincipal.getVolverBtn().addActionListener(e -> ventanaMenuPrincipal.mostrarPanel("Ficha Personal"));

        
    }
   	public void salirAplicacion() {
        System.exit(0);  // Cerrar la aplicación
    }
   	
    // Método para crear el controlador de un curso
    public void crearCursoController(Curso curso) {
        String panelKey = "CursoController" + curso.getId();
        // Verificar si ya existe un controlador para el curso
        if (!cursoControllers.containsKey(panelKey)) {
            // Crear un nuevo CursoController, pasando usuarioActual
            CursoController cursoController = new CursoController(this, curso, usuarioActual, cardsPanel, ventanas);
            cursoControllers.put(panelKey, cursoController);
            System.out.println("Se creó el controlador para el curso con ID " + curso.getId());
        } else {
            System.out.println("El controlador para el curso con ID " + curso.getId() + " ya existe.");
        }
    }
    
    // Método para crear ventanas de curso dinámicamente
    public void crearVentanaCurso(Curso curso, String tipoVentana) {
        String panelKey = tipoVentana + curso.getId();

        // Verificar si la ventana ya existe
        if (!ventanas.containsKey(panelKey)) {
            // Llamar al CursoController para crear la ventana
            crearCursoController(curso);
            // El CursoController manejará la creación de ventanas
            CursoController cursoController = cursoControllers.get("CursoController" + curso.getId());
            JPanel nuevaVentana = cursoController.crearVentana(tipoVentana);

            // Guardar la ventana y agregarla al panel de tarjetas
            ventanas.put(panelKey, nuevaVentana);
            cardsPanel.add(nuevaVentana, panelKey);

            System.out.println("Se creó la ventana " + tipoVentana + " para el curso con ID " + curso.getId());
        } else {
            System.out.println("La ventana " + tipoVentana + " para el curso con ID " + curso.getId() + " ya existe.");
        }
    }
    
    // Método para mostrar las ventanas de curso, delegando al CursoController
    public void mostrarVentanaCurso(Curso curso, String tipoVentana) {
        String panelKey = "CursoController" + curso.getId();
        if (cursoControllers.containsKey(panelKey)) {
            CursoController cursoController = cursoControllers.get(panelKey);
            cursoController.mostrarVentana(tipoVentana);  // El controlador del curso maneja esta lógica
        } else {
            crearCursoController(curso);
            cursoControllers.get(panelKey).mostrarVentana(tipoVentana);
        }
    }

    
    //-------------- Interacción con el modelo --------------------
    
   
    
    private void confirmarSeleccion() {
        String cursoSeleccionado = (String) ventanaMenuPrincipal.getCursoComboBox().getSelectedItem();
        int idCurso = Integer.parseInt(cursoSeleccionado);
        System.out.println("Curso seleccionado: " + idCurso);
        
        // Llamada al modelo para manejar el curso seleccionado
        // Aquí puedes hacer las validaciones necesarias o redirigir a otras ventanas
    }
    
	 // Método para actualizar el nombre del usuario
	    public void cambiarNombreUsuario() {
	    	String nuevoNombre = ventanaMenuPrincipal.getNombreField().getText();
            usuarioActual.setNombre(nuevoNombre);
            JOptionPane.showMessageDialog(null, "Nombre actualizado!");
	    }
	
	    // Método para actualizar el email del usuario
	    public void cambiarEmailUsuario() {
	    	String nuevoEmail = ventanaMenuPrincipal.getEmailField().getText();
            usuarioActual.setEmail(nuevoEmail);
            JOptionPane.showMessageDialog(null, "Email actualizado!");
	    }
    
    
    // Método para obtener el panel contenedor
    public JPanel getCardsPanel() {
        return cardsPanel;
    }

    // Método para obtener el CardLayout
    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JFrame getFrame() {
        return frame;  // Para acceder al frame desde VentanaMenuPrincipal
    }
    
    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }
    public Usuario getUsuarioActual() {
    	return usuarioActual;
    }
    
    // Método para obtener el paralelo del estudiante
    public int getParaleloEstudiante() {
        if (usuarioActual instanceof Estudiante) {
            return ((Estudiante) usuarioActual).getParalelo();
        }
        return 0;
    }

    // Método para verificar si el usuario es un profesor
    public boolean esProfesor() {
        return usuarioActual instanceof Profesor;
    }
    
 // Método para verificar si el usuario es un estudiante
    public boolean esEstudiante() {
        return usuarioActual instanceof Estudiante;
    }

    // Método para obtener el departamento del profesor
    public String getDepartamentoProfesor() {
        if (usuarioActual instanceof Profesor) {
            return ((Profesor) usuarioActual).getDepartamento();
        }
        return null;
    }

}
