package com.xunxin.controller.app.square;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunxin.controller.system.BaseController;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.qa.QuestionService;
import com.xunxin.service.app.square.ShakeItOffRecordService;
import com.xunxin.service.app.square.ShakeOffAnswerRecordService;
import com.xunxin.util.PeriodsUtil;
import com.xunxin.vo.square.ShakeItOffRecord;
import com.xunxin.vo.square.ShakeOffAnswerRecord;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月27日 -- 下午5:02:36
 * @Version 1.0
 * @Description		摇一摇
 */
@Controller
@RequestMapping(value=Router.PATH+Router.Shake.PATH)
public class ShakeOffController extends BaseController{

	private final static Logger log = Logger.getLogger(ShakeOffController.class);
	
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private ShakeItOffRecordService shakeItOffRecordService;
	@Autowired
	private ShakeOffAnswerRecordService shakeOffAnswerRecordService;
	
	/**
	 * 摇一摇
	 * 
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.Shake.SHAKE_IT_OFF,method=RequestMethod.POST)
	@ResponseBody
	public Response shake_it_off(HttpServletRequest request,@RequestParam("userId") int userId) {
		log.info("infoMsg:--- 用户摇一摇开始");
		Response reponse = this.getReponse();
		PageData pd = new PageData<>(); 
		try {
			UserEntity user = appUserService.findById(userId);
			//保存摇一摇记录
			ShakeItOffRecord record = new ShakeItOffRecord();
			record.setUserId(userId);
			shakeItOffRecordService.save(record);
			
			Query requery = new Query();
			Date endDate = PeriodsUtil.longToDate(PeriodsUtil.addDate(new Date()) + 3*1000);
			requery.addCriteria(Criteria.where("createTime").gte(new Date()).lte(endDate));
			List<ShakeItOffRecord> recordList = shakeItOffRecordService.find(requery);
			if(recordList.size() > 0) {
				for(ShakeItOffRecord vo : recordList) {
					int otherId = vo.getUserId();
					UserEntity otherUser = appUserService.findById(otherId);
					if(otherUser.getSexualOrientation().equals(user.getGender())){
						pd.put("userId", otherUser.getUid());
						pd.put("gender", otherUser.getGender());
						pd.put("nickName", otherUser.getNickName());
						pd.put("ID", otherUser.getID());
						
						Query query = new Query();
						query.addCriteria(Criteria.where("userId").is(userId));
						query.limit(0);
						ShakeItOffRecord shake = shakeItOffRecordService.findOneByQuery(query);
						shake.setOtherId(otherId);
						break;
					}else{
						pd.put("userId", otherUser.getUid());
						pd.put("gender", otherUser.getGender());
						pd.put("nickName", otherUser.getNickName());
						pd.put("ID", otherUser.getID());
						
						Query query = new Query();
						query.addCriteria(Criteria.where("userId").is(userId));
						query.limit(0);
						ShakeItOffRecord shake = shakeItOffRecordService.findOneByQuery(query);
						shake.setOtherId(otherId);
						break;
					}
				}
			}else {
				pd.put("msg", "相识满天下，知心能几人?");
			}
			log.info("infoMsg:--- 用户摇一摇结束");
			return reponse.success(pd);
		} catch (Exception e) {
			log.error("errorMsg:--- 用户摇一摇失败:" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 摇一摇匹配用户
	 * 
	 * @param userId
	 * @param questionID
	 * @param answer
	 * @return
	 */
	@RequestMapping(value=Router.Shake.USER_MATCHED_SHAKE_OFF,method=RequestMethod.POST)
	@ResponseBody
	private Response user_matched_shake_off(@RequestParam("userId") int userId,@RequestParam("otherId") int otherId,
			@RequestParam("questionID") String questionID,@RequestParam("answers") String answer) {
		
		log.info("infoMsg:--- 摇一摇用户匹配开始");
		Response reponse = this.getReponse();
		PageData pd = new PageData<>();
		try {
			UserEntity user = appUserService.findById(userId);
			
			ShakeOffAnswerRecord record = new ShakeOffAnswerRecord();
			record.setAnswerID(userId);	
			record.setAnswerTime(new Date());
			record.setQuestionID(questionID);
			record.setType((short) 0);
			String answers = questionID + "_" + answer;
			record.setAnswers(answers);
			shakeOffAnswerRecordService.save(record);		
	
			Query query = new Query();
			query.addCriteria(Criteria.where("userId").is(otherId));
			query.with(new Sort(new Order(Direction.DESC,"createTime")));
			query.limit(0);
			ShakeOffAnswerRecord shakeOff = shakeOffAnswerRecordService.findOneByQuery(query);
			
			if(shakeOff.getAnswers().equals(answers)) {
				//匹配成功返回一个用户
				pd.put("msg", "陪陪成功");
			}else {
				pd.put("msg", "相识满天下，知心能几人?");
			}
			
			log.info("infoMsg:--- 摇一摇用户匹配结束");
			return reponse.success(pd);
		} catch (Exception e) {
			log.error("errorMsg:{--- 摇一摇用户匹配失败:" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
	}
	
	
	
}
