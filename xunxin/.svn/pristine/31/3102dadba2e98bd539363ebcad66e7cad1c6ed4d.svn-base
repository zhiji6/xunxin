package com.xunxin.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mongodb.framework.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xunxin.service.app.CommentService;
import com.xunxin.util.page.PageInfo;
import com.xunxin.vo.qa.CommentVO;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.UserAuthentication;
import com.xunxin.web.api.bean.Route;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月30日 -- 下午2:22:10
 * @Version 1.0
 * @Description		留言管理中心
 */
@Controller
@RequestMapping(value=Route.PATH+Route.Message.PATH)
public class MessageManagerController extends BaseController{

	private final static Logger log = Logger.getLogger(MessageManagerController.class);
	
	@Autowired
	private CommentService commentService;
	
	/**
	 * 留言列表
	 * @return
	 */
	@RequestMapping(value=Route.Message.MESSAGE_MANAGER,method=RequestMethod.GET)
	public ModelAndView message_manager() {
		log.info("");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/message/message_manager");
		return mv;
	}
	/**
	 * 留言列表(分页)
	 * @return
	 */
	@RequestMapping(value=Route.Message.MESSAGE_MANAGER_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Object message_manager_list(CommentVO commentVO) {
		log.info("infoMsg：--- 获取留言列表开始");
		PageData pd = this.getPageData();
		List<PageData> pdList = new ArrayList<>();
		try {
			log.info("infoMsg：--- 获取留言列表结束");
			Pagination<CommentVO> pagination = commentService.findPageList(commentVO);
			Map<String, Object> page = new HashMap<String,Object>();
			page.put("page", pagination.getPageNo());
			page.put("total", pagination.getTotalCount());
			page.put("rows", pagination.getDatas());
			return page;
		} catch (Exception e) {
			log.error("errorMsg:--- 获取留言列表失败");
			return null;
		}
	}
	/**
	 * 留言删除
	 * @return
	 */
	@RequestMapping(value=Route.Message.MESSAGE_MANAGER_DELETE,method=RequestMethod.POST)
	@ResponseBody
	public String message_manager_delete(String  IDS) {
		log.info("infoMsg：--- 留言删除开始");
		try {
			log.info("infoMsg：--- 留言删除结束");
			commentService.deleteByIds(IDS);
			return "OK";  
		} catch (Exception e) {
			log.error("errorMsg:--- 留言删除失败");
			return null;
		}
	}
	
	/**
	 * 留言互动
	 * @return
	 */
	@RequestMapping(value=Route.Message.MESSAGE_INTERACTION,method=RequestMethod.GET)
	public ModelAndView message_interaction() {
		log.info("");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/message/message_interaction");
		return mv;
	}
	
	
	
}
