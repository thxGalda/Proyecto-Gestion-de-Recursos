package vistas;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class JFrameTwo implements ActionListener{

	private JFrame frame;
	private JTextField textField;
	private JLabel label;
	private JLabel success;
	private JPasswordField passwordField;
	private JPanel panel;
	private JButton button;
	private JCheckBox checkbox;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem newMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem exitMenuItem;
	
	int count = 0;
	/**
	 * Create the application.
	 */
	public JFrameTwo() {
		initialize();
		
	}
	public void initialize() {
		frame = new JFrame();
		frame.setSize(350, 200);
		frame.setTitle("Aula Virtual");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout(10, 10));
		//frame.pack();

		// Cambiar fuente para menu e item de menu
		Font f = new Font("Roboto", Font.PLAIN, 20);
		UIManager.put("Menu.font", f);
		UIManager.put("MenuItem.font", f);
		UIManager.put("CheckBoxMenuItem.font", f);
		UIManager.put("RadioButtonMenuItem.font", f);
		
		//--------------------------------------------------- MENU BARRA LATERAL
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		newMenuItem = new JMenuItem("New...");
		//ImageIcon icon = new ImageIcon("images/check.png"); // agregar iconos
		//newMenuItem.setIcon(icon);
		newMenuItem.setIconTextGap(10);
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newMenuItem.addActionListener(this);
		
		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveMenuItem.addActionListener(this);
		
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		exitMenuItem.addActionListener(this);
		
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(new JRadioButtonMenuItem("RadioButton"));
		fileMenu.add(new JCheckBoxMenuItem("Checkbox"));
		fileMenu.add(new JMenu("Submenu"));
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
		
		//
		// PANEL 1
		//
		
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.CENTER);
		layout.setHgap(10);
		layout.setVgap(20); // alternativa FlowLayout(FlowLayout.CENTER, 10, 20) // centrado, distancia de 10px, altura 20px

		
		panel = new JPanel(); // contenedor para otros componentes
		frame.add(panel);
		panel.setBorder(BorderFactory.createEmptyBorder());
		panel.setLayout(layout);
		panel.setBackground(Color.WHITE);
		
		checkbox = createCheckBox("Check Box");
		
		label = createLabel("Nombre Usuario");
		panel.add(label);
		textField = createTextField("");
		panel.add(textField);
		JLabel passwordLabel = new JLabel("Contraseña");
		passwordLabel.setBounds(10, 50, 80, 25);
		panel.add(passwordLabel);
		
		
		passwordField = new JPasswordField(20);
		passwordField.setText("hwa27a7ia"); // contraseña default
		passwordField.setEchoChar('.');
		passwordField.setBounds(100, 50, 165, 25);
		panel.add(passwordField);
		
		success = createLabel("");
		success.setBounds(10, 110, 300, 25);
		panel.add(success);
		//button = createButton("Login"); // utiliza 2 action listeners 
		button = new JButton("Login");
		button.addActionListener(this);
		panel.add(button);
		
		

		
		// 
		// Agregar todo
		//
		//frame.setLayout(layout);

	}
	
	private JButton createButton(String text) {
		JButton btn = new JButton(text);
		//Image printIcon = new ImageIcon(this.getClass().getResource("/docx.png")).getImage(); // agregar iconos
		// btn.setIcon(printIcon); 
		btn.setToolTipText("Este es un boton");
		btn.setFont(new Font("Roboto", Font.PLAIN, 12));
		btn.setBounds(10,80,80,25);
		btn.setMargin(new Insets(10,10, 10, 10));
		btn.setVerticalTextPosition(SwingConstants.BOTTOM);
		btn.setHorizontalTextPosition(SwingConstants.CENTER);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count ++;
				label.setText("Contador: "+ count);

			}
		});
		return btn;
	}
	private JLabel createLabel(String text) {
		JLabel lbl = new JLabel(text);
		lbl.setForeground(Color.BLACK); // se puede agregar icono tambien
		lbl.setBounds(10,20,80,25);
		lbl.setFont(new Font("Roboto", Font.BOLD, 12));
		lbl.setVerticalTextPosition(SwingConstants.BOTTOM);
		lbl.setHorizontalTextPosition(SwingConstants.CENTER);
		return lbl;
	}
	private JTextField createTextField(String text) {
		JTextField txtFld = new JTextField(10);
		txtFld.setText(text);
		txtFld.setFont(new Font("Roboto", Font.BOLD, 12));
		txtFld.setBounds(100,20,165,25); // x, y, width, height
		txtFld.setForeground(Color.WHITE);
		txtFld.setBackground(Color.GRAY);
		txtFld.setMargin(new Insets(5,5, 5, 5));
		txtFld.setColumns(10);
		txtFld.setToolTipText("Ingresa un texto");
		txtFld.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String txt = txtFld.getText();
				System.out.println("Nombre: " + txt);
			}
		});
		return txtFld;
	}
	private JCheckBox createCheckBox(String text) {
		JCheckBox chkbx = new JCheckBox();
		chkbx.setText(text);
		chkbx.setFont(new Font("Roboto", Font.BOLD, 24));
		chkbx.setForeground(Color.WHITE);
		chkbx.setBackground(Color.GRAY);
		chkbx.setMnemonic(KeyEvent.VK_C);
		chkbx.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Selected!");

			}
		});
		return chkbx;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JMenuItem) {
			JMenuItem item = (JMenuItem) e.getSource();
			String text = item.getText();
			System.out.println(text);
		}else if(e.getSource() instanceof JButton) {
			char[] pf = passwordField.getPassword();
			String value = new String(pf);
			String txt = textField.getText();
			System.out.println("Usuario: " + txt);
			System.out.println("Contraseña: " + value);
				
			if(txt.equals("Ignacio") && value.equals("1234")){
				success.setText("Login Exitoso!");
			}
				
		}
	}
	
	public void mostrar() {
		frame.setVisible(true);
	}

}