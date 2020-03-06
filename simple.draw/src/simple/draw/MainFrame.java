package simple.draw;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	public void initialize() {
		this.setBounds(100, 100, 800, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void addPanel(JPanel panel)
	{
		this.add(panel);
	}
}
