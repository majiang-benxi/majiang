package com.mahjong.server.entity;

import java.util.Date;

public class RoomCartChange {
    private Integer id;

    private Integer manageUserId;

    private String manageName;

    private Integer userId;

    private String userName;

    private Integer changeNum;

    private Date changeTime;

    private Byte isSuccess;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManageUserId() {
        return manageUserId;
    }

    public void setManageUserId(Integer manageUserId) {
        this.manageUserId = manageUserId;
    }

    public String getManageName() {
        return manageName;
    }

    public void setManageName(String manageName) {
        this.manageName = manageName == null ? null : manageName.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(Integer changeNum) {
        this.changeNum = changeNum;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public Byte getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Byte isSuccess) {
        this.isSuccess = isSuccess;
    }
}