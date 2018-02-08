package com.xunxin.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
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
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.xunxin.service.MenuService;
import com.xunxin.service.web.sys.XDocumentAPIService;
import com.xunxin.util.PeriodsUtil;
import com.xunxin.vo.admin.Menu;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.sys.XDocumentAPI;
import com.xunxin.web.api.bean.Response;
import com.xunxin.web.api.bean.Route;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月16日 -- 上午3:32:09
 * @Version 1.0
 * @Description
 */
/**
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2017年12月22日 -- 上午9:23:48
 * @Version 1.0
 * @Description	 
 */
@Controller
@RequestMapping(value=Route.PATH+Route.System.PATH)
public class SystemController extends BaseController{

	private static final Logger log = Logger.getLogger(SystemController.class);
	
	@Autowired
	private MenuService menuService;
	@Autowired
	private XDocumentAPIService xDocumentAPIService;
	
	

	/**
	 * 系统管理
	 * @return
	 */
	@RequestMapping(value=Route.System.SYSTEM_MANAGER,method=RequestMethod.GET)
	public ModelAndView system_manager() {
		log.info("");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/system/system_manager");
		return mv;
	}

	/**
	 * 接口管理
	 * @return
	 */
	@RequestMapping(value=Route.System.SYSTEM_API_MANAGER,method=RequestMethod.GET)
	public ModelAndView system_api_manager() {
		log.info("");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/system/system_api_manager");
		return mv;
	}
	
	
	/**
	 * 接口新增
	 * @return
	 */
	@RequestMapping(value=Route.System.SYSTEM_API_ADD,method=RequestMethod.GET)
	public ModelAndView system_api_add() {
		log.info("");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/system/api_add");
		return mv;
	}
	
