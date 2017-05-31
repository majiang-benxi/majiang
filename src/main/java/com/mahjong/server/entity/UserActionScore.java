package com.mahjong.server.entity;

import java.util.Date;

public class UserActionScore {
    private Integer id;

    private Integer userId;

    private Integer roomRecordId;

    private Integer roundNum;

    private Integer actionType;

    private Date createTime;

    private Integer actionScore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoomRecordId() {
        return roomRecordId;
    }

    public void setRoomRecordId(Integer roomRecordId) {
        this.roomRecordId = roomRecordId;
    }

    public Integer getRoundNum() {
        return roundNum;
    }

    public void setRoundNum(Integer roundNum) {
        this.roundNum = roundNum;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getActionScore() {
        return actionScore;
    }

    public void setActionScore(Integer actionScore) {
        this.actionScore = actionScore;
    }
}