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
    
    private String changeTimeStr;

    private String changecause;
    
    private Integer changeTypeNum;

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

    public String getChangecause() {
        return changecause;
    }

    public void setChangecause(String changecause) {
        this.changecause = changecause == null ? null : changecause.trim();
    }

	public String getChangeTimeStr() {
		return changeTimeStr;
	}

	public void setChangeTimeStr(String changeTimeStr) {
		this.changeTimeStr = changeTimeStr;
	}

	public Integer getChangeTypeNum() {
		return changeTypeNum;
	}

	public void setChangeTypeNum(Integer changeTypeNum) {
		this.changeTypeNum = changeTypeNum;
	}
}