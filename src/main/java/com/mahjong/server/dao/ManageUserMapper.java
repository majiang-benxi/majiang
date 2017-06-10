package com.mahjong.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mahjong.server.entity.ManageUser;
import com.mahjong.server.entity.ManageUserExample;

public interface ManageUserMapper {
    int countByExample(ManageUserExample example);

    int deleteByExample(ManageUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ManageUser record);

    int insertSelective(ManageUser record);

    List<ManageUser> selectByExample(ManageUserExample example);

    ManageUser selectByPrimaryKey(Integer id);

    List selectByCdt(ManageUser record);

    int updateByExampleSelective(@Param("record") ManageUser record,@Param("example")  ManageUserExample example);

    int updateByPrimaryKeySelective(ManageUser record);

    int updateUserSate(@Param("id")Integer id, @Param("state") Integer state);

	ManageUser selectManageUserByUname(@Param("userName")String userName);

	List<ManageUser> selectAllManageUserLimit(@Param("datemin")String datemin,@Param("datemax")String datemax,@Param("searchUname")String searchUname,@Param("start")Integer start,@Param("count") Integer count);

	int selectAllManageUserCount(@Param("datemin")String datemin,@Param("datemax")String datemax,@Param("searchUname")String searchUname);
	
}