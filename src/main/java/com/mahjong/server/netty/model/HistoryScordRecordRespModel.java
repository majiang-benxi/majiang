package com.mahjong.server.netty.model;

import java.util.List;

import com.mahjong.server.entity.UserActionScore;

public class HistoryScordRecordRespModel {
	private List<UserActionScore> userActionScores;

	public List<UserActionScore> getUserActionScores() {
		return userActionScores;
	}

	public void setUserActionScores(List<UserActionScore> userActionScores) {
		this.userActionScores = userActionScores;
	}

}
