package com.xunxin.controller.app.timer;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.xunxin.service.app.DelFileTask;

public class ContextListener implements ServletContextListener {
	
    public ContextListener() {    
    }    
        
     private java.util.Timer timer = null;      


	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		
		timer = new java.util.Timer(true);
		
		arg0.getServletContext().log("定时器已启动");
		
		Calendar calendar = Calendar.getInstance();
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(year, month, day, 14, 15, 00);    
//        Date date = calendar.getTime(); 
        Date date = new Date(System.currentTimeMillis()+1000*60*2);
//        int period = 24 * 60 * 60 * 1000;    
        int period = 60*1000;    
        
        //每天的date时刻执行task，每隔persion 时间重复执行    
        timer.schedule(new DelFileTask(arg0.getServletContext()), date, period);    
            
            
        arg0.getServletContext().log("已经添加任务调度表");      
          
	}
	

}
