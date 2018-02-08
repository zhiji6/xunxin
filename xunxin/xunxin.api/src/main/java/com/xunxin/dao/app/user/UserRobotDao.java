package com.xunxin.dao.app.user;

import java.util.List;

import com.xunxin.vo.user.UserRobot;

public interface UserRobotDao {

    List<UserRobot> findAll();
    
    UserRobot findOneById(Integer pid);
    
    UserRobot findOneByName(String nickName);
    
    void insert(UserRobot robot);
    
    void delete(Integer pid);
    
    void update(UserRobot robot);

    List<UserRobot> findByGender(String gender);
    
    
}
