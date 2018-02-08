package com.xunxin.controller.system;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xunxin.service.SystemEntityService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.vo.condition.UserSearchCondition;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.web.api.bean.Route;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月30日 -- 上午10:39:58
 * @Version 1.0
 * @Description	用户管理
 */
@Controller
@RequestMapping(value=Route.PATH+Route.User.PATH)
public class UserManagerController extends BaseController{

	private static final Logger log = Logger.getLogger(UserManagerController.class);
	
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private SystemEntityService systemEntityService;
	
	
	
	
	/**
	 * 用户管理
	 * @return
	 */
	@RequestMapping(value=Route.User.USER_MANAGER,method=RequestMethod.GET)
	public ModelAndView user_manager() {
		log.info("进入平台用户管理界面");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("/jsp/xunxin/user/user_manager");
		return mv;
	}
	
	
	/**
	 * 活跃用户管理
	 * @return
	 */
	@RequestMapping(value=Route.User.USER_ONLINE,method=RequestMethod.GET)
	public ModelAndView user_online() {
		log.info("进入平台活跃用户管理界面");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("/jsp/xunxin/user/user_online");
		return mv;
	}
	
	
	/**
	 * 黑名单管理
	 * @return
	 */
	@RequestMapping(value=Route.User.USER_BLACK,method=RequestMethod.GET)
	public ModelAndView user_black() {
		log.info("进入平台用户黑名单管理界面");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("/jsp/xunxin/user/user_black");
		return mv;
	}
	
	
	/**
	 * 志愿者管理
	 * @return
	 */
	@RequestMapping(value=Route.User.VOLUTEER_MANAGER,method=RequestMethod.GET)
	public ModelAndView volunteer_manager() {
		log.info("进入平台志愿者用户管理界面");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("/jsp/xunxin/user/volunteer_manager");
		return mv;
	}
	
	/**
	 * 后台系统获取用户列表
	 */
	@RequestMapping(value=Route.User.USER_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Object user_list() {
	    log.info("infoMsg:---获取用户列表开始");
	    PageData pageData = this.getPageData();
	    try {
	        String phone = pageData.getString("phone");    //手机号
	        String nickName = pageData.getString("nickName");  //昵称
	        String gender = pageData.getString("gender");  //性别
	        UserSearchCondition condition = new UserSearchCondition(phone,nickName,gender);
	        List<UserEntity> userList = appUserService.getUserList(condition);
	        
	        PageData pagingPd = getPagingPd(userList);
	        
	        log.info("infoMsg:---获取用户列表结束");
	        return pagingPd;
        } catch (Exception e) {
            log.info("errorMsg:{---获取用户列表失败:" + e.getMessage() + "---}");
            return null;
        }
	    
	}
	
	
	/**
	 * 后台系统获取用户详情
	 */
	@RequestMapping(value=Route.User.USER_VIEW,method=RequestMethod.GET)
	public ModelAndView user_view() {
	    log.info("infoMsg:---获取用户详情开始");
	    PageData pageData = this.getPageData();
	    ModelAndView mv = new ModelAndView();
	    PageData pd = new PageData<>();
	    try {
	        int userId = Integer.parseInt(pageData.getString("id"));    //用户id
	        UserEntity user = appUserService.findById(userId);
	        
	        pd.put("phone", user.getPhone());
	        pd.put("name", user.getName());
            pd.put("nickName", user.getNickName());
            pd.put("gender", user.getGender());
            pd.put("age", user.getAge());
            pd.put("sexualOrientation", user.getSexualOrientation());
            pd.put("address", user.getAddress());
            pd.put("height", user.getHeight());
            pd.put("profession", user.getProfession());
            pd.put("trade", user.getTrade());
            pd.put("position", user.getPosition());
            pd.put("income", user.getIncome());
            pd.put("education", user.getEducation());
            pd.put("williamsCollege", user.getWilliamsCollege());
            pd.put("relationshipStatus", user.getRelationshipStatus());
            pd.put("makeFriendWay", user.getMakeFriendWay());
            
            pd.put("introduce", user.getIntroduce());
            pd.put("housingConditions", user.getHousingConditions());
            pd.put("trafficTools", user.getTrafficTools());
            pd.put("weight", user.getWeight());
            pd.put("nation", user.getNation());
            //国籍
            if(user.getNationality() != null && !StringUtils.trim(user.getNationality()).equals("")) {
                String country =  systemEntityService.findCountryById(Integer.parseInt(user.getNationality())).getName_Chinese();
                if(country != null) {
                    pd.put("nationality", country);
                }
            }
            //籍贯
            if(user.getNativePlace() != null && !StringUtils.trim(user.getNativePlace()).equals("")) {
                String[] addrs = user.getNativePlace().replace("[","").replace("]", "").trim().split(",");
                String nativePlace = "";
                for(String addr : addrs) {
                    nativePlace +=  systemEntityService.findNameByCode(addr).getName();
                }
                pd.put("nativePlace", nativePlace);
            }
            //户籍
            if(user.getCensusRegister() != null && !StringUtils.trim(user.getCensusRegister()).equals("")) {
                String[] censusRegisters = user.getCensusRegister().replace("[","").replace("]", "").trim().split(",");
                String censusRegister = "";
                for(String addr : censusRegisters) {
                    censusRegister +=  systemEntityService.findNameByCode(addr).getName();
                }
                pd.put("censusRegister", censusRegister);
            }
            pd.put("familyOrder", user.getFamilyOrder());
            pd.put("haveChild", user.getHaveChild());
            pd.put("religiousBelief", user.getReligiousBelief());
            pd.put("constellation", user.getConstellation());
            pd.put("zodiac", user.getZodiac());
            pd.put("blood", user.getBlood());
	        
            mv.setViewName("/jsp/xunxin/user/user_view");
            mv.addObject("pd", pd);
            
	        log.info("infoMsg:---获取用户详情结束");
	        return mv;
	    } catch (Exception e) {
	        log.info("errorMsg:{---获取用户详情失败:" + e.getMessage() + "---}");
	        return new ModelAndView("error");
	    }
	    
	}
	
	
	
	
}
