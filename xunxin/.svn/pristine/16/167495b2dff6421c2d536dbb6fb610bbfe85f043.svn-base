package com.xunxin.service.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.xunxin.dao.app.user.AppUserDao;
import com.xunxin.dao.impl.ArecordTestDaoImpl;
import com.xunxin.vo.qa.ArecordTest;
import com.xunxin.vo.qa.ArecordVO;
import com.xunxin.vo.qa.QASection;
import com.xunxin.vo.user.UserEntity;
@Repository
public class ArecordTestService extends ArecordTestDaoImpl{

	@Autowired
	private UserFriendsService userFriendsService;
	@Autowired
	private QASectionService qASectionService;
	@Autowired
	private AppUserDao appUserDao;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private MongoTemplate mongoTemplate;
	//按回答模块挑选兴趣相同的人
	public List<Integer> findUsersByModuleHeat(Integer userId, Integer num) {
		try {
			List<QASection> all = qASectionService.getAll();
			List<UserEntity> listUsers = appUserDao.findAll();
			List<Integer> listMyInterest = sameInterest(userId,all);
			List<Integer> returnList = new ArrayList<>();
			for (UserEntity userEntity : listUsers) {
				List<Integer> listPK = sameInterest(userEntity.getUid(),all);
				boolean flag = listMyInterest.containsAll(listPK);
				if(flag){
					returnList.add(userEntity.getUid());
				}
			}
			return returnList;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	//取最近两个月热门板块
	private List<Integer> sameInterest(Integer userId, List<QASection> all) {
		List<Integer> queryType = new ArrayList<Integer>();
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>(new Comparator<Integer>(){  
            public int compare(Integer o1, Integer o2) {  
                return o2.compareTo(o1);  
            }     
        }); 
		for (QASection qaSection : all) {
			Query query = new Query();
			query.addCriteria(Criteria.where("isDelete").is(false));
			query.addCriteria(Criteria.where("answerID").is(userId));
			query.addCriteria(Criteria.where("type").is(qaSection.getSectionType()));
			query.addCriteria(Criteria.where("createTime").gte(userFriendsService.getBeforeDate(new Date(), 60)));
			query.with(new Sort(new Order(Direction.DESC, "createTime")));
			Integer count = findCountByQuery(query);
			map.put((int)qaSection.getSectionType(), count);
		}
		queryType=sort(map);
		return queryType;
	}
	//map排序取前三个
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Integer> sort(TreeMap<Integer, Integer> map) {
		List list = new ArrayList<Integer>();
		Set entries = map.entrySet();
		Iterator it = null;
		if (entries != null)
		it = entries.iterator();
		for(int k=1; it.hasNext();k++){
			Map.Entry entry = (Map.Entry) it.next();
			int key = (Integer) entry.getKey();
				if(k>3){
					break;
				}
				list.add(key);
			}
		return list;
	}
	//此时此刻查找符合的人
	public List<Integer> findMomentUser(Integer userId) {
		List<Integer> idList =appUserService.findAllNotUserId(userId);
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("answerID").is(userId));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));
		query.limit(10);
		List<ArecordTest> list = find(query);
		
		Iterator<Integer> it = idList.iterator();
		while(it.hasNext()){
		    Integer x = it.next();
		    boolean flag = findMomentUser(list,x);
		    if(!flag){
		        it.remove();
		    }
		}
		return idList;
	}
	// 查找匹配的人》此时此刻
	private boolean findMomentUser(List<ArecordTest> list, Integer userId) {
		List<String> queryString = new ArrayList<>();
		for (ArecordTest arecordTest : list) {
			if(arecordTest!= null && arecordTest.getAnswers() != null)
			queryString.add(arecordTest.getAnswers());
		}
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("answerID").is(userId));
		query.addCriteria(Criteria.where("answers").in(queryString));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));
		List<ArecordTest> listResult = find(query);
		if(listResult.size()>=10){
			return true;
		}
		return false;
	}

}
