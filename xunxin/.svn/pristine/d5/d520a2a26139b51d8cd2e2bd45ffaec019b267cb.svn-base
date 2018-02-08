package com.xunxin.controller.common.pay;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.domain.ExtendParams;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.xunxin.controller.system.BaseController;
import com.xunxin.service.ThirdPayService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.RechargeRecordService;
import com.xunxin.util.app.OrderGeneratedUtils;
import com.xunxin.vo.pay.ThirdPayBean;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Route;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月23日 -- 下午3:07:51
 * @Version 1.0
 * @Description		支付宝扫码付
 */
@Controller
@RequestMapping(value=Route.PATH+Route.Payment.PATH)
public class AliPayScanController extends BaseController {
	
	private static final String alipay_Url = "https://openapi.alipay.com/gateway.do";
	private static final Logger log = Logger.getLogger(AliPayScanController.class);
	
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private ThirdPayService thirdPayService;
	@Autowired
	private RechargeRecordService rechargeRecordService;
	
	@RequestMapping(value=Route.Payment.ALI_SCAN_PAY,method=RequestMethod.POST)
	@ResponseBody
	public Response ali_scan_pay(@RequestParam("userId") int userId,@RequestParam("bank_id") int bank_id,	
			@RequestParam("amount") String amount) {
		log.info("infoMsg:--- 支付宝扫码付开始");
		Response reponse = this.getReponse();
		try {
			//----------------请求参数------------------//
			ThirdPayBean payBean = thirdPayService.findByPayId(bank_id);
			String notify_url = payBean.getReturn_url();
			String APP_ID = payBean.getMer_no();	//支付宝商戶号
			String ALIPAY_PUBLIC_KEY = new String("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsbwAZ/90rKQ1MptCdlS96TqYOJa2ypyM4EOjmFYsb2wSgtxNpmWKip6d2Tcobz9jjZvSmalB2RfWBKO82VSyRBLdn3CKdnUtGRMNUN5o3ayElmhWKDzf3LiuP0RWjyXXhMe4ldlXrbYX6ZjnUxmXGLdD1B++yj1hRsZUyScZYdaXb64hxbq4e4GdezGmSE0aRI3ajqOe2DBgbwbJwMKdybp+5iodgc6fag86cYueQ67CpS4BqWyF8rclLvyJUd44VfP1xgxrWpLJVc7gpXsvXDMlTWwM4CPw3OuzwkYuUTDzVGKRenbZRJkFi4FDfKTaBGgiydm39NKs4pJKBQz2KQIDAQAB");  
	        String APP_PRIVATE_KEY = new String("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCd9ISQRnsfWSWLvEMOsazWbP3J2qtwqm82PKbUggDP4upB+tliPJ1afaswrJRNL7rTW/WEGKbXUOWjfq3RuAJoj0qfxpFhb3HIpZqnkqhyx0u8KQut4DZI13nJ2nK+Ixxbg7zThKQAXfVAr3xx1PhKP5Pceyxz3AcZOzRUxJCzF2YR6TG1qbk8b0cOah9pTtM4tsmKXEo0Uldm+rEb7VBI8Fb7uXp8vunyEGkTgJK4eSZDVkEJ1rdydEy78cj8Ix7oQuO6/nNLfcEtYajiNicTsEOoM2Ane/7S3V1veTCqbVb1agSj7s+lleFP6lcgqReNgk0phf3BpVYnM2XY88epAgMBAAECggEAI5OUXA6T4q6ohz2i+OdJ343y54kJ/jlVDSlCBjE1z5zzWGMQnVC7vEr7yN3GFVB/yuU2ekc3JN4Cqv14VvkUCcrkavJFgmWgginSmJWuvRNoWnwANmx+rY9izfUWzP6Jf48/c4C3k6GWALjF1bm7JrYFLq7Lh1jyfFtaYRFY6g0riaAhPKS/RY1L1tNqzOoS2rge7z9qJJFyDpZM0Lu6pdI9/LvVHS5j6o7WIrqb7gsKgS267uAMFqXwRcyq/P/oTj0wbrlAtgDOfoHAmITbue2jE/oAK18vswefXud5IiWpIKFRRiDNz8UqAUPgmXdEUAVJqgzgTiMHlzKQt7zTZQKBgQDJOLJaLEpRtqGwEhTVzy2r6mnlG29TuapV7Esw3wSqHwx8vLdb0VtgTPq32i1W7BE0p6ZHPkDDOKURRHwCz2as5fh+Udt/YNbTj5nHhudFZ0KULJ0OsPDslx5pe5H1FBPBXPcekzDfwnGOFNOH75gcrAsTiM9XaktOtKRxelRXUwKBgQDI9I10i/UBrezIdo/pz5mjrRSNCRlNVUoMRBnXWeoHqAuAfxxy+TuYVdTelVn8ZgNy7lt+/w9j64XSHMq5S29eJg+FuOKV3HBtiGjxZOz1blEfF3b1pIxNJNAZJPOzpGJNmYQw+rt5s75wXA9n/q/us3HADClMVLFAczKJAt5xkwKBgCHvwvy8TYh8gcZ9NjBdMbm13kg6mUsInDbDlGbYpiO++s8q0M3WgE+8i+hoDo+DXt9/iuanFCsYqZZA851Rt2JfospDKf7QqUqjBG+HTAgDg1IUOCTbKLbuQb3Ojm5EBZTuBeuNLYf/dkFdN9PMT94+EdwojbeTgMH0a2uMEx9rAoGAYW2fv2ezq9LFQBOrhnJuTNq3YgGNUN8O/Y9u7+fZ/UhN+0ilZGDNsfe7Mwc6D5LuDSTfG11R+uHPiaUH7HpUTlMpp22R/ZJYt+Iw7wg9kmifz/EybboPg79bXTV7KheCyZiqbIzDpCevJw6bMZJbfeFmPvQmeal+Hn87ew33Bx0CgYEAkLJ/KSeRbco/jvgzg7G5PHaZbRgrXVmBpfHCsXt/bAqEb0ea2hAG/tJ8yw+8zGG0R8WORZoojWqKfDTjm+0bR+YQxa3HJ/1El9A9jixPgpS+yf9dwBRyCNr2hTepzoaCWAZy5Cialn5FPfSHPJHkNaHPjtxWPihb1s0BzZb3I1s=");  
			ExtendParams extendParams = new ExtendParams();
			extendParams.setSysServiceProviderId(userId + "|" + bank_id);	//拓展参数
			
			//实例化客户端
			AlipayClient alipayClient = new DefaultAlipayClient(alipay_Url, APP_ID, APP_PRIVATE_KEY, "JSON", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2");
			//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
			AlipayTradePayRequest request = new AlipayTradePayRequest();
			//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
			AlipayTradePayModel model = new AlipayTradePayModel();
			String orderNo = OrderGeneratedUtils.getOrderNo();
			model.setOutTradeNo(orderNo);
			model.setScene("bar_code");
			model.setAuthCode("2017102686056359");
			model.setSubject("循心科技");
			model.setTotalAmount(amount);
			model.setBody("循心科技");
			model.setTimeoutExpress("30m");
			model.setExtendParams(extendParams);
			request.setBizModel(model);
			request.setNotifyUrl(notify_url);
	        //这里和普通的接口调用不同，使用的是sdkExecute
	        AlipayTradePayResponse response = alipayClient.sdkExecute(request);
	        System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
	        //生成账单
	        boolean result = rechargeRecordService.generatedBills(orderNo,extendParams);
	        if(!result) {
	        	log.error("订单生成失败");
	        }
			log.info("infoMsg:--- 支付宝扫码付结束");
			return reponse.success(response);
		} catch (Exception e) {
			log.info("errorMsg:--- 支付宝扫码付失败");
			return reponse.failure();
		}
	}
	
	
	
	
	
}
