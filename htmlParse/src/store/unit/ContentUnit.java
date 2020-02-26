package store.unit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import pojo.store.FileItem;
import pojo.store.StoreItem;
import store.StoreUtil;
import store.config.FileConfig;
import store.constant.FileConstant;
import store.constant.FileType;
import store.task.cache.FileCache;
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
			if(!content.equals(""))
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
		return item.getCbegin();
	}

	@Override
	public String getFileName()
	{
		return FileCache.single().getItem(this.item.getStoreId(), FileType.FILE_TYPE_CONTENT).getFileName();
	}

	@Override
	public boolean isFull()
	{
		if((buildFileContent().length()*8)>item.getClength())
		{
			return false;
		}
		
		return true;
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
			File file = new File(FileConfig.root,getFileName());
			RandomAccessFile stream = new RandomAccessFile(file, "rw");
			stream.seek(item.getCbegin());
			if(isFull())
			{
				stream.seek(item.getCbegin());
				stream.writeBytes(buildFileContent());
				stream.close();
			}
			else {
				stream.writeBytes(FileConstant.STATUS_ITEM_DISCARD);
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

	@Override
	public int getLength() {
		return buildFileContent().length();
	}

	@Override
	public void readItem(RandomAccessFile stream,String fileName) throws Exception {
		
		File file = new File(FileConfig.root,getFileName());
		RandomAccessFile input = new RandomAccessFile(file, "rw");
		StoreUtil util = new StoreUtil();
		
		input.seek(item.getCbegin());
		
		byte[] bs = util.getByte(input, FileConstant.LENGTH_state);
		
		bs = util.getByte(input, item.getClength()-FileConstant.LENGTH_state);
		String content = new String(bs);
		content = util.decrypt(util.keys[0], content);
		String[] values = content.split("&");
		
		for(String val : values)
		{
			String str = util.decrypt(util.keys[1], val);
			String[] kv = str.split("=");
			item.addContent(kv[0], kv[1]);
		}
		log.log("添加content",item.getId());
		input.close();
	}
	
	public void readItem() throws Exception
	{
		readItem(null,null);
	}

	@Override
	public String getFileId() {
		return this.item.getStoreId();
	}


}
