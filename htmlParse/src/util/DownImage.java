package util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownImage
{
	// 下载网络图片到本地
	public void saveToFile(String destUrl, File dst)
	{
		FileOutputStream out = null;
		BufferedInputStream input = null;
		HttpURLConnection conn = null;
		byte[] buffer = new byte[1024 * 10];// 每次获取10K
		try
		{
			// 连接文件的网络路径
			URL url = new URL(destUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			input = new BufferedInputStream(conn.getInputStream());// 读取文件的流
			out = new FileOutputStream(dst);// 保存文件的输出流
			int size = 0;
			// 读取文件,输出到指定的文件中
			while ((size = input.read(buffer)) != -1)
			{
				out.write(buffer, 0, size);
			}
			System.out.println("完成文件保存...");
		} catch (IOException e)
		{
			//e.printStackTrace();
			System.out.println("下载失败:"+destUrl);
		} catch (ClassCastException e)
		{
			//e.printStackTrace();
			System.out.println("下载失败:"+destUrl);
		} finally
		{//关闭连接
			try
			{
				out.close();
				input.close();
				conn.disconnect();
			} catch (IOException e)
			{
				//e.printStackTrace();
				System.out.println("下载结束");
			}
		}

	}

	public static void main(String[] args)
	{
		DownImage image = new DownImage();
		File f = new File("c:/image.jpg");
		image.saveToFile(
				"http://127.0.0.1:8080/file/3579CCD2BB5943628E84BA596FB71867.jpg",
				f);
	}
}
