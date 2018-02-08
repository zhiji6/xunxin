package com.xunxin.controller.system;

import java.io.PrintWriter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fail")
public class NoPromissionController {

  	@RequestMapping("/nopromission")
	public String nopromission(PrintWriter writer){
		
		
		return "author";
		
	}
  	@RequestMapping("/error")
  	public String error(PrintWriter writer){
  		
  		
  		return "error";
  		
  	}
  	
	@RequestMapping("/nopage")
	public String nopage(PrintWriter writer){
		
		
		return "404";
		
	}
}
