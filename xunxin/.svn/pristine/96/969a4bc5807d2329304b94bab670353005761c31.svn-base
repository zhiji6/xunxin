package xunxin.core;


import java.io.BufferedInputStream;  
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;  
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;  
import java.util.zip.ZipFile;  
import java.util.zip.ZipInputStream;  


public class aaaa {

    public static void main(String[] args) throws Exception {
        try {  
        	//E:\develop\chat
               readZipFile("E:\\develop\\chat\\201711211.zip");  
           } catch (Exception e) {  
               // TODO Auto-generated catch block  
               e.printStackTrace();  
           }  
    	
    	
    	 

    	
    	
    	
    }
    
    public static void readZipFile(String file) throws Exception {  
           ZipFile zf = new ZipFile(file);  
           InputStream in = new BufferedInputStream(new FileInputStream(file));  
           ZipInputStream zin = new ZipInputStream(in);  
           ZipEntry ze;  
           while ((ze = zin.getNextEntry()) != null) {  
               if (ze.isDirectory()) {
               } else {  
                   System.err.println("file - " + ze.getName() + " : "  
                           + ze.getSize() + " bytes");  
                   long size = ze.getSize();  
                   if (size > 0) {  
                       BufferedReader br = new BufferedReader(  
                               new InputStreamReader(zf.getInputStream(ze)));  
                       String line;  
                       while ((line = br.readLine()) != null) {  
                           System.out.println(line);  
                       }  
                       br.close();  
                   }  
                   System.out.println();  
               }  
           }  
           zin.closeEntry();  
       }
    
    
}