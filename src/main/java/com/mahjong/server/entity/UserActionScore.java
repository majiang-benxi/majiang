package com.mahjong.server.entity;

public class UserActionScore {
    private Integer id;

    private Integer userRoomRecordId;

    private Integer actionType;

    private Integer actionScore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserRoomRecordId() {
        return userRoomRecordId;
    }

    public void setUserRoomRecordId(Integer userRoomRecordId) {
        this.userRoomRecordId = userRoomRecordId;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Integer getActionScore() {
        return actionScore;
    }

    public void setActionScore(Integer actionScore) {
        this.actionScore = actionScore;
    }
}