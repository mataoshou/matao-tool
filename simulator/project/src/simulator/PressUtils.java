package simulator;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;

public class PressUtils
{
	
	private Robot rbt =null;
	
	int delay = 100;
	
	public PressUtils()
	{
		try
		{
			rbt = new Robot();
		} catch (AWTException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void showDesktop()
	{
    	Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
    	int width = (int)screensize.getWidth();
    	int height = (int)screensize.getHeight();
    	
    	this.rbt.delay(delay);
    	this.rbt.mouseMove(width-5, height-10);
    	this.rbt.delay(delay);
        mouseLeftClick();
	}
	
	public void moveTo(int x,int y)
	{
		this.rbt.delay(delay);
		this.rbt.mouseMove(x, y);
	}
	
	public void mouseDoubleClick(int x,int y ) {
		moveTo(x,y);
		mouseLeftClick();
		mouseLeftClick();
	}
	
	public void mouseLeftClick(int x,int y) {
		moveTo(x,y);
		mouseLeftClick();
	}
	
	
	public void mouseLeftClick() {
		this.rbt.delay(delay);
		this.rbt.mousePress(InputEvent.BUTTON1_MASK);
		this.rbt.delay(delay);
		this.rbt.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	public void mouseRightClick(int x,int y) {
		moveTo(x,y);
		this.rbt.delay(delay);
		this.rbt.mousePress(InputEvent.BUTTON3_MASK);
		this.rbt.delay(delay);
		this.rbt.mouseRelease(InputEvent.BUTTON3_MASK);
	}
	
	public void keyPress(int keyCode) {
		
		this.rbt.delay(delay);
		this.rbt.keyPress(keyCode);
		this.rbt.delay(delay);
		this.rbt.keyRelease(keyCode);
	}
	
	public void keyCombinationPress(int...keyCodes) {
		
		for(int i=0;i<keyCodes.length;i++)
		{
			this.rbt.delay(delay);
			this.rbt.keyPress(keyCodes[i]);
		}
		
		for(int i=0;i<keyCodes.length;i++)
		{
			this.rbt.delay(delay);
			this.rbt.keyRelease(keyCodes[i]);
		}
		
	}
}
