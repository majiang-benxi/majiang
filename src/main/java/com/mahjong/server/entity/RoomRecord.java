package com.mahjong.server.entity;

import java.util.Date;

public class RoomRecord {
    private Integer id;

    private Integer roomNum;

    private String roomRule;

    private Integer creatorId;

    private Date createTime;
    
    private Date endTime;

    private Byte roomState;
    
    private String createTimeStr;
    
    private String endTimeStr;
    
    private Integer eastUid;
    
    private Integer southUid;
    
    private Integer westUid;
    
    private Integer northUid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public String getRoomRule() {
        return roomRule;
    }

    public void setRoomRule(String roomRule) {
        this.roomRule = roomRule == null ? null : roomRule.trim();
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getRoomState() {
        return roomState;
    }

    public void setRoomState(Byte roomState) {
        this.roomState = roomState;
    }

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public Integer getEastUid() {
		return eastUid;
	}

	public void setEastUid(Integer eastUid) {
		this.eastUid = eastUid;
	}

	public Integer getSouthUid() {
		return southUid;
	}

	public void setSouthUid(Integer southUid) {
		this.southUid = southUid;
	}

	public Integer getWestUid() {
		return westUid;
	}

	public void setWestUid(Integer westUid) {
		this.westUid = westUid;
	}

	public Integer getNorthUid() {
		return northUid;
	}

	public void setNorthUid(Integer northUid) {
		this.northUid = northUid;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	
}