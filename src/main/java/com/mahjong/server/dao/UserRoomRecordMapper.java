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

    int updateByExample(@Param("record") UserRoomRecord record, @Param("example") UserRoomRecordExample example);

    int updateByPrimaryKeySelective(UserRoomRecord record);

    int updateByPrimaryKey(UserRoomRecord record);
}