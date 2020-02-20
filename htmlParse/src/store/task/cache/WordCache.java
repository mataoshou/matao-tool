package store.task.cache;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import log.Logger;
import pojo.store.FileItem;
import pojo.store.WordItem;
import store.constant.FileConstant;
import store.constant.FileType;
import store.unit.WordUnit;

public class WordCache
{
	
	private static WordCache one = new WordCache();
	
	public static WordCache single()
	{
		return one;
	}
	
	Logger log = new Logger(this.getClass());
	
	Map<String,WordItem> m_map = new ConcurrentHashMap();
	
	private int mod =0;

	
	public void loadCache(File root)
	{
		
		this.mod=0;
		
		for(FileItem file :FileCache.single().words.values())
		{
			RandomAccessFile input =null;
			try {
				File itemFile = new File(root,file.getFileName());
				
//				FileInputStream  input = new FileInputStream(itemFile);
				input = new RandomAccessFile(itemFile, "rw");
				while(true)
				{
					if(input.getFilePointer()>=itemFile.length())break;
					WordUnit unit = new WordUnit();
					unit.readItem(input, file.getId());
					
					m_map.put(unit.getItem().getWord(), unit.getItem());
				}
				
			}
			catch(Exception e)
			{
				
			}
			finally {
				try
				{
					input.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
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
	
	
	public void persist()
	{
		if(mod==0)return;
		
		log.log("开始word的本地化");
		
		log.log("删除word存储文件");
		FileCache.single().delete(FileType.FILE_TYPE_WORD);
		
		for(Entry<String,WordItem> entry : m_map.entrySet())
		{
			WordItem item = entry.getValue();
			WordUnit unit = new WordUnit();
			unit.setItem(item);
			item.setFileId(FileCache.single().getEmpty(FileType.FILE_TYPE_WORD, unit.getLength()).getId());
			unit.persist();
		}
		log.getLog("完成word的本地化");
		mod=0;
	}
	
	
	public void addId(String[] words,String id)
	{
		for(String word : words)
		{
			addId(word,id);
		}
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

	
	
}
