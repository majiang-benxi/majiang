package com.mahjong.server.game.object;

import com.mahjong.server.game.action.ActionAndLocation;

public class DisCardActionAndLocation {
	private ActionAndLocation actionAndLocation;
	private int tileGroupType;// 打牌的类型。单张，吃碰杠胡

	public DisCardActionAndLocation(ActionAndLocation actionAndLocation, int tileGroupType) {
		super();
		this.actionAndLocation = actionAndLocation;
		this.tileGroupType = tileGroupType;
	}

	public ActionAndLocation getActionAndLocation() {
		return actionAndLocation;
	}

	public void setActionAndLocation(ActionAndLocation actionAndLocation) {
		this.actionAndLocation = actionAndLocation;
	}

	public int getTileGroupType() {
		return tileGroupType;
	}

	public void setTileGroupType(int tileGroupType) {
		this.tileGroupType = tileGroupType;
	}

}
