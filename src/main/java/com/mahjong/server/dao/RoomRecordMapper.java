package com.mahjong.server.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.mahjong.server.entity.RoomRecord;
import com.mahjong.server.entity.RoomRecordExample;

public interface RoomRecordMapper {
    int countByExample(RoomRecordExample example);

    int deleteByExample(RoomRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RoomRecord record);

    int insertSelective(RoomRecord record);

    List<RoomRecord> selectByExample(RoomRecordExample example);

    RoomRecord selectByPrimaryKey(Integer id);

    List selectByCdt(RoomRecord record);

    int updateByExampleSelective(@Param("record") RoomRecord record, @Param("example") RoomRecordExample example);

    int updateByPrimaryKeySelective(RoomRecord record);


	int deleteRoomRecordInfoByID(@Param("recordId")Integer recordId);

	List<RoomRecord> selectRoomRecordInfoList(@Param("recordIdList")Set<Integer> recordIdList);

	int selectRoomRecordInfoCount(@Param("roomNum")String roomNum, @Param("datemin")String datemin, @Param("datemax")String datemax);

	List<RoomRecord> selectRoomRecordInfoLimit( @Param("roomNum")String roomNum, @Param("datemin")String datemin, @Param("datemax")String datemax,
			 @Param("start") Integer startIndex,@Param("count")Integer eachCount);
}