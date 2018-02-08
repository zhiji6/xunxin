package com.xunxin.controller.app.im;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunxin.controller.system.BaseController;
import com.xunxin.service.ArecordService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.IMNewsService;
import com.xunxin.service.app.MessageReadService;
import com.xunxin.service.app.RechargeRecordService;
import com.xunxin.vo.im.HeartConsonanc;
import com.xunxin.vo.im.MessageRead;
import com.xunxin.vo.im.UserMatch;
import com.xunxin.vo.im.UserShields;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;

/**
 * 
 * @Author gyf
 * @Compile 2017年11月28日
 * @Version 1.0
 * @Description	消息
 */
@Controller
@RequestMapping(value=Router.PATH+Router.Im.PATH)
public class IMNewsController extends BaseController{

	private static final Logger log = Logger.getLogger(IMNewsController.class);
	
	@Autowired
	private IMNewsService iMNewsService;
	@Autowired
	private ArecordService arecordService;
	@Autowired
	private MessageReadService messageReadService;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private RechargeRecordService rechargeRecordService;
	/**
	 * 查找此时此刻列表
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Im.QUERY_MOMENT,method=RequestMethod.POST)
	@ResponseBody
	public Response query_moment(@RequestParam("userId") Integer userId, Integer range, Integer age) {
		log.info("infoMsg:--- 查找此时此刻列表开始");
		Response res = this.getReponse();
		try {
			List<UserMatch> list = iMNewsService.queryMomentPlanB(userId,range,age);
//			if(list == null || list.size()<1){
//				 list=iMNewsService.findMatchsByUserId(userId);
//			}
			log.info("infoMsg:--- 查找此时此刻列表结束");
				return res.success(list);
		} catch (Exception e) {
			log.error("errorMsg:--- 查找此时此刻列表失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	
	/**
	 * 查找心有灵犀列表
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Im.QUERY_HEART_CONSONANCE,method=RequestMethod.POST)
	@ResponseBody
	@Test
	public Response query_heart_consonance(@RequestParam("userId") Integer userId) {
		log.info("infoMsg:--- 查找心有灵犀列表开始");
		Response res = this.getReponse();
		try {
			List<HeartConsonanc> list=iMNewsService.findHertConsonanceByUserId(userId);
				log.info("infoMsg:--- 查找心有灵犀列表结束");
				return res.success(list);
		} catch (Exception e) {
			log.error("errorMsg:--- 查找心有灵犀列表失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	
	/**
	 * 查询匹配列表
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Im.QUERY_USER_MATCHS,method=RequestMethod.POST)
	@ResponseBody
	public Response query_user_matchs(@RequestParam("userId") Integer userId) {
		log.info("infoMsg:--- 查询匹配列表开始");
		Response res = this.getReponse();
		try {
			List<UserMatch> list=iMNewsService.findMatchsByUserId(userId);
				log.info("infoMsg:--- 查询匹配列表结束");
				return res.success(list);
		} catch (Exception e) {
			log.error("errorMsg:--- 查询匹配列表失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	/**
	 * 消息列表列表
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Im.QUERY_MESSAGES,method=RequestMethod.POST)
	@ResponseBody
	public Response query_messages(@RequestParam("userId") Integer userId) {
		log.info("infoMsg:--- 查询匹配列表开始");
		Response res = this.getReponse();
		try {
			
			Map<String, Object>  map=iMNewsService.findMessageByUserId(userId);
				log.info("infoMsg:--- 查询匹配列表结束");
				return res.success(map);
		} catch (Exception e) {
			log.error("errorMsg:--- 查询匹配列表失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	
	/**
	 * 阅读心有灵犀
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Im.READ_HRET,method=RequestMethod.POST)
	@ResponseBody
	public Response read_hert(@RequestParam("userId") Integer userId) {
		log.info("infoMsg:--- 阅读心有灵犀开始");
		Response res = this.getReponse();
		try {
			
			messageReadService.findMessageReadHert(userId);
				log.info("infoMsg:--- 阅读心有灵犀结束");
				return res.success("");
		} catch (Exception e) {
			log.error("errorMsg:--- 阅读心有灵犀失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	
	/**
	 * 阅读官方互动
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Im.READ_INTERACTION,method=RequestMethod.POST)
	@ResponseBody
	public Response read_message(@RequestParam("id") String id) {
		log.info("infoMsg:--- 阅读官方互动开始");
		Response res = this.getReponse();
		try {
			messageReadService.findMessageRead(id);
				log.info("infoMsg:--- 阅读官方互动结束");
				return res.success("OK");
		} catch (Exception e) {
			log.error("errorMsg:--- 阅读官方互动失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
//	@RequestMapping(value=Router.Im.READ_INTERACTION,method=RequestMethod.POST)
//	@ResponseBody
//	public Response read_message(Integer type,Integer userId,Integer another,String id) {
//		log.info("infoMsg:--- 阅读官方互动开始");
//		Response res = this.getReponse();
//		try {
//			iMNewsService.userBehaviorPushMessage(type,userId,2,id);
//				log.info("infoMsg:--- 阅读官方互动结束");
//				return res.success("OK");
//		} catch (Exception e) {
//			log.error("errorMsg:--- 阅读官方互动失败" + e.getMessage());
//			return res.failure(e.getMessage());
//		}
//	}
	/**
	 * 消息列表列表
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Im.READ_INTERACTION_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Response read_message_list(@RequestParam("userId") Integer userId,@RequestParam("tag")Integer tag) {
		log.info("infoMsg:--- 查询匹配列表开始");
		Response res = this.getReponse();
		try {
			List<MessageRead>  list=messageReadService.findMessageByUserId(userId,tag);
				log.info("infoMsg:--- 查询匹配列表结束");
				return res.success(list);
		} catch (Exception e) {
			log.error("errorMsg:--- 查询匹配列表失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	/**
	 * 删除消息
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Im.DELETE_MESSAGES,method=RequestMethod.POST)
	@ResponseBody
	public Response delete_messages(@RequestParam("userId") Integer userId,Integer tag) {
		log.info("infoMsg:--- 删除消息开始");
		Response res = this.getReponse();
		try {
			if(userId == null || tag == null){
				return res.failure("参数不完整");
			}
			messageReadService.deleteMessage(userId,tag);
				log.info("infoMsg:--- 删除消息结束");
				return res.success("OK");
		} catch (Exception e) {
			log.error("errorMsg:--- 删除消息失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	/**
	 * 消息设置
	 * 
	 * @returnv
	 */
	@RequestMapping(value=Router.Im.MESSAGE_SETTING,method=RequestMethod.POST)
	@ResponseBody
	public Response message_setting(@RequestParam("userId") Integer userId,String setting,Integer isDelete) {
		log.info("infoMsg:--- 消息设置开始");
		Response res = this.getReponse();
		try {
			if(userId == null || setting == null || setting.length() <1 || isDelete == null){
				return res.failure("参数不完整");
			}
			if(isDelete != null && isDelete.equals(0)){
				isDelete =1;
			}else if(isDelete != null && isDelete.equals(1)){
				isDelete =0;
			}
			appUserService.messageSetting(userId,setting,isDelete);
				log.info("infoMsg:--- 消息设置结束");
				return res.success("OK");
		} catch (Exception e) {
			log.error("errorMsg:--- 消息设置失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	/**
	 * 读取匹配消息
	 * 
	 * @returnv
	 */
	@RequestMapping(value=Router.Im.READ_MATCH_MESSAGE,method=RequestMethod.POST)
	@ResponseBody
	public Response read_match_message(Integer matchId) {
		log.info("infoMsg:--- 读取匹配消息开始");
		Response res = this.getReponse();
		try {
			if(matchId==null){
				return res.failure("参数不完整");
			}
			iMNewsService.readMatch(matchId);
				log.info("infoMsg:--- 读取匹配消息结束");
				return res.success("OK");
		} catch (Exception e) {
			log.error("errorMsg:--- 读取匹配消息失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
}
