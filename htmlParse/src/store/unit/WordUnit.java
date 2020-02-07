package store.unit;

import java.io.File;
import java.io.RandomAccessFile;

import pojo.store.FileItem;
import pojo.store.WordItem;
import store.FileCache;
import store.config.FileConfig;
import store.constant.FileType;
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
		return this.item.getFileName();
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
		String result = "1";
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
			File file = new File(FileConfig.root,this.item.getFileName());
			RandomAccessFile stream = new RandomAccessFile(file, "rw");
			stream.seek(item.getBegin());
			
			if(this.item.getMax()>this.item.getMax())
			{
				stream.writeBytes(buildFileContent());
				stream.close();
			}
			else {
				stream.writeBytes("0");
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

}
