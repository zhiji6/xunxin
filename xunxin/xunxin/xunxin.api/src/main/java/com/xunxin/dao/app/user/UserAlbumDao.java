package com.xunxin.dao.app.user;

import java.util.List;

import com.xunxin.vo.user.UserAlbum;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月21日 -- 上午10:39:05
 * @Version 1.0
 * @Description
 */
public interface UserAlbumDao {

	List<UserAlbum> findByUserId(Integer userId);

	void savePhoto(UserAlbum album);

}
