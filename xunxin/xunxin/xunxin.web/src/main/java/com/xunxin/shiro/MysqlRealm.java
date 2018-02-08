package com.xunxin.shiro;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xunxin.service.AdminService;
import com.xunxin.util.Constant;
import com.xunxin.vo.admin.Admin;
import com.xunxin.vo.admin.Resource;

public class MysqlRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(MysqlRealm.class);
    
    @Autowired
    private AdminService adminService;

    /*
     * @Autowired private ResUserService resUserService;
     */

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
	// 获取登录的用户名
	String accountName = SecurityUtils.getSubject().getPrincipal().toString();
	// 获取shirosession
	log.info("当前登录用户：" + accountName);
	if (accountName != null || accountName != "") {
	    Session session = SecurityUtils.getSubject().getSession();
	    try {
		Admin admin = (Admin) session.getAttribute(Constant.USER_SESSION);
		if (admin != null) {

		    // 通过用户名获取用户对象
		    Admin u = this.adminService.findUserById(admin.getId());
		    List<Resource> rs = admin.getResourceList();
		    // 权限信息对象info，用来存放查出的用户所有角色（role）及权限（permission）
		    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		    for (Resource r : rs) {
			log.info("资源：" + r.getName() + ":" + r.getResUrl());
			info.addStringPermission(r.getResKey());
		    }
		    session.setAttribute("resourceslist", rs);
		    log.info("当前登录用户访问资源权限：" + info);
		    return info;
		}
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
	// 获取用户的名称
	UsernamePasswordToken usertoken = (UsernamePasswordToken) token;
	try {

	    // 根据获取到的用户信息从数据库查询是否存在该用户名下的信息
	    Admin admin = this.adminService.findUserByAccountName(usertoken.getUsername());

	    if (admin != null) {
			// 当验证都通过后，把用户信息放在session里
			Session session = SecurityUtils.getSubject().getSession();
			Admin u = this.adminService.findUserById(admin.getId());
	
			// 通过集合获取资源
			List<Resource> rs = u.getResourceList();
			session.setAttribute(Constant.USER_SESSION,admin);
			session.setAttribute("userSessionId", admin.getId());
			session.setAttribute("resourceslist", rs);
			return new SimpleAuthenticationInfo(admin.getAdminName(), admin.getPassword(), Constant.REALMNAME);
	    } else {
	    	return null;
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
    }

}
