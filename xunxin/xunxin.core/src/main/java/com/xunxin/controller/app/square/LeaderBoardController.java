package com.xunxin.controller.app.square;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xunxin.config.JedisUtil;
import com.xunxin.config.RedisRepository;
import com.xunxin.controller.system.BaseController;
import com.xunxin.vo.sys.PageData;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月28日 -- 上午11:01:19
 * @Version 1.0
 * @Description		排行榜
 */
@Controller
@RequestMapping(value=Router.PATH+Router.LeaderBoard.PATH)
public class LeaderBoardController extends BaseController {

	private final static Logger log = Logger.getLogger(LeaderBoardController.class);
	
	/**
	 * 获取每日排行榜
	 * @return
	 */
	@RequestMapping(value=Router.LeaderBoard.DAY_LEADER_BOARD,method=RequestMethod.POST)
	@ResponseBody
	public Response day_leader_board() {
		log.info("infoMsg:--- 获取每日排行榜开始");
		Response reponse = this.getReponse();
		List<PageData> pdList = new ArrayList<>();
		try {
			//先从Redis中get 
		    JedisUtil jedisUtil= JedisUtil.getInstance();    
	        JedisUtil.Lists Lists=jedisUtil.new Lists();  
	        List<String> lrange = Lists.lrange("leader_board_day", 0, 10);
	        for(String user : lrange) {
	            PageData pd = new PageData<>();
	            JSONObject object = JSON.parseObject(user);
	            pd.put("nickName", (String)object.get("nickName"));
	            pd.put("gender", (String)object.get("gender"));
	            pd.put("ID", (String)object.get("ID"));
	            pd.put("count", object.get("count"));
	            pd.put("order", object.get("order"));
	            pdList.add(pd);
	        }
			log.info("infoMsg:--- 获取每日排行榜结束");
			return reponse.success(pdList);
		} catch (Exception e) {
			log.error("errorMsg:--- 获取每日排行榜失败" + e.getMessage());
			return reponse.failure(e.getMessage());
		}
	}
	
	/**
	 * 获取每周排行榜
	 * @return
	 */
	@RequestMapping(value=Router.LeaderBoard.WEEK_LEADER_BOARD,method=RequestMethod.POST)
	@ResponseBody
	public Response week_leader_board() {
		log.info("infoMsg:--- 获取每周排行榜开始");
		Response reponse = this.getReponse();
        List<PageData> weekList = new ArrayList<>();
        try {
            //先从Redis中get 
            JedisUtil jedisUtil= JedisUtil.getInstance();    
            JedisUtil.Lists Lists=jedisUtil.new Lists();  
            List<String> lrange = Lists.lrange("leader_board_day", 0, 10);
            for(String user : lrange) {
                PageData pd = new PageData<>();
                JSONObject object = JSON.parseObject(user);
                pd.put("nickName", (String)object.get("nickName"));
                pd.put("gender", (String)object.get("gender"));
                pd.put("ID", (String)object.get("ID"));
                pd.put("count", object.get("count"));
                pd.put("order", object.get("order"));
                weekList.add(pd);
            }
			return reponse.success(weekList);
		} catch (Exception e) {
			log.error("errorMsg:--- 获取每周排行榜失败" + e.getMessage());
			return reponse.failure(e.getMessage());
		}
	}
	
	/**
	 * 获取每月排行榜
	 * @return
	 */
	@RequestMapping(value=Router.LeaderBoard.MONTH_LEADER_BOARD,method=RequestMethod.POST)
	@ResponseBody
	public Response month_leader_board() {
		log.info("infoMsg:--- 获取每月排行榜开始");
		Response reponse = this.getReponse();
        List<PageData> monthList = new ArrayList<>();
        try {
            //先从Redis中get 
            JedisUtil jedisUtil= JedisUtil.getInstance();    
            JedisUtil.Lists Lists=jedisUtil.new Lists();  
            List<String> lrange = Lists.lrange("leader_board_day", 0, 10);
            for(String user : lrange) {
                PageData pd = new PageData<>();
                JSONObject object = JSON.parseObject(user);
                pd.put("nickName", (String)object.get("nickName"));
                pd.put("gender", (String)object.get("gender"));
                pd.put("ID", (String)object.get("ID"));
                pd.put("count", object.get("count"));
                pd.put("order", object.get("order"));
                monthList.add(pd);
            }
			log.info("infoMsg:--- 获取每月排行榜结束");
			return reponse.success(monthList);
		} catch (Exception e) {
			log.error("errorMsg:--- 获取每月排行榜失败" + e.getMessage());
			return reponse.failure(e.getMessage());
		}
	}
	
	
	
	
	
}
