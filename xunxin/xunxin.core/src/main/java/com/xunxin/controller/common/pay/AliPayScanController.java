package com.xunxin.controller.common.pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.domain.ExtendParams;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.xunxin.constants.ExpConstants;
import com.xunxin.controller.system.BaseController;
import com.xunxin.service.ThirdPayService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.RechargeRecordService;
import com.xunxin.service.app.user.UserAmountChangeRecordService;
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
 * @Compile 2017年11月23日 -- 下午3:07:51
 * @Version 1.0
 * @Description		支付宝扫码付
 */
@Controller
@RequestMapping(value=Route.PATH+Route.Payment.PATH)
public class AliPayScanController extends BaseController {
	
    private static final Logger log = Logger.getLogger(AliPayScanController.class);
	private static final String alipay_Url = "https://openapi.alipay.com/gateway.do";
	private static final String APP_RECEIPT = "https://sandbox.itunes.apple.com/verifyReceipt";        //苹果的测试服
//	private static final String APP_STORE_RECEIPT = "https://buy.itunes.apple.com/verifyReceipt";        //苹果的生产服
	
	
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private ThirdPayService thirdPayService;
	@Autowired
	private RechargeRecordService rechargeRecordService;
	@Autowired
	private UserAmountChangeRecordService userAmountChangeRecordService;
	
	/**
	 * 验证IOS内购订单
	 * 
	 * @param productIdentifier    产品标识符
	 * @param state                交易状态    Purchased 购买成功 | Restored 恢复购买 | Failed 失败 | Deferred 等待确认，儿童模式需要询问家长同意
	 * @param receipt              二次验证的重要依据,验签
	 * @param transactionIdentifier    交易标识符
	 * @return
	 */
	@RequestMapping(value=Route.Payment.VERIFY_ORDER,method=RequestMethod.POST)
	@ResponseBody
	public Response verify_order(@RequestParam("userId") int userId,@RequestParam("receipt") String receipt) {
	    log.info("infoMsg:--- 验证IOS内购订单开始");
	    Response reponse = this.getReponse();
	    try {
	        UserEntity user = appUserService.findById(userId);
	        String sendPost = sendPost(APP_RECEIPT, "{\"receipt-data\":\"" + receipt + "\"}");
	        System.out.println(sendPost.toString());
	        
	        JSONObject object = JSON.parseObject(sendPost);
	        int status = (int) object.get("status");
	        if(status == 0) {
	            String response = object.getString("receipt");
	            JSONObject obj = JSON.parseObject(response);
	            String in_app = (String) obj.getString("in_app");
	            System.out.println(in_app);
	            JSONArray obj_app = JSON.parseArray(in_app);
	            for(Object str : obj_app) {
	                JSONObject app = JSON.parseObject(str.toString());
	                String product_id = (String) app.getString("product_id");
	                System.out.println(product_id);
	                if(product_id.substring(0,20).equals("com.XXKJ.FollowHeart")) {
	                    //账变
	                    appUserService.user_amount_change(userId, user.getAmount()+Double.parseDouble(product_id.substring(20)));
	                    //充值记录
	                    UserAmountChangeRecord record = new UserAmountChangeRecord(ExpConstants.INCOME, ExpConstants.INCOME, user.getAmount(), Double.parseDouble(product_id.substring(20)), user.getAmount()+Double.parseDouble(product_id.substring(20)), userId);
	                    userAmountChangeRecordService.save(record);
	                }
	            }
	        }
	        log.info("infoMsg:--- 验证IOS内购订单结束");
	        return reponse.success();
        } catch (Exception e) {
            log.error("errorMsg:{--- 验证IOS内购订单结束:" + e.getMessage() + "---}");
            return reponse.failure(e.getMessage());
        }
	}
	
	
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
	
	public static void main(String[] args) {
        String sendPost = "{\"status\":0, \"environment\":\"Sandbox\", \"receipt\":{\"receipt_type\":\"ProductionSandbox\", \"adam_id\":0, \"app_item_id\":0, \"bundle_id\":\"com.XXKJ.FollowHeart\", \"application_version\":\"1.0\", \"download_id\":0, \"version_external_identifier\":0, \"receipt_creation_date\":\"2018-01-17 04:24:28 Etc/GMT\", \"receipt_creation_date_ms\":\"1516163068000\", \"receipt_creation_date_pst\":\"2018-01-16 20:24:28 America/Los_Angeles\", \"request_date\":\"2018-01-17 05:12:25 Etc/GMT\", \"request_date_ms\":\"1516165945359\", \"request_date_pst\":\"2018-01-16 21:12:25 America/Los_Angeles\", \"original_purchase_date\":\"2013-08-01 07:00:00 Etc/GMT\", \"original_purchase_date_ms\":\"1375340400000\", \"original_purchase_date_pst\":\"2013-08-01 00:00:00 America/Los_Angeles\", \"original_application_version\":\"1.0\", \"in_app\":[{\"quantity\":\"1\", \"product_id\":\"com.XXKJ.FollowHeart30\", \"transaction_id\":\"1000000367080299\", \"original_transaction_id\":\"1000000367080299\", \"purchase_date\":\"2018-01-17 04:24:28 Etc/GMT\", \"purchase_date_ms\":\"1516163068000\", \"purchase_date_pst\":\"2018-01-16 20:24:28 America/Los_Angeles\", \"original_purchase_date\":\"2018-01-17 04:24:28 Etc/GMT\", \"original_purchase_date_ms\":\"1516163068000\", \"original_purchase_date_pst\":\"2018-01-16 20:24:28 America/Los_Angeles\", \"is_trial_period\":\"false\"}]}}";
        JSONObject object = JSON.parseObject(sendPost);
        int status = (int) object.get("status");
        if(status == 0) {
            String response = object.getString("receipt");
            JSONObject obj = JSON.parseObject(response);
            String in_app = (String) obj.getString("in_app");
            System.out.println(in_app);
            JSONArray obj_app = JSON.parseArray(in_app);
            for(Object str : obj_app) {
                JSONObject app = JSON.parseObject(str.toString());
                String product_id = (String) app.getString("product_id");
                System.out.println(product_id);
            }
        }
	}
	
    /** 
     * 向指定 URL 发送POST方法的请求 
     *  
     * @param url 
     *            发送请求的 URL 
     * @param param 
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。 
     * @return 所代表远程资源的响应结果 
     */  
    public static String sendPost(String url, String param) {  
        StringBuilder sb = new StringBuilder();  
        PrintWriter out = null;  
        BufferedReader in = null;  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
            out = new PrintWriter(conn.getOutputStream());  
            // 发送请求参数  
            out.print(param);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(  
                    new InputStreamReader(conn.getInputStream()));  
            String line;  
            sb = new StringBuilder();  
            while ((line = in.readLine()) != null) {  
                sb.append(line);  
            }  
        } catch (Exception e) {  
            System.out.println("发送 POST 请求出现异常！"+e);  
            e.printStackTrace();  
        }  
        //使用finally块来关闭输出流、输入流  
        finally{  
            try{  
                if(out!=null){  
                    out.close();  
                }  
                if(in!=null){  
                    in.close();  
                }  
            }  
            catch(IOException ex){  
                ex.printStackTrace();  
            }  
        }  
        return sb.toString();  
    }   
    
}

