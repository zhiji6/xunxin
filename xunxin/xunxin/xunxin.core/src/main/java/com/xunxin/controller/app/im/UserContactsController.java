package com.xunxin.controller.app.im;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunxin.config.RedisRepository;
import com.xunxin.controller.system.BaseController;
import com.xunxin.service.app.SysDictService;
import com.xunxin.service.app.UserFriendsService;
import com.xunxin.service.app.UserReportsService;
import com.xunxin.service.app.UserShieldsService;
import com.xunxin.util.JacksonUtil;
import com.xunxin.vo.im.UserFriends;
import com.xunxin.vo.im.UserReports;
import com.xunxin.vo.im.UserShields;
import com.xunxin.vo.qa.QASection;
import com.xunxin.vo.qa.SysDict;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;
import com.xunxin.util.app.jsonInterceptor.JSON;
import com.xunxin.util.app.jsonInterceptor.JSONS;

/**
 * 
 * @Author gyf
 * @Compile 2017年11月22日
 * @Version 1.0
 * @Description	联系人匹配
 */
@Controller
@RequestMapping(value=Router.PATH+Router.Contacts.PATH)
public class UserContactsController  extends BaseController{
	
	private static final Logger log = Logger.getLogger(UserContactsController.class);

	@Autowired 
	private UserShieldsService userShieldsService;
	
	@Autowired 
	private UserReportsService userReportsService;
	
	@Autowired
	private UserFriendsService userFriendsService;
	
