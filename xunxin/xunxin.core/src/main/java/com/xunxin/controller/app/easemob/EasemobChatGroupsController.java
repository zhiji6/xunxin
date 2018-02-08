package com.xunxin.controller.app.easemob;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xunxin.controller.system.BaseController;
import com.xunxin.web.api.bean.Router;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月2日 -- 上午10:34:25
 * @Version 1.0
 * @Description	群组管理
 */
@Controller
@RequestMapping(value=Router.PATH+Router.Easemob.PATH+Router.Easemob.CHAT_GROUPS)
public class EasemobChatGroupsController extends BaseController{

	private static final Logger log = Logger.getLogger(EasemobChatGroupsController.class);
	
}
