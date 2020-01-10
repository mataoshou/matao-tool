package store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemCache
{
	Map<Long,StoreItem> m_map = new ConcurrentHashMap();
	
	
	public void load()
	{
		
	}
	
	public void save() 
	{
		
	}
	
	public StoreItem getItem(Long id)
	{
		return m_map.get(id);
	}
	
	public void add(StoreItem item)
	{
		m_map.put(item.getId(), item);
	}
}
