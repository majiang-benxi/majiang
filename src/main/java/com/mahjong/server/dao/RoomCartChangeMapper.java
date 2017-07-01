package com.mahjong.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mahjong.server.entity.RoomCartChange;
import com.mahjong.server.entity.RoomCartChangeExample;

public interface RoomCartChangeMapper {
    int countByExample(RoomCartChangeExample example);

    int deleteByExample(RoomCartChangeExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(RoomCartChange record);

    List<RoomCartChange> selectByExample(RoomCartChangeExample example);

    RoomCartChange selectByPrimaryKey(Integer id);

    List selectByCdt(RoomCartChange record);

    int updateByExampleSelective(@Param("record") RoomCartChange record, @Param("example") RoomCartChangeExample example);

    int updateByPrimaryKeySelective(RoomCartChange record);

	int selectRoomCardChangeInfoCount(@Param("userID")Integer userID,@Param("changeTypeNum")Integer changeTypeNum, @Param("datemin")String datemin, @Param("datemax")String datemax);

	List<RoomCartChange> selectRoomCardChangeInfoLimit(@Param("userID")Integer userID,@Param("changeTypeNum")Integer changeTypeNum, @Param("datemin")String datemin, @Param("datemax")String datemax, @Param("start") Integer startIndex,@Param("count")Integer eachCount);

}