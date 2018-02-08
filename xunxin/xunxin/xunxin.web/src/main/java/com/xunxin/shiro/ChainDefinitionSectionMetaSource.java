package com.xunxin.shiro;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.config.Ini;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xunxin.dao.admin.ResourceDao;
import com.xunxin.vo.admin.Resource;

public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section> {

	private static final Logger log = Logger.getLogger(ChainDefinitionSectionMetaSource.class);
	
	private String filterChainDefinitions;


	public Ini.Section getObject() throws Exception {
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[] { "application-config.xml" });
		ResourceDao resourceDao = (ResourceDao) ac.getBean("resourceDao");
		// 获取所有Resource
		Ini ini = new Ini();
		// 加载默认的url
		ini.load(filterChainDefinitions);
		Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		List<Resource> list = resourceDao.findAll();
		// 循环Resource的url,逐个添加到section中。section就是filterChainDefinitionMap,
		// 里面的键就是链接URL,值就是存在什么条件才能访问该链接
		for (Resource resource : list) {
			// 构成permission字符串
			if (StringUtils.isNotEmpty(resource.getResUrl() + "")
					&& StringUtils.isNotEmpty(resource.getResKey() + "")) {
				String permission = "perms[" + resource.getResKey() + "]";
				log.info("所有权限信息" + permission);
				// 不对角色进行权限验证
				// 如需要则 permission = "roles[" + resources.getResKey() + "]";
				// map.put(resource.getResUrl() + "", permission);
				section.put(resource.getResUrl() + "", permission);
			}
		}

		// 所有资源的访问权限，必须放在最后
		/* section.put("/**", "authc"); */
		/**
		 * 如果需要一个用户只能登录一处地方,,修改为 section.put("/**",
		 * "authc,kickout,sysUser,user");
		 **/
		section.put("/login", "authc");
		return section;
	}



	/**
	 * 通过filterChainDefinitions对默认的url过滤定义
	 * 
	 * @param filterChainDefinitions
	 *            默认的url过滤定义
	 */
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	public Class<?> getObjectType() {
		return this.getClass();
	}

	public boolean isSingleton() {
		return false;
	}
}
