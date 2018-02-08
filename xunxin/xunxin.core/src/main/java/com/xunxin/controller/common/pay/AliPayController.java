package com.xunxin.controller.common.pay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.xunxin.constants.AmountConstants;
import com.xunxin.constants.ExpConstants;
import com.xunxin.controller.system.BaseController;
import com.xunxin.dao.pay.RechargeRecordDao;
import com.xunxin.service.ThirdPayService;
import com.xunxin.service.TransferRecordService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.RechargeRecordService;
import com.xunxin.service.app.user.UserAmountChangeRecordService;
import com.xunxin.util.MD5_UTIL;
import com.xunxin.util.app.OrderGeneratedUtils;
import com.xunxin.vo.account.RechargeRecord;
import com.xunxin.vo.account.UserAmountChangeRecord;
import com.xunxin.vo.pay.ThirdPayBean;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Route;


/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月2日 -- 上午3:06:55
 * @Version 1.0
 * @Description	 支付宝 APP支付 SDK接入
 */
@Controller
@RequestMapping(value=Route.PATH+Route.Payment.PATH)
public class AliPayController extends BaseController{

	private static final String alipay_Url = "https://openapi.alipay.com/gateway.do";
	private static final Logger log = Logger.getLogger(AliPayController.class);
	
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private ThirdPayService thirdPayService;
	@Autowired
	private RechargeRecordService rechargeRecordService;
	@Autowired
	private TransferRecordService transferRecordService;
	@Autowired
	private UserAmountChangeRecordService userAmountChangeRecordService;
	@Autowired
	private RechargeRecordDao rechargeRecordDao;
	
