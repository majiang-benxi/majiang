package com.mahjong.server.netty.model;

import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.PlayerLocation;

public class DiscardRespModel  extends RoomRespModel{
	
	private int roomState;
	public int getRoomState() {
		return roomState;
	}
	
	public void setRoomState(int roomState) {
		this.roomState = roomState;
	}
	
	public DiscardRespModel() {
		super();
	}

	public DiscardRespModel(RoomContext roomContext, PlayerLocation playerLocation) {
		super(roomContext, playerLocation);
		this.roomState = roomContext.getRoomStatus().getCode();
	}

	public DiscardRespModel(RoomContext roomContext, PlayerLocation playerLocation,boolean isWinView) {
		super(roomContext, playerLocation,isWinView);

	}



}
