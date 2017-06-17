package com.mahjong.server.vo;

public class ScoreRecordVO {
	
	private String nickName;
	
	private String weixinId;
	
	private  int location;
	
	private String winActionTypes;
	
	private int totalScore;
	
	private Integer winTimes;

    private Integer loseTimes;

    private Integer huTimes;
	
	

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

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public Integer getWinTimes() {
		return winTimes;
	}

	public void setWinTimes(Integer winTimes) {
		this.winTimes = winTimes;
	}

	public Integer getLoseTimes() {
		return loseTimes;
	}

	public void setLoseTimes(Integer loseTimes) {
		this.loseTimes = loseTimes;
	}

	public Integer getHuTimes() {
		return huTimes;
	}

	public void setHuTimes(Integer huTimes) {
		this.huTimes = huTimes;
	}


}
