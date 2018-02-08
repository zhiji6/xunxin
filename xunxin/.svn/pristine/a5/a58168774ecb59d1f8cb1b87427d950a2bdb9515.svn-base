package com.xunxin.controller.app.easemob;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.xunxin.config.RedisRepository;
import com.xunxin.controller.system.BaseController;
import com.xunxin.service.PlatformMutualityManagentService;
import com.xunxin.util.APIHttpClient;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.sys.PlatformMutualityManagent;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;

import redis.clients.jedis.Jedis;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月24日 -- 下午5:31:38
 * @Version 1.0
 * @Description 获取环信SDK的返回信息
 */
@Controller
@RequestMapping(value=Router.PATH+Router.Easemob.PATH)
public class EasemobController extends BaseController{

	private static final Logger log = Logger.getLogger(EasemobController.class);
	
	@Autowired
	private PlatformMutualityManagentService platformMutualityManagentService; 
	
	/**
	 * 获取环信 Token
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Easemob.GET_TOKEN,method=RequestMethod.POST)
	@ResponseBody
	public Response getToken() {
		log.info("info-: get huanxin token begin");	
		Response res = this.getReponse();
		try {
			PlatformMutualityManagent pmBean = platformMutualityManagentService.findOne(1);
			Assert.notNull(pmBean);
			String linked_url = pmBean.getLinked_url();		//环信API host
			String org_name = pmBean.getOrg_name();			
			String app_name = pmBean.getApp_name();	
			String client_id = pmBean.getClient_id();
			String client_secret = pmBean.getClient_secret();
			
			String reqUrl = linked_url + "/" + org_name + "/" + app_name + "/" + "token"; 	//环信 Token path
			APIHttpClient client = new APIHttpClient(reqUrl);
			JsonObject j = new JsonObject();  
			j.addProperty("grant_type", "client_credentials");  
			j.addProperty("client_id", client_id);  
			j.addProperty("client_secret", client_secret);  
			
			HttpResponse response = client.post(j.toString());
			String return_obj = EntityUtils.toString(response.getEntity());
			Jedis jedis = RedisRepository.getJedis();
			if(jedis.exists("easemob_token")) {
				jedis.del("easemob_token");
				jedis.set("easemob_token", return_obj);
			}else {
				jedis.set("easemob_token", return_obj);
			}
			int statusCode = response.getStatusLine().getStatusCode(); 
			
			PageData easemob_token = new PageData<Object, Object>();
			if(statusCode == 200) {
				JSONObject register_obj = JSON.parseObject(return_obj);
				String access_token = register_obj.getString("access_token");	//环信 token
				String expires_in = register_obj.getString("expires_in");		//环信 token 有效时间
				String application = register_obj.getString("application");		//环信 token 应用程序
				
				//获得返回值 并解析
				easemob_token.put("access_token", access_token);
				easemob_token.put("expires_in", expires_in);
				easemob_token.put("application", application);
			}
			log.info("infoMsg:- get token success, ok");
			return res.success(easemob_token);
		} catch (Exception e) {
			log.error("errorMsg:- An error occurred when get token!" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	
	
	
}
