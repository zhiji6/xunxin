package com.xunxin.controller.app.rules;

import java.math.BigDecimal;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.xunxin.service.ArecordService;
import com.xunxin.service.app.AppUserService;

public class ContextRulers  {

	   private Rules ruler;

	   public ContextRulers(Rules ruler){
	      this.ruler = ruler;
	   }

	   public BigDecimal executeStrategy(Integer num1, Integer num2,ArecordService arecordService,AppUserService appUserService){
	      return ruler.getScore(num1, num2,arecordService,appUserService);
	   }

}
