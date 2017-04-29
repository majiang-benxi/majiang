package com.mahjong.server.vo;

import java.util.List;

import com.mahjong.server.entity.UserActionScore;

public class ScoreRecordVO {
	private String nickName;
	private List<UserActionScore> userActionScores;
	private int totalScore;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<UserActionScore> getUserActionScores() {
		return userActionScores;
	}

	public void setUserActionScores(List<UserActionScore> userActionScores) {
		this.userActionScores = userActionScores;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

}
