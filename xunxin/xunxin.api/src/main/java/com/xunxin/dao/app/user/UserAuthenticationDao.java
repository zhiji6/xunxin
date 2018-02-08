package com.xunxin.dao.app.user;

import java.util.List;


import org.apache.ibatis.annotations.Param;

import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.UserAuthentication;

public interface UserAuthenticationDao {

	void save(UserAuthentication auth);

	UserAuthentication isAuthentication(PageData pd);

	List<UserAuthentication> getAll(Integer userId);

	List<UserAuthentication> findPageList(UserAuthentication userAuthentication);

	UserAuthentication findOneById(Integer id);

	void authenticationManagerExamine(UserAuthentication userAuthentication);

	UserAuthentication model(@Param("userId") Integer userId,@Param("type") String type);

    void update(PageData pd);

    void removeAll(Integer userId);


}
