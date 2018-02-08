package com.xunxin.dao.im;

import com.xunxin.vo.im.UserShields;
import com.xunxin.vo.im.UserShieldsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserShieldsMapper {
    int countByExample(UserShieldsExample example);

    int deleteByExample(UserShieldsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserShields record);

    int insertSelective(UserShields record);

    List<UserShields> selectByExample(UserShieldsExample example);

    UserShields selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserShields record, @Param("example") UserShieldsExample example);

    int updateByExample(@Param("record") UserShields record, @Param("example") UserShieldsExample example);

    int updateByPrimaryKeySelective(UserShields record);

    int updateByPrimaryKey(UserShields record);

	List<UserShields> findShieldsByUserId(Integer userId);

	void deleteShield(@Param("userId")Integer userId, @Param("id")Integer id);
}