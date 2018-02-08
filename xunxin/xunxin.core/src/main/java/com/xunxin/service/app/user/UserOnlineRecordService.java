package com.xunxin.service.app.user;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.xunxin.dao.app.user.AppUserDao;
import com.xunxin.dao.impl.app.UserOnlineRecordDaoImpl;
import com.xunxin.service.app.AppUserService;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.vo.user.UserOnlineRecord;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018年1月7日 -- 上午11:17:03
 * @Version 1.0
 * @Description     用户在线时长记录
 */
@Repository
public class UserOnlineRecordService extends UserOnlineRecordDaoImpl{


	@Autowired
	private AppUserDao appUserDao;
	@Autowired
	private UserWelfareService userWelfareService;
	
	//用户平均在线时长
	public void queryAverageOnlineLength() {
		
	    //过滤条件  
//	    DBObject queryObject=new BasicDBObject("isDelete", false);  
//	    DBObject queryMatchA=new BasicDBObject("$match",queryObject);  
//	    //展开数组  
//	    DBObject queryUnwindA=new BasicDBObject("$unwind","$items");  
//	    //分组统计  
//	    DBObject groupObject=new BasicDBObject("_id",new BasicDBObject("userId","$items.userId"));  
//	    groupObject.put("total", new BasicDBObject("$sum","$items.quantity"));  
//	    DBObject  queryGroupA=new BasicDBObject("$group",groupObject);  
	    //过滤条件  
//	    DBObject finalizeMatch=new BasicDBObject("$match",new BasicDBObject("total",new BasicDBObject("$gt",1)));  
//	    DBObject queryMatch =JSON.parse(queryMatchA);
//        String groupStr = "{$group:{_id:{'num':'$userId'},docsNum:{$sum:1}}}";
//        DBObject group = (DBObject) JSON.parse(groupStr);
//        String matchStr = "{$match:{isDelete:false}}";
//        DBObject match = (DBObject) JSON.parse(matchStr);
//        String sortStr = "{$sort:{_id.docsNum:-1}}";
//        DBObject sort = (DBObject) JSON.parse(sortStr);
//	    @SuppressWarnings("deprecation")
//		AggregationOutput  output=mongoTemplate.getCollection("userOnlineRecord").aggregate(group,match,sort);  
//	    for (Iterator<DBObject> iterator = output.results().iterator(); iterator.hasNext();) {  
//	        DBObject obj =iterator.next();  
//	        System.out.println(obj.toString());  
//	    } 
		Integer count = 0;
		List<UserEntity> list = appUserDao.findAll();
		for (UserEntity userEntity : list) {
			Query query = new Query();
			query.addCriteria(Criteria.where("isDelete").is(false));
			query.addCriteria(Criteria.where("userId").is(userEntity.getUid()));
			List<UserOnlineRecord> listCount = find(query);
			Integer userCount = 0;
			for (UserOnlineRecord userOnlineRecord : listCount) {
				if(userOnlineRecord.getOnlineTime()>0){
					userCount+=userOnlineRecord.getOnlineTime();
				}
			}
			userWelfareService.updateOnline(userEntity.getUid(),userCount);
			count+=userCount;
			userCount=0;
		}
		Integer sun = count/list.size();
		userWelfareService.updateSum(sun);
	}

}
