package store.task.cache;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import log.Logger;
import pojo.store.FileItem;
import store.config.FileConfig;
import store.constant.FileConstant;
import store.constant.FileType;
import util.DomUtil;
import util.Shift;

public class FileCache
{
	public Map<String,FileItem> items = new HashMap();
	public Map<String,FileItem> words = new HashMap();
	public Map<String,FileItem> contents = new HashMap();
	
	public String itemEmpty  ="";
	public String wordEmpty ="";
	public String contentEmpty ="";
	
	private static FileCache one = new FileCache();
	
	public static FileCache single()
	{
		return one;
	}
	
	Logger log = new Logger(this.getClass());
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
		try {
			initStore(root,fileName);
		}
		catch(Exception e)
		{
			log.log("文件列表加载失败",e.getMessage());
		}
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
		log.log("开始加载","存储文件信息");
		File house = new File(root,fileName);
		Document doc = DomUtil.getDocument(house);
		
		Element rootEle = doc.getRootElement();
		
		List<Element> itemsEle = rootEle.element("items").elements("item");
		
		for(Element ele: itemsEle)
		{
			FileItem item =getFileItem(ele);
			item.setType(FileType.FILE_TYPE_ITEM);
			if(isExist(item))
			{
				items.put(item.getId(),item);
			}
		}
		
		FileItem iitem  = getNewFile(FileType.FILE_TYPE_ITEM);
		items.put(iitem.getId(), iitem);
		itemEmpty = iitem.getId();
		
		log.log("item文件加载完成","预留文件",itemEmpty);
		List<Element> wordsEle = rootEle.element("words").elements("word");
		for(Element ele: wordsEle)
		{
			FileItem item =getFileItem(ele);
			item.setType(FileType.FILE_TYPE_WORD);
			
			if(isExist(item))
			{
				words.put(item.getId(),item);
			}
		}
		
		FileItem witem  = getNewFile(FileType.FILE_TYPE_WORD);
		words.put(witem.getId(), witem);
		wordEmpty = witem.getId();
		
		log.log("word文件加载完成","预留文件",wordEmpty);
		List<Element> storeEle = rootEle.element("contents").elements("content");
		for(Element ele: storeEle)
		{
			FileItem item =getFileItem(ele);
			item.setType(FileType.FILE_TYPE_CONTENT);
			if(isExist(item))
			{
				contents.put(item.getId(),item);
			}
		}
		
		FileItem citem  = getNewFile(FileType.FILE_TYPE_CONTENT);
		contents.put(citem.getId(), citem);
		contentEmpty = citem.getId();
		
//		System.out.println("content完成，预留文件:"+contentEmpty);
		log.log("content文件加载完成","预留文件",contentEmpty);
		
		log.log("完成加载","存储文件信息");
	}
	
	private boolean isExist(FileItem item)
	{
		File file = new File(FileConfig.root,item.getFileName());
		if(file.exists())
		{
			return true;
		}
		return false;
	}
	
	private FileItem getFileItem(Element ele)
	{
		FileItem item = new FileItem();
		item.setId(ele.attributeValue("id"));
		item.setFileName(ele.attributeValue("fileName"));
		item.setMax(Long.valueOf(ele.attributeValue("max")));
		item.setUsed(Long.valueOf(ele.attributeValue("use")));
		item.setEmpty(Long.valueOf(ele.attributeValue("empty")));
		item.setStoreCount(Integer.valueOf(ele.attributeValue("count")));
		
		return item;
	}
	
	//构建新的空文件对象
	private FileItem getNewFile(String type)
	{
		switch(type)
		{
			case FileType.FILE_TYPE_ITEM: {
				int no = items.size()+1;
				while(true)
				{
					String fileName= "itemhouse_"+no +".mt";
					if(items.get(fileName)==null)
					{
						return buildEmpty(FileType.FILE_TYPE_ITEM, fileName,no);
					}
					else {
						no++;
					}
				}
			}
			case FileType.FILE_TYPE_WORD: {
				int no = words.size()+1;
				while(true)
				{
					String fileName= "wordhouse_"+no+".mt";
					if(words.get(fileName)==null)
					{
						return buildEmpty(FileType.FILE_TYPE_WORD, fileName,no);
					}
					else {
						no++;
					}
					
				}
			}
			case FileType.FILE_TYPE_CONTENT: {
				int no = contents.size()+1;
				while(true)
				{
					String fileName= "contenthouse_"+no+".mt";
					if(contents.get(fileName)==null)
					{
						return buildEmpty(FileType.FILE_TYPE_CONTENT, fileName,no);
					}
					else {
						no++;
					}
				}
			}
			default:return null;
		}
	}
	
	private FileItem buildEmpty(String type,String fileName,int id)
	{
		Shift shift = new Shift();
		FileItem item = new FileItem();
		item.setId(shift.leftZeroShift((getPre(type)*10000)+id, FileConstant.LENGTH_NO));
		item.setFileName(fileName);
		item.setMax(FileConstant.FILE_STORE_MAX_SPACE);
		item.setEmpty(FileConstant.FILE_STORE_MAX_SPACE);
		item.setUsed(0);
		item.setIsExit(0);
		item.setType(type);
		item.setStoreCount(0);
		return item;
	}
	
	public int getPre(String type)
	{
		switch(type)
		{
			case FileType.FILE_TYPE_ITEM :return 1;
			case FileType.FILE_TYPE_CONTENT :return 2;
			case FileType.FILE_TYPE_WORD :return 3;
		}
		
		return 9;
	}
	
	
	public void saveFile() throws IOException, DocumentException
	{
		if(mod==0)return;
		
		File house = new File(FileConfig.root,FileConfig.fileHouse);
		
		Element root = DocumentHelper.createElement("root");;
		
		
		
		Map<String,FileItem>[] maps =new Map[] {
				items,words,contents
		};
		
		String[] names = new String[] {"item","word","content"};
		
		for(int i=0;i<3;i++)
		{
			Map<String,FileItem> map = maps[i];
			Element pEle = DocumentHelper.createElement(names[i]  +"s");
			for(Entry<String,FileItem> entry:map.entrySet())
			{
				FileItem fitem = entry.getValue();
				
				Element ele = DocumentHelper.createElement(names[i]);
				ele.addAttribute("id", fitem.getId());
				ele.addAttribute("fileName", fitem.getFileName());
				ele.addAttribute("max", String.valueOf(fitem.getMax()));
				ele.addAttribute("use", String.valueOf(fitem.getUsed()));
				ele.addAttribute("empty", String.valueOf(fitem.getEmpty()));
				ele.addAttribute("count", String.valueOf(fitem.getStoreCount()));
				
				pEle.add(ele);
			}
			root.add(pEle);
		}
		
		Document doc = DocumentHelper.createDocument();
		doc.add(root);
		DomUtil.writeDocument(doc, new File(FileConfig.root,FileConfig.fileHouse));
	}
	
	private int mod =0;
	
	public void modAdd()
	{
		mod++;
	}
}
