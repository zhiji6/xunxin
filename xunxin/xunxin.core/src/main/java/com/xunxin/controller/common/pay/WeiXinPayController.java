package com.xunxin.controller.common.pay;

import java.io.BufferedReader;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.ExtendParams;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.xunxin.constants.ExpConstants;
import com.xunxin.controller.system.BaseController;
import com.xunxin.service.PlatformMutualityManagentService;
import com.xunxin.service.ThirdPayService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.RechargeRecordService;
import com.xunxin.service.app.user.UserAmountChangeRecordService;
import com.xunxin.util.MD5_UTIL;
import com.xunxin.util.MaryunHttpUtils;
import com.xunxin.util.MaryunHttpUtils.UHeader;
import com.xunxin.util.app.OrderGeneratedUtils;
import com.xunxin.util.app.WXPayUtil;
import com.xunxin.util.app.WXRandomNumberUtil;
import com.xunxin.util.app.h5Pay.weixin.HttpUtil;
import com.xunxin.vo.account.UserAmountChangeRecord;
import com.xunxin.vo.pay.ThirdPayBean;
import com.xunxin.vo.sys.PlatformMutualityManagent;
import com.xunxin.vo.user.UserEntity;
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
	private static final String alipay_Url = "https://openapi.alipay.com/gateway.do";
	
	@Autowired
	private ThirdPayService thirdPayService;
	@Autowired
	private RechargeRecordService rechargeRecordService;
	@Autowired
	private PlatformMutualityManagentService platformMutualityManagentService;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private UserAmountChangeRecordService userAmountChangeRecordService;

	/**
     * 支付宝请求交易
     * 
     * @param bean
     * @return
     */
