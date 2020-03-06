package simple.draw.y2020.m03.d05;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import simple.draw.MainFrame;

public class Draw20200305 extends JPanel {
	
	int count=100;

	@Override
	protected void paintComponent(Graphics g) {
		BasicStroke stroke = new BasicStroke(2);
		Graphics2D g_2d=(Graphics2D)g;
		g_2d.setStroke(stroke);
		for(int i=0;i<5;i++)
		{
			g.setColor(Color.red);
			g.drawArc(100+i*50, 100+i*50, 500-i*100, 500-i*100, 0, 360);
		}
	}

	


	public static void main(String[] args) throws InterruptedException
	{
		Draw20200305 draw = new Draw20200305();
		MainFrame frame = new MainFrame();
		frame.initialize();
		frame.addPanel(draw);
		
		Thread.sleep(1000*5);
		
		draw.repaint();
	}
	
}