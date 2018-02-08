package com.xunxin.service.app.qa;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.impl.QuestionDaoImpl;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2017年12月25日 -- 上午10:24:27
 * @Version 1.0
 * @Description
 */
@Repository
public class QuestionService extends QuestionDaoImpl{
	
	/**
	 * 获取来源
	 * @param linked_url
	 * @return
	 */
	public String getSource(String linked_url) {

/**********************************************************************************
	PC端的新闻
**********************************************************************************/	
		
		if(linked_url.contains("people")){
			return "人民网";
		}
		if(linked_url.contains("tvmao")){
			return "电视猫";
		}
		if(linked_url.contains("cctv")){
			return "央视网";
		}
		if(linked_url.contains("xinhua")){
			return "新华网";
		}
		if(linked_url.contains("china")){
			return "中国网";
		}
		if(linked_url.contains("rednet")){
			return "红网";
		}
		if(linked_url.contains("toutiao")){
			return "今日头条";
		}
		if(linked_url.contains("qq")){
			return "腾讯新闻";
		}
		if(linked_url.contains("sohu")){
			return "搜狐新闻";
		}
		if(linked_url.contains("163")){
			return "网易新闻";
		}
		if(linked_url.contains("baidu")){
			return "百度新闻";
		}
		if(linked_url.contains("ifeng")){
			return "凤凰新闻";
		}
		if(linked_url.contains("sina")){
			return "新浪新闻";
		}
		
		return "其他";
	}

	/**
	 * 获取内容
	 * @param body
	 * @return
	 */
	public String getContent(String linked_url,Document body) {
		String content = "";
/**********************************************************************************
	PC端的新闻
**********************************************************************************/	
		    
	    
		Elements pElement = body.getElementsByTag("p");
		
		String pContent = pElement.text().trim() + "\r\n";
		if(pContent != null && !StringUtils.trim(pContent).equals("")) {
			content = pContent.substring(0, 500);
		}else {
		    content = "未获取到正文,请检查链接来源";
		}
//		if(linked_url.startsWith("http://www.tvmao.com/") || linked_url.startsWith("https://www.tvmao.com/")){
//			Elements pElement = body.getElementsByTag("p");
//			String pContent = pElement.text().trim() + "\r\n";
//			String content = pContent.substring(0, 500);
//			return content;
//		}
//		if(linked_url.startsWith("http://www.cctv.com/") || linked_url.startsWith("https://www.cctv.com/")){
//			Elements pElement = body.getElementsByTag("p");
//			String pContent = pElement.text().trim() + "\r\n";
//			String content = pContent.substring(0, 500);
//			return content;
//		}
//		if(linked_url.startsWith("http://xinhuanet.com/") || linked_url.startsWith("https://xinhuanet.com/")){
//			Elements pElement = body.getElementsByTag("p");
//			String pContent = pElement.text().trim() + "\r\n";
//			String content = pContent.substring(0, 500);
//			return content;
//		}
//		if(linked_url.startsWith("http://www.china.com.cn/") || linked_url.startsWith("https://www.china.com.cn/")){
//			Elements pElement = body.getElementsByTag("p");
//			String pContent = pElement.text().trim() + "\r\n";
//			String content = pContent.substring(0, 500);
//			return content;
//		}
//		if(linked_url.startsWith("http://www.rednet.cn/") || linked_url.startsWith("https://www.rednet.cn/")){
//			Elements pElement = body.getElementsByTag("p");
//			String pContent = pElement.text().trim() + "\r\n";
//			String content = pContent.substring(0, 500);
//			return content;
//		}
//		if(linked_url.startsWith("https://www.toutiao.com/") || linked_url.startsWith("https://www.toutiao.com/")){
//			Elements pElement = body.getElementsByTag("p");
//			String pContent = pElement.text().trim() + "\r\n";
//			String content = pContent.substring(0, 500);
//			return content;
//		}
//		if(linked_url.startsWith("http://news.qq.com/") || linked_url.startsWith("https://news.qq.com/")){
//			Elements pElement = body.getElementsByTag("p");
//			String pContent = pElement.text().trim() + "\r\n";
//			String content = pContent.substring(0, 500);
//			return content;
//		}
//		if(linked_url.startsWith("http://news.sohu.com/") || linked_url.startsWith("https://news.sohu.com/")){
//			Elements pElement = body.getElementsByTag("p");
//			String pContent = pElement.text().trim() + "\r\n";
//			String content = pContent.substring(0, 500);
//			return content;
//		}
//		if(linked_url.startsWith("http://news.163.com/") || linked_url.startsWith("https://news.163.com/")){
//			Elements pElement = body.getElementsByTag("p");
//			String pContent = pElement.text().trim() + "\r\n";
//			String content = pContent.substring(0, 500);
//			return content;
//		}
//		if(linked_url.startsWith("http://news.baidu.com/") || linked_url.startsWith("https://news.baidu.com/")){
//			Elements pElement = body.getElementsByTag("p");
//			String pContent = pElement.text().trim() + "\r\n";
//			String content = pContent.substring(0, 500);
//			return content;
//		}
//		if(linked_url.startsWith("http://news.ifeng.com/") || linked_url.startsWith("https://news.ifeng.com/")){
//			Elements pElement = body.getElementsByTag("p");
//			String pContent = pElement.text().trim() + "\r\n";
//			String content = pContent.substring(0, 500);
//			return content;
//		}
//		if(linked_url.startsWith("http://news.sina.com.cn/") || linked_url.startsWith("https://news.sina.com.cn/")){
//			Elements pElement = body.getElementsByTag("p");
//			String pContent = pElement.text().trim() + "\r\n";
//			String content = pContent.substring(0, 500);
//			return content;
//		}
		

/**********************************************************************************
	APP端的新闻
**********************************************************************************/
//		if(linked_url.startsWith("http://m.toutiaocdn.net/")){
//			Elements pElement = body.getElementsByTag("p");
//			String pContent = pElement.text().trim() + "\r\n";
//			String content = pContent.substring(0, 500);
//			return content;
//		}
//		if(linked_url.startsWith("https://view.inews.qq.com/")){
//			Elements pElement = body.getElementsByTag("span");
//			String pContent = pElement.text().trim() + "\r\n";
//			String content = pContent.substring(0, 500);
//			return content;
//		}
//		if(linked_url.startsWith("https://m.ifeng.com/")){
//			Elements pElement = body.getElementsByTag("p");
//			String pContent = pElement.text().trim() + "\r\n";
//			String content = pContent.substring(0, 500);
//			return content;
//		}
//		if(linked_url.startsWith("https://news.sina.cn/")){
//			Elements pElement = body.getElementsByTag("p");
//			String pContent = pElement.text().trim() + "\r\n";
//			String content = pContent.substring(0, 500);
//			return content;
//		}		
		
		
		return content;
	}

	
	
}
