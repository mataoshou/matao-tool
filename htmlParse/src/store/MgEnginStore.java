package store;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import pojo.store.StoreItem;
import pojo.store.WordItem;
import store.config.FileConfig;
import store.unit.ContentUnit;

public class MgEnginStore
{
	
	public void setRoot(File root)
	{
		FileConfig.root = root;
	}
	
	public void setFileHouese(String fileName)
	{
		FileConfig.fileHouse = fileName;
	}
	

	/**
	 * 启动
	 */
	public void startUp() {
		synchronized(MgEnginStore.class)
		{
			try
			{
				loadStore();
				MgEnginTask task = new MgEnginTask();
				task.start();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}
	
	//获取word对应对象的内容
	public Map<String,Map> getWords(String key)
	{
		WordItem word = WordCache.single().getItem(key);
		Map<String,Map> result = new HashMap();
		for(int i= 0;i<word.getsIds().size();i++)
		{
			if(word.getsIds().size()>i)
			{
				try
				{
					StoreItem item = ItemCache.single().getItem(word.getsIds().get(i));
					ContentUnit unit = new ContentUnit(); 
					Map content = unit.getContent();
					result.put(word.getsIds().get(i), content);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 加载
	 * @throws Exception 
	 */
	private void loadStore() throws Exception
	{
		FileCache.single().load(FileConfig.root, FileConfig.fileHouse);
		ItemCache.single().loadCache(FileConfig.root);
		WordCache.single().loadCache(FileConfig.root);
	}
	
	
	/**
	 * 保存内容
	 * @param content
	 */
	public void save(Map<String,String> content) {
		
	}
	
	public void query(String... keys)
	{
		
	}
	
}
