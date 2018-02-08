package com.xunxin.controller.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xunxin.service.ThirdPayService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.RechargeRecordService;
import com.xunxin.service.app.user.UserAmountChangeRecordService;
import com.xunxin.util.PeriodsUtil;
import com.xunxin.vo.account.RechargeRecord;
import com.xunxin.vo.account.UserAmountChangeRecord;
import com.xunxin.vo.pay.ThirdPayBean;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.web.api.bean.Response;
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
public class FinanceManagerController extends BaseController{

	private static final Logger log = Logger.getLogger(FinanceManagerController.class);
	
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private RechargeRecordService rechargeRecordService;
	@Autowired
	private ThirdPayService thirdPayService;
	@Autowired
	private UserAmountChangeRecordService userAmountChangeRecordService;
	
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
	 * 交易明细管理
	 * @return
	 */
	@RequestMapping(value=Route.Payment.ACCOUNT_CONSUME_MANAGER,method=RequestMethod.GET)
	public ModelAndView account_consume_manager() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/pay/account_consume_manager");
		return mv;
	}
	
	/**
	 * 获取订单列表
	 * @return
	 */
	@RequestMapping(value=Route.Payment.RECHARGE_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Object recharge_list() {
		log.info("infoMsg:--- 获取订单列表开始");
		PageData pageData = this.getPageData();
		List<PageData> pdList = new ArrayList<>();
	    List<RechargeRecord> rechargeList = rechargeRecordService.recharge_list(pageData);
	    for(RechargeRecord record : rechargeList) {
	        PageData pd = new PageData<>();
	        pd.put("id", record.getId());
	        pd.put("orderNo", record.getOrderNo());
	        UserEntity user = appUserService.findById(record.getUserId());
	        pd.put("nickName", user.getNickName());
	        pd.put("thirdPayId", record.getThirdPayId());
	        pd.put("orderBefore", record.getOrderBefore());
	        pd.put("tradeAmount", record.getTradeAmount());
	        pd.put("orderEnd", record.getOrderEnd());
	        pd.put("orderTime", record.getOrderTime());
	        pd.put("orderAccountingTime", record.getOrderAccountingTime());
	        pd.put("remark", record.getRemark());
	        pd.put("orderState", record.getOrderState());
	        pdList.add(pd);
	    }
	    PageData pagingPd = this.getPagingPd(pdList);
		log.info("infoMsg:--- 获取订单列表结束");
		return pagingPd;
	}
	

    /**
     * 订单详情
     * @return
     */
    @RequestMapping(value=Route.Payment.ORDER_VIEW,method=RequestMethod.GET)
    @ResponseBody
    public ModelAndView order_view() {
        log.info("infoMsg:--- 获取订单详情开始");
        List<PageData> pdList = new ArrayList<>();
        PageData pageData = this.getPageData();
        PageData pd = new PageData<>();
        ModelAndView mv = this.getModelAndView();
        try {
            int id = Integer.parseInt(pageData.getString("id"));
            RechargeRecord record = rechargeRecordService.findByOrderId(id);
            pd.put("id", record.getId());
            pd.put("orderNo", record.getOrderNo());
            UserEntity user = appUserService.findById(record.getUserId());
            pd.put("nickName", user.getNickName());
            ThirdPayBean third = thirdPayService.findByPayId(record.getThirdPayId());
            pd.put("thirdPayId", third.getThird_name());
            pd.put("orderBefore", record.getOrderBefore());
            pd.put("tradeAmount", record.getTradeAmount());
            
            pd.put("orderEnd", record.getOrderEnd());
            pd.put("orderTime", PeriodsUtil.getWholeTime(record.getOrderTime()));
            pd.put("orderAccountingTime", PeriodsUtil.getWholeTime(record.getOrderAccountingTime()));
            pd.put("remark", record.getRemark());
            if(record.getOrderState() == 0) {
                pd.put("orderState", "已到账");
            }else if(record.getOrderState() == 1) {
                pd.put("orderState", "未到账");
            }
            pdList.add(pd);
            
            mv.addObject("pd",pd);
            mv.setViewName("jsp/xunxin/system/order_view");
            log.info("infoMsg:--- 获取系统管理员详情结束");
            return mv;
        } catch (Exception e) {
            log.error("errorMsg:{--- 获取系统管理员详情失败:" + e.getMessage() + "---}");
        }
        return new ModelAndView("error");
    }
    
    /**
     * 管理员修改页
     * @return
     */
    @RequestMapping(value=Route.Payment.ORDER_EDIT,method=RequestMethod.GET)
    public ModelAndView order_edit(HttpServletRequest request) {
        log.info("infoMsg:--- 管理员修改页开始");
        PageData pageData = this.getPageData();
        PageData pd = new PageData<>();
        ModelAndView mv = this.getModelAndView();
        try {
            int id = Integer.parseInt(pageData.getString("id"));
            RechargeRecord record = rechargeRecordService.findByOrderId(id);
            pd.put("id", record.getId());
            pd.put("orderNo", record.getOrderNo());
            UserEntity user = appUserService.findById(record.getUserId());
            pd.put("nickName", user.getNickName());
            pd.put("thirdPayId", record.getThirdPayId());
            pd.put("orderBefore", record.getOrderBefore());
            pd.put("tradeAmount", record.getTradeAmount());
            pd.put("orderEnd", record.getOrderEnd());
            pd.put("orderTime", record.getOrderTime());
            pd.put("orderAccountingTime", record.getOrderAccountingTime());
            pd.put("remark", record.getRemark());
            pd.put("orderState", record.getOrderState());
            
            mv.addObject("pd",pd);
            mv.setViewName("jsp/xunxin/system/order_edit");
            log.info("infoMsg:--- 管理员修改页结束");
            return mv;
        } catch (Exception e) {
            log.error("errorMsg:{--- 管理员修改页失败:" + e.getMessage() + "---}");
            return new ModelAndView("error");
        }
    }
    
    
    /**
     * 删除管理员
     * @return
     */
    @RequestMapping(value=Route.Payment.ORDER_DELETE,method=RequestMethod.POST)
    @ResponseBody
    public Response order_delete(HttpServletRequest request) {
        log.info("infoMsg:--- 删除管理员开始");
        Response reponse = this.getReponse();
        PageData pd = this.getPageData();
        try {
            rechargeRecordService.delete(pd);
            log.info("infoMsg:--- 删除管理员结束");
            return reponse.success();
        } catch (Exception e) {
            log.error("errorMsg:{--- 删除管理员失败:" + e.getMessage() + "---}");
            return reponse.failure(e.getMessage());
        }
    }
    
    /**
     * 交易明细列表
     * @return
     */
    @RequestMapping(value=Route.Payment.CONSUME_LIST,method=RequestMethod.POST)
    @ResponseBody
    public Object consume_list() {
        log.info("infoMsg:--- 获取订单列表开始");
        PageData pageData = this.getPageData();
        List<PageData> pdList = new ArrayList<>();
        Query query = new Query(); 
        List<UserAmountChangeRecord> changeList = userAmountChangeRecordService.find(query);
        for(UserAmountChangeRecord record : changeList) {
            PageData pd = new PageData<>();
            pd.put("id", record.getId());
            pd.put("changeType", record.getChangeType());
            pd.put("direction", record.getDirection());
            pd.put("tansferBefore", record.getTansferBefore());
            pd.put("tansferAmount", record.getTansferAmount());
            pd.put("tansferEnd", record.getTansferEnd());
            pd.put("createTime", record.getCreateTime());
            UserEntity user = appUserService.findById(record.getUserId());
            pd.put("nickName", user.getNickName());
            pdList.add(pd);
        }
        PageData pagingPd = this.getPagingPd(pdList);
        log.info("infoMsg:--- 获取订单列表结束");
        return pagingPd;
    }
    

}

