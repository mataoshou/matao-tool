package store;

import java.io.File;
import java.util.List;

import pojo.store.FileItem;

public interface ICache<T>
{
	void loadCache(File root);
	void saveCache(File root);
	
	void edit(String key,T item);
}
