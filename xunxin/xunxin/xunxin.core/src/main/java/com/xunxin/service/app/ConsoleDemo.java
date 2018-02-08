package com.xunxin.service.app;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ConsoleDemo {
	
	public static void main(String[] args) {
		
		String externalUrl = "https://jingyan.baidu.com/article/7c6fb42865223f80642c90c4.html";
		
		
		Document doc = null;
		try {
			doc = Jsoup.connect(externalUrl.trim())
					.timeout(6000)
					.data("query", "Java")   // �������
					.userAgent("I’m jsoup") // ���� User-Agent 
					.cookie("auth", "token") // ���� cookie  
					.get();
			Document body = Jsoup.parse(doc.body().html());
			Elements tagElement = doc.getElementsByTag("title");
			System.out.println(tagElement.text()+"\r\n");
			
			
			System.out.println(HtmlHelper.drawTitle(HtmlHelper.filterContent(doc.html()))+"\r\n");
			
			
			System.out.println(HtmlHelper.drawCon(body).text()+"\r\n");
			
			//ͼƬ
			Elements imgs = doc.getElementsByTag("img");
			if(imgs.size()>0){
				System.out.println(imgs.get(0).attr("abs:src"));
            }
			else {
			}
			
			
		}catch(Exception ex) {

		}
        
		 
	}

}
 
