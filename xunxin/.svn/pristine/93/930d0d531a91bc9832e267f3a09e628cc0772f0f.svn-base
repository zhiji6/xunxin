package com.xunxin.controller.app.user;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xunxin.controller.system.BaseController;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.PersonalAuthenticationService;
import com.xunxin.service.app.user.UserAuthenticationService;
import com.xunxin.util.PeriodsUtil;
import com.xunxin.util.app.OrderGeneratedUtils;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.UserAuthentication;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;

/**
 * 
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月30日 -- 下午5:07:17
 * @Version 1.0
 * @Description	个人资料认证
 */
@Controller
@RequestMapping(value=Router.PATH+Router.Personal.PATH)
public class PersonalAuthenticationController extends BaseController{

	private static final Logger log = Logger.getLogger(PersonalAuthenticationController.class);
	
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private PersonalAuthenticationService personalAuthenticationService;
	@Autowired
	private UserAuthenticationService userAuthenticationService;
	
	/**
	 * 是否实名认证
	 * 
	 * @param id
	 * @param certNo
	 * @return
	 */
	@RequestMapping(value=Router.Personal.IS_AUTHENTICATION,method=RequestMethod.POST)
	@ResponseBody
	public Response is_authentication(@RequestParam("userId") int userId) {
		log.info("InfoMsg:--- is_authentication start");
		Response response = this.getReponse();
		try {
//			int isAuthentication = appUserService.is_authentication(userId);
			UserAuthentication isAuthentication = userAuthenticationService.model(userId,"cert");
			log.info("InfoMsg:--- is_authentication end");
			return response.success(isAuthentication);
		} catch (Exception e) {
			log.error("errorMsg:--- is_authentication occur error " + e.getMessage());
			return response.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 手机认证
	 * 
	 * @param phone
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping(value=Router.Personal.PHONE_AUTHENTICATION,method=RequestMethod.POST)
	@ResponseBody
	public Response phone_authentication(@RequestParam("userId") int userId,@RequestParam("phone") String phone
			,@RequestParam("authType") String authType) {
		log.info("InfoMsg:--- phone_authentication start");
		Response response = this.getReponse();
		try {
			PageData pd = new PageData<>();
			pd.put("userId", userId);
			pd.put("authType", "cert");
			if(!userAuthenticationService.isAuthentication(pd)) {
				return response.failure("请先进行实名认证");
			}
			String message = personalAuthenticationService.phone_authentication(userId,phone,authType);
			if(message.equals("匹配")) {
				log.info("InfoMsg:--- phone_authentication end");
				return response.success(message);
			}else {
				log.info("InfoMsg:--- phone_authentication end");
				return response.failure(message);
			}
		} catch (Exception e) {
			log.error("errorMsg:--- phone_authentication occur error " + e.getMessage());
			return response.failure(e.getMessage());
		}
	}
	
	/**
	 * 学历认证
	 * 
	 * @param phone
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping(value=Router.Personal.EDUCATION_AUTHENTICATION,method=RequestMethod.POST)
	@ResponseBody
	public Response education_authentication(MultipartHttpServletRequest request,@RequestParam("usreId") int userId
			,@RequestParam("certNumber") String certNumber,@RequestParam("authType") String authType) {
		log.info("InfoMsg:--- education_authentication start");
		Response response = this.getReponse();
		BufferedInputStream is = null;  
		BufferedOutputStream out = null;  // 准备好一个输出的对象
		try {
			PageData pd = new PageData<>();
			pd.put("userId", userId);
			pd.put("authType", "cert");
			if(!userAuthenticationService.isAuthentication(pd)) {
				return response.failure("请先进行实名认证");
			}
			MultipartFile str =  request.getFile("file");
			String[] fileExts = {"jpg", "png"};
			String fileName = str.getOriginalFilename();   //原文件名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			if (Arrays.binarySearch(fileExts, fileExt) != -1) {
		    	 InputStream inputStream = str.getInputStream();
		    	 is = new BufferedInputStream(inputStream);
		    	 String url = "D:\\programmeTools\\nginx-1.12.1\\photo\\auth" + "/" + str + OrderGeneratedUtils.getOrderNo()  + "." + fileExt;
		    	 File newFile = new File(url);
	        	 if(is != null) {
	        		 out = new BufferedOutputStream(new FileOutputStream(newFile));  
	        		 byte[] buffer = new byte[1024];  
	        		 int len = -1;  
	        		 while ((len = is.read(buffer)) != -1) {  
	        			 out.write(buffer, 0, len);  
	        		 }
	        		 String name = str + OrderGeneratedUtils.getOrderNo() + "." + fileExt;	//新文件名
	        		 String nickName = appUserService.findUserNameByUserId(userId);
	        		 String authRemark = "用户:" + nickName + "于:---" + PeriodsUtil.getWholeTime(new Date()) + "进行了--" + authType + "认证";
	        		 UserAuthentication auth = new UserAuthentication(certNumber, authType, UserEntity.UNUSUAL, name, authRemark, new Date(), userId);
	        		 userAuthenticationService.save(auth);
	        	 }
	         }else {
				return response.failure("该文件类型不能够上传");
	         }
			log.info("InfoMsg:--- education_authentication end");
			return response.success();
		} catch (Exception e) {
			log.error("errorMsg:--- education_authentication occur error " + e.getMessage());
			return response.failure(e.getMessage());
		}finally {
			if(is != null) {  
                try {  
                    is.close();  
                }catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
            if(out != null) {  
                try {  
                    out.close();  
                }catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
		}
		
	}
	
	/**
	 * 邮箱认证
	 * 
	 * @param phone
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping(value=Router.Personal.MAIL_AUTHENTICATION,method=RequestMethod.POST)
	@ResponseBody
	public Response mail_authentication(@RequestParam("userId") int userId,@RequestParam("mail") String mail,
			@RequestParam("authType") String authType) {
		log.info("InfoMsg:--- mail_authentication start");
		Response response = this.getReponse();
		try {
			PageData pd = new PageData<>();
			pd.put("userId", userId);
			pd.put("authType", "cert");
			if(!userAuthenticationService.isAuthentication(pd)) {
				return response.failure("请先进行实名认证");
			}
			boolean authention = personalAuthenticationService.mail_authentication(userId,mail,authType);
			log.info("InfoMsg:--- mail_authentication end");
			return response.success();
		} catch (Exception e) {
			log.error("errorMsg:--- mail_authentication occur error " + e.getMessage());
			return response.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 实名认证
	 * 
	 * @param phone
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping(value=Router.Personal.CERTIFICATION_AUTHENTICATION,method=RequestMethod.POST)
	@ResponseBody
	public Response certification_authentication(@RequestParam(value="userId") int userId,@RequestParam(value="authType") String authType,
			@RequestParam(value="certNo") String certNo) {
		log.info("InfoMsg:--- certification_authentication start");
		Response response = this.getReponse();
		try {
			boolean authention = personalAuthenticationService.certification_authentication(userId,certNo,authType);
			log.info("InfoMsg:--- certification_authentication end");
			return response.success(authention);
		} catch (Exception e) {
			log.error("errorMsg:--- certification_authentication occur error " + e.getMessage());
			return response.failure(e.getMessage());
		}
	}
	
	
	/**s
	 * 职业认证
	 * 
	 * @param phone
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping(value=Router.Personal.PROFESSION_AUTHENTICATION,method=RequestMethod.POST)
	@ResponseBody
	public Response profession_authentication(MultipartHttpServletRequest request,@RequestParam(value="userId") int userId,
			@RequestParam(value="authType") String authType) {
		log.info("InfoMsg:--- profession_authentication start");
		Response response = this.getReponse();
		BufferedInputStream is = null;  
		BufferedOutputStream out = null;  // 准备好一个输出的对象
		try {
			PageData pd = new PageData<>();
			pd.put("userId", userId);
			pd.put("authType", "cert");
			if(!userAuthenticationService.isAuthentication(pd)) {
				return response.failure("请先进行实名认证");
			}
			MultipartFile str =  request.getFile("file");
			String[] fileExts = {"jpg", "png"};
			String fileName = str.getOriginalFilename();   //原文件名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			if (Arrays.binarySearch(fileExts, fileExt) != -1) {
		    	 InputStream inputStream = str.getInputStream();
		    	 is = new BufferedInputStream(inputStream);
		    	 String url = "D:\\programmeTools\\nginx-1.12.1\\photo\\auth" + "/" + str + OrderGeneratedUtils.getOrderNo()  + "." + fileExt;
		    	 File newFile = new File(url);
	        	 if(is != null) {
	        		 out = new BufferedOutputStream(new FileOutputStream(newFile));  
	        		 byte[] buffer = new byte[1024];  
	        		 int len = -1;  
	        		 while ((len = is.read(buffer)) != -1) {  
	        			 out.write(buffer, 0, len);  
	        		 }
	        		 String name = str + OrderGeneratedUtils.getOrderNo() + "." + fileExt;	//新文件名
	        		 String nickName = appUserService.findUserNameByUserId(userId);
	        		 String authRemark = "用户:" + nickName + "于:---" + PeriodsUtil.getWholeTime(new Date()) + "进行了--" + authType + "认证";
	        		 UserAuthentication auth = new UserAuthentication("", authType, UserEntity.UNUSUAL, name, authRemark, new Date(), userId);
	        		 userAuthenticationService.save(auth);
	        	 }
	         }else {
				return response.failure("该文件类型不能够上传");
	         }
			log.info("InfoMsg:--- profession_authentication end");
			return response.success();
		} catch (Exception e) {
			log.error("errorMsg:--- profession_authentication occur error " + e.getMessage());
			return response.failure(e.getMessage());
		}finally {
			if(is != null) {  
                try {  
                    is.close();  
                }catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
            if(out != null) {  
                try {  
                    out.close();  
                }catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
		}
	}
	
	/**
	 * 芝麻分认证
	 * 
	 * @param phone
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping(value=Router.Personal.SESAME_AUTHENTICATION,method=RequestMethod.POST)
	@ResponseBody
	public Response sesame_authentication(@RequestParam(value="userId") int userId,@RequestParam("authType") String authType,
			@RequestParam(value="phone") String phone,@RequestParam(value="verifyCode") String verifyCode) {
		log.info("InfoMsg:--- sesame_authentication start");
		Response response = this.getReponse();
		try {
			PageData pd = new PageData<>();
			pd.put("userId", userId);
			pd.put("authType", "cert");
			if(!userAuthenticationService.isAuthentication(pd)) {
				return response.failure("请先进行实名认证");
			}
			log.info("InfoMsg:--- sesame_authentication end");
			return response.success();
		} catch (Exception e) {
			log.error("errorMsg:--- sesame_authentication occur error " + e.getMessage());
			return response.failure(e.getMessage());
		}
	}
	
	
}
