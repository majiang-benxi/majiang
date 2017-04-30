package com.mahjong.server.netty.model;

import java.util.List;

import com.mahjong.server.game.object.PlayerInfo;

public class MajiangPlayView {
	private String ruleStrategy;
	private int fangKaStrategy;
	private int roomId;
	private List<PlayerInfo> players;

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

}
