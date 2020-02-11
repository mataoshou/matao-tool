package store.task.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import pojo.store.FileItem;
import store.unit.IBaseStoreUnit;

public abstract class IBaseTask<T extends IBaseStoreUnit> implements Runnable
{

	public Map<String,T> m_map= new ConcurrentHashMap();
	
	
	private FileItem fitem;
	
	
	private volatile boolean action = false;
	
	public boolean isAction()
	{
		return action;
	}
	
	public void setAction()
	{
		this.action = true;
	}
	
	
	
	@Override
	public void run()
	{
		if(action) return;
		setAction();
		if(m_map.size()>0)
		{
			synchronized(this)
			{
				Map<String,T> tmp= m_map;
				m_map = new ConcurrentHashMap();
				for(Entry<String,T> entry : tmp.entrySet())
				{
					try {
						System.out.println("开始处理任务:"+entry.getKey());
						execute(entry.getValue());
						System.out.println("完成任务处理:"+entry.getKey());
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		
		action = false;
	}
	
	public abstract void execute(T unit);
	
	
	public void addTask(T unit)
	{
		synchronized(this)
		{
			empty=false;
			m_map.put(unit.getId(), unit);
		}
	}
	
	
	boolean empty =true;
	
	public void setEmpty()
	{
		empty = true;
	}
	
	
	public boolean isEmpty()
	{
		if(m_map.size()>0&&!empty)
		{
			return false;
		}
		return true;
	}

	public FileItem getFitem()
	{
		return fitem;
	}

	public void setFitem(FileItem fitem)
	{
		this.fitem = fitem;
	}
}
