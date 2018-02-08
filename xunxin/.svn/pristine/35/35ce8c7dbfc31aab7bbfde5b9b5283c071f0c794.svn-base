package com.xunxin.controller.common.base;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
import com.xunxin.vo.user.UserRobot;
import com.xunxin.web.api.bean.Base;
import com.xunxin.web.api.bean.Response;
import com.xunxin.service.app.user.UserRobotService;
import com.xunxin.util.app.IDGeneratorUtil;

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
	@Autowired
	private UserRobotService userRobotService;
	
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
		       
	
	/**
	 * 机器人扩容
	 * @return
	 */
	@RequestMapping(value=Base.Data.ROBOT_INCREASE,method=RequestMethod.POST)
	@ResponseBody
	public Response robot_increase() {
	    log.info("infoMsg:--- 机器人扩容开始 ");
	    Response reponse = this.getReponse();
	    List<PageData> pdList = new ArrayList<>();
	    FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        List<String> nickList = new ArrayList<>();
	    try {
	        String str = "";
            fis = new FileInputStream("D:\\lucene\\nickname.txt");// FileInputStream
            // 从文件系统中的某个文件中获取字节
            isr = new InputStreamReader(fis,"UTF-8");// InputStreamReader 是字节流通向字符流的桥梁,
            br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
            while ((str = br.readLine()) != null) {
                nickList.add(str);
            }
	        for(int i = 500;i < nickList.size();i++) {
	            UserRobot robot = new UserRobot(nickList.get(i), nickList.get(i), IDGeneratorUtil.createAppCode(), "女", 0);
	            userRobotService.insert(robot);
	        }
	        log.info("infoMsg:--- 机器人扩容开始 ");
	        return reponse.success();
	    } catch (Exception e) {
	        log.error("errorMsg:{--- 机器人扩容失败，" + e.getMessage() + "---}");
	        return reponse.failure(e.getMessage());
	    } finally {
            try {
                br.close();
                isr.close();
                fis.close();
               // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
              } catch (IOException e) {
               e.printStackTrace();
              }
        }
	    
	}
	
	public static void main(String[] args) {
	    FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        List<String> nickList = new ArrayList<>();
        try {
            String str = "";
            fis = new FileInputStream("D:\\lucene\\nickname.txt");// FileInputStream
            // 从文件系统中的某个文件中获取字节
            isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
            br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
            while ((str = br.readLine()) != null) {
                nickList.add(new String(str.getBytes("UTF-8")));
            }
            // 当读取的一行不为空时,把读到的str的值赋给str1
            System.out.println(nickList.size());// 打印出str1
        } catch (Exception e) {
            log.error("errorMsg:{--- 机器人扩容失败，" + e.getMessage() + "---}");
//            return reponse.failure(e.getMessage());
        } finally {
            try {
                br.close();
                isr.close();
                fis.close();
               // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
              } catch (IOException e) {
               e.printStackTrace();
              }
        }
        
        
    }
	
	
}
