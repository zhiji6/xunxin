package com.xunxin.controller.common.pay;

import java.io.BufferedReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunxin.controller.system.BaseController;
import com.xunxin.service.PlatformMutualityManagentService;
import com.xunxin.service.ThirdPayService;
import com.xunxin.service.app.RechargeRecordService;
import com.xunxin.util.MaryunHttpUtils;
import com.xunxin.util.MaryunHttpUtils.UHeader;
import com.xunxin.util.app.OrderGeneratedUtils;
import com.xunxin.util.app.WXPayUtil;
import com.xunxin.util.app.WXRandomNumberUtil;
import com.xunxin.vo.pay.ThirdPayBean;
import com.xunxin.vo.sys.PlatformMutualityManagent;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Route;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月2日 -- 上午3:57:43
 * @Version 1.0
 * @Description  微信支付 SDK 接口接入
 */
@Controller
@RequestMapping(value=Route.PATH+Route.Payment.PATH)
public class WeiXinPayController extends BaseController{

	private static final Logger log = Logger.getLogger(WeiXinPayController.class);
	
	private static final String weixin_pay_Url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	private static final String weixin_query_Url = "https://api.mch.weixin.qq.com/pay/orderquery";
	
	@Autowired
	private ThirdPayService thirdPayService;
	@Autowired
	private RechargeRecordService rechargeRecordService;
	@Autowired
	private PlatformMutualityManagentService platformMutualityManagentService;

	/**
	 * 微信统一下单请求交易
	 * 
	 * @param userId
	 * @param amount
	 * @return
	 */
	@RequestMapping(value=Route.Payment.WEIXIN_PAY,method=RequestMethod.POST)
	@ResponseBody
	public Response pay(@RequestParam("userId") int userId,@RequestParam("amount") String amount) {
		log.info("InfoMsg:--- 微信统一下单请求交易开始");
		Response resp = this.getReponse();
		String message = "";
		try {
			ThirdPayBean bean = thirdPayService.findByPayId(19);
			String mch_id = bean.getMer_no();  	//商户号
			String notify_url = bean.getReturn_url();	//通知地址
			PlatformMutualityManagent pmm = platformMutualityManagentService.findOne(5);
			String appid = pmm.getClient_id();  	//应用ID
			String App_Secret = pmm.getClient_secret();
			String nonce_str = WXPayUtil.generateNonceStr();		//随机字符串
			String sign = "";				//签名
			String sign_type = "MD5";				//签名类型
			String body = "循心";				//商品描述 ps:腾讯充值中心-QQ会员充值
			String out_trade_no = OrderGeneratedUtils.getOrderNo();		//商户订单号
			int total_fee = WXRandomNumberUtil.wx_format_PayAmount(amount);	//交易金额
			String spbill_create_ip = InetAddress.getLocalHost().getHostAddress(); //终端IP
			String trade_type = "APP";		//交易类型 
			String attach = userId + "|" + bean.getBank_id();	//附加数据 ps:用户|支付方式
			
			StringBuffer sb = new StringBuffer();
			sb.append("appid=").append(appid).append("&");
			sb.append("attach=").append(attach).append("&");
			sb.append("body=").append(body).append("&");
			sb.append("mch_id=").append(mch_id).append("&");
			sb.append("notify_url=").append(notify_url).append("&");
			sb.append("nonce_str=").append(nonce_str).append("&");
			sb.append("out_trade_no=").append(out_trade_no).append("&");
			sb.append("sign_type=").append(sign_type).append("&");
			sb.append("spbill_create_ip=").append(spbill_create_ip).append("&");
			sb.append("total_fee=").append(total_fee).append("&");
			sb.append("trade_type=").append(trade_type).append("&");
			String params = sb.toString();
			//需要签名的数据
			String stringSignTemp = params + "&key=" + App_Secret;
			//MD5签名方式
			sign = WXPayUtil.MD5(stringSignTemp).toUpperCase();
			Map<String, Object> map = new HashMap<>();
			map.put("appid", appid);
			map.put("attach", attach);
			map.put("body", body);
			map.put("mch_id", mch_id);
			map.put("notify_url", notify_url);
			map.put("nonce_str", nonce_str);
			map.put("out_trade_no", out_trade_no);
			map.put("sign", sign);
			map.put("sign_type", sign_type);
			map.put("spbill_create_ip", spbill_create_ip);
			map.put("total_fee", total_fee);
			map.put("trade_type", trade_type);
			
			List<UHeader> theaderList = new ArrayList<>();
			theaderList.add(new UHeader("Content-Type", "application/x-www-form-urlencoded"));
			//Httpclient发送请求
			String postResponse = MaryunHttpUtils.getPostResponse(weixin_pay_Url, map, theaderList);
			//解析返回的XML数据
			Map<String, String> response = WXPayUtil.xmlToMap(postResponse);
			if(!response.isEmpty() && response.get("return_code").equals("SUCCESS")){
				if(response.get("result_code").equals("SUCCESS")) {
					boolean result = rechargeRecordService.generatedBills(response,attach);
					if(result) {
						log.info("InfoMsg:--- 微信统一下单请求交易成功");
					}
				}else {
					message = response.get("err_code_des");
					log.error("errorMsg:--- 微信统一下单请求交易解析失败" + message);
				}
			}else {
				log.error("errorMsg:--- 微信统一下单请求交易解析失败" + message);
			}
			log.info("InfoMsg:--- 微信统一下单请求交易结束");
			return resp.success();
		} catch (Exception e) {
			log.error("errorMsg:--- 微信统一下单请求交易失败" + e.getMessage());
			return resp.failure(e.getMessage());
		} 
		
	}
	