//    @RequestMapping(value=Route.Payment.ALI_PAY,method=RequestMethod.POST)
//    @ResponseBody
//    public Response ali_pay(@RequestParam("userId") int userId,@RequestParam("amount") String amount) {
//        log.info("infoMsg:--- 支付宝请求交易开始");
//        Response resp = this.getReponse();
//        try {
//            //----------------请求参数------------------//
//            ThirdPayBean payBean = thirdPayService.findByPayId(18);
////          Assert.notNull(payBean);
//            String notify_url = payBean.getReturn_url();
//            String APP_ID = payBean.getMer_no();    //支付宝商戶号
////          String mer_key = payBean.getMer_key();  //支付宝公钥|商户私钥
//            String ALIPAY_PUBLIC_KEY = new String("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsbwAZ/90rKQ1MptCdlS96TqYOJa2ypyM4EOjmFYsb2wSgtxNpmWKip6d2Tcobz9jjZvSmalB2RfWBKO82VSyRBLdn3CKdnUtGRMNUN5o3ayElmhWKDzf3LiuP0RWjyXXhMe4ldlXrbYX6ZjnUxmXGLdD1B++yj1hRsZUyScZYdaXb64hxbq4e4GdezGmSE0aRI3ajqOe2DBgbwbJwMKdybp+5iodgc6fag86cYueQ67CpS4BqWyF8rclLvyJUd44VfP1xgxrWpLJVc7gpXsvXDMlTWwM4CPw3OuzwkYuUTDzVGKRenbZRJkFi4FDfKTaBGgiydm39NKs4pJKBQz2KQIDAQAB");  
//            String APP_PRIVATE_KEY = new String("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCd9ISQRnsfWSWLvEMOsazWbP3J2qtwqm82PKbUggDP4upB+tliPJ1afaswrJRNL7rTW/WEGKbXUOWjfq3RuAJoj0qfxpFhb3HIpZqnkqhyx0u8KQut4DZI13nJ2nK+Ixxbg7zThKQAXfVAr3xx1PhKP5Pceyxz3AcZOzRUxJCzF2YR6TG1qbk8b0cOah9pTtM4tsmKXEo0Uldm+rEb7VBI8Fb7uXp8vunyEGkTgJK4eSZDVkEJ1rdydEy78cj8Ix7oQuO6/nNLfcEtYajiNicTsEOoM2Ane/7S3V1veTCqbVb1agSj7s+lleFP6lcgqReNgk0phf3BpVYnM2XY88epAgMBAAECggEAI5OUXA6T4q6ohz2i+OdJ343y54kJ/jlVDSlCBjE1z5zzWGMQnVC7vEr7yN3GFVB/yuU2ekc3JN4Cqv14VvkUCcrkavJFgmWgginSmJWuvRNoWnwANmx+rY9izfUWzP6Jf48/c4C3k6GWALjF1bm7JrYFLq7Lh1jyfFtaYRFY6g0riaAhPKS/RY1L1tNqzOoS2rge7z9qJJFyDpZM0Lu6pdI9/LvVHS5j6o7WIrqb7gsKgS267uAMFqXwRcyq/P/oTj0wbrlAtgDOfoHAmITbue2jE/oAK18vswefXud5IiWpIKFRRiDNz8UqAUPgmXdEUAVJqgzgTiMHlzKQt7zTZQKBgQDJOLJaLEpRtqGwEhTVzy2r6mnlG29TuapV7Esw3wSqHwx8vLdb0VtgTPq32i1W7BE0p6ZHPkDDOKURRHwCz2as5fh+Udt/YNbTj5nHhudFZ0KULJ0OsPDslx5pe5H1FBPBXPcekzDfwnGOFNOH75gcrAsTiM9XaktOtKRxelRXUwKBgQDI9I10i/UBrezIdo/pz5mjrRSNCRlNVUoMRBnXWeoHqAuAfxxy+TuYVdTelVn8ZgNy7lt+/w9j64XSHMq5S29eJg+FuOKV3HBtiGjxZOz1blEfF3b1pIxNJNAZJPOzpGJNmYQw+rt5s75wXA9n/q/us3HADClMVLFAczKJAt5xkwKBgCHvwvy8TYh8gcZ9NjBdMbm13kg6mUsInDbDlGbYpiO++s8q0M3WgE+8i+hoDo+DXt9/iuanFCsYqZZA851Rt2JfospDKf7QqUqjBG+HTAgDg1IUOCTbKLbuQb3Ojm5EBZTuBeuNLYf/dkFdN9PMT94+EdwojbeTgMH0a2uMEx9rAoGAYW2fv2ezq9LFQBOrhnJuTNq3YgGNUN8O/Y9u7+fZ/UhN+0ilZGDNsfe7Mwc6D5LuDSTfG11R+uHPiaUH7HpUTlMpp22R/ZJYt+Iw7wg9kmifz/EybboPg79bXTV7KheCyZiqbIzDpCevJw6bMZJbfeFmPvQmeal+Hn87ew33Bx0CgYEAkLJ/KSeRbco/jvgzg7G5PHaZbRgrXVmBpfHCsXt/bAqEb0ea2hAG/tJ8yw+8zGG0R8WORZoojWqKfDTjm+0bR+YQxa3HJ/1El9A9jixPgpS+yf9dwBRyCNr2hTepzoaCWAZy5Cialn5FPfSHPJHkNaHPjtxWPihb1s0BzZb3I1s=");  
//            ExtendParams extendParams = new ExtendParams();
//            extendParams.setSysServiceProviderId(userId + "|" + 18);    //拓展参数
//            
//            //实例化客户端
//            AlipayClient alipayClient = new DefaultAlipayClient(alipay_Url, APP_ID, APP_PRIVATE_KEY, "JSON", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2");
//            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
//            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
//            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//            model.setSubject("循心科技");
//            String orderNo = OrderGeneratedUtils.getOrderNo();
//            model.setOutTradeNo(orderNo);
//            model.setTimeoutExpress("30m");
//            model.setTotalAmount(amount);
//            model.setProductCode("QUICK_MSECURITY_PAY");
//            model.setExtendParams(extendParams);
//            request.setBizModel(model);
//            request.setNotifyUrl(notify_url);
//            //这里和普通的接口调用不同，使用的是sdkExecute
//            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
////          System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
//            //生成账单
//            boolean result = rechargeRecordService.generatedBills(orderNo,extendParams);
//            if(!result) {
//                log.error("订单生成失败");
//            }
//            log.info("infoMsg:--- 支付宝请求交易结束");
//            return resp.success(response.getBody());
//        } catch (AlipayApiException e) {
//            log.error("errorMsg:--- 支付宝请求交易失败" + e.getMessage());
//            return resp.failure(e.getMessage());
//        }
//    }
//    
//    /**
//     * 支付宝支付通知地址
//     * 
//     * @param request
//     * @return
//     */
//    @RequestMapping(value=Route.Payment.ALI_PAY_NOTIFY,method=RequestMethod.POST)
//    @ResponseBody
//    public Response alipay_notify(HttpServletRequest request) {
//        log.info("infoMsg:--- 支付宝验证异步通知信息开始");
//        Response resp = this.getReponse();
//        try {
//        //----------------请求参数------------------//
//            ThirdPayBean payBean = thirdPayService.findByPayId(18);
//            Assert.notNull(payBean);
//            String mer_key = payBean.getMer_key();  //支付宝公钥|商户私钥
//            String public_key = mer_key.split("|")[0];
//            String ALIPAY_PUBLIC_KEY = MD5_UTIL.convertMD5(MD5_UTIL.convertMD5(public_key));
//            
//            //获取支付宝POST过来反馈信息
//            Map<String,String> params = new HashMap<String,String>();
//            Map requestParams = request.getParameterMap();
//            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
//                String name = (String) iter.next();
//                String[] values = (String[]) requestParams.get(name);
//                String valueStr = "";
//                for (int i = 0; i < values.length; i++) {
//                    valueStr = (i == values.length - 1) ? valueStr + values[i]
//                                : valueStr + values[i] + ",";
//                }
//                //乱码解决，这段代码在出现乱码时使用。
//                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//                params.put(name, valueStr);
//            }
//        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
//        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
//            boolean flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "utf-8","RSA2");
//            if(flag) {
//                //TODO 账变，修改状态，到账提醒
//                Double amount = Double.parseDouble(params.get("amount"));
//                String passbackParams = params.get("passbackParams");
//                String order_no = params.get("out_trade_no");
//                boolean result = rechargeRecordService.updateBill(amount,passbackParams,order_no);
//                if(result) {
//                    UserAmountChangeRecord record = new UserAmountChangeRecord();
//                    record.setDirection(ExpConstants.INCOME);
//                    record.setChangeType("");
//                    record.setTansferAmount(amount);
//                    int userId = Integer.parseInt(passbackParams.split("\\|")[0]);
//                    UserEntity user = appUserService.findById(userId);
//                    Double order_before = user.getAmount();     //充值前余额
//                    Double order_end = order_before + amount;     //充值后金额
//                    record.setTansferBefore(order_before);
//                    record.setTansferEnd(order_end);
//                    record.setUserId(userId);
//                    userAmountChangeRecordService.save(record);
//                }
//            }
//            log.info("infoMsg:--- 支付宝验证异步通知信息结束");
//            return resp.success();
//        } catch (AlipayApiException e) {
//            log.info("infoMsg:--- 支付宝验证异步通知信息结束" + e.getMessage());
//            return resp.failure(e.getMessage());
//        }
//    }
    
	
	
	/**
	 * 微信统一下单请求交易
	 * 
	 * @param userId
	 * @param amount
	 * @return
	 */
	@RequestMapping(value=Route.Payment.WEIXIN_PAY_H)
	@ResponseBody
	public Response weixin_pay_h( Integer userId,String amount,HttpServletRequest request) {
		log.info("InfoMsg:--- 微信统一下单请求交易开始");
		Response resp = this.getReponse();
		String message = "";
		try {
			ThirdPayBean bean = thirdPayService.findByPayId(19);
			String mch_id = bean.getMer_no();  	//商户号
			String notify_url = String.format("http://www.xunxinkeji.cn/api/pay/weixin_notify_h");	//通知地址
			PlatformMutualityManagent pmm = platformMutualityManagentService.findOne(5);
			String appid = pmm.getClient_id();  	//应用ID
//			String App_Secret = pmm.getClient_secret();
			String App_Secret = "71967313C8853EDADDA5DA6E6981050C";
			String nonce_str = WXPayUtil.generateNonceStr();		//随机字符串
			String sign = "";				//签名
			String sign_type = "MD5";				//签名类型
			String body = "循心币";				//商品描述 ps:腾讯充值中心-QQ会员充值
			String out_trade_no = OrderGeneratedUtils.getOrderNo();		//商户订单号
			int total_fee = (int)(Double.parseDouble(amount) * 100);	//交易金额
		     String ip = request.getHeader("x-forwarded-for");
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("Proxy-Client-IP");
				}
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("WL-Proxy-Client-IP");
				}
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getRemoteAddr();
				}
				ip = ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
				System.out.println(ip);
			String trade_type = "MWEB";		//交易类型 
			String attach = userId + "|" + bean.getBank_id();	//附加数据 ps:用户|支付方式
			
			StringBuffer sb = new StringBuffer();
			sb.append("appid=").append(appid).append("&");
