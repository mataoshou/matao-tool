package simple.draw.y2020.m03.d03;

import java.awt.Graphics;

import javax.swing.JPanel;

import simple.draw.MainFrame;

public class Draw20200303 extends JPanel {
	
	int count=100;

	@Override
	protected void paintComponent(Graphics g) {
		g.drawLine(0,0,50+count,50);
        g.drawString("Banner",count,40);
        count +=100;
	}

	


	public static void main(String[] args) throws InterruptedException
	{
		Draw20200303 draw = new Draw20200303();
		MainFrame frame = new MainFrame();
		frame.initialize();
		frame.addPanel(draw);
		
		Thread.sleep(1000*5);
		
		draw.repaint();
	}
	
}
