package com.mahjong.server.netty.model;

public class NoticeRespModel {
	
	private Integer mesID;//消息id
	
	private Byte mesType;//消息类型，1：公告，2：广播
	
	private Integer mesState;

	private Byte mesPosition;//广播消息发放位置：1：大厅，2：房间，3：大厅房间同时(广播才有）

	private String mesTitle;//消息标题

	private String messageContent;//具体消息

	private Integer intervalTime;//轮播间隔时间单位（s）

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
		this.mesTitle = mesTitle;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Integer getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}

	public Integer getMesID() {
		return mesID;
	}

	public void setMesID(Integer mesID) {
		this.mesID = mesID;
	}

	public Integer getMesState() {
		return mesState;
	}

	public void setMesState(Integer mesState) {
		this.mesState = mesState;
	}
	
}
