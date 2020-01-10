package store;

import java.io.File;
import java.security.Key;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import util.HexUtil;

public class StoreUtil
{
	
	public List<FileItem> loadFile(File file)
	{
		List<FileItem> list = new ArrayList();
		
		
		
		return list;
	}
	
	public void getWords(String key)
	{
		
		
		
	}
	
	
	/**
	 * 文件存储规则  
	 * 每个对象 前32个字节存储长度  8个字节可以存储个数  8个字节实际使用个数  
	 * 通过计算得出word存储位置  
	 * 每个id长度16个字节  预留十个存储位置
	 */
	public void saveToFile(WordItem item)
	{
		
	}
	
	
	private String[] keys= new String[]{"1111111mtgmz1111","2222222mtgmz2222","3333333mtgmz3333"};
	
	// 加密
    public String encry(String keyStr ,String content)    {
        try {
            // 获取密钥
            byte[] keyBytes = keyStr.getBytes();
            
            // KEY转换
            Key key = new SecretKeySpec(keyBytes, "AES");
            // 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal("马焘".getBytes());
            String resultStr =HexUtil.to(result);
            return resultStr;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "";
    }
    
    //解密
    public String decrypt(String keyStr ,String content)    {
        try {
            // 获取密钥
            byte[] keyBytes = keyStr.getBytes();
            
            // KEY转换
            Key key = new SecretKeySpec(keyBytes, "AES");
            
            // 解密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(HexUtil.from(content));
           
            return new String(result);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		return "";
    }
    
    public static void main(String[] args) {
    	StoreUtil util = new StoreUtil();
    	String ency = util.encry(util.keys[0],"matao123");
    	String result = util.decrypt(util.keys[0], ency);
    	
    	System.out.println(ency);
    	System.out.println(result);
    }
}
