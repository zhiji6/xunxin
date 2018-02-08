package com.xunxin.controller.app.qa;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.xunxin.config.RedisRepository;
import com.xunxin.controller.system.BaseController;
import com.xunxin.service.app.QASectionService;
import com.xunxin.vo.qa.QASection;
import com.xunxin.vo.sys.PageData;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Router;


/**
 * 
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月13日 -- 下午4:14:52
 * @Version 1.0
 * @Description	·Q&A板块管理中心
 */
@Controller
@RequestMapping(value=Router.PATH + Router.Section.PATH)
public class QASectionController extends BaseController{
	
	private static final Logger log = Logger.getLogger(QASectionController.class);
	
	@Autowired
	private QASectionService QASectionService;
	
	/**
	 * 获取&QA板块列表
	 * 
	 * @return
	 */
	@RequestMapping(value=Router.Section.GET_SECTION_LIST,method=RequestMethod.POST)
	@ResponseBody
	public Response get_section_list() {
		log.info("infoMsg:--- 获取板块列表开始");
		Response reponse = this.getReponse();
		List<PageData> pdList = new ArrayList<>();
		try {
			List<QASection> qsList = QASectionService.getAll();
			for(QASection section : qsList ) {
				PageData pd = new PageData<>();
				pd.put("id", section.getId());
				pd.put("sectionName", section.getSectionName());
				pdList.add(pd);
			}
			RedisRepository.setList("qsList", pdList);
			log.info("infoMsg:--- 获取板块列表结束");
			return reponse.success(pdList);
		} catch (Exception e) {
			log.error("errorMsg:{--- 获取板块列表失败:" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 新增板块
	 * 
	 * @param section_name
	 * @param section_type
	 * @return
	 */
	@RequestMapping(value=Router.Section.ADD_SECTION,method=RequestMethod.POST)
	@ResponseBody
	public Response addSectiont(@RequestParam("section_name") String section_name,@RequestParam("section_type") short section_type) {
		log.info("infoMsg:--- 获取板块列表开始");
		Response reponse = this.getReponse();
		try {
			QASection qs = new QASection();
			qs.setSectionName(section_name);
			qs.setSectionType(section_type);
			QASectionService.save(qs);
			log.info("infoMsg:--- 获取板块列表结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:--- 获取板块列表失败" + e.getMessage());
			return reponse.failure(e.getMessage());
		}
	}
	
	
	
	
	

}
