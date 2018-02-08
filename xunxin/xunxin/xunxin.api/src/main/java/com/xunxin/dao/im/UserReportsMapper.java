package com.xunxin.dao.im;

import com.xunxin.vo.im.UserReports;
import com.xunxin.vo.im.UserReportsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserReportsMapper {
    int countByExample(UserReportsExample example);

    int deleteByExample(UserReportsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserReports record);

    int insertSelective(UserReports record);

    List<UserReports> selectByExample(UserReportsExample example);

    UserReports selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserReports record, @Param("example") UserReportsExample example);

    int updateByExample(@Param("record") UserReports record, @Param("example") UserReportsExample example);

    int updateByPrimaryKeySelective(UserReports record);

    int updateByPrimaryKey(UserReports record);

	List<UserReports> findReport(Integer userId);
	
	void deleteReport(@Param("userId")Integer userId, @Param("id")Integer id);
}