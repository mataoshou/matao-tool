package store.unit;

import java.io.File;
import java.io.RandomAccessFile;

import pojo.store.FileItem;
import pojo.store.WordItem;
import store.StoreUtil;
import store.config.FileConfig;
import store.constant.FileConstant;
import store.constant.FileType;
import store.task.cache.FileCache;
import util.Shift;

public class WordUnit extends IBaseStoreUnit<WordItem>
{

	@Override
	public long getBegin()
	{
		return this.getItem().getBegin();
	}

	@Override
	public String getFileName()
	{
		return FileCache.single().getItem(this.item.getFileId(), FileType.FILE_TYPE_WORD).getFileName();
	}

	@Override
	public boolean isFull()
	{
		if(item.getMax()<item.getsIds().size())
		{
			return false;
		}
		return true;
	}

	@Override
	public String buildFileContent()
	{
		Shift shift = new Shift();
		String result = FileConstant.STATUS_ITEM_DISCARD;
		result +=shift.leftZeroShift(this.item.getBegin(), 32);
		result +=shift.leftZeroShift(this.item.getEnd(), 32);
		result +=shift.leftZeroShift(this.item.getLength(), 32);
		result += shift.leftZeroShift(this.item.getMax(), 32);
		result += shift.leftZeroShift(this.item.getsIds().size(), 32);
		
		for(String id : this.item.getsIds())
		{
			result += id;
		}
		
		for(int i=this.item.getsIds().size();i<this.item.getMax();i++)
		{
			result += shift.leftZeroShift("0", 32);
		}
		
		return result;
	}

	@Override
	public String getId()
	{
		return this.item.getWord();
	}

	@Override
	public void persist()
	{
		try {
			File file = new File(FileConfig.root,getFileName());
			RandomAccessFile stream = new RandomAccessFile(file, "rw");
			stream.seek(item.getBegin());
			
			if(this.item.getMax()>this.item.getMax())
			{
				stream.writeBytes(buildFileContent());
				stream.close();
			}
			else {
				stream.writeBytes(FileConstant.STATUS_ITEM_DISCARD);
				stream.close();
				FileItem fi = FileCache.single().getEmpty(FileType.FILE_TYPE_WORD,buildFileContent().length()*8);
				File newFile = new File(FileConfig.root, fi.getFileName());
				RandomAccessFile newStream = new RandomAccessFile(newFile, "rw");
				newStream.seek(fi.getUsed());
				newStream.writeBytes(buildFileContent());
				newStream.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int getLength() {
		return buildFileContent().length();
	}

	@Override
	public void readItem(RandomAccessFile stream, String fileId) throws Exception {

		item = new WordItem();
		StoreUtil util = new StoreUtil();
		byte[] bs = util.getByte(stream, FileConstant.LENGTH_state);
		
		if(bs==null)return;
		//前8位表示是否继续使用
		int state =Integer.valueOf(new String(bs));
		
		//8*8 存储存储起始位置
		bs = util.getByte(stream, FileConstant.LENGTH_NO);
		long begin =Long.valueOf( new String(bs));
		
		//8*8 存储存储结束位置
		bs = util.getByte(stream, FileConstant.LENGTH_NO);
		long end =Long.valueOf( new String(bs));
		
		//8*8 存储存储长度
		bs = util.getByte(stream, FileConstant.LENGTH_NO);
		int length = Integer.valueOf( new String(bs));					
		
		
		if(state==0)
		{
			bs= util.getByte(stream, length-(FileConstant.LENGTH_NO*3)-FileConstant.LENGTH_state);
			return;
		}
		
		item.setFileId(fileId);
		item.setBegin(begin);
		item.setEnd(end);
		item.setLength(length);
		
		//8*8 存储 最大id个数
		bs = util.getByte(stream, FileConstant.LENGTH_NO);
		item.setMax(Integer.valueOf( new String(bs)));
		
		//8*8 存储  实际使用id个数
		bs = util.getByte(stream, FileConstant.LENGTH_NO);
		item.setCount(Integer.valueOf( new String(bs)));
		
		//计算存储索引word的长度
		int wordLength = item.getLength() - (item.getMax()* FileConstant.LENGTH_NO -8);
		bs = util.getByte(stream, wordLength);
		
		item.setWord(new String(bs,"UTF-8"));
		
		for(int i=0;i<item.getMax();i++)
		{
			bs = util.getByte(stream, FileConstant.LENGTH_ID);
			if(i<item.getCount()) {
				item.addId(new String(bs));
			}
		}
		
		log.log("添加word",item.getWord());
		
	}

}
