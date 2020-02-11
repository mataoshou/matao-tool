package store.task.cache;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import log.Logger;
import pojo.store.FileItem;
import pojo.store.StoreItem;
import store.StoreUtil;
import store.unit.ItemUnit;
import util.StringUtil;

public class ItemCache 
{
	
	private static ItemCache one = new ItemCache();
	
	public static ItemCache single()
	{
		return one;
	}
	
	Map<String,StoreItem> m_map = new ConcurrentHashMap();
	private int mod =0;
	
	
	Logger log = new Logger(this.getClass());
	
	//加载所有item的存储信息
	public void loadCache(File root)
	{
		this.mod=0;
		
		StoreUtil util = new StoreUtil();
		for(FileItem file :FileCache.single().items.values())
		{
			log.log("开始读取","元素存储",file.getFileName());
			try {
				File itemFile = new File(root,file.getFileName());
				if(!itemFile.exists())
				{
					log.log("文件不存在",file.getFileName());
					continue;
				}
//				FileInputStream  input = new FileInputStream(itemFile);
				RandomAccessFile input = new RandomAccessFile(itemFile, "rw");
				while(true)
				{
					if(input.getFilePointer()>=itemFile.length())break;
					ItemUnit unit = new ItemUnit();
					unit.readItem(input, file.getId());
					
					m_map.put(unit.getId(), unit.getItem());
					
					log.log("加载元素单元",unit.getId());
				}
				input.close();
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
				
			}
			log.log("完成读取","元素存储",file.getFileName());
		}
	}
	
	public void saveCache(File root) 
	{
		this.mod=0;
	}
	
	public StoreItem getItem(String id)
	{
		return this.m_map.get(id);
	}
	
	
	
	public void add(StoreItem item)
	{
		this.mod++;
		this.m_map.put(item.getId(), item);
	}
	
	
	public void save()
	{
		
	}

	public void edit(String key, StoreItem item) {
		StringUtil util = new StringUtil();
		
		
		
		
		
	}
}
