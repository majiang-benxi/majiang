package com.mahjong.server.entity;

import java.util.Date;

public class MessageInfo {
    private Integer id;

    private Byte mesType;

    private Byte mesPosition;

    private String mesTitle;

    private String messageContent;

    private Date createTime;

    private Integer intervalTime;

    private Byte state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getMesType() {
        return mesType;
    }

    public void setMesType(Byte mesType) {
        this.mesType = mesType;
    }

    public Byte getMesPosition() {
        return mesPosition;
    }

    public void setMesPosition(Byte mesPosition) {
        this.mesPosition = mesPosition;
    }

    public String getMesTitle() {
        return mesTitle;
    }

    public void setMesTitle(String mesTitle) {
        this.mesTitle = mesTitle == null ? null : mesTitle.trim();
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}