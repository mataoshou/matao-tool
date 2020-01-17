package store.unit;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import pojo.store.StoreItem;
import store.StoreUtil;

public class ContentUnit extends IBaseStoreUnit<StoreItem>
{

	@Override
	public String buildFileContent()
	{
		StoreUtil util = new StoreUtil();
		
		String result ="1";
		String content ="";
		for(Entry<String,String> e : this.getItem().getContent().entrySet())
		{
			String val = util.encry(util.keys[1],String.format("%s=%s", e.getKey(),e.getValue()));
			if(content.equals(""))
			{
				content += "&";
			}
			content += val;
		}
		result += util.encry(util.keys[0],content);
		
		return result;
	}

	@Override
	public long getBegin()
	{
		// TODO Auto-generated method stub
		return item.getBegin();
	}

	@Override
	public String getFileName()
	{
		// TODO Auto-generated method stub
		return item.getStoreName();
	}

	@Override
	public boolean isFull()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	public Map getContent(File root) throws Exception
	{
		File storeFile = new File(root,this.item.getStoreName());
		FileInputStream input = new FileInputStream(storeFile);
		StoreUtil util = new StoreUtil();
		
		byte[] bs = util.getByte(input, item.getLength());
		String content = new String(bs);
		content = util.decrypt(util.keys[0], content);
		String[] values = content.split("&");
		
		for(String val : values)
		{
			String str = util.decrypt(util.keys[1], val);
			String[] kv = str.split("=");
			item.addContent(kv[0], kv[1]);
		}
		
		return item.getContent();
	}

}
