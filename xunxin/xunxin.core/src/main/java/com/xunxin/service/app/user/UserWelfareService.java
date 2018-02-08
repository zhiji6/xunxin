package com.xunxin.service.app.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.app.user.AppUserDao;
import com.xunxin.dao.impl.app.UserWelfareDaoImpl;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.vo.user.UserWelfare;

@Repository
public class UserWelfareService extends UserWelfareDaoImpl{
	
	@Autowired
	private AppUserDao appUserDao;
	//更新上个月人均在线时长
	public void updateOnline(Integer uid, Integer userCount) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("userId").is(uid));
		UserWelfare byQuery = findOneByQuery(query);
		if(byQuery != null){
			Update update = Update.update("lastMonthOnTime", userCount);
			updateFirst(query, update);
		}else{
			UserWelfare welfare = new UserWelfare();
			welfare.setCreateTime(new Date());
			welfare.setIsDelete(false);
			welfare.setUserId(uid);
			welfare.setLastMonthOnTime(userCount);
			welfare.setPerCapitaLastMonthOnTime(0);
			insert(welfare);
		}

	}
	//更新上个月人均在线时长
	public void updateSum(Integer sum) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		Update update = Update.update("perCapitaLastMonthOnTime", sum);
		updateAllByQuery(query, update);
	}
	//更新用户等级
	public void updateUserGrade() {
		List<UserEntity> list = appUserDao.findAll();
		for (UserEntity userEntity : list) {
			Query query = new Query();
			query.addCriteria(Criteria.where("isDelete").is(false));
			query.addCriteria(Criteria.where("userId").is(userEntity.getUid()));
			UserWelfare byQuery = findOneByQuery(query);
			if(byQuery != null && byQuery.getPerCapitaLastMonthOnTime() != null && byQuery.getLastMonthOnTime() != null){
				int averageTime = byQuery.getPerCapitaLastMonthOnTime();
				int ownTime = byQuery.getLastMonthOnTime();
				if(ownTime < averageTime){
					appUserDao.updateGrade(userEntity.getUid(),0);
				}
				else if(ownTime >= averageTime && ownTime < averageTime*1.2){
					appUserDao.updateGrade(userEntity.getUid(),1);
				}
				else if(ownTime >= averageTime*1.2 && ownTime > averageTime*1.4){
					appUserDao.updateGrade(userEntity.getUid(),2);
				}
				else if(ownTime >= averageTime*1.4){
					appUserDao.updateGrade(userEntity.getUid(),3);
				}
			}
			
		}
	}

}
