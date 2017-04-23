package com.mahjong.server.dao;

import java.util.List;

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

    int updateByExample(@Param("record") RoomRecord record, @Param("example") RoomRecordExample example);

    int updateByPrimaryKeySelective(RoomRecord record);

    int updateByPrimaryKey(RoomRecord record);

	int deleteRoomRecordInfoByID(@Param("recordId")Integer recordId);
}