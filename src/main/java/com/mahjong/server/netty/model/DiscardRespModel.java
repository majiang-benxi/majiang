package com.mahjong.server.netty.model;

import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.PlayerInfo;

public class DiscardRespModel  extends RoomRespModel{
	public DiscardRespModel() {
		super();
	}

	public DiscardRespModel(RoomContext roomContext) {
		super(roomContext);
	}

	public DiscardRespModel getPlayView(PlayerLocation playerLocation) {
		for (PlayerInfo playerInfo : super.getPlayers()) {
			if (playerInfo.getUserLocation() == playerLocation.getCode()) {
				continue;
			} else {
				playerInfo = playerInfo.getOtherPlayerInfoView();
			}
		}
		return this;
	}

}
