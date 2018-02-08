package com.xunxin.controller.app.open;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunxin.controller.system.BaseController;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.UserInfoDataService;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.vo.user.UserInfoData;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月20日 -- 下午1:22:20
 * @Version 1.0
 * @Description		第三方登录
 */
@Controller
@RequestMapping(value=Router.PATH)
public class XOpenController extends BaseController{

	private static final Logger log = Logger.getLogger(XOpenController.class);
	
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private UserInfoDataService userInfoDataService;
	
	
	/**
	 * 第三方登录
	 * 
	 * @param openId
	 * @param openType
	 * @return
	 */
	@RequestMapping(value=Router.OPEN_LOGIN,method=RequestMethod.POST)
	@ResponseBody
	public Response open_login(@RequestParam("openId") String openId,@RequestParam("openType") String openType) {
		log.info("infoMsg:---  第三方登录开始");
		Response reponse = this.getReponse();
		PageData pd = new PageData<>();
		int result = 0;
		try {
			if(StringUtils.trim(openId).equals("") || StringUtils.isBlank(openType)) {
				return reponse.failure("Getting user openId failed");
			}
			UserInfoData info = userInfoDataService.open_login(openId,openType);
			if(info == null) {
				result = UserEntity.NORMAL;
				pd.put("state", result);
				pd.put("info", "未绑定");
			}else {
				result = UserEntity.UNUSUAL;
				UserEntity user = appUserService.findById(info.getUserId());
				pd.put("state", result);
				pd.put("info", "已绑定");
				pd.put("uid", user.getUid());
				pd.put("phone", user.getPhone());
				if(user.getGender() == null) {
					pd.put("gender", "");
				}else {
					pd.put("gender", user.getGender());
				}
				pd.put("isCompleteBasic", user.getIsCompleteBasic());
			}
			log.info("infoMsg:---  第三方登录结束");
			return reponse.success(pd);
		} catch (Exception e) {
			log.error("errorMsg:--- 第三方登录失败." + e.getMessage());
			return reponse.failure(e.getMessage());
		}
		
	}

	
	/**
	 * 手机号绑定
	 * 
	 * @param openId
	 * @param openType
	 * @param phone
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping(value=Router.BIND_USER_PHONE,method=RequestMethod.POST)
	@ResponseBody
	public Response bind_user_phone(@RequestParam("openId") String openId,@RequestParam("openType") String openType,
			@RequestParam("phone") String phone,@RequestParam("verifyCode") int verifyCode) {
		log.info("infoMsg:--- 手机号绑定开始");
		Response reponse = this.getReponse();
		PageData pd = new PageData<>();
		try {
			UserEntity entity = appUserService.findOne(phone);
			if(null == entity) {
				boolean result = appUserService.register(phone, verifyCode, "xunxin");
				if(result) {
					UserEntity user = appUserService.findOne(phone);
					userInfoDataService.bind_open_account(user.getUid(),openId,openType);
					pd.put("uid", entity.getUid());
					pd.put("phone", entity.getPhone());
					if(entity.getGender() == null) {
						pd.put("gender", "");
					}else {
						pd.put("gender", entity.getGender());
					}
					pd.put("isCompleteBasic", entity.getIsCompleteBasic());
				}
			}else {
				userInfoDataService.bind_open_account(entity.getUid(),openId,openType);
				pd.put("uid", entity.getUid());
				pd.put("phone", entity.getPhone());
				if(entity.getGender() == null) {
					pd.put("gender", "");
				}else {
					pd.put("gender", entity.getGender());
				}
				pd.put("isCompleteBasic", entity.getIsCompleteBasic());
			}
			log.info("infoMsg:--- 手机号绑定结束");
			return reponse.success(pd);
		} catch (Exception e) {
			log.error("errorMsg:--- 手机号绑定失败" + e.getMessage());
			return reponse.failure(e.getMessage());
		}
	}
	
	/**
	 * 用户绑定第三方账号
	 * 
	 * @param userId
	 * @param openId
	 * @param openType
	 * @return
	 */
	@RequestMapping(value=Router.BIND_OPEN_ACCOUNT,method=RequestMethod.POST)
	@ResponseBody
	public Response bind_open_account(@RequestParam("userId") int userId,
			@RequestParam("openId") String openId,@RequestParam("openType") String openType) {
		log.info("infoMsg:--- 用户绑定第三方账户开始");
		Response reponse = this.getReponse();
		try {
			userInfoDataService.bind_open_account(userId,openId,openType);
			log.info("infoMsg:--- 用户绑定第三方账户结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:--- 用户绑定" + openType + "失败" + e.getMessage());
			return reponse.failure(e.getMessage());
		}
		
		
	}
	
	
	
	
	
	
}
