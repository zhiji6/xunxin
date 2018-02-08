package com.xunxin.service.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.circle.UserCirclePhotoMapper;
import com.xunxin.vo.circle.UserCirclePhoto;
import com.xunxin.vo.circle.UserCirclePhotoExample;
import com.xunxin.vo.circle.UserCirclePhotoExample.Criteria;

@Service("userCirclePhotoService")
public class UserCirclePhotoService {
	@Autowired
	private UserCirclePhotoMapper userCirclePhotoMapper;

	public List<UserCirclePhoto> findByUserId(Integer userId, String circleId) {
		UserCirclePhotoExample example=new UserCirclePhotoExample();
		Criteria criteria = example.createCriteria();  
		criteria.andUseridEqualTo(userId);
		criteria.andCircleIdEqualTo(circleId);
		criteria.andIsDeleteEqualTo(0);
		List<UserCirclePhoto> list = userCirclePhotoMapper.selectByExample(example);
		return list;
	}

	public void save(UserCirclePhoto cphoto) {
		userCirclePhotoMapper.insert(cphoto);
	}
	public List<UserCirclePhoto> findById(String id) {
		UserCirclePhotoExample example=new UserCirclePhotoExample();
		Criteria criteria = example.createCriteria();  
		criteria.andCircleIdEqualTo(id);
		criteria.andIsDeleteEqualTo(0);
		List<UserCirclePhoto> list = userCirclePhotoMapper.selectByExample(example);
		return list;
	}
}
