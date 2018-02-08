package com.xunxin.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.xunxin.config.RedisRepository;
import com.xunxin.controller.app.circle.EmpathyCircleController;
import com.xunxin.dao.app.user.AppUserDao;
import com.xunxin.dao.impl.app.ArecordDaoImpl;
import com.xunxin.dao.qa.SysDictMapper;
import com.xunxin.dao.qa.XunxinAuditInformationRecordMapper;
import com.xunxin.dao.qa.XunxinUserAuditMapper;
import com.xunxin.dao.qa.XunxinUserDeviceTokenMapper;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.QASectionService;
import com.xunxin.service.app.UserFriendsService;
import com.xunxin.service.app.qa.QuestionVOManagerService;
import com.xunxin.util.CalcDemo;
import com.xunxin.vo.qa.ArecordTest;
import com.xunxin.vo.qa.ArecordVO;
import com.xunxin.vo.qa.QASection;
import com.xunxin.vo.qa.QuestionVO;
import com.xunxin.vo.qa.SysDict;
import com.xunxin.vo.qa.XunxinUserAudit;
import com.xunxin.vo.qa.XunxinUserAuditExample;
import com.xunxin.vo.qa.XunxinUserDeviceToken;
import com.xunxin.vo.user.UserEntity;

@Repository
public class ArecordService  extends ArecordDaoImpl{

	/*@Autowired
	private ArecordDao arecordDao;*/
	
	@Autowired
	XunxinUserAuditMapper xunxinUserAuditMapper;
	@Autowired
	SysDictMapper sysDictMapper;
	@Autowired
	private QASectionService qectionService;
	@Autowired
	private XunxinUserDeviceTokenMapper xunxinUserDeviceTokenMapper;
	@Autowired
	private XunxinAuditInformationRecordMapper xunxinAuditInformationRecordMapper;
	@Autowired
	private QASectionService QASectionService;
	@Autowired
	QuestionVOManagerService questionVOManagerService;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private UserFriendsService userFriendsService;
	@Autowired
	private QASectionService qASectionService;
	@Autowired
	private AppUserDao appUserDao;
	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Logger log = Logger.getLogger(ArecordService.class);
	public List<QASection> auditList(Integer userId, List<QASection> qsList, String answerNum) {
		
		List<QASection> list = new ArrayList();
		
		

		
		for (QASection qaSection : qsList) {
			double exec = 0.01;
			Query query = new Query();
			query.addCriteria(Criteria.where("type").is(qaSection.getSectionType()));
			query.addCriteria(Criteria.where("isDelete").is(false));
			Integer count = findCountByQuery(query);
			
			Query queryOwn = new Query();
			queryOwn.addCriteria(Criteria.where("type").is(qaSection.getSectionType()));
			queryOwn.addCriteria(Criteria.where("isDelete").is(false));
			queryOwn.addCriteria(Criteria.where("AnswerID").is(userId));
			Integer countOwn = findCountByQuery(query);
			
			if(countOwn>0){
				 exec = CalcDemo.exec(countOwn+"/"+count);
			}
			
			
			if(exec > Double.parseDouble(answerNum)){
				list.add(qaSection);
				XunxinUserAuditExample xunxinUserAudit = new XunxinUserAuditExample();
				XunxinUserAudit audit = new XunxinUserAudit();
				audit.setIsDelete("0");
				audit.setUserId(userId);
				audit.setType(qaSection.getSectionType());
				audit.setAnswerProportion(exec);
				audit.setUpdateTime(new Date());
				
				
				com.xunxin.vo.qa.XunxinUserAuditExample.Criteria createCriteria = xunxinUserAudit.createCriteria();
				createCriteria.andUserIdEqualTo(userId);
				createCriteria.andTypeEqualTo((byte)qaSection.getSectionType());
				createCriteria.andIsDeleteEqualTo("0");
				
				List<XunxinUserAudit> selectByExample = xunxinUserAuditMapper.selectByExample(xunxinUserAudit);
				
				if(selectByExample != null && selectByExample.size()>0){
					xunxinUserAuditMapper.updateByPrimaryKey(audit);
				}else{
					audit.setCreateTime(new Date());
					xunxinUserAuditMapper.insert(audit);
				}
			}
			
		}
		List<XunxinUserAudit> listAudit = xunxinUserAuditMapper.findListUserId(userId);
		if(list != null && listAudit.size() >0){
			for (int i = 0; i < listAudit.size(); i++) {
				if(i<1){
					continue;
				}
				listAudit.get(i).setIsDelete("1");
				xunxinUserAuditMapper.updateByPrimaryKey(listAudit.get(i));
				
			}
		}
		return list;
	}

