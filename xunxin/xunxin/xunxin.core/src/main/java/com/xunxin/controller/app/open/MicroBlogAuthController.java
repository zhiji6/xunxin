package com.xunxin.controller.app.open;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunxin.controller.system.BaseController;
import com.xunxin.service.app.UserInfoDataService;
import com.xunxin.vo.user.UserInfoData;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;

/**
 * 
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月26日 -- 下午6:01:51
 * @Version 1.0
 * @Description	新浪微博第三方登录
 */
@Controller
@RequestMapping(value=Router.PATH)
public class MicroBlogAuthController extends BaseController{

	private static final Logger log = Logger.getLogger(MicroBlogAuthController.class);
	
	@Autowired
	private UserInfoDataService userInfoDataService;
	  
	
}
