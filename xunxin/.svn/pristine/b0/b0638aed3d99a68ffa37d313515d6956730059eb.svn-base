package com.xunxin.dao.im;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xunxin.vo.im.IMRecord;

public interface IMRecordMapper {
    int deleteByPrimaryKey(String msgId);

    int insert(IMRecord record);

    int insertSelective(IMRecord record);

    IMRecord selectByPrimaryKey(String msgId);

    int updateByPrimaryKeySelective(IMRecord record);

    int updateByPrimaryKey(IMRecord record);

	List<Integer> queryImMysendsids(Integer userId);

	List<Integer> queryImSendsMyIds(Integer userId);

	Map queryChatTime(@Param("userId")Integer userId, @Param("friendId")Integer friendId);

	Integer continuousChat(Map mapUser);

	List<String> findNickNameByUserId(String phone);

	List<IMRecord> findPageList(IMRecord iMRecord);

	
}