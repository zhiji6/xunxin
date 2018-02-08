package com.xunxin.controller.app.rules;

public class RuleFactory {

	   public  Rules getRule(Integer type){
	      if(type == null){
	         return null;
	      }        
	      if(type.equals(11)){
	    	  return new ModularRuler();
	      } else if(type.equals(12)){
	    	  return new SexualOrientation();
	      } else if(type.equals(13)){
	    	  return new QARuler();
	      } else if(type.equals(14)){
	    	  return new MomentRuler();
	      }
	      return null;
	   }
}
