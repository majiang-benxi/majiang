package com.mahjong.server.netty.model;

public class EnterRoomRespModel extends RoomRespModel {
	private int roomState;

	public int getRoomState() {
		return roomState;
	}

	public void setRoomState(int roomState) {
		this.roomState = roomState;
	}

}
