package store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import log.Logger;
import pojo.store.FileItem;
import pojo.store.StoreItem;
import store.constant.FileConstant;
import util.StringUtil;

public class ItemCache implements ICache<StoreItem>
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
				FileInputStream  input = new FileInputStream(itemFile);
				
				while(true)
				{
					StoreItem item = new StoreItem();
					byte[] bs = util.getByte(input,FileConstant.LENGTH_ID);
					item.setId(new String(bs));
					
					bs = util.getByte(input, FileConstant.LENGTH_NO);
					item.setBegin(Long.valueOf( new String(bs)));
					bs = util.getByte(input, FileConstant.LENGTH_NO);
					item.setEnd(Long.valueOf( new String(bs)));
					bs = util.getByte(input, FileConstant.LENGTH_NO);
					item.setLength(Integer.valueOf( new String(bs)));
					
					bs = util.getByte(input, FileConstant.LENGTH_NO);
					item.setStoreName(new String(bs));

					item.setFileName(file.getFileName());
					
					m_map.put(item.getId(), item);
					
					log.log("加载元素单元",item.getId());
				}
				
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

	@Override
	public void edit(String key, StoreItem item) {
		StringUtil util = new StringUtil();
		
		
		
		
		
	}
}
