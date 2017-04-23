package com.mahjong.server.vo;

import java.io.Serializable;
import java.util.List;

import com.mahjong.server.entity.RoomRecord;
import com.mahjong.server.entity.UserActionScore;
import com.mahjong.server.entity.UserRoomRecord;

public class UserLatestPlayRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private RoomRecord roomRecord;
	private UserRoomRecord userRoomRecord;
	private List<UserActionScore> userActionScoreList;
	
	public RoomRecord getRoomRecord() {
		return roomRecord;
	}
	public void setRoomRecord(RoomRecord roomRecord) {
		this.roomRecord = roomRecord;
	}
	public UserRoomRecord getUserRoomRecord() {
		return userRoomRecord;
	}
	public void setUserRoomRecord(UserRoomRecord userRoomRecord) {
		this.userRoomRecord = userRoomRecord;
	}
	public List<UserActionScore> getUserActionScoreList() {
		return userActionScoreList;
	}
	public void setUserActionScoreList(List<UserActionScore> userActionScoreList) {
		this.userActionScoreList = userActionScoreList;
	}
	
	

}
