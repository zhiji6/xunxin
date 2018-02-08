package com.xunxin.util.app.chat;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xunxin.util.app.server.example.api.impl.EasemobIMUsers;

import io.swagger.client.model.NewPassword;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by easemob on 2017/3/21.
 */
public class UserTest {

    private static final Logger logger = LoggerFactory.getLogger(UserTest.class);
    private static EasemobIMUsers easemobIMUsers = new EasemobIMUsers();

    @Test
    public void createUser() {
        RegisterUsers users = new RegisterUsers();
        User user = new User().username("aaaa123456" + new Random().nextInt(500)).password("123456");
        User user1 = new User().username("aaa123456" + new Random().nextInt(500)).password("123456");
        users.add(user);
        users.add(user1);
        Object result = easemobIMUsers.createNewIMUserSingle(users);
        logger.info(result.toString());
        Assert.assertNotNull(result);
    }

    @Test
    public void getUserByName() {
        String userName = "stringa";
        Object result = easemobIMUsers.getIMUserByUserName(userName);
        logger.info(result.toString());
    }

    @Test
    public void gerUsers() {
        Object result = easemobIMUsers.getIMUsersBatch(5L, null);
        logger.info(result.toString());
    }

    @Test
    public void changePassword() {
        String userName = "stringa";
        NewPassword psd = new NewPassword().newpassword("123");
        Object result = easemobIMUsers.modifyIMUserPasswordWithAdminToken(userName, psd);
        logger.info(result.toString());
    }

    @Test
    public void getFriend() {
        String userName = "stringa";
        Object result = easemobIMUsers.getFriends(userName);
        logger.info(result.toString());
    }
    public static Map  ringRegistration(String uuid) {
        RegisterUsers users = new RegisterUsers();
//        String password = WeiXin.genNonceStr();
        User user = new User().username(uuid).password("254545151");
        users.add(user);
        Object result = easemobIMUsers.createNewIMUserSingle(users);
        System.out.println(result.toString());
        logger.info(result.toString());
//        Assert.assertNotNull(result);
        Map<String,String> map = new HashMap<String,String>();
        JSONObject jsonObject =  JSONObject.fromObject(result);   
        String jstr=jsonObject.getString("entities");  
        JSONObject obj=new JSONArray().fromObject(jstr).getJSONObject(0);  
        String appid_t=obj.getString("uuid");
        map.put("uuid", appid_t);
        map.put("password", "25646454");
		return map;  



	}
    public static void main(String[] args) {
    	RegisterUsers users = new RegisterUsers();
        User user = new User().username("aaasddadaa1ff23456" + new Random().nextInt(500)).password("123456");
        User user1 = new User().username("aaa123456" + new Random().nextInt(500)).password("123456");
        users.add(user);
        users.add(user1);
        Object result = easemobIMUsers.createNewIMUserSingle(users);
        logger.info(result.toString());
        Assert.assertNotNull(result);
	}
   
		
}
