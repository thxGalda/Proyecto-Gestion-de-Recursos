package vistas;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class VentanaMenu implements ActionListener{

	private JFrame frame;
	private JTextField textField;
	private JLabel label;
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
	public VentanaMenu() {
		initialize();
		
	}
	public void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 500);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout(10, 10));
		frame.setTitle("Aula Virtual");
		//frame.pack();
		/*frame.getContentPane().add(txtEstaEsUna, BorderLayout.CENTER);
		txtEstaEsUna.setColumns(10);
		*/
		// Cambiar fuente para menu e item de menu
		Font f = new Font("Roboto", Font.PLAIN, 20);
		UIManager.put("Menu.font", f);
		UIManager.put("MenuItem.font", f);
		UIManager.put("CheckBoxMenuItem.font", f);
		UIManager.put("RadioButtonMenuItem.font", f);
		
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
		
		
		
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		layout.setHgap(10);
		layout.setVgap(10); // alternativa FlowLayout(FlowLayout.CENTER, 10, 20) // centrado, distancia de 10px, altura 20px
		
		panel = new JPanel(); // contenedor para otros componentes
		panel.setBorder(BorderFactory.createEmptyBorder());
		panel.setLayout(layout);
		panel.setBackground(Color.GRAY);
		
		textField = createTextField("");
		checkbox = createCheckBox("Check Box");
		label = createLabel("Enter password");
		passwordField = new JPasswordField(10);
		passwordField.setText("hwa27a7ia"); // contraseña default
		passwordField.setEchoChar('.');
		
		
		//button = createButton("Save"); // utiliza 2 action listeners
		button = new JButton("Check");
		//button.addActionListener(this);
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				char[] pf = passwordField.getPassword();
				String value = new String(pf);
				System.out.println("Password: " + value);
			}
		});
	
		frame.setLayout(layout);
		frame.add(label);
		frame.add(passwordField);
		frame.add(button);
		panel.add(checkbox);
		
		frame.add(panel, BorderLayout.NORTH);

	}
	
	private JButton createButton(String text) {
		JButton btn = new JButton(text);
		//Image printIcon = new ImageIcon(this.getClass().getResource("/docx.png")).getImage(); // agregar iconos
		// btn.setIcon(printIcon); 
		btn.setToolTipText("Este es un boton");
		btn.setFont(new Font("Roboto", Font.PLAIN, 12));
		btn.setMargin(new Insets(10,10, 10, 10));
		btn.setVerticalTextPosition(SwingConstants.BOTTOM);
		btn.setHorizontalTextPosition(SwingConstants.CENTER);
		btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				count++;
				label.setText("Contador: "+ count);
			}
		});
		return btn;
	}
	private JLabel createLabel(String text) {
		JLabel lbl = new JLabel(text);
		lbl.setForeground(Color.BLACK); // se puede agregar icono tambien
		lbl.setFont(new Font("Roboto", Font.BOLD, 12));
		lbl.setVerticalTextPosition(SwingConstants.BOTTOM);
		lbl.setHorizontalTextPosition(SwingConstants.CENTER);
		return lbl;
	}
	private JTextField createTextField(String text) {
		JTextField txtFld = new JTextField(10);
		txtFld.setText(text);
		txtFld.setFont(new Font("Roboto", Font.BOLD, 24));
		txtFld.setForeground(Color.WHITE);
		txtFld.setBackground(Color.GRAY);
		txtFld.setMargin(new Insets(5,10, 5, 10));
		txtFld.setColumns(10);
		txtFld.setToolTipText("Ingresa un texto");
		txtFld.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText(txtFld.getText());
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
		}
	}
	
	public void mostrar() {
		frame.setVisible(true);
	}

}

