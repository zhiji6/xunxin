package com.xunxin.dao.im;

import com.xunxin.vo.im.UserOnlineTime;
import com.xunxin.vo.im.UserOnlineTimeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserOnlineTimeMapper {
    int countByExample(UserOnlineTimeExample example);

    int deleteByExample(UserOnlineTimeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserOnlineTime record);

    int insertSelective(UserOnlineTime record);

    List<UserOnlineTime> selectByExample(UserOnlineTimeExample example);

    UserOnlineTime selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserOnlineTime record, @Param("example") UserOnlineTimeExample example);

    int updateByExample(@Param("record") UserOnlineTime record, @Param("example") UserOnlineTimeExample example);

    int updateByPrimaryKeySelective(UserOnlineTime record);

    int updateByPrimaryKey(UserOnlineTime record);
}