package store;

import java.io.IOException;

import org.dom4j.DocumentException;

import log.Logger;
import store.task.cache.FileCache;
import store.task.device.CacheDevice;
import store.task.device.ItemDevice;

public class MgDeviceStart extends Thread
{
	private static MgDeviceStart one = new MgDeviceStart();
	
	public static MgDeviceStart single()
	{
		return one;
	}
	
	Logger log = new Logger(this.getClass());
	
	
	public void startService()
	{
		log.log("驱动任务","启动");
		start();
	}


	@Override
	public void run() {
		while(true)
		{
			CacheDevice.single().run();
			ItemDevice.single().run();
			
			try {
				FileCache.single().saveFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
			
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
}
