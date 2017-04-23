package com.mahjong.server.entity;

import java.util.Date;

public class UserInfo {
    private Integer id;

    private String nickName;

    private String weixinMark;

    private Byte sex;

    private String headImgurl;

    private Date createTime;

    private Integer roomCartNum;

    private Integer scoreNum;

    private String lastLoginIp;

    private Date lastLoginTime;

    private Integer currRoom;

    private Byte state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getWeixinMark() {
        return weixinMark;
    }

    public void setWeixinMark(String weixinMark) {
        this.weixinMark = weixinMark == null ? null : weixinMark.trim();
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getHeadImgurl() {
        return headImgurl;
    }

    public void setHeadImgurl(String headImgurl) {
        this.headImgurl = headImgurl == null ? null : headImgurl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRoomCartNum() {
        return roomCartNum;
    }

    public void setRoomCartNum(Integer roomCartNum) {
        this.roomCartNum = roomCartNum;
    }

    public Integer getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(Integer scoreNum) {
        this.scoreNum = scoreNum;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getCurrRoom() {
        return currRoom;
    }

    public void setCurrRoom(Integer currRoom) {
        this.currRoom = currRoom;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}