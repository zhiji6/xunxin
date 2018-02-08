package com.xunxin.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * java 读.properties文件并转换成HashMap  
 */
public class PropertiesToMap {
	Properties prop = new Properties();
	
	public PropertiesToMap(String path){
		FileInputStream ins = null;
		try {
			ins = new FileInputStream(path);
			prop.load(ins);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if (ins != null) {
					ins.close();
					ins=null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> toMap(){
	    HashMap<String, String> propMap = new HashMap<String, String>((Map) prop);
	    return propMap;
	}
	
}
