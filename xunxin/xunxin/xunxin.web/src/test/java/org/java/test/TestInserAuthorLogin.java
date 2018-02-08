package org.java.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestInserAuthorLogin {
	
	ApplicationContext ac=null;
	
	@Before
	public void befort(){
		 ac=new ClassPathXmlApplicationContext(new String[]{"application-config.xml","dispatcher-servlet.xml","dispatcher-shiro.xml"});
	}
	
	
	
	
	//@Test
	public void test() throws Exception {	
		String msg="";
		UsernamePasswordToken token = new UsernamePasswordToken("fliay","123456");
		token.setRememberMe(true);// 记住我
		Subject subject =SecurityUtils.getSubject();//获得主体
		try{
			subject.login(token);
			System.out.println(subject.isAuthenticated());
			if(subject.isAuthenticated()){
				System.out.println("登录成功");
			}else{
				System.out.println("登录失败");
			}
		}catch (IncorrectCredentialsException e) {  
	        msg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.";  
	    } catch (ExcessiveAttemptsException e) {  
	        msg = "登录失败次数过多";  
	    } catch (LockedAccountException e) {  
	        msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";  
	    } catch (DisabledAccountException e) {  
	        msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";  
	    } catch (ExpiredCredentialsException e) {  
	        msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";  
	    } catch (UnknownAccountException e) {  
	        msg = "帐号不存在. There is no user with username of " + token.getPrincipal();  
	    } catch (UnauthorizedException e) {  
	        msg = "您没有得到相应的授权！" + e.getMessage();  
	    }finally{
		        System.out.println(msg); 
	    }  
		
}

}
