package com.xunxin.util.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

public class JsoupLoadHtmlUtils {
	
	private static Document doc;  
    private static String html;  
    private static String baseUri = "http://www.baidu.com";  
    public static Document getDoc() {  
        return doc;  
    }  
    public static void setDoc(Document doc) {  
    	JsoupLoadHtmlUtils.doc = doc;  
    }  
    
    /** 
     * 设置代理服务器（ps：单位用的代理上网） 
     */  
    public static void Host(){  
          
        System.getProperties().setProperty("proxySet", "true");    
        //用的代理服务器    
        System.getProperties().setProperty("http.proxyHost", "192.168.130.15");    
        //代理端口    
        System.getProperties().setProperty("http.proxyPort", "8848");  
    }  
    public static void LoadHtml(String url) throws Exception{  
        Host();  
        JsoupClean();  
        JsoupConnect(url);  
        JsoupIsValid();  
        JsoupParseFile();  
        JsoupParseStream();  
        JsoupParseStr();  
        JsoupURL();  
        JsoupBody();  
    }  
    /** 
     * html过滤 
     */  
    public static void JsoupClean(){  
        html = "<p><a href='http://blog.csdn.net/xyw_eliot' onclick='stealCookies()'> Eliot </a></p>";  
        String doc = Jsoup.clean(html, baseUri, Whitelist.basic());   
        System.out.println(doc);  
    }  
    /** 
     * 连接URL返回Document 
     * @throws IOException 
     */  
    public static Document JsoupConnect(String url) throws IOException{  
        doc = Jsoup.connect(url).get();  
        System.out.println(doc);  
        return doc;
    }  
    /** 
     * 判断是否符合过滤规则 
     */  
    public static void JsoupIsValid(){  
        html = "<p><a href='http://www.baidu/'onclick='stealCookies()'> 百度一下，你就知道 </a></p>";  
        System.out.println(Jsoup.isValid(html, Whitelist.basic()));  
    }  
    /** 
     * 解析文件 
     * @throws IOException 
     */  
    public static void JsoupParseFile() throws IOException{  
        File file = new File("C://baidu.txt");  
        doc = Jsoup.parse(file, "GBK", baseUri);  
        System.out.println(doc);  
    }  
    /** 
     * 解析流 
     * @throws Exception 
     */  
    public static void JsoupParseStream() throws Exception{  
        FileInputStream in = new FileInputStream("C://baidu.txt");  
        doc = Jsoup.parse(in, "GBK", baseUri);  
        System.out.println(doc);  
        in.close();  
    }  
    /** 
     * 解析字符串 
     */  
    public static void JsoupParseStr(){  
        html = "<html><head><title>First parse</title></head>"  
              + "<body><p>Parsed HTML into a doc.</p></body></html>";  
        doc = Jsoup.parse(html, baseUri);  
        System.out.println(doc);  
    }  
    /** 
     * 解析URL 
     * @throws Exception 
     */  
    public static void JsoupURL() throws Exception{  
        URL url = new URL("http://www.baidu.com");  
        doc = Jsoup.parse(url, 500);  
        System.out.println(doc);  
    }  
    /** 
     * 将html片段解析为body格式 
     */  
    public static void JsoupBody(){  
        html = "<div><p>Lorem ipsum.</p>";  
        doc = Jsoup.parseBodyFragment(html, baseUri);  
        System.out.println(doc);  
        Element body = doc.body(); //获取body元素，效果等同于doc.getElementsByTag("body")  
        System.out.println("*******");  
        System.out.println(body);  
    } 
    
    
    public static void main(String[] args){           
        try {  
        	String url = "";
            LoadHtml(url);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    } 
    
    
    
}
