package com.xunxin.controller.app.message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.xunxin.dao.app.user.AppUserDao;
import com.xunxin.dao.qa.XunxinUserDeviceTokenMapper;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.MessageReadService;
import com.xunxin.service.app.SysDictService;
import com.xunxin.util.JacksonUtil;
import com.xunxin.util.SpringContextUtil;
import com.xunxin.util.app.push.umpush.PushClient;
import com.xunxin.util.app.push.umpush.ios.IOSUnicast;
import com.xunxin.util.constants.UpushConstants;
import com.xunxin.vo.im.MessageRead;
import com.xunxin.vo.qa.XunxinUserDeviceToken;

public class PassByFriend implements Message {

	private PushClient client = new PushClient();
	@Override
	public void PushMessage(Integer userId, Integer friendId, String empathyCircleId,Integer type,SysDictService sysDictService,XunxinUserDeviceTokenMapper xunxinUserDeviceTokenMapper,AppUserService appUserService) {
		String lable = sysDictService.findDictByValue(type);
		XunxinUserDeviceToken userDeviceToken = xunxinUserDeviceTokenMapper.selectByUserId(userId);
		MessageReadService messageReadService = (MessageReadService) SpringContextUtil.getBean("messageReadService");
		AppUserDao appUserDao = (AppUserDao) SpringContextUtil.getBean("appUserDao");
		String nameByUserId = appUserService.findUserNameByUserId(friendId);
		IOSUnicast unicast;
		try {
			unicast = new IOSUnicast(UpushConstants.APP_appkey,UpushConstants.IOS_appMasterSecret);
			if(userDeviceToken != null && !"".equals(userDeviceToken.getDeviceToken())){
				
				MessageRead messageRead = new MessageRead();
				if( type == null){
						    	 throw new Exception("消息类型不能为空");
				}
			    if(type.equals(1)||type.equals(2)||type.equals(3)||type.equals(4)){
			    	 messageRead.setTag(2);
			    }
			    if(type.equals(5)||type.equals(6)||type.equals(7)||type.equals(8)||type.equals(9)){
			    	 messageRead.setTag(3);
			    }
				messageRead.setCreateTime(new Date());
				messageRead.setUserId(userId);
				messageRead.setIsRead(0);
				messageRead.setUpdateTime(new Date());
				messageRead.setIsDelete(false);
				messageRead.setContentId(empathyCircleId);
				messageRead.setTitle(lable);;
				messageRead.setType(type);
				messageReadService.insert(messageRead);
				
				unicast.setDeviceToken(userDeviceToken.getDeviceToken());
				unicast.setAlert(lable);
				unicast.setBadge( 0);
				unicast.setSound( "default");
				unicast.setProductionMode();
				Map<String, Object> map = appUserDao.queryMatchMessage(friendId);
				Map<String, Object> mapUser=new HashMap<String, Object>();
				map.put("tag", "passBy");
				map.put("messageType", "0");
				map.put("info", mapUser);
				System.out.println(mapUser);
				Date date = new Date();  
		        String tsStr = "";   
		        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		            tsStr = sdf.format(date); 
				map.put("date", tsStr);
				String json = JacksonUtil.Builder().map2Json(map);
				System.out.println(json);
				unicast.setCustomizedField("InteractiveMessage", json);
				client.send(unicast);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}