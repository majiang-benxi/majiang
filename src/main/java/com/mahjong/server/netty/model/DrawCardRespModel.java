package com.mahjong.server.netty.model;

import com.mahjong.server.game.enums.PlayerLocation;

public class DrawCardRespModel extends RoomRespModel {
	private PlayerLocation userLocation;

	public DrawCardRespModel(PlayerLocation userLocation) {
		super();
		this.userLocation = userLocation;
	}

	public DrawCardRespModel() {
		super();
	}


	public PlayerLocation getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(PlayerLocation userLocation) {
		this.userLocation = userLocation;
	}

}
