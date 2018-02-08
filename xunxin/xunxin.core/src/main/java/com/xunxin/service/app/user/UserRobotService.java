package com.xunxin.service.app.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.app.user.UserRobotDao;
import com.xunxin.vo.user.UserRobot;

@Service("userRobotService")
public class UserRobotService {
    
    @Autowired
    private UserRobotDao userRobotDao;

    public List<UserRobot> findAll() {
        return userRobotDao.findAll();
    }

    public void insert(UserRobot robot) {
        userRobotDao.insert(robot);
    }

    public List<UserRobot> findByGender(String gender) {
        return userRobotDao.findByGender(gender);
    }

}
