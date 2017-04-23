package com.mahjong.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mahjong.server.entity.UserActionScore;
import com.mahjong.server.entity.UserActionScoreExample;

public interface UserActionScoreMapper {
    int countByExample(UserActionScoreExample example);

    int deleteByExample(UserActionScoreExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserActionScore record);

    int insertSelective(UserActionScore record);

    List<UserActionScore> selectByExample(UserActionScoreExample example);

    UserActionScore selectByPrimaryKey(Integer id);

    List selectByCdt(UserActionScore record);

    int updateByExampleSelective(@Param("record") UserActionScore record, @Param("example") UserActionScoreExample example);

    int updateByExample(@Param("record") UserActionScore record, @Param("example") UserActionScoreExample example);

    int updateByPrimaryKeySelective(UserActionScore record);

    int updateByPrimaryKey(UserActionScore record);

	List<UserActionScore> selectUserActionScoreInfoByRecordId(@Param("recordId")Integer userId);

}