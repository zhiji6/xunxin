package com.xunxin.controller.system;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.mongodb.framework.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.xunxin.config.GenerateSequenceUtil;
import com.xunxin.service.AdminService;
import com.xunxin.service.ArecordService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.CommentService;
import com.xunxin.service.app.HtmlHelper;
import com.xunxin.service.app.QASectionService;
import com.xunxin.service.app.qa.AnswerService;
import com.xunxin.service.app.qa.ExcelUploadRecordService;
import com.xunxin.service.app.qa.QACollectionRecordService;
import com.xunxin.service.app.qa.QuestionService;
import com.xunxin.service.app.qa.XTagLibraryService;
import com.xunxin.service.app.square.QAImageUrlRecordService;
import com.xunxin.service.app.square.UserAccessLinkedRecordService;
import com.xunxin.util.MD5_UTIL;
import com.xunxin.util.app.OrderGeneratedUtils;
import com.xunxin.vo.admin.Admin;
import com.xunxin.vo.qa.AnswerVO;
import com.xunxin.vo.qa.ArecordVO;
import com.xunxin.vo.qa.CommentVO;
import com.xunxin.vo.qa.ExcelUploadRecordVO;
import com.xunxin.vo.qa.QACollectionRecord;
import com.xunxin.vo.qa.QAImageUrlRecord;
import com.xunxin.vo.qa.QASection;
import com.xunxin.vo.qa.QuestionVO;
import com.xunxin.vo.qa.UserAccessLinkedRecordVO;
import com.xunxin.vo.qa.XTagLibrary;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Route;
import com.xunxin.web.api.bean.Router;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月30日 -- 下午2:17:46
 * @Version 1.0
 * @Description		Q&A管理中心
 */
@Controller
@RequestMapping(value=Route.PATH+Route.QA.PATH)
public class QuestionManagerController extends BaseController {
	
	private final static Logger log = Logger.getLogger(QuestionManagerController.class);
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private AnswerService answerService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private QASectionService qASectionService;
	@Autowired
	private ExcelUploadRecordService excelUploadRecordService;
	@Autowired
	private XTagLibraryService xTagLibraryService;
	@Autowired
	private ArecordService arecordService;
	@Autowired
	private UserAccessLinkedRecordService userAccessLinkedRecordService;
	@Autowired
	private QAImageUrlRecordService qAImageUrlRecordService;
	@Autowired
	private QACollectionRecordService qACollectionRecordService;
	
	/**
	 * Q&A管理
	 * @return
	 */
	@RequestMapping(value=Route.QA.QA_QUESTION_MANAGER,method=RequestMethod.GET)
	public ModelAndView qa_question_manager() {
		log.info("");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/qa/qa_question_manager");
		return mv;
	}
	
	/**
	 * Q&A新增
	 * @return
	 */
	@RequestMapping(value=Route.QA.QA_ADD,method=RequestMethod.GET)
	public ModelAndView qa_add() {
		log.info("");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/qa/qa_add");
		return mv;
	}
	
	/**
	 * UEditor编辑器初始化
	 * @return
	 */
	@RequestMapping(value=Route.QA.UEDITOR_LOADING,method=RequestMethod.GET)
	public ModelAndView ueditor_loading() {
	    ModelAndView mv = this.getModelAndView();
        mv.setViewName("plugins/ueditor/jsp/controller");
        return mv;
	}
	
