package com.xunxin.controller.system;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xunxin.web.api.bean.Route;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月30日 -- 下午1:49:34
 * @Version 1.0
 * @Description		用户积分管理中心
 */
@Controller
@RequestMapping(value=Route.PATH+Route.Exp.PATH)
public class UserExpManagerController extends BaseController{

	private final static Logger log = Logger.getLogger(UserExpManagerController.class);
	
	/**
	 * 用户积分管理
	 * @return
	 */
	@RequestMapping(value=Route.Exp.USER_EXP_MANAGER,method=RequestMethod.GET)
	public ModelAndView userExp_manager() {
		log.info("");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/exp/userExp_manager");
		return mv;
	}
	
	/**
	 * 用户流水管理
	 * @return
	 */
	@RequestMapping(value=Route.Exp.USER_EXP_RUNNING,method=RequestMethod.GET)
	public ModelAndView userExp_running() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/exp/userExp_running");
		return mv;
	}
	
	
	
}
