package com.xunxin.dao.qa;

import com.xunxin.vo.qa.XunxinUserDeviceToken;
import com.xunxin.vo.qa.XunxinUserDeviceTokenExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface XunxinUserDeviceTokenMapper {
    int countByExample(XunxinUserDeviceTokenExample example);

    int deleteByExample(XunxinUserDeviceTokenExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XunxinUserDeviceToken record);

    int insertSelective(XunxinUserDeviceToken record);

    List<XunxinUserDeviceToken> selectByExample(XunxinUserDeviceTokenExample example);

    XunxinUserDeviceToken selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XunxinUserDeviceToken record, @Param("example") XunxinUserDeviceTokenExample example);

    int updateByExample(@Param("record") XunxinUserDeviceToken record, @Param("example") XunxinUserDeviceTokenExample example);

    int updateByPrimaryKeySelective(XunxinUserDeviceToken record);

    int updateByPrimaryKey(XunxinUserDeviceToken record);
    
    XunxinUserDeviceToken selectByUserId(Integer userId);
}