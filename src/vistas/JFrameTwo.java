package vistas;
import javax.swing.JFrame;

public class JFrameTwo {
	private JFrame frame;
	public JFrameTwo() {
		initialize();
	}
	public void initialize() {
		frame = new JFrame();
		this.frame.setTitle("JFrameTwo");
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.frame.setSize(800, 500);
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(false);
		this.frame.setVisible(true);
	}

}
