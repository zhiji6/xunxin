package com.xunxin.dao.app.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xunxin.vo.condition.UserCondition;
import com.xunxin.vo.condition.UserSearchCondition;
import com.xunxin.vo.im.UserMatch;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.UserEntity;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月11日 -- 下午4:55:41
 * @Version 1.0
 * @Description	APP用户监管处
 */
public interface AppUserDao {

	Integer findByPhone(String phone);

	void save(UserEntity entity);

	UserEntity findOne(@Param("phone") String phone);

	Integer getCertificationDegree(String phone);

	void complete_particular_information(PageData pd);

	boolean complete_album_information(String photo);

	void update_pwd(@Param("phone") String phone,@Param("newWord") String newWord);

	Integer is_authentication(Integer userId); //是否实名认证

	UserEntity findById(Integer userId);

	void updateAccount(UserEntity user);

	void complete_basics_information(UserCondition uc);

	UserEntity findUserById(Integer userId);

	String findUserPhoneByUserId(Integer userId);

	void setup_password(@Param("userId") Integer userId, @Param("password") String password);

	List<UserEntity> findAll();

	List<UserEntity> findUserListByAge(@Param("userId")Integer userId, @Param("age")Integer age);

	Integer findUserGradeByUserId(Integer userId);

	String findUserGenderByUserId(Integer userId);

	List<Integer> findAllNotUserId(Integer userId);

	List<UserMatch> findUserListByUser(List<Integer> userIdList);

	void edit_user_information(PageData pd);

	Integer findCount();

	void user_exp_change(@Param("userId") Integer userId, @Param("userExp") Integer userExp);

	//资料完成度
    void completeness(@Param("userId") Integer userId,@Param("completeness") Integer completeness);

    //金额变动
    void user_amount_change(@Param("userId")Integer userId, @Param("amount")double amount);
    
    List<UserEntity> getUserList(UserSearchCondition condition);

	void updateIsLogin(@Param("userId")Integer userId, @Param("isLogin") Integer isLogin);

	Map<String, Object> queryMatchMessage(@Param("anotherUserId")Integer anotherUserId);

    void user_block(@Param("userId") Integer userId, @Param("ishiden") Integer ishiden);
    //获取用户积分
	Integer queryUserExp(Integer userId);
	//消息设置
	void messageSetting(@Param("userId")Integer userId, @Param("setting")String setting, @Param("isDelete")Integer isDelete);
	//获取自画像设置
	Integer querySelfPortraitUserId(Integer anotherId);
	//更新用户等级
	void updateGrade(@Param("userId") Integer userId, @Param("grade")Integer grade);
	//获取用户金额
	Double queryUserAmount(Integer userId);

    void setEmail(@Param("userId") Integer userId,@Param("email") String email);

    void update_user_certNo(@Param("userId") Integer userId,@Param("certNo") String certNo);
  //获取用户消息设置
	Integer querymessage(Integer friendId);

	//重置用户状态
    void update_persoanl_status(PageData pd);

	
	
}
