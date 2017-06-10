package com.mahjong.server.vo;

import java.util.Date;

public class UserRecordScoreVO {
	
	private Integer userId;
	private Integer roomNum;
	private Integer roundNum;
	
	private Date roomCreateTime;
	
	private Integer scoreNum;
	
	private String roomCreateTimeStr;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	public Integer getRoundNum() {
		return roundNum;
	}

	public void setRoundNum(Integer roundNum) {
		this.roundNum = roundNum;
	}

	public Integer getScoreNum() {
		return scoreNum;
	}

	public void setScoreNum(Integer scoreNum) {
		this.scoreNum = scoreNum;
	}

	public Date getRoomCreateTime() {
		return roomCreateTime;
	}

	public void setRoomCreateTime(Date roomCreateTime) {
		this.roomCreateTime = roomCreateTime;
	}

	public String getRoomCreateTimeStr() {
		return roomCreateTimeStr;
	}

	public void setRoomCreateTimeStr(String roomCreateTimeStr) {
		this.roomCreateTimeStr = roomCreateTimeStr;
	}

}
