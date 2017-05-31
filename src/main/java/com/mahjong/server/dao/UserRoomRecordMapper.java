package com.mahjong.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mahjong.server.entity.UserRoomRecord;
import com.mahjong.server.entity.UserRoomRecordExample;

public interface UserRoomRecordMapper {
    int countByExample(UserRoomRecordExample example);

    int deleteByExample(UserRoomRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserRoomRecord record);

    int insertSelective(UserRoomRecord record);

    List<UserRoomRecord> selectByExample(UserRoomRecordExample example);

    UserRoomRecord selectByPrimaryKey(Integer id);

    List selectByCdt(UserRoomRecord record);

    int updateByExampleSelective(@Param("record") UserRoomRecord record, @Param("example") UserRoomRecordExample example);

    int updateByPrimaryKeySelective(UserRoomRecord record);


	List<UserRoomRecord> selectUserRoomRecordInfoByUserID(Integer userId);

	List<UserRoomRecord> selectLatestUserRoomRecordInfo(@Param("userId")Integer userId,@Param("topNum") Integer topNum);

	int getUserPlayRecordInfoCount(@Param("userId")String uid, @Param("roomNum")String roomNum, @Param("datemin")String datemin, @Param("datemax")String datemax);

	List<UserRoomRecord> getUserPlayRecordInfoLimit(@Param("userId")String uid, @Param("roomNum")String roomNum, @Param("datemin")String datemin, @Param("datemax")String datemax,
			 @Param("start") Integer startIndex,@Param("count")Integer eachCount);

	List<UserRoomRecord> selectUserRoomRecordInfoByRoomId(@Param("roomid")Integer roomid);

}