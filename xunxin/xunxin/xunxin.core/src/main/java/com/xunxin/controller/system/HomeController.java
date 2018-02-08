package com.xunxin.controller.system;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xunxin.web.api.bean.Route;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月2日 -- 上午2:49:24
 * @Version 1.0
 * @Description
 */
@Controller
public class HomeController{

	private static final Logger log = Logger.getLogger(HomeController.class);
	
	/**
	 * 跳转登录界面
	 * 
	 * @return
	 */
	@RequestMapping(value=Route.LOGIN,method=RequestMethod.GET)
	public ModelAndView login() {
		log.info("欢迎访问循心后台管理系统");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/login");
		return modelAndView;
	}

	/**
	 * 跳转index界面
	 * 
	 * @return
	 */
	@RequestMapping(value=Route.INDEX,method=RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/index");
		return modelAndView;
	}

	@RequestMapping(value=Route.LOGOUT,method=RequestMethod.GET)
	public ModelAndView loginOut(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		Subject subject = SecurityUtils.getSubject();// 获得主体
		subject.logout();
		session.invalidate();
		return modelAndView;
	}

	
}
