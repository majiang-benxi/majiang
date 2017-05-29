package com.mahjong.server.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mahjong.server.game.object.PlayerInfo;

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

	// 序列化的时候使用的get方法，此方法根据访问隐藏掉其他玩家中的活牌。
	public List<PlayerInfo> getPlayers() {
		if (players == null) {
			return Collections.EMPTY_LIST;
		}
		List<PlayerInfo> result = new ArrayList<PlayerInfo>();
		for (PlayerInfo playerInfo : players) {
			if (playerInfo.getUserLocation() == null || playerInfo.getUserLocation() == curUserLocation) {
				result.add(playerInfo);
			} else {
				playerInfo = playerInfo._getOtherPlayerInfoView();
				result.add(playerInfo);
			}
		}
		return result;
	}

	// 正常获取所有玩家的真实信息
	public List<PlayerInfo> _getPlayers() {
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
