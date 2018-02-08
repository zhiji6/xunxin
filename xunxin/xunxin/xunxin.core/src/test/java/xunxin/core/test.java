
package xunxin.core;
import java.io.*;
import java.util.zip.GZIPInputStream;

import com.alibaba.fastjson.JSONObject;
import com.xunxin.util.JacksonUtil;

public class test {

    /**
     * Uncompress the incoming file.  
     * @param inFileName Name of the file to be uncompressed  
     */
    private static void doUncompressFile(String inFileName) {

        try {

            if (!getExtension(inFileName).equalsIgnoreCase("gz")) {
                System.err.println("File name must have extension of \".gz\"");
                System.exit(1);
            }

            System.out.println("Opening the compressed file.");
            GZIPInputStream in = null;
            try {
                in = new GZIPInputStream(new FileInputStream(inFileName));
            } catch(FileNotFoundException e) {
                System.err.println("File not found. " + inFileName);
                System.exit(1);
            }

            System.out.println("Open the output file.");
            String outFileName = getFileName(inFileName);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(outFileName);
            } catch (FileNotFoundException e) {
                System.err.println("Could not write to file. " + outFileName);
                System.exit(1);
            }

            System.out.println("Transfering bytes from compressed file to the output file.");
            byte[] buf = new byte[1024];
            int len;
            while((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            int tempchar;
            Reader reader = null;
            reader = new InputStreamReader(new FileInputStream(outFileName));
            String obj = "";
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
//                    System.out.print((char) tempchar);
                	if(((char) tempchar) != '\n'){
                			obj = obj + (char) tempchar;	
                	}
                	
                	if('\n' == ((char) tempchar)){
                		System.out.println(JSONObject.parseObject(obj));
                		System.out.println(JSONObject.parseObject(obj).get("from"));
                		obj ="";
                	}
//                	System.out.println(JacksonUtil.Builder().obj2Json(obj));
                	
                }
            }

            System.out.println("Closing the file and stream");
            in.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    /**
     * Used to extract and return the extension of a given file.  
     * @param f Incoming file to get the extension of  
     * @return <code>String</code> representing the extension of the incoming  
     *         file.  
     */
    public static String getExtension(String f) {
        String ext = "";
        int i = f.lastIndexOf('.');

        if (i > 0 &&  i < f.length() - 1) {
            ext = f.substring(i+1);
        }
        return ext;
    }

    /**
     * Used to extract the filename without its extension.  
     * @param f Incoming file to get the filename  
     * @return <code>String</code> representing the filename without its  
     *         extension.  
     */
    public static String getFileName(String f) {
        String fname = "";
        int i = f.lastIndexOf('.');

        if (i > 0 &&  i < f.length() - 1) {
            fname = f.substring(0,i);
        }
        return fname;
    }

    /**
     * Sole entry point to the class and application.  
     * @param args Array of String arguments.  
     */
    public static void main(String[] args) {


        doUncompressFile("E:\\develop\\chat\\2017112209.gz");


    }

}   