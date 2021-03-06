package com.xunxin.service.app;

import java.math.BigDecimal;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.xunxin.controller.app.message.CommentMessage;
import com.xunxin.controller.app.message.Context;
import com.xunxin.controller.app.message.FollowMessage;
import com.xunxin.controller.app.message.GiveUpMessage;
import com.xunxin.controller.app.message.LeaveMessage;
import com.xunxin.controller.app.message.MatchingMessage;
import com.xunxin.controller.app.message.MessTypeFactory;
import com.xunxin.controller.app.message.NotThrough;
import com.xunxin.controller.app.message.ReportMessage;
import com.xunxin.controller.app.message.ThroughMessage;
import com.xunxin.controller.app.quartz.heartConsonanceJob;
import com.xunxin.controller.app.rules.ContextRulers;
import com.xunxin.controller.app.rules.ModularRuler;
import com.xunxin.controller.app.rules.QARuler;
import com.xunxin.controller.app.rules.RuleFactory;
import com.xunxin.controller.app.rules.Rules;
import com.xunxin.controller.app.rules.SexualOrientation;
import com.xunxin.dao.app.user.AppUserDao;
import com.xunxin.dao.im.HeartConsonancMapper;
import com.xunxin.dao.im.UserMatchMapper;
import com.xunxin.dao.qa.XunxinUserDeviceTokenMapper;
import com.xunxin.service.ArecordService;
import com.xunxin.service.app.quartz.QuartzManagerService;
import com.xunxin.vo.im.HeartConsonanc;
import com.xunxin.vo.im.HeartConsonancExample;
import com.xunxin.vo.im.HeartConsonancExample.Criteria;
import com.xunxin.vo.im.MessageRead;
import com.xunxin.vo.im.NearLocation;
import com.xunxin.vo.im.UserFriends;
import com.xunxin.vo.im.UserMatch;
import com.xunxin.vo.im.UserMatchExample;
import com.xunxin.vo.user.UserEntity;


@Service("iMNewsService")
public class IMNewsService {
	
	@Autowired
	private SysDictService sysDictService;
	@Autowired
	private XunxinUserDeviceTokenMapper xunxinUserDeviceTokenMapper;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private HeartConsonancMapper heartConsonancMapper;
	@Autowired
	private UserMatchMapper userMatchMapper;
	@Autowired 
	private NearLocationService nearLocationService;
	@Autowired
	private SchedulerFactoryBean MyScheduler;
	@Autowired
	private AppUserDao appUserDao;
	@Autowired
	private ArecordService arecordService;
	@Autowired
	private MessageReadService messageReadService;
	@Autowired
	private UserFriendsService userFriendsService;
	@Autowired
	private QuartzManagerService quartzManagerService;
	@Autowired
	private ArecordTestService arecordTestService;
	
	private static final Logger logger = Logger.getLogger(IMNewsService.class);

