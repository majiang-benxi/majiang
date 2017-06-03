package com.mahjong.server.netty.model;

import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.PlayerLocation;

public class EnterRoomRespModel extends RoomRespModel {
	private int roomState;
	
	public EnterRoomRespModel() {

	}

	public EnterRoomRespModel(String weixinId, boolean result, String mesg, RoomContext roomContex) {
		super(weixinId, roomContex);
		if(result){
			this.setResult(result);
			this.setMsg(mesg);
			this.roomState = roomContex.getRoomStatus().getCode();
		}else{
			this.setResult(result);
			this.setMsg(mesg);
		}
	}

	public EnterRoomRespModel(String weixinId, boolean result, String mesg, RoomContext roomContex,
			PlayerLocation playerLocation) {
		super(roomContex, playerLocation);
		if (result) {
			this.setResult(result);
			this.setMsg(mesg);
			this.roomState = roomContex.getRoomStatus().getCode();
		} else {
			this.setResult(result);
			this.setMsg(mesg);
		}
	}

	public int getRoomState() {
		return roomState;
	}

	public void setRoomState(int roomState) {
		this.roomState = roomState;
	}

}
