package com.mahjong.server.vo;

public class ScoreRecordVO {
	
	private String nickName;
	
	private String winActionTypes;
	
	private int totalScore;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getWinActionTypes() {
		return winActionTypes;
	}

	public void setWinActionTypes(String winActionTypes) {
		this.winActionTypes = winActionTypes;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}


}
