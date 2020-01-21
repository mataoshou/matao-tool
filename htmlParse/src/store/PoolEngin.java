package store;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import pojo.store.FileItem;
import store.constant.FileType;
import store.task.ContentTask;
import store.task.IBaseTask;
import store.task.ItemTask;
import store.task.WordTask;
import store.unit.IBaseStoreUnit;

public class PoolEngin
{
	
	public static PoolEngin engin = new PoolEngin();
	
	public static PoolEngin single()
	{
		return engin;
	}
	
	
	
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
		threadMap.put(item.getFileName(), val);
		
	}
	
	public void startWork()
	{
		for(Entry<String,IBaseTask> entry :threadMap.entrySet())
		{
			IBaseTask val =entry.getValue();
			if(!val.isEmpty())
			{
				if(val.isAction())continue;
				Thread thread = new Thread(entry.getValue());
				thread.start();
			}
		}
	}
}
