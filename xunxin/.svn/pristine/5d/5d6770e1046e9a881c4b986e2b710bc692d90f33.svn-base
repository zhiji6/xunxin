package com.xunxin.dao.circle;

import com.xunxin.vo.circle.UserCirclePhoto;
import com.xunxin.vo.circle.UserCirclePhotoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserCirclePhotoMapper {
    int countByExample(UserCirclePhotoExample example);

    int deleteByExample(UserCirclePhotoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserCirclePhoto record);

    int insertSelective(UserCirclePhoto record);

    List<UserCirclePhoto> selectByExample(UserCirclePhotoExample example);

    UserCirclePhoto selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserCirclePhoto record, @Param("example") UserCirclePhotoExample example);

    int updateByExample(@Param("record") UserCirclePhoto record, @Param("example") UserCirclePhotoExample example);

    int updateByPrimaryKeySelective(UserCirclePhoto record);

    int updateByPrimaryKey(UserCirclePhoto record);
}