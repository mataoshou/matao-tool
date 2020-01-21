package store.unit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import pojo.store.FileItem;
import pojo.store.StoreItem;
import store.FileCache;
import store.StoreUtil;
import store.config.FileConfig;
import store.constant.FileType;
import util.Shift;

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
		return item.getBegin();
	}

	@Override
	public String getFileName()
	{
		return item.getStoreName();
	}

	@Override
	public boolean isFull()
	{
		if((buildFileContent().length()*8)>item.getLength())
		{
			return false;
		}
		
		return true;
	}
	
	public Map getContent() throws Exception
	{
		File storeFile = new File(FileConfig.root,this.item.getStoreName());
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

	@Override
	public String getId()
	{
		return this.item.getId();
	}

	@Override
	public void persist()
	{
		try
		{
			File file = new File(FileConfig.fileHouse,this.item.getFileName());
			RandomAccessFile stream = new RandomAccessFile(file, "rw");
			stream.seek(item.getBegin());
			if(isFull())
			{
				stream.seek(item.getBegin());
				stream.writeBytes(buildFileContent());
				stream.close();
			}
			else {
				stream.writeBytes("0");
				stream.close();
				FileItem fi = FileCache.single().getEmpty(FileType.FILE_TYPE_CONTENT,buildFileContent().length()*8);
				File newFile = new File(FileConfig.root, fi.getFileName());
				RandomAccessFile newStream = new RandomAccessFile(newFile, "rw");
				newStream.seek(fi.getUsed());
				newStream.writeBytes(buildFileContent());
				newStream.close();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}


}
