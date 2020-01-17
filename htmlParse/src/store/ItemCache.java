package store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pojo.store.FileItem;
import pojo.store.StoreItem;

public class ItemCache implements ICache<StoreItem>
{
	
	private static ItemCache one = new ItemCache();
	
	public static ItemCache single()
	{
		return one;
	}
	
	Map<String,StoreItem> m_map = new ConcurrentHashMap();
	private int mod =0;
	
	private File root;
	
	//加载所有item的存储信息
	public void loadCache(File root)
	{
		this.mod=0;
		this.root = root;
		
		StoreUtil util = new StoreUtil();
		for(FileItem file :FileCache.single().items.values())
		{
			try {
				File itemFile = new File(root,file.getFileName());
				int id_len = 32*8;
				int no_len = 32*8;
				FileInputStream  input = new FileInputStream(itemFile);
				
				while(true)
				{
					StoreItem item = new StoreItem();
					byte[] bs = util.getByte(input,id_len);
					item.setId(new String(bs));
					
					bs = util.getByte(input, no_len);
					item.setBegin(Long.valueOf( new String(bs)));
					bs = util.getByte(input, no_len);
					item.setEnd(Long.valueOf( new String(bs)));
					bs = util.getByte(input, no_len);
					item.setLength(Integer.valueOf( new String(bs)));
					
					bs = util.getByte(input, no_len);
					item.setStoreName(new String(bs));

					item.setFileName(file.getFileName());
					
					m_map.put(item.getId(), item);
				}
				
			}
			catch(Exception e)
			{
				
			}
			
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
}
