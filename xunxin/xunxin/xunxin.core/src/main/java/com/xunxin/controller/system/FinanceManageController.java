package com.xunxin.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xunxin.vo.account.RechargeRecord;
import com.xunxin.web.api.bean.Route;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月16日 -- 上午3:07:46
 * @Version 1.0
 * @Description	财务管理
 */
@Controller
@RequestMapping(value=Route.PATH+Route.Payment.PATH)
public class FinanceManageController extends BaseController{

	private static final Logger log = Logger.getLogger(FinanceManageController.class);
	
	/**
	 * 充值记录管理
	 * @return
	 */
	@RequestMapping(value=Route.Payment.ACCOUNT_RECHARGE_MANAGER,method=RequestMethod.GET)
	public ModelAndView account_recharge_manager() {
		log.info("获取订单列表开始");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/pay/account_recharge_manager");
		return mv;
	}
	
	/**
	 * 消费记录管理
	 * @return
	 */
	@RequestMapping(value=Route.Payment.ACCOUNT_CONSUME_MANAGER,method=RequestMethod.GET)
	public ModelAndView account_consume_manager() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/pay/account_consume_manager");
		return mv;
	}
	
	@RequestMapping(value=Route.Payment.RECHARGE_LIST)
	public ModelAndView recharge_list() {
		log.info("获取订单列表开始");
		try {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("jsp/xunxin/pay/account_consume_manager");
			List<RechargeRecord> recordList = new ArrayList<RechargeRecord>();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("model", recordList);
			mv.addObject(map);
			log.info("获取订单列表结束");
			return mv;
		} catch (Exception e) {
			log.error("获取订单列表失败",e);
		}
		return new ModelAndView("error");
	}
	
	
	
}
