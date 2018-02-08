package com.xunxin.util.app.chat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class uploadUtils {
	/**
	 * @功能 下载临时素材接口
	 * @param filePath 文件将要保存的目录
	 * @param method 请求方法，包括POST和GET
	 * @param url 请求的路径
	 * @return
	 */

	public static File saveUrlAs(String url,String filePath,String method,String fileName){
		 //System.out.println("fileName---->"+filePath);
		 //创建不同的文件夹目录
		 File file=new File(filePath);
		 //判断文件夹是否存在
		 if (!file.exists())
		{
			//如果文件夹不存在，则创建新的的文件夹
			 file.mkdirs();
		}
		 FileOutputStream fileOut = null;
		 HttpURLConnection conn = null;
		 InputStream inputStream = null;
		 try
		{
			 // 建立链接
			 URL httpUrl=new URL(url);
			 conn=(HttpURLConnection) httpUrl.openConnection();
			 //以Post方式提交表单，默认get方式
			 conn.setRequestMethod(method);
		     conn.setDoInput(true);  
		     conn.setDoOutput(true);
		     // post方式不能使用缓存 
		     conn.setUseCaches(false);
		     //连接指定的资源 
		     conn.connect();
		     //获取网络输入流
		     inputStream=conn.getInputStream();
		     BufferedInputStream bis = new BufferedInputStream(inputStream);
		     //判断文件的保存路径后面是否以/结尾
		     if (!filePath.endsWith("/")) {

		    	 filePath += "/";

		    	 }
		     //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
	         fileOut = new FileOutputStream(filePath+fileName);
	         BufferedOutputStream bos = new BufferedOutputStream(fileOut);
	         
	         byte[] buf = new byte[4096];
	         int length = bis.read(buf);
	         //保存文件
	         while(length != -1)
	         {
	        	 bos.write(buf, 0, length);
	        	 length = bis.read(buf);
	         }
	         bos.close();
	         bis.close();
	         conn.disconnect();
		} catch (Exception e)
		{
			 e.printStackTrace();
			 System.out.println("抛出异常！！");
		}
		 
		 return file;
		 
	 }
	
	 public static void main(String[] args)  
	    {  
//	        String photoUrl = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
	        String photoUrl = "http://ebs-chatmessage-a1.easemob.com/history/3D/1169171019178830/followheart/2017112209.gz?Expires=1511328461&OSSAccessKeyId=LTAIlKPZStPokdA8&Signature=s2iPKFT%2B6Vv52wbzDFkc3T9KVB4%3D";
	        String[] split = photoUrl.split("\\?");
	        String fileName = split[0].substring(split[0].lastIndexOf("/"));   
	        //System.out.println("fileName---->"+fileName);  
	        String filePath = "E:\\develop\\chat";    
	        File file = saveUrlAs(photoUrl, filePath,"GET",fileName);    
	        System.out.println("Run ok!/n<BR>Get URL file " + file);    
	  
	    }  

}