	/**
	 * 支付宝请求交易
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping(value=Route.Payment.ALI_PAY_H,method=RequestMethod.POST)
	@ResponseBody
	public Response ali_pay_h(@RequestParam("userId") int userId,@RequestParam("amount") String amount) {
		log.info("infoMsg:--- 支付宝请求交易开始");
		Response resp = this.getReponse();
		try {
			//----------------请求参数------------------//
			ThirdPayBean payBean = thirdPayService.findByPayId(18);
//			Assert.notNull(payBean);
			String notify_url = payBean.getReturn_url();
			String APP_ID = payBean.getMer_no();	//支付宝商戶号
//			String mer_key = payBean.getMer_key();	//支付宝公钥|商户私钥
			String ALIPAY_PUBLIC_KEY = new String("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsbwAZ/90rKQ1MptCdlS96TqYOJa2ypyM4EOjmFYsb2wSgtxNpmWKip6d2Tcobz9jjZvSmalB2RfWBKO82VSyRBLdn3CKdnUtGRMNUN5o3ayElmhWKDzf3LiuP0RWjyXXhMe4ldlXrbYX6ZjnUxmXGLdD1B++yj1hRsZUyScZYdaXb64hxbq4e4GdezGmSE0aRI3ajqOe2DBgbwbJwMKdybp+5iodgc6fag86cYueQ67CpS4BqWyF8rclLvyJUd44VfP1xgxrWpLJVc7gpXsvXDMlTWwM4CPw3OuzwkYuUTDzVGKRenbZRJkFi4FDfKTaBGgiydm39NKs4pJKBQz2KQIDAQAB");  
	        String APP_PRIVATE_KEY = new String("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCd9ISQRnsfWSWLvEMOsazWbP3J2qtwqm82PKbUggDP4upB+tliPJ1afaswrJRNL7rTW/WEGKbXUOWjfq3RuAJoj0qfxpFhb3HIpZqnkqhyx0u8KQut4DZI13nJ2nK+Ixxbg7zThKQAXfVAr3xx1PhKP5Pceyxz3AcZOzRUxJCzF2YR6TG1qbk8b0cOah9pTtM4tsmKXEo0Uldm+rEb7VBI8Fb7uXp8vunyEGkTgJK4eSZDVkEJ1rdydEy78cj8Ix7oQuO6/nNLfcEtYajiNicTsEOoM2Ane/7S3V1veTCqbVb1agSj7s+lleFP6lcgqReNgk0phf3BpVYnM2XY88epAgMBAAECggEAI5OUXA6T4q6ohz2i+OdJ343y54kJ/jlVDSlCBjE1z5zzWGMQnVC7vEr7yN3GFVB/yuU2ekc3JN4Cqv14VvkUCcrkavJFgmWgginSmJWuvRNoWnwANmx+rY9izfUWzP6Jf48/c4C3k6GWALjF1bm7JrYFLq7Lh1jyfFtaYRFY6g0riaAhPKS/RY1L1tNqzOoS2rge7z9qJJFyDpZM0Lu6pdI9/LvVHS5j6o7WIrqb7gsKgS267uAMFqXwRcyq/P/oTj0wbrlAtgDOfoHAmITbue2jE/oAK18vswefXud5IiWpIKFRRiDNz8UqAUPgmXdEUAVJqgzgTiMHlzKQt7zTZQKBgQDJOLJaLEpRtqGwEhTVzy2r6mnlG29TuapV7Esw3wSqHwx8vLdb0VtgTPq32i1W7BE0p6ZHPkDDOKURRHwCz2as5fh+Udt/YNbTj5nHhudFZ0KULJ0OsPDslx5pe5H1FBPBXPcekzDfwnGOFNOH75gcrAsTiM9XaktOtKRxelRXUwKBgQDI9I10i/UBrezIdo/pz5mjrRSNCRlNVUoMRBnXWeoHqAuAfxxy+TuYVdTelVn8ZgNy7lt+/w9j64XSHMq5S29eJg+FuOKV3HBtiGjxZOz1blEfF3b1pIxNJNAZJPOzpGJNmYQw+rt5s75wXA9n/q/us3HADClMVLFAczKJAt5xkwKBgCHvwvy8TYh8gcZ9NjBdMbm13kg6mUsInDbDlGbYpiO++s8q0M3WgE+8i+hoDo+DXt9/iuanFCsYqZZA851Rt2JfospDKf7QqUqjBG+HTAgDg1IUOCTbKLbuQb3Ojm5EBZTuBeuNLYf/dkFdN9PMT94+EdwojbeTgMH0a2uMEx9rAoGAYW2fv2ezq9LFQBOrhnJuTNq3YgGNUN8O/Y9u7+fZ/UhN+0ilZGDNsfe7Mwc6D5LuDSTfG11R+uHPiaUH7HpUTlMpp22R/ZJYt+Iw7wg9kmifz/EybboPg79bXTV7KheCyZiqbIzDpCevJw6bMZJbfeFmPvQmeal+Hn87ew33Bx0CgYEAkLJ/KSeRbco/jvgzg7G5PHaZbRgrXVmBpfHCsXt/bAqEb0ea2hAG/tJ8yw+8zGG0R8WORZoojWqKfDTjm+0bR+YQxa3HJ/1El9A9jixPgpS+yf9dwBRyCNr2hTepzoaCWAZy5Cialn5FPfSHPJHkNaHPjtxWPihb1s0BzZb3I1s=");  
			ExtendParams extendParams = new ExtendParams();
			extendParams.setSysServiceProviderId(userId + "|" + 18);	//拓展参数
			
			//实例化客户端
			AlipayClient alipayClient = new DefaultAlipayClient(alipay_Url, APP_ID, APP_PRIVATE_KEY, "JSON", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2");
			AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();
			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
			model.setSubject("循心币");
			String orderNo = OrderGeneratedUtils.getOrderNo();
			model.setOutTradeNo(orderNo);
			model.setTimeoutExpress("30m");
			model.setTotalAmount(amount);
			model.setProductCode("QUICK_WAP_WAY");
			model.setExtendParams(extendParams);
		    alipay_request.setBizModel(model);
		    // 设置异步通知地址
		    alipay_request.setNotifyUrl(notify_url);
		    // 设置同步地址
		    alipay_request.setReturnUrl("www.xunxinkeji.cn://");
		    String form = alipayClient.pageExecute(alipay_request).getBody();
	        //生成账单
	        boolean result = rechargeRecordService.generatedBills(orderNo,extendParams);
	        if(!result) {
	        	log.error("订单生成失败");
	        }
	        log.info("infoMsg:--- 支付宝请求交易结束");
	        return resp.success(form);
	    } catch (AlipayApiException e) {
	    	log.error("errorMsg:--- 支付宝请求交易失败" + e.getMessage());
	    	return resp.failure(e.getMessage());
		}
	}
	
	/**
	 * 支付宝支付通知地址 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value=Route.Payment.ALI_PAY_NOTIFY,method=RequestMethod.POST)
	@ResponseBody
	public Response alipay_notify(HttpServletRequest request) {
		log.info("infoMsg:--- 支付宝验证异步通知信息开始");
		Response resp = this.getReponse();
		//----------------请求参数------------------//
			ThirdPayBean payBean = thirdPayService.findByPayId(18);
			String mer_key = payBean.getMer_key();	//支付宝公钥|商户私钥
			String public_key = mer_key.split("|")[0];
			String ALIPAY_PUBLIC_KEY = MD5_UTIL.convertMD5(MD5_UTIL.convertMD5(public_key));
			
			//获取支付宝POST过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			log.info("infoMsg:--- 支付返回的信息"+requestParams);
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			    String name = (String) iter.next();
			    String[] values = (String[]) requestParams.get(name);
			    String valueStr = "";
			    for (int i = 0; i < values.length; i++) {
			        valueStr = (i == values.length - 1) ? valueStr + values[i]
			                    : valueStr + values[i] + ",";
			  	}
			    //乱码解决，这段代码在出现乱码时使用。
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
//				boolean flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "utf-8","RSA2");
				log.info("infoMsg:--- 支付宝信息验证"+true);
				//TODO 账变，修改状态，到账提醒
				Double amount = Double.parseDouble(params.get("total_amount"));
				log.info("infoMsg:--- 支付宝信息验证amount"+amount);
//				String passbackParams = params.get("passbackParams");
				String order_no = params.get("out_trade_no");
				log.info("infoMsg:--- 支付宝信息验证out_trade_no"+order_no);
				boolean result = rechargeRecordService.updateBill(amount,order_no);
					if(result) {
						RechargeRecord rechargeRecord = rechargeRecordDao.findRechargeRecordByOrderNo(order_no);
					    UserAmountChangeRecord record = new UserAmountChangeRecord();
					    record.setDirection(ExpConstants.INCOME);
					    record.setChangeType("");
					    record.setTansferAmount(amount);
					    if(rechargeRecord != null && (Integer)rechargeRecord.getUserId() != null){
						    UserEntity user = appUserService.findById(rechargeRecord.getUserId());
						    Double order_before = user.getAmount();     //充值前余额
				            Double order_end = order_before + amount;     //充值后金额
				            record.setChangeType(AmountConstants.RECHARGE);
						    record.setTansferBefore(order_before);
						    record.setTansferEnd(order_end);
						    record.setUserId(rechargeRecord.getUserId());
						    userAmountChangeRecordService.save(record);
						    return resp.success("SUCCESS");
					    }
					}
			log.info("infoMsg:--- 支付宝验证异步通知信息结束");
			return resp.success("SUCCESS");
			
	}
//	
//	/**
//	 * 支付宝单笔提现
//	 * 
//	 * @param userId
//	 * @param amount
//	 * @return
//	 */
//	@RequestMapping(value=Route.Payment.ALI_PAY_TRANSFER,method=RequestMethod.POST)
//	@ResponseBody
//	public Response alipay_transfer(@RequestParam("userId") int userId,@RequestParam("amount") String amount) {
//		String message = "";
//		log.info("infoMsg:--- 支付宝提现开始");
//		Response resp = this.getReponse();
//		try {
//			//----------------请求参数------------------//
//			ThirdPayBean payBean = thirdPayService.findByPayId(18);
//			Assert.notNull(payBean);
//			String APP_ID = payBean.getMer_no();	//支付宝商戶号
//			String mer_key = payBean.getMer_key();	//支付宝公钥|商户私钥
//			String public_key = mer_key.split("\\|")[0];
//			String private_key = mer_key.split("\\|")[1];
//			String ALIPAY_PUBLIC_KEY = MD5_UTIL.convertMD5(MD5_UTIL.convertMD5(public_key));
//			String APP_PRIVATE_KEY = MD5_UTIL.convertMD5(MD5_UTIL.convertMD5(private_key));
//			String passbackParams = userId + "|" + "18";	//拓展参数
//			String orderNo = OrderGeneratedUtils.getOrderNo();
//			
//			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",APP_ID,APP_PRIVATE_KEY,"JSON","utf-8",ALIPAY_PUBLIC_KEY,"RSA2");
//			AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
//			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//			//转账参数拼装 TODO
//			model.setSubject("循心科技");
//			model.setOutTradeNo(orderNo);
//			model.setTimeoutExpress("30m");
//			model.setProductCode("QUICK_MSECURITY_PAY");
//			request.setBizModel(model);
//			AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
//			String body = response.getBody();
//			//解析返回值
//			message = rechargeRecordService.createTransfer(body,passbackParams);
//			if(message.equalsIgnoreCase("Success")) {
//				boolean result = transferRecordService.saveTransRecord(body,amount,passbackParams);
//				if(result) {
//					log.info("infoMsg:--- 支付宝提现成功");
//				}
//			}else {
//				log.error("errorMsg:--- 支付宝提现失败" + message);
//			}
//			log.info("infoMsg:--- 支付宝提现结束");
//			return resp.success();
//		} catch (AlipayApiException e) {
//			log.error("errorMsg:--- 支付宝提现失败" + e.getMessage());
//			return resp.failure(message + e.getMessage());
//		}
//	}
//	
//	
//	/**
//	 * 支付宝支付订单查询
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value=Route.Payment.ALI_PAY_QUERY,method=RequestMethod.POST)
//	@ResponseBody
//	public Response alipay_query(HttpServletRequest req) {
//		log.info("infoMsg:--- 支付宝订单查询开始");
//		Response resp = this.getReponse();
//		try {
//			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","GBK","alipay_public_key","RSA2");
//			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
//			request.setBizContent("{" +
//			"\"out_trade_no\":\"20150320010101001\"," +
//			"\"trade_no\":\"2014112611001004680 073956707\"" +
//			"  }");
//			AlipayTradeQueryResponse response;
//				response = alipayClient.execute(request);
//				if(response.isSuccess()){
//					System.out.println("调用成功");
//				} else {
//					System.out.println("调用失败");
//				}
//				log.info("infoMsg:--- 支付宝订单查询结束");
//				return resp.success();
//			} catch (AlipayApiException e) {
//				log.error("errorMsg:--- 支付宝订单查询失败");
//				return resp.failure(e.getMessage());
//			}
//	}
//	
//	
	
	
	
}
