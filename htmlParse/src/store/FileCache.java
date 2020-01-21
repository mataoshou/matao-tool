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
import store.config.FileConfig;
import store.constant.FileConstant;
import store.constant.FileType;
import util.DomUtil;

public class FileCache
{
	Map<String,FileItem> items = new HashMap();
	Map<String,FileItem> words = new HashMap();
	Map<String,FileItem> contents = new HashMap();
	
	public String itemEmpty  ="";
	public String wordEmpty ="";
	public String contentEmpty ="";
	
	private static FileCache one;
	
	public static FileCache single()
	{
		return one;
	}
	
	
	private FileCache() {
		
	}
	
	/**
	 * 加载文件
	 * @param root
	 * @param fileName
	 * @throws Exception
	 */
	public void load(File root ,String fileName) throws Exception
	{
		initStore(root,fileName);
	}
	/**
	 * 获取文件对象
	 * 
	 * @param key
	 * @param type
	 * @return
	 */
	public FileItem getItem(String key ,String type)
	{
		switch(type) {
			case FileType.FILE_TYPE_CONTENT: return contents.get(key);
			case FileType.FILE_TYPE_ITEM: return items.get(key);
			case FileType.FILE_TYPE_WORD: return words.get(key);
		}
		return null;
	}
	
	/**
	 *获取空文件对象 
	 */
	public FileItem getEmpty(String type,int length)
	{
		FileItem item = null;
		switch(type) {
			case FileType.FILE_TYPE_CONTENT: item = contents.get(contentEmpty);break;
			case FileType.FILE_TYPE_ITEM: item = items.get(itemEmpty);break;
			case FileType.FILE_TYPE_WORD: item = words.get(wordEmpty);break;
		}
		
		if(item.getEmpty()>length)
		{
			return item;
		}
		else {
			item = getNewFile(type);
			switch(type) {
				case FileType.FILE_TYPE_CONTENT: contents.put(item.getFileName(), item);break;
				case FileType.FILE_TYPE_ITEM: items.put(item.getFileName(), item);break;
				case FileType.FILE_TYPE_WORD: words.put(item.getFileName(), item);break;
			}
		}
		return item;
	}
	
	/**
	 * 初始化
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	private void initStore(File root ,String fileName) throws Exception
	{
		System.out.println("开始加载【存储文件信息】");
		File house = new File(root,fileName);
		Document doc = DomUtil.getDocument(house);
		
		Element rootEle = doc.getRootElement();
		
		List<Element> itemsEle = rootEle.element("items").elements("item");
		
		for(Element ele: itemsEle)
		{
			FileItem item =getFileItem(ele);
			item.setType(FileType.FILE_TYPE_ITEM);
			items.put(item.getFileName(),item);
		}
		
		FileItem iitem  = getNewFile(FileType.FILE_TYPE_ITEM);
		items.put(iitem.getFileName(), iitem);
		itemEmpty = iitem.getFileName();
		
		System.out.println("item完成，预留文件:"+itemEmpty);
		
		List<Element> wordsEle = rootEle.element("words").elements("word");
		for(Element ele: wordsEle)
		{
			FileItem item =getFileItem(ele);
			item.setType(FileType.FILE_TYPE_WORD);
			words.put(item.getFileName(),item);
		}
		
		FileItem witem  = getNewFile(FileType.FILE_TYPE_WORD);
		words.put(witem.getFileName(), witem);
		wordEmpty = witem.getFileName();
		
		System.out.println("word完成，预留文件:"+wordEmpty);
		
		List<Element> storeEle = rootEle.element("stores").elements("store");
		for(Element ele: storeEle)
		{
			FileItem item =getFileItem(ele);
			item.setType(FileType.FILE_TYPE_CONTENT);
			contents.put(item.getFileName(),item);
		}
		
		FileItem citem  = getNewFile(FileType.FILE_TYPE_CONTENT);
		contents.put(citem.getFileName(), citem);
		contentEmpty = citem.getFileName();
		
		System.out.println("content完成，预留文件:"+contentEmpty);
		
		System.out.println("完成加载【存储文件信息】");
	}
	
	private FileItem getFileItem(Element ele)
	{
		FileItem item = new FileItem();
		item.setFilePath(FileConfig.root.getPath());
		item.setFileName(ele.attributeValue("fileName"));
		item.setMax(Long.valueOf(ele.attributeValue("max")));
		item.setUsed(Long.valueOf(ele.attributeValue("use")));
		item.setEmpty(Long.valueOf(ele.attributeValue("empty")));
		item.setStoreCount(Integer.valueOf(ele.attributeValue("conut")));
		
		return item;
	}
	
	//构建新的空文件对象
	private FileItem getNewFile(String type)
	{
		switch(type)
		{
			case FileType.FILE_TYPE_ITEM: {
				int no = items.size();
				while(true)
				{
					String fileName= "itemhouse_"+no;
					if(items.get(fileName)==null)
					{
						return buildEmpty(FileType.FILE_TYPE_ITEM, fileName);
					}
					else {
						no++;
					}
				}
			}
			case FileType.FILE_TYPE_WORD: {
				int no = words.size();
				while(true)
				{
					String fileName= "wordhouse_"+no;
					if(words.get(fileName)==null)
					{
						return buildEmpty(FileType.FILE_TYPE_WORD, fileName);
					}
					else {
						no++;
					}
					
				}
			}
			case FileType.FILE_TYPE_CONTENT: {
				int no = contents.size();
				while(true)
				{
					String fileName= "contenthouse_"+no;
					if(contents.get(fileName)==null)
					{
						return buildEmpty(FileType.FILE_TYPE_CONTENT, fileName);
					}
					else {
						no++;
					}
				}
			}
			default:return null;
		}
	}
	
	private FileItem buildEmpty(String type,String fileName)
	{
		FileItem item = new FileItem();
		item.setFileName(fileName);
		item.setFilePath(FileConfig.root.getPath());
		item.setMax(FileConstant.FILE_STORE_MAX_SPACE);
		item.setEmpty(FileConstant.FILE_STORE_MAX_SPACE);
		item.setUsed(0);
		item.setIsExit(0);
		item.setType(FileType.FILE_TYPE_CONTENT);
		item.setStoreCount(0);
		return item;
	}
}
