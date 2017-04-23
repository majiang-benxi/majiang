package com.mahjong.server.entity;

import java.util.Date;

public class RoomRecord {
    private Integer id;

    private Integer roomNum;

    private String roomRule;

    private Integer creatorId;

    private Date createTime;

    private Byte roomState;

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
}