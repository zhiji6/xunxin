package com.xunxin.controller.app.search;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xunxin.controller.system.BaseController;
import com.xunxin.service.app.DbSearchService;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;

/**
 * 
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月20日 -- 上午10:05:23
 * @Version 1.0
 * @Description		基于Lucene5.5.4的数据库搜索
 */
@Controller
@RequestMapping(value=Router.PATH+Router.Search.PATH)
public class XLuenceController extends BaseController{

	private static final Logger log = Logger.getLogger(XLuenceController.class);
	
	public Response search_question(@RequestParam("userId") int userId,@RequestParam("question") String question) {
		log.info("infoMsg:--- 搜索问题开始");
		Response reponse = this.getReponse();
		try {
			List<String> list = DbSearchService.search(question);
			
			
			log.info("infoMsg:--- 搜索问题结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:--- 搜索问题失败");
			return reponse.failure();
		}
	}
	
	
	
	
	
	
	
	
	
}
