package com.xunxin.controller.app.message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sun.jmx.snmp.Timestamp;
import com.xunxin.dao.qa.XunxinUserDeviceTokenMapper;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.SysDictService;
import com.xunxin.util.JacksonUtil;
import com.xunxin.util.app.push.umpush.PushClient;
import com.xunxin.util.app.push.umpush.ios.IOSUnicast;
import com.xunxin.util.constants.UpushConstants;
import com.xunxin.vo.qa.XunxinUserDeviceToken;

public class GiveUpMessage implements Message {

	private PushClient client = new PushClient();
	@Override
	public void PushMessage(Integer userId, Integer friendId, String dynamicId,Integer type,SysDictService sysDictService,XunxinUserDeviceTokenMapper xunxinUserDeviceTokenMapper,AppUserService appUserService) {
		String lable = sysDictService.findDictByValue(type);
		XunxinUserDeviceToken userDeviceToken = xunxinUserDeviceTokenMapper.selectByUserId(userId);
		String nameByUserId = appUserService.findUserNameByUserId(friendId);
		IOSUnicast unicast;
		try {
			unicast = new IOSUnicast(UpushConstants.APP_appkey,UpushConstants.IOS_appMasterSecret);
			if(userDeviceToken != null && !"".equals(userDeviceToken.getDeviceToken())){
				unicast.setDeviceToken(userDeviceToken.getDeviceToken());
				unicast.setAlert(lable);
				unicast.setBadge( 0);
				unicast.setSound( "default");
				unicast.setTestMode();
				
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("tag", "InteractiveMessage");
				map.put("messageType", "5");
				map.put("OthersId", friendId);
				map.put("OthersName", nameByUserId);
				map.put("dynamicId", dynamicId);
				Date date = new Date();  
		        String tsStr = "";   
		        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		            tsStr = sdf.format(date); 
				map.put("date", tsStr);
				String json = JacksonUtil.Builder().map2Json(map);
				unicast.setCustomizedField("InteractiveMessage", json);
				client.send(unicast);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
