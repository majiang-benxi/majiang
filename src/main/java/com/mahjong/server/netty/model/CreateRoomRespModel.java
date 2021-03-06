package com.mahjong.server.netty.model;

import com.mahjong.server.game.context.RoomContext;

public class CreateRoomRespModel extends RoomRespModel {
	
	private int roomState;
	
	public CreateRoomRespModel() {

	}
	public CreateRoomRespModel(String weixinId ,boolean result,RoomContext roomContex) {
		super(weixinId, roomContex);
		if(result){
			this.setResult(result);
			this.setMsg("恭喜您，创建房间成功！");
			this.roomState = roomContex.getRoomStatus().getCode();
		}else{
			this.setResult(result);
			this.setMsg("创建房间失败！");
		}
	}
	public int getRoomState() {
		return roomState;
	}
	public void setRoomState(int roomState) {
		this.roomState = roomState;
	}
	
}
