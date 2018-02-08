package com.xunxin.controller.app.circle;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mongodb.framework.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.xunxin.config.RedisRepository;
import com.xunxin.controller.system.BaseController;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.ArecordTestService;
import com.xunxin.service.app.EmpathyCircleService;
import com.xunxin.service.app.circle.CircleCommentService;
import com.xunxin.service.app.circle.CircleLikeService;
import com.xunxin.util.SpringContextUtil;
import com.xunxin.util.app.OrderGeneratedUtils;
import com.xunxin.util.app.jsonInterceptor.JSON;
import com.xunxin.vo.circle.CircleComment;
import com.xunxin.vo.circle.EmpathyCircle;
import com.xunxin.vo.qa.ArecordTest;
import com.xunxin.vo.qa.SysDict;
import com.xunxin.vo.user.UserAlbum;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;

/**
 * 
 * Copyright @ 2017 Xunxin Network Technology Co. Ltd.
 * 
 * @Author yufeng
 * @Compile 2017年12月11日 -- 上午10:11:02
 * @Version 1.0
 * @Description   共情圈管理
 */
@Controller
@RequestMapping(value = Router.PATH + Router.Circle.PATH)
public class EmpathyCircleController extends BaseController{
	
	private static final Logger log = Logger.getLogger(EmpathyCircleController.class);
	
	@Autowired
	private EmpathyCircleService empathyCircleService;
	@Autowired
	private CircleCommentService circleCommentService;
	@Autowired
	private CircleLikeService circleLikeService;
	@Autowired
	private AppUserService appUserService;

