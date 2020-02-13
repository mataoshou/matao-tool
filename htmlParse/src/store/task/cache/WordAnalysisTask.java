package store.task.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import pojo.store.FileItem;
import pojo.store.StoreItem;
import store.constant.FileType;
import store.divide.DivideWord;
import store.task.device.ItemDevice;
import store.unit.ContentUnit;
import store.unit.ItemUnit;
import util.GuidUtil;

public class WordAnalysisTask extends IBaseCacheTask<StoreItem> {

	
	private List<StoreItem> list = new ArrayList();

	@Override
	protected void execute() {
		if(list.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				StoreItem item = list.get(i);
				list.remove(i);
				i--;
				
				
				ContentUnit unit = new ContentUnit();
				
				unit.setItem(item);
				
				if(item.getId()!=null&&item.getId().length()>0)
				{
					
				}
				item.setId(GuidUtil.gen());
				FileItem fitem = FileCache.single().getEmpty(FileType.FILE_TYPE_CONTENT, unit.getLength());
				
				long begin = fitem.getUsed()+fitem.getReserve();
				fitem.addReserve(unit.getLength());
				
				item.setCbegin(begin);
				item.setClength(unit.getLength());
				item.setCend(item.getCbegin() + item.getClength());
				
				item.setStoreId(fitem.getId());
				
				
				ItemDevice.single().addTask(fitem.getId(), unit);
				
				
				ItemUnit iunit = new ItemUnit();
				iunit.setItem(item);
				
				fitem = FileCache.single().getEmpty(FileType.FILE_TYPE_ITEM, iunit.getLength());
				
				item.setFileId(fitem.getId());
				item.setIbegin(fitem.getUsed()+fitem.getReserve());
				item.setIlength(iunit.getLength());
				
				
				
				ItemDevice.single().addTask(fitem.getId(), iunit);
				ItemCache.single().add(item);
				
				DivideWord divide = new DivideWord();
				
				for(Entry<String,String> en: item.getContent().entrySet())
				{
					String[] keys = divide.divide(en.getValue());
					
					WordCache.single().addId(keys, item.getId());
				}
				
				
				
			}
		}
	}

	@Override
	public void addItem(StoreItem item) {
		synchronized(this)
		{
			list.add(item);
		}
	}
	
}
