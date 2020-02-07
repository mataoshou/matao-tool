package store.unit;

import java.io.File;
import java.io.RandomAccessFile;

import pojo.store.StoreItem;
import store.config.FileConfig;
import util.Shift;

public class ItemUnit extends IBaseStoreUnit<StoreItem>
{

	@Override
	public String buildFileContent()
	{
		Shift shift = new Shift();
		
		String result = "1";
		
		result += shift.leftZeroShift(item.getBegin(), noLen);
		result += shift.leftZeroShift(item.getEnd(), noLen);
		result += shift.leftZeroShift(item.getLength(), noLen);
		
		result += shift.leftZeroShift(item.getStoreName(), idLen);
		
		return result;
	}

	@Override
	public long getBegin()
	{
		return this.getItem().getBegin();
	}

	@Override
	public String getFileName()
	{
		return this.getItem().getFileName();
	}

	@Override
	public boolean isFull()
	{
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
		try {
			File file = new File(FileConfig.root,this.item.getFileName());
			RandomAccessFile stream = new RandomAccessFile(file, "rw");
			stream.seek(item.getBegin());
			stream.writeBytes(buildFileContent());
			stream.close();
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
