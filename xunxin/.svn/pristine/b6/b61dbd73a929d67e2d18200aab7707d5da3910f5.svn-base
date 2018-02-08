package com.xunxin.util;

import com.xunxin.util.app.push.umpush.PushClient;

import com.xunxin.util.app.push.umpush.ios.IOSUnicast;
import com.xunxin.util.constants.UpushConstants;

public class UpushUtils {
	
	//IOS单播推送
	public static void sendIOSUnicast(String appId,String appMasterSecret,String deviceToken,String alert,Integer badge,String sound,String name,String content) throws Exception  {
		
		PushClient client = new PushClient();
		IOSUnicast unicast = new IOSUnicast(appId,appMasterSecret);
		
		unicast.setDeviceToken(deviceToken);
		unicast.setAlert(alert);
		unicast.setBadge(badge);
		unicast.setSound( "default");
		unicast.setTestMode();
		unicast.setCustomizedField(name, content);
		client.send(unicast);
	}
	public static void main(String[] args) throws Exception {
		PushClient client = new PushClient();
		IOSUnicast unicast = new IOSUnicast(UpushConstants.APP_appkey,UpushConstants.IOS_appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken( "xx");
		unicast.setAlert("IOS 单播测试");
		unicast.setBadge( 0);
		unicast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		unicast.setTestMode();
		// Set customized fields
		unicast.setCustomizedField("test", "helloworld");
		client.send(unicast);
	}

}
