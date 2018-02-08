package com.xunxin.controller.app.user;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.xunxin.constants.DynamicConstants;
import com.xunxin.constants.ExpConstants;
import com.xunxin.controller.app.message.MessTypeFactory;
import com.xunxin.controller.system.BaseController;
import com.xunxin.service.ArecordService;
import com.xunxin.service.SystemEntityService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.CommentService;
import com.xunxin.service.app.IMNewsService;
import com.xunxin.service.app.QASectionService;
import com.xunxin.service.app.SelfPortraitService;
import com.xunxin.service.app.UserAlbumService;
import com.xunxin.service.app.UserFriendsService;
import com.xunxin.service.app.XunxinAuditInformationRecordService;
import com.xunxin.service.app.qa.AnswerService;
import com.xunxin.service.app.qa.QACollectionRecordService;
import com.xunxin.service.app.qa.QAttentionRecordService;
import com.xunxin.service.app.qa.QuestionService;
import com.xunxin.service.app.qa.XTagLibraryService;
import com.xunxin.service.app.square.QAImageUrlRecordService;
import com.xunxin.service.app.square.UserAccessLinkedRecordService;
import com.xunxin.service.app.user.DynamicThumbRecordService;
import com.xunxin.service.app.user.UserAuthenticationService;
import com.xunxin.service.app.user.UserChangeInformationRecordService;
import com.xunxin.service.app.user.UserDynamicRecordService;
import com.xunxin.service.app.user.UserExpChangeRecordService;
import com.xunxin.service.app.user.UserInterestPointService;
import com.xunxin.service.app.user.UserSelfPortraitService;
import com.xunxin.util.MD5_UTIL;
import com.xunxin.util.MapRemoveNullUtil;
import com.xunxin.util.PeriodsUtil;
import com.xunxin.util.SortAlgorithmUtils;
import com.xunxin.util.app.CollectionRandomUtil;
import com.xunxin.util.app.OrderGeneratedUtils;
import com.xunxin.vo.account.UserExpChangeRecord;
import com.xunxin.vo.app.exception.QueryExceptionConstant;
import com.xunxin.vo.qa.AnswerVO;
import com.xunxin.vo.qa.ArecordVO;
import com.xunxin.vo.qa.QACollectionRecord;
import com.xunxin.vo.qa.QAImageUrlRecord;
import com.xunxin.vo.qa.QASection;
import com.xunxin.vo.qa.QAttentionRecord;
import com.xunxin.vo.qa.QuestionVO;
import com.xunxin.vo.qa.UserAccessLinkedRecordVO;
import com.xunxin.vo.qa.XTagLibrary;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.DynamicThumbRecord;
import com.xunxin.vo.user.UserAlbum;
import com.xunxin.vo.user.UserAuthentication;
import com.xunxin.vo.user.UserDynamicRecordVO;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.vo.user.UserInterestPoint;
import com.xunxin.vo.user.UserSelfPortraitVO;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;
import com.xunxin.web.api.exception.QueryExcetion;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * 
 * @Author Noseparte
 * @Compile 2017年10月11日 -- 下午4:04:31
 * @Version 1.0
 * @Description app端用户管理
 */
@Controller
@RequestMapping(value = Router.PATH + Router.User.PATH)
public class AppUserController extends BaseController {

	private static final Logger log = Logger.getLogger(AppUserController.class);
	
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private UserAlbumService userAlbumService;
	@Autowired
	private UserSelfPortraitService userSelfPortraitService;
	@Autowired
	private UserInterestPointService userInterestPointService;
	@Autowired
	private UserAuthenticationService userAuthenticationService;
	@Autowired
	private SelfPortraitService selfPortraitService;
	@Autowired
	private UserChangeInformationRecordService userChangeInformationRecordService;
	@Autowired
	private UserDynamicRecordService userDynamicRecordService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private ArecordService arecordService;
	@Autowired
	private QASectionService qASectionService;
	@Autowired
	private UserAccessLinkedRecordService userAccessLinkedRecordService;
	@Autowired
	private AnswerService answerService;
	@Autowired
	private XTagLibraryService xTagLibraryService;
	@Autowired
	private QACollectionRecordService qACollectionRecordService;
	@Autowired
	private QAttentionRecordService qAttentionRecordService;
	@Autowired
	private QAImageUrlRecordService qAImageUrlRecordService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserExpChangeRecordService userExpChangeRecordService;
	@Autowired
	private DynamicThumbRecordService dynamicThumbRecordService;
	@Autowired
	private XunxinAuditInformationRecordService xunxinAuditInformationRecordService;
	@Autowired
	private SystemEntityService systemEntityService;
	@Autowired
	private UserFriendsService userFriendsService;
	@Autowired
	private IMNewsService iMNewsService;

	/**
	 * APP user registered
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = Router.User.REGISTER, method = RequestMethod.POST)
	@ResponseBody
	public Response register(@RequestParam("phone") String phone,
			@RequestParam("verifyCode") int verifyCode,@RequestParam("password") String password) {
		log.info("infoMsg：--- 用户注册开始");
		Response reponse = this.getReponse();
		try {
			boolean verify = appUserService.register(phone, verifyCode, password);
			if (verify) {
				log.info("infoMsg:- register success.");
			}
			return reponse.success("infoMsg：--- 用户注册结束");
		} catch (Exception e) {
			log.error("errorMsg：--- 用户注册失败" + e.getMessage());
			return reponse.failure(e.getMessage());
		}
	}

	/**
	 * 用户认证程度
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = Router.User.DEGREE_OF_COMPLETION, method = RequestMethod.POST)
	@ResponseBody
	public Response getCertificationDegree(String phone) {
		log.info("infoMsg:---- obtain user certificationDegree start. ");
		Response reponse = this.getReponse();
		try {
			int state = appUserService.getCertificationDegree(phone);
			log.info("infoMsg:---- obtain user certificationDegree end. ");
			return reponse.success(state);
		} catch (Exception e) {
			log.error("errMsg:---- user certificationDegree is mistaken.");
			return reponse.failure("未获取到用户的认证程度");
		}
	}

	/**
	 * 用户资料完善程度
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = Router.User.INFO_COMPLETE_DEGREE, method = RequestMethod.POST)
	@ResponseBody
	public Response info_complete_degree(String phone) {
		log.info("infoMsg:---- obtain user certificationDegree start. ");
		Response reponse = this.getReponse();
		try {
			int state = 0;
			UserEntity user = appUserService.findOne(phone);
			if (user == null) {
				throw new QueryExcetion(QueryExceptionConstant.NULL_OBJECT);
			}
			if (!user.getAddress().equals("")) {
				state = UserEntity.NORMAL;
			}
			log.info("infoMsg:---- obtain user certificationDegree end. ");
			return reponse.success(state);
		} catch (Exception e) {
			log.error("errMsg:---- user certificationDegree is mistaken." + e.getMessage());
			return reponse.failure("未获取到用户的认证程度");
		}
	}

	/**
	 * 用户填写基本资料
	 * 
	 * @return
	 */
	@RequestMapping(value = Router.User.COMPLETE_BASICS_INFORMATION, method = RequestMethod.POST)
	@ResponseBody
	public Response complete_basics_information(@RequestParam("userId") int userId, @RequestParam("name") String name,
			@RequestParam("nickName") String nickName, @RequestParam("age") int age,
			@RequestParam(value = "sex") int sex, @RequestParam(value = "gender") String gender,
			@RequestParam(value = "sexualOrientation") String sexualOrientation,
			@RequestParam(value = "address") String address, @RequestParam(value = "height") int height,
			@RequestParam(value = "profession") String profession, @RequestParam(value = "trade") String trade,
			@RequestParam(value = "position") String position, @RequestParam(value = "income") String income,
			@RequestParam(value = "education") String education,
			@RequestParam(value = "williamsCollege") String williamsCollege,
			@RequestParam(value = "relationshipStatus") String relationshipStatus,
			@RequestParam(value = "makeFriendWay") String makeFriendWay) {
		log.info("infoMsg:--- 用户完善基本资料开始");
		Response res = this.getReponse();
		try {
			UserEntity user = appUserService.complete_basics_information(userId, name, nickName, age, sex, gender,
					sexualOrientation, address, height, profession, trade, position, income, education, williamsCollege,
					relationshipStatus, makeFriendWay);
			UserDynamicRecordVO userDynamicRecord = new UserDynamicRecordVO();
            userDynamicRecord.setContent("完善了基本资料");
            userDynamicRecord.setThumbCount(0);
            userDynamicRecord.setDynamicExtend("");;
            userDynamicRecord.setDynamicSource(DynamicConstants.INFORMATEION_CHANGE);
            userDynamicRecord.setUserId(userId);
            userDynamicRecord.setIshiden(0);
            userDynamicRecordService.save(userDynamicRecord);
            UserSelfPortraitVO vo = new UserSelfPortraitVO("", "", new Date(), userId);
            userSelfPortraitService.save(vo);
			log.info("infoMsg:--- 用户完善基本资料结束");
			return res.success(user.getIsCompleteBasic());
		} catch (Exception e) {
			log.error("errorMsg:--- 用户完善基本资料失败" + e.getMessage());
			return res.failure(e.getMessage());
		}
	}

