package com.mahjong.server.entity;

import java.util.Date;

public class UserRoomRecord {
    private Integer id;

    private Integer roomRecordId;

    private Integer roomNum;

    private Integer userId;

    private Byte userDirection;

    private Date operateTime;

    private Byte operateType;

    private String userIp;

    private String operateCause;

    private Integer winTimes;

    private Integer loseTimes;

    private Integer huTimes;
    
    private String operateTimeStr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomRecordId() {
        return roomRecordId;
    }

    public void setRoomRecordId(Integer roomRecordId) {
        this.roomRecordId = roomRecordId;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getUserDirection() {
        return userDirection;
    }

    public void setUserDirection(Byte userDirection) {
        this.userDirection = userDirection;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Byte getOperateType() {
        return operateType;
    }

    public void setOperateType(Byte operateType) {
        this.operateType = operateType;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp == null ? null : userIp.trim();
    }

    public String getOperateCause() {
        return operateCause;
    }

    public void setOperateCause(String operateCause) {
        this.operateCause = operateCause;
    }

    public Integer getWinTimes() {
        return winTimes;
    }

    public void setWinTimes(Integer winTimes) {
        this.winTimes = winTimes;
    }

    public Integer getLoseTimes() {
        return loseTimes;
    }

    public void setLoseTimes(Integer loseTimes) {
        this.loseTimes = loseTimes;
    }

    public Integer getHuTimes() {
        return huTimes;
    }

    public void setHuTimes(Integer huTimes) {
        this.huTimes = huTimes;
    }

	public String getOperateTimeStr() {
		return operateTimeStr;
	}

	public void setOperateTimeStr(String operateTimeStr) {
		this.operateTimeStr = operateTimeStr;
	}

}