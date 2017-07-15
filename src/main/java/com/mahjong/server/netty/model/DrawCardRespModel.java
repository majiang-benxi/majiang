package com.mahjong.server.netty.model;

import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.PlayerLocation;

public class DrawCardRespModel extends RoomRespModel {
	
	private int roomState;
	public int getRoomState() {
		return roomState;
	}
	
	public void setRoomState(int roomState) {
		this.roomState = roomState;
	}
	

	public DrawCardRespModel(RoomContext roomContext, PlayerLocation userLocation) {
		super(roomContext, userLocation);
		this.roomState = roomContext.getRoomStatus().getCode();
	}


}
