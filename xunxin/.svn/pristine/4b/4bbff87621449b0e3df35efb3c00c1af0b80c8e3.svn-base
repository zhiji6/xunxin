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
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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

import com.xunxin.controller.system.BaseController;
import com.xunxin.service.SystemEntityService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.SelfPortraitService;
import com.xunxin.service.app.UserAlbumService;
import com.xunxin.service.app.user.UserAuthenticationService;
import com.xunxin.service.app.user.UserChangeInformationRecordService;
import com.xunxin.service.app.user.UserDynamicRecordService;
import com.xunxin.service.app.user.UserInterestPointService;
import com.xunxin.service.app.user.UserSelfPortraitService;
import com.xunxin.util.app.OrderGeneratedUtils;
import com.xunxin.vo.app.exception.QueryExceptionConstant;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.sys.SystemCountryEntity;
import com.xunxin.vo.user.UserAlbum;
import com.xunxin.vo.user.UserAuthentication;
import com.xunxin.vo.user.UserDynamicRecordVO;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.vo.user.UserInterestPoint;
import com.xunxin.vo.user.UserSelfPortraitVO;
import com.xunxin.web.api.bean.Pageable;
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
	private SystemEntityService systemEntityService;

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
				log.info("用户完善详细资料结束");
				return res.success();
			}
			return res.failure("用户完善详细资料失败");
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
		log.info("用户完善基本资料开始");
		Response res = this.getReponse();
		CommonsMultipartFile multipartFile = null;
		BufferedInputStream is = null;  
		BufferedOutputStream out = null;  // 准备好一个输出的对象
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
		        	 String url = "D:\\programmeTools\\nginx-1.12.1\\html\\photo\\album" + "/" + str + OrderGeneratedUtils.getOrderNo()  + "." + fileExt;
		        	 File newFile = new File(url);
		        	 if(is != null) {
		        		 out = new BufferedOutputStream(new FileOutputStream(newFile));  
		        		 byte[] buffer = new byte[1024];  
		        		 int len = -1;  
		        		 while ((len = is.read(buffer)) != -1) {  
		        			 out.write(buffer, 0, len);  
		        		 }
		        		 String name = str + OrderGeneratedUtils.getOrderNo() + "." + fileExt;
		        		 List<UserAlbum> albumList = userAlbumService.findByUserId(userId);
		        		 String showUrl = url.replace("D:\\programmeTools\\nginx-1.12.1\\html\\photo\\album", "http://www.xunxinkeji.cn:8761/photo/album");
		        		 if(albumList.size() < 12) {
		        			 UserAlbum album = new UserAlbum(userId,name, showUrl, new Date());
		        			 userAlbumService.save(album);
		        			 
		        		 }else {
		        			 res.failure("图片数量超过上限");
		        		 }
		        	 }
		         }else {
					return res.failure("该文件类型不能够上传");
		         }
		    }
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
		try {
			Boolean flag = selfPortraitService.queryJurisdiction(userId);
			
			if(flag){
				UserSelfPortraitVO vo = userSelfPortraitService.findById(userId);
				if(StringUtils.isBlank(vo.getExtraInfo()) || StringUtils.trim(vo.getExtraInfo()).equals("")) {
					//固定信息
					Map<String, Object> usualInfo = appUserService.getUsualInfo(userId);
					spInfo.put("usualInfo", usualInfo);
				}else {
					Map<String, Object> usualInfo = appUserService.getUsualInfo(userId);
					String[] split = vo.getExtraInfo().split(",");
					for(String str : split) {
						usualInfo.put(str.toString(), appUserService.getByColum(userId,str.toString()));
					}
					spInfo.put("usualInfo", usualInfo);
				}
				//认证信息
				List<UserAuthentication> authList = userAuthenticationService.getAll(userId);
				spInfo.put("authList", authList) ;
				//个人兴宾区点
				Query query = new Query().addCriteria(Criteria.where("userId").is(userId));
				List<UserInterestPoint> interestList = userInterestPointService.find(query);
				spInfo.put("interestList", interestList) ;
				
				//
				// TODO 话题分布情况
				log.info("infoMsg:--- 获取用户自画像结束");
				selfPortraitService.insertSelfPortrait(userId);
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
		log.info("infoMsg:--- ");
		Response reponse = this.getReponse();
		
		
		try {
			return reponse.success();
		} catch (Exception e) {
			log.error("");
			return reponse.failure();
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
				String country =  systemEntityService.findCountryById(Integer.parseInt(user.getNationality())).getName_Chinese();
				pd.put("nationality", country);
				//籍贯
				String[] addrs = user.getNativePlace().replace("[","").replace("]", "").trim().split(",");
				String nativePlace = "";
				for(String addr : addrs) {
				    nativePlace +=  systemEntityService.findNameByCode(addr).getName();
				}
				pd.put("nativePlace", nativePlace);
				//户籍
				String[] censusRegisters = user.getCensusRegister().replace("[","").replace("]", "").trim().split(",");
				String censusRegister = "";
				for(String addr : censusRegisters) {
				    censusRegister +=  systemEntityService.findNameByCode(addr).getName();
				}
				pd.put("censusRegister", censusRegister);
				pd.put("familyOrder", user.getFamilyOrder());
				pd.put("haveChild", user.getHaveChild());
				pd.put("religiousBelief", user.getReligiousBelief());
				pd.put("constellation", user.getConstellation());
				pd.put("zodiac", user.getZodiac());
				pd.put("blood", user.getBlood());
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
			log.info("infoMsg:--- 用户修改资料结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:{---用户修改资料失败：" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
		
	}
	
	
	/**
	 * 用户动态列表
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value=Router.User.USER_DYNAMIC_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Response user_dynamic_list(@RequestParam("userId") int userId,@RequestParam("page") Pageable page) {
		log.info("infoMsg:--- 获取用户动态列表开始");
		Response reponse = this.getReponse();
		List<PageData> pdList = new ArrayList<>();
		try {
			Query query = new Query();
			query.with(new Sort(new Order(Direction.DESC,"createTime")));
			// 分页
			query.skip((page.getPageNo() - 1) * 10).limit(page.getPageSize());
			List<UserDynamicRecordVO> dyList = userDynamicRecordService.find(query);
			for(UserDynamicRecordVO vo : dyList) {
				PageData pd = new PageData<>();
				pd.put("id", vo.getId());
				pd.put("content", vo.getContent());
				pd.put("createTime", vo.getCreateTime());
				pd.put("dynamicUrls", vo.getDynamicUrls());
				pd.put("thumbCount", vo.getThumbCount());
				pdList.add(pd);
			}
			
			log.info("infoMsg:--- 获取用户动态列表开始");
			return reponse.success(pdList);
		} catch (Exception e) {
			log.info("infoMsg:--- 获取用户动态列表开始");
			return reponse.failure(e.getMessage());
		}
	}
	
	
	
}
