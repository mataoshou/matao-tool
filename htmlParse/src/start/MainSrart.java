package start;

import java.io.File;
import java.io.IOException;

import org.dom4j.DocumentException;

import oper.GroupTask;
import oper.TaskItem;
import pojo.oper.GroupItem;


public class MainSrart
{
	public static void main(String[] args)
	{
		System.out.println(System.currentTimeMillis());
		if(args ==null||args.length==0||args[0]==null||args[0].length()==0)
		{
			System.out.println("config path is error!!");
			System.exit(0);
		}

		File homeDir = new File(args[0]);
		
		String path =  MainSrart.class.getClass().getResource("/").getPath();
		
		File driveFile = new File(path,"chromedriver.exe"); 
		System.out.println("驱动存储位置："+driveFile.getPath());
		System.setProperty("webdriver.chrome.driver",driveFile.getPath());
		TaskHouse house = new TaskHouse();
		try
		{
			house.load(homeDir);
		} catch (IOException | DocumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(TaskItem item : house.taskItems)
		{
			if(item.getSync()==1)
			{
				for(GroupItem gItem :item.getList())
				{
					for(int i=0;i<gItem.getCount();i++)
					{
						GroupTask run = new GroupTask();
						run.setItem(gItem);
						Thread thread = new Thread(run);
						thread.start();
						try
						{
							Thread.sleep(item.getInterval()*1000);
						} catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		
	}
}