	/**
	 * 接口修改
	 * @return
	 */
	@RequestMapping(value=Route.System.SYSTEM_API_EDIT,method=RequestMethod.GET)
	public ModelAndView system_api_edit() {
		log.info("");
		PageData pageData = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData<>();
		try {
			String id = pageData.getString("id");
			XDocumentAPI api = xDocumentAPIService.findOneById(id);
			pd.put("id", api.getId());
			pd.put("api_name", api.getApi_name());
			pd.put("api_model", api.getApi_model());
			pd.put("api_url", api.getApi_url());
			pd.put("api_state", api.getApi_state());
			mv.setViewName("jsp/xunxin/system/api_edit");
			mv.addObject("pd", pd);
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("error");
	}

	/**
	 * 角色分配
	 * @return
	 */
	@RequestMapping(value=Route.System.USER_MANAGER,method=RequestMethod.GET)
	public ModelAndView user_manager() {
		log.info("");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jsp/xunxin/system/user_manager");
		return mv;
	}
	
	
	/**
	 * 获取菜单树
	 * 
	 * @return
	 */
	@RequestMapping(value=Route.System.MENU_TREE,method=RequestMethod.POST)
	@ResponseBody
	public Response getTree() {
		log.info("获取菜单列表开始");
		Response resp = new Response();
		try {
			//一级菜单
			List<Menu> menuList = menuService.getAll();
			return resp.success(menuList);
		} catch (Exception e) {
			log.error("获取菜单失败",e);
			return resp.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 获取当前节点的二级菜单
	 * 
	 * @param pid
	 * @return
	 */
	@RequestMapping(value=Route.System.MENU_SUB_TREE,method=RequestMethod.POST)
	@ResponseBody
	public Response getSubTree(@RequestParam("pid") String pid) {
		log.info("获取菜单列表开始");
		Response resp = new Response();
		try {
			//一级菜单所属的二级菜单
			List<Menu> SubMenu = menuService.findByParentId(pid);
			return resp.success(SubMenu);
		} catch (Exception e) {
			log.error("获取菜单失败",e);
			return resp.failure(e.getMessage());
		}
	}
	
	/**
	 * 菜单管理
	 * @return
	 */
	@RequestMapping(value=Route.System.MENU_MANAGER,method=RequestMethod.GET)
	public ModelAndView menu_manager() {
		log.info("获取菜单列表开始");
		ModelAndView mv = new ModelAndView();
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		try {
			//一级菜单
			List<Menu> menuList = menuService.getAll();
			if(null != menuList) {
				for(Menu m : menuList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("mainMenu", m);
					//一级菜单所属的二级菜单
					List<Menu> SubMenu = menuService.findByParentId(m.getId());
					if(null != SubMenu && !SubMenu.isEmpty()) {
						map.put("SubMenu", SubMenu);
					}else {
						map.put("SubMenu", "");
					}
					retList.add(map);
				}
				mv.setViewName("jsp/xunxin/system/menu_manager");
				mv.addObject(retList);
				return mv;
			}
		} catch (Exception e) {
			log.error("获取菜单失败",e);
		}
		return new ModelAndView("error");
	}
	
	/**
	 * 系统监控
	 * @return
	 */
	@RequestMapping(value=Route.System.SYSTEM_MONITOR,method=RequestMethod.GET)
	public ModelAndView system_monitor() {
		log.info("系统监控开始");
		try {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("/jsp/xunxin/system/system_monitor");
			log.info("系统监控结束");
			return mv;
		} catch (Exception e) {
			log.error("系统监控失败",e);
		}
		return new ModelAndView("error");
	}
	
	

	/**
	 * 平台接口API列表
	 * @return
	 */
	@RequestMapping(value=Route.System.SHOW_API_DOCUMENTS,method=RequestMethod.POST)
	@ResponseBody
	public Object show_api_documents() {
		log.info("infoMsg:--- 获取平台接口API列表开始");
		PageData pageData = this.getPageData();
		List<PageData> pdList = new ArrayList<>();
		try {
			Query apiQuery = new Query();
			apiQuery.with(new Sort(new Order(Direction.DESC,"createTime")));
			int pageNumber = pageData.getPageNumber(); // 获取页条数(当前页数)
			int pageSize = pageData.getPageSize(); // 获取页码(每页多少条)
			// 分页
			apiQuery.skip((pageNumber - 1) * 10).limit(pageSize);
			List<XDocumentAPI> apiList = xDocumentAPIService.find(apiQuery);
			for(XDocumentAPI api : apiList) {
				PageData pd = new PageData<>();
				pd.put("id", api.getId());
				pd.put("api_name", api.getApi_name());
				pd.put("api_model", api.getApi_model());
				pd.put("api_url", api.getApi_url());
				pd.put("api_source", api.getApi_source());
				pd.put("api_prefix", api.getApi_prefix());
				pd.put("createTime", api.getCreateTime());
				pd.put("api_state", api.getApi_state());
				pdList.add(pd);
			}
			Query countQuery = new Query();
			countQuery.with(new Sort(new Order(Direction.DESC,"createTime")));
			Integer count = xDocumentAPIService.findCountByQuery(countQuery);
			
			PageHelper.startPage(pageNumber,pageSize);
			
			PageData data = new PageData<>();
			data.put("total", count);
			data.put("rows", pdList);
			
			log.info("infoMsg:--- 获取平台接口API列表结束");
			return data;
		} catch (Exception e) {
			log.error("errorMsg:{--- 获取平台接口API列表失败:" + e.getMessage() + "---}");
		}
		return new ModelAndView("error");
	}
	

	/**
	 * 接口浏览
	 * @return
	 */
	@RequestMapping(value=Route.System.SYSTEM_API_VIEW,method=RequestMethod.GET)
	public ModelAndView system_api_view() {
		log.info("infoMsg:--- 获取平台接口API列表开始");
		PageData pageData = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData<>();
		try {
			String id = pageData.getString("id");
			XDocumentAPI api = xDocumentAPIService.findOneById(id);
			pd.put("id", api.getId());
			pd.put("api_name", api.getApi_name());
			pd.put("api_model", api.getApi_model());
			pd.put("api_url", api.getApi_url());
			pd.put("api_source", api.getApi_source());
			pd.put("api_prefix", api.getApi_prefix());
			pd.put("createTime", PeriodsUtil.getWholeTime(api.getCreateTime()));
			if(api.getApi_state() == 0) {
				pd.put("api_state", "不可用");
			}else if(api.getApi_state() == 1){
				pd.put("api_state", "可用");
			}else if(api.getApi_state() == 2){
				pd.put("api_state", "废弃");
			}
			mv.setViewName("jsp/xunxin/system/api_view");
			mv.addObject("pd", pd);
			log.info("infoMsg:--- 获取平台接口API列表结束");
			return mv;
		} catch (Exception e) {
			log.error("errorMsg:{--- 获取平台接口API列表失败:" + e.getMessage() + "---}");
		}
		return new ModelAndView("error");
	}
	
	/**
	 * 新增平台接口API
	 * @return
	 */
	@RequestMapping(value=Route.System.ADD_API_DOCUMENT,method=RequestMethod.POST)
	@ResponseBody
	public Response add_api_document() {
		log.info("infoMsg:--- 新增平台接口API开始");
		Response reponse = this.getReponse();
		PageData pageData = this.getPageData();
		try {
			String api_name = pageData.getString("api_name");
			String api_url = pageData.getString("api_url");
			int api_model = Integer.parseInt(pageData.getString("api_model"));
			int api_state = Integer.parseInt(pageData.getString("api_state"));
			
			XDocumentAPI api = new XDocumentAPI();
			api.setApi_name(api_name);
			api.setApi_state(api_state);
			api.setApi_url(api_url);
			if(api_model == 0) {
				api.setApi_prefix("/api");
				api.setApi_source("web");
				api.setApi_model("PC端");
			}else if(api_model == 1) {
				api.setApi_prefix("/api-base");
				api.setApi_source("base");
				api.setApi_model("基础设施与数据共享");
			}else if(api_model == 2) {
				api.setApi_prefix("/app-api");
				api.setApi_source("app");
				api.setApi_model("APP端");
			}
			xDocumentAPIService.save(api);
			
			log.info("infoMsg:--- 新增平台接口API结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:{--- 新增平台接口API失败:" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
	}
	
	
	/**
	 * 新增平台接口API
	 * @return
	 */
	@RequestMapping(value=Route.System.EDIT_API_DOCUMENT,method=RequestMethod.POST)
	@ResponseBody
	public Response edit_api_document() {
		log.info("infoMsg:--- 新增平台接口API开始");
		Response reponse = this.getReponse();
		PageData pageData = this.getPageData();
		try {
			String id = pageData.getString("id");
			String api_name = pageData.getString("api_name");
			String api_url = pageData.getString("api_url");
			int api_model = Integer.parseInt(pageData.getString("api_model"));
			int api_state = Integer.parseInt(pageData.getString("api_state"));
			
			
			XDocumentAPI api = xDocumentAPIService.findOneById(id);
			api.setApi_name(api_name);
			api.setApi_state(api_state);
			api.setApi_url(api_url);
			Query query = new Query().addCriteria(Criteria.where("id").is(id));
			Update update = new Update();
			update.set("api_name", api_name);
			update.set("api_state", api_state);
			update.set("api_url", api_url);
			if(api_model == 0) {
				update.set("api_prefix", "/api");
				update.set("api_source", "web");
				update.set("api_model", "PC端");
			}else if(api_model == 1) {
				update.set("api_prefix", "/api-base");
				update.set("api_source", "base");
				update.set("api_model", "基础设施与数据共享");
			}else if(api_model == 2) {
				update.set("api_prefix", "/app-api");
				update.set("api_source", "app");
				update.set("api_model", "APP端");
			}
			xDocumentAPIService.updateFirst(query, update);
			log.info("infoMsg:--- 新增平台接口API结束");
			return reponse.success();
		} catch (Exception e) {
			log.error("errorMsg:{--- 新增平台接口API失败:" + e.getMessage() + "---}");
			return reponse.failure(e.getMessage());
		}
	}
	
	
	
	
	
}
