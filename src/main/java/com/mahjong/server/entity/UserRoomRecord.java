package com.mahjong.server.entity;

import java.util.Date;

public class UserRoomRecord {
    private Integer id;

    private Integer roomRecordId;

    private Integer roomNum;

    private Integer userId;

    private Byte userDirection;

    private Date joinTime;

    private Integer lastScore;

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

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Integer getLastScore() {
        return lastScore;
    }

    public void setLastScore(Integer lastScore) {
        this.lastScore = lastScore;
    }
}