//			sb.append("attach=").append(attach).append("&");
			sb.append("body=").append(body).append("&");
			sb.append("mch_id=").append(mch_id).append("&");
			sb.append("nonce_str=").append(nonce_str).append("&");
			sb.append("notify_url=").append(notify_url).append("&");
			sb.append("out_trade_no=").append(out_trade_no).append("&");
//			sb.append("sign_type=").append(sign_type).append("&");
			sb.append("spbill_create_ip=").append(ip).append("&");
			sb.append("total_fee=").append(total_fee).append("&");
			sb.append("trade_type=").append(trade_type);
			String params = sb.toString();
			//需要签名的数据
			String stringSignTemp = params + "&key=" + App_Secret;
			//MD5签名方式
			sign = WXPayUtil.MD5(stringSignTemp).toUpperCase();
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("appid", appid);
			map.put("body", body);
			map.put("mch_id", mch_id);
			map.put("nonce_str", nonce_str);
			map.put("notify_url", notify_url);
			map.put("out_trade_no", out_trade_no);
			map.put("spbill_create_ip", ip);
			map.put("total_fee", total_fee+"");
			map.put("trade_type", trade_type);
			map.put("sign", sign);
		     String xmlstring =WXPayUtil.mapToXml(map);
		     System.out.println("提交的签名"+xmlstring);
		     Map<String,String> xmlr=null;
	    	 String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
	         String postResponse = HttpUtil.sendPostUrl(url,String.format(xmlstring));
	         System.out.println(postResponse);
			Map<String, String> response = WXPayUtil.xmlToMap(new String(postResponse.getBytes("ISO-8859-1"), "UTF-8"));
			if(!response.isEmpty() && response.get("return_code").equals("SUCCESS")){
				if(response.get("result_code").equals("SUCCESS")) {
					boolean result = rechargeRecordService.generatedBills(response,attach,amount,out_trade_no);
					if(result) {
						log.info("InfoMsg:--- 微信统一下单请求交易成功");
						return resp.success(response.get("mweb_url"));
					}
				}else {
					message = response.get("err_code_des");
					log.error("errorMsg:--- 微信统一下单请求交易解析失败" + message);
					return resp.failure("error");
				}
			}else {
				log.error("errorMsg:--- 微信统一下单请求交易解析失败" + message);
				return resp.failure("error");
			}
			return resp.failure("error");
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
	@RequestMapping(value=Route.Payment.WEIXIN_PAY_NOTIFY_H)
	@ResponseBody
	public Response weixin_notify_h(HttpServletRequest request) {
		log.info("infoMsg:--- 微信异步通知开始");
		Response resp = this.getReponse();
		BufferedReader reader = null;
		String wx_map = "";
		try {
			PlatformMutualityManagent pmm = platformMutualityManagentService.findOne(5);
			Assert.notNull(pmm);
			String app_key = pmm.getClient_id();
			log.info("infoMsg:--- app_key"+app_key);
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
	        	log.info("infoMsg:--- xmlString"+xmlString);
	        	Map<String, String> return_map = WXPayUtil.xmlToMap(xmlString);
	        	log.info("infoMsg:--- return_map"+return_map);
	        		log.info("infoMsg:--- appKey校验成功");
	        		if(return_map.get("return_code").equals("SUCCESS")) {
	        			//TODO 账变，修改状态，到账提醒
	    				Double amount = Double.parseDouble(return_map.get("total_fee"));
	    				String order_no = return_map.get("out_trade_no");
	    				Double returnAmount = amount/100;
	    				boolean result = rechargeRecordService.updateBillWeiXin(returnAmount,order_no);
	    				log.info("infoMsg:--- 微信异步通知成功");
	    				return resp.success("SUCCESS");
	        		}
	        }
			log.info("infoMsg:--- 微信异步通知失败");
			return resp.success(wx_map);
		} catch (Exception e) {
			log.error("errorMsg:--- 微信通知失败" + e.getMessage());
			return resp.failure(e.getMessage());
		}
	}
}
