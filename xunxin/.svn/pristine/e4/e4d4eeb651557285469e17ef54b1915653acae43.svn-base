package com.xunxin.controller.system;

import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.mongodb.framework.util.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.xunxin.service.LogService;
import com.xunxin.vo.sys.Log;


@Controller
@RequestMapping("/log")
public class LogController {
	private static final Logger log = LoggerFactory .getLogger(LogController.class);
	
	@Autowired 
	private LogService logService;
	
	
	/***
	 *  查询所有日志信息
	 * @param pid
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value = "log:list")
	public ModelAndView findlist(
			@RequestParam(value = "pageNo0", defaultValue = "1") Integer pageNo0,
			@RequestParam(value = "pageSize0", defaultValue = "10") Integer pageSize0,
			@RequestParam(value = "pageNo1", defaultValue = "1") Integer pageNo1,
			@RequestParam(value = "pageSize1", defaultValue = "10") Integer pageSize1,
			@RequestParam(value="type",defaultValue="")String type,
			HttpSession session) {
		log.info("查询所有日志信息——————————》");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/admin/log/log");
		try{
			//如果type为空查询所有
				Pagination<Log> pagination0=this.logService.findPaginationByQuery(this.logService.findAllLogbyQuery("0"), pageNo0, pageSize0);
				if(pagination0!=null)
					modelAndView.addObject("pageList0", pagination0);
				Pagination<Log> pagination1=this.logService.findPaginationByQuery(this.logService.findAllLogbyQuery("1"), pageNo1, pageSize1);
				if(pagination1!=null)
					modelAndView.addObject("pageList1", pagination1);
					log.info("查询所有的日志信息成功");
		
				if(type.equals("0")){
				//查询所有操作日志
				modelAndView.addObject("click","<script type='text/javascript'>$(function(){$('#a').trigger('click')})</script>");
				
			}else if(type.equals("1")){
				modelAndView.addObject("click","<script type='text/javascript'>$(function(){$('#b').trigger('click')})</script>");
			}
			
			modelAndView.addObject("pageno0", pageNo0);
			modelAndView.addObject("pageno1", pageNo1);
			
			
		}catch(Exception e){
			log.info("查询所有的日志信息失败"+e);
			e.printStackTrace();
		}
		return modelAndView;// 返回到学校信息
	}
	
	
	
}
