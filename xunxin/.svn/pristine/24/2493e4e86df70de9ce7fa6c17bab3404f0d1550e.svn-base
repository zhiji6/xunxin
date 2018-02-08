package com.xunxin.controller.app.message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.xunxin.controller.app.circle.EmpathyCircleController;
import com.xunxin.dao.qa.XunxinUserDeviceTokenMapper;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.SysDictService;
import com.xunxin.service.app.circle.CircleCommentRecordService;
import com.xunxin.util.JacksonUtil;
import com.xunxin.util.SpringContextUtil;
import com.xunxin.util.app.push.umpush.PushClient;
import com.xunxin.util.app.push.umpush.ios.IOSUnicast;
import com.xunxin.util.constants.UpushConstants;
import com.xunxin.vo.qa.XunxinUserDeviceToken;

public class CircleCommentComment implements Message {

	private PushClient client = new PushClient();
	private static final Logger log = Logger.getLogger(CircleCommentComment.class);
	@Override
	public void PushMessage(Integer userId, Integer friendId, String empathyCircleId,Integer type,SysDictService sysDictService,XunxinUserDeviceTokenMapper xunxinUserDeviceTokenMapper,AppUserService appUserService) {
		log.info("infoMsg:--- 评论数量推送开始");
		XunxinUserDeviceToken userDeviceToken = xunxinUserDeviceTokenMapper.selectByUserId(userId);
		CircleCommentRecordService circleCommentRecordService = (CircleCommentRecordService) SpringContextUtil.getBean("circleCommentRecordService");
		IOSUnicast unicast;
		try {
			Integer num =circleCommentRecordService.findNews(userId);
			unicast = new IOSUnicast(UpushConstants.APP_appkey,UpushConstants.IOS_appMasterSecret);
			if(userDeviceToken != null && !"".equals(userDeviceToken.getDeviceToken())){
				log.info("infoMsg:--- "+userDeviceToken);
				unicast.setDeviceToken(userDeviceToken.getDeviceToken());
				unicast.setAlert("");
				unicast.setBadge(0);
				unicast.setSound( "");
				unicast.setContentAvailable(1);
				unicast.setProductionMode();
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("tag", "circleNoti");
				map.put("messageType", "0");
				map.put("messageNum", num+1);
				Date date = new Date();  
		        String tsStr = "";   
		        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		            tsStr = sdf.format(date); 
				map.put("date", tsStr);
				String json = JacksonUtil.Builder().map2Json(map);
				log.info("infoMsg:--- "+json);
				unicast.setCustomizedField("InteractiveMessage", json);
				client.send(unicast);
				log.info("infoMsg:--- 评论数量默认类型推送完成");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}