package com.xunxin.controller.app.user;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.xunxin.controller.system.BaseController;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.UserInfoDataService;
import com.xunxin.service.app.UserShieldsService;
import com.xunxin.service.app.user.UserFeedBackService;
import com.xunxin.util.app.OrderGeneratedUtils;
import com.xunxin.vo.im.UserShields;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.vo.user.UserFeedBackVO;
import com.xunxin.vo.user.UserInfoData;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月28日 -- 上午10:16:38
 * @Version 1.0
 * @Description		用户设置
 */
@Controller
@RequestMapping(value=Router.PATH+Router.Option.PATH)
public class XOptionController extends BaseController {

	private final static Logger log = Logger.getLogger(XOptionController.class);
	
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private UserFeedBackService userFeedBackService;
	@Autowired
	private UserShieldsService userShieldsService;
	@Autowired
	private UserInfoDataService userInfoDataService;
	
	/**
	 * 用户提交反馈信息
	 * 
	 * @param request
	 * @param userId
	 * @param feedContent
	 * @return
	 */
	@RequestMapping(value=Router.Option.USER_FEEDBACK,method=RequestMethod.POST)
	@ResponseBody
	public Response user_feedback(MultipartHttpServletRequest request,@RequestParam("userId") int userId,
			@RequestParam("feedContent") String feedContent) {
		log.info("infoMsg:--- 用户反馈开始");
		Response reponse = this.getReponse();
		CommonsMultipartFile multipartFile = null;
		BufferedInputStream is = null;  
		BufferedOutputStream out = null;  // 准备好一个输出的对象
		String urls = "";
		try {
			Iterator<String> itr =  request.getFileNames();
			while(itr.hasNext()){
		         String str = itr.next();
		         multipartFile = (CommonsMultipartFile)request.getFile(str);
		         String[] fileExts = {"jpg", "png"};
		         String fileName = multipartFile.getOriginalFilename();   //原文件名
		         String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		         if (Arrays.binarySearch(fileExts, fileExt) != -1) {
		        	 MultipartFile mpf = request.getFile(str);
		        	 InputStream inputStream = mpf.getInputStream();
		        	 is = new BufferedInputStream(inputStream);
		        	 String url = "D:\\programmeTools\\nginx-1.12.1\\photo\\feedback" + "/" + str + OrderGeneratedUtils.getOrderNo()  + "." + fileExt;
		        	 File newFile = new File(url);
		        	 if(is != null) {
		        		 out = new BufferedOutputStream(new FileOutputStream(newFile));  
		        		 byte[] buffer = new byte[1024];  
		        		 int len = -1;  
		        		 while ((len = is.read(buffer)) != -1) {  
		        			 out.write(buffer, 0, len);  
		        		 }
		        		 String name = str + OrderGeneratedUtils.getOrderNo() + "." + fileExt;
		        		 urls += name + "|";
		        	 }
		         }else {
					return reponse.failure("该文件类型不能够上传");
		         }
		    }
			String feedRemark = "";
			UserFeedBackVO feed = new UserFeedBackVO(feedContent, urls, new Date(), feedRemark, userId);
			userFeedBackService.save(feed);
			log.info("infoMsg:--- 用户反馈结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:--- 用户反馈失败" + e.getMessage());
			return reponse.failure(e.getMessage());
		}finally {
			if(is != null) {  
                try {  
                    is.close();  
                }catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
            if(out != null) {  
                try {  
                    out.close();  
                }catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
		}
	}
	
	
	/**
	 * 用户账号设置
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.Option.USER_ACCOUNT_SETUP,method=RequestMethod.POST)
	@ResponseBody
	public Response user_account_setup(@RequestParam("userId") int userId) {
		log.info("infoMsg:--- 获取用户账号设置信息开始");
		Response reponse = this.getReponse();
		PageData pd = new PageData<>();
		try {
			UserEntity user = appUserService.findById(userId);
			pd.put("phone", user.getPhone());
			if(StringUtils.isBlank(user.getPassword()) && StringUtils.trim(user.getPassword()).equals("")){
				pd.put("state", UserEntity.UNUSUAL);
				pd.put("havePwd", "未设置密码");
			}else {
				pd.put("state", UserEntity.NORMAL);
				pd.put("havePwd", "已有密码");
			}
			List<UserInfoData> infoList = userInfoDataService.findByUserId(userId);
			String[] openType = new String[] {"MicroBlog","Tencent","WeChat"};
			pd.put("MicroBlogState", UserEntity.NORMAL);
			pd.put("TencentState", UserEntity.NORMAL);
			pd.put("WeChatState", UserEntity.NORMAL);
			for(UserInfoData data : infoList) {
				if(data.getOpenType().equals(openType[0])) {
					pd.put("MicroBlogState", data.getOpenState());
				}
				if(data.getOpenType().equals(openType[1])) {
					pd.put("TencentState", data.getOpenState());
				}
				if(data.getOpenType().equals(openType[2])) {
					pd.put("WeChatState", data.getOpenState());
				}
			}
			log.info("infoMsg:--- 获取用户账号设置信息开始");
			return reponse.success(pd);
		} catch (Exception e) {
			log.info("errorMsg:{--- 获取用户账号设置信息失败：" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
	}
	
	
	
	
	/**
	 * 设置密码
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	@RequestMapping(value=Router.Option.SETUP_PASSWORD,method=RequestMethod.POST)
	@ResponseBody
	public Response setup_password(@RequestParam("usreId") int userId,@RequestParam("password") String password) {
		log.info("infoMsg:--- 设置密码开始");
		Response reponse = this.getReponse();
		try {
			appUserService.setup_password(userId,password);
			log.info("infoMsg:--- 设置密码结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:--- 设置密码失败" + e.getMessage());
			return reponse.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 获取用户的屏蔽人列表
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.Option.GET_BLOCK_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Response get_block_list (@RequestParam("userId") int userId) {
		log.info("infoMsg:--- 获取屏蔽人列表开始");
		Response reponse = this.getReponse();
		try {
			List<UserShields> findShields = userShieldsService.findShields(userId);
			
			log.info("infoMsg:--- 获取屏蔽人列表结束");
			return reponse.success(findShields);
		} catch (Exception e) {
			log.error("errorMsg:--- 获取屏蔽人列表失败");
			return reponse.failure();
		}
		
	}
	
	
	
	
	
	
}
