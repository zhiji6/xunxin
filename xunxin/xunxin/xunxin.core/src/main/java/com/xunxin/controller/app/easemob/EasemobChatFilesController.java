package com.xunxin.controller.app.easemob;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xunxin.controller.system.BaseController;
import com.xunxin.web.api.bean.Router;
/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月2日 -- 上午10:30:04
 * @Version 1.0
 * @Description	文件上传下载
 */
@Controller
@RequestMapping(value=Router.PATH+Router.Easemob.PATH+Router.Easemob.CHAT_FILES)
public class EasemobChatFilesController extends BaseController{

	private static final Logger log = Logger.getLogger(EasemobChatFilesController.class);
	
}
