package com.xunxin.service.app;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ConsoleDemo {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//随便找找，不一定都可以，新闻还是OK得
		//String externalUrl = "https://jingyan.baidu.com/article/7c6fb42865223f80642c90c4.html";
		
		//新浪新闻
		//String externalUrl= "http://news.sina.com.cn/w/sy/2017-11-17/doc-ifynwxum2548854.shtml";
//	    String externalUrl= "http://news.sina.cn/2018-01-04/detail-ifyqiwuw6314171.d.html?sinawapsharesource=newsapp";
		
		//百度新闻
		String externalUrl= "http://politics.people.com.cn/n1/2017/1117/c1001-29651767.html";
		
		//微信公众号
//		String externalUrl ="https://m.ifeng.com/shareNews?forward=1&aid=cmpp_033680054614213&CBanner=https%3A%2F%2Fd.ifengimg.com%2Fw640_h100_q75%2Fp0.ifengimg.com%2Fa%2F2017_49%2Ffe4734c4f40a877_size27_w640_h100.jpg&aman=5et8750a2c4aa2ia98C89fc2a4D1a1K3c0md5bEb2f";
		
		//今日头条
		//String externalUrl ="http://m.haiwainet.cn/ttc/3541093/2017/1117/content_31181020_1.html?tt_group_id=6489385507314385166";
		
		Document doc = null;
		try {
			doc = Jsoup.connect(externalUrl.trim())
					.timeout(6000)
					.data("query", "Java")   // 请求参数
					.userAgent("I ’ m jsoup") // 设置 User-Agent 
					.cookie("auth", "token") // 设置 cookie  
					.get();
			Document body = Jsoup.parse(doc.body().html());
			System.out.println("-------------------标题方式一------------------");
			Elements tagElement = doc.getElementsByTag("title");
			System.out.println(tagElement.text()+"\r\n");
			
			
			System.out.println("-------------------标题方式二------------------");
			System.out.println(HtmlHelper.drawTitle(HtmlHelper.filterContent(doc.html()))+"\r\n");//另外一种处理方式
			
			
			System.out.println("-------------------正文方式------------------");
			System.out.println(HtmlHelper.drawCon(body).text()+"\r\n");
			
			//图片
			Elements imgs = doc.getElementsByTag("img");//取得所有Img标签的值
			if(imgs.size()>0){
				System.out.println("-------------------图片地址------------------");
				System.out.println(imgs.get(0).attr("abs:src"));//默认取第一个为图片
            }
			else {
				//没有图片，用一个默认图片代替，具体需要和iOS开发沟通
			}
			
			//TODO 包装一个好看的DIV。。。
			
		}catch(Exception ex) {

		}
        
		 
	}

}
 
