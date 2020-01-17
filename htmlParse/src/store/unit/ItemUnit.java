package store.unit;

import pojo.store.StoreItem;
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
		
		return "";
	}

	@Override
	public long getBegin()
	{
		// TODO Auto-generated method stub
		return this.getItem().getBegin();
	}

	@Override
	public String getFileName()
	{
		// TODO Auto-generated method stub
		return this.getItem().getFileName();
	}

	@Override
	public boolean isFull()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
