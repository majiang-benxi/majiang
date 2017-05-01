package com.mahjong.server.bo;

import java.util.List;

import com.mahjong.server.game.object.PlayerInfo;

/**
 * 麻将信息视图显示信息
 */
public class MajiangPlayView {
	
	private String ruleStrategy;
	private int fangKaStrategy;
	private int roomId;
	private List<PlayerInfo> players;
	private int curUserLocation;

	public String getRuleStrategy() {
		return ruleStrategy;
	}

	public void setRuleStrategy(String ruleStrategy) {
		this.ruleStrategy = ruleStrategy;
	}

	public int getFangKaStrategy() {
		return fangKaStrategy;
	}

	public void setFangKaStrategy(int fangKaStrategy) {
		this.fangKaStrategy = fangKaStrategy;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public List<PlayerInfo> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerInfo> players) {
		this.players = players;
	}

	public int getCurUserLocation() {
		return curUserLocation;
	}

	public void setCurUserLocation(int curUserLocation) {
		this.curUserLocation = curUserLocation;
	}

}
