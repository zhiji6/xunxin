package com.xunxin.controller.system;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.xunxin.service.PlatformMutualityManagentService;
import com.xunxin.vo.sys.PageData;
import com.xunxin.web.api.bean.Route;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月20日 -- 下午2:06:28
 * @Version 1.0
 * @Description	数据中心
 */
@Controller
@RequestMapping(value=Route.PATH+Route.Data.PATH)
public class DataCenterController extends BaseController{

	private static final Logger log = Logger.getLogger(DataCenterController.class);
	
	@Autowired
	private PlatformMutualityManagentService platformMutualityManagentService;
	
	/**
	 * 获取数据中心
	 * 
	 * @return
	 */
	@RequestMapping(value=Route.Data.DATA_CENTER,method=RequestMethod.GET)
	public ModelAndView data_center() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/data/data_center");
		return mv;
	}
	
	/**
	 * 获取平台下相关账户的列表
	 * 
	 * @param pd
	 * @return
	 */
	@RequestMapping(value=Route.Data.PLATFORM_MUTUALITY_LIST,method=RequestMethod.GET)
	public ModelAndView getPlatformMutuality() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/data/platform_Mutuality_list");
		return mv;
	}
	
	@RequestMapping(value=Route.Data.FINDALL,method=RequestMethod.POST)
	@ResponseBody
	public Object getPlatformMutualityList() {
		log.info("begin-: get the platform_Mutuality_Managent list");
		try {
			PageData pd = this.getPageData();
//			ModelAndView mv = this.getModelAndView();
//			mv.setViewName("jsp/xunxin/data/platform_Mutuality_list");
			List<PageData> lists = null;
			if (pd.getPageNumber() != 0) {
				PageHelper.startPage(pd.getPageNumber(), pd.getPageSize());
			}
			lists = platformMutualityManagentService.findAll(pd);
			// 分页
			pd = this.getPagingPd(lists);
			// 结果集封装
//			mv.addObject(pmList);
			log.info("end-: get the platform_Mutuality_Managent list success");
			return pd;
		} catch (Exception e) {
			log.error("error-: get the platform_Mutuality_Managent list" + e.getMessage());
			return "error";
//			return new ModelAndView("error");
		}
	}

	
	/**
	 * 获取平台数据列表
	 * 
	 * @return
	 */
	/*@RequestMapping(value=Route.Data.FINDALL,method=RequestMethod.POST)
	@ResponseBody
	public Response getPlatformMutualityList(HttpServletRequest request) {
		String order = (String) request.getAttribute("order");
		String limit = (String) request.getAttribute("limit");
		String offset = (String) request.getAttribute("offset");
		String pageNumber = (String) request.getAttribute("pageNumber");
		String pageSize = (String) request.getAttribute("pageSize");
		System.out.println(order);
		System.out.println(limit);
		System.out.println(offset);
		System.out.println(pageNumber);
		System.out.println(pageSize);
		Response reponse = this.getReponse();
		log.info("begin-: get the platform_Mutuality_Managent list");
		try {
			List<PlatformMutualityManagent> pmList = platformMutualityManagentService.findAll();
			log.info("end-: get the platform_Mutuality_Managent list success");
			return reponse.success(pmList);
		} catch (Exception e) {
			log.error("error-: get the platform_Mutuality_Managent list" + e.getMessage());
			return reponse.failure(e.getMessage());
		}
	}*/
	
	
	
	
	
	
	
	
}
