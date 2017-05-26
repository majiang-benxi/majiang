package com.mahjong.server.netty.model;

import com.mahjong.server.game.context.RoomContext;

public class DiscardRespModel  extends RoomRespModel{
	public DiscardRespModel() {
		super();
	}

	public DiscardRespModel(RoomContext roomContext) {
		super(roomContext);
	}
}
