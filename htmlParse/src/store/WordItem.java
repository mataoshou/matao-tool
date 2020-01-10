package store;

import java.util.List;

public class WordItem
{
	private String word;
	
	private List<Long> sIds;

	public String getWord()
	{
		return word;
	}

	public void setWord(String word)
	{
		this.word = word;
	}

	public List<Long> getsIds()
	{
		return sIds;
	}

	public void setsIds(List<Long> sIds)
	{
		this.sIds = sIds;
	}
	
	
	public void addId(Long id)
	{
		this.sIds.add(id);
	}
}