	/**
	 * 共情圈动态添加保存
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value=Router.Circle.SAVE_CIRCLE,method=RequestMethod.POST)
	@ResponseBody
	public Response save_circle(String content, Integer userId,String address,Integer isVague) {
		log.info("infoMsg:--- 共情圈动态添加开始");
		Response res = this.getReponse();

		CommonsMultipartFile multipartFile = null;
		BufferedInputStream is = null;  
		BufferedOutputStream out = null;  // 准备好一个输出的对象
		List<String> list = new ArrayList<String>();
		try {
//			Iterator<String> itr =  request.getFileNames();
//			while(itr.hasNext()){
//		         String str = itr.next();
//		         multipartFile = (CommonsMultipartFile)request.getFile(str);
//		         String[] fileExts = {"jpg", "png"};
//		         String fileName = multipartFile.getOriginalFilename();   //原文件名
//		         String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
//		         if (Arrays.binarySearch(fileExts, fileExt) != -1) {
//		        	 MultipartFile mpf = request.getFile(str);
//		        	 InputStream inputStream = mpf.getInputStream();
//		        	 is = new BufferedInputStream(inputStream);
//		        	 String url = "D:\\programmeTools\\nginx-1.12.1\\photo\\album" + "/" + str + OrderGeneratedUtils.getOrderNo()  + "." + fileExt;
//		        	 File newFile = new File(url);
//		        	 if(is != null) {
//		        		 out = new BufferedOutputStream(new FileOutputStream(newFile));  
//		        		 byte[] buffer = new byte[1024];  
//		        		 int len = -1;  
//		        		 while ((len = is.read(buffer)) != -1) {  
//		        			 out.write(buffer, 0, len);  
//		        		 }
//		        		 String name = str + OrderGeneratedUtils.getOrderNo() + "." + fileExt;
//		        		 list.add(url);
//		        	 }
//		         }else {
//					return res.failure("该文件类型不能够上传");
//		         }
//		    }
			list.add("D:\\programmeTools\\nginx-1.12.1\\photo\\album");
			list.add("D:\\programmeTools\\nginx-1.12.1\\photo\\album");
			list.add("D:\\programmeTools\\nginx-1.12.1\\photo\\album");
			list.add("D:\\programmeTools\\nginx-1.12.1\\photo\\album");
			list.add("D:\\programmeTools\\nginx-1.12.1\\photo\\album");
			list.add("D:\\programmeTools\\nginx-1.12.1\\photo\\album");
			if(list == null ){
				return res.failure("图片上传失败");
			}else if(list.size()>6){
				return res.failure("图片大于6张");
			}
			
			if(userId == null || content == null || "".equals(content.trim()) || address == null || "".equals(address.trim()) || isVague == null){
				return res.failure("参数不完整");
			}
			int count = 0;
			String regEx = "[\\u4e00-\\u9fa5]";
	        String term = content.replaceAll(regEx, "xx");
	        count = term.length()-content.length();
	        if(count>1000){
	        	return res.failure("评论字数大于1000");
	        }
			EmpathyCircle cicle = new EmpathyCircle();
					cicle.setUserId(userId);
					cicle.setAddress(address);
					cicle.setContent(content);
					cicle.setIsVague(isVague);
					cicle.setPhotos(list.toArray(new String[0]));
					cicle.setCreateTime(new Date());
					cicle.setUpdateDate(new Date());
			empathyCircleService.insert(cicle);
			log.info("infoMsg:--- 共情圈动态添加结束");
			return res.success("");
		} catch (Exception e) {
			log.error("errorMsg:--- 用户相册上传失败" + e.getMessage());
			return res.failure(e.getMessage());
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
	 * 用户填写评论
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Circle.SAVE_CIRCLE_COMMENT,method=RequestMethod.POST)
	@ResponseBody
	public Response save_circle_comment( Integer userId, String empathyCircleId, String content,Integer replyUserId) {
		log.info("infoMsg:--- 保存用户评论开始");
		Response res = this.getReponse();
		try {
			if(Integer.valueOf(userId) == null || empathyCircleId== null || "".equals(empathyCircleId) || content == null || "".equals(content)){
				return res.failure("参数不完整");
			}
			if(userId.equals(replyUserId)){
				return res.failure("不能给自己回复");
			}
			String phone = appUserService.findUserPhoneByUserId(userId);
			boolean flag = RedisRepository.exists("limit"+phone);
			if(!flag){
				boolean frequency = circleCommentService.isFrequency(userId);
				if(!frequency){
					circleCommentService.saveCircleComment(userId,empathyCircleId,content,replyUserId);
					log.info("infoMsg:--- 用户保存评论结束");
					return res.success("success");
				}else{
					Map<String, Object> map = new HashMap<>();
					Date now = new Date();
					map.put("limit", 1);
					map.put("time", new Date(now .getTime() + 60000));
					RedisRepository.setMap("limit"+phone, map);
					return res.failure("您的回复太频繁");
				}
			}else{
				Map<String, Object> map = RedisRepository.getMap("limit"+phone);
				Date date =new Date();
				date.setTime(Long.parseLong((String) map.get("time")));
				Date now = new Date();
				if(date.getTime()>now.getTime()){
					return res.failure("您被限制回复"+map.get("limit")+"分钟");
				}else{
					boolean frequency = circleCommentService.isFrequency(userId);
					if(!frequency){
						circleCommentService.saveCircleComment(userId,empathyCircleId,content,replyUserId);
						log.info("infoMsg:--- 用户保存评论结束");
						return res.success("success");
					}else{
						Integer limit = Integer.valueOf((String) map.get("limit"));
						Date datelimt = null;
						Integer limitNow = null;
						if(limit.equals(1)){
							limitNow = 30;
							datelimt = new Date(new Date().getTime()+60000*30);
						}
						else if(limit.equals(30)){
							limitNow = 60;
							datelimt = new Date(new Date().getTime()+60000*30*2);
						}else if(limit.equals(60)){
							limitNow = 60;
							datelimt = new Date(new Date().getTime()+60000*30*2);
						}else{
							limitNow = 1;
							datelimt = new Date(new Date().getTime()+60000);
						}
						map.put("limit", limitNow);
						map.put("time", datelimt);
						RedisRepository.del("limit"+phone);
						RedisRepository.setMap("limit"+phone, map);
						
						return res.failure("您的回复太频繁");
					}
				}
			}
			
		} catch (Exception e) {
			log.error("errorMsg:--- 用户保存评论失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	
	/**
	 * 用户评论删除
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Circle.DELETE_CIRCLE_COMMENT,method=RequestMethod.POST)
	@ResponseBody
	public Response delete_circle_comment(@RequestParam("id") String id) {
		log.info("infoMsg:--- 用户评论删除开始");
		Response res = this.getReponse();
		try {
			
			circleCommentService.deleteCircleComment(id);
			
			log.info("infoMsg:--- 用户评论删除结束");
			return res.success("success");
		} catch (Exception e) {
			log.error("errorMsg:--- 用户评论删除失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	/**
	 * 用户共情圈点赞
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Circle.SAVE_GIVE_UP_CIRCLE,method=RequestMethod.POST)
	@ResponseBody
	public Response save_give_up_circle(String empathyCircleId,  Integer userId, Integer giveUp) {
		log.info("infoMsg:--- 点赞开始");
		Response res = this.getReponse();
		try {
			if(userId == null || empathyCircleId == null || giveUp == null){
				log.error("errorMsg:--- 参数不完整");
				return res.failure("参数不完整");
			}
			circleLikeService.saveOrupdate(empathyCircleId,userId,giveUp);
			log.info("infoMsg:--- 点赞结束");
			return res.success("suuess");
		} catch (Exception e) {
			log.error("errorMsg:--- 点赞失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	/**
	 * 查看我的共情圈
	 * 
	 * @return
	 */
	