	/**
	 * Q&A编辑
	 * @return
	 */
	@RequestMapping(value=Route.QA.QA_EDIT,method=RequestMethod.GET)
	public ModelAndView qa_edit() {
	    log.info("");
	    ModelAndView mv = this.getModelAndView();
	    PageData pageData = this.getPageData();
	    PageData pd = new PageData<>();
	    try {
	        String questionID = pageData.getString("id");
	        QuestionVO vo = questionService.findOneById(questionID);
            pd.put("questionID", questionID);
            //如有外链，外链的展示
            String linked_recordId = vo.getLinked_recordId();
            if(linked_recordId != null && !StringUtils.trim(linked_recordId).equals("") && !StringUtils.isBlank(linked_recordId)) {
                UserAccessLinkedRecordVO record = userAccessLinkedRecordService.findOneById(linked_recordId);
                pd.put("linked_recordId", linked_recordId);
                pd.put("linked_url", record.getLinked_url());
                pd.put("content", record.getContent());
                pd.put("image", record.getImage());
                pd.put("source", record.getSource());
                pd.put("title", record.getTitle());
            }
            //外链之外的其他资源
            pd.put("name", vo.getName());
            pd.put("qa_content", vo.getContent());
            pd.put("HtmlContent", vo.getHtmlContent().toString());
            Query sectionQuery = new Query().addCriteria(Criteria.where("sectionType").is(vo.getType()));
            QASection section = qASectionService.findOneByQuery(sectionQuery);
            pd.put("sectionName", section.getId());
            //观点
            String[] answers = vo.getAnswers().replace("[","").replace("]","").split(",");
            List<AnswerVO> answerVOList = new ArrayList<>();
            for(String answer : answers) {
                AnswerVO answerVO = answerService.findOneById(answer.trim());
                answerVOList.add(answerVO);
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
            String[] imgs = vo.getImg_urls().split(",");
            List<QAImageUrlRecord> aAImageUrlRecordList = new ArrayList<>();
            for(String img : imgs) {
                QAImageUrlRecord aAImageUrlRecord = qAImageUrlRecordService.findOneByQuery(new Query().addCriteria(Criteria.where("url").is(img)));
                aAImageUrlRecordList.add(aAImageUrlRecord);
            }
            pd.put("images", aAImageUrlRecordList);
            int userId = vo.getUserID();
            if(vo.getEditorType() == 1){
                Admin admin = adminService.findUserById(userId);
                pd.put("gender", admin.getGender());
                pd.put("nickName", admin.getNickName());
            }else {
                UserEntity user = appUserService.findById(userId);
                pd.put("gender", user.getGender());
                pd.put("nickName", user.getNickName());
            }
            
            //收藏数
            Query collectionQuery = new Query().addCriteria(Criteria.where("id").is(vo.getId()));
            Integer collectionCount = qACollectionRecordService.findCountByQuery(collectionQuery);
            pd.put("collectionCount", collectionCount);
            
            //已选数
            Query selectQuery = new Query().addCriteria(Criteria.where("questionID").is(vo.getId()));
            Integer selectCount = arecordService.findCountByQuery(selectQuery);
            pd.put("selectCount", selectCount);
	        
	        
	        mv.setViewName("jsp/xunxin/qa/qa_edit");
	        mv.addObject("pd",pd);
	        return mv;
        } catch (Exception e) {
            log.info("erroroMsg:{--- 跳转Q&A编辑页面:" + e.getMessage() + "---}");
            return new ModelAndView("error");
        }
	}
	
	
	
	/**
	 * Q&A浏览
	 * @return
	 */
	@RequestMapping(value=Route.QA.QA_VIEW,method=RequestMethod.GET)
	public ModelAndView qa_view() {
		log.info("infoMsg:--- 获取Q&A详情开始");
		PageData pageData = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData<>();
		try {
			String id = pageData.getString("id");
			QuestionVO vo = questionService.findOneById(id);
			
			//如有外链，外链的展示
			String linked_recordId = vo.getLinked_recordId();
			if(linked_recordId != null && !StringUtils.trim(linked_recordId).equals("") && !StringUtils.isBlank(linked_recordId)) {
				UserAccessLinkedRecordVO record = userAccessLinkedRecordService.findOneById(linked_recordId);
				pd.put("linked_recordId", linked_recordId);
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
			List<AnswerVO> answerVOList = new ArrayList<>();
			for(String answer : answers) {
				AnswerVO answerVO = answerService.findOneById(answer.trim());
				answerVOList.add(answerVO);
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
			List<PageData> aAImageUrlRecordList = new ArrayList<>();
			if(vo.getImg_urls() != null) {
			    String[] imgs = vo.getImg_urls().split(",");
			    for(String img : imgs) {
			        QAImageUrlRecord aAImageUrlRecord = qAImageUrlRecordService.findOneByQuery(new Query().addCriteria(Criteria.where("url").is(img.trim())));
			        PageData imgPd = new PageData<>();
			        imgPd.put("url", img.trim());
                    aAImageUrlRecordList.add(imgPd);
			    }
			}
			pd.put("imgs", aAImageUrlRecordList);
			int userId = vo.getUserID();
			if(vo.getEditorType() == 1){
			    Admin admin = adminService.findUserById(userId);
			    pd.put("gender", admin.getGender());
			    pd.put("nickName", admin.getNickName());
			}else {
			    UserEntity user = appUserService.findById(userId);
			    pd.put("gender", user.getGender());
			    pd.put("nickName", user.getNickName());
			}
			
			//收藏数
			Query collectionQuery = new Query().addCriteria(Criteria.where("id").is(vo.getId()));
			Integer collectionCount = qACollectionRecordService.findCountByQuery(collectionQuery);
			pd.put("collectionCount", collectionCount);
			
			//已选数
			Query selectQuery = new Query().addCriteria(Criteria.where("questionID").is(vo.getId()));
			Integer selectCount = arecordService.findCountByQuery(selectQuery);
			pd.put("selectCount", selectCount);
			
			
			mv.setViewName("jsp/xunxin/qa/qa_view");
			mv.addObject("pd", pd);
			log.info("infoMsg:--- 获取Q&A详情结束");
			return mv;
		} catch (Exception e) {
			log.error("errorMsg:{--- 获取Q&A详情失败:" + e.getMessage() + "---}");
		}
		return new ModelAndView("error");
	}
	
	/**
     * Q&A审核
     * @return
     */
    @RequestMapping(value=Route.QA.QA_AUDIT_MANAGER,method=RequestMethod.GET)
    public ModelAndView qa_audit_manager() {
        log.info("");
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("jsp/xunxin/qa/qa_audit_manager");
        return mv;
    }
    /**
     * 获取 Q&A审核列表(分页)
     * @return
     */
    @RequestMapping(value=Route.QA.QA_AUDIT_MANAGER_LIST,method=RequestMethod.POST)
    @ResponseBody
    public Object qa_audit_manager_list(QuestionVO questionVO,Integer typeQuery) {
        log.info("infoMsg：--- 获取 Q&A审核列表开始");
        try {
            Pagination<QuestionVO> pagination = questionService.findPageList(questionVO,typeQuery);
            Map<String, Object> page = new HashMap<String,Object>();
            page.put("page", pagination.getPageNo());
            page.put("total", pagination.getTotalCount());
            page.put("rows", pagination.getDatas());
            log.info("infoMsg：--- 获取 Q&A审核列表结束");
            return page;
        } catch (Exception e) {
            log.error("errorMsg:--- 获取 Q&A审核列表失败");
            return null;
        }
    }
    /**
     *  Q&A审核查询
     * @return
     */
    @RequestMapping(value=Route.QA.QA_AUDIT_MANAGER_TO_EXAMINE)
    public ModelAndView qa_audit_manager_to_examine(String id,Model model,Integer type ,String notId) {
        log.info("进入Q&A审核查询");
        ModelAndView mv = this.getModelAndView();
        QuestionVO question = questionService.findOneById(id,type,notId);
        try {
        	//图片
//			List<QAImageUrlRecord> aAImageUrlRecordList = new ArrayList<>();
//			if(question != null && question.getImg_urls() != null) {
//			    String[] imgs = question.getImg_urls().split(",");
//			    for(String img : imgs) {
//			        QAImageUrlRecord aAImageUrlRecord = qAImageUrlRecordService.findOneByQuery(new Query().addCriteria(Criteria.where("url").is(img)));
//			        aAImageUrlRecordList.add(aAImageUrlRecord);
//			    }
//			}
        	//图片
        	List<QAImageUrlRecord> aAImageUrlRecordList = new ArrayList<>();
			if(question != null && question.getImg_urls() != null) {
			    String[] imgs = question.getImg_urls().split(",");
			    for(String img : imgs) {
			        QAImageUrlRecord aAImageUrlRecord = qAImageUrlRecordService.findOneByQuery(new Query().addCriteria(Criteria.where("url").is(img.trim())));
                    aAImageUrlRecordList.add(aAImageUrlRecord);
			    }
			}

			if(question != null && question.getLinked_recordId() != null && !StringUtils.trim(question.getLinked_recordId()).equals("") && !StringUtils.isBlank(question.getLinked_recordId())) {
				UserAccessLinkedRecordVO record = userAccessLinkedRecordService.findOneById(question.getLinked_recordId());
				 model.addAttribute("link", record);
			}
			//收藏数
			if(question != null){
				Query collectionQuery = new Query().addCriteria(Criteria.where("id").is(question.getId()));
				Integer collectionCount = qACollectionRecordService.findCountByQuery(collectionQuery);
				model.addAttribute("collectionCount", collectionCount);
			}
			//已选数
			if(question != null){
				Query selectQuery = new Query().addCriteria(Criteria.where("questionID").is(question.getId()));
				Integer selectCount = arecordService.findCountByQuery(selectQuery);
				model.addAttribute("selectCount", selectCount);
			}
            List<AnswerVO> answers = answerService.findAnswerVOS(question);
            List<XTagLibrary> tags = xTagLibraryService.findTags(question);
            model.addAttribute("question", question);
            model.addAttribute("answers", answers);
            model.addAttribute("tags", tags);
            model.addAttribute("imgs", aAImageUrlRecordList);
            mv.setViewName("jsp/xunxin/qa/qa_audit_manager_form");
            log.info("进入Q&A审核查询结束");
            return mv;
        } catch (Exception e) {
            e.printStackTrace();
            return mv;
        }

    }
    /**
     *  Q&A审核
     * @return
     */
    @RequestMapping(value=Route.QA.QA_AUDIT_MANAGER_EXAMINE,method=RequestMethod.POST)
    @ResponseBody
    public Object qa_audit_manager_examine(String id,Integer status,String content,Integer type) {
        Response reponse = this.getReponse();
        log.info("infoMsg：---  Q&A审核开始");
        try {
            if(id == null || id.trim().equals("") || status == null){
                return reponse.failure("error");
            }
            questionService.audit(id,status,content,type);
            log.info("infoMsg：---  Q&A审核结束");
             return reponse.success("OK");
        } catch (Exception e) {
            log.error("errorMsg:---  Q&A审核失败");
            return reponse.failure(e.getMessage());
        }
    }
	
	
	/**
	 * Q&A上传excel页面
	 * @return
	 */
	@RequestMapping(value=Route.QA.QA_EXCEL_IMPORT,method=RequestMethod.GET)
	public ModelAndView qa_excel_import() {
		log.info("");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/qa/qa_excel_import");
		return mv;
	}
	
	/**
	 * 上传excel
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value=Route.QA.UPLOAD_EXCEL,method=RequestMethod.POST)
	@ResponseBody
	public Response upload_excel(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
		log.info("infoMsg:---Excel文件上传开始");
		Response reponse = this.getReponse();
		try {
			// 判断文件是否为空  
	        if (!file.isEmpty()) { 
	        	String fileName = file.getOriginalFilename();
	        	String[] fileExts = {"xls", "xlsx"};
	        	String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		        	if (Arrays.binarySearch(fileExts, fileExt) != -1) {
	                // 文件保存路径  
//		        	String filePath = "D:\\programmeTools\\nginx-1.12.1\\file\\excel" + "/" + fileName;
		        	String filePath = "/data01/file/excel" + "/" + fileName;
	                // 转存文件  
	                File fi = new File(filePath);
	                file.transferTo(fi);
	                if(!fi.exists()){
	                	fi.delete();
	                }
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                ExcelUploadRecordVO record = new ExcelUploadRecordVO();
	                record.setFileName(fileName);
	                record.setFileUrl(filePath);
	                record.setUploadMark("{---" + fileName + "---}" + "于:" + sdf.format(new Date()) + "上传。");
	                excelUploadRecordService.save(record);
                	
	                return reponse.success("导入成功");
		        } else {
		        	return reponse.failure("文件类型有误");
		        }
	        }else {
	        	return reponse.failure("文件为空");
	        }
		} catch (Exception e) {
			log.error("errorMsg:--- excel文件上传失败" + e.getMessage());
			return reponse.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 导入上传Excel的数据
	 * @return
	 */
	@RequestMapping(value=Route.QA.IMPORT_EXCEL_DATEBASE,method=RequestMethod.POST)
	@ResponseBody
	public Response import_excel_database() {
		log.info("infoMsg：---Excel数据导入开始");
		Response reponse = this.getReponse();
		Admin admin = getAdmin();
		PageData pageData = this.getPageData();
		try {
			//问题名称
			String name = pageData.getString("name");	
			//板块名
			String sectionName = pageData.getString("sectionName");	
			//观点列表
			String answers = pageData.getString("answers").toString();		
			
			List<String> aList = (List<String>) JSONArray.parse(answers);
			Set<String> IDS = new HashSet<>();
			for(String answer : aList) {
				AnswerVO vo = answerService.findOneByQuery(new Query().addCriteria(Criteria.where("answer").is(answer)));
				if(vo == null) {
					AnswerVO av = new AnswerVO();
					av.setType(3);
					av.setCount(1);
					av.setAnswer(answer);
					answerService.save(av);
					IDS.add(av.getId());
				}else {
					vo.setCount(vo.getCount() + 1);
					Update.update("count", vo.getCount() + 1);
					IDS.add(vo.getId());
				}
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	         
			Query query = new Query().with(new Sort(new Order(Direction.DESC, "createTime")));
			query.limit(1);
	        ExcelUploadRecordVO vo =  excelUploadRecordService.findOneByQuery(query);
	         
         	//获取question部分	
	        String[] original = name.split("&");
	        String preStr = original[0].toString();
	        String endStr = original[1].toString();
	        
         	List<List<Object>> qList = readExcel(new File(vo.getFileUrl()),0);
         	for(List<Object> qc : qList) {
         		String explain = (String) qc.get(0);	//问题名称
         		String questionName = preStr + explain + endStr;
         		
         		short type = qASectionService.findOneByQuery(new Query().addCriteria(Criteria.where("id").is(sectionName))).getSectionType();
         		Query sec_query = new Query();
        		sec_query.addCriteria(Criteria.where("name").is(name));
        		sec_query.addCriteria(Criteria.where("type").is(type));
         		QuestionVO qaVO = questionService.findOneByQuery(sec_query);
         		if(null == qaVO) {
         			QuestionVO questionVO = new QuestionVO();
         			questionVO.setName(questionName);
         			questionVO.setAnswers(IDS.toString());
         			questionVO.setType(type);
         			questionVO.setIndexNo(GenerateSequenceUtil.generateSequenceNo());
         			questionVO.setStatus((short) 1);
         			questionVO.setEditorType((short) 1);
         			questionVO.setUserID(admin.getId());
         			questionVO.setReleaseTime(new Date());
         			questionVO.setRemark("后台于:---" +sdf.format(new Date()) + "---导入该数据");
         			questionService.save(questionVO);
         		}else {
         			return reponse.failure("该QA已存在" + "名称为:-" + name);
         		}
         	}
			
			log.info("infoMsg：---Excel数据导入结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:--- Excel数据导入失败");
			return reponse.failure(e.getMessage());
		}
		
	}
	
	
	/**
	 * 获取 Q&A列表(分页)
	 * @return
	 */
	@RequestMapping(value=Route.QA.FULL_QA_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Object full_qa_list() {
		log.info("infoMsg：--- 获取 Q&A列表开始");
		PageData pd = this.getPageData();
		List<PageData> pdList = new ArrayList<>();
		try {
			Query query = new Query();
			if(!StringUtils.isBlank(pd.getString("name")) && !StringUtils.trim(pd.getString("name")).equals("")) {
				query.addCriteria(Criteria.where("name").regex(pd.getString("name")));
			}
			if(!StringUtils.isBlank(pd.getString("status")) && !StringUtils.trim(pd.getString("status")).equals("")) {
			    query.addCriteria(Criteria.where("status").is((short) Integer.parseInt(pd.getString("status"))));
			}
			if(!StringUtils.isBlank(pd.getString("isHot")) && !StringUtils.trim(pd.getString("isHot")).equals("")) {
			    query.addCriteria(Criteria.where("isHot").is(Integer.parseInt(pd.getString("isHot"))));
			}
			if(!StringUtils.isBlank(pd.getString("sectionName")) && !StringUtils.trim(pd.getString("sectionName")).equals("")) {
				short type = qASectionService.findOneById(pd.getString("sectionName")).getSectionType();
				query.addCriteria(Criteria.where("type").is(type));
			}
			query.with(new Sort(new Order(Direction.DESC,"createTime")));
			int pageNumber = pd.getPageNumber(); // 获取页条数(当前页数)
			int pageSize = pd.getPageSize(); // 获取页码(每页多少条)
			// 分页
			query.skip((pageNumber - 1) * pageSize).limit(pageSize);
			List<QuestionVO> qaList = questionService.find(query);
			for(QuestionVO vo : qaList) {
				PageData newPd = new PageData<>();
				newPd.put("name", vo.getName());
				Query q = new Query();
				q.addCriteria(Criteria.where("sectionType").is(vo.getType()));
				QASection section = qASectionService.findOneByQuery(q);
				newPd.put("id", vo.getId());
				newPd.put("name", vo.getName());
				newPd.put("sectionName", section.getSectionName());
				newPd.put("indexNo", vo.getIndexNo());
//				newPd.put("userID", vo.getUserID());
				if(vo.getEditorType() == 1) {
				    Admin admin = adminService.findUserById(vo.getUserID());
				    newPd.put("userID", admin.getNickName());
                }else if(vo.getEditorType() == 0){
                    UserEntity user = appUserService.findById(vo.getUserID());
                    newPd.put("userID", user.getNickName());
                }
				newPd.put("status", vo.getStatus());
				newPd.put("isHot", vo.getIsHot());
				newPd.put("content", vo.getContent());
				newPd.put("releaseTime", vo.getReleaseTime());
				newPd.put("remark", vo.getRemark());
				pdList.add(newPd);
			}
			
//			PageHelper.startPage(PaginationContext.getPageNum(), PaginationContext.getPageSize());
//	        List<UserAuthentication> list = userAuthenticationDao.findPageList(userAuthentication);
//	        return new PageInfo<UserAuthentication>(list);

			Query countQuery = new Query();
			countQuery.with(new Sort(new Order(Direction.DESC,"createTime")));
			Integer count = questionService.findCountByQuery(countQuery);
			
			PageHelper.startPage(pd.getPageNumber(), pd.getPageSize());
			
			PageData data = new PageData<>();
			data.put("total", count);
			data.put("rows", pdList);
			log.info("infoMsg：--- 获取 Q&A列表结束");
			return data;
		} catch (Exception e) {
			log.error("errorMsg:--- 获取 Q&A列表失败");
			return null;
		}
	}
	
	
	/**
	 * 获取所有板块列表
	 * @return
	 */
	@RequestMapping(value=Route.QA.FULL_QA_SECTION_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Object full_qa_section_list() {
		PageData pageData = this.getPageData();
		List<PageData> pdList = new ArrayList<>();
		List<QASection> qaList = qASectionService.find(new Query().with(new Sort(new Order(Direction.ASC, "sectionType"))));
		for(QASection qa : qaList) {
			PageData pd = new PageData<>();
			pd.put("ID", qa.getId());
			pd.put("TYPE", qa.getSectionType());
			pd.put("TEXT", qa.getSectionName());
			pdList.add(pd);
		}
		return pdList;
	}
	
	
	/**
	 * 添加观点
	 * @return
	 */
	@RequestMapping(value=Route.QA.ADD_ANSWER,method=RequestMethod.POST)
	@ResponseBody
	public Response add_answer(@RequestParam("answer") String answer,@RequestParam("type") int type) {
		log.info("infoMsg:--- 添加观点开始");
		Response reponse = this.getReponse();
		try {
			AnswerVO an = answerService.findOneByQuery(new Query().addCriteria(Criteria.where("answer").is(answer)));
			if(an == null) {
				AnswerVO vo = new AnswerVO();
				vo.setAnswer(answer);
				vo.setCount(1);
				vo.setType(type);
				answerService.save(vo);
			}
			log.info("infoMsg:--- 添加观点结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:{--- 添加观点失败:" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
	
	}
	
	
	/**
	 * 添加标签
	 * @return
	 */
	@RequestMapping(value=Route.QA.ADD_TAG,method=RequestMethod.POST)
	@ResponseBody
	public Response add_tag(@RequestParam("tagName") String tagName,@RequestParam("type") short type) {
		log.info("infoMsg:--- 添加标签开始");
		Response reponse = this.getReponse();
		try {
			XTagLibrary xTag = xTagLibraryService.findOneByQuery(new Query().addCriteria(Criteria.where("tagName").is(tagName)));
			if(xTag == null) {
				XTagLibrary tag = new XTagLibrary();
				tag.setTagName(tagName);
				tag.setType(type);
				xTagLibraryService.save(tag);
			}
			log.info("infoMsg:--- 添加标签结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:{--- 添加标签失败:" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
		
	}
	

    /**
     * 获取链接摘要 TODO 爬虫
     * 
     * @param linked_url    http://news.xinhuanet.com/politics/2017-12/18/c_1122130242.htm
     * @return
     */
    @RequestMapping(value=Route.QA.SPIDER_LINKED_URL,method=RequestMethod.POST)
    @ResponseBody
    public Response spider_linked_url() {
        log.info("infoMsg:--- 获取链接摘要开始");
        PageData pageData = this.getPageData();
        Response reponse = this.getReponse();
        PageData pd = new PageData<>();
        Document doc = null;
        String title = "";      //标题
        String source = "";     //来源
        String content = "";    //内容
        String image = "";      //图片
        try {
            
            int adminId = getAdmin().getId();
            String linked_url = pageData.getString("linked_url");
            
            //Jsoup获取HTML
            doc = Jsoup.connect(linked_url.trim())
                    .timeout(6000)
                    .data("query", "Java")   // 请求参数
                    .userAgent("I ’ m jsoup") // 设置 User-Agent 
                    .cookie("auth", "token") // 设置 cookie  
                    .get();
            //获取来源
            source =  questionService.getSource(linked_url);
            //获取标题
            Document body = Jsoup.parse(doc.body().html());
            Elements tagElement = doc.getElementsByTag("title");
            if(tagElement.text() != null) {
                title = tagElement.text()+"\r\n";
            }else {
                title = HtmlHelper.drawTitle(HtmlHelper.filterContent(doc.html()))+"\r\n";
            }
            if(StringUtils.trim(title).equals("")) {
                title = "未知标题";
            }
            //获取内容
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
            pd.put("id", vo.getId());           //外链记录id
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
    public Response upload_question_photo(MultipartHttpServletRequest request) {
        log.info("infoMsg:--- 后台上传Q&A照片开始");
        Response response = this.getReponse();
        PageData pageData = this.getPageData();
        Admin admin = this.getAdmin();
        BufferedInputStream is = null;  
        BufferedOutputStream out = null;  // 准备好一个输出的对象
        String showUrl = "";
        String url = "";
        try {
            // 获得文件
            MultipartFile file = request.getFile("file");
            String fileName = file.getOriginalFilename();     //原文件名
            
            String[] fileExts = {"jpg", "png"};
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            if (Arrays.binarySearch(fileExts, fileExt) != -1) {
                InputStream inputStream = file.getInputStream();
                is = new BufferedInputStream(inputStream);
//                url = "D:\\programmeTools\\nginx-1.12.1\\html\\photo\\qa" + "/" + MD5_UTIL.string2MD5(OrderGeneratedUtils.getOrderNo()) + "." + fileExt;
                url = "/data01/nginxdata/photo/qa" + "/" + MD5_UTIL.string2MD5(OrderGeneratedUtils.getOrderNo()) + "." + fileExt;
                File newFile = new File(url);
                if(is != null) {
                     out = new BufferedOutputStream(new FileOutputStream(newFile));  
                     byte[] buffer = new byte[1024];  
                     int len = -1;  
                     while ((len = is.read(buffer)) != -1) {  
                         out.write(buffer, 0, len);  
                     }
                     String name = MD5_UTIL.string2MD5(OrderGeneratedUtils.getOrderNo()) + "." + fileExt;  //新文件名
//                     showUrl = url.replace("D:\\programmeTools\\nginx-1.12.1\\html\\photo\\qa", "http://localhost:8761/photo/qa");
//                     showUrl = url.replace("D:\\programmeTools\\nginx-1.12.1\\html\\photo\\qa", "http://192.168.1.122:8761/photo/qa");
                     showUrl = url.replace("/data01/nginxdata/photo/qa", "http://www.xunxinkeji.cn:8761/photo/qa");
                     QAImageUrlRecord record = new QAImageUrlRecord();
                     record.setName(name);
                     record.setUrl(showUrl);
                     record.setUserId(admin.getId());
                     qAImageUrlRecordService.save(record);
                 }
             }else {
                return response.failure("该文件类型不能够上传");
             }
            log.info("InfoMsg:--- 后台上传Q&A照片结束");

            return response.success(showUrl);
        } catch (Exception e) {
            log.error("errorMsg:--- 后台上传Q&A照片失败 " + e.getMessage());
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
	 * 添加 Q&A(新增)
	 * @return
	 */
	@RequestMapping(value=Route.QA.PUBLISH_QA_TO_REPERTORY,method=RequestMethod.POST)
	@ResponseBody
	public Response publish_qa_to_repertory() {
		log.info("infoMsg：--- 获取 Q&A列表开始");
		Response reponse = this.getReponse();
		PageData pageData = this.getPageData();
		Admin admin = getAdmin();
		List<PageData> pdList = new ArrayList<>();
		try {
			String name = pageData.getString("name");		//问题名称
			String sectionName = pageData.getString("sectionName");		//板块名
			String linked_recordId = pageData.getString("linked_recordId");		//外链记录id
			String content = pageData.getString("content");		//板块名
			
			String jsonContent = HtmlHelper.getImagesFromContent(content);     //存取json文本
			String qa_tags = pageData.getString("tags").toString();		//标签列表
			String qa_answers = pageData.getString("answers").toString().replace("[", "").replace("]", "").trim();		//观点列表
			String img_urls = HtmlHelper.getImgStr(content).toString().replace("[", "").replace("]", "").trim();     //图片列表
			
			Query nameQuery = new Query().addCriteria(Criteria.where("name").is(name));
			QuestionVO nameVo = questionService.findOneByQuery(nameQuery);
			if(nameVo != null) {
				return reponse.failure("该问题已存在，请勿重复提交");
			}
			
//			Query typeQuery = new Query().addCriteria(Criteria.where("id").is(sectionName));		
			short type = qASectionService.findOneById(sectionName).getSectionType();   //获取板块type
			if(qa_tags != null) {
			    //解析标签
			    String[] tags = qa_tags.replace("[", "").replace("]", "").trim().split(",");
			    for(String tag : tags) {
			        XTagLibrary xTag = xTagLibraryService.findOneByQuery(new Query().addCriteria(Criteria.where("tagName").is(tag)));
			        if(xTag == null) {
			            XTagLibrary t = new XTagLibrary();
			            t.setTagName(tag);
			            t.setType(type);
			            xTagLibraryService.save(t);
			        }
			    }
			}
			//解析观点
			String[] answers = qa_answers.replace("[", "").replace("]", "").trim().split(",");
			List<String> IDS = new ArrayList<>();
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
			vo.setName(name);
			vo.setLinked_recordId(linked_recordId);
			vo.setTags(qa_tags);
			vo.setImg_urls(img_urls);
			vo.setContent(jsonContent);
			vo.setIndexNo(GenerateSequenceUtil.generateSequenceNo());;
			vo.setType(type);
			vo.setAnswers(IDS.toString());
			vo.setReleaseTime(new Date());
			//表示后台编辑发布
			vo.setEditorType((short) 1);
			vo.setStatus((short) 0);
			vo.setUserID(admin.getId());
			vo.setHtmlContent(content);
			questionService.save(vo);
			
			String[] imgs = img_urls.split(",");
			for(String img : imgs) {
				Query imgQuery = new Query().addCriteria(Criteria.where("url").is(img.trim()));
				Update update = new Update().set("url", img.trim());
				qAImageUrlRecordService.updateFirst(imgQuery, update);
			}
			log.info("infoMsg：--- 获取 Q&A列表结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:{--- 获取 Q&A列表失败:" + e.getMessage()+ "---}");
			return reponse.failure(e.getMessage());
		}
	}
	

	/**
	 * 修改 Q&A(修改)
	 * @return
	 */
	@RequestMapping(value=Route.QA.UPDATE_QA_TO_REPERTORY,method=RequestMethod.POST)
	@ResponseBody
	public Response update_qa_to_repertory() {
		log.info("infoMsg：--- 获取 Q&A列表开始");
		Response reponse = this.getReponse();
		Admin admin = getAdmin();
		PageData pageData = this.getPageData();
		List<PageData> pdList = new ArrayList<>();
		try {
			String id = pageData.getString("id");		//问题id
			String name = pageData.getString("name");		//问题名称
			String sectionName = pageData.getString("sectionName");		//板块名
			String linked_recordId = pageData.getString("linked_recordId");		//外链记录id
			String content = pageData.getString("content");		//板块名
			String jsonContent = HtmlHelper.getImagesFromContent(content);     //存取json文本
			String qa_tags = pageData.getString("qa_tags").toString();		//标签列表
			String qa_answers = pageData.getString("answers").toString();		//观点列表
			String img_urls = HtmlHelper.getImagesFromContent(content);		//图片列表
			
			Query nameQuery = new Query().addCriteria(Criteria.where("id").is(id));
			QuestionVO vo = questionService.findOneByQuery(nameQuery);
			
			Query typeQuery = new Query().addCriteria(Criteria.where("sectionName").is(sectionName));		//获取模块对象
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
			//修改Q&A
			Update update = new Update();
			update.set("name", name);
			update.set("linked_recordId", linked_recordId);
			update.set("tags", qa_tags);
			update.set("img_urls", img_urls);
			update.set("content", jsonContent);
			update.set("HtmlContent", content);
			update.set("answers", IDS.toString());
			update.set("editorType", (short) 1);
			update.set("userID", admin.getId());
			update.set("releaseTime", new Date());
			
			questionService.updateFirst(nameQuery, update);
			
			String[] imgs = img_urls.split(",");
			for(String img : imgs) {
				Query imgQuery = new Query().addCriteria(Criteria.where("url").is(img));
				Update imgUpdate = new Update().set("url", img);
				qAImageUrlRecordService.updateFirst(imgQuery, imgUpdate);
			}
			log.info("infoMsg：--- 获取 Q&A列表结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:{--- 获取 Q&A列表失败:" + e.getMessage()+ "---}");
			return reponse.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 删除 Q&A(删除)
	 * @return
	 */
	@RequestMapping(value=Route.QA.DELETE_QA_TO_REPERTORY,method=RequestMethod.POST)
	@ResponseBody
	public Response delete_qa_to_repertory() {
		log.info("infoMsg：--- 获取 Q&A列表开始");
		Response reponse = this.getReponse();
		PageData pageData = this.getPageData();
		List<PageData> pdList = new ArrayList<>();
		try {
			String IDS = pageData.getString("IDS");		//问题名称
			String[] ids = IDS.split(",");
			for(String id : ids) {
				QuestionVO vo = questionService.findOneById(id);
				if(null != vo) {
				    questionService.remove(vo);
				}
				//相关评论
				Query commentQuery = new Query().addCriteria(Criteria.where("questionId").is(vo.getId()));
				List<CommentVO> CommentList = commentService.find(commentQuery);
				for(CommentVO comment : CommentList) {
				    if(null != comment) {
				        commentService.remove(comment);
				    }
				}
				//相关收藏
				Query collectQuery = new Query().addCriteria(Criteria.where("questionId").is(vo.getId()));
				List<QACollectionRecord> collectList = qACollectionRecordService.find(collectQuery);
				for(QACollectionRecord collect : collectList) {
				    if(null != collect) {
				        qACollectionRecordService.remove(collect);
				    }
				}
				//相关答题
				Query areQuery = new Query().addCriteria(Criteria.where("questionID").is(vo.getId()));
				List<ArecordVO> areList = arecordService.find(areQuery);
				for(ArecordVO are : areList) {
				    if(null != are) {
				        arecordService.remove(are);
				    }
				}
			}
			log.info("infoMsg：--- 获取 Q&A列表结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:{--- 获取 Q&A列表失败:" + e.getMessage()+ "---}");
			return reponse.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 提升为热点话题
	 * @return
	 */
	@RequestMapping(value=Route.QA.QA_HOT_TOPIC,method=RequestMethod.POST)
	@ResponseBody
	public Response qa_hot_topic() {
	    log.info("infoMsg：--- 提升为热点话题开始");
	    Response reponse = this.getReponse();
	    PageData pageData = this.getPageData();
	    List<PageData> pdList = new ArrayList<>();
	    try {
	        String IDS = pageData.getString("IDS");		//问题名称
	        String[] ids = IDS.split(",");
	        for(String id : ids) {
	            Query query = new Query().addCriteria(Criteria.where("id").is(id));
	            Update update = new Update().set("isHot", 1);
	            questionService.updateFirst(query, update);
	        }
	        log.info("infoMsg：--- 提升为热点话题结束");
	        return reponse.success();
	    } catch (Exception e) {
	        log.error("errorMsg:{--- 提升为热点话题失败:" + e.getMessage()+ "---}");
	        return reponse.failure(e.getMessage());
	    }
	}
	
	
	
    
    
    
	public static void main(String[] args) {
        String content = "<p><br /></p>\r\n" + 
                "  <p>[链接]</p>\r\n" + 
                "  <p>asdasdasd</p>\r\n" + 
                "  <p>asds<img src=\"http://localhost:8761/photo/qa/20171229103408088008background-flower.png\" title=\"\" alt=\"\" /></p>\r\n" + 
                "  <p>[图片]</p>\r\n" + 
                "  <p>sadsad</p>\r\n" + 
                "  <p><br /></p>\r\n" + 
                "  <p>sadasd</p>\r\n" + 
                "  <p><img src=\"http://localhost:8761/photo/qa/20171229103419703010640.jpg\" title=\"\" alt=\"\" /></p>\r\n" + 
                "  <p>[图片]</p>";
        String str = HtmlHelper.getImagesFromContent(content);
        System.out.println(str);
    }
	
	
	
}
