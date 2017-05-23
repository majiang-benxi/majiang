package com.mahjong.server.netty.model;

import com.mahjong.server.game.action.Action;

public class AskChoiceRespModel {
	private Action action;

	public AskChoiceRespModel(Action action) {
		super();
		this.action = action;
	}

	public AskChoiceRespModel() {
		super();
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

}
