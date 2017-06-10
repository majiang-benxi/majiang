package com.mahjong.server.vo;

public class UserRoomActScore {
	
	private String winActionTypes;
	
	private Integer actionScore;

	public Integer getActionScore() {
		return actionScore;
	}

	public void setActionScore(Integer actionScore) {
		this.actionScore = actionScore;
	}
	
	public String getWinActionTypes() {
		return winActionTypes;
	}

	public void setWinActionTypes(String winActionTypes) {
		this.winActionTypes = winActionTypes;
	}

}
