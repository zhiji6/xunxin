package com.xunxin.controller.common.base;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunxin.controller.system.BaseController;
import com.xunxin.service.SystemEntityService;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.sys.SystemCountryEntity;
import com.xunxin.web.api.bean.Base;
import com.xunxin.web.api.bean.Response;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * 
 * @Author Noseparte
 * @Compile 2017年12月12日 -- 上午11:27:38
 * @Version 1.0
 * @Description		通用数据管理
 */
@Controller
@RequestMapping(value=Base.PATH+Base.Data.PATH)
public class XCommonDataController extends BaseController {

	private static final Logger log = Logger.getLogger(XCommonDataController.class);
	
	@Autowired
	private SystemEntityService systemEntityService;
	
	/**
	 * 获取国籍列表
	 * @return
	 */
	@RequestMapping(value=Base.Data.GET_NATIONALITY_DICTIONARY,method=RequestMethod.POST)
	@ResponseBody
	public Response get_nationality_dictionary() {
		log.info("infoMsg:--- 获取国籍字典开始 ");
		Response reponse = this.getReponse();
		List<PageData> pdList = new ArrayList<>();
		try {
			Comparator<Object> com=Collator.getInstance(java.util.Locale.CHINA); 
			
			List<SystemCountryEntity> countryList = systemEntityService.getAll();
			List<String> list = new ArrayList<>();
			for(SystemCountryEntity country : countryList) {
				if(!country.getName_Chinese().equals("中国")) {
					list.add(country.getName_Chinese());
				}
			}
			Collections.sort(list, com); 
			
			list.set(0, "中国");
			
			for(String name : list) {
				SystemCountryEntity entity = systemEntityService.findByChinaName(name);
				PageData pd = new PageData<>();
				pd.put("countryId", entity.getCountry_id());
				pd.put("countryName", entity.getName_Chinese());
				pdList.add(pd);
			}
			
			log.info("infoMsg:--- 获取国籍字典结束 ");
			return reponse.success(pdList);
		} catch (Exception e) {
			log.error("errorMsg:{--- 获取国籍字典异常，" + e.getMessage() + "\n\t---}");
			return reponse.failure(e.getMessage());
		}
		
	}
		       
	
	
	
}
