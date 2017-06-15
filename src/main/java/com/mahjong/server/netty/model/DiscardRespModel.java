package com.mahjong.server.netty.model;

import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.PlayerLocation;

public class DiscardRespModel  extends RoomRespModel{
	public DiscardRespModel() {
		super();
	}

	public DiscardRespModel(RoomContext roomContext, PlayerLocation playerLocation) {
		super(roomContext, playerLocation);
	}

	public DiscardRespModel(RoomContext roomContext, PlayerLocation playerLocation,boolean hide) {
		super(roomContext, playerLocation,hide);

	}


}
