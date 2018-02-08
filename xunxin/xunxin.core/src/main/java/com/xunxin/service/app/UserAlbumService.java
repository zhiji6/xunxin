package com.xunxin.service.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.app.user.UserAlbumDao;
import com.xunxin.vo.user.UserAlbum;

@Service("userAlbumService")
public class UserAlbumService {

	@Autowired
	private UserAlbumDao userAlbumDao;

	public List<UserAlbum> findByUserId(int userId) {
		return userAlbumDao.findByUserId(userId);
	}

	public void save(UserAlbum album) {
		userAlbumDao.savePhoto(album);
	}

    public void delete_album_information(int userId, int photoId) {
        userAlbumDao.delete_album_information(userId,photoId);
    }

    public void removeAll(int userId) {
        userAlbumDao.removeAll(userId);
    }
	
	
	
	
	
}