	public boolean questionAudit(String questionId, Integer type) throws Exception {
		
		Integer auditNum = null;
		boolean flag = false;
		
		boolean auditNumredis = RedisRepository.exists("auditNum");
		
		if(!auditNumredis){
			List<SysDict> dict =sysDictMapper.findDictByType("audit_Num");
			if(dict != null && dict.size()> 0 && dict.get(0).getLabel() != null){
				auditNum = Integer.valueOf(dict.get(0).getLabel());
				RedisRepository.set("auditNum", auditNum);
			}
		}else{
			auditNum = Integer.valueOf((String) RedisRepository.get("auditNum"));
			if(auditNum == null ){
				List<SysDict> dict =sysDictMapper.findDictByType("audit_Num");
				if(dict != null && dict.size()> 0 && dict.get(0).getLabel() != null){
					auditNum = Integer.valueOf(dict.get(0).getLabel());
					RedisRepository.set("auditNum", auditNum);
				}
			}
		}
		
		List<XunxinUserAudit> users = xunxinUserAuditMapper.findAuditUserLogin(type,auditNum);
		
		if(users == null && users.size() < auditNum){
			return flag;
		}else{
			//QASection findOneById = qectionService.findOneById(questionId);
			QuestionVO questionVO = new QuestionVO();
			questionVO.setId("adsfsgshsthy");
			questionVO.setName("审核测试");
			if(questionVO != null ){
				for (XunxinUserAudit userEntity : users) {
					XunxinUserDeviceToken user = xunxinUserDeviceTokenMapper.selectByPrimaryKey(userEntity.getUserId());
					if(user != null && user.getDeviceToken() != null){
					
					/*UpushUtils.sendIOSUnicast(UpushConstants.APP_appkey,UpushConstants.IOS_appMasterSecret,
							
							user.getDeviceToken(), "测试", 0, "测试", "gvf", JacksonUtil.Builder().obj2Json(questionVO));*/
					}
				}
				
				//定时审核任务
				Timer timer = new Timer();
		        // 三秒后开始执行，每隔一秒执行一次
		        timer.schedule(new AuditimerTask(questionId,auditNum,timer,new Date(),type,xunxinAuditInformationRecordMapper,xunxinUserAuditMapper,
		        		xunxinUserDeviceTokenMapper,QASectionService,questionVOManagerService), 1000, 1000);
				return true;
				
			}else{
				return flag;
			}
			
		}
		
	}

	public List<Integer> findMoment(Integer userId) {
		List<Integer> idList =appUserService.findAllNotUserId(userId);
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("userId").is(userId));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));
		query.limit(10);
		List<ArecordVO> list = find(query);
		for (Integer id : idList) {
			for (ArecordVO arecordVO : list) {
				Query queryTwo = new Query();
				queryTwo.addCriteria(Criteria.where("isDelete").is(false));
				queryTwo.addCriteria(Criteria.where("userId").is(userId));
				queryTwo.addCriteria(Criteria.where("answers").is(arecordVO.getAnswerID()));
				ArecordVO arecordVO1 = findOneByQuery(queryTwo);
			}

			
		}

		return null;
	}

///////////////////////////////////////////////////////////////////////////////////////


	//按回答模块挑选兴趣相同的人
	public List<Integer> findUsersByModuleHeat(Integer userId, Integer num) {
		try {
			List<QASection> all = qASectionService.getAll();
			List<UserEntity> listUsers = appUserDao.findAll();
			List<Integer> listMyInterest = sameInterest(userId,all);
			log.info("自己的热门板块"+userId);
			System.out.println(listMyInterest);
			List<Integer> returnList = new ArrayList<>();
			for (UserEntity userEntity : listUsers) {
				List<Integer> listPK = sameInterest(userEntity.getUid(),all);
				log.info("别人的热门板块"+userEntity.getUid());
				System.out.println(listPK);
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
	private List<Integer> sort(TreeMap<Integer, Integer> mapsort) {
		
        List<Map.Entry<Integer,Integer>> listMap = new ArrayList<>(mapsort.entrySet());
        Collections.sort(listMap, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return  o2.getValue().compareTo(o1.getValue());
            }
        }); 
        Map map = new LinkedHashMap<>();
        for(Map.Entry<Integer,Integer> entry:listMap){
        	if(entry.getKey() != null && entry.getValue() != null){
        		map.put(entry.getKey(), entry.getValue());
        	}
        }
        
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
		List<ArecordVO> list = find(query);
		log.info("我最近答的10道题");
		System.out.println(list);
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
	private boolean findMomentUser(List<ArecordVO> list, Integer userId) {
		List<String> queryString = new ArrayList<>();
		for (ArecordVO arecordTest : list) {
			if(arecordTest!= null && arecordTest.getAnswers() != null)
			queryString.add(arecordTest.getAnswers());
		}
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("answerID").is(userId));
		query.addCriteria(Criteria.where("answers").in(queryString));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));
		List<ArecordVO> listResult = find(query);
		log.info("其他人所答题和用户10道题的匹配的题");
		for (ArecordVO arecordVO : listResult) {
			if(arecordVO.getAnswers() != null){
				System.out.println(arecordVO.getAnswers());
			}
		}
		if(listResult.size()>=10){
			return true;
		}
		return false;
	}
	//答题人数
	public Integer queryNum(String questionId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("questionID").is(questionId));
		return findCountByQuery(query);
	}
	//有效观点数
	public Integer effectiveView(String questionId) {
		List<String> numList = new ArrayList<String>();
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("questionID").is(questionId));
		List<ArecordVO> list = find(query);
		for (ArecordVO arecordVO : list) {
			if(arecordVO != null && arecordVO.getAnswers() != null){
				String[] strings = arecordVO.getAnswers().split("_");
				if(strings.length >1){
					if(!numList.contains(strings[1])){
						numList.add(strings[1]);
					}
				}
			}
		}
		return numList.size();
	}


	
	
}