	/**
	 * 完善详细资料
	 * 
	 * @param phone
	 *            手机号
	 * @param introduce
	 *            独白+自我介绍
	 * @param housingConditions
	 *            住房情况
	 * @param trafficTools
	 *            交通工具
	 * @param weight
	 *            体重
	 * @param nation
	 *            民族
	 * @param nationality
	 *            国籍
	 * @param nativePlace
	 *            籍贯
	 * @param censusRegister
	 *            户籍
	 * @param familyOrder
	 *            家中排行
	 * @param haveChild
	 *            有无子女
	 * @param religiousBelief
	 *            宗教信仰
	 * @param constellation
	 *            星座
	 * @param zodiac
	 *            生肖
	 * @param blood
	 *            血型
	 * @return
	 */
	@RequestMapping(value = Router.User.COMPLETE_PARTICULAR_INFORMATION, method = RequestMethod.POST)
	@ResponseBody
	public Response complete_particular_information(@RequestParam(value = "userId") int userId,
			@RequestParam(value = "introduce") String introduce,
			@RequestParam(value = "housingConditions") String housingConditions,
			@RequestParam(value = "trafficTools") String trafficTools, @RequestParam(value = "weight") int weight,
			@RequestParam(value = "nation") String nation, @RequestParam(value = "nationality") String nationality,
			@RequestParam(value = "nativePlace") String nativePlace,
			@RequestParam(value = "censusRegister") String censusRegister,
			@RequestParam(value = "familyOrder") String familyOrder, @RequestParam(value = "haveChild") String haveChild,
			@RequestParam(value = "religiousBelief") String religiousBelief,
			@RequestParam(value = "constellation") String constellation, @RequestParam(value = "zodiac") String zodiac,
			@RequestParam(value = "blood") String blood) {
		log.info("用户完善详细资料开始");
		Response res = this.getReponse();
		try {
			boolean flag = appUserService.complete_particular_information(userId, introduce, housingConditions,
					trafficTools, weight, nation, nationality, nativePlace, censusRegister, familyOrder, haveChild,
					religiousBelief, constellation, zodiac, blood);
			if(flag) {
			    UserDynamicRecordVO userDynamicRecord = new UserDynamicRecordVO();
				userDynamicRecord.setContent("您完善了详细资料");
	            userDynamicRecord.setThumbCount(0);
	            userDynamicRecord.setDynamicExtend("");;
	            userDynamicRecord.setDynamicSource(DynamicConstants.INFORMATEION_CHANGE);
	            userDynamicRecord.setUserId(userId);
	            userDynamicRecord.setIshiden(0);
	            userDynamicRecordService.save(userDynamicRecord);
	            log.info("用户完善详细资料结束");
				return res.success();
			}else {
			    return res.failure("用户完善详细资料失败");
			}
		} catch (Exception e) {
			log.error("errorMsg:{--- 用户完善详细资料失败:" + e.getMessage() + "---}");
			return res.failure(e.getMessage());
		}
	}