	@Autowired
	private SysDictService sysDictService;
	/**
	 * 查找屏蔽的人列表
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Contacts.QUERY_SHIELDS,method=RequestMethod.POST)
	@ResponseBody
	public Response query_shields(@RequestParam("userId") Integer userId) {
		log.info("infoMsg:--- 查找屏蔽的列表开始");
		Response res = this.getReponse();
		try {

			List<UserShields> list = userShieldsService.findShields(userId);
				log.info("infoMsg:--- 查找屏蔽的列表结束");
				return res.success(list);
		} catch (Exception e) {
			log.error("errorMsg:--- 查找屏蔽的列表失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	/**
	 * 查找举报的人列表
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Contacts.QUERY_REPORTS,method=RequestMethod.POST)
	@ResponseBody
	public Response query_reports(@RequestParam("userId") Integer userId) {
		log.info("infoMsg:--- 查找举报的人列表开始");
		Response res = this.getReponse();
		try {

			List<UserReports> list = userReportsService.findShields(userId);
				log.info("infoMsg:--- 查查找举报的人列表结束");
				return res.success(list);
		} catch (Exception e) {
			log.error("errorMsg:--- 查查找举报的人列表失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	
	/**
	 * 用户屏蔽的人保存
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Contacts.SAVE_SHIELD,method=RequestMethod.POST)
	@ResponseBody
	public Response save_shield( Integer userId,Integer anotherId,Integer isdelete) {
		log.info("infoMsg:--- 用户屏蔽的人保存开始");
		Response res = this.getReponse();
		try {
			
			if(userId == null || anotherId == null || isdelete == null){
				return res.failure("参数不完整");
			}
			
			int insert = userShieldsService.save(userId,anotherId,isdelete);
			
			log.info("infoMsg:--- 用户屏蔽的人保存结束");
			if(insert>0){
				return res.success("success");
			}else{
				return res.failure("保存失败");
			}
		} catch (Exception e) {
			log.error("errorMsg:--- 用户屏蔽的人保存失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	
	/**
	 * 用户举报的人保存
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Contacts.SAVE_REPORT,method=RequestMethod.POST)
	@ResponseBody
	public Response save_report( Integer userId,Integer anotherId,Integer isdelete,String reportObjectId,String content,String reportType,Integer value) {
		log.info("infoMsg:--- 用户举报的人保存开始");
		Response res = this.getReponse();
		try {
			
			if(userId == null  || isdelete == null || reportType == null){
				return res.failure("参数不完整");
			}
			int insert = userReportsService.save(userId,anotherId,isdelete,reportObjectId,content,reportType,value);
			
			log.info("infoMsg:--- 用户举报的人保存结束");
			if(insert>0){
				return res.success("success");
			}else{
				return res.failure("保存失败");
			}
		} catch (Exception e) {
			log.error("errorMsg:--- 用户举报的人保存失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	
	/**
	 * 查看发送聊天和自发像权限
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Contacts.QUERY_CHAT_PERMISSIONS,method=RequestMethod.POST)
	@ResponseBody
	public Response query_chat_permissions( Integer userId) {
		log.info("infoMsg:--- 查看发送聊天和自发像权限开始");
		Response res = this.getReponse();
		try {
			
			if(userId == null){
				return res.failure("参数不完整");
			}
			
			Map map = null;
				return res.success(map);
	
			
		} catch (Exception e) {
			log.error("errorMsg:--- 查看发送聊天和自发像权限存失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	
	/**
	 * 单个注册环信用户账号
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Contacts.SAVE_SINGLE_OPEN_REGISTRATION,method=RequestMethod.POST)
	@ResponseBody
	public Response save_single_open_registration( Integer userId) {
		log.info("infoMsg:--- 单个注册环信用户账号开始");
		Response res = this.getReponse();
		try {
			if(userId == null){
				return res.failure("参数不完整");
			}
			userFriendsService.singleregistration(userId);
			return res.success("OK");
		} catch (Exception e) {
			log.error("errorMsg:--- 单个注册环信用户账号失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	
	/**
	 * 查找字典列列表
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Contacts.QUERY_DICT,method=RequestMethod.POST)
	@ResponseBody
	public Response query_dict( String type) {
		log.info("infoMsg:--- 查找字典列列表开始");
		Response res = this.getReponse();
		try {
			boolean flag = RedisRepository.exists(type);
			List<SysDict> types = null;
			if(!flag){
				types = sysDictService.findDics(type);
				RedisRepository.setList(type, types);
			}else{
				types = RedisRepository.getListEntity(type, SysDict.class);
				if(types == null || types.size() <1 ){
					 types = sysDictService.findDics(type);
						RedisRepository.setList(type, types);
				}
			}
				log.info("infoMsg:--- 查找字典列列表结束");
				return res.success(types);
		} catch (Exception e) {
			log.error("errorMsg:--- 查找字典列列表失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	
	/**
	 * 查找好友互动标识
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Contacts.QUERY_FRIEND_INTERACTION,method=RequestMethod.POST)
	@ResponseBody
	public Response query_friend_interaction(Integer userId,Integer friendId) {
		log.info("infoMsg:--- 查找好友互动标识开始");
		Response res = this.getReponse();
		try {

			Integer time = userFriendsService.queryChatTime(userId,friendId,8);
			
			log.info("infoMsg:--- 查找好友互动标识结束");
			if(time == null || time <1){
				return res.success("");
			}else if(time >0 && time <=4){
				return res.success("E");
			}else if(time >4 && time <=7){
				return res.success("Q");
			}else if(time >7 ){
				return res.success("T");
			}
			return res.success("");
		} catch (Exception e) {
			log.error("errorMsg:--- 查找好友互动标识失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	/**
	 * 查找我的关注
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Contacts.QUERY_MY_ATTENTIONS,method=RequestMethod.POST)
	@ResponseBody
	@JSON(type = UserFriends.class  , include="id")
	public Response query_my_attentions(Integer userId) {
		log.info("infoMsg:--- 查找我的关注开始");
		Response res = this.getReponse();
		try {
			List<UserFriends> list = userFriendsService.findAttentionsMe(userId);
			log.info("infoMsg:--- 查找我的关注结束");
			return res.success(list);
		} catch (Exception e) {
			log.error("errorMsg:--- 查找我的关注失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	/**
	 * 查找关注我的人
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Contacts.QUERY_ATTENTIONS_ME,method=RequestMethod.POST)
	@ResponseBody
	public Response query_attentions_me(Integer userId) {
		log.info("infoMsg:--- 查找关注我的人开始");
		Response res = this.getReponse();
		try {
			List<UserFriends> list = userFriendsService.findAttentionsToMe(userId);
			log.info("infoMsg:--- 查找关注我的人结束");
			return res.success(list);
		} catch (Exception e) {
			log.error("errorMsg:--- 查找关注我的人失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
}
