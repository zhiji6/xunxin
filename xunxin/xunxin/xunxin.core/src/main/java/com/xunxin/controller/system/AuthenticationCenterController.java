package com.xunxin.controller.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xunxin.config.RedisRepository;
import com.xunxin.service.app.SysDictService;
import com.xunxin.service.app.user.UserAuthenticationService;
import com.xunxin.util.page.PageInfo;
import com.xunxin.vo.qa.QASection;
import com.xunxin.vo.qa.SysDict;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.UserAuthentication;
import com.xunxin.web.api.bean.Route;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月30日 -- 上午11:12:44
 * @Version 1.0
 * @Description		认证中心
 */
@Controller
@RequestMapping(value=Route.PATH+Route.Auth.PATH)
public class AuthenticationCenterController extends BaseController{

	private final static Logger log = Logger.getLogger(AuthenticationCenterController.class);
	
	@Autowired
	private UserAuthenticationService userAuthenticationService;
	@Autowired
	private SysDictService sysDictService;
	/**
	 * 认证信息管理
	 * @return
	 */
	@RequestMapping(value=Route.Auth.AUTHENTICATION_MANAGER,method=RequestMethod.GET)
	public ModelAndView authentication_manager() {
		log.info("进入认证信息管理界面");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/auth/authentication_manager");
		return mv;
	}
	
	/**
	 * 认证信息管理列表(分页)
	 * @return
	 */
	@RequestMapping(value=Route.Auth.AUTHENTICATION_MANAGER_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Object authentication_manager_list(UserAuthentication userAuthentication) {
		log.info("infoMsg：--- 获取认证信息管理列表开始");
		PageData pd = this.getPageData();
		List<PageData> pdList = new ArrayList<>();
		try {
			log.info("infoMsg：--- 获取认证信息管理列表结束");
			PageInfo<UserAuthentication> page = userAuthenticationService.findPageList(userAuthentication);
			return page;
		} catch (Exception e) {
			log.error("errorMsg:--- 获取认证信息管理列表失败");
			return null;
		}
	}
	/**
	 * 认证信息管理去修改
	 * @return
	 */
	@RequestMapping(value=Route.Auth.AUTHENTICATION_MANAGER_TO_EDIT,method=RequestMethod.GET)
	public ModelAndView authentication_manager_to_edit(Integer SP_ID,Model model) {
		log.info("进入认证信息管理界面");
		ModelAndView mv = this.getModelAndView();
		UserAuthentication attributeValue = userAuthenticationService.findOneById(SP_ID);
		model.addAttribute("authentication", attributeValue);
		mv.setViewName("jsp/xunxin/auth/authentication_manager_form");
		return mv;
	}
	/**
	 * 认证信息管理审核
	 * @return
	 */
	@RequestMapping(value=Route.Auth.AUTHENTICATION_MANAGER_EXAMINE,method=RequestMethod.POST)
	public String authentication_manager_examine(UserAuthentication userAuthentication) {
		log.info("infoMsg：--- 认证信息管理审核开始");
		try {
			log.info("infoMsg：--- 获取认证信息管理列表结束");
			userAuthenticationService.authenticationManagerExamine(userAuthentication);
			return "redirect:/api/auth/authentication_manager";  
		} catch (Exception e) {
			log.error("errorMsg:--- 获取认证信息管理列表失败");
			return null;
		}
	}
	/**
	 * 职业认证
	 * @return
	 */
	@RequestMapping(value=Route.Auth.PROFESSION_AUTHENTICATION,method=RequestMethod.GET)
	public ModelAndView profession_authentication() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/auth/profession_authentication");
		return mv;
	}
	
	/**
	 * 学历认证
	 * @return
	 */
	@RequestMapping(value=Route.Auth.EDUCATION_AUTHENTICATION,method=RequestMethod.GET)
	public ModelAndView education_authentication() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/auth/education_authentication");
		return mv;
	}
	
	/**
	 * 字典查询
	 * @return
	 */
	@RequestMapping(value=Route.Auth.QUERY_DICT,method=RequestMethod.POST)
	@ResponseBody
	public Object query_dict() {
		log.info("infoMsg：--- 字典查询开始");
		PageData pageData = this.getPageData();
		List<PageData> pdList = new ArrayList<>();
		try {
			log.info("infoMsg：--- 字典查询结束");
			String type="";
			if(!StringUtils.isBlank(pageData.getString("type")) && !StringUtils.trim(pageData.getString("type")).equals("")) {
				type=pageData.getString("type");
			}
			boolean flag = RedisRepository.exists(type);
			List<SysDict> types = null;
			if(!flag){
				types = sysDictService.findDics(type);
				RedisRepository.setList(type, types);
			}else{
				types = RedisRepository.getListEntity(type, SysDict.class);
				if(types == null || types.size() <1 ){
					 types = sysDictService.findDics(type);
						RedisRepository.setList(type, types);
				}
			}
				log.info("infoMsg:--- 查找字典列列表结束");
				for(SysDict qa : types) {
					PageData pd = new PageData<>();
					pd.put("ID", qa.getValue());
					pd.put("TEXT", qa.getLabel());
					pdList.add(pd);
				}
				return pdList;
		} catch (Exception e) {
			log.error("errorMsg:--- 获字典查询失败");
			return null;
		}
	}
	
	
}
