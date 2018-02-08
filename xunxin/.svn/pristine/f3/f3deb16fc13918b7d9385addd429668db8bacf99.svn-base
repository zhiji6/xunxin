package com.xunxin.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xunxin.service.app.IMRecordService;
import com.xunxin.service.app.UserFriendsService;
import com.xunxin.util.page.PageInfo;
import com.xunxin.vo.im.IMRecord;
import com.xunxin.vo.im.UserRelation;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.UserAuthentication;
import com.xunxin.web.api.bean.Route;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月30日 -- 下午2:26:57
 * @Version 1.0
 * @Description		消息管理中心
 */
@Controller
@RequestMapping(value=Route.PATH+Route.Info.PATH)
public class InfoManagerController extends BaseController{

	private final Logger log = Logger.getLogger(InfoManagerController.class);
	@Autowired
	private UserFriendsService userFriendsService;
	@Autowired
	private IMRecordService iMRecordService;
	/**
	 * 系统消息管理(推送)
	 * @return
	 */
	@RequestMapping(value=Route.Info.SYSTEM_INFO_MANAGER,method=RequestMethod.GET)
	public ModelAndView system_info_manager() {
		log.info("");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/info/system_info_manager");
		return mv;
	}
	
	
	/**
	 * 用户消息管理(推送)
	 * @return
	 */
	@RequestMapping(value=Route.Info.USER_INFO_MANAGER,method=RequestMethod.GET)
	public ModelAndView user_info_manager() {
		log.info("");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/info/user_info_manager");
		return mv;
	}
	
	
	/**
	 * 用户反馈管理
	 * @return
	 */
	@RequestMapping(value=Route.Info.USER_FEEDBACK,method=RequestMethod.GET)
	public ModelAndView user_feedback() {
		log.info("");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/info/user_feedback");
		return mv;
	}
	
	
	/**
	 * 用户体系管理(环信)
	 * @return
	 */
	@RequestMapping(value=Route.Info.EASEMOB_USERS_MANAGER,method=RequestMethod.GET)
	public ModelAndView easemob_users_manager() {
		log.info("");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/info/easemob_users_manager");
		return mv;
	}
	/**
	 * 用户体系列表(分页)
	 * @return
	 */
	@RequestMapping(value=Route.Info.EASEMOB_USERS_MANAGER_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Object easemob_users_manager_list() {
		log.info("infoMsg：--- 获取用户体系列表开始");
		try {
			log.info("infoMsg：--- 获取用户体系列表结束");
			Map page = userFriendsService.findPageList();
			return page;
		} catch (Exception e) {
			log.error("errorMsg:--- 获取用户体系列表失败");
			return null;
		}
	}
	/**
	 * 用户体系去修改
	 * @return
	 */
	@RequestMapping(value=Route.Info.EASEMOB_USERS_MANAGER_TO_EDIT,method=RequestMethod.GET)
	public ModelAndView easemob_users_manager_to_edit(Integer id,Model model) {
		log.info("进入认证信息管理界面");
		ModelAndView mv = this.getModelAndView();
		UserRelation userRelation = userFriendsService.findOneById(id);
		model.addAttribute("userRelation", userRelation);
		mv.setViewName("jsp/xunxin/info/easemob_users_manager_form");
		return mv;
	}
	/**
	 * 用户体系重构
	 * @return
	 */
	@RequestMapping(value=Route.Info.EASEMOB_USERS_MANAGER_RESTRUCTURE,method=RequestMethod.POST)
	@ResponseBody
	public Object easemob_users_manager_restructure(Integer id,String idType,Integer userId) {
		log.info("infoMsg：--- 用户体系重构开始");
		try {
			log.info("infoMsg：--- 用户体系重构结束");
			 userFriendsService.restructureFriend(id,idType,userId);
			 return "OK";
		} catch (Exception e) {
			log.error("errorMsg:--- 用户体系重构失败");
			return null;
		}
	}
	
	/**
	 * 用户聊天记录管理(环信)
	 * @return
	 */
	@RequestMapping(value=Route.Info.EASEMOB_CHAT_MESSAGES_MANAGER,method=RequestMethod.GET)
	public ModelAndView easemob_chat_messages_manager() {
		log.info("");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/info/easemob_chat_messages_manager");
		return mv;
	}
	/**
	 * 用户聊天记录列表(分页)
	 * @return
	 */
	@RequestMapping(value=Route.Info.EASEMOB_CHAT_MESSAGES_MANAGER_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Object easemob_chat_messages_manager_list(IMRecord iMRecord) {
		log.info("infoMsg：--- 获取用户聊天记录列表开始");
		try {
			log.info("infoMsg：--- 获取用户聊天记录列表结束");
			PageInfo<IMRecord> page = iMRecordService.findPageList(iMRecord);
			return page;
		} catch (Exception e) {
			log.error("errorMsg:--- 获取用户聊天记录列表失败");
			return null;
		}
	}
	
	/**
	 * 文件管理(环信) 
	 * @return
	 */
	@RequestMapping(value=Route.Info.EASEMOB_CHAT_FILES_MANAGER,method=RequestMethod.GET)
	public ModelAndView easemob_chat_files_manager() {
		log.info("");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/info/easemob_chat_files_manager");
		return mv;
	}
	
	
	
	
	
}
