package store.unit;

import pojo.store.FileItem;
import pojo.store.StoreItem;
import pojo.store.WordItem;
import store.constant.FileType;
import store.task.cache.FileCache;
import util.GuidUtil;

public class UnitUtil {
	public StoreItem calculateContent(StoreItem item)
	{
		ContentUnit unit = new ContentUnit();
		
		unit.setItem(item);
		FileItem fitem = FileCache.single().getEmpty(FileType.FILE_TYPE_CONTENT, unit.getLength());
		
		long begin = fitem.getUsed()+fitem.getReserve();
		fitem.addReserve(unit.getLength());
		
		item.setCbegin(begin);
		item.setClength(unit.getLength());
		item.setCend(item.getCbegin() + item.getClength());
		item.setStoreId(fitem.getId());
		fitem.addReserve(unit.getLength());
		
		return item;
	}
	
	public StoreItem calculateStore(StoreItem item)
	{
		ItemUnit unit = new ItemUnit();
		unit.setItem(item);
		
		FileItem fitem = FileCache.single().getEmpty(FileType.FILE_TYPE_ITEM, unit.getLength());
		
		item.setFileId(fitem.getId());
		item.setIbegin(fitem.getUsed()+fitem.getReserve());
		item.setIlength(unit.getLength());
		item.setIend(item.getIbegin()+ unit.getLength());
		fitem.addReserve(unit.getLength());
		
		return item;
	}
	
	public WordItem calculateWord(WordItem item)
	{
		WordUnit unit = new WordUnit();
		unit.setItem(item);
		FileItem file = FileCache.single().getEmpty(FileType.FILE_TYPE_WORD, unit.getLength());
		item.setFileId(file.getId());
		item.setBegin(file.getUsed()+file.getReserve());
		item.setEnd(item.getBegin() + unit.getLength());
		file.addReserve(unit.getLength());
		return item;
	}
	
	
	public ContentUnit calculateContent(ContentUnit unit)
	{
		StoreItem item = unit.getItem();
		calculateContent(item);
		
		return unit;
	}
	
	public ItemUnit calculateStore(ItemUnit unit)
	{
		StoreItem item = unit.getItem();
		calculateStore(item);
		
		return unit;
	}
	
	public WordUnit calculateWord(WordUnit unit)
	{
		WordItem item = unit.getItem();
		calculateWord(item);
		return unit;
	}
	
	public ContentUnit buildContentUnit(StoreItem item)
	{
		ContentUnit unit = new ContentUnit();
		unit.setItem(item);
		return unit;
	}
	
	public ItemUnit buildStoreUnit(StoreItem item)
	{
		ItemUnit unit = new ItemUnit();
		unit.setItem(item);
		return unit;
	}
	
	
	public WordUnit buildWordUnit(WordItem item)
	{
		WordUnit unit = new WordUnit();
		unit.setItem(item);
		return unit;
	}
}
