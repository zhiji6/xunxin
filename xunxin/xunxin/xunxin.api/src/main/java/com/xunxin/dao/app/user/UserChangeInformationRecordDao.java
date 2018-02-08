package com.xunxin.dao.app.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xunxin.vo.user.UserChangeInformationRecord;

public interface UserChangeInformationRecordDao {

	List<UserChangeInformationRecord> findByUserId(Integer userId);

	List<UserChangeInformationRecord> findByUserIdAndField(@Param("userId") Integer userId, @Param("fieldName") String fieldName);

	void save(UserChangeInformationRecord record);

}
