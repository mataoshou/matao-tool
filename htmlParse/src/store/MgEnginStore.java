package store;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import log.Logger;
import pojo.store.StoreItem;
import pojo.store.WordItem;
import store.config.FileConfig;
import store.constant.CacheConstant;
import store.task.cache.FileCache;
import store.task.cache.ItemCache;
import store.task.cache.WordCache;
import store.task.device.CacheDevice;
import store.unit.ContentUnit;	

public class MgEnginStore
{
	
	
	Logger log = new Logger(this.getClass());
	
	public void setRoot(File root)
	{
		FileConfig.root = root;
		log.log("设置根目录",root.getPath());
	}
	
	public void setFileHouese(String fileName)
	{
		FileConfig.fileHouse = fileName;
		log.log("设置文件仓库",fileName);
	}
	

	/**
	 * 启动
	 */
	public void startUp() {
		synchronized(MgEnginStore.class)
		{
			log.log("开始启动服务");
			try
			{
				loadStore();
				MgDeviceStart.single().startService();
			} catch (Exception e)
			{
				log.log("启动服务失败",e.getMessage());
				e.printStackTrace();
			}
			log.log("启动服务成功");
		}
	}
	
	/**
	 * 结束
	 */
	public void finish()
	{
//		MgDeviceStart.single().finish();
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
		log.log("加载文件列表");
		FileCache.single().load(FileConfig.root, FileConfig.fileHouse);
		log.log("加载存储项列表");
		ItemCache.single().loadCache(FileConfig.root);
		log.log("加载单词项列表");
		WordCache.single().loadCache(FileConfig.root);
	}
	
	public StoreItem getContent(String key) throws Exception
	{
		ContentUnit unit = new ContentUnit();
		unit.setItem(ItemCache.single().getItem(key));
		unit.readItem();
		
		return unit.getItem();
	}
	
	
	/**
	 * 保存内容
	 * @param content
	 */
	public void save(Map<String,String> content) {
		
		StoreItem item = new StoreItem();
		item.setContent(content);
		log.log("开始保存内容");
		
		
		CacheDevice.single().addTask(CacheConstant.CACHE_NAME_WORDANALYSIS, item);
		
		log.log("完成内容保存");		
	}
	
	public void query(String... keys)
	{
		
	}
	
	
	public static void main(String[] args) throws Exception
	{
		
		new File("D:\\source\\tool\\matao-tool\\warehouse").mkdirs();
		MgEnginStore store = new MgEnginStore();
		
		
		store.setFileHouese("FileHouse.xml");
		store.setRoot(new File("D:\\source\\tool\\matao-tool\\warehouse"));
		
		store.startUp();
		
		Thread.sleep(1000);
//		
//		StoreItem item = store.getContent("5A3A58E444BD4A8EB642AEA458CEBF69");
//		System.out.println(item.getContent().keySet().iterator().next());
//		System.out.println(item.getContent().values().iterator().next());
		
//		
		
		Thread.sleep(1000);
		Map map = new HashMap();
		map.put("matao", "昨天");
		
		store.save(map);
//		
		Thread.sleep(4000);
//		
		map.put("matao2", "今天");
//		
		store.save(map);
	}
}
