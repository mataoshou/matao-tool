package store;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import pojo.store.FileItem;
import util.DomUtil;

public class FileCache
{
	Map<String,FileItem> items = new HashMap();
	Map<String,FileItem> words = new HashMap();
	Map<String,FileItem> stores = new HashMap();
	
	private static FileCache one;
	
	public static FileCache single()
	{
		return one;
	}
	
	
	private FileCache() {
	}
	
	
	public void load(File root ,String fileName) throws Exception
	{
		initStore(root,fileName);
	}
	
	/**
	 * 初始化
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	private void initStore(File root ,String fileName) throws Exception
	{
		File house = new File(root,fileName);
		Document doc = DomUtil.getDocument(house);
		
		Element rootEle = doc.getRootElement();
		
		List<Element> itemsEle = rootEle.element("items").elements("item");
		
		for(Element ele: itemsEle)
		{
			FileItem item =getFileItem(ele);
			items.put(item.getFileName(),item);
		}
		
		List<Element> wordsEle = rootEle.element("words").elements("word");
		for(Element ele: wordsEle)
		{
			FileItem item =getFileItem(ele);
			words.put(item.getFileName(),item);
		}
		
		List<Element> storeEle = rootEle.element("stores").elements("store");
		for(Element ele: storeEle)
		{
			FileItem item =getFileItem(ele);
			stores.put(item.getFileName(),item);
		}
	}
	
	public FileItem getFileItem(Element ele)
	{
		FileItem item = new FileItem();
		item.setFilePath(ele.attributeValue("fileName"));
		item.setFileName(ele.attributeValue("fileName"));
		item.setMax(Long.valueOf(ele.attributeValue("max")));
		item.setUsed(Long.valueOf(ele.attributeValue("use")));
		item.setEmpty(Long.valueOf(ele.attributeValue("empty")));
		item.setStoreCount(Integer.valueOf(ele.attributeValue("conut")));
		
		return item;
	}
	
	public void getNewFile()
	{
		
	}
}
