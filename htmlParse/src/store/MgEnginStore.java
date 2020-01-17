package store;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pojo.store.StoreItem;
import pojo.store.WordItem;
import util.Convert;

public class MgEnginStore
{
	//文件存储根目录
	File root =null;

	
	public void setRoot(File root)
	{
		this.root = root;
	}
	
	public void setFileHouese(String fileName)
	{
		this.fileHouse = fileName;
	}
	//存储文件配置文件
	String fileHouse ="FileHouse.xml";

	/**
	 * 启动
	 */
	public void startUp() {
		synchronized(MgEnginStore.class)
		{
			try
			{
				loadStore();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}
	
	//获取word对应对象的内容
	public List<Map> getWords(String key)
	{
		WordItem word = WordCache.single().getItem(key);
		List<Map> result = new ArrayList();
		for(int i= 0;i<word.getsIds().size();i++)
		{
			if(word.getsIds().size()>i)
			{
				try
				{
					StoreItem item = ItemCache.single().getItem(word.getsIds().get(i));
					Map content =ItemCache.single().getContent(item);
					result.add(content);
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
		FileCache.single().load(root, fileHouse);
		ItemCache.single().loadCache(root);
		WordCache.single().loadCache(root);
	}
	
	
	/**
	 * 存储到磁盘持久化
	 */
	private void saveStore()
	{
		
	}
	
}
