package automatic.oper;

import java.util.ArrayList;
import java.util.List;

public class GroupItem
{
	private List<SourceItem> sources = new ArrayList();
	
	private List<OperItem> opers = new ArrayList();
	
	private int interval;
	
	private int count;
	
	private String name;
	
	private int show;
	
	
	
	public int getShow()
	{
		return show;
	}
	public void setShow(int show)
	{
		this.show = show;
	}
	public void addSource(SourceItem item)
	{
		sources.add(item);
	}
	public void addOpers(OperItem item)
	{
		opers.add(item);
	}

	public List<SourceItem> getSources()
	{
		return sources;
	}

	public void setSources(List<SourceItem> sources)
	{
		this.sources = sources;
	}

	public List<OperItem> getOpers()
	{
		return opers;
	}

	public void setOpers(List<OperItem> opers)
	{
		this.opers = opers;
	}

	public int getInterval()
	{
		return interval;
	}

	public void setInterval(int interval)
	{
		this.interval = interval;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	
}
