package store;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pojo.store.FileItem;
import pojo.store.StoreItem;
import pojo.store.WordItem;
import util.Shift;

public class WordCache implements ICache<WordItem>
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

		StoreUtil util = new StoreUtil();
		
		for(FileItem file :FileCache.single().words.values())
		{
			try {
				File itemFile = new File(root,file.getFileName());
				
				FileInputStream  input = new FileInputStream(itemFile);
				
				while(true)
				{
					WordItem item = new WordItem();
					
					byte[] bs = util.getByte(input, state_len);
					
					if(bs==null)break;
					//前8位表示是否继续使用
					int state =Integer.valueOf(new String(bs));
					
					//8*8 存储存储起始位置
					bs = util.getByte(input, no_len);
					long begin =Long.valueOf( new String(bs));
					
					//8*8 存储存储结束位置
					bs = util.getByte(input, no_len);
					long end =Long.valueOf( new String(bs));
					
					//8*8 存储存储长度
					bs = util.getByte(input, no_len);
					int length = Integer.valueOf( new String(bs));					
					
					
					if(state==0)
					{
						bs= util.getByte(input, length-(no_len*3)-state_len);
						continue;
					}
					
					item.setFileName(file.getFileName());
					item.setBegin(begin);
					item.setEnd(end);
					item.setLength(length);
					
					//8*8 存储 最大id个数
					bs = util.getByte(input, no_len);
					item.setMax(Integer.valueOf( new String(bs)));
					
					//8*8 存储  实际使用id个数
					bs = util.getByte(input, no_len);
					item.setCount(Integer.valueOf( new String(bs)));
					
					//计算存储索引word的长度
					int wordLength = item.getLength() - (item.getMax()* no_len -8);
					bs = util.getByte(input, wordLength);
					
					item.setWord(new String(bs,"UTF-8"));
					
					for(int i=0;i<item.getMax();i++)
					{
						bs = util.getByte(input, id_len);
						if(i<item.getCount()) {
							item.addId(new String(bs));
						}
					}
					m_map.put(item.getWord(), item);
				}
				
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
			item.addId(id);
		}
		mod++;
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
				if(item.getsIds().size()<item.getMax())
				{
					try {
						File f = new File(root,item.getFileName());
						RandomAccessFile stream = new RandomAccessFile(f, "rw");
						stream.seek(item.getBegin());						
						writeItem(item,stream);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				else 
				{
					try 
					{
						{
							File f = new File(root,item.getFileName());
							RandomAccessFile stream = new RandomAccessFile(f, "rw");
							Shift shift = new Shift();
							stream.seek(item.getBegin());
							stream.writeBytes("0");
							stream.close();
						}
						int length = getLength(item);
						for(FileItem file: fileitems.values())
						{
							if(file.getEmpty()>length)
							{
								File f = new File(root,file.getFileName());
								RandomAccessFile stream = new RandomAccessFile(f, "rw");
								stream.seek(file.getUsed());
								writeItem(item,stream);
								continue first;
							}
						}
						
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public int getLength(WordItem item)
	{
		return (state_len + no_len*5+id_len*item.getsIds().size() +item.getWord().length());
	}
	
	public void writeItem(WordItem item,RandomAccessFile stream) throws IOException
	{
		Shift shift = new Shift();
		
		stream.writeBytes("1");
		stream.writeBytes(shift.leftZeroShift(item.getBegin(), 32));
		stream.writeBytes(shift.leftZeroShift(item.getEnd(), 32));
		stream.writeBytes(shift.leftZeroShift(item.getLength(), 32));
		int max= item.getsIds().size();
		if(max>32) {
			max +=32;
		}else {
			max = max*2;
		}
		stream.writeBytes(shift.leftZeroShift(max, 32));
		stream.writeBytes(shift.leftZeroShift(item.getsIds().size(), 32));
		
		for(String id : item.getsIds())
		{
			stream.writeBytes(id);
		}
		stream.close();
	}
}
