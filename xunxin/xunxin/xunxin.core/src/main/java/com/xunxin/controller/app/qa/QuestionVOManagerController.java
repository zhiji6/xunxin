package com.xunxin.controller.app.qa;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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

import com.xunxin.controller.system.BaseController;
import com.xunxin.service.ArecordService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.CommentService;
import com.xunxin.service.app.HtmlHelper;
import com.xunxin.service.app.QASectionService;
import com.xunxin.service.app.qa.AnswerService;
import com.xunxin.service.app.qa.QACollectionRecordService;
import com.xunxin.service.app.qa.QAttentionRecordService;
import com.xunxin.service.app.qa.QuestionService;
import com.xunxin.service.app.qa.XTagLibraryService;
import com.xunxin.service.app.square.QAImageUrlRecordService;
import com.xunxin.service.app.square.UserAccessLinkedRecordService;
import com.xunxin.util.app.JsoupLoadHtmlUtils;
import com.xunxin.util.app.OrderGeneratedUtils;
import com.xunxin.vo.qa.AnswerVO;
import com.xunxin.vo.qa.ArecordVO;
import com.xunxin.vo.qa.QACollectionRecord;
import com.xunxin.vo.qa.QAImageUrlRecord;
import com.xunxin.vo.qa.QAttentionRecord;
import com.xunxin.vo.qa.QuestionVO;
import com.xunxin.vo.qa.UserAccessLinkedRecordVO;
import com.xunxin.vo.qa.XTagLibrary;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;

/**
 * 
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月13日 -- 下午3:35:53
 * @Version 1.0
 * @Description	 Q&A管理中心
 */
@Controller
@RequestMapping(value=Router.PATH+Router.QA.PATH)
public class QuestionVOManagerController extends BaseController{
	
	private static final Logger log = Logger.getLogger(QuestionVOManagerController.class);
	
	@Autowired
	private AppUserService appUserService;
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
	