	public void userBehaviorPushMessage(Integer type, Integer userId,Integer anotherId, String dynamicId) {
		try {
			MessTypeFactory factory = MessTypeFactory.getSingleton();
			Context context = new Context(factory.getMessage(type));        
		    context.executeStrategy(userId, anotherId, dynamicId, type,sysDictService,xunxinUserDeviceTokenMapper,appUserService);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public List<HeartConsonanc> findHertConsonanceByUserId(Integer userId) {
		List<HeartConsonanc> list = heartConsonancMapper.findHertConsonanceByUserId(userId);
		for (HeartConsonanc userMatch : list) {
			if(userMatch!= null && userMatch.getConsonanceId() != null ){
				BigDecimal value =nearLocationService.queryDistance(userId,userMatch.getConsonanceId());
				userMatch.setDistance(value+"米");
			}
		}
		
		return list;
	}

	public void insert(Integer userId, Integer consonanceId, BigDecimal matchValue) {
		HeartConsonancExample example = new HeartConsonancExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andConsonanceIdEqualTo(consonanceId);
		criteria.andIsDeleteEqualTo(0);
		List<HeartConsonanc> exampleList = heartConsonancMapper.selectByExample(example);
		if(exampleList != null && exampleList.size()>0){
			HeartConsonanc heartConsonanc = exampleList.get(0);
			heartConsonanc.setUserId(userId);
			heartConsonanc.setConsonanceId(consonanceId);
			heartConsonanc.setMatchValue(matchValue);
			heartConsonanc.setUpdatedate(new Date());
		}else{
			HeartConsonanc heartConsonanc = new HeartConsonanc();
			heartConsonanc.setUserId(userId);
			heartConsonanc.setIsDelete(0);
			heartConsonanc.setMatchValue(matchValue);
			heartConsonanc.setConsonanceId(consonanceId);
			heartConsonanc.setCreatedate(new Date());
			heartConsonanc.setUpdatedate(new Date());
			heartConsonancMapper.insert(heartConsonanc);
		}
		
	}
	//添加匹配信息
	public void insertatch(Integer userId, Integer consonanceId, BigDecimal matchValue) {
		UserMatch userMatch = new UserMatch();
		userMatch.setUserId(userId);
		userMatch.setIsDelete(0);
		userMatch.setMatchValue(matchValue);
		userMatch.setConsonanceId(consonanceId);
		userMatch.setCreatedate(new Date());
		userMatch.setUpdatedate(new Date());
		userMatchMapper.insert(userMatch);
		
	}
	//查询匹配列表
	public List<UserMatch> findMatchsByUserId(Integer userId) {
		List<UserMatch> list = userMatchMapper.findMatchsByUserId(userId);
		for (UserMatch userMatch : list) {
			if(userMatch!= null && userMatch.getConsonanceId() != null ){
				BigDecimal value =nearLocationService.queryDistance(userId,userMatch.getConsonanceId());
				if(value.intValue() < 1){
					int i = (int)(10+Math.random()*(20-10+1));
					userMatch.setNearby(i);
				}else{
					userMatch.setNearby(Integer.valueOf(value.intValue()));
				}
			}
		}
		for (UserMatch userMatch : list) {
			if(userMatch.getNearby()!= null){
				Integer integer = userMatch.getNearby();
				if(integer/1000>0){
					String value= integer.toString().substring(0,integer.toString().length()-3);
					if(integer/100%10>0){
						value =value+"."+integer/100%10;
					}
					userMatch.setDistance(value+"公里");
				}else{
					userMatch.setDistance(integer.toString()+"米");
				}
			}
		}
		 for (int i = 0; i < list.size() - 1; i++) {
	            for (int j = list.size() - 1; j > i; j--) {
	                if (list.get(j).equals(list.get(i))) {
	                    list.remove(j);
	                }
	            }
	        }
		return list;
	}
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(1000);
		list.add(1200);
		list.add(1200000);
		for (Integer integer : list) {
			
		}
	}
	//定时任务增加
	public void addHeartConsonanceJob() {
		 String JOB_NAME = "动态任务调度";  
		    String TRIGGER_NAME = "动态任务触发器";  
		    String JOB_GROUP_NAME = "XLXXCC_JOB_GROUP";  
		    String TRIGGER_GROUP_NAME = "XLXXCC_JOB_GROUP";
//		    QuartzManager QuartzManager = new QuartzManager();
		    QuartzManagerService QuartzManager=new QuartzManagerService();

		try {
			Thread.sleep(5000);
			System.out.println("【系统启动】开始(每1秒输出一次)...");    
             QuartzManager.addJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME, heartConsonanceJob.class, "0/1 * * * * ?",MyScheduler);
             System.out.println("【修改时间】开始(每5秒输出一次)...");    
	         QuartzManager.modifyJobTime(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME, "0/5 * * * * ?",MyScheduler);
	         Thread.sleep(6000);    
	         System.out.println("【移除定时】开始...");    
	         QuartzManager.removeJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME,MyScheduler); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			           
	}
	//心有灵犀轮询初始化
	public void heartConsonanceInitJob() {
		 String JOB_NAME = "心有灵犀定时任务调度任务调度";  
		    String TRIGGER_NAME = "心有灵犀触发器";  
		    String JOB_GROUP_NAME = "heartConsonance_GROUP";  
		    String TRIGGER_GROUP_NAME = "XLheartConsonance_GROUP";
//		    QuartzManager QuartzManager = new QuartzManager();
		    QuartzManagerService QuartzManager=new QuartzManagerService();
		    
        try {
    	    System.out.println("【移除定时】开始...");    
    	    QuartzManager.removeJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME,MyScheduler); 
       	 System.out.println("【系统启动】开始(每1秒输出一次)...");    
       	 	
       	 Scheduler sched = MyScheduler.getScheduler();  
         JobDetail jobDetail= JobBuilder.newJob(heartConsonanceJob.class).withIdentity(JOB_NAME, JOB_GROUP_NAME).build();
         TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
         triggerBuilder.withIdentity(TRIGGER_NAME, TRIGGER_GROUP_NAME);
         triggerBuilder.startNow();
         triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule("2/59 0/10 * * * ? "));
         CronTrigger trigger = (CronTrigger) triggerBuilder.build();

