package store.task.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import pojo.store.FileItem;
import pojo.store.StoreItem;
import store.FileCache;
import store.WordCache;
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
				fitem.addReserve(item.getLength());
				
				item.setBegin(begin);
				item.setLength(unit.getLength());
				item.setEnd(item.getBegin() + item.getLength());
				
				item.setStoreName(fitem.getFileName());
				
				
				ItemDevice.single().addTask(fitem.getFileName(), unit);
				
				
				ItemUnit iunit = new ItemUnit();
				iunit.setItem(item);
				
				fitem = FileCache.single().getEmpty(FileType.FILE_TYPE_ITEM, iunit.getLength());
				
				item.setFileName(fitem.getFileName());
				
				
				
				
				ItemDevice.single().addTask(fitem.getFileName(), iunit);
				
				
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
