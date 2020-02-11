package store.task.cache;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pojo.store.FileItem;
import pojo.store.WordItem;
import store.unit.WordUnit;

public class WordCache
{
	
	private static WordCache one = new WordCache();
	
	public static WordCache single()
	{
		return one;
	}
	
	Map<String,WordItem> m_map = new ConcurrentHashMap();
	
	private int mod =0;
	
	int id_len = 32*8;//id长度
	int no_len = 8*8;//数字长度
	int state_len =8;//状态长度
	
	public void loadCache(File root)
	{
		
		this.mod=0;
		
		for(FileItem file :FileCache.single().words.values())
		{
			try {
				File itemFile = new File(root,file.getFileName());
				
//				FileInputStream  input = new FileInputStream(itemFile);
				RandomAccessFile input = new RandomAccessFile(itemFile, "rw");
				while(true)
				{
					if(input.getFilePointer()>=itemFile.length())break;
					WordUnit unit = new WordUnit();
					unit.readItem(input, file.getId());
					
					m_map.put(unit.getItem().getWord(), unit.getItem());
				}
				input.close();
			}
			catch(Exception e)
			{
				
			}
			
		}
		
	}
	//添加新 检索词
	public void addNewItem(String word,String[] ids)
	{
		WordItem item = new WordItem();
		item.setWord(word);
		for(String id : ids)
		{
			item.addId(id);
		}
		this.m_map.put(word, item);
	}
	
	public void addId(String word,String id)
	{
		WordItem item = this.m_map.get(word);
		if(item==null)
		{
			addNewItem(word,new String[] { id});
		}
		else {
			
			if(!item.getsIds().contains(id))
			{
				item.addId(id);
			}
			
		}
		mod++;
	}
	
	
	public void addId(String[] words,String id)
	{
		for(String word : words)
		{
			addId(word,id);
		}
	}
	
	public void saveCache(File root) {
		this.mod =0;
	}
	
	public WordItem getItem(String word)
	{
		return this.m_map.get(word);
	}
	
	public void add(WordItem item)
	{
		this.mod++;
		this.m_map.put(item.getWord(), item);
	}
	
	public void save(File root,Map<String,FileItem> fileitems)
	{
		first:for(WordItem item : m_map.values())
		{
			if(item.getMod()>0)
			{
				WordUnit unit = new WordUnit();
				unit.setItem(item);
				
				
				
			}
		}
	}
	
	public int getLength(WordItem item)
	{
		return (state_len + no_len*5+id_len*item.getsIds().size() +item.getWord().length());
	}
	
	public void edit(String key, WordItem item) {
		// TODO Auto-generated method stub
		
	}
	
}
