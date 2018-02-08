package com.xunxin.controller.app.qa;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunxin.config.RedisRepository;
import com.xunxin.constants.DynamicConstants;
import com.xunxin.constants.ExpConstants;
import com.xunxin.controller.app.comment.CommentController;
import com.xunxin.controller.system.BaseController;
import com.xunxin.service.ArecordService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.QASectionService;
import com.xunxin.service.app.XunxinAuditInformationRecordService;
import com.xunxin.service.app.qa.QuestionService;
import com.xunxin.service.app.user.UserDynamicRecordService;
import com.xunxin.service.app.user.UserExpChangeRecordService;
import com.xunxin.vo.account.UserExpChangeRecord;
import com.xunxin.vo.qa.QASection;
import com.xunxin.vo.qa.QuestionVO;
import com.xunxin.vo.user.UserDynamicRecordVO;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;

/**
 * 
 * @Author gyf
 * @Compile 2017年11月15日 -- 下午13:26
 * @Version 1.0
 * @Description	QA审核
 */
@Controller
@RequestMapping(value=Router.PATH+Router.Audit.PATH)
public class QuestionAuditController extends BaseController{
	
	private static final Logger log = Logger.getLogger(CommentController.class);
	
	@Autowired
	private QASectionService QASectionService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private UserDynamicRecordService userDynamicRecordService;
	@Autowired
	private ArecordService arecordService;
    @Autowired
    private UserDynamicRecordService UserDynamicRecordServics;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private UserExpChangeRecordService userExpChangeRecordService;
	@Autowired
	private XunxinAuditInformationRecordService xunxinAuditInformationRecordService; 
	
	
	/**
	 * 查询用户审核资格
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Audit.QUERY_AUDIT_QUALIFICATION,method=RequestMethod.POST)
	@ResponseBody
	public Response query_audit_qualification(@RequestParam("userId") Integer userId) {
		log.info("infoMsg:--- 查询用户审核资格开始");
		Response res = this.getReponse();
		try {
			boolean exists = RedisRepository.exists("qsList");
			boolean answerNumexists = RedisRepository.exists("answerNum");
			List<QASection> qsList = null;
			List<QASection> listEntity = null;
			String answerNum = "0.1";
			
			if(!exists){
				qsList = QASectionService.getAll();
				RedisRepository.setList("qsList", qsList);
			}else{
				qsList = RedisRepository.getListEntity("qsList", QASection.class);
				if(qsList == null || qsList.size() <1 ){
					 qsList = QASectionService.getAll();
						RedisRepository.setList("qsList", qsList);
				}
			}
			
			if(!answerNumexists){
				RedisRepository.set("answerNum", "0.1");
			}else{
				answerNum = (String) RedisRepository.get("answerNum");
				if(answerNum == null || "".equals(answerNum) ){
					RedisRepository.set("answerNum", "0.1");
					answerNum = "0.1";
				}
			}
		
			if(qsList == null){
				return res.failure("查询失败");
			}else{
				
				listEntity = arecordService.auditList(userId,qsList,answerNum);
				
				if(listEntity != null && listEntity.size() >1){
					log.info("infoMsg:--- 查询用户审核资格结束");
					return res.success(listEntity);
				}else{
					log.info("infoMsg:--- 查询用户审核资格结束");
					return res.success(listEntity);
				}
				
			}
			
			
		} catch (Exception e) {
			log.error("errorMsg:--- 查询用户审核资格失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 志愿者审核
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Audit.QUERY_AUDIT_XOLUNTEER_ADOPT,method=RequestMethod.POST)
	@ResponseBody
	public Response query_audit_volunteer_adopt(@RequestParam("userId") Integer userId,@RequestParam("questionId") String questionId,Integer type,@RequestParam("isAdopt")Integer isAdopt,String content) {
		log.info("infoMsg:--- 志愿者审核开始");
		Response res = this.getReponse();
		try {
			UserEntity user = appUserService.findById(userId);		
//			boolean flag = arecordService.questionAudit("5a091b8c65df4a415498038c",1);
			boolean flag = xunxinAuditInformationRecordService.volunteerAdopt(userId,questionId,type,isAdopt,content,"volunteer");
					log.info("infoMsg:--- 志愿者审核结束");
			if(flag){
				//生成动态
				UserDynamicRecordVO vo = new UserDynamicRecordVO();
				QuestionVO questionVO = questionService.findOneById(questionId);
				vo.setDynamicUrls(questionVO.getContent());
				vo.setDynamicSource("Q&A");
				vo.setContent(questionVO.getName());
				vo.setUserId(questionVO.getUserID());
				vo.setThumbCount(0);
				userDynamicRecordService.save(vo);
				//赠送积分
				int userExp = user.getUserExp();
				appUserService.user_exp_change(userId, userExp + 2);
				appUserService.user_exp_change(questionVO.getUserID(),userExp + 5);
				//生成积分记录
				UserExpChangeRecord userExpChangeRecord = new UserExpChangeRecord();
				userExpChangeRecord.setChangeType(ExpConstants.QUESTION_ONLINE);
				userExpChangeRecord.setDirection(ExpConstants.INCOME);
				userExpChangeRecord.setUserId(userId);
				userExpChangeRecordService.save(userExpChangeRecord);
				userExpChangeRecord.setTansferAmount(2);
				userExpChangeRecord.setTansferBefore(userExp);
				userExpChangeRecord.setTansferEnd(userExp + 2);
				Query query = new Query()
				        .addCriteria(Criteria.where("userId").is(userId))
				        .addCriteria(Criteria.where("changeType").is(DynamicConstants.PUBLISH_QUESTION))
        				.addCriteria(Criteria.where("direction").is(ExpConstants.INCOME));
				UserDynamicRecordVO record = userDynamicRecordService.findOneByQuery(query);
				if(record == null) {
				    UserDynamicRecordVO userDynamicRecord = new UserDynamicRecordVO();
				    userDynamicRecord.setContent("您发布的:" + questionVO.getName() + "通过审核");
				    userDynamicRecord.setThumbCount(0);
				    userDynamicRecord.setDynamicExtend(questionId);
				    userDynamicRecord.setDynamicSource(DynamicConstants.PUBLISH_QUESTION);
				    userDynamicRecord.setUserId(questionVO.getUserID());
				    userDynamicRecord.setIshiden(0);
				    userDynamicRecordService.save(userDynamicRecord);
				}else {
				    Update update = new Update();
				    update.set("createTime", new Date());
				    update.set("content", "您发布的:" + questionVO.getName() + "通过审核");
				    update.set("dynamicExtend", questionId);
				    userDynamicRecordService.updateFirst(query, update);
				}
				return res.success("审核成功");
			}else{
				return res.failure("审核失败");
			}
					
		} catch (Exception e) {
			log.error("errorMsg:--- 志愿者审核失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	

	/**
	 * QA审核失败的原因
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Audit.QUERY_CAUSE_OF_FAILURE,method=RequestMethod.POST)
	@ResponseBody
	public Response query_cause_of_failure(@RequestParam("id") String id) {
		log.info("infoMsg:--- QA审核失败的原因开始");
		Response res = this.getReponse();
		try {
			List<String> list = xunxinAuditInformationRecordService.queryCause(id);
			log.info("infoMsg:--- QA审核失败的原因结束");
			return res.success(list);
		} catch (Exception e) {
			log.error("errorMsg:--- QA审核失败的原因失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}

}
