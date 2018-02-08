package com.xunxin.controller.system;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunxin.util.app.excel.ReadExcel;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Route;


/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月29日 -- 下午5:51:44
 * @Version 1.0
 * @Description		Apache POI导入导出Excel文件
 */
@Controller
@RequestMapping(value=Route.PATH+Route.ExcelPoi.PATH)
public class XExcelPOIController extends BaseController {

	private final static Logger log = Logger.getLogger(XExcelPOIController.class);
	
	
	@RequestMapping(value=Route.ExcelPoi.IMPORT_QUESTIONVO_EXCEL,method=RequestMethod.POST)
	@ResponseBody
	public Response import_questionVO_excel(@RequestParam ("adminId") int adminId,@RequestParam("file") File file) {
		log.error("infoMsg:--- 导入QA问题Excel文件开始");
		Response response = this.getReponse();
		try {
			List<List<Object>> readExcel = ReadExcel.readExcel(file,0);
			Iterator<List<Object>> iterator = readExcel.iterator();
			while(iterator.hasNext()) {
				List<Object> next = iterator.next();
			}
			
			log.error("errorMsg:--- 导入QA问题Excel文件结束");
			return response.success();
		} catch (Exception e) {
			log.error("errorMsg:--- 导入QA问题Excel文件失败");
			return response.failure();
		}
		
	}
	
	
	
	
	
	
}
