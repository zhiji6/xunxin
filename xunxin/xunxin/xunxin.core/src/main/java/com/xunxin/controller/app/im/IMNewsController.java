package com.xunxin.controller.app.im;

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
import com.xunxin.service.app.IMNewsService;
import com.xunxin.service.app.MessageReadService;
import com.xunxin.vo.im.HeartConsonanc;
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
			List<UserMatch> list = iMNewsService.queryMoment(userId,range,age);
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
			
			List<Map<String,Object>>  map=iMNewsService.findMessageByUserId(userId);
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
	public Response read_Interaction(@RequestParam("id") Integer id) {
		log.info("infoMsg:--- 阅读官方互动开始");
		Response res = this.getReponse();
		try {
			iMNewsService.userBehaviorPushMessage(id,10,null,null);
//			messageReadService.findMessageRead(id);
				log.info("infoMsg:--- 阅读官方互动结束");
				return res.success("");
		} catch (Exception e) {
			log.error("errorMsg:--- 阅读官方互动失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}


}
