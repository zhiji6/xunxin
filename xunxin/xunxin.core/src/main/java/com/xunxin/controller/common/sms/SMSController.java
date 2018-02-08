package com.xunxin.controller.common.sms;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.xunxin.service.app.MessageService;
import com.xunxin.util.VerifyCodeUtil;
import com.xunxin.vo.sys.Message;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Route;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年9月30日 -- 下午7:56:21
 * @Version 1.0
 * @Description  阿里云短信sdk接口
 */
@Controller
@RequestMapping(Route.PATH+Route.Common.PATH)
public class SMSController {
	
	private static final Logger log = Logger.getLogger(SMSController.class);	
	
	@Autowired
	private MessageService messageService;
	
	/**
	 * 用户注册验证接口
	 * @throws Exception 
	 */
	@RequestMapping(value=Route.Common.SEND_REGISTER_SMS,method=RequestMethod.POST)
	@ResponseBody
    public Response send_register_message(String phone) {
		log.info("infoMsg:--- 用户注册短信验证码发送开始");
		try {
		    //设置超时时间-可自行调整
		    System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		    System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		    //初始化ascClient需要的几个参数
		    final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
		    final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
		    //替换成你的AK
		    final String accessKeyId = "LTAIy7v8haubbBFP";//你的accessKeyId,参考本文档步骤2
		    final String accessKeySecret = "nbeERYIbL3faVzThLZOjoRHtkbT5D7";//你的accessKeySecret，参考本文档步骤2
		    //初始化ascClient,暂时不支持多region（请勿修改）
		    IClientProfile profile = DefaultProfile.getProfile("cn-beijing", accessKeyId,
		    accessKeySecret);
		    DefaultProfile.addEndpoint("cn-beijing", "cn-beijing", product, domain);
		    IAcsClient acsClient = new DefaultAcsClient(profile);
		    Integer math=(int)(Math.random()*9000)+1000;
		     //组装请求对象
		     SendSmsRequest request = new SendSmsRequest();
		     //使用post提交
		     request.setMethod(MethodType.POST);
		     //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		     request.setPhoneNumbers(phone);
		     //必填:短信签名-可在短信控制台中找到
		     request.setSignName("循心");
		     //必填:短信模板-可在短信控制台中找到
		     request.setTemplateCode("SMS_109395229");
		     //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		     //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		     request.setTemplateParam("{\"code\":\""+math+"\"}");
		     //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
		     //request.setSmsUpExtendCode("90997");
		     //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		     request.setOutId("90997");
		    //请求失败这里会抛ClientException异常
		    SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		    if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
		    	//请求成功
	    		System.out.println("请求成功");
		    }
			
			/** 将生成的验证码存入MongoDB */
			Message mes = new Message();
			mes.setCode(math);
			mes.setPhone(phone);
			mes.setType(0);
			messageService.save(mes);
			/** 存库任务结束  */
			log.info("infoMsg:--- 用户注册短信验证码发送结束");
			return new Response().success(mes);
		} catch (Exception e) {
			log.error("send msgcode error :{}" + e.getMessage(),e);
			return new Response().failure("errorMsg:--- 用户注册短信验证码发送失败" + e.getMessage());
		}
    }
	
	/**
	 * 用户找回密码验证接口
	 * @throws Exception 
	 */
	@RequestMapping(value=Route.Common.SEND_FORGOT_SMS,method=RequestMethod.POST)
	@ResponseBody
    public Response send_forgot_message(String phone) {
		log.info("ifoMsg:--- 用户找回密码短信验证码发送开始");
		try {
		    //设置超时时间-可自行调整
		    System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		    System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		    //初始化ascClient需要的几个参数
		    final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
		    final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
		    //替换成你的AK
		    final String accessKeyId = "LTAIy7v8haubbBFP";//你的accessKeyId,参考本文档步骤2
		    final String accessKeySecret = "nbeERYIbL3faVzThLZOjoRHtkbT5D7";//你的accessKeySecret，参考本文档步骤2
		    //初始化ascClient,暂时不支持多region（请勿修改）
		    IClientProfile profile = DefaultProfile.getProfile("cn-beijing", accessKeyId,
		    accessKeySecret);
		    DefaultProfile.addEndpoint("cn-beijing", "cn-beijing", product, domain);
		    IAcsClient acsClient = new DefaultAcsClient(profile);
		    Integer math=(int)(Math.random()*9000)+1000;
		     //组装请求对象
		     SendSmsRequest request = new SendSmsRequest();
		     //使用post提交
		     request.setMethod(MethodType.POST);
		     //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		     request.setPhoneNumbers(phone);
		     //必填:短信签名-可在短信控制台中找到
		     request.setSignName("循心");
		     //必填:短信模板-可在短信控制台中找到
		     request.setTemplateCode("SMS_109430187");
		     //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		     //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		     request.setTemplateParam("{\"code\":\""+math+"\"}");
		     //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
		     //request.setSmsUpExtendCode("90997");
		     //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		     request.setOutId("90997");
		    //请求失败这里会抛ClientException异常
		    SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		    if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
		    	//请求成功
	    		System.out.println("请求成功");
		    }
			
			/** 将生成的验证码存入MongoDB */
			Message mes = new Message();
			mes.setCode(math);
			mes.setPhone(phone);
			mes.setType(1);
			messageService.save(mes);
			/** 存库任务结束  */
			log.info("ifoMsg:--- 用户找回密码短信验证码发送结束");
			return new Response().success(mes);
		} catch (Exception e) {
			log.error("send msgcode error :{}" + e.getMessage(),e);
			return new Response().failure("用户找回密码短信验证码发送失败" + e.getMessage());
		}
    }
	
	
	/**
	 * send login-security-code
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value=Route.Common.LOGIN_SECURITY_CODE,method=RequestMethod.GET)
	@ResponseBody
	public Response sendVerifyCode(HttpServletRequest request,HttpServletResponse  response) {
		log.info("web验证码制作开始");
		try {
			// 通知浏览器不要缓存  
			response.setHeader("Expires", "-1");  
			response.setHeader("Cache-Control", "no-cache");  
			response.setHeader("Pragma", "-1");  
			VerifyCodeUtil util = VerifyCodeUtil.Instance();  
			// 将验证码输入到session中，用来验证  
			String code = util.getString();  
			request.getSession().setAttribute("code", code);  
			// 输出到web页面  
			ImageIO.write(util.getImage(), "jpg", response.getOutputStream());  
			log.info("web验证码制作完成");
			return new Response().success();
		} catch (Exception e) {
			log.error("web验证码制作失败",e);
		}
		return new Response().failure();
	}
	
	
	
}