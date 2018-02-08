package com.xunxin.config;

import com.xunxin.service.app.HtmlHelper;
import com.xunxin.vo.sys.PageData;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2017年12月23日 -- 下午4:32:03
 * @Version 1.0
 * @Description WebMagic垂直爬虫技术实战
 */
public class XSpiderPageProcessor implements PageProcessor {

	 // 第一步：DownLoader加载器下载网页时，抓取网站的相关配置，包括编码、抓取间隔、重试次数等  
	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
	
	@ExtractBy("//div[@id='root']/text()")
	private static String date;
	
	private static int count = 0;

	public Site getSite() {
		return site;
	}

	public static String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		XSpiderPageProcessor.count = count;
	}

	public void setSite(Site site) {
		this.site = site;
	}



	//第二步： process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑  
	/**
	 * @param page
	 */
	public void process(Page page) {
	    
		//每个html网页的url地址 
        System.out.println(page.getUrl());
        //获取网页的title，该title位于haead标签中，text()方法表示获得文本内容
        String title = page.getHtml().xpath("//head/title/text()").toString();  
        String content = page.getHtml().css("p").all().toString();  
        String delHTMLTag = HtmlHelper.delHTMLTag(content).trim();
        System.out.println(title);  
        System.out.println(delHTMLTag);  
        if (title == null) {  
        //setSkip这个方法是对resultItems的内容进行忽略，默认设置为false，就是在本层逻辑中，爬取到的信息不进入管道进行保存。
            page.setSkip(true);  
        }  
        
	}
	
	public static void spider(String url){
//		long startTime, endTime;
//		startTime = System.currentTimeMillis();
		Spider.create(new XSpiderPageProcessor())
		// 从指定的URL开始抓 
		.addUrl(url)
		// 开启5个线程抓取 ，底层处理了线程同步
		.thread(5)
		// 抓取页面的存储路径  
		.addPipeline(new FilePipeline("/data/pachong/govnews"))
		// 启动爬虫  
		.run();
//		endTime = System.currentTimeMillis();
//		System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒，抓取了" + count + "条记录");
	}
	

	public static void main(String[] args) {
//		String url = "https://m.ifeng.com/shareNews?forward=1&aid=cmpp_030200054395302&aman=422852eeb4E99c309dg993a4646222Z7d2R2b1x422&gud=90Q397o829";
//		String url = "https://view.inews.qq.com/a/20171222A064XZ00/";
//		String url = "https://view.inews.qq.com/a/20171224A0CUPV00";
//		String url = "https://view.inews.qq.com/a/20171222A064XZ00?tbkt=E&refer=wx_hot";
//		String url = "https://www.toutiao.com/a6502179508777386509/?tt_from=copy_link&utm_campaign=client_share&app=news_article&utm_source=copy_link&iid=20449389398&utm_medium=toutiao_ios";
//		String url = "https://m.ifeng.com/shareNews?forward=1%26aid=cmpp_030200054395302%26aman=422852eeb4E99c309dg993a4646222Z7d2R2b1x422%26gud=90Q397o829";
//		String url = "https://www.zhihu.com/question/65938579/answer/254442654";
//		String url = "https://www.toutiao.com/a6504386655036637710/?tt_from=copy_link&utm_campaign=client_share&timestamp=1514434952&app=news_article&utm_source=copy_link&iid=20859356424&utm_medium=toutiao_ios";
		String url = "http://news.xinhuanet.com/politics/2017-12/18/c_1122130242.htm";
		spider(url);
	}

}
