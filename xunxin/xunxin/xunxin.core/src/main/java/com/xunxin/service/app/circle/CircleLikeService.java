package com.xunxin.service.app.circle;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.circle.CircleCommentDao;
import com.xunxin.dao.circle.CircleLikeDao;
import com.xunxin.dao.impl.CircleCommentDaoImpl;
import com.xunxin.dao.impl.CircleLikeDaoImpl;
import com.xunxin.service.app.EmpathyCircleService;
import com.xunxin.vo.circle.CircleLike;
import com.xunxin.vo.qa.GiveUpVo;
@Repository
public class CircleLikeService extends CircleLikeDaoImpl{

	private static final Logger logger = Logger.getLogger(CircleLikeService.class);
	
	@Autowired
	private EmpathyCircleService empathyCircleService;

	public void saveOrupdate(String empathyCircleId, Integer userId, Integer giveUp) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("empathyCircleId").is(empathyCircleId));
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("userId").is(userId));
		empathyCircleService.updateLikeNum(empathyCircleId,giveUp);
		CircleLike findOneByQuery = findOneByQuery(query);
		if(findOneByQuery != null){
			Update update = Update.update("giveUP", giveUp);
			update.set("updateTime", new Date());
			updateFirst(query, update);
		}else{
			CircleLike vo = new CircleLike();
			vo.setEmpathyCircleId(empathyCircleId);
			vo.setCreateTime(new Date());
			vo.setUpdateTime(new Date());
			vo.setIsDelete(false);
			vo.setGiveUP(giveUp);
			vo.setUserId(userId);
			insert(vo);
		}
	}
}
