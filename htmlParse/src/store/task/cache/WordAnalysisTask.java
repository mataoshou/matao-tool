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
import store.unit.UnitUtil;
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
				
				
				UnitUtil util = new UnitUtil();
				item.setId(GuidUtil.gen());
				
				ContentUnit unit = util.calculateContent(util.buildContentUnit(item));		
				
				ItemDevice.single().addTask(unit.getFileId(), unit);
				
				
				ItemUnit iunit = util.calculateStore(util.buildStoreUnit(item));
				
				ItemDevice.single().addTask(iunit.getFileId(), iunit);
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
