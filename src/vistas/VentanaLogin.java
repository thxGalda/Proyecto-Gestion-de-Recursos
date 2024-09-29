package vistas;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class VentanaLogin extends JPanel implements ActionListener {
    
    private CardLayout cardLayout;
    private JPanel cardsPanel;

    // Componentes de la pantalla de bienvenida
    private JPanel panelBienvenida;
    private JButton btnIniciar;

    // Componentes del formulario
    private JPanel panelFormulario;
    private JTextField nombreField, rutField, correoField, paraleloField, departamentoField;
    private JComboBox<String> tipoUsuarioBox;
    private JButton btnEnviar;

    // Constructor adaptado a JPanel
    public VentanaLogin() {
        setLayout(new BorderLayout());  // Cambiar el layout del JPanel
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
        panelBienvenida = new JPanel(new BorderLayout());
        JLabel labelBienvenida = new JLabel("Bienvenido al Sistema de Gestión de Recursos Educativos", JLabel.CENTER);
        labelBienvenida.setFont(new Font("Roboto", Font.BOLD, 14));

        btnIniciar = new JButton("Iniciar");
        btnIniciar.addActionListener(e -> mostrarFormulario());

        panelBienvenida.add(labelBienvenida, BorderLayout.CENTER);
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
        String[] tiposUsuarios = { "Estudiante", "Profesor" };
        tipoUsuarioBox = new JComboBox<>(tiposUsuarios);
        tipoUsuarioBox.addActionListener(e -> toggleCampos());

        // Botón para enviar los datos
        btnEnviar = new JButton("Enviar");
        btnEnviar.addActionListener(this);

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
        panelFormulario.add(new JLabel()); // Espacio vacío
        panelFormulario.add(btnEnviar);

        // Ocultar campos que no correspondan al tipo de usuario
        toggleCampos();
    }

    // Método para mostrar el formulario de ingreso de datos
    private void mostrarFormulario() {
        cardLayout.show(cardsPanel, "Formulario");
    }

    // Método para mostrar/ocultar campos según el tipo de usuario seleccionado
    private void toggleCampos() {
        boolean esEstudiante = tipoUsuarioBox.getSelectedItem().equals("Estudiante");
        paraleloField.setEnabled(esEstudiante);
        departamentoField.setEnabled(!esEstudiante);
    }
    //
    // Acciones
    //

    @Override
    public void actionPerformed(ActionEvent e) {
        // Lógica para procesar los datos ingresados
        String nombre = nombreField.getText();
        String rut = rutField.getText();
        String correo = correoField.getText();
        String tipoUsuario = (String) tipoUsuarioBox.getSelectedItem();

        if (tipoUsuario.equals("Estudiante")) {
            String paralelo = paraleloField.getText();
            System.out.println("Creando Estudiante: " + nombre + ", RUT: " + rut + ", Correo: " + correo + ", Paralelo: " + paralelo);
            // Crear objeto Estudiante y procesar
        } else if (tipoUsuario.equals("Profesor")) {
            String departamento = departamentoField.getText();
            System.out.println("Creando Profesor: " + nombre + ", RUT: " + rut + ", Correo: " + correo + ", Departamento: " + departamento);
            // Crear objeto Profesor y procesar
        }
    }
}
