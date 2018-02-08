package com.xunxin.controller.app.message;

public class MessTypeFactory {

	   public  Message getMessage(Integer type){
	      if(type == null){
	         return null;
	      }        
	      if(type.equals(1)){
	    	  return new LeaveMessage();
	      } else if(type.equals(2)){
	    	  return new ReportMessage();
	      } else if(type.equals(3)){
	    	  return new NotThrough();
	      }else if(type.equals(4)){
	    	  return new ThroughMessage();
	      }else if(type.equals(5)){
	    	  return new GiveUpMessage();
	      }else if(type.equals(6)){
	    	  return new CommentMessage();
	      }else if(type.equals(7)){
	    	  return new MatchingMessage();
	      }else if(type.equals(8)){
	    	  return new FollowMessage();
	      }else if(type.equals(9)){
	    	  
	      }else if(type.equals(10)){
	    	  
	      }else if(type.equals(11)){
	    	  
	      }
	      return null;
	   }
}
