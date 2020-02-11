package pojo.store;

import java.util.HashMap;
import java.util.Map;

public class StoreItem
{
	//32字节  前4存储文件id  12位存储时间戳   16 字符 md5
	private String id;
	
	private String name;
	
	private long ibegin;
	private long iend;
	private long ilength;
	
	
	//
	private long cbegin;
	private long cend;
	private int clength;
	
	private String fileId;
	
	//32字节  存储文件名称
	private String storeId;

	private Map<String,String> content = new HashMap();
	
	private int mod = 0;
	
	
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	
	
	public int getMod()
	{
		return mod;
	}

	public void setMod(int mod)
	{
		this.mod = mod;
	}


	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	

	public long getIbegin() {
		return ibegin;
	}

	public void setIbegin(long ibegin) {
		this.ibegin = ibegin;
	}

	public long getIend() {
		return iend;
	}

	public void setIend(long iend) {
		this.iend = iend;
	}

	public long getIlength() {
		return ilength;
	}

	public void setIlength(long ilength) {
		this.ilength = ilength;
	}

	public long getCbegin() {
		return cbegin;
	}

	public void setCbegin(long cbegin) {
		this.cbegin = cbegin;
	}

	public long getCend() {
		return cend;
	}

	public void setCend(long cend) {
		this.cend = cend;
	}

	public int getClength() {
		return clength;
	}

	public void setClength(int clength) {
		this.clength = clength;
	}

	public Map<String, String> getContent()
	{
		return content;
	}

	public void setContent(Map<String, String> content)
	{
		this.content = content;
	}
	
	public void addContent(String key ,String value)
	{
		this.content.put(key, value);
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	
	
}
