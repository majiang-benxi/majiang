package com.mahjong.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mahjong.server.entity.UpdateInfo;
import com.mahjong.server.entity.UpdateInfoExample;

public interface UpdateInfoMapper {
    int countByExample(UpdateInfoExample example);

    int deleteByExample(UpdateInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UpdateInfo record);

    int insertSelective(UpdateInfo record);

    List<UpdateInfo> selectByExample(UpdateInfoExample example);

    UpdateInfo selectByPrimaryKey(Integer id);

    List selectByCdt(UpdateInfo record);

    int updateByExampleSelective(@Param("record") UpdateInfo record, @Param("example") UpdateInfoExample example);

    int updateByExample(@Param("record") UpdateInfo record, @Param("example") UpdateInfoExample example);

    int updateByPrimaryKeySelective(UpdateInfo record);

    int updateByPrimaryKey(UpdateInfo record);

	List<UpdateInfo> selectUpdateInfoByDeviceType(@Param("deviceType")Integer deviceType,@Param("appVersion")Float version);
}