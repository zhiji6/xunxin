package com.xunxin.util.app.chat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xunxin.util.app.server.example.api.impl.EasemobChatMessage;




/**
 * Created by easemob on 2017/3/22.
 */
public class ChatMessagesTest {
    private EasemobChatMessage easemobChatMessage = new EasemobChatMessage();
    private static final Logger logger = LoggerFactory.getLogger(ChatMessagesTest.class);
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHH");

    @Test
    public void getChatMessagesDownloadUrl() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -3);
        String timeStr = SDF.format(calendar.getTime());
//        Object result = easemobChatMessage.exportChatMessages(timeStr);
        Object result = easemobChatMessage.exportChatMessages(timeStr);
        System.out.println(result);
        if (null == result) {
            logger.error("Failed to get expected response by calling GET chatmessages API, maybe there is no chatmessages history at {}", timeStr);
            logger.info("\n**************************\nFor successful response example:"
                    + "\ncurl -H \"Authorization:Bearer xxxxxx\" -XGET http://a1.easemob.com/easemob-demo/chatdemoui/chatmessages/2017072717"
                    + "\n{\n" + "    \"action\": \"get\",\n" + "    \"application\": \"4d7e4ba0-dc4a-11e3-90d5-e1ffbaacdaf5\",\n"
                    + "    \"uri\": \"http://a1.easemob.com/easemob-demo/chatdemoui/chatmessages/2017072717\",\n" + "    \"data\": [\n"
                    + "        {\n"
                    + "            \"url\": \"http://ebs-chatmessage-a1.easemob.com/history/14D/easemob-demo/chatdemoui/2017072717.gz?Expires=1501155823&OSSAccessKeyId=LTAIlKPZStPokdA8&Signature=4iksrdotTr1Y6KVu8zVHPy6MOxw%3D\"\n"
                    + "        }\n" + "    ],\n" + "    \"timestamp\": 1501154023457,\n" + "    \"duration\": 0,\n"
                    + "    \"organization\": \"easemob-demo\",\n" + "    \"applicationName\": \"chatdemoui\"\n" + "}"
                    + "\n**************************");
        } else {
            logger.info(result.toString());
        }
    }
    @SuppressWarnings("unused")
	public String returnUrl() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -2);
        String timeStr = SDF.format(calendar.getTime());
//        Object result = easemobChatMessage.exportChatMessages(timeStr);
        Object result = easemobChatMessage.exportChatMessages(timeStr);
        System.out.println(result);
        logger.info("下载消息环信返回信息"+result);
        if (null == result) {
            return null;
        } else {
        	logger.info("环信返回信息成功"+result.toString());
            JSONObject obj = JSONObject.parseObject(result.toString());
            JSONArray jsonArray = obj.getJSONArray("data");
            Object object = jsonArray.get(0);
            JSONObject objreturn = JSONObject.parseObject(object.toString());
            String string = objreturn.getString("url");
            logger.info("下载消息环信返回信息"+string);
            return string;
            		
        }
    }
}
