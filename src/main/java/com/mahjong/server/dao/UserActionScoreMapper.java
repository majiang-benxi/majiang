package com.mahjong.server.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.mahjong.server.entity.UserActionScore;
import com.mahjong.server.entity.UserActionScoreExample;
import com.mahjong.server.vo.UserRecordScoreVO;

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

    int updateByPrimaryKeySelective(UserActionScore record);

	List<UserActionScore> selectUserActionScoreInfoByRecordId(@Param("recordId")Integer userId);

	List<UserActionScore> selectUserActionScoreInfos(@Param("userRoomRecordIdList")Set<Integer> userRoomRecordIdList);

	int getUserPlayRecordInfoCount(@Param("userId")String uid, @Param("roomNum")String roomNum, @Param("datemin")String datemin, @Param("datemax")String datemax);

	List<UserRecordScoreVO> getUserPlayRecordInfoLimit(@Param("userId")String uid, @Param("roomNum")String roomNum, @Param("datemin")String datemin, @Param("datemax")String datemax,
			 @Param("start") Integer startIndex,@Param("count")Integer eachCount);

	List<UserActionScore> selectLatestUserRoomRecordScoreInfo(@Param("userId")Integer userId, @Param("latestRoomRecordIds")List<Integer> latestRoomRecordIds);

	List<Integer> selectLatestRoomRecordIds(@Param("userId")Integer userId, @Param("topNum")Integer topNum);

}