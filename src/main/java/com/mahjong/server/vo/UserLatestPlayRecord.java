package com.mahjong.server.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mahjong.server.entity.RoomRecord;

public class UserLatestPlayRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private RoomRecord roomRecord;
	private List<UserRoomActScore> userActionScoreList = new ArrayList<UserRoomActScore>();
	
	public RoomRecord getRoomRecord() {
		return roomRecord;
	}
	public void setRoomRecord(RoomRecord roomRecord) {
		this.roomRecord = roomRecord;
	}
	public List<UserRoomActScore> getUserActionScoreList() {
		return userActionScoreList;
	}
	public void setUserActionScoreList(List<UserRoomActScore> userActionScoreList) {
		this.userActionScoreList = userActionScoreList;
	}
	
	

}
