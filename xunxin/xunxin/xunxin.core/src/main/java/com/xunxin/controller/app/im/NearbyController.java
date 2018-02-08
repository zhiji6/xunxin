package com.xunxin.controller.app.im;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunxin.controller.app.comment.CommentController;
import com.xunxin.controller.system.BaseController;
import com.xunxin.service.app.CommentService;
import com.xunxin.service.app.NearLocationService;
import com.xunxin.service.app.UserFriendsService;
import com.xunxin.vo.im.NearLocation;
import com.xunxin.vo.im.UserFriends;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;


/**
 * 
 * @Author gyf
 * @Compile 2017年11月20日
 * @Version 1.0
 * @Description	附近的人
 */
@Controller
@RequestMapping(value=Router.PATH+Router.Im.PATH)
public class NearbyController extends BaseController{
	
	private static final Logger log = Logger.getLogger(NearbyController.class);
	
	@Autowired 
	private NearLocationService nearLocationService;
	@Autowired 
	private UserFriendsService userFriendsService;
	/**
	 * 更新或保存用户位置信息
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Im.SAVE_LOCATION,method=RequestMethod.POST)
	@ResponseBody
	public Response save_location(@RequestParam("userId") Integer userId,@RequestParam("longitude") Double longitude,@RequestParam("latitude") Double latitude,
			String cityCode) {
		log.info("infoMsg:--- 更新或保存用户位置信息开始");
		Response res = this.getReponse();
		try {
			 nearLocationService.saveLocation(userId,longitude,latitude,cityCode);
				log.info("infoMsg:--- 更新或保存用户位置信息结束");
				return res.success("");
		} catch (Exception e) {
			log.error("errorMsg:--- 更新或保存用户位置信息失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	
	/**
	 * 查找好友列表
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Im.QUERY_FRIENDS,method=RequestMethod.POST)
	@ResponseBody
	public Response query_frinds(@RequestParam("userId") Integer userId) {
		log.info("infoMsg:--- 查找好友列表开始");
		Response res = this.getReponse();
		try {

			List<UserFriends> list = userFriendsService.findFriends(userId);
				log.info("infoMsg:--- 查找好友列表结束");
				return res.success(list);
		} catch (Exception e) {
			log.error("errorMsg:--- 查找好友列表失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	

}
