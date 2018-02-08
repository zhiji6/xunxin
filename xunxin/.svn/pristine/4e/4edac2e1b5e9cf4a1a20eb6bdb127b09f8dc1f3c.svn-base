package com.xunxin.controller.app.qa;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunxin.config.RedisRepository;
import com.xunxin.controller.app.comment.CommentController;
import com.xunxin.controller.system.BaseController;
import com.xunxin.service.ArecordService;
import com.xunxin.service.app.QASectionService;
import com.xunxin.service.app.XunxinAuditInformationRecordService;
import com.xunxin.service.app.qa.QuestionService;
import com.xunxin.service.app.user.UserDynamicRecordService;
import com.xunxin.vo.qa.QASection;
import com.xunxin.vo.qa.QuestionVO;
import com.xunxin.vo.user.UserDynamicRecordVO;
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
					
//			boolean flag = arecordService.questionAudit("5a091b8c65df4a415498038c",1);
			boolean flag = xunxinAuditInformationRecordService.volunteerAdopt(userId,questionId,type,isAdopt,content);
					log.info("infoMsg:--- 志愿者审核结束");
			if(flag){
				//生成动态
				UserDynamicRecordVO vo = new UserDynamicRecordVO();
				QuestionVO questionVO = questionService.findOneById(questionId);
				questionVO.getName();
				vo.setDynamicUrls(questionVO.getContent());
				vo.setDynamicSource("Q&A");
				vo.setContent(questionVO.getName());
				vo.setUserId(questionVO.getUserID());
				vo.setThumbCount(0);
				userDynamicRecordService.save(vo);
				return res.success("审核成功");
			}else{
				return res.failure("审核失败");
			}
					
		} catch (Exception e) {
			log.error("errorMsg:--- 志愿者审核失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}

}
