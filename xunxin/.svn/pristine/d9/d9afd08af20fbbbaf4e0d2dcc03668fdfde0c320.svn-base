package com.xunxin.controller.app.square;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunxin.controller.system.BaseController;
import com.xunxin.service.app.EmpathyCircleService;
import com.xunxin.service.app.square.ThrowHydrangeaRecordService;
import com.xunxin.vo.circle.EmpathyCircle;
import com.xunxin.vo.square.ThrowHydrangeaRecord;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月28日 -- 下午2:42:01
 * @Version 1.0
 * @Description		抛绣球
 */
@Controller
@RequestMapping(value=Router.PATH+Router.Hydrangea.PATH)
public class ThrowHydrangeaController extends BaseController {

	private final static Logger log = Logger.getLogger(ThrowHydrangeaController.class);
	
	@Autowired
	private ThrowHydrangeaRecordService throwHydrangeaRecordService;
	@Autowired
	private EmpathyCircleService empathyCircleService;
	
	
	/**
	 * 抛一个绣球
	 * 
	 * @param userId
	 * @param qaList
	 * @param isSendCircle
	 * @return
	 */
	@RequestMapping(value=Router.Hydrangea.THROW_HYDRANGEA,method=RequestMethod.POST)
	@ResponseBody
	public Response throw_hydrangea(@RequestParam("userId") int userId,
			@RequestParam("qaList") String qaList,@RequestParam("isSendCircle") boolean isSendCircle) {
		log.info("infoMsg:--- 用户抛绣球开始");
		Response reponse = this.getReponse();
		try {
			
			ThrowHydrangeaRecord record = new ThrowHydrangeaRecord();
			record.setQaList(qaList);
			//TODO 信息内容根据配置的QA，打包成一个qa包
			String content = "";
			//转发到共情圈
			if(isSendCircle) {
				EmpathyCircle cicle = new EmpathyCircle();
				cicle.setUserId(userId);
				cicle.setContent(content);
				empathyCircleService.save(cicle);
			}
			record.setSendCircle(isSendCircle);
			record.setUserId(userId);
			record.setContent(content);
			record.setRecordTime(new Date());
			throwHydrangeaRecordService.save(record);
			
			log.info("infoMsg:--- 用户抛绣球结束");
			return reponse.success();
		} catch (Exception e) {
			log.info("errorMsg:{--- 用户抛绣球失败:" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
	}
	
	
}
