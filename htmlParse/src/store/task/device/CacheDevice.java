package store.task.device;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import log.Logger;
import pojo.store.StoreItem;
import store.constant.CacheConstant;
import store.task.cache.IBaseCacheTask;
import store.task.cache.WordAnalysisTask;

public class CacheDevice{
	
	
	private static CacheDevice one;
	
	public static CacheDevice single()
	{
		if(one ==null)
		{
			one= new CacheDevice();
			one.addCache();
		}
		return one;
	}
	
	Logger log = new Logger(this.getClass());
	
	
	
	Map<String,IBaseCacheTask> m_map = new HashMap();
	
	
	public void addCache()
	{
		m_map.put(CacheConstant.CACHE_NAME_WORDANALYSIS, new WordAnalysisTask());
	}
	

	public void run() {
		//log.log("开始","执行周期缓存任务");
		for(Entry<String,IBaseCacheTask> entry: m_map.entrySet())
		{
			if(entry.getValue().isHour())
			{
				entry.getValue().run();
			}
		}
		//log.log("完成","执行周期缓存任务");
	}

	
	public void addTask(String name,StoreItem item)
	{
		log.log("添加任务",name);
		IBaseCacheTask cache = m_map.get(name);
		cache.addItem(item);
	}
}
