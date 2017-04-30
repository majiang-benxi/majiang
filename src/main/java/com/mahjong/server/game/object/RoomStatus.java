package com.mahjong.server.game.object;

public enum RoomStatus {

	WAIT_USERS(0), WAIT_FOR_READY(1), PLAYING(2), CLOSE(3);

	private int code;

	RoomStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}

}