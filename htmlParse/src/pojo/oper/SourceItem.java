package pojo.oper;

import java.util.ArrayList;
import java.util.List;

public class SourceItem
{
	private String src;
	
	private int begin;
	
	private int end;
	
	private int interval;
	
	private String[] params;

	public String getSrc()
	{
		return src;
	}

	public void setSrc(String src)
	{
		this.src = src;
	}

	public int getBegin()
	{
		return begin;
	}

	public void setBegin(int begin)
	{
		this.begin = begin;
	}

	public int getEnd()
	{
		return end;
	}

	public void setEnd(int end)
	{
		this.end = end;
	}

	public int getInterval()
	{
		return interval;
	}

	public void setInterval(int interval)
	{
		this.interval = interval;
	}

	public String[] getParams()
	{
		return params;
	}

	public void setParams(String[] params)
	{
		
		List<String> list = new ArrayList();
		
		for(String key : params)
		{
			if(key==null||key.length()==0)continue;
			
			list.add(key);
			
		}
		
		this.params = new String[list.size()];
		
		for(int i=0;i<list.size();i++)
		{
			this.params[i] = list.get(i);
			
		}
	}
	
	
	
}
