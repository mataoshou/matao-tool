package store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WordCache
{
	Map<String,WordItem> m_map = new ConcurrentHashMap();
	
	public void load()
	{
		
		
		
	}
	
	public void save() {
		
		
		
	}
	
	public WordItem getItem(Long id)
	{
		return m_map.get(id);
	}
	
	public void add(WordItem item)
	{
		m_map.put(item.getWord(), item);
	}
}
