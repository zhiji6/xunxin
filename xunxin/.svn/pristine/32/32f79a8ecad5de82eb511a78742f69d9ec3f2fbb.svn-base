package com.xunxin.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.tools.ant.types.resources.selectors.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xunxin.service.AdminService;
import com.xunxin.service.MenuService;
import com.xunxin.service.RoleService;
import com.xunxin.service.web.sys.XDocumentAPIService;
import com.xunxin.util.PeriodsUtil;
import com.xunxin.vo.admin.Admin;
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
	@Autowired
	private AdminService adminService;
	@Autowired
	private RoleService roleService;
	

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
		List<PageData> pdList = new ArrayList<>();
		try {
			Query apiQuery = new Query();
			List<XDocumentAPI> apiList = xDocumentAPIService.find(apiQuery);
			for(XDocumentAPI api : apiList) {
				PageData pd = new PageData<>();
				pd.put("api_name", api.getApi_name());
				pd.put("api_model", api.getApi_model());
				pd.put("api_url", api.getApi_url());
				pd.put("api_source", api.getApi_source());
				pd.put("api_prefix", api.getApi_prefix());
				pd.put("createTime", api.getCreateTime());
				pd.put("api_state", api.getApi_state());
				pdList.add(pd);
			}
			log.info("infoMsg:--- 获取平台接口API列表结束");
			return pdList;
		} catch (Exception e) {
			log.error("errorMsg:{--- 获取平台接口API列表失败:" + e.getMessage() + "---}");
		}
		return new ModelAndView("error");
	}
	
	
	/**
	 * 修改平台接口API
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
	
	   
    /**
     * 系统管理人员列表
     * @return
     */
    @RequestMapping(value=Route.System.ADMIN_LIST,method=RequestMethod.POST)
    @ResponseBody
    public Object admin_list() {
        log.info("infoMsg:--- 获取系统管理人员列表开始");
        List<PageData> pdList = new ArrayList<>();
        try {
            List<Admin> adminList = adminService.admin_list();
            for(Admin admin : adminList) {
                PageData pd = new PageData<>();
                pd.put("id", admin.getId());
                pd.put("adminName", admin.getAdminName());
                pd.put("gender", admin.getGender());
                pd.put("roleId", admin.getRoleId());
                pd.put("nickName", admin.getNickName());
                pd.put("createTime", PeriodsUtil.getWholeTime(admin.getCreateTime()));
                pd.put("lastLoginTime", PeriodsUtil.getWholeTime(admin.getLastLoginTime()));
                pd.put("lastLoginIp", admin.getLastLoginIp());
                pd.put("ishiden", admin.getIshiden());
                pdList.add(pd);
            }
            
            PageData pagingPd = this.getPagingPd(adminList);
            log.info("infoMsg:--- 获取系统管理人员列表结束");
            return pagingPd;
        } catch (Exception e) {
            log.error("errorMsg:{--- 获取系统管理人员列表失败:" + e.getMessage() + "---}");
        }
        return null;
    }
    
    
    /**
     * 管理员详情
     * @return
     */
    @RequestMapping(value=Route.System.ADMIN_VIEW,method=RequestMethod.GET)
    public ModelAndView admin_view() {
        log.info("infoMsg:--- 获取系统管理员详情开始");
        List<PageData> pdList = new ArrayList<>();
        PageData pageData = this.getPageData();
        PageData pd = new PageData<>();
        ModelAndView mv = this.getModelAndView();
        try {
            int id = Integer.parseInt(pageData.getString("id"));
            Admin admin = adminService.findUserById(id);
            pd.put("id", admin.getId());
            pd.put("adminName", admin.getAdminName());
            pd.put("gender", admin.getGender());
            pd.put("roleId", roleService.findByRoleId(admin.getRoleId()).getRoleName());
            pd.put("nickName", admin.getNickName());
            pd.put("createTime", PeriodsUtil.getWholeTime(admin.getCreateTime()));
            pd.put("lastLoginTime", PeriodsUtil.getWholeTime(admin.getLastLoginTime()));
            pd.put("lastLoginIp", admin.getLastLoginIp());
            pd.put("ishiden", admin.getIshiden());
            
            mv.addObject("pd",pd);
            mv.setViewName("jsp/xunxin/system/admin_view");
            log.info("infoMsg:--- 获取系统管理员详情结束");
            return mv;
        } catch (Exception e) {
            log.error("errorMsg:{--- 获取系统管理员详情失败:" + e.getMessage() + "---}");
        }
        return new ModelAndView("error");
    }
    
    /**
     * 管理员新增页
     * @return
     */
    @RequestMapping(value=Route.System.TO_ADMIN_ADD,method=RequestMethod.GET)
    public ModelAndView to_admin_add() {
        log.info("infoMsg:--- 管理员新增开始");
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("jsp/xunxin/system/admin_add");
        return mv;
    }
    
    /**
     * 管理员新增
     * @return
     */
    @RequestMapping(value=Route.System.ADMIN_ADD,method=RequestMethod.POST)
    @ResponseBody
    public Response admin_add(HttpServletRequest request) {
        log.info("infoMsg:--- 管理员新增开始");
        Response reponse = this.getReponse();
        PageData pd = this.getPageData();
        try {
            pd.put("lastLoginTime", new Date());
            pd.put("lastLoginIp", request.getRemoteAddr());
            adminService.insert(pd);
            log.info("infoMsg:--- 管理员新增结束");
            return reponse.success();
        } catch (Exception e) {
            log.error("errorMsg:{--- 管理员新增失败:" + e.getMessage() + "---}");
            return reponse.failure(e.getMessage());
        }
    }
    
    /**
     * 管理员修改页
     * @return
     */
    @RequestMapping(value=Route.System.ADMIN_EDIT,method=RequestMethod.GET)
    public ModelAndView admin_edit(HttpServletRequest request) {
        log.info("infoMsg:--- 管理员修改页开始");
        Response reponse = this.getReponse();
        PageData pageData = this.getPageData();
        PageData pd = new PageData<>();
        ModelAndView mv = this.getModelAndView();
        try {
            int id = Integer.parseInt(pageData.getString("id"));
            Admin admin = adminService.findUserById(id);
            pd.put("id", admin.getId());
            pd.put("adminName", admin.getAdminName());
            pd.put("gender", admin.getGender());
            pd.put("roleId", roleService.findByRoleId(admin.getRoleId()).getRoleName());
            pd.put("nickName", admin.getNickName());
            pd.put("createTime", PeriodsUtil.getWholeTime(admin.getCreateTime()));
            pd.put("lastLoginTime", PeriodsUtil.getWholeTime(admin.getLastLoginTime()));
            pd.put("lastLoginIp", admin.getLastLoginIp());
            pd.put("ishiden", admin.getIshiden());
            mv.addObject("pd",pd);
            mv.setViewName("jsp/xunxin/system/admin_edit");
            log.info("infoMsg:--- 管理员修改页结束");
            return mv;
        } catch (Exception e) {
            log.error("errorMsg:{--- 管理员修改页失败:" + e.getMessage() + "---}");
            return new ModelAndView("error");
        }
    }
    
    
    /**
     * 删除管理员
     * @return
     */
    @RequestMapping(value=Route.System.ADMIN_DELETE,method=RequestMethod.POST)
    @ResponseBody
    public Response admin_delete(HttpServletRequest request) {
        log.info("infoMsg:--- 删除管理员开始");
        Response reponse = this.getReponse();
        PageData pd = this.getPageData();
        try {
            adminService.delete(pd);
            log.info("infoMsg:--- 删除管理员结束");
            return reponse.success();
        } catch (Exception e) {
            log.error("errorMsg:{--- 删除管理员失败:" + e.getMessage() + "---}");
            return reponse.failure(e.getMessage());
        }
    }
    

	 
	
	
	
	
}