	@RequestMapping(value=Router.Circle.QUERY_MY_CIRCLE,method=RequestMethod.POST)
	@ResponseBody
	@JSON(type=EmpathyCircle.class,filter="updateDate")
	public Response query_my_circle(Integer userId,Integer pageNo,Integer pageSize) {
		log.info("infoMsg:--- 查看我的共情圈开始");
		Response res = this.getReponse();
		try {
			if(userId == null ){
				log.error("errorMsg:--- 参数不完整");
				return res.failure("参数不完整");
			}
			if(pageNo == null || pageNo.equals("")){
				pageNo = 0;
			}
			if(pageSize == null || pageSize.equals("")){
				pageSize = 20;
			}
			Pagination<EmpathyCircle> list = empathyCircleService.queryMyCircle(userId, pageNo, pageSize);
			log.info("infoMsg:--- 查看我的共情圈结束");
			return res.success(list);
		} catch (Exception e) {
			log.error("errorMsg:--- 查看我的共情圈失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	/**
	 * 查看共情圈列表
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Circle.QUERY_CIRCLES,method=RequestMethod.POST)
	@ResponseBody
	public Response query_circles(Integer userId,Integer pageNo,Integer pageSize) {
		log.info("infoMsg:--- 查看共情圈列表开始");
		Response res = this.getReponse();
		try {
			if(userId == null ){
				log.error("errorMsg:--- 参数不完整");
				return res.failure("参数不完整");
			}
			if(pageNo == null || pageNo.equals("")){
				pageNo = 0;
			}
			if(pageSize == null || pageSize.equals("")){
				pageSize = 20;
			}
			
			Pagination<EmpathyCircle> list = empathyCircleService.queryCirclesPlanB(userId, pageNo, pageSize);
			log.info("infoMsg:--- 查看共情圈列表结束");
			return res.success(list);
		} catch (Exception e) {
			log.error("errorMsg:--- 查看共情圈列表失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	/**
	 * 查看共情圈照片权限
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Circle.QUERY_CIRCLE_PHOTO,method=RequestMethod.POST)
	@ResponseBody
	public Response query_cicle_photo(Integer userId) {
		log.info("infoMsg:--- 查看共情圈照片权限开始");
		Response res = this.getReponse();
		try {
			if(userId == null ){
				log.error("errorMsg:--- 参数不完整");
				return res.failure("参数不完整");
			}
			EmpathyCircleService bean = (EmpathyCircleService) SpringContextUtil.getBean("empathyCircleService");
			Boolean flag = empathyCircleService.queryJurisdiction(userId);
			if(flag){
				
				log.info("infoMsg:--- 查看共情圈照片权限结束");
				empathyCircleService.insertSelfPortrait(userId);
				return res.success("OK");
			}else{
				log.info("infoMsg:--- 查看共情圈照片权限结束");
				return res.failure("今天免费查看共情圈次数已不够");
			}
			
		} catch (Exception e) {
			log.error("errorMsg:--- 查看共情圈照片权限失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