	/**
	 * 微信支付通知地址
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value=Route.Payment.WEIXIN_PAY_NOTIFY,method=RequestMethod.POST)
	@ResponseBody
	public Response weixin_pay_notify(HttpServletRequest request) {
		log.info("infoMsg:--- 微信异步通知开始");
		Response resp = this.getReponse();
		BufferedReader reader = null;
		String wx_map = "";
		try {
			PlatformMutualityManagent pmm = platformMutualityManagentService.findOne(5);
			Assert.notNull(pmm);
			String app_key = pmm.getClient_id();
	        reader = request.getReader();
	        String line = "";
	        String xmlString = null;
	        StringBuffer inputString = new StringBuffer();
	        while ((line = reader.readLine()) != null) {
	            inputString.append(line);
	        }
	        xmlString = inputString.toString();
	        request.getReader().close();
	        if(!StringUtils.isBlank(xmlString)) {
	        	Map<String, String> return_map = WXPayUtil.xmlToMap(xmlString);
	        	//验签
	        	if(WXPayUtil.isSignatureValid(xmlString, app_key)) {
	        		if(return_map.get("return_map").equals("SUCCESS")) {
	        			//TODO 账变，修改状态，到账提醒
	    				Double amount = Double.parseDouble(return_map.get("total_fee"));
	    				String passbackParams = return_map.get("total_fee");
	    				String order_no = return_map.get("out_trade_no");
	    				boolean result = rechargeRecordService.updateBill(amount,passbackParams,order_no);
	    				if(result) {
	    					Map<String, String> map = new HashMap<>();
	    					map.put("return_map", "SUCCESS");
	    					map.put("return_msg", "OK");
	    					wx_map = WXPayUtil.mapToXml(map);
	    				}
	        		}
	        	}
	        }
			log.info("infoMsg:--- 微信异步通知失败");
			return resp.success(wx_map);
		} catch (Exception e) {
			log.error("errorMsg:--- 微信通知失败" + e.getMessage());
			return resp.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 微信支付订单查询
	 * 
	 * @param transaction_id 微信订单号
	 * @param out_trade_no	平台订单号
	 * @return
	 */
	@RequestMapping(value=Route.Payment.WEIXIN_PAY_QUERY,method=RequestMethod.POST)
	@ResponseBody
	public Response weixin_pay_query(@RequestParam("transaction_id") String transaction_id,@RequestParam("out_trade_no") String out_trade_no) {
		log.info("infoMsg:--- 微信支付订单查询开始");	
		Response resp = this.getReponse();
		String sign = "";
		String message = "";
		Map<String, String> return_map = null;
		try {
			ThirdPayBean bean = thirdPayService.findByPayId(19);
			Assert.notNull(bean);
			String mch_id = bean.getMer_no();  	//商户号
			PlatformMutualityManagent pmm = platformMutualityManagentService.findOne(5);
			Assert.notNull(pmm);
			String appid = pmm.getClient_id();  	//应用ID
			String App_Secret = pmm.getClient_secret();	
			String nonce_str = WXPayUtil.generateNonceStr();		//随机字符串
			
			StringBuffer sb = new StringBuffer();
			sb.append("appid=").append(appid).append("&");
			sb.append("nonce_str=").append(nonce_str).append("&");
			sb.append("out_trade_no=").append(out_trade_no).append("&");
			String params = sb.toString();
			//需要签名的数据
			String stringSignTemp = params + "&key=" + App_Secret;
			//MD5签名方式
			sign = WXPayUtil.MD5(stringSignTemp).toUpperCase();
			Map<String, Object> req_map = new HashMap<>();
			req_map.put("appid", appid);
			req_map.put("mch_id", mch_id);
			req_map.put("transaction_id", transaction_id);
			req_map.put("out_trade_no", out_trade_no);
			req_map.put("nonce_str", nonce_str);
			req_map.put("sign", sign);
			List<UHeader> headerList = new ArrayList<>();
			headerList.add(new UHeader("Content-Type", "application/x-www-form-urlencoded"));
			String postResponse = MaryunHttpUtils.getPostResponse(weixin_query_Url, req_map, headerList);
			if(StringUtils.trim(postResponse).equals("")) {
				return_map = WXPayUtil.xmlToMap(postResponse);
				if(!return_map.isEmpty()) {
					String return_code = return_map.get("return_code");
					if(return_code.equals("SUCCESS")) {
						String result_code = return_map.get("return_code");
						if(result_code.equals("SUCCESS")) {
							message = (String) req_map.get("trade_state_desc");
						}
					}else {
						message = (String) req_map.get("err_code_des");
					}
				}
			}
			log.info("infoMsg:--- 微信支付订单查询结束");	
			return resp.success(return_map);
		} catch (Exception e) {
			log.error("erroroMsg:--- 微信支付订单查询失败" + e.getMessage() + message);	
			return resp.failure(e.getMessage() + message);
		}
	}
	
	
	
	
}
