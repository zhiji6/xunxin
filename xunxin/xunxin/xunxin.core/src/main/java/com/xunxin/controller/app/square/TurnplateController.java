package com.xunxin.controller.app.square;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import com.xunxin.service.app.square.TurnplateAwardRecordService;
import com.xunxin.service.app.square.TurnplateAwardService;
import com.xunxin.vo.square.TurnplateAwardRecord;
import com.xunxin.vo.square.TurnplateAwardVO;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月27日 -- 下午5:10:25
 * @Version 1.0
 * @Description		转盘游戏
 */
@Controller
@RequestMapping(value=Router.PATH+Router.Turnplate.PATH)
public class TurnplateController extends BaseController{

	private final static Logger log = Logger.getLogger(TurnplateController.class);
	
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private TurnplateAwardRecordService turnplateAwardRecordService;
	@Autowired
	private TurnplateAwardService turnplateAwardService;
	
	/**
	 * 转盘游戏开奖结果(用户)
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.Turnplate.TOUCH_TURNPLATE,method=RequestMethod.POST)
	@ResponseBody
	public Response touch_turnplate(@RequestParam("userId") int userId) {
		log.info("infoMsg:--- 转盘游戏开始");
		Response reponse = this.getReponse();
		int result = 0;
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("userId").is(userId));
			query.with(new Sort(new Order(Direction.DESC, "createTime")));
			List<TurnplateAwardRecord> recordList = turnplateAwardRecordService.find(query);
			int arr[] = new int[]{1,1,1,1,2,2,3,3,5,10}; 
			if((recordList.size()) / 10 < 1) {
				Random random = new java.util.Random();// 定义随机类
				int dom = random.nextInt(10);// 返回[0,10)集合中的整数，注意不包括10
				result = arr[dom];
			}else {
				Random random = new java.util.Random();// 定义随机类
				int dom = random.nextInt(10);// 返回[0,10)集合中的整数，注意不包括10
				result = arr[dom];
			}
			//保存游戏获奖记录
			TurnplateAwardRecord record = new TurnplateAwardRecord();
			TurnplateAwardVO vo = turnplateAwardService.findByState(result);
			record.setAwardLevel(vo.getAwardLevel());
			record.setAwardContent(vo.getAwardContent());
			record.setCount(recordList.size() + 1);
			record.setUserId(userId);
			turnplateAwardRecordService.save(record);
			
			//转盘游戏 一次抵扣10积分
			UserEntity user = appUserService.findById(userId);
			int exp = user.getUserExp() - 10;
			if(exp < 10){
				return reponse.failure("积分不足");
			}
			appUserService.user_exp_change(userId,exp);
			
			return reponse.success(result);
		} catch (Exception e) {
			log.error("errorMsg:--- 转盘游戏失败");
			return reponse.failure();
		}
	}
	
	
	/**
	 * 转盘游戏开奖结果(列表)
	 * 
	 * "$lt", "$lte", "$gt", "$gte", "$ne"就是全部的比较操作符，对应于"<", "<=", ">", ">=","!="。
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.Turnplate.TURNPLATE_AWARD_RECORD_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Response turnplate_award_record_list() {
		log.info("infoMsg:--- 获取转盘游戏开奖结果列表开始");
		Response reponse = this.getReponse();
		List<PageData> pdList = new ArrayList<>();
		try {
			Query query = new Query();
			query.with(new Sort(new Order(Direction.DESC, "createTime")));
//			query.addCriteria(Criteria.where("awardLevel").ne("鼓励奖"));
			List<TurnplateAwardRecord> recordList = turnplateAwardRecordService.find(query);
			for(TurnplateAwardRecord record : recordList) {
				PageData pd = new PageData<>();
				int userId = record.getUserId();
				UserEntity user = appUserService.findById(userId);
				String nickName = user.getNickName();
				TurnplateAwardVO vo = turnplateAwardService.findByLevel(record.getAwardLevel());
				int awardState = vo.getAwardState();
				if(awardState > 1) {
					int exp = awardState * 10;
					pd.put("nickName", nickName);
					pd.put("userExp", exp);
					pdList.add(pd);
				}
			}
			
			log.info("infoMsg:--- 获取转盘游戏开奖结果列表开始");
			return reponse.success(pdList);
		} catch (Exception e) {
			log.error("errorMsg:{--- 获取转盘游戏开奖结果列表失败:" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
	}
	
	
	
	
	
	
}
