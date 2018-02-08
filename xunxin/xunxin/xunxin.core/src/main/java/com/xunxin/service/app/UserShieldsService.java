package com.xunxin.service.app;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.app.user.AppUserDao;
import com.xunxin.dao.im.UserShieldsMapper;
import com.xunxin.util.app.server.example.api.impl.EasemobIMUsers;
import com.xunxin.vo.im.UserShields;
import com.xunxin.vo.im.UserShieldsExample;
import com.xunxin.vo.im.UserShieldsExample.Criteria;

@Service("userShieldsService")
public class UserShieldsService {

	@Autowired
	private UserShieldsMapper userShieldsMapper;
	@Autowired
	private AppUserDao appUserDao;
	private static final Logger logger = Logger.getLogger(UserShieldsService.class);

	public List<UserShields> findShields(Integer userId) {

		List<UserShields> list = userShieldsMapper.findShieldsByUserId(userId);
		return list;
	}

	public Integer save(Integer userId, Integer anotherId, Integer isdelete) {
		int insert = 0;
		EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
		UserShieldsExample example = new UserShieldsExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andShieldIdEqualTo(anotherId);
		 List<UserShields> list = userShieldsMapper.selectByExample(example);
		 
		if(list != null && list.size()>0){
			UserShields userShields = list.get(0);
			userShields.setIsDelete(isdelete);
			userShields.setUpdatedate(new Date());
			insert = userShieldsMapper.updateByPrimaryKeySelective(userShields);
		}else{
			UserShields userShield = new UserShields();
			userShield.setUserId(userId);
			userShield.setShieldId(anotherId);
			userShield.setIsDelete(isdelete);
			userShield.setCreatedate(new Date());
			userShield.setUpdatedate(new Date());
			insert = userShieldsMapper.insert(userShield);
		}
		
		if(isdelete.equals(0)){
			String phone = appUserDao.findUserPhoneByUserId(userId);
			String phoneShield = appUserDao.findUserPhoneByUserId(anotherId);
			String payloadId = "{'usernames':['"+phoneShield+"']}";
			if(phone != null && phoneShield != null){
				Object addToBlackList = easemobIMUsers.addToBlackList(phone, payloadId);
			}
			
			
		}else if(isdelete.equals(1)){
			String phone = appUserDao.findUserPhoneByUserId(userId);
			String phoneShield = appUserDao.findUserPhoneByUserId(anotherId);
			if(phone != null && phoneShield != null){
			Object addToBlackList = easemobIMUsers.removeFromBlackList(phone, phoneShield);
			}
		}
		return insert;
	}
	
	 public void execute() {  
	        System.out.println("ddddddddddddddddddddddddddddddddd");  
	    }

	public void deleteShield(Integer userId, Integer id) {
		userShieldsMapper.deleteShield(userId,id);
	}  
}
