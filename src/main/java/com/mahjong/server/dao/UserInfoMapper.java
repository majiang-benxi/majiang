package com.mahjong.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.entity.UserInfoExample;

public interface UserInfoMapper {
    int countByExample(UserInfoExample example);

    int deleteByExample(UserInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(UserInfo record);

    List<UserInfo> selectByExample(UserInfoExample example);

    UserInfo selectByPrimaryKey(Integer id);

    List selectByCdt(UserInfo record);

    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByPrimaryKeySelective(UserInfo record);

	UserInfo selectUserInfoByWeiXinMark(@Param("weixinMark")String weiXinMark);

	int deleteUserInfoByWeiXinMark(@Param("weixinMark")String weiXinMark);

	int deleteUserInfoByID(@Param("id")Integer userId);

	int updateUserRoomCard(@Param("userId")Integer userId,@Param("cartNum") Integer cartNum);

	int selectAllUserCount(@Param("userId")String uid, @Param("datemin")String datemin, @Param("datemax")String datemax, @Param("searchUname")String searchUname);

	List<UserInfo> selectAllUserLimit(@Param("userId")String uid, @Param("datemin")String datemin, @Param("datemax")String datemax, @Param("searchUname")String searchUname
			, @Param("start")Integer startIndex, @Param("count")Integer eachCount);
}