	/**
	 * 上传相册(多张)
	 * 
	 * @param photo
	 * @return
	 */
	@RequestMapping(value = Router.User.COMPLETE_ALBUM_INFORMATION, method = RequestMethod.POST)
	@ResponseBody
	public Response complete_album_information(@RequestParam("userId") int userId,MultipartHttpServletRequest request, HttpServletResponse response) {
		log.info("用户上传相册开始");
		Response res = this.getReponse();
		CommonsMultipartFile multipartFile = null;
		BufferedInputStream is = null;  
		BufferedOutputStream out = null;  // 准备好一个输出的对象
		String showUrl = "";
		List<String> resultUrl = new ArrayList<>();
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
//		        	 String url = "D:\\programmeTools\\nginx-1.12.1\\html\\photo\\album" + "/" + MD5_UTIL.string2MD5(OrderGeneratedUtils.getOrderNo())  + "." + fileExt;
		        	 String url = "/data01/nginxdata/photo/album" + "/" + MD5_UTIL.string2MD5(OrderGeneratedUtils.getOrderNo())  + "." + fileExt;
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
		        		 String name = MD5_UTIL.string2MD5(OrderGeneratedUtils.getOrderNo()) + "." + fileExt;
		        		 showUrl = url.replace("/data01/nginxdata/photo/album", "http://www.xunxinkeji.cn:8761/photo/album");
//		        		 showUrl = url.replace("D:\\programmeTools\\nginx-1.12.1\\html\\photo\\album", "http://www.xunxinkeji.cn:8761/photo/album");
		        		 List<UserAlbum> albumList = userAlbumService.findByUserId(userId);
		        		 if(albumList.size() < 12) {
		        			 UserAlbum album = new UserAlbum(userId,name, showUrl, new Date());
		        			 resultUrl.add(showUrl);
		        			 userAlbumService.save(album);
		        		 }else {
		        			 res.failure("图片数量超过上限");
		        		 }
		        	 }
		         }else {
					return res.failure("该文件类型不能够上传");
		         }
		    }
			String result = CollectionRandomUtil.listToString(resultUrl);
			UserDynamicRecordVO userDynamicRecord = new UserDynamicRecordVO();
			userDynamicRecord.setContent("我增加了"+resultUrl.size()+"张相册照片。");
			userDynamicRecord.setThumbCount(0);
			userDynamicRecord.setDynamicUrls(result);
			userDynamicRecord.setDynamicExtend("");
			userDynamicRecord.setDynamicSource(DynamicConstants.ABLUM_CHANGE);
			userDynamicRecord.setUserId(userId);
			userDynamicRecord.setIshiden(0);
			userDynamicRecordService.save(userDynamicRecord);
			log.error("infoMsg:--- 用户相册上传结束");
			return res.success("上传成功");
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
	 * 删除照片(单张)
	 * 
	 * @param photo
	 * @return
	 */
	@RequestMapping(value = Router.User.DELETE_ALBUM_INFORMATION, method = RequestMethod.POST)
	@ResponseBody
	public Response delete_album_information(@RequestParam("userId") int userId,@RequestParam("photoId") int photoId) {
	    log.info("infoMsg:--- 删除照片开始");
	    Response res = this.getReponse();
	    try {
	        userAlbumService.delete_album_information(userId,photoId);
	        UserDynamicRecordVO userDynamicRecord = new UserDynamicRecordVO();
            userDynamicRecord.setContent("我删除了1张相册照片。");
            userDynamicRecord.setThumbCount(0);
            userDynamicRecord.setDynamicSource(DynamicConstants.ABLUM_CHANGE);
            userDynamicRecord.setUserId(userId);
            userDynamicRecord.setIshiden(0);
            userDynamicRecordService.save(userDynamicRecord);
	        log.error("infoMsg:--- 删除照片结束");
	        return res.success("上传成功");
	    } catch (Exception e) {
	        log.error("errorMsg:--- 删除照片失败:" + e.getMessage() + "---}");
	        return res.failure(e.getMessage());
	    }
	    
	}
	
	
	/**
	 * 获取用户自画像
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.User.GET_SELF_PORTRAIT,method=RequestMethod.POST)
	@ResponseBody
	public Response get_self_portrait(@RequestParam("userId") int userId) {
		log.info("infoMsg:--- 获取用户自画像开始");
		Response reponse = this.getReponse();
		PageData spInfo = new PageData<>();
		List<PageData> pdList = new ArrayList<>();
		List<PageData> resultList = new ArrayList<>();
        Map<Short,Integer> map = new HashMap<>();
        int result = 0;
        int pushCount = 0;
        int temp = 0;
		try {
		    //我答过的
            Query arecordQuery = new Query()
                    .addCriteria(Criteria.where("answerID").is(userId));
            int arecordCount = arecordService.findCountByQuery(arecordQuery);
			Boolean flag = true;
			if(flag){
				UserSelfPortraitVO vo = userSelfPortraitService.findById(userId);
				if(vo != null) {
				    if(StringUtils.isBlank(vo.getExtraInfo()) || StringUtils.trim(vo.getExtraInfo()).equals("")) {
				        //固定信息
				        Map<String, Object> usualInfo = appUserService.getUsualInfo(userId);
				        spInfo.put("usualInfo", usualInfo);
				    }else {
				        Map<String, Object> usualInfo = appUserService.getUsualInfo(userId);
				        String[] split = vo.getExtraInfo().replace("[", "").replace("]", "").trim().split(",");
				        for(String str : split) {
				            usualInfo.put(str.toString(), appUserService.getByColum(userId,str.toString()));
				        }
				        spInfo.put("extraInfo", vo.getExtraInfo());
				        spInfo.put("usualInfo", usualInfo);
				    }
				}else {
				    UserSelfPortraitVO userSelfPortraitVO = new UserSelfPortraitVO("", "", new Date(), userId);
				    userSelfPortraitService.save(userSelfPortraitVO);
				    //固定信息
				    Map<String, Object> usualInfo = appUserService.getUsualInfo(userId);
				    spInfo.put("usualInfo", usualInfo);
				}
				//用户认证信息
	            List<UserAuthentication> authentList = userAuthenticationService.getAll(userId);
	            spInfo.put("phone", UserEntity.NORMAL);
	            spInfo.put("certification", UserEntity.NORMAL);
	            spInfo.put("education", UserEntity.NORMAL);
	            spInfo.put("mail", UserEntity.NORMAL);
	            spInfo.put("profession", UserEntity.NORMAL);
	            spInfo.put("sesame", UserEntity.NORMAL);
	            String[] authentType = new String[] {"phone","certification","education","mail","profession","sesame"};
	            for(UserAuthentication authent : authentList){
	                if(authent.getAuthType().equals(authentType[0])) {
	                    spInfo.put("phone", authent.getAuthState());
	                }
	                if(authent.getAuthType().equals(authentType[1])) {
	                    spInfo.put("certification", authent.getAuthState());
	                }
	                if(authent.getAuthType().equals(authentType[2])) {
	                    spInfo.put("education", authent.getAuthState());
	                }
	                if(authent.getAuthType().equals(authentType[3])) {
	                    spInfo.put("mail", authent.getAuthState());
	                }
	                if(authent.getAuthType().equals(authentType[4])) {
	                    spInfo.put("profession", authent.getAuthState());
	                }
	                if(authent.getAuthType().equals(authentType[5])) {
	                    spInfo.put("sesame", authent.getAuthState());
	                }
	            }
				//个人兴趣点
	            List<PageData> tagList = new ArrayList<>();
				Query query = new Query().addCriteria(Criteria.where("userId").is(userId));
				List<UserInterestPoint> interestList = userInterestPointService.find(query);
				if(interestList != null) {
				    for(UserInterestPoint point : interestList) {
				        PageData tagPd = new PageData<>();
				        XTagLibrary tag = xTagLibraryService.findOneById(point.getPointId());
				        tagPd.put("tagId", tag.getId());
				        tagPd.put("tagName", tag.getTagName());
				        tagList.add(tagPd);
				    }
				}
				spInfo.put("interestList", tagList) ;
				//板块列表
	            List<QASection> qASectionList = qASectionService.find(new Query().with(new Sort(new Order(Direction.ASC, "sectionType"))));
	            //话题分布情况
	            for(QASection section : qASectionList) {
	                Query seQuery = new Query()
	                        .addCriteria(Criteria.where("answerID").is(userId));
	                seQuery.addCriteria(Criteria.where("type").is(section.getSectionType()));
	                List<ArecordVO> arecordList = arecordService.find(seQuery);
	                if(arecordList.size() != 0) {
	                    map.put(section.getSectionType(),arecordList.size());
	                }
	            }
	            if(map.size() < 10) {
	                Integer[] a = new Integer[map.size()];
	                Integer[] array = map.values().toArray(a);
	                Set<Integer> set = new HashSet<>();  
	                for(int i=0;i<array.length;i++){  
	                    set.add(array[i]);  
	                }  
	                Integer[] arrayResult = (Integer[]) set.toArray(new Integer[set.size()]);  
	                Integer[] bubble_sort = SortAlgorithmUtils.bubble_sort((Integer[])arrayResult);
	                for(int key : bubble_sort) {
	                    List<Short> typeList = MapRemoveNullUtil.getKeyList(map,key);
	                    if(typeList.size() > 1) {
	                        for(Short type : typeList) {
	                            PageData tyPd = new PageData<>();
	                            QASection qASection = qASectionService.findOneByQuery(new Query().addCriteria(Criteria.where("sectionType").is(type)));
	                            tyPd.put("sectionName", qASection.getSectionName());
	                            // 创建一个数值格式化对象     
	                            NumberFormat numberFormat = NumberFormat.getInstance();     
	                            // 设置精确到小数点后2位     
	                            numberFormat.setMaximumFractionDigits(0);     
	                            String prent = numberFormat.format((float)key/(float)arecordCount*100); 
	                            tyPd.put("prent", prent);
	                            resultList.add(tyPd);
	                        }
	                    }else {
	                        PageData resultPd = new PageData<>();
	                        Short type = MapRemoveNullUtil.getKey(map,key);
	                        QASection qASection = qASectionService.findOneByQuery(new Query().addCriteria(Criteria.where("sectionType").is(type)));
	                        resultPd.put("sectionName", qASection.getSectionName());
	                        // 创建一个数值格式化对象     
	                        NumberFormat numberFormat = NumberFormat.getInstance();     
	                        // 设置精确到小数点后2位     
	                        numberFormat.setMaximumFractionDigits(0);     
	                        String prent = numberFormat.format((float)key/(float)arecordCount*100); 
	                        resultPd.put("prent", prent);
	                        resultList.add(resultPd);
	                    }
	                }
	            }else {
	                Integer[] a = new Integer[map.size()];
	                Integer[] array = map.values().toArray(a);
	                Set<Integer> set = new HashSet<>();  
	                for(int i=0;i<array.length;i++){  
	                    set.add(array[i]);  
	                }  
	                Integer[] arrayResult = (Integer[]) set.toArray(new Integer[set.size()]);  
	                Integer[] bubble_sort = SortAlgorithmUtils.bubble_sort((Integer[])arrayResult);
	                for(int i = 0;i < 10;i++) {
	                    PageData resultPd = new PageData<>();
	                    Short type = MapRemoveNullUtil.getKey(map,bubble_sort[i]);
	                    QASection qASection = qASectionService.findOneByQuery(new Query().addCriteria(Criteria.where("sectionType").is(type)));
	                    resultPd.put("sectionName", qASection.getSectionName());
	                    // 创建一个数值格式化对象     
	                    NumberFormat numberFormat = NumberFormat.getInstance();     
	                    // 设置精确到小数点后2位     
	                    numberFormat.setMaximumFractionDigits(0);     
	                    String prent = numberFormat.format((float)bubble_sort[i]/(float)arecordCount*100); 
	                    resultPd.put("prent", prent);
	                    resultList.add(resultPd);
	                }
	                for(int i = 9;i <= map.size();i++) {
	                    temp += bubble_sort[i];
	                }
	                PageData resultPd = new PageData<>();
	                resultPd.put("sectionName", "其他");
	                // 创建一个数值格式化对象     
	                NumberFormat numberFormat = NumberFormat.getInstance();     
	                // 设置精确到小数点后2位     
	                numberFormat.setMaximumFractionDigits(0);     
	                String prent = numberFormat.format(temp/arecordCount*100); 
	                resultPd.put("prent", prent);
	                resultList.add(resultPd);
	            }
	            spInfo.put("qaList", resultList);
				log.info("infoMsg:--- 获取用户自画像结束");
				return reponse.success(spInfo);
			}else{
				return reponse.failure("今天免费发送自画像次数已不够");
			}
		} catch (Exception e) {
			log.error("errorMsg:--- 获取用户自画像失败" + e.getMessage());
			return reponse.failure(e.getMessage());
		}
	}

	/**
	 * 用户编辑自画像
	 * 
	 * @param userId
	 * @param col_String
	 * @return
	 */
	@RequestMapping(value=Router.User.EDIT_SELF_PORTRAIT,method=RequestMethod.POST)
	@ResponseBody
	public Response edit_self_portrait(@RequestParam("userId") int userId,@RequestParam("col_String") String col_String) {
		log.info("infoMsg:--- 用户编辑自画像开始");
		Response reponse = this.getReponse();
		try {
	        if(col_String.split(",").length > 10) {
	            return reponse.failure("操作有误");
	        }
		    UserSelfPortraitVO vo = userSelfPortraitService.findById(userId);
		    if(vo != null) {
		        userSelfPortraitService.updatePortrait(userId,col_String);
		    }else {
		        vo = new UserSelfPortraitVO("", col_String, new Date(), userId);
		        userSelfPortraitService.save(vo);
		    }
		    log.info("infoMsg:--- 用户编辑自画像结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:{----用户编辑自画像失败:" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 获取用户资料
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.User.GET_USER_BASIC_INFORMATION,method=RequestMethod.POST)
	@ResponseBody
	public Response get_user_basic_information(@RequestParam("userId") int userId) {
		log.info("infoMsg:--- 获取用户资料开始");
		Response reponse = this.getReponse();
		PageData pd = new PageData<>();
		try {
			UserEntity user = appUserService.findById(userId); 
			if(user != null) {
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
				String country = "";
				if(user.getNationality() != null && !StringUtils.trim(user.getNationality()).equals("")) {
				    country =  systemEntityService.findCountryById(Integer.parseInt(user.getNationality())).getName_Chinese();
				    if(country != null && !StringUtils.trim(country).equals("")) {
				        pd.put("nationality", country);
				    }
				}else {
				    pd.put("nationality", country);
				}
                //籍贯
                String nativePlace = "";
                if(user.getNativePlace() != null) {
                    String[] addrs = user.getNativePlace().toString().replace("[","").replace("]", "").trim().split(",");
                    if(addrs != null && addrs.length > 1) {
                        for(String addr : addrs) {
                            nativePlace +=  systemEntityService.findNameByCode(addr).getName();
                        }
                        pd.put("nativePlace", nativePlace);
                    }else {
                        pd.put("nativePlace", nativePlace);
                    }
                }else {
                    pd.put("nativePlace", nativePlace);
                }
                //户籍
                String censusRegister = "";
                if(user.getCensusRegister() != null) {
                    String[] censusRegisters = user.getCensusRegister().toString().replace("[","").replace("]", "").trim().split(",");
                    if(censusRegisters != null && censusRegisters.length > 1) {
                        for(String addr : censusRegisters) {
                            censusRegister +=  systemEntityService.findNameByCode(addr).getName();
                        }
                        pd.put("censusRegister", censusRegister);
                    }else {
                        pd.put("censusRegister", censusRegister);
                    }
                }else {
                    pd.put("censusRegister", censusRegister);
                }
				pd.put("familyOrder", user.getFamilyOrder());
				pd.put("haveChild", user.getHaveChild());
				pd.put("religiousBelief", user.getReligiousBelief());
				pd.put("constellation", user.getConstellation());
				pd.put("zodiac", user.getZodiac());
				pd.put("blood", user.getBlood());
				pd.put("completeness", user.getCompleteness());
			}
			log.info("infoMsg:--- 获取用户资料结束");
			return reponse.success(pd);
		} catch (Exception e) {
			log.error("errorMsg:{--- 获取用户资料失败：" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 我的页面信息
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.User.MY_DETAIL_INFO,method=RequestMethod.POST)
	@ResponseBody
	public Response my_detail_info(@RequestParam("userId") int userId) {
		log.info("infoMsg:--- 我的页面信息开始");
		Response reponse = this.getReponse();
		PageData pd = new PageData<>();
		try {
			UserEntity user = appUserService.findById(userId);
			if(user != null) {
				pd.put("gender", user.getGender());
				pd.put("nickName", user.getNickName());
				pd.put("ID", user.getID());
				pd.put("grade", user.getGrade());
			}
			log.info("infoMsg:--- 我的页面信息结束");
			return reponse.success(pd);
		} catch (Exception e) {
			log.error("errorMsg:{--- 我的页面信息失败：" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 我的资料
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.User.MY_INFOMATION,method=RequestMethod.POST)
	@ResponseBody
	public Response my_infomation(@RequestParam("userId") int userId,@RequestParam("attentionId") int attentionId) {
	    log.info("infoMsg:--- 我的资料开始");
	    Response reponse = this.getReponse();
	    PageData pd = new PageData<>();
	    List<PageData> dyList = new ArrayList<>();
	    int result = 0;
	    try {
	        UserEntity user = appUserService.findById(userId);
	        if(user != null) {
	            pd.put("sex", user.getSex());
	            pd.put("gender", user.getGender());
	            pd.put("nickName", user.getNickName());
	            pd.put("ID", user.getID());
	            pd.put("grade", user.getGrade());
	            pd.put("sexualOrientation", user.getSexualOrientation());  //性取向
	            pd.put("makeFriendWay", user.getMakeFriendWay());          //正在寻找
	        }
	        List<UserAlbum> albumList = userAlbumService.findByUserId(userId);
	        //用户相册
	        pd.put("albumList", albumList);
	        //用户认证信息
	        List<UserAuthentication> authentList = userAuthenticationService.getAll(userId);
            pd.put("phone", UserEntity.NORMAL);
            pd.put("certification", UserEntity.NORMAL);
            pd.put("education", UserEntity.NORMAL);
            pd.put("mail", UserEntity.NORMAL);
            pd.put("profession", UserEntity.NORMAL);
            pd.put("sesame", UserEntity.NORMAL);
            String[] authentType = new String[] {"phone","certification","education","mail","profession","sesame"};
            for(UserAuthentication authent : authentList){
                if(authent.getAuthType().equals(authentType[0])) {
                    pd.put("phone", authent.getAuthState());
                }
                if(authent.getAuthType().equals(authentType[1])) {
                    pd.put("certification", authent.getAuthState());
                }
                if(authent.getAuthType().equals(authentType[2])) {
                    pd.put("education", authent.getAuthState());
                }
                if(authent.getAuthType().equals(authentType[3])) {
                    pd.put("mail", authent.getAuthState());
                }
                if(authent.getAuthType().equals(authentType[4])) {
                    pd.put("profession", authent.getAuthState());
                }
                if(authent.getAuthType().equals(authentType[5])) {
                    pd.put("sesame", authent.getAuthState());
                }
            }
            //Q&A总数
            Query countQuery = new Query();
            countQuery.with(new Sort(new Order(Direction.DESC,"createTime")));
            int count = questionService.findCountByQuery(countQuery);
            //与我相关的Q&A  我发布的
            Query publishQuery = new Query()
                .addCriteria(Criteria.where("editorType").is((short) 0))
                .addCriteria(Criteria.where("userID").is(userId));
            int publishCount = questionService.findCountByQuery(publishQuery);
            //我答过的
            Query arecordQuery = new Query()
                    .addCriteria(Criteria.where("answerID").is(userId));
            int arecordCount = arecordService.findCountByQuery(arecordQuery);
            result = publishCount + arecordCount;
            pd.put("totalCount", count);   //总数
            pd.put("ownCount", result);    //与我相关的
            //动态列表
            Query DynamicQuery = new Query()
                    .addCriteria(Criteria.where("userId").is(userId));
            List<UserDynamicRecordVO> dynamicList = userDynamicRecordService.find(DynamicQuery);
            for(UserDynamicRecordVO vo : dynamicList) {
                PageData dyPd = new PageData<>();
                dyPd.put("id", vo.getId());
                dyPd.put("content", vo.getContent());
                dyPd.put("dynamicExtend", vo.getDynamicExtend());
                dyPd.put("createTime", vo.getCreateTime());
                dyPd.put("dynamicUrls", vo.getDynamicUrls());
                dyPd.put("dynamicSource", vo.getDynamicSource());
                dyPd.put("thumbCount", vo.getThumbCount());
                Query thumbQuery = new Query()
                        .addCriteria(Criteria.where("dynamicId").is(vo.getId()))
                        .addCriteria(Criteria.where("userId").is(userId));
                DynamicThumbRecord record = dynamicThumbRecordService.findOneByQuery(thumbQuery);
                if(record != null) {
                    dyPd.put("thumbState", 0);
                }else {
                    dyPd.put("thumbState", 1);
                }
                dyList.add(dyPd);
            }
            pd.put("dynamicList", dyList);
            //是否关注
            boolean flag = userFriendsService.isAttention(attentionId,userId);
            if(flag) {
                pd.put("attentionState", 0);
            }else {
                pd.put("attentionState", 1);
            }
            
	        log.info("infoMsg:--- 我的资料结束");
	        return reponse.success(pd);
	    } catch (Exception e) {
	        log.error("errorMsg:{--- 我的资料失败：" + e.getMessage() + "---}");
	        return reponse.failure(e.getMessage());
	    }
	}
	
	/**
	 * 编辑用户资料
	 * 
	 * @param userId
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	@RequestMapping(value=Router.User.EDIT_USER_INFORMATION,method=RequestMethod.POST)
	@ResponseBody
	public Response edit_user_information(@RequestParam("userId") int userId, 
			@RequestParam("fieldName") String fieldName,@RequestParam("fieldValue") String fieldValue) {
		log.info("infoMsg:--- 用户修改资料开始");
		Response reponse = this.getReponse();
		try {
			PageData pd = new PageData<>();
			pd.put("userId", userId);
			pd.put("fieldName", fieldName);
			pd.put("fieldValue", fieldValue);
			appUserService.edit_user_information(pd);
			//生成动态记录
			UserDynamicRecordVO userDynamicRecord = new UserDynamicRecordVO();
            userDynamicRecord.setContent("我成功修改了个人资料。");
            userDynamicRecord.setThumbCount(0);
            userDynamicRecord.setDynamicUrls("");
            userDynamicRecord.setDynamicExtend("");
            userDynamicRecord.setDynamicSource(DynamicConstants.INFORMATEION_CHANGE);
            userDynamicRecord.setUserId(userId);
            userDynamicRecord.setIshiden(0);
            userDynamicRecordService.save(userDynamicRecord);
			log.info("infoMsg:--- 用户修改资料结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:{---用户修改资料失败：" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
		
	}

    /**
     * 查看用户动态列表
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value=Router.User.WATCH_USER_DYNAMIC_LIST,method=RequestMethod.POST)
    @ResponseBody
    public Response watch_user_dynamic_list(@RequestParam("userId") int userId,@RequestParam("thumbId") int thumbId,@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize) {
        log.info("infoMsg:--- 获取用户动态列表开始");
        Response reponse = this.getReponse();
        List<PageData> pdList = new ArrayList<>();
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(userId));
            query.with(new Sort(new Order(Direction.DESC,"createTime")));
            // 分页
            query.skip((pageNo - 1) * pageSize).limit(pageSize);
            List<UserDynamicRecordVO> dyList = userDynamicRecordService.find(query);
            for(UserDynamicRecordVO vo : dyList) {
                PageData pd = new PageData<>();
                pd.put("id", vo.getId());
                pd.put("content", vo.getContent());
                pd.put("dynamicExtend", vo.getDynamicExtend());
                pd.put("createTime", vo.getCreateTime());
                pd.put("dynamicUrls", vo.getDynamicUrls());
                pd.put("dynamicSource", vo.getDynamicSource());
                pd.put("thumbCount", vo.getThumbCount());
                Query thumbQuery = new Query()
                        .addCriteria(Criteria.where("dynamicId").is(vo.getId()))
                        .addCriteria(Criteria.where("userId").is(userId))
                        .addCriteria(Criteria.where("thumbId").is(thumbId)); 
                DynamicThumbRecord record = dynamicThumbRecordService.findOneByQuery(thumbQuery);
                if(record != null) {
                    pd.put("thumbState", 0);
                }else {
                    pd.put("thumbState", 1);
                }
                pdList.add(pd);
            }
            log.info("infoMsg:--- 获取用户动态列表结束");
            return reponse.success(pdList);
        } catch (Exception e) {
            log.info("infoMsg:{--- 获取用户动态列表失败:"+ e.getMessage() + "---}");
            return reponse.failure(e.getMessage());
        }
    }
	
	/**
	 * 用户删除动态
	 * 
	 * @param userId
	 * @param dynamicId
	 * @return
	 */
	@RequestMapping(value=Router.User.USER_DELETE_DYNAMIC,method=RequestMethod.POST)
	@ResponseBody
	public Response user_delete_dynamic(@RequestParam("userId") int userId,@RequestParam("dynamicId") String dynamicId) {
		log.info("infoMsg:--- 用户删除动态开始");
		Response reponse = this.getReponse();
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("userId").is(userId));
			query.addCriteria(Criteria.where("id").is(dynamicId));
			query.with(new Sort(new Order(Direction.DESC,"createTime")));
			UserDynamicRecordVO record = userDynamicRecordService.findOneByQuery(query);
			userDynamicRecordService.remove(record);
			log.info("infoMsg:--- 用户删除动态结束");
			return reponse.success();
		} catch (Exception e) {
			log.info("infoMsg:{--- 用户删除动态失败:"+ e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
	}
	
	/**
	 * 用户动态点赞 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.User.USER_DYNAMIC_THUMB,method=RequestMethod.POST)
	@ResponseBody
	public Response user_dynamic_thumb(@RequestParam("userId") int userId,@RequestParam("thumbId") int thumbId,@RequestParam("dynamicId") String dynamicId) {
	    log.info("infoMsg:--- 用户动态点赞开始");
	    Response reponse = this.getReponse();
	    try {
	        Query query = new Query()
                    .addCriteria(Criteria.where("userId").is(userId))
                    .addCriteria(Criteria.where("thumbId").is(thumbId))
                    .addCriteria(Criteria.where("dynamicId").is(dynamicId));
            DynamicThumbRecord record = dynamicThumbRecordService.findOneByQuery(query);
            if(record == null) {
                DynamicThumbRecord dynamicThumbRecord = new DynamicThumbRecord(dynamicId, 0,thumbId, userId);
                dynamicThumbRecordService.save(dynamicThumbRecord);
                UserDynamicRecordVO dynamic = userDynamicRecordService.findOneById(dynamicId);
                Update update = new Update().set("thumbCount", dynamic.getThumbCount()+1);
                Query dyQuery = new Query().addCriteria(Criteria.where("id").is(dynamicId));
                userDynamicRecordService.updateFirst(dyQuery, update);
                iMNewsService.userBehaviorPushMessage(MessTypeFactory.MESSAGE_GIVE_UP,thumbId,userId,dynamicThumbRecord.getId());
            }
            
	        log.info("infoMsg:--- 用户动态点赞结束");
	        return reponse.success();
	    } catch (Exception e) {
	        log.info("infoMsg:{--- 用户动态点赞失败:"+ e.getMessage() + "---}");
	        return reponse.failure(e.getMessage());
	    }
	}
	
	/**
	 * 用户动态取消点赞 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.User.USER_CANCEL_DYNAMIC_THUMB,method=RequestMethod.POST)
	@ResponseBody
	public Response user_cancel_dynamic_thumb(@RequestParam("userId") int userId,@RequestParam("thumbId") int thumbId,@RequestParam("dynamicId") String dynamicId) {
	    log.info("infoMsg:--- 用户动态取消点赞开始");
	    Response reponse = this.getReponse();
	    try {
	        Query query = new Query()
	                .addCriteria(Criteria.where("userId").is(userId))
	                .addCriteria(Criteria.where("thumbId").is(thumbId))
        	        .addCriteria(Criteria.where("dynamicId").is(dynamicId));
	        DynamicThumbRecord record = dynamicThumbRecordService.findOneByQuery(query);
	        if(record != null) {
	            dynamicThumbRecordService.remove(record); 
	            UserDynamicRecordVO dynamic = userDynamicRecordService.findOneById(dynamicId);
                Update update = new Update().set("thumbCount", dynamic.getThumbCount()-1);
                Query dyQuery = new Query().addCriteria(Criteria.where("id").is(dynamicId));
                userDynamicRecordService.updateFirst(dyQuery, update);
	        }
	        log.info("infoMsg:--- 用户动态取消点赞结束");
	        return reponse.success();
	    } catch (Exception e) {
	        log.info("infoMsg:{--- 用户动态取消点赞失败:"+ e.getMessage() + "---}");
	        return reponse.failure(e.getMessage());
	    }
	}
	
	/**
	 * 我的积分
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.User.USER_EXP,method=RequestMethod.POST)
	@ResponseBody
	public Response user_exp(@RequestParam("userId") int userId) {
	    log.info("infoMsg:--- 获取用户积分明细开始");
	    Response reponse = this.getReponse();
	    PageData pd = new PageData<>();
	    int totalExp = 0;
	    int preExp = 0;
	    try {
	        //当前积分
	        UserEntity user = appUserService.findById(userId);
	        int userExp = user.getUserExp();
	        pd.put("exp", userExp);
	        //累计获得积分
	        Query query = new Query()
	                .addCriteria(Criteria.where("userId").is(userId))
	                .addCriteria(Criteria.where("direction").is(ExpConstants.INCOME));
	        List<UserExpChangeRecord> expList = userExpChangeRecordService.find(query);
	        for(UserExpChangeRecord record : expList) {
	            totalExp += record.getTansferAmount();
	        }
	        pd.put("totalExp", totalExp);
	        //昨日收益
	        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
	        String startTime = simple.format(new Date());
	        long tian = PeriodsUtil.quene(new Date());
	        Date endTime = PeriodsUtil.longToDate(tian - 1000*60*60*24);
	        Query preQuery = new Query()
	                .addCriteria(Criteria.where("userId").is(userId))
	                .addCriteria(Criteria.where("direction").is(ExpConstants.INCOME))
	                .addCriteria(Criteria.where("createTIme").gte(startTime).lt(endTime));
	        List<UserExpChangeRecord> preList = userExpChangeRecordService.find(preQuery);
	        for(UserExpChangeRecord record : preList) {
	            preExp += record.getTansferAmount();
	        }
	        pd.put("preExp", preExp);
	        log.info("infoMsg:--- 获取用户积分明细结束");
	        return reponse.success(pd);
	    } catch (Exception e) {
	        log.info("infoMsg:{--- 获取用户积分明细失败:" + e.getMessage() + "---}");
	        return reponse.failure(e.getMessage());
	    }
	}
	
	
	/**
	 * 用户积分明细
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.User.USER_EXP_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Response user_exp_list(@RequestParam("userId") int userId,@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize) {
	    log.info("infoMsg:--- 获取用户积分明细开始");
	    Response reponse = this.getReponse();
	    List<PageData> pdList = new ArrayList<>();
	    try {
	        Query query = new Query()
	                .addCriteria(Criteria.where("userId").is(userId))
	                .with(new Sort(new Order(Direction.DESC,"createTime")))
        	        // 分页
                    .skip((pageNo - 1) * pageSize).limit(pageSize);
	        List<UserExpChangeRecord> expList = userExpChangeRecordService.find(query);
	        for(UserExpChangeRecord record : expList) {
	            PageData pd = new PageData<>();
	            pd.put("exp", record.getTansferAmount());
	            pd.put("direction", record.getDirection());
	            pd.put("changeType", record.getChangeType());
	            pd.put("createTime", record.getCreateTime());
	            pdList.add(pd);
	        }
	        log.info("infoMsg:--- 获取用户积分明细结束");
	        return reponse.success(pdList);
	    } catch (Exception e) {
	        log.info("infoMsg:{--- 获取用户积分明细失败:" + e.getMessage() + "---}");
	        return reponse.failure(e.getMessage());
	    }
	}
	
	
	/**
	 * 我的Q&A
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.User.USER_QUESTION_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Response user_question_list(@RequestParam("userId") int userId) {
	    log.info("infoMsg:--- 获取我的Q&A开始");
	    Response reponse = this.getReponse();
	    PageData pd = new PageData<>();
	    List<PageData> resultList = new ArrayList<>();
	    Map<Short,Integer> map = new HashMap<>();
	    int result = 0;
	    int pushCount = 0;
	    int temp = 0;
	    try {
	        //Q&A总数
	        Query countQuery = new Query();
	        countQuery.with(new Sort(new Order(Direction.DESC,"createTime")));
	        int count = questionService.findCountByQuery(countQuery);
	        //与我相关的Q&A  我发布的
	        Query publishQuery = new Query()
	            .addCriteria(Criteria.where("editorType").is((short) 0))
	            .addCriteria(Criteria.where("userID").is(userId));
	        int publishCount = questionService.findCountByQuery(publishQuery);
	        //我答过的
	        Query arecordQuery = new Query()
	                .addCriteria(Criteria.where("answerID").is(userId));
	        int arecordCount = arecordService.findCountByQuery(arecordQuery);
	        //我收藏的
	        Query collectionQuery = new Query()
	                .addCriteria(Criteria.where("userId").is(userId));
	        int collectionCount = qACollectionRecordService.findCountByQuery(collectionQuery);
	        result = publishCount + arecordCount;
	        pd.put("totalCount", count);   //总数
	        pd.put("ownCount", result);    //与我相关的
	        pd.put("collectionCount", collectionCount);    //我收藏的
	        //板块列表
	        List<QASection> qASectionList = qASectionService.find(new Query().with(new Sort(new Order(Direction.ASC, "sectionType"))));
	        //话题分布情况
            for(QASection section : qASectionList) {
                Query query = new Query()
                        .addCriteria(Criteria.where("answerID").is(userId));
                query.addCriteria(Criteria.where("type").is(section.getSectionType()));
                List<ArecordVO> arecordList = arecordService.find(query);
                if(arecordList.size() != 0) {
                    map.put(section.getSectionType(),arecordList.size());
                }
	        }
            if(map.size() < 10) {
                Integer[] a = new Integer[map.size()];
                Integer[] array = map.values().toArray(a);
                Set<Integer> set = new HashSet<>();  
                for(int i=0;i<array.length;i++){  
                    set.add(array[i]);  
                }  
                Integer[] arrayResult = (Integer[]) set.toArray(new Integer[set.size()]);  
                Integer[] bubble_sort = SortAlgorithmUtils.bubble_sort((Integer[])arrayResult);
                for(int key : bubble_sort) {
                    List<Short> typeList = MapRemoveNullUtil.getKeyList(map,key);
                    if(typeList.size() > 1) {
                        for(Short type : typeList) {
                            PageData tyPd = new PageData<>();
                            QASection qASection = qASectionService.findOneByQuery(new Query().addCriteria(Criteria.where("sectionType").is(type)));
                            tyPd.put("sectionName", qASection.getSectionName());
                            // 创建一个数值格式化对象     
                            NumberFormat numberFormat = NumberFormat.getInstance();     
                            // 设置精确到小数点后2位     
                            numberFormat.setMaximumFractionDigits(0);     
                            String prent = numberFormat.format((float)key/(float)arecordCount*100); 
                            tyPd.put("prent", prent);
                            resultList.add(tyPd);
                        }
                    }else {
                        PageData resultPd = new PageData<>();
                        Short type = MapRemoveNullUtil.getKey(map,key);
                        QASection qASection = qASectionService.findOneByQuery(new Query().addCriteria(Criteria.where("sectionType").is(type)));
                        resultPd.put("sectionName", qASection.getSectionName());
                        // 创建一个数值格式化对象     
                        NumberFormat numberFormat = NumberFormat.getInstance();     
                        // 设置精确到小数点后2位     
                        numberFormat.setMaximumFractionDigits(0);     
                        String prent = numberFormat.format((float)key/(float)arecordCount*100); 
                        resultPd.put("prent", prent);
                        resultList.add(resultPd);
                    }
                }
            }else {
                Integer[] a = new Integer[map.size()];
                Integer[] array = map.values().toArray(a);
                Integer[] bubble_sort = SortAlgorithmUtils.bubble_sort((Integer[])array);
                for(int i = 0;i < 10;i++) {
                    PageData resultPd = new PageData<>();
                    Short type = MapRemoveNullUtil.getKey(map,bubble_sort[i]);
                    QASection qASection = qASectionService.findOneByQuery(new Query().addCriteria(Criteria.where("sectionType").is(type)));
                    resultPd.put("sectionName", qASection.getSectionName());
                    // 创建一个数值格式化对象     
                    NumberFormat numberFormat = NumberFormat.getInstance();     
                    // 设置精确到小数点后2位     
                    numberFormat.setMaximumFractionDigits(0);     
                    String prent = numberFormat.format((float)bubble_sort[i]/(float)arecordCount*100); 
                    resultPd.put("prent", prent);
                    resultList.add(resultPd);
                }
                for(int i = 9;i <= map.size();i++) {
                    temp += bubble_sort[i];
                }
                PageData resultPd = new PageData<>();
                resultPd.put("sectionName", "其他");
                // 创建一个数值格式化对象     
                NumberFormat numberFormat = NumberFormat.getInstance();     
                // 设置精确到小数点后2位     
                numberFormat.setMaximumFractionDigits(0);     
                String prent = numberFormat.format(temp/arecordCount*100); 
                resultPd.put("prent", prent);
                resultList.add(resultPd);
            }
            pd.put("qaList", resultList);
	        log.info("infoMsg:--- 获取我的Q&A结束");
	        return reponse.success(pd);
	    } catch (Exception e) {
	        log.info("infoMsg:--- 获取我的Q&A失败");
	        return reponse.failure(e.getMessage());
	    }
	}
	
	/**
	 * 我参与的Q&A
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.User.USER_PARTICIPATE_QUESTION_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Response user_participate_question_list(@RequestParam("userId") int userId,@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize) {
	    log.info("infoMsg:--- 获取我参与的Q&A开始");
	    Response reponse = this.getReponse();
	    List<PageData> pdList = new ArrayList<>();
	    try {
	        //我答过的
	        Query arecordQuery = new Query()
	                .addCriteria(Criteria.where("answerID").is(userId))
	                .with(new Sort(new Order(Direction.DESC,"createTime")))
	                // 分页
                    .skip((pageNo - 1) * pageSize).limit(pageSize);
	        List<ArecordVO> arecordList = arecordService.find(arecordQuery);
	        List<QuestionVO> questionList = new ArrayList<>();
	        for(ArecordVO vo : arecordList) {
	            QuestionVO questionVO = questionService.findOneById(vo.getQuestionID());
	            if(questionVO != null) {
	                questionList.add(questionVO);
	            }
	        }
	        for(QuestionVO vo : questionList) {
	            if(null != vo) {
	                PageData pd = new PageData<>();
	                //如有外链，外链的展示
	                pd.put("questionID", vo.getId());
	                //如有外链，外链的展示
	                String linked_recordId = vo.getLinked_recordId();
	                if(linked_recordId != null && !StringUtils.trim(linked_recordId).equals("") && !StringUtils.isBlank(linked_recordId)) {
	                    UserAccessLinkedRecordVO record = userAccessLinkedRecordService.findOneById(linked_recordId);
	                    pd.put("linked_url", record.getLinked_url());
	                    pd.put("content", record.getContent());
	                    pd.put("image", record.getImage());
	                    pd.put("source", record.getSource());
	                    pd.put("title", record.getTitle());
	                }
	                //外链之外的其他资源
	                pd.put("name", vo.getName());
	                pd.put("qa_content", vo.getContent());
	                //观点
	                String[] answers = vo.getAnswers().replace("[","").replace("]","").split(",");
	                List<String> answerVOList = new ArrayList<>();
	                for(String answer : answers) {
	                    AnswerVO answerVO = answerService.findOneById(answer.trim());
	                    answerVOList.add(answerVO.getAnswer());
	                }
	                pd.put("answers", answerVOList);
	                //标签
	                if(vo.getTags() != null) {
	                    String[] tags = vo.getTags().split(",");
	                    List<XTagLibrary> xTagLibraryList = new ArrayList<>();
	                    for(String tag : tags) {
	                        XTagLibrary xTagLibrary = xTagLibraryService.findOneByQuery(new Query().addCriteria(Criteria.where("tagName").is(tag)));
	                        xTagLibraryList.add(xTagLibrary);
	                    }
	                }
	                //图片
//              String[] imgs = vo.getImg_urls().split(",");
//              List<QAImageUrlRecord> aAImageUrlRecordList = new ArrayList<>();
//              for(String img : imgs) {
//                  QAImageUrlRecord aAImageUrlRecord = qAImageUrlRecordService.findOneByQuery(new Query().addCriteria(Criteria.where("url").is(img)));
//                  aAImageUrlRecordList.add(aAImageUrlRecord);
//              }
	                pd.put("imgs", vo.getImg_urls());
	                UserEntity user = appUserService.findById(userId);
	                pd.put("gender", user.getGender());
	                pd.put("nickName", user.getNickName());
	                
	                //收藏数
	                Query collectionQuery = new Query().addCriteria(Criteria.where("id").is(vo.getId()));
	                Integer collectionCount = qACollectionRecordService.findCountByQuery(collectionQuery);
	                pd.put("collectionCount", collectionCount);
	                
	                //已选数
	                Query selectQuery = new Query().addCriteria(Criteria.where("questionID").is(vo.getId()));
	                Integer selectCount = arecordService.findCountByQuery(selectQuery);
	                pd.put("selectCount", selectCount);
	                //审核状态
	                pd.put("status", vo.getStatus());
	                pdList.add(pd);
	            }
	        }
	        log.info("infoMsg:--- 获取我参与的Q&A结束");
	        return reponse.success(pdList);
	    } catch (Exception e) {
	        log.info("infoMsg:--- 获取我参与的Q&A失败");
	        return reponse.failure(e.getMessage());
	    }
	}
	
	
	/**
	 * 我发布的Q&A
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.User.USER_ISSUE_QUESTION_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Response user_issue_question_list(@RequestParam("userId") int userId,@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize) {
	    log.info("infoMsg:--- 获取我发布的Q&A开始");
	    Response reponse = this.getReponse();
	    List<PageData> pdList = new ArrayList<>();
	    try {
	        //与我相关的Q&A  我发布的
            Query publishQuery = new Query()
                .addCriteria(Criteria.where("editorType").is((short) 0))
                .addCriteria(Criteria.where("userID").is(userId))
                .with(new Sort(new Order(Direction.DESC,"createTime")))
                // 分页
                .skip((pageNo - 1) * pageSize).limit(pageSize);
            List<QuestionVO> publishList = questionService.find(publishQuery); 
            for(QuestionVO vo : publishList) {
                PageData pd = new PageData<>();
                //如有外链，外链的展示
                pd.put("questionID", vo.getId());
                //如有外链，外链的展示
                String linked_recordId = vo.getLinked_recordId();
                if(linked_recordId != null && !StringUtils.trim(linked_recordId).equals("") && !StringUtils.isBlank(linked_recordId)) {
                    UserAccessLinkedRecordVO record = userAccessLinkedRecordService.findOneById(linked_recordId);
                    pd.put("linked_url", record.getLinked_url());
                    pd.put("content", record.getContent());
                    pd.put("image", record.getImage());
                    pd.put("source", record.getSource());
                    pd.put("title", record.getTitle());
                }
                //外链之外的其他资源
                pd.put("name", vo.getName());
                pd.put("qa_content", vo.getContent());
                //观点
                String[] answers = vo.getAnswers().replace("[","").replace("]","").split(",");
                List<String> answerVOList = new ArrayList<>();
                for(String answer : answers) {
                    AnswerVO answerVO = answerService.findOneById(answer.trim());
                    answerVOList.add(answerVO.getAnswer());
                }
                pd.put("answers", answerVOList);
                //标签
                if(vo.getTags() != null) {
                String[] tags = vo.getTags().split(",");
                List<XTagLibrary> xTagLibraryList = new ArrayList<>();
                    for(String tag : tags) {
                        XTagLibrary xTagLibrary = xTagLibraryService.findOneByQuery(new Query().addCriteria(Criteria.where("tagName").is(tag)));
                        xTagLibraryList.add(xTagLibrary);
                    }
                }
                //图片
//              String[] imgs = vo.getImg_urls().split(",");
//              List<QAImageUrlRecord> aAImageUrlRecordList = new ArrayList<>();
//              for(String img : imgs) {
//                  QAImageUrlRecord aAImageUrlRecord = qAImageUrlRecordService.findOneByQuery(new Query().addCriteria(Criteria.where("url").is(img)));
//                  aAImageUrlRecordList.add(aAImageUrlRecord);
//              }
                pd.put("imgs", vo.getImg_urls());
                UserEntity user = appUserService.findById(userId);
                pd.put("gender", user.getGender());
                pd.put("nickName", user.getNickName());
                
                //收藏数
                Query collectionQuery = new Query().addCriteria(Criteria.where("id").is(vo.getId()));
                Integer collectionCount = qACollectionRecordService.findCountByQuery(collectionQuery);
                pd.put("collectionCount", collectionCount);
                
                //已选数
                Query selectQuery = new Query().addCriteria(Criteria.where("questionID").is(vo.getId()));
                Integer selectCount = arecordService.findCountByQuery(selectQuery);
                pd.put("selectCount", selectCount);
                //审核状态
                pd.put("status", vo.getStatus());
                pdList.add(pd);
            }
	        
	        log.info("infoMsg:--- 获取我发布的Q&A结束");
	        return reponse.success(pdList);
	    } catch (Exception e) {
	        log.info("infoMsg:--- 获取我发布的Q&A失败");
	        return reponse.failure(e.getMessage());
	    }
	}
	

    /**
     * 我发布的Q&A详情
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value=Router.User.USER_ISSUE_QUESTION_DETAIL,method=RequestMethod.POST)
    @ResponseBody
    public Response user_issue_question_detail(@RequestParam("userId") int userId,@RequestParam("questionID") String questionID) {
        log.info("infoMsg:--- 获取我发布的Q&A详情开始");
        Response reponse = this.getReponse();
        PageData pd = new PageData<>();
        try {
            QuestionVO vo = questionService.findOneById(questionID);
            
            //答题记录
            Query arecordQuery = new Query()
                    .addCriteria(Criteria.where("questionID").is(questionID))
                    .addCriteria(Criteria.where("answerID").is(userId));
            ArecordVO arecordVO = arecordService.findOneByQuery(arecordQuery);
            if(arecordVO != null) {
                pd.put("arecordState", UserEntity.UNUSUAL);
                pd.put("arecordTime", arecordVO.getCreateTime());
                pd.put("arecordId", arecordVO.getAnswers().split("_")[1]);
            }else {
                pd.put("arecordState", UserEntity.NORMAL);
            }
            //已收藏，未收藏
            Query collectionQuery = new Query().addCriteria(Criteria.where("questionId").is(questionID)).addCriteria(Criteria.where("userId").is(userId));
            QACollectionRecord qACollectionRecord = qACollectionRecordService.findOneByQuery(collectionQuery);
            if(qACollectionRecord != null) {
                pd.put("collectionState", UserEntity.UNUSUAL);
            }else {
                pd.put("collectionState", UserEntity.NORMAL);
            }
            //已关注，未关注
            Query attentionQuery = new Query()
                    .addCriteria(Criteria.where("attentionId").is(vo.getUserID()))
                    .addCriteria(Criteria.where("userId").is(userId));
            QAttentionRecord qAttentionRecord = qAttentionRecordService.findOneByQuery(attentionQuery);
            if(qAttentionRecord != null) {
                pd.put("attentionState", UserEntity.UNUSUAL);
            }else {
                pd.put("attentionState", UserEntity.NORMAL);
            }
            //如有外链，外链的展示
            String linked_recordId = vo.getLinked_recordId();
            if(linked_recordId != null && !StringUtils.trim(linked_recordId).equals("") && !StringUtils.isBlank(linked_recordId)) {
                UserAccessLinkedRecordVO record = userAccessLinkedRecordService.findOneById(linked_recordId);
                pd.put("id", record.getId());
                pd.put("content", record.getContent());
                pd.put("image", record.getImage());
                pd.put("source", record.getSource());
                pd.put("title", record.getTitle());
            }
            //板块
            QASection section = qASectionService.findOneByQuery(new Query().addCriteria(Criteria.where("sectionType").is(vo.getType())));
            pd.put("sectionName", section.getSectionName());
            pd.put("sectionId", section.getId());
            //外链之外的其他资源
            pd.put("name", vo.getName());
            pd.put("qa_content", vo.getContent());
            pd.put("releaseTime", vo.getReleaseTime());
            //观点
            String[] answers = vo.getAnswers().replace("[","").replace("]","").split(",");
            List<PageData> answerList = new ArrayList<>();
            
            Integer totalCount = arecordService.findCountByQuery(new Query().addCriteria(Criteria.where("questionID").is(questionID)));     //已选总数
            for(String answer : answers) {
                AnswerVO answerVO = answerService.findOneById(answer.trim());
                
                PageData ansPd = new PageData<>();
                ansPd.put("id", answerVO.getId());
                ansPd.put("answer", answerVO.getAnswer());
                // 创建一个数值格式化对象     
                NumberFormat numberFormat = NumberFormat.getInstance();     
                // 设置精确到小数点后2位     
                numberFormat.setMaximumFractionDigits(0);     
                String result = numberFormat.format((float)answerVO.getCount()/(float)totalCount*100); 
                ansPd.put("proportion", result);       //百分比
                answerList.add(ansPd);
            }
            pd.put("answers", answerList);
            //标签
            if(vo.getTags() != null) {
                String[] tags = vo.getTags().split(",");
                List<XTagLibrary> xTagLibraryList = new ArrayList<>();
                for(String tag : tags) {
                    XTagLibrary xTagLibrary = xTagLibraryService.findOneByQuery(new Query().addCriteria(Criteria.where("tagName").is(tag)));
                    xTagLibraryList.add(xTagLibrary);
                }
            }
            //图片
            if(vo.getImg_urls() != null && !StringUtils.trim(vo.getImg_urls()).equals("")) {
                String[] imgs = vo.getImg_urls().toString().replace("[", "").replace("]", "").trim().split(",");
                List<PageData> aAImageUrlRecordList = new ArrayList<>();
                for(String img : imgs) {
                    QAImageUrlRecord aAImageUrlRecord = qAImageUrlRecordService.findOneByQuery(new Query().addCriteria(Criteria.where("url").is(img)));
                    PageData imgPd = new PageData<>();
                    imgPd.put("imgUrl", aAImageUrlRecord.getUrl().trim());
                    InputStream murl = new URL(aAImageUrlRecord.getUrl().trim()).openStream();
                    BufferedImage sourceImg = ImageIO.read(murl);   
                    imgPd.put("imgWidth", sourceImg.getWidth());    // 源图宽度
                    imgPd.put("imgHeight", sourceImg.getHeight()); // 源图高度
                    aAImageUrlRecordList.add(imgPd);
                }
                pd.put("imgs", aAImageUrlRecordList);
            }
            int userID = vo.getUserID();
            UserEntity user = appUserService.findById(userID);
            pd.put("gender", user.getGender());
            pd.put("nickName", user.getNickName());
            
            //收藏数
            Query collectionCountQuery = new Query().addCriteria(Criteria.where("id").is(vo.getId()));
            Integer collectionCount = qACollectionRecordService.findCountByQuery(collectionCountQuery);
            pd.put("collectionCount", collectionCount);
            
            //已选数
            Query selectQuery = new Query().addCriteria(Criteria.where("questionID").is(vo.getId()));
            Integer selectCount = arecordService.findCountByQuery(selectQuery);
            pd.put("selectCount", selectCount);
            
            //评论数
            Query commentQuery = new Query().addCriteria(Criteria.where("questionId").is(questionID));
            Integer commentCount = commentService.findCountByQuery(commentQuery);
            pd.put("commentCount", commentCount);
            
            //审核未通过的原因
            if(vo.getStatus() == 2) {
                List<String> CauseList = xunxinAuditInformationRecordService.queryCause(questionID);
              //审核状态
                pd.put("status", vo.getStatus());
                pd.put("backReasons", CauseList);
            }
            log.info("infoMsg:--- 获取我发布的Q&A详情结束");
            return reponse.success(pd);
        } catch (Exception e) {
            log.info("infoMsg:--- 获取我发布的Q&A详情失败");
            return reponse.failure(e.getMessage());
        }
    }
    

    /**
     * 修改我发布的Q&A
     * 
     * @param userId
     * @param questionId
     * @param qa_name
     * @param qa_content
     * @param qa_type
     * @param qa_tags
     * @param qa_answers
     * @param linked_recordId
     * @param img_urls
     * @return
     */
    @RequestMapping(value=Router.User.USER_UPDATE_ISSUE_QUESTION,method=RequestMethod.POST)
    @ResponseBody
    public Response user_update_issue_question(@RequestParam("userId") int userId,@RequestParam("questionId") String questionId,@RequestParam("qa_name") String qa_name,
            @RequestParam("qa_content") String qa_content,@RequestParam("qa_type") String qa_type,@RequestParam("qa_tags") String qa_tags,
            @RequestParam("qa_answers") String qa_answers,@RequestParam("linked_recordId") String linked_recordId,@RequestParam("img_urls") String img_urls) {
        log.info("infoMsg:--- 修改我发布的Q&A开始");
        Response reponse = this.getReponse();
        PageData pd = new PageData<>();
        try {
            QuestionVO vo = questionService.findOneById(questionId);
            if(null != vo){
                Query query = new Query()
                        .addCriteria(Criteria.where("id").is(questionId))
                        .addCriteria(Criteria.where("userID").is(userId));
                Update update = new Update();
                update.set("name", qa_name);
                update.set("linked_recordId", linked_recordId);
                update.set("content", qa_content);
                Query typeQuery = new Query().addCriteria(Criteria.where("sectionName").is(qa_type));       //获取模块对象
                short type = qASectionService.findOneByQuery(typeQuery).getSectionType();
                update.set("type", type);
                //解析观点
                String[] answers = qa_answers.split(",");
                Set<String> IDS = new HashSet<>();
                for(String answer : answers) {
                    AnswerVO answerVO = answerService.findOneByQuery(new Query().addCriteria(Criteria.where("answer").is(answer)));
                    if(answerVO == null) {
                        AnswerVO av = new AnswerVO();
                        av.setType(3);
                        av.setCount(1);
                        av.setAnswer(answer);
                        answerService.save(av);
                        IDS.add(av.getId());
                    }else {
                        answerVO.setCount(answerVO.getCount() + 1);
                        Update.update("count", answerVO.getCount() + 1);
                        IDS.add(answerVO.getId());
                    }
                }
                //解析观点
                String[] tags = qa_tags.split(",");
                for(String tag : tags) {
                    XTagLibrary xTag = xTagLibraryService.findOneByQuery(new Query().addCriteria(Criteria.where("tagName").is(tag)));
                    if(xTag == null) {
                        XTagLibrary t = new XTagLibrary();
                        t.setTagName(tag);
                        t.setType(type);
                        xTagLibraryService.save(t);
                    }
                }
                update.set("tags", qa_tags);
                update.set("status", 0);
                update.set("answers", IDS.toString());
                update.set("img_urls", img_urls);
                questionService.updateFirst(query, update);
            }
            log.info("infoMsg:--- 修改我发布的Q&A结束");
            return reponse.success();
        } catch (Exception e) {
            log.info("infoMsg:--- 修改我发布的Q&A失败");
            return reponse.failure(e.getMessage());
        }
    }
    
	
	/**
	 * 我收藏的Q&A
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.User.USER_COLLECT_QUESTION_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Response user_collect_question_list(@RequestParam("userId") int userId,@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize) {
	    log.info("infoMsg:--- 获取我收藏的Q&A开始");
	    Response reponse = this.getReponse();
	    List<PageData> pdList = new ArrayList<>();
	    try {
	        //我收藏的
            Query collecQuery = new Query()
                    .addCriteria(Criteria.where("userId").is(userId))
                    .with(new Sort(new Order(Direction.DESC,"createTime")))
                 // 分页
                    .skip((pageNo - 1) * pageSize).limit(pageSize);;
            List<QACollectionRecord> collectionList = qACollectionRecordService.find(collecQuery);
            List<QuestionVO> questionList = new ArrayList<>();
            for(QACollectionRecord vo : collectionList) {
                questionList.add(questionService.findOneById(vo.getQuestionId()));
            }
            for(QuestionVO vo : questionList) {
                PageData pd = new PageData<>();
                //如有外链，外链的展示
                pd.put("questionID", vo.getId());
                //如有外链，外链的展示
                String linked_recordId = vo.getLinked_recordId();
                if(linked_recordId != null && !StringUtils.trim(linked_recordId).equals("") && !StringUtils.isBlank(linked_recordId)) {
                    UserAccessLinkedRecordVO record = userAccessLinkedRecordService.findOneById(linked_recordId);
                    pd.put("linked_url", record.getLinked_url());
                    pd.put("content", record.getContent());
                    pd.put("image", record.getImage());
                    pd.put("source", record.getSource());
                    pd.put("title", record.getTitle());
                }
                //外链之外的其他资源
                pd.put("name", vo.getName());
                pd.put("qa_content", vo.getContent());
                //观点
                String[] answers = vo.getAnswers().replace("[","").replace("]","").split(",");
                List<String> answerVOList = new ArrayList<>();
                for(String answer : answers) {
                    AnswerVO answerVO = answerService.findOneById(answer.trim());
                    answerVOList.add(answerVO.getAnswer());
                }
                pd.put("answers", answerVOList);
                //标签
                if(vo.getTags() != null) {
                String[] tags = vo.getTags().split(",");
                List<XTagLibrary> xTagLibraryList = new ArrayList<>();
                    for(String tag : tags) {
                        XTagLibrary xTagLibrary = xTagLibraryService.findOneByQuery(new Query().addCriteria(Criteria.where("tagName").is(tag)));
                        xTagLibraryList.add(xTagLibrary);
                    }
                }
                //图片
//              String[] imgs = vo.getImg_urls().split(",");
//              List<QAImageUrlRecord> aAImageUrlRecordList = new ArrayList<>();
//              for(String img : imgs) {
//                  QAImageUrlRecord aAImageUrlRecord = qAImageUrlRecordService.findOneByQuery(new Query().addCriteria(Criteria.where("url").is(img)));
//                  aAImageUrlRecordList.add(aAImageUrlRecord);
//              }
                pd.put("imgs", vo.getImg_urls());
                UserEntity user = appUserService.findById(userId);
                pd.put("gender", user.getGender());
                pd.put("nickName", user.getNickName());
                
                //收藏数
                Query collectionQuery = new Query().addCriteria(Criteria.where("id").is(vo.getId()));
                Integer collectionCount = qACollectionRecordService.findCountByQuery(collectionQuery);
                pd.put("collectionCount", collectionCount);
                
                //已选数
                Query selectQuery = new Query().addCriteria(Criteria.where("questionID").is(vo.getId()));
                Integer selectCount = arecordService.findCountByQuery(selectQuery);
                pd.put("selectCount", selectCount);
                //审核状态
                pd.put("status", vo.getStatus());
                pdList.add(pd);
            }
	        log.info("infoMsg:--- 获取我收藏的Q&A结束");
	        return reponse.success(pdList);
	    } catch (Exception e) {
	        log.info("errorMsg:{--- 获取我收藏的Q&A失败:" + e.getMessage() + "---}");
	        return reponse.failure(e.getMessage());
	    }
	}
	
	
	/**
	 * 删除我发布的Q&A
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.User.USER_PUBLISH_COLLECT_QUESTION,method=RequestMethod.POST)
	@ResponseBody
	public Response user_delete_publish_question(@RequestParam("userId") int userId,@RequestParam("questionID") String questionID) {
	    log.info("infoMsg:--- 删除我发布的Q&A开始");
	    Response reponse = this.getReponse();
	    PageData pd = new PageData<>();
	    try {
	        Query addCriteria = new Query()
	                .addCriteria(Criteria.where("id").is(questionID))
	                .addCriteria(Criteria.where("userID").is(userId));
            QuestionVO vo = questionService.findOneByQuery(addCriteria);
            questionService.remove(vo);
	        log.info("infoMsg:--- 删除我发布的Q&A结束");
	        return reponse.success();
	    } catch (Exception e) {
	        log.error("errorMsg:{--- 删除我发布的Q&A失败:" + e.getMessage() + "---}");
	        return reponse.failure(e.getMessage());
	    }
	}
	
	/**
	 * 删除我收藏的Q&A
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.User.USER_DELETE_COLLECT_QUESTION_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Response user_delete_collect_question_list(@RequestParam("userId") int userId,@RequestParam("questionIDS") String questionIDS) {
	    log.info("infoMsg:--- 删除Q&A开始");
	    Response reponse = this.getReponse();
	    PageData pd = new PageData<>();
	    List<PageData> pdList = new ArrayList<>();
	    try {
	        String[] IDS = questionIDS.replace("[", "").replace("]", "").trim().split(",");
	        for(String id : IDS) {
	            QACollectionRecord vo = qACollectionRecordService.findOneByQuery(
	                    new Query().addCriteria(Criteria.where("questionId").is(id)).addCriteria(Criteria.where("userId").is(userId)));
	            qACollectionRecordService.remove(vo);
	        }
	        log.info("infoMsg:--- 删除Q&A结束");
	        return reponse.success(pdList);
	    } catch (Exception e) {
	        log.info("infoMsg:--- 删除Q&A失败");
	        return reponse.failure(e.getMessage());
	    }
	}
	
	public static void main(String[] args) throws ParseException {
	    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        String format = simple.format(new Date());
	    Date date=simple.parse(format);
	    long addDate = PeriodsUtil.addDate(date);
	    
	    Date longToDate = PeriodsUtil.longToDate(addDate - 1000*60*60*24);
	    String format1 = simple.format(longToDate);
	    System.out.println(format1);
	    System.out.println(longToDate);
	}
	
	
	
}
