package com.mahjong.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mahjong.server.entity.MessageInfo;
import com.mahjong.server.entity.MessageInfoExample;

public interface MessageInfoMapper {
    int countByExample(MessageInfoExample example);

    int deleteByExample(MessageInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(MessageInfo record);

    List<MessageInfo> selectByExample(MessageInfoExample example);

    MessageInfo selectByPrimaryKey(Integer id);

    List selectByCdt(MessageInfo record);

    int updateByExampleSelective( @Param("record")MessageInfo record, @Param("example")  MessageInfoExample example);

    int updateByPrimaryKeySelective(MessageInfo record);

	int deleteMessageInfoById(Integer id);

	int selectMessageInfoCount(@Param("msgPositionnum")Integer msgPositionnum,@Param("mesgstate")Integer mesgstate, @Param("datemin")String datemin, @Param("datemax")String datemax);

	List<MessageInfo> selectMessageInfoLimit(@Param("msgPositionnum")Integer msgPositionnum,@Param("mesgstate")Integer mesgstate, @Param("datemin")String datemin, @Param("datemax")String datemax,
			 @Param("start") Integer startIndex,@Param("count")Integer eachCount);
}