         sched.scheduleJob(jobDetail, trigger);  

         // 启动  
         if (!sched.isShutdown()) {  
             sched.start();  
         }  
//		System.out.println("【修改时间】开始(每5秒输出一次)...");    
//	    QuartzManager.modifyJobTime(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME, "0/5 * * * * ?",MyScheduler);    
//	    System.out.println("【移除定时】开始...");    
//	    QuartzManager.removeJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME,MyScheduler); 
		} catch (SchedulerException e) {
			e.printStackTrace();
		}    
        
		
	}
	//心有灵犀处理方法
	public void evenTheHeart() {
		
		List<UserEntity> userList = appUserDao.findAll();
		for (UserEntity userEntity : userList) {
			for (UserEntity user : userList) {
				if(user.equals(userEntity)){
					continue;
				}
				
				List<HeartConsonanc> selectByExample= null;
				double d=0;
				if(userEntity != null && Integer.valueOf(userEntity.getUid()) != null && user != null && Integer.valueOf(user.getUid()) != null){
					 d= match(user.getUid(),userEntity.getUid());
					 HeartConsonancExample example = new HeartConsonancExample();
						com.xunxin.vo.im.HeartConsonancExample.Criteria create = example.createCriteria();
						create.andUserIdEqualTo(userEntity.getUid());
						create.andConsonanceIdEqualTo(user.getUid());
						create.andIsDeleteEqualTo(0);
						selectByExample = heartConsonancMapper.selectByExample(example);
						
				}
				
				if(d>0.9 && selectByExample.size() < 1){
					HeartConsonanc userMatch = new HeartConsonanc();
					userMatch.setUserId(userEntity.getUid());
					userMatch.setConsonanceId(user.getUid());
					userMatch.setIsDelete(0);
					userMatch.setMatchValue(BigDecimal.valueOf(d));
					userMatch.setCreatedate(new Date());
					userMatch.setUpdatedate(new Date());
					heartConsonancMapper.insert(userMatch);
					MessageRead messageRead = new MessageRead();
					messageRead.setCreateTime(new Date());
					messageRead.setUserId(userEntity.getUid());
					messageRead.setIsRead(0);
					messageRead.setUpdateTime(new Date());
					messageRead.setTag(1);
					messageRead.setIsDelete(false);
					messageReadService.insert(messageRead);
					selectByExample=null;
				}
			}
		}
	}
	//匹配值
	public double match(Integer a,Integer b){
		return queryMatchingDegree(a,b).doubleValue();
	}

	//
	public BigDecimal queryMatchingDegree(Integer userId, Integer friendId) {
		ContextRulers context = new ContextRulers(new ModularRuler());
		BigDecimal degree = context.executeStrategy(userId, friendId, arecordService,appUserService);
		ContextRulers contextSex = new ContextRulers(new SexualOrientation());
		BigDecimal degreeSex = contextSex.executeStrategy(userId, friendId, arecordService,appUserService);
		ContextRulers contextQA = new ContextRulers(new QARuler());
		BigDecimal degreeQA = contextQA.executeStrategy(userId, friendId, arecordService,appUserService);
		return BigDecimal.valueOf(degree.doubleValue()*0.7+degreeSex.doubleValue()*0.1+degreeQA.doubleValue()*0.2);
	}
	//查找此时此刻A

	public List<UserMatch> queryMoment(Integer userId, Integer range,Integer age) {
		NearLocation near =nearLocationService.findOneByQuery(userId);
		RuleFactory factory = new RuleFactory();
		Rules rule = factory.getRule(14);
		List<NearLocation> list = null;
		List<UserEntity> listUser = null;
		if(range == null){
			list = nearLocationService.queryNearLocation(near.getLongitude(),near.getLatitude(),userId,20);
		}else{
			list =nearLocationService.queryNearLocationRange(near.getLongitude(),near.getLatitude(),userId,range);
		}
		if(age == null){
			age = 5;
		}
		listUser = appUserService.findUserListByAge(userId,age);
		List<Integer> userIdList = new ArrayList<>();
		
		
		List<Integer> ids = new ArrayList<>();
		Map<BigDecimal, Integer> map = new TreeMap<BigDecimal, Integer>(
                new Comparator<BigDecimal>() {
                    public int compare(BigDecimal obj1, BigDecimal obj2) {
                        // 降序排序
                        return obj2.compareTo(obj1);
                    }
                });
		if(near == null || near.getLongitude() == null || near.getLatitude() == null){
			return null;
		}
		
		if(listUser == null || listUser.size()<1){
			return null;
		}
		for (UserEntity userEntity : listUser) {
			for (NearLocation nearLocation : list) {
				if(Integer.valueOf(userEntity.getUid()).equals(nearLocation.getUserId())&& !ids.contains(userEntity.getUid())){
					ids.add(userEntity.getUid());
				}
			}
		}
		for (Integer id : ids) {
			BigDecimal score = rule.getScore(userId, id, null, null);
			map.put(score, id);
		}

		Set entries = map.entrySet();
		Iterator it = null;
		if (entries != null){
			it = entries.iterator();
		}

		for(int k=1; it.hasNext();k++){
			Map.Entry entry = (Map.Entry) it.next();
			int key = (Integer) entry.getKey();
			String value = entry.getValue().toString();
			userIdList.add(Integer.valueOf(value));
			if(k<=10){
				break;
			}
		}
		List<UserMatch> listReturn = appUserService.findUserListByUser(userIdList);
		for (UserMatch userMatch : listReturn) {
			BigDecimal queryDistance = nearLocationService.queryDistance(userId, userMatch.getUserId());
			userMatch.setDistance(queryDistance.toString());
		}
        Collections.sort(listReturn, new Comparator<UserMatch>() {
            @Override
            public int compare(UserMatch o1, UserMatch o2) {
                Collator collator = Collator.getInstance(Locale.CHINA);
                return collator.compare(o1.getDistance(), o2.getDistance());
            }
        });
		return listReturn;
	}
	//查询消息列表
	public Map<String, Object> findMessageByUserId(Integer userId) {
		List<MessageRead> listOfficial =null;
		List<MessageRead> listHert =null;
		List<MessageRead> listInteraction =null;
		{		
			Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("userId").is(userId));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("isDelete").is(false));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("isRead").is(0));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("tag").is(1));
			listHert = messageReadService.find(query);
		}
		{
			Query query = new Query();
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("userId").is(userId));
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("isDelete").is(false));
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("isRead").is(0));
			query.with(new Sort(new Order(Direction.DESC, "createTime")));
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("tag").is(2));
			listOfficial = messageReadService.find(query);
		}
		{
			Query query = new Query();
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("userId").is(userId));
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("isDelete").is(false));
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("isRead").is(0));
			query.with(new Sort(new Order(Direction.DESC, "createTime")));
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("tag").is(3));
			listInteraction = messageReadService.find(query);
		}
		Map<String,Object> mapOfficial = new HashMap<>();
		mapOfficial.put("tag", "Official");
		if(listOfficial != null && listOfficial.size() > 0 && listOfficial.get(0).getUpdateTime()!=null){
			mapOfficial.put("time", listOfficial.get(0).getUpdateTime());
			mapOfficial.put("content", listOfficial.get(0).getTitle());
		}else{
			mapOfficial.put("time", null);
			mapOfficial.put("content", null);
		}
		mapOfficial.put("num", listOfficial.size());
		
		
		Map<String,Object> mapHert = new HashMap<>();
		if(listHert != null && listHert.size() > 0 && listHert.get(0).getUpdateTime()!=null){
			mapHert.put("time", listHert.get(0).getUpdateTime());
			mapHert.put("content", listHert.get(0).getTitle());
		}else{
			mapHert.put("time", null);
			mapHert.put("content", null);
		}
		mapHert.put("tag", "Hert");
		mapHert.put("num", listHert.size());
		
		
		Map<String,Object> mapInteraction = new HashMap<>();
		mapInteraction.put("tag", "Interaction");
		if(listInteraction != null && listInteraction.size() > 0 && listInteraction.get(0).getUpdateTime()!=null){
			mapInteraction.put("time", listInteraction.get(0).getUpdateTime());
			mapInteraction.put("content", listInteraction.get(0).getTitle());
		}else{
			mapInteraction.put("time", null);
			mapInteraction.put("content", null);
		}
		mapInteraction.put("num", listInteraction.size());
		Map<String,Object> mapFriends = new HashMap<>();
		List<Map<String, Object>> mapList=new ArrayList<>();
		mapList.add(mapHert);
		mapList.add(mapOfficial);
		mapList.add(mapInteraction);
		mapFriends.put("news", mapList);
		return mapFriends;
	}
	//查询此时此刻B
	public List<UserMatch> queryMomentPlanB(Integer userId, Integer range, Integer age) {
		NearLocation near =nearLocationService.findOneByQuery(userId);
		if(near == null || near.getLongitude() == null || near.getLatitude() == null){
			return null;
		}
		logger.info("我的位置"+near.getLongitude()+"-------"+near.getLatitude());
		//查找最近10道题符合的人
//		List<Integer> listMoment = arecordService.findMomentUser(userId);
//		if(listMoment ==null || listMoment.size()<1){
//			return null;
//		}
		logger.info("最近10道题符合的人");
//		System.out.println(listMoment);
		List<NearLocation> list = null;
		List<UserEntity> listUser = null;
		
		
		//查找距离符合的人
		if(range == null){
			list = nearLocationService.queryNearLocation(near.getLongitude(),near.getLatitude(),userId,20);
		}else{
			list =nearLocationService.queryNearLocationRange(near.getLongitude(),near.getLatitude(),userId,range*1000);
		}
		if(list == null || list.size()<1){
			return null;
		}
		logger.info("距离符合的人");
		System.out.println(list);
		if(age == null){
			age = 50;
		}
		//查找年龄和性别符合的人
//		listUser = appUserService.findUserListByAge(userId,age);
		List<Integer> ids = new ArrayList<>();
//		
//		if(listUser == null || listUser.size()<1){
//			return null;
//		}
		logger.info("年龄和性别符合的人");
//		System.out.println(listUser);
//		for (UserEntity userEntity : listUser) {
//			for (NearLocation nearLocation : list) {
////				for(Integer userid :listMoment){
////					if(Integer.valueOf(userEntity.getUid()).equals(nearLocation.getUserId())&&userid != null && userid.equals(userEntity.getUid()) &&!userid.equals(userId)&& !ids.contains(userEntity.getUid())){
////						ids.add(userEntity.getUid());
////					}
////				}
//				if(userEntity != null && (Integer)userEntity.getUid()!= null && !userId.equals(userEntity.getUid())){
//					ids.add(userEntity.getUid());
//				}
//				
//			}
//		}
		for (NearLocation nearLocation : list) {
//			if(nearLocation != null && (Integer)nearLocation.getUserId()!= null && !userId.equals(nearLocation.getUserId())){
				ids.add(nearLocation.getUserId());
//			}
		}
//		//ids.retainAll(listUser);
		if(ids==null||ids.size()<1){
			return null;
		}
		logger.info("满足条件的人");
//		System.out.println(ids);
		List<UserMatch> listMatch = queryMoment(ids,userId);
		return listMatch;
	}
	//查找距离
	private List<UserMatch> queryMoment(List<Integer> ids,Integer userId) {
		List<UserMatch> list=appUserDao.findUserListByUser(ids);
		for (UserMatch userMatch : list) {
			if(userMatch!= null && userMatch.getUserId() != null ){
				BigDecimal value =nearLocationService.queryDistance(userId,userMatch.getUserId());
				if(value.intValue() < 1){
					int i = (int)(10+Math.random()*(20-10+1));
					userMatch.setNearby(i);
				}else{
					userMatch.setNearby(Integer.valueOf(value.intValue()));
				}
			}
		}
		Collections.sort(list);
		for (UserMatch userMatch : list) {
			if(userMatch.getNearby()!= null){
				Integer integer = userMatch.getNearby();
				if(integer/1000>0){
					String value= integer.toString().substring(0,integer.toString().length()-3);
					if(integer/100%10>0){
						value =value+"."+integer/100%10;
					}
					userMatch.setDistance(value+"公里");
				}else{
					userMatch.setDistance(integer.toString()+"米");
				}
			}
		}
		return list;
	}
	//阅读匹配消息
	public void readMatch(Integer matchId) {
		userMatchMapper.updateByMatchRead(matchId);
	}
	//保存匹配信息
	public void save(Integer userId, Integer anotherUserId) {
		UserMatchExample example =new UserMatchExample();
		com.xunxin.vo.im.UserMatchExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andConsonanceIdEqualTo(anotherUserId);
		List<UserMatch> list = userMatchMapper.selectByExample(example);
		
		UserMatch record = new UserMatch();
		record.setUserId(userId);
		record.setConsonanceId(anotherUserId);
		record.setIsDelete(0);
		record.setIsRead(0);
		record.setCreatedate(new Date());
		record.setUpdatedate(new Date());
		if(list != null && list.size()>0){
			record.setId(list.get(0).getId());
			userMatchMapper.updateByPrimaryKey(record);
		}else{
			userMatchMapper.insert(record);
		}
		
	}

}


