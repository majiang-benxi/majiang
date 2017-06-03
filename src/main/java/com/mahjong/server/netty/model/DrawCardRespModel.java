package com.mahjong.server.netty.model;

import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.PlayerLocation;

public class DrawCardRespModel extends RoomRespModel {

	public DrawCardRespModel(RoomContext roomContext, PlayerLocation userLocation) {
		super(roomContext, userLocation);
	}

	public DrawCardRespModel() {
		super();
	}

}
