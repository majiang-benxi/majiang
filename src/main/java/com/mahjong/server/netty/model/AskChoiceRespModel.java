package com.mahjong.server.netty.model;

import java.util.List;

import com.mahjong.server.game.action.Action;

public class AskChoiceRespModel {
	private List<Action> actions;

	public AskChoiceRespModel(List<Action> actions) {
		super();
		this.actions = actions;
	}

	public AskChoiceRespModel() {
		super();
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

}
