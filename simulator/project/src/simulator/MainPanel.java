package simulator;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

import org.dom4j.DocumentException;

import simulator.pojo.OptionItem;

public class MainPanel
{
	public static void main(String[] args)
	{
		System.out.println("校验文件路径..");

		if(args ==null||args.length==0||args[0]==null||args[0].length()==0)
		{
			System.out.println("config path is error!!");
			System.exit(0);
		}

		File homeDir = new File(args[0]);
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainPanel window = new MainPanel();
					window.startDemo(homeDir);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
	}

	
	public void startDemo(File homeDir) {
	        	
    	OptionsHouse house = new OptionsHouse();
    	try
		{
			house.load(homeDir);
		} catch (IOException e1)
		{
			e1.printStackTrace();
		} catch (DocumentException e1)
		{
			e1.printStackTrace();
		}
    	////操作开始
    	OptionCarry carry = new OptionCarry();
    	
    	for(OptionItem item:house.options)
    	{
    		carry.optionCarry(item);
    	}
    	
    	System.out.println("运行结束！！");
    	System.exit(0);
    }

}