	/**
	 * 用户编辑Q&A
	 * 
	 * @param userId		用户
	 * @param qa_name		问题名称
	 * @param qa_content	问题内容
	 * @param qa_type		板块名称
	 * @param qa_tags		标签x
	 * @param qa_answers	问题观点
	 * @param linked_recordId	外链记录id
	 * @param img_urls		问题图片地址
	 * @return
	 */
	@RequestMapping(value=Router.QA.PUBLISH_QA_BY_USER,method=RequestMethod.POST)
	@ResponseBody
	public Response publish_QA_by_user(@RequestParam("userId") int userId,@RequestParam("qa_name") String qa_name,
			@RequestParam("qa_content") String qa_content,@RequestParam("qa_type") String qa_type,@RequestParam("qa_tags") String qa_tags,
			@RequestParam("qa_answers") String qa_answers,@RequestParam("linked_recordId") String linked_recordId,@RequestParam("img_urls") String img_urls) {
		log.info("infoMsg:--- 用户发布Q&A开始");
		Response reponse = this.getReponse();
		try {
			Query nameQuery = new Query().addCriteria(Criteria.where("name").is(qa_name));
			QuestionVO nameVo = questionService.findOneByQuery(nameQuery);
			if(nameVo != null) {
				return reponse.failure("该问题已存在，请勿重复提交");
			}
			
			Query typeQuery = new Query().addCriteria(Criteria.where("sectionName").is(qa_type));		//获取模块对象
			short type = qASectionService.findOneByQuery(typeQuery).getSectionType();
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
			
			QuestionVO vo = new QuestionVO();
			vo.setName(qa_name);
			vo.setLinked_recordId(linked_recordId);
			vo.setTags(qa_tags);
			vo.setImg_urls(img_urls);
			vo.setContent(qa_content);
			vo.setType(type);
			vo.setAnswers(IDS.toString());
			//表示用户发布的
			vo.setEditorType((short) 0);
			vo.setUserID(userId);
			vo.setCreateTime(new Date());
			questionService.save(vo);
			
			String[] imgs = img_urls.split(",");
			for(String img : imgs) {
				Query imgQuery = new Query().addCriteria(Criteria.where("url").is(img));
				Update update = new Update().set("url", img);
				qAImageUrlRecordService.updateFirst(imgQuery, update);
			}
			log.info("infoMsg:--- 用户发布Q&A结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:--- 用户发布Q&A失败" + e.getMessage());
			return reponse.failure(e.getMessage());
		}
		
	}
	
	
	/**
	 * 获取链接摘要 TODO 爬虫
	 * 
	 * @param linked_url	http://news.xinhuanet.com/politics/2017-12/18/c_1122130242.htm
	 * @return
	 */
	@RequestMapping(value=Router.QA.GET_TITLE_BY_URL,method=RequestMethod.POST)
	@ResponseBody
	public Response get_title_by_url(@RequestParam("linked_url") String linked_url,@RequestParam("userId") int userId) {
		log.info("infoMsg:--- 获取链接摘要开始");
		Response reponse = this.getReponse();
		PageData pd = new PageData<>();
		Document doc = null;
		String title = "";	  	//标题
		String source = "";		//来源
		String content = "";	//内容
		String image = "";		//图片
		try {
//			if(!linked_url.endsWith(".html") && !linked_url.endsWith(".htm")) {
//				return reponse.failure("您输入的链接有误");
//			}
			//Jsoup获取HTML
			doc = JsoupLoadHtmlUtils.JsoupConnect(linked_url);
			//获取来源
			source =  questionService.getSource(linked_url);
			//获取标题
			Elements tagElement = doc.getElementsByTag("title");
			String drawTitle = HtmlHelper.drawTitle(HtmlHelper.filterContent(doc.html()) + "\r\n");
			if(StringUtils.trim(drawTitle).equals("") && StringUtils.isBlank(drawTitle)) {
				drawTitle = tagElement.text() + "\r\n";
			}
			String[] split = drawTitle.split("-");
			for(int i = 0;i < split.length-1;i++) {
				title += split[i];
			}
			
			if(StringUtils.trim(title).equals("")) {
				title = "未知标题";
			}
			//获取内容
			Document body = Jsoup.parse(doc.body().html());
			System.out.println(body.toString());
			content =  questionService.getContent(linked_url,body);
			if(content == null) {
				content = linked_url;
			}
			
			Elements imgs = doc.getElementsByTag("img");
			if(imgs.size()>0){
				image = imgs.get(0).attr("abs:src");
            }else {
            	System.out.println("该链接没有图片");
			}
			
			//保存获取外链记录
			UserAccessLinkedRecordVO vo = new UserAccessLinkedRecordVO();
			vo.setContent(content);
			vo.setImage(image);
			vo.setLinked_url(linked_url);
			vo.setSource(source);
			vo.setTitle(title);
			userAccessLinkedRecordService.save(vo);
			
			//JSON字符串的拼装
			pd.put("id", vo.getId());			//外链记录id
			pd.put("title", title);
			pd.put("source", source);
			pd.put("content", content);
			pd.put("image", image);
			
			log.info("infoMsg:--- 获取链接摘要结束");
			return reponse.success(pd);
		} catch (Exception e) {
			log.error("errorMsg:--- 获取链接摘要失败" + e.getMessage());
			return reponse.failure(e.getMessage());
		}
		
	}
	
	
	/**
	 * 上传图片(单张)
	 * 
	 * @param photo
	 * @return
	 */
	@RequestMapping(value = Router.QA.UPLOAD_QUESTION_PHOTO, method = RequestMethod.POST)
	@ResponseBody
	public Response upload_question_photo(@RequestParam("userId") int userId,MultipartHttpServletRequest request) {
		log.info("用户完善基本资料开始");
		Response response = this.getReponse();
		BufferedInputStream is = null;  
		BufferedOutputStream out = null;  // 准备好一个输出的对象
		CommonsMultipartFile multipartFile = null;
		String showUrl = "";
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
		        	 String url = "D:\\programmeTools\\nginx-1.12.1\\html\\photo\\qa" + "/" + str + OrderGeneratedUtils.getOrderNo()  + "." + fileExt;
		        	 File newFile = new File(url);
		        	 if(is != null) {
		        		 out = new BufferedOutputStream(new FileOutputStream(newFile));  
		        		 byte[] buffer = new byte[1024];  
		        		 int len = -1;  
		        		 while ((len = is.read(buffer)) != -1) {  
		        			 out.write(buffer, 0, len);  
		        		 }
		        		 String name = str + OrderGeneratedUtils.getOrderNo() + "." + fileExt;	//新文件名
		        		 showUrl = url.replace("D:\\programmeTools\\nginx-1.12.1\\html\\photo\\qa", "http://www.xunxinkeji.cn:8761/photo/qa");
		        		 QAImageUrlRecord record = new QAImageUrlRecord();
		        		 record.setName(name);
		        		 record.setUrl(showUrl);
		        		 record.setUserId(userId);
		        		 qAImageUrlRecordService.save(record);
		        	 }
		         }else {
					return response.failure("该文件类型不能够上传");
		         }
		    }
			log.info("InfoMsg:--- profession_authentication end");
			return response.success(showUrl);
		} catch (Exception e) {
			log.error("errorMsg:--- profession_authentication occur error " + e.getMessage());
			return response.failure(e.getMessage());
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
	 * 用户答题
	 * 
	 * @param userId
	 * @param questionID
	 * @param answers
	 * @param type
	 * @return
	 */
	@RequestMapping(value=Router.QA.USER_ANSWER_QUESTION,method=RequestMethod.POST)
	@ResponseBody
	public Response user_answer_question(@RequestParam("userId") int userId,@RequestParam("questionID") String questionID,
	        @RequestParam("questionID") String answerID) {
		log.info("infoMsg:--- 用户答题开始");
		Response reponse = this.getReponse();
		PageData pd = new PageData<>();
		try {
			QuestionVO questionVO = questionService.findOneById(questionID);
			String nextID = "";
			//下一题id
            Query nextQuery = new Query();
            nextQuery.addCriteria(Criteria.where("type").is(questionVO.getType()));
            nextQuery.with(new Sort(new Order(Direction.DESC,"createTime")));
            List<QuestionVO> questionList = questionService.find(nextQuery);
            for(int i = 0;i < questionList.size();i++) {
                if(questionList.get(i).getId().equals(questionID)) {
                    int indexVo = i;  //当前题的位置
                    if(indexVo + 1 > questionList.size()) {
                        nextID = "最后一题";
                    }else {
                        nextID = questionList.get(indexVo + 1).getId();
                    }
                }
            }
            pd.put("nextID", nextID);
            
			Query arecordQuery = new Query();
			arecordQuery.addCriteria(Criteria.where("answerID").is(userId));
			arecordQuery.addCriteria(Criteria.where("questionID").is(questionID));
			ArecordVO arecordVO = arecordService.findOneByQuery(arecordQuery);
			String answers = questionID + "_" + answerID;
			if(arecordVO == null) {	//是否该题已答,没答则新增记录
				ArecordVO vo = new ArecordVO();
				vo.setQuestionID(questionID);
				vo.setAnswerID(userId);
				vo.setAnswers(answers);  
				vo.setAnswerTime(new Date());
				vo.setType(questionVO.getType());
				arecordService.save(vo);
			}else {		//存在则修改答案
				Update update = new Update().set("answers", answers);
				arecordService.updateFirst(arecordQuery, update);
			}
			log.info("infoMsg:--- 用户答题结束");
			return reponse.success(pd);
		} catch (Exception e) {
			log.error("errorMsg:{--- 用户答题失败:" + e.getMessage() + "----}");
			return reponse.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 用户关注Q&A发布人
	 * 
	 * @param userId
	 * @param questionID
	 * @return
	 */
	@RequestMapping(value=Router.QA.USER_ATTENTION_QUESTION,method=RequestMethod.POST)
	@ResponseBody
	public Response user_attention_question(@RequestParam("userId") int userId,@RequestParam("questionID") String questionID) {
	    log.info("infoMsg:--- 用户关注Q&A发布人开始");
	    Response reponse = this.getReponse();
	    try {
	        QuestionVO vo = questionService.findOneById(questionID);
	        if(vo != null) {
	            Query findQuery = new Query();
	            findQuery.addCriteria(Criteria.where("attentionId").is(vo.getUserID()));
	            findQuery.addCriteria(Criteria.where("userId").is(userId));
	            QAttentionRecord qAttentionRecord = qAttentionRecordService.findOneByQuery(findQuery);
	            
	            if(qAttentionRecord == null) {
	                QAttentionRecord record = new QAttentionRecord();
	                record.setAttentionId(vo.getUserID());
	                record.setUserId(userId);
	                qAttentionRecordService.save(record);
	            }
	        }
	        log.info("infoMsg:--- 用户关注Q&A发布人结束");
	        return reponse.success();
	    } catch (Exception e) {
	        log.error("errorMsg:{--- 用户关注Q&A发布人失败:" + e.getMessage() + "----}");
	        return reponse.failure(e.getMessage());
	    }
	}
	
	
	
	/**
	 * 用户取消关注Q&A发布人
	 * 
	 * @param userId
	 * @param questionID
	 * @return
	 */
	@RequestMapping(value=Router.QA.USER_CANCEL_ATTENTION_QUESTION,method=RequestMethod.POST)
	@ResponseBody
	public Response user_cancel_attention_question(@RequestParam("userId") int userId,@RequestParam("questionID") String questionID) {
	    log.info("infoMsg:--- 用户取消关注Q&A发布人开始");
	    Response reponse = this.getReponse();
	    try {
	        QuestionVO vo = questionService.findOneById(questionID);
	        
	        Query findQuery = new Query();
	        findQuery.addCriteria(Criteria.where("attentionId").is(vo.getUserID()));
	        findQuery.addCriteria(Criteria.where("userId").is(userId));
	        QACollectionRecord qACollectionRecord = qACollectionRecordService.findOneByQuery(findQuery);
	        if(qACollectionRecord != null) {
	            qACollectionRecordService.remove(qACollectionRecord);
	        }
	        log.info("infoMsg:--- 用户取消关注Q&A发布人结束");
	        return reponse.success();
	    } catch (Exception e) {
	        log.error("errorMsg:{--- 用户取消关注Q&A发布人失败:" + e.getMessage() + "----}");
	        return reponse.failure(e.getMessage());
	    }
	}
	
	
	
	/**
	 * 用户收藏Q&A
	 * 
	 * @param userId
	 * @param questionID
	 * @return
	 */
	@RequestMapping(value=Router.QA.USER_COLLECTION_QUESTION,method=RequestMethod.POST)
	@ResponseBody
	public Response user_collection_question(@RequestParam("userId") int userId,@RequestParam("questionID") String questionID) {
		log.info("infoMsg:--- 用户收藏Q&A开始");
		Response reponse = this.getReponse();
		try {
			Query findQuery = new Query();
			findQuery.addCriteria(Criteria.where("questionId").is(questionID));
			findQuery.addCriteria(Criteria.where("userId").is(userId));
			QACollectionRecord qACollectionRecord = qACollectionRecordService.findOneByQuery(findQuery);
			if(qACollectionRecord == null) {
				QACollectionRecord record = new QACollectionRecord();
				record.setQuestionId(questionID);
				record.setUserId(userId);
				qACollectionRecordService.save(record);
			}
			log.info("infoMsg:--- 用户收藏Q&A结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:{--- 用户收藏Q&A失败:" + e.getMessage() + "----}");
			return reponse.failure(e.getMessage());
		}
	}

	
	
	/**
	 * 用户取消收藏Q&A
	 * 
	 * @param userId
	 * @param questionID
	 * @return
	 */
	@RequestMapping(value=Router.QA.USER_CANCEL_COLLECTION_QUESTION,method=RequestMethod.POST)
	@ResponseBody
	public Response user_cancel_collection_question(@RequestParam("userId") int userId,@RequestParam("questionID") String questionID) {
	    log.info("infoMsg:--- 用户取消收藏Q&A开始");
	    Response reponse = this.getReponse();
	    try {
	        Query findQuery = new Query();
//	        ObjectId id = new ObjectId(questionID);
	        findQuery.addCriteria(Criteria.where("questionId").is(questionID));
	        findQuery.addCriteria(Criteria.where("userId").is(userId));
	        QACollectionRecord qACollectionRecord = qACollectionRecordService.findOneByQuery(findQuery);
	        if(qACollectionRecord != null) {
	            qACollectionRecordService.findAndRemove(findQuery);
	        }
	        log.info("infoMsg:--- 用户取消收藏Q&A结束");
	        return reponse.success();
	    } catch (Exception e) {
	        log.error("errorMsg:{--- 用户取消收藏Q&A失败:" + e.getMessage() + "----}");
	        return reponse.failure(e.getMessage());
	    }
	}
	
	
	
	/**
	 * 查看Q&A答题比例
	 * 
	 * @param userId
	 * @param questionID
	 * @return
	 */
	@RequestMapping(value=Router.QA.CHECK_QA_PROPORTION,method=RequestMethod.POST)
	@ResponseBody
	public Response check_qa_proportion(@RequestParam("userId") int userId,@RequestParam("questionID") String questionID) {
		log.info("infoMsg:--- 用户答题开始");
		Response reponse = this.getReponse();
		List<PageData> pdList = new ArrayList<>();
		try {
			QuestionVO questionVO = questionService.findOneById(questionID);
//			if(questionVO != null){
//				Integer totalCount = arecordService.findCountByQuery(new Query().addCriteria(Criteria.where("questionID").is(questionID)));		//已选总数
//				String[] answers = questionVO.getAnswers().replace("[","").replace("]","").split(",");
//				for(String answer : answers) {
//					AnswerVO answerVO = answerService.findOneByQuery(new Query().addCriteria(Criteria.where("id").is(answer.trim())));
//					PageData pd = new PageData<>();
//					pd.put("id", answerVO.getId());
//					pd.put("answer", answerVO.getAnswer());
//					
//					// 创建一个数值格式化对象     
//					NumberFormat numberFormat = NumberFormat.getInstance();     
//					// 设置精确到小数点后2位     
//					numberFormat.setMaximumFractionDigits(0);     
//					String result = numberFormat.format((float)answerVO.getCount()/(float)totalCount*100); 
//					
//					pd.put("proportion", result);		//百分比
//					pdList.add(pd);
//				}
//			}else{
//				return reponse.success("暂无答题信息");
//			}
			
			//TODO 扣用户积分
			UserEntity user = appUserService.findById(userId);
			if(user.getUserExp() > 50) {
				appUserService.user_exp_change(userId, user.getUserExp() - 50);
			}else {
				return reponse.failure("积分不足");
			}
			
			log.info("infoMsg:--- 用户答题结束");
			return reponse.success(pdList);
		} catch (Exception e) {
			log.error("errorMsg:{--- 用户答题失败:" + e.getMessage() + "----}");
			return reponse.failure(e.getMessage());
		}
	}
	
	
	
	/**
	 * 获取Q&A对应板块话题列表
	 * 
	 * @param type
	 * @param pageNo		第几页
	 * @param pageSize		每页多少条
	 * @return
	 */
	@RequestMapping(value=Router.QA.QA_HOT_TOPIC_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Response qa_Hot_topic_list(@RequestParam("type") String type,@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize) {
		log.info("infoMsg:--- 获取Q&A热点话题列表开始");
		Response reponse = this.getReponse();
		List<PageData> pdList = new ArrayList<>();
		try {
			Query typeQuery = new Query().addCriteria(Criteria.where("sectionName").is(type));		//获取模块对象
			short qa_type = qASectionService.findOneByQuery(typeQuery).getSectionType();
			
			Query query = new Query();
			query.addCriteria(Criteria.where("type").is(qa_type));
			query.with(new Sort(new Order(Direction.DESC,"createTime")));
			// 分页
			query.skip((pageNo - 1) * 10).limit(pageSize);
			List<QuestionVO> qaList = questionService.find(query);
			for(QuestionVO vo : qaList) {
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
//				String[] imgs = vo.getImg_urls().split(",");
//				List<QAImageUrlRecord> aAImageUrlRecordList = new ArrayList<>();
//				for(String img : imgs) {
//					QAImageUrlRecord aAImageUrlRecord = qAImageUrlRecordService.findOneByQuery(new Query().addCriteria(Criteria.where("url").is(img)));
//					aAImageUrlRecordList.add(aAImageUrlRecord);
//				}
				pd.put("imgs", vo.getImg_urls());
				int userId = vo.getUserID();
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
				
				pdList.add(pd);
			}
			
			log.info("infoMsg:--- 获取Q&A热点话题列表结束");
			return reponse.success(pdList);
		} catch (Exception e) {
			log.info("errorMsg:{--- 获取Q&A热点话题列表结束:" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 获取Q&A话题详情
	 * 
	 * @param questionID
	 * @return
	 */
	@RequestMapping(value=Router.QA.QA_TOPIC_DETAIL,method=RequestMethod.POST)
	@ResponseBody
	public Response qa_topic_detail(@RequestParam("questionID") String questionID,@RequestParam("userId") String userId) {
		log.info("infoMsg:--- 获取Q&A热点话题列表开始");
		Response reponse = this.getReponse();
		PageData pd = new PageData<>();
		try {
		    QuestionVO vo = questionService.findOneById(questionID);
		    //已收藏，未收藏
		    if(userId == null || StringUtils.trim(userId).equals("")) {
		        pd.put("collectionState", UserEntity.NORMAL);
		    }else{
		        Query collectionQuery = new Query().addCriteria(Criteria.where("questionId").is(questionID)).addCriteria(Criteria.where("userId").is(userId));
		        QACollectionRecord qACollectionRecord = qACollectionRecordService.findOneByQuery(collectionQuery);
		        if(qACollectionRecord != null) {
		            pd.put("collectionState", UserEntity.UNUSUAL);
		        }else {
		            pd.put("collectionState", UserEntity.NORMAL);
		        }
		    }
		    //已关注，未关注
		    if(userId == null || StringUtils.trim(userId).equals("")) {
		        pd.put("attentionState", UserEntity.NORMAL);
		    }else{
		        Query attentionQuery = new Query()
		                .addCriteria(Criteria.where("attentionId").is(vo.getUserID()))
		                .addCriteria(Criteria.where("userId").is(userId));
		        QAttentionRecord qAttentionRecord = qAttentionRecordService.findOneByQuery(attentionQuery);
		        if(qAttentionRecord != null) {
		            pd.put("attentionState", UserEntity.UNUSUAL);
		        }else {
		            pd.put("attentionState", UserEntity.NORMAL);
		        }
		    }
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
//				String[] imgs = vo.getImg_urls().split(",");
//				List<QAImageUrlRecord> aAImageUrlRecordList = new ArrayList<>();
//				for(String img : imgs) {
//					QAImageUrlRecord aAImageUrlRecord = qAImageUrlRecordService.findOneByQuery(new Query().addCriteria(Criteria.where("url").is(img)));
//					aAImageUrlRecordList.add(aAImageUrlRecord);
//				}
			pd.put("imgs", vo.getImg_urls());
			int userID = vo.getUserID();
			UserEntity user = appUserService.findById(userID);
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
			
			//评论数
			Query commentQuery = new Query().addCriteria(Criteria.where("questionId").is(questionID));
			Integer commentCount = commentService.findCountByQuery(commentQuery);
			pd.put("commentCount", commentCount);
			
//			String nextID = ""; 
			//下一题id
//			Query nextQuery = new Query();
//			nextQuery.addCriteria(Criteria.where("type").is(vo.getType()));
//			nextQuery.with(new Sort(new Order(Direction.DESC,"createTime")));
//			List<QuestionVO> questionList = questionService.find(nextQuery);
//			int indexVo = questionList.indexOf(vo);  //当前题的位置
//			if(indexVo + 1 > questionList.size()) {
//			    nextID = "最后一题";
//			}else {
//			    nextID = questionList.get((indexVo + 1)).getId();
//			}
//			pd.put("nextID", nextID);
			
			log.info("infoMsg:--- 获取Q&A热点话题列表结束");
			return reponse.success(pd);
		} catch (Exception e) {
			log.info("errorMsg:{--- 获取Q&A热点话题列表结束:" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 获取观点列表
	 * @return
	 */
	@RequestMapping(value=Router.QA.GET_ANSWER_LIST,method=RequestMethod.POST)
	@ResponseBody

	public Response get_answer_list() {
		log.info("infoMsg:--- 获取观点列表开始");
		Response reponse = this.getReponse();
		PageData pd = new PageData();
		try {
			//常用观点
			Query hostQuery = new Query();
			hostQuery.addCriteria(Criteria.where("type").is(1));
			List<AnswerVO> hostList = answerService.find(hostQuery);
			pd.put("host", hostList);	
			
			//热门观点
			Query usualQuery = new Query();
			usualQuery.addCriteria(Criteria.where("type").is(2));
			List<AnswerVO> usualList = answerService.find(usualQuery);
			pd.put("usual", usualList);	
			
			log.info("infoMsg:--- 获取观点列表结束");
			return reponse.success(pd);
		} catch (Exception e) {
			log.error("errorMsg:{--- 获取观点列表失败:" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
		
	}
	
	
	/**
	 * 获取标签列表
	 * @return
	 */
	@RequestMapping(value=Router.QA.GET_TAG_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Response get_tag_list() {
		log.info("infoMsg:--- 获取标签列表开始");
		Response reponse = this.getReponse();
		List<PageData> pdList = new ArrayList<>();
		try {
			
			Query query = new Query();
			query.with(new Sort(new Order(Direction.DESC,"createTime")));
			List<XTagLibrary> tagList = xTagLibraryService.find(query);
			for(XTagLibrary tag: tagList) {
				PageData pd = new PageData<>();
				pd.put("id", tag.getId());
				pd.put("tagName", tag.getTagName());
				pdList.add(pd);
			}
			
			log.info("infoMsg:--- 获取标签列表结束");
			return reponse.success(pdList);
		} catch (Exception e) {
			log.error("errorMsg:{--- 获取标签列表失败:" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
		
	}
	

	
}
