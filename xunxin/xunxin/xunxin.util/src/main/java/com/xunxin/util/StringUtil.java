package com.xunxin.util;


public class StringUtil {
	
	/**
	 * 获取目标string中指定开始标签和结束标签中的string，如果有多个，返回数组
	 * @param targetStr  	目标string 
	 * @param startStr   	开始标签
	 * @param endStr	   	结束标签
	 * @return				返回截取的string数组
	 */
	public static String[] getMiddleStrings(String targetStr, String startStr , String endStr){
		//如果目标string为空，则返回一个包含空字符串的数组
		String[] returnStrings;
		if(targetStr==null||"".equals(targetStr)){
			return new String[] { "" };
		}
		int includeCount = numberOfStr(targetStr,endStr);
		if(includeCount==0){
			return new String[] { targetStr };
		}else{
			returnStrings = new String[includeCount];
		}
		int startIndex = -1;
		int endIndex = -1;
		int beginIndex = 0;
		for (int i = 0; i < includeCount; i++) {
			startIndex = targetStr.indexOf(startStr,beginIndex);
			endIndex = targetStr.indexOf(endStr,beginIndex);
			if (startIndex != -1 && endIndex != -1) {
				beginIndex = endIndex+endStr.length();
				returnStrings[i]= targetStr.substring(startIndex,beginIndex);
			}
		}
		return returnStrings;
	}

	/**
	 * 目标字符串中包含子字符串的次数
	 * @param targetString
	 * @param contentString
	 * @return
	 */
	public static int numberOfStr(String targetString, String contentString){
		if(targetString.endsWith(contentString)){
			return targetString.split(contentString).length;
		}else{
			return targetString.split(contentString).length - 1;
		}
	}
}