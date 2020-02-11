package pojo.store;

import java.util.ArrayList;
import java.util.List;

public class WordItem
{
	private long begin;
	
	private long end;
	
	private int length;
	
	private int count;
	
	private String word;
	
	private List<String> sIds = new ArrayList();
	
	private int max;
	
	private int mod=0;
	
	private String fileId;

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}
	
	

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public int getMod()
	{
		return mod;
	}

	public void setMod(int mod)
	{
		this.mod = mod;
	}

	public long getBegin()
	{
		return begin;
	}

	public void setBegin(long begin)
	{
		this.begin = begin;
	}

	public long getEnd()
	{
		return end;
	}

	public void setEnd(long end)
	{
		this.end = end;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public String getWord()
	{
		return word;
	}

	public void setWord(String word)
	{
		this.word = word;
	}

	public List<String> getsIds()
	{
		return sIds;
	}

	public void setsIds(List<String> sIds)
	{
		this.sIds = sIds;
	}
	
	
	public void addId(String id)
	{
		this.sIds.add(id);
		mod++;
	}
	
	public void removeId(String id)
	{
		this.sIds.remove(id);
		mod++;
	}

	public int getMax()
	{
		return max;
	}

	public void setMax(int max)
	{
		this.max = max;
	}
	
	
}
