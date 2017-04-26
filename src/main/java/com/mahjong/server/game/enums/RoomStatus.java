package com.mahjong.server.game.enums;

public enum RoomStatus {
	
	waitUser(0),waitForReady(1),playing(2),close(3);
	
	private int code;
	
	RoomStatus(int code){
		this.code = code;
	}
	
	public int getCode(){
		return this.code;
	}

}
