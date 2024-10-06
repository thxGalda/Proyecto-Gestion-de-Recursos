package vistas;

import java.awt.*;
import javax.swing.*;
import controladores.*;

import java.awt.event.*;

public class VentanaLogin extends JPanel{

    private CardLayout cardLayout;
    private JPanel cardsPanel;
    private MainController mainController; // Cambiamos MainApp por MainController

    // Componentes de la pantalla de bienvenida
    private JPanel panelBienvenida;
    private JButton btnIniciar;

    // Componentes del formulario
    private JPanel panelFormulario;
    private JTextField nombreField, rutField, correoField, paraleloField, departamentoField;
    private JComboBox<String> tipoUsuarioBox;
    private JButton btnEnviar;

    // Constructor adaptado para MainController
    public VentanaLogin(MainController mainController) {
        this.mainController = mainController; // Asignamos el controlador
        initialize();
    }

    public void initialize() {
        setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        // Crear pantallas
        crearPantallaBienvenida();
        crearPantallaFormulario();

        // Agregar ambas pantallas al panel principal
        cardsPanel.add(panelBienvenida, "Bienvenida");
        cardsPanel.add(panelFormulario, "Formulario");

        // Mostrar pantalla de bienvenida al inicio
        add(cardsPanel, BorderLayout.CENTER);
        cardLayout.show(cardsPanel, "Bienvenida");
    }
    
    // Método para crear la pantalla de bienvenida
    private void crearPantallaBienvenida() {
        panelBienvenida = new JPanel();
        panelBienvenida.setLayout(new BorderLayout());
        JLabel bienvenidaLabel = new JLabel("Bienvenido al Sistema de Gestión de Recursos Virtuales", JLabel.CENTER);
        bienvenidaLabel.setFont(new Font("Arial", Font.BOLD, 24));
        btnIniciar = new JButton("Iniciar Sesión");
        btnIniciar.addActionListener(e -> cardLayout.show(cardsPanel, "Formulario"));

        panelBienvenida.add(bienvenidaLabel, BorderLayout.CENTER);
        panelBienvenida.add(btnIniciar, BorderLayout.SOUTH);
    }

 // Método para crear la pantalla del formulario de ingreso de datos
    private void crearPantallaFormulario() {
        panelFormulario = new JPanel(new GridLayout(7, 2, 10, 10));

        // Campos del formulario
        nombreField = new JTextField();
        rutField = new JTextField();
        correoField = new JTextField();
        paraleloField = new JTextField();
        departamentoField = new JTextField();

        // Crear ComboBox para seleccionar el tipo de usuario
        String[] tiposUsuarios = {"Estudiante", "Profesor"};
        tipoUsuarioBox = new JComboBox<>(tiposUsuarios);
        tipoUsuarioBox.addActionListener(e -> toggleCampos());

        // Botón para enviar los datos
        btnEnviar = new JButton("Enviar");
        btnEnviar.addActionListener(e -> mainController.handleLogin(
                nombreField.getText(),
                rutField.getText(),
                correoField.getText(),
                (String) tipoUsuarioBox.getSelectedItem(),
                paraleloField.getText(),
                departamentoField.getText()
        ));

        // Agregar componentes al formulario
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(nombreField);
        panelFormulario.add(new JLabel("RUT:"));
        panelFormulario.add(rutField);
        panelFormulario.add(new JLabel("Correo:"));
        panelFormulario.add(correoField);
        panelFormulario.add(new JLabel("Tipo de Usuario:"));
        panelFormulario.add(tipoUsuarioBox);
        panelFormulario.add(new JLabel("Paralelo (solo estudiantes):"));
        panelFormulario.add(paraleloField);
        panelFormulario.add(new JLabel("Área de Especialización (solo profesores):"));
        panelFormulario.add(departamentoField);
        panelFormulario.add(new JLabel());
        panelFormulario.add(btnEnviar);

        toggleCampos();
    }
    
    // Método para mostrar/ocultar campos según el tipo de usuario seleccionado
    private void toggleCampos() {
        boolean esEstudiante = tipoUsuarioBox.getSelectedItem().equals("Estudiante");
        paraleloField.setEnabled(esEstudiante);
        departamentoField.setEnabled(!esEstudiante);
    }
    
    
}
