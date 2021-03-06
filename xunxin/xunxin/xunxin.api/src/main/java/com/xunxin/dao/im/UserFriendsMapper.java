package com.xunxin.dao.im;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xunxin.vo.im.UserFriends;

public interface UserFriendsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserFriends record);

    int insertSelective(UserFriends record);

    UserFriends selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserFriends record);

    int updateByPrimaryKey(UserFriends record);

	List<Integer> queryFriendsBy(Integer userId);

	List<UserFriends> findAllFriendsByUserId(Integer userId);

	void insertFriend(List insertList);

	List<UserFriends> findAttentionsMe(Integer userId);

	List<UserFriends> findAttentionsToMe(Integer userId);

	void deletefriend(@Param("userId")Integer userId, @Param("id")Integer id);

	void deleteShield(@Param("userId")Integer userId, @Param("id")Integer id);

	void deleteReport(@Param("userId")Integer userId, @Param("id")Integer id);


}