package com.xunxin.controller.app.user;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xunxin.constants.ExpConstants;
import com.xunxin.controller.app.message.MessTypeFactory;
import com.xunxin.controller.system.BaseController;
import com.xunxin.service.AdminService;
import com.xunxin.service.ArecordService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.CommentService;
import com.xunxin.service.app.QASectionService;
import com.xunxin.service.app.UserAlbumService;
import com.xunxin.service.app.UserFriendsService;
import com.xunxin.service.app.IMNewsService;
import com.xunxin.service.app.UserInfoDataService;
import com.xunxin.service.app.UserShieldsService;
import com.xunxin.service.app.XunxinAuditInformationRecordService;
import com.xunxin.service.app.qa.AnswerService;
import com.xunxin.service.app.qa.QACollectionRecordService;
import com.xunxin.service.app.qa.QAttentionRecordService;
import com.xunxin.service.app.qa.QuestionService;
import com.xunxin.service.app.qa.XTagLibraryService;
import com.xunxin.service.app.square.ExposureCommentRecordService;
import com.xunxin.service.app.square.ExposureCommentThumbRecordService;
import com.xunxin.service.app.square.QAImageUrlRecordService;
import com.xunxin.service.app.square.ShakeItOffRecordService;
import com.xunxin.service.app.square.ThrowHydrangeaRecordService;
import com.xunxin.service.app.square.TurnplateAwardRecordService;
import com.xunxin.service.app.square.UserAccessLinkedRecordService;
import com.xunxin.service.app.square.UserBrushAgainstRecordService;
import com.xunxin.service.app.user.DynamicThumbRecordService;
import com.xunxin.service.app.user.UserAmountChangeRecordService;
import com.xunxin.service.app.user.UserAuthenticationService;
import com.xunxin.service.app.user.UserBlockRecordService;
import com.xunxin.service.app.user.UserDynamicRecordService;
import com.xunxin.service.app.user.UserExpChangeRecordService;
import com.xunxin.service.app.user.UserFeedBackService;
import com.xunxin.service.app.user.UserOnlineRecordService;
import com.xunxin.service.app.user.UserSelfPortraitService;
import com.xunxin.service.app.user.UserSignService;
import com.xunxin.util.MD5_UTIL;
import com.xunxin.util.PeriodsUtil;
import com.xunxin.util.app.OrderGeneratedUtils;
import com.xunxin.vo.account.UserAmountChangeRecord;
import com.xunxin.vo.account.UserExpChangeRecord;
import com.xunxin.vo.im.UserShields;
import com.xunxin.vo.qa.ArecordVO;
import com.xunxin.vo.qa.CommentVO;
import com.xunxin.vo.qa.QACollectionRecord;
import com.xunxin.vo.qa.QuestionVO;
import com.xunxin.vo.qa.UserAccessLinkedRecordVO;
import com.xunxin.vo.square.ExposureCommentRecord;
import com.xunxin.vo.square.ExposureCommentThumbRecord;
import com.xunxin.vo.square.ShakeItOffRecord;
import com.xunxin.vo.square.ThrowHydrangeaRecord;
import com.xunxin.vo.square.TurnplateAwardRecord;
import com.xunxin.vo.square.UserBrushAgainstRecord;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.DynamicThumbRecord;
import com.xunxin.vo.user.UserBlockRecord;
import com.xunxin.vo.user.UserDynamicRecordVO;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.vo.user.UserFeedBackVO;
import com.xunxin.vo.user.UserInfoData;
import com.xunxin.vo.user.UserOnlineRecord;
import com.xunxin.vo.user.UserWelfare;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Route;
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
	@Autowired
	private UserOnlineRecordService userOnlineRecordService;
	@Autowired
	private UserSignService userSignService;
	@Autowired
	private UserBlockRecordService userBlockRecordService;
	@Autowired
    private QuestionService questionService;
    @Autowired
    private ArecordService arecordService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private QASectionService qASectionService;
    @Autowired
    private UserAccessLinkedRecordService userAccessLinkedRecordService;
    @Autowired
    private XTagLibraryService xTagLibraryService;
    @Autowired
    private QAImageUrlRecordService qAImageUrlRecordService;
    @Autowired
    private QACollectionRecordService qACollectionRecordService;
    @Autowired 
    private CommentService commentService;
    @Autowired 
    private QAttentionRecordService qAttentionRecordService;
    @Autowired
    private UserExpChangeRecordService userExpChangeRecordService;
    @Autowired
    private XunxinAuditInformationRecordService xunxinAuditInformationRecordService; 
    @Autowired
    private UserDynamicRecordService userDynamicRecordService; 
    @Autowired
    private UserFriendsService userFriendsService; 
    @Autowired
    private AdminService adminService; 
    @Autowired
    private UserAlbumService userAlbumService; 
    @Autowired
    private UserAuthenticationService userAuthenticationService; 
    @Autowired
    private UserSelfPortraitService userSelfPortraitService; 
    @Autowired
    private UserAmountChangeRecordService userAmountChangeRecordService; 
    @Autowired
    private UserBrushAgainstRecordService userBrushAgainstRecordService; 
    @Autowired
    private TurnplateAwardRecordService turnplateAwardRecordService; 
    @Autowired
    private ExposureCommentThumbRecordService exposureCommentThumbRecordService; 
    @Autowired
    private ThrowHydrangeaRecordService throwHydrangeaRecordService; 
    @Autowired
    private ShakeItOffRecordService shakeItOffRecordService; 
    @Autowired
    private ExposureCommentRecordService exposureCommentRecordService; 
    @Autowired
    private DynamicThumbRecordService dynamicThumbRecordService; 
	@Autowired
	private IMNewsService iMNewsService;
	
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
//		        	 String url = "D:\\programmeTools\\nginx-1.12.1\\html\\photo\\feedback" + "/" + MD5_UTIL.string2MD5(OrderGeneratedUtils.getOrderNo()) + "." + fileExt;
		        	 String url = "/data01/nginxdata/photo/feedback" + "/" + MD5_UTIL.string2MD5(OrderGeneratedUtils.getOrderNo()) + "." + fileExt;
		        	 File newFile = new File(url);
		        	 if(is != null) {
		        		 out = new BufferedOutputStream(new FileOutputStream(newFile));  
		        		 byte[] buffer = new byte[1024];  
		        		 int len = -1;  
		        		 while ((len = is.read(buffer)) != -1) {  
		        			 out.write(buffer, 0, len);  
		        		 }
		        		//关闭输入流  
                         is.close();  
                         //关闭输出流  
                         out.close();
//		        		 String showUrl = url.replace("D:\\programmeTools\\nginx-1.12.1\\html\\photo\\feedback", "http://localhost:8761/photo/feedback");
		        		 String showUrl = url.replace("/data01/nginxdata/photo/feedback", "http://www.xunxinkeji.cn:8761/photo/feedback");
		        		 urls += showUrl + "|";
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
	public Response setup_password(@RequestParam("usreId") int userId) {
		log.info("infoMsg:--- 设置密码开始");
		Response reponse = this.getReponse();
		PageData pd = new PageData<>();
		try {
			UserEntity user = appUserService.findById(userId);
			if(!StringUtils.trim(user.getPassword()).equals("") && !StringUtils.isBlank(user.getPassword())) {
			    pd.put("hasPwd", UserEntity.NORMAL);
			}else {
			    pd.put("hasPwd", UserEntity.UNUSUAL);
			}
			log.info("infoMsg:--- 设置密码结束");
			return reponse.success(pd);
		} catch (Exception e) {
			log.error("errorMsg:--- 设置密码失败" + e.getMessage());
			return reponse.failure(e.getMessage());
		}
	}
	
	/**
	 * 重置密码
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	@RequestMapping(value=Router.Option.RESET_PASSWORD,method=RequestMethod.POST)
	@ResponseBody
	public Response reset_password(@RequestParam("usreId") int userId,@RequestParam("newWord") String newWord) {
	    log.info("infoMsg:--- 设置密码开始");
	    Response reponse = this.getReponse();
	    PageData pd = new PageData<>();
	    try {
	        appUserService.setup_password(userId, newWord);
	        log.info("infoMsg:--- 设置密码结束");
	        return reponse.success(pd);
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
	
	
	/**
	 * 统计用户在线时长
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.Option.ONLINE_COUNT_TIMES,method=RequestMethod.POST)
	@ResponseBody
	public Response online_count_times(@RequestParam("userId") int userId,@RequestParam("minutes") int minutes) {
	    log.info("infoMsg:--- 获取用户在线时长开始");
	    Response reponse = this.getReponse();
	    try {
	        UserOnlineRecord record = new UserOnlineRecord();
	        record.setOnlineTime(minutes);
	        record.setUserId(userId);
	        userOnlineRecordService.save(record);
	        appUserService.updateIsLogin(userId,1);
	        log.info("infoMsg:--- 获取用户在线时长结束");
	        return reponse.success();
	    } catch (Exception e) {
	        log.error("errorMsg:{--- 获取用户在线时长失败:" + e.getMessage() + "---}");
	        return reponse.failure(e.getMessage());
	    }
	    
	}
	
	/**
	 * 用户签到
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.Option.USER_SIGN,method=RequestMethod.POST)
	@ResponseBody
	public Response user_sign(@RequestParam("userId") Integer userId) {
	    log.info("infoMsg:---  用户签到开始");
	    Response reponse = this.getReponse();
	    try {
	    	boolean flag = userSignService.saveSgn(userId);
	        log.info("infoMsg:---  用户签到结束");
	        if(flag){
	        	  return reponse.success();
	        }else{
	        	return reponse.failure("今天已签到");
	        }
	    } catch (Exception e) {
	        log.error("errorMsg:{---  用户签到失败:" + e.getMessage() + "---}");
	        return reponse.failure(e.getMessage());
	    }
	    
	}
	
	/**
	 * 我的福利
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.Option.USER_WELFARE,method=RequestMethod.POST)
	@ResponseBody
	public Response user_Welfare(@RequestParam("userId") Integer userId) {
	    log.info("infoMsg:---  我的福利开始");
	    Response reponse = this.getReponse();
	    try {
	    	UserWelfare welfare = userSignService.queryMyWelfare(userId);
	        log.info("infoMsg:---  我的福利结束");
	        return reponse.success(welfare);
	    } catch (Exception e) {
	        log.error("errorMsg:{---  我的福利失败:" + e.getMessage() + "---}");
	        return reponse.failure(e.getMessage());
	    }
	    
	}
	
	
	/**
	 * 分享循心软件
	 * 
	 * @param userId
	 * @param changeType  SHARE_CIRCLE
	 * @return
	 */
	@RequestMapping(value=Router.Option.SHARE_SOFTWARE,method=RequestMethod.POST)
	@ResponseBody
	public Response share_software(@RequestParam("userId") int userId,@RequestParam("changeType") String changeType) {
	    log.info("infoMsg:---  我的福利开始");
	    Response reponse = this.getReponse();
	    try {
	        UserEntity user = appUserService.findById(userId);
	        int userExp = user.getUserExp();
	        Query expQuery = new Query()
	                .addCriteria(Criteria.where("userId").is(userId))
	                .limit(1);
	        UserExpChangeRecord exp = userExpChangeRecordService.findOneByQuery(expQuery);
	        if(!PeriodsUtil.getTianShu(exp.getCreateTime()).equals(PeriodsUtil.getTianShu(new Date()))) {
	            if(changeType.equals("SHARE_CIRCLE")) {
	                //赠送积分
	                appUserService.user_exp_change(userId, userExp+10);
	                //生成积分记录
	                UserExpChangeRecord record = new UserExpChangeRecord(ExpConstants.SHARE_CIRCLE, ExpConstants.INCOME, userExp, 10, userExp+10, userId);
	                userExpChangeRecordService.save(record);
	            }
	        }
	        log.info("infoMsg:---  分享循心软件");
	        return reponse.success();
	    } catch (Exception e) {
	        log.error("errorMsg:{---  分享循心软件:" + e.getMessage() + "---}");
	        return reponse.failure(e.getMessage());
	    }
	    
	}
	
	
	/**
	 * 资料清空
	 * 
	 * @param userId
	 * @param changeType  SHARE_CIRCLE
	 * @return
	 */
	@RequestMapping(value=Router.Option.INFORMATION_TO_EMPTY,method=RequestMethod.POST)
	@ResponseBody
	public Response Information_to_empty(@RequestParam("userId") int userId) {
	    log.info("infoMsg:---  资料清空开始");
	    Response reponse = this.getReponse();
	    try {
	        UserEntity user = appUserService.findById(userId);
	        appUserService.complete_basics_information(userId, "", "", 0, 0, "", "", "", 60, "", "", "", "", "", "", "", "");  //基本资料
	        appUserService.complete_particular_information(userId, "", "", "", 0, "", "", "", "", "", "", "", "", "", "");     //详细资料
	        PageData pd = new PageData<>();
	        pd.put("userId", userId);
	        pd.put("onlineTotal", 0);
	        pd.put("completeness", 0);
	        pd.put("certificationDegree", 0);
	        pd.put("isCompleteBasic", 0);
	        pd.put("isAuthentication", 0);
	        pd.put("isAuditor", 0);
	        pd.put("isLogin", 0);
	        pd.put("ishiden", 0);
	        pd.put("messageSetting", 0);
	        pd.put("selfPortraitSetting", 0);
	        appUserService.update_persoanl_status(pd);     //重置个人状态
	        //相册
	        userAlbumService.removeAll(userId);
	        //认证信息
	        userAuthenticationService.removeAll(userId);
	        //反馈
	        userFeedBackService.removeAll(userId);
	        //第三方绑定
	        userInfoDataService.removeAll(userId);
	        //自画像
	        userSelfPortraitService.removeAll(userId);
	        //积分明细
	        Query expQuery = new Query().addCriteria(Criteria.where("userId").size(userId));
	        List<UserExpChangeRecord> expList = userExpChangeRecordService.find(expQuery);
	        for(UserExpChangeRecord exp : expList) {
	            if(null != exp) {
	                userExpChangeRecordService.remove(exp);
	            }
	        }
	        //交易明细
	        Query amountQuery = new Query().addCriteria(Criteria.where("userId").size(userId));
	        List<UserAmountChangeRecord> amountList = userAmountChangeRecordService.find(amountQuery);
	        for(UserAmountChangeRecord amount : amountList) {
	            if(null != amount) {
	                userAmountChangeRecordService.remove(amount);
	            }
	        }
	        //擦肩而过记录
	        Query brushQuery = new Query().addCriteria(Criteria.where("userId").size(userId));
	        List<UserBrushAgainstRecord> brushList = userBrushAgainstRecordService.find(brushQuery);
	        for(UserBrushAgainstRecord brush : brushList) {
	            if(null != brush) {
	                userBrushAgainstRecordService.remove(brush);
	            }
	        }
	        //外链记录
	        Query linkedQuery = new Query().addCriteria(Criteria.where("userId").size(userId));
	        List<UserAccessLinkedRecordVO> linkedList = userAccessLinkedRecordService.find(linkedQuery);
	        for(UserAccessLinkedRecordVO linked : linkedList) {
	            if(null != linked) {
	                userAccessLinkedRecordService.remove(linked);
	            }
	        }
	        //转盘
	        Query turnQuery = new Query().addCriteria(Criteria.where("userId").size(userId));
	        List<TurnplateAwardRecord> turnList = turnplateAwardRecordService.find(turnQuery);
	        for(TurnplateAwardRecord turn : turnList) {
	            if(null != turn) {
	                turnplateAwardRecordService.remove(turn);
	            }
	        }
	        //姻缘树
	        Query throwQuery = new Query().addCriteria(Criteria.where("userId").size(userId));
	        List<ThrowHydrangeaRecord> throwList = throwHydrangeaRecordService.find(throwQuery);
	        for(ThrowHydrangeaRecord Hydrangea : throwList) {
	            if(null != Hydrangea) {
	                throwHydrangeaRecordService.remove(Hydrangea);
	            }
	        }
	        //摇一摇
	        Query shakeQuery = new Query().addCriteria(Criteria.where("userId").size(userId));
	        List<ShakeItOffRecord> shakeList = shakeItOffRecordService.find(shakeQuery);
	        for(ShakeItOffRecord shake : shakeList) {
	            if(null != shake) {
	                shakeItOffRecordService.remove(shake);
	            }
	        }
	        
	        //删除用户的动态
	        Query dynamicQuery = new Query().addCriteria(Criteria.where("userId").is(userId));
	        List<UserDynamicRecordVO> dynamicList = userDynamicRecordService.find(dynamicQuery);
	        for(UserDynamicRecordVO dynamic : dynamicList) {
	            if(null != dynamic) {
	                userDynamicRecordService.remove(dynamic);
	            }
	        }
	        //删除用户的动态点赞记录
	        Query dynamicThumbQuery = new Query().addCriteria(Criteria.where("userId").is(userId));
	        List<DynamicThumbRecord> dynamicThumbList = dynamicThumbRecordService.find(dynamicThumbQuery);
	        for(DynamicThumbRecord dynamicThumb : dynamicThumbList) {
	            if(null != dynamicThumb) {
	                dynamicThumbRecordService.remove(dynamicThumb);
	            }
	        }
	        //删除曝光栏评论 exposureCommentRecord
	        Query exposureQuery = new Query().addCriteria(Criteria.where("userId").is(userId));
	        List<ExposureCommentRecord> exposureList = exposureCommentRecordService.find(exposureQuery);
	        for(ExposureCommentRecord exposure : exposureList) {
	            if(null != exposure) {
	                exposureCommentRecordService.remove(exposure);
	            }
	        }
	        //删除曝光栏评论点赞记录 exposureCommentThumbRecord
	        Query exposureCommentThumbQuery = new Query().addCriteria(Criteria.where("userId").is(userId));
	        List<ExposureCommentThumbRecord> exposureCommentThumbList = exposureCommentThumbRecordService.find(exposureCommentThumbQuery);
	        for(ExposureCommentThumbRecord exposureCommentThumb : exposureCommentThumbList) {
	            if(null != exposureCommentThumb) {
	                exposureCommentThumbRecordService.remove(exposureCommentThumb);
	            }
	        }
	        //删除相关QA
	        Query questionQuery = new Query()
	                .addCriteria(Criteria.where("userID").is(userId))
	                .addCriteria(Criteria.where("editorType").is(0));
	        List<QuestionVO> questionList = questionService.find(questionQuery);
	        for(QuestionVO questionVO : questionList) {
	            if(null != questionVO) {
	                questionService.remove(questionVO);
	            }
	        }
	        //删除与我相关QA
	        Query arecordQuery = new Query()
	                .addCriteria(Criteria.where("answerID").is(userId));
	        List<ArecordVO> arecordList = arecordService.find(arecordQuery);
	        for(ArecordVO arecordVO : arecordList) {
	            if(null != arecordVO) {
	                arecordService.remove(arecordVO);
	            }
	        }
	        //删除我收藏的QA
	        Query collectionQuery = new Query()
	                .addCriteria(Criteria.where("userId").is(userId));
	        List<QACollectionRecord> collectList = qACollectionRecordService.find(collectionQuery);
	        for(QACollectionRecord collect : collectList) {
	            if(null != collect) {
	                qACollectionRecordService.remove(collect);
	            }
	        }
	        //清除收藏的脏数据
            Query collectQuery1 = new Query();
            collectQuery1.with(new Sort(new Order(Direction.DESC,"createTime")));
            List<QACollectionRecord> collectList1 = qACollectionRecordService.find(collectQuery1);
            for(QACollectionRecord collect : collectList1) {
                QuestionVO questionVO = questionService.findOneById(collect.getQuestionId());
                if(questionVO == null) {
                    qACollectionRecordService.remove(collect);
                }
            }
            //清除参与的脏数据
            Query arecordQuery1 = new Query();
            arecordQuery1.with(new Sort(new Order(Direction.DESC,"createTime")));
            List<ArecordVO> arecordList1 = arecordService.find(arecordQuery1);
            for(ArecordVO arecord : arecordList1) {
                QuestionVO questionVO = questionService.findOneById(arecord.getQuestionID());
                if(questionVO == null) {
                    arecordService.remove(arecord);
                }
            }
	        
	        log.info("infoMsg:---  资料清空结束");
	        return reponse.success();
	    } catch (Exception e) {
	        log.error("errorMsg:{---  资料清空失败:" + e.getMessage() + "---}");
	        return reponse.failure(e.getMessage());
	    }
	    
	}
	
	
	/**
	 * 彻底关闭
	 * 
	 * @param userId
	 * @param changeType  SHARE_CIRCLE
	 * @return
	 */
	@RequestMapping(value=Router.Option.SHUT_DOWN,method=RequestMethod.POST)
	@ResponseBody
	public Response shut_down(@RequestParam("userId") int userId,@RequestParam("reason") String reason) {
	    log.info("infoMsg:---  彻底关闭开始");
	    Response reponse = this.getReponse();
	    try {
	        appUserService.user_block(userId,1);
	        UserBlockRecord record = new UserBlockRecord();
	        record.setUserId(userId);
	        record.setReason(reason);
	        userBlockRecordService.save(record);
	        log.info("infoMsg:---  彻底关闭结束");
	        return reponse.success();
	    } catch (Exception e) {
	        log.error("errorMsg:{---  彻底关闭失败:" + e.getMessage() + "---}");
	        return reponse.failure(e.getMessage());
	    }
	    
	}
	/**
	 * 
	 * 支付链接推送
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value=Router.Option.PAY_FAIL,method=RequestMethod.POST)
	@ResponseBody
	public Response pay_fail(@RequestParam("userId") Integer userId) {
	    log.info("infoMsg:---  支付链接推送开始");
	    Response reponse = this.getReponse();
	    try {
	    	iMNewsService.userBehaviorPushMessage(MessTypeFactory.MESSAGE_PAY,userId,null,null);
	        log.info("infoMsg:---  支付链接推送结束");
	        return reponse.success();
	    } catch (Exception e) {
	        log.error("errorMsg:{---  支付链接推送失败:" + e.getMessage() + "---}");
	        return reponse.failure(e.getMessage());
	    }
	}
}
