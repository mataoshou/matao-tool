package store.unit;

import java.io.File;
import java.io.RandomAccessFile;

import pojo.store.StoreItem;
import store.StoreUtil;
import store.config.FileConfig;
import store.constant.FileConstant;
import store.constant.FileType;
import store.task.cache.FileCache;
import util.Shift;

public class ItemUnit extends IBaseStoreUnit<StoreItem>
{

	@Override
	public String buildFileContent()
	{
		Shift shift = new Shift();
		
		String result = FileConstant.STATUS_ITEM_USE;
		result += shift.leftZeroShift(item.getId(), FileConstant.LENGTH_ID);
		result += shift.leftZeroShift(item.getCbegin(), FileConstant.LENGTH_NO);
		result += shift.leftZeroShift(item.getCend(), FileConstant.LENGTH_NO);
		result += shift.leftZeroShift(item.getClength(), FileConstant.LENGTH_NO);
		
		result += shift.leftZeroShift(item.getStoreId(), FileConstant.LENGTH_NO);
		
		return result;
	}

	@Override
	public long getBegin()
	{
		return this.getItem().getIbegin();
	}

	@Override
	public String getFileName()
	{
		return FileCache.single().getItem(this.item.getFileId(), FileType.FILE_TYPE_ITEM).getFileName();
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
			File file = new File(FileConfig.root,getFileName());
			RandomAccessFile stream = new RandomAccessFile(file, "rw");
			stream.seek(item.getIbegin());
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

	@Override
	public void readItem(RandomAccessFile stream,String fileId) throws Exception {
		
		if(this.item==null)this.item = new StoreItem();
		
		StoreUtil util = new StoreUtil();
		byte[] bs = util.getByte(stream,FileConstant.LENGTH_state);
		int status = Integer.valueOf(new String(bs));
		if(status==1)
		{
		}
		bs = util.getByte(stream,FileConstant.LENGTH_ID);
		item.setId(new String(bs));
		
		bs = util.getByte(stream, FileConstant.LENGTH_NO);
		item.setCbegin(Long.valueOf( new String(bs)));
		bs = util.getByte(stream, FileConstant.LENGTH_NO);
		item.setCend(Long.valueOf( new String(bs)));
		bs = util.getByte(stream, FileConstant.LENGTH_NO);
		item.setClength(Integer.valueOf( new String(bs)));
		
		bs = util.getByte(stream, FileConstant.LENGTH_NO);
		item.setStoreId(new String(bs));

		item.setFileId(fileId);
		
		log.log("添加item",item.getId());
		
	}

}
