package com.mahjong.server.netty.model;

public class EnterRoomReqModel  extends RequestBaseMode{
	private int roomId;

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

}
