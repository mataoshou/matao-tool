package store.task.device;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import log.Logger;
import pojo.store.FileItem;
import store.constant.FileType;
import store.task.cache.FileCache;
import store.task.item.ContentTask;
import store.task.item.IBaseTask;
import store.task.item.ItemTask;
import store.task.item.WordTask;
import store.unit.IBaseStoreUnit;

public class ItemDevice
{
	
	public static ItemDevice engin = null;
	
	public static ItemDevice single()
	{
		
		if(engin==null)
		{
			engin = new ItemDevice();
			engin.load();
		}
		
		return engin;
	}
	
	Logger log = new Logger(this.getClass());
	
	private Map<String,IBaseTask> threadMap = new HashMap();
	
	
	public void load()
	{
		for(FileItem item :  FileCache.single().items.values())
		{
			addThread(item);
		}
		
		for(FileItem item :  FileCache.single().contents.values())
		{
			addThread(item);
		}
		for(FileItem item :  FileCache.single().words.values())
		{
			addThread(item);
		}
	}
	
	/**
	 *添加任务 
	 */
	public void addTask(String fileName,IBaseStoreUnit unit)
	{
		IBaseTask val = threadMap.get(fileName);
		val.addTask(unit);
	}
	
	
	/**
	 * 
	 * 添加进程项
	 * @param item
	 */
	public void addThread(FileItem item)
	{
		IBaseTask val = null;
		
		switch(item.getType())
		{
			case FileType.FILE_TYPE_CONTENT:val =new ContentTask();break;
			case FileType.FILE_TYPE_ITEM:val =new ItemTask();break;
			case FileType.FILE_TYPE_WORD:val =new WordTask();break;
		}
		threadMap.put(item.getId(), val);
		
		log.log("添加文件存储任务",item.getFileName());
	}
	
	public void run()
	{
		for(Entry<String,IBaseTask> entry :threadMap.entrySet())
		{
			IBaseTask val =entry.getValue();
			if(!val.isEmpty())
			{
				if(val.isAction())continue;
				val.setEmpty();
				log.log("启动持久化任务",entry.getKey());
				Thread thread = new Thread(entry.getValue());
				thread.start();
			}
		}
	}
}
