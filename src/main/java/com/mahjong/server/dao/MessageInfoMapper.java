package com.mahjong.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mahjong.server.entity.MessageInfo;
import com.mahjong.server.entity.MessageInfoExample;

public interface MessageInfoMapper {
    int countByExample(MessageInfoExample example);

    int deleteByExample(MessageInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MessageInfo record);

    int insertSelective(MessageInfo record);

    List<MessageInfo> selectByExample(MessageInfoExample example);

    MessageInfo selectByPrimaryKey(Integer id);

    List selectByCdt(MessageInfo record);

    int updateByExampleSelective( @Param("record")MessageInfo record, @Param("example")  MessageInfoExample example);

    int updateByPrimaryKeySelective(MessageInfo record);

	int deleteMessageInfoById(Integer id);
}