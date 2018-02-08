package com.xunxin.controller.app.easemob;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xunxin.config.RedisRepository;
import com.xunxin.controller.system.BaseController;
import com.xunxin.service.PlatformMutualityManagentService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.util.APIHttpClient;
import com.xunxin.vo.sys.PlatformMutualityManagent;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;

/**
 * 
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月2日 -- 上午10:23:16
 * @Version 1.0
 * @Description 环信用户体系集成 
 */
@Controller
@RequestMapping(value=Router.PATH+Router.Easemob.PATH+Router.Easemob.USERS)
public class EasemobUsersController extends BaseController{

	private static final Logger log = Logger.getLogger(EasemobUsersController.class);
	
	@Autowired
	private PlatformMutualityManagentService platformMutualityManagentService;
	@Autowired
	private AppUserService appUserService;
	
	
	/**
	 * 删除 IM 用户[单个]
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping(value=Router.Easemob.DELETE_EASEMOB_USER,method=RequestMethod.DELETE)
	@ResponseBody
	public Response delete_easemob_user(String phone) {
		log.info("infoMsg--- 删除 IM 用户 开始");
		Response reponse = this.getReponse();
		try {
			PlatformMutualityManagent pmBean = platformMutualityManagentService.findOne(1);
			Assert.notNull(pmBean);
			String linked_url = pmBean.getLinked_url();		//环信API host
			String org_name = pmBean.getOrg_name();			
			String app_name = pmBean.getApp_name();	
			String req_URL = linked_url + "/" + org_name + "/" + app_name + "/users" + "/" + phone;
			String easemob_token = RedisRepository.getJedis().get("easemob_token");
			APIHttpClient Client = new APIHttpClient(req_URL);
			String user_post = Client.user_post("Authorization", "Bearer" + easemob_token);
			JSONObject return_obj = JSON.parseObject(user_post);
			String entities = return_obj.getString("entities");
			JSONObject user = JSON.parseObject(entities);
			String username = user.getString("username");
			String remark = "环信用户：---" + username + "删除成功！";
			log.info("infoMsg--- 删除 IM 用户 结束");
			return reponse.success(remark);
		} catch (Exception e) {
			log.error("errorMsg:--- 删除 IM 用户 结束" + e.getMessage());
			return reponse.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 删除 IM 用户[单个]
	 * 
	 * @param phone
	 * @return
	  */
	@RequestMapping(value=Router.Easemob.GET_EASEMOB_USER,method=RequestMethod.POST)
	@ResponseBody
	public Response get_easemob_user(String phone) {
		log.info("infoMsg--- 获取 IM 用户 开始");
		Response reponse = this.getReponse();
		try {
			PlatformMutualityManagent pmBean = platformMutualityManagentService.findOne(1);
			Assert.notNull(pmBean);
			String linked_url = pmBean.getLinked_url();		//环信API host
			String org_name = pmBean.getOrg_name();			
			String app_name = pmBean.getApp_name();	
			String req_URL = linked_url + "/" + org_name + "/" + app_name + "/users" + "/" + phone;
			String easemob_token = RedisRepository.getJedis().get("easemob_token");
			APIHttpClient Client = new APIHttpClient(req_URL);
			String user_post = Client.user_post("Authorization", "Bearer" + easemob_token);
			JSONObject return_obj = JSON.parseObject(user_post);
			String entities = return_obj.getString("entities");
			JSONObject user = JSON.parseObject(entities);
			String username = user.getString("username");
			String remark = "环信用户：---" + username + "获取成功！";
			log.info("infoMsg--- 获取 IM 用户 结束");
			return reponse.success(remark);
		} catch (Exception e) {
			log.error("errorMsg:--- 获取 IM 用户 结束" + e.getMessage());
			return reponse.failure(e.getMessage());
		}
	}
	
	/**
	 * 修改 IM 用户昵称
	 * 
	 * @param phone
	 * @param nickname
	 * @return
	 */
/*	@RequestMapping(value=Router.Easemob.UPDATE_USER_NICKNAME,method=RequestMethod.PUT)
	@ResponseBody
	public Response update_user_nickname(@RequestParam("phone") String phone,@RequestParam("nickname") String nickname) {
		log.info("infoMsg:--- 修改 IM 用户昵称开始");
		Response reponse = this.getReponse();
		try {
			PlatformMutualityManagent pmBean = platformMutualityManagentService.findOne(1);
			Assert.notNull(pmBean);
			String linked_url = pmBean.getLinked_url();		//环信API host
			String org_name = pmBean.getOrg_name();			
			String app_name = pmBean.getApp_name();	
			String req_URL = linked_url + "/" + org_name + "/" + app_name + "/users" + "/" + phone;
			String easemob_token = RedisRepository.getJedis().get("easemob_token");
			JsonObject j = new JsonObject();  
			j.addProperty("nickname", nickname);  
			APIHttpClient Client = new APIHttpClient(req_URL);
			HttpResponse user_post = Client.users_post("Authorization", "Bearer" + easemob_token,j.toString());
			String return_obj = EntityUtils.toString(user_post.getEntity());
			JSONObject object = JSON.parseObject(return_obj);
			String entities = object.getString("entities");
			JSONObject result = JSON.parseObject(entities);
			String username = result.getString("username");
			UserEntity entity = appUserService.findOne(phone);
			if(username.equals(entity.getName())) {
				System.out.println("用户:---" + username + ",的昵称修改成功");
			}
			log.info("infoMsg:--- 修改 IM 用户昵称结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:--- 修改 IM 用户昵称失败");
			return reponse.failure(e.getMessage());
		}
		
	}*/
	
	
	
	
}
