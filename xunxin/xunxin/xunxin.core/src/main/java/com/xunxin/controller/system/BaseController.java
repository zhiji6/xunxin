package com.xunxin.controller.system;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.xunxin.service.UserService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.util.ReflectUtils;
import com.xunxin.util.app.excel.ReadExcel;
import com.xunxin.vo.admin.Admin;
import com.xunxin.vo.sys.PageData;
import com.xunxin.web.api.bean.Response;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月8日 -- 下午3:12:58
 * @Version 1.0
 * @Description
 */
public class BaseController {

	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	/**
	 * 得到后台当前操作用户
	 */
	public Admin getAdmin() {
		try {
			HttpSession session = this.getRequest().getSession();
			Admin admin = (Admin) session.getAttribute("usersession");
			return admin;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 得到PageData
	 */
	public PageData getPageData() {
		try {
			return new PageData(this.getRequest());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	
	@Autowired
	private UserService userService;
	
	//系统服务地址
	public String resSuccCode="200";

	/**
	 * 得到ModelAndView
	 */
	public Response getReponse() {
		return new Response();
	}
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * 合并数据，把 source 的数据 合并到 target上 ，要求字段名一致，类型一致
	 * 
	 * @param targetBean
	 * @param sourceBean
	 * @return
	 */
	public static Object mergeEneity(Object targetBean, Object sourceBean) {
		return ReflectUtils.mergeEneity(targetBean, sourceBean);
	}
	
	/**
	 * 解析读取Excel文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List<List<Object>> readExcel(File file,int index) throws IOException {
		return ReadExcel.readExcel(file,index);
	}
	
	/**
	 * 只返回pd
	 * @param pd
	 * @param systemServer(应用的名字)
	 * @return
	 */
	@Deprecated
	public Object pd(String uri,PageData pd){
		//接收服务器失败抛出的错误
		try {
			pd=this.postForPageData(uri, pd);
		} catch (Exception e) {
			e.printStackTrace();
			pd.put("state", 500);
			pd.put("msg", "服务器错误");
		}
			int state = (int) pd.get("state");
			String msg = (String) pd.get("msg");
			
			if(state!=200){
				PageData returnPd=new PageData();
				returnPd.put("state", state);
				returnPd.put("msg", msg);
				return returnPd;
			}
			return pd.get("content");
		
	}
	
	public PageData postForPageData(String URI,Object param){
		PageData pd=null;
		try{
		String rest= restTemplate().postForObject(URI, param,String.class);
		pd=JSON.parseObject(rest, PageData.class);
		
		//PageData pd=JsonUtil.getPageDataFromJsonObjStr(rest);
		}catch(Exception e){
			e.printStackTrace();
			PageData<String ,String > pageData = new PageData<String ,String >();
			pageData.put("state",500);
			pageData.put("msg", "服务器错误");
			return pageData;
		}
        return pd;
	}
	
	/**
	 * 分页封装
	 * @param list
	 * @return
	 */
	 public PageData getPagingPd(List list){
		 PageData pd = new PageData();
		// 分页信息结果集整合到分页框架
		PageInfo pi = new PageInfo(list);
		// 查询结果与分页信息封装为bootstrampTable认可结构
		//pd = BootstrapUtils.parsePage2BootstrmpTable(pi.getTotal(), list);
		if (list == null)
			list = new ArrayList();
		pd.put("total", pi.getTotal());
		pd.put("rows", list);
		return pd;
	 }
	 
	 
	
}
