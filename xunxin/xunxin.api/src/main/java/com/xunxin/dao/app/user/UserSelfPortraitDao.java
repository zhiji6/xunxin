package com.xunxin.dao.app.user;

import org.apache.ibatis.annotations.Param;

import com.xunxin.vo.user.UserSelfPortraitVO;

public interface UserSelfPortraitDao {

	UserSelfPortraitVO findById(Integer userId);

    void updatePortrait(@Param("userId") Integer userId, @Param("extraInfo") String extraInfo);

    void save(UserSelfPortraitVO vo);

    void removeAll(Integer userId);

}
