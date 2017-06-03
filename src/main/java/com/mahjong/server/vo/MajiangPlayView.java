package com.mahjong.server.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.common.primitives.Bytes;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.Tile;

public class MajiangPlayView {
	private String ruleStrategy;
	private int fangKaStrategy;
	private int roomId;
	private List<PlayerInfo> players;
	private int curUserLocation;
	private int hui1;
	private int hui2;
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
			if (playerInfo.getUserLocation() != null && playerInfo.getUserLocation() == curUserLocation) {
				reSortIfContainsHuiPai(playerInfo);
				result.add(playerInfo);

			} else {
				playerInfo = playerInfo._getOtherPlayerInfoView();
				result.add(playerInfo);
			}
		}
		return result;
	}

	// 前端展示的时候会牌在最前边。
	private void reSortIfContainsHuiPai(PlayerInfo playerInfo) {
		Tile aliveTile = playerInfo.getAliveTiles();
		if (aliveTile != null) {
			List<Byte> huiPai = new ArrayList<Byte>();
			List<Byte> otherPai = new ArrayList<Byte>();
			for (int i = 0; i < aliveTile.getPai().length; i++) {
				if (aliveTile.getPai()[i] == hui1 || aliveTile.getPai()[i] == hui2) {
					huiPai.add(aliveTile.getPai()[i]);
				} else {
					otherPai.add(aliveTile.getPai()[i]);
				}
			}
			Collections.sort(otherPai);
			huiPai.addAll(otherPai);
			aliveTile.setPai(Bytes.toArray(huiPai));
		}

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

	public int getHui1() {
		return hui1;
	}

	public void setHui1(int hui1) {
		this.hui1 = hui1;
	}

	public int getHui2() {
		return hui2;
	}

	public void setHui2(int hui2) {
		this.hui2 = hui2;
	}

	public static void main(String[] args) {
		MajiangPlayView majiangPlayView = new MajiangPlayView();
		List<PlayerInfo> players = new ArrayList<PlayerInfo>();
		PlayerInfo playerInfo1 = new PlayerInfo();
		playerInfo1.setAliveTiles(new Tile(new byte[] { 1, 2, 3, 5, 7 }));
		playerInfo1.setUserLocation(1);
		players.add(playerInfo1);
		PlayerInfo playerInfo2 = new PlayerInfo();
		playerInfo2.setAliveTiles(new Tile(new byte[] { 1, 2, 3, 5, 7, 8 }));
		playerInfo2.setUserLocation(2);
		players.add(playerInfo2);
		majiangPlayView.setPlayers(players);
		byte hui1 = 5;
		byte hui2 = 6;
		majiangPlayView.setHui1(hui1);
		majiangPlayView.setHui2(hui2);
		majiangPlayView.setCurUserLocation(2);
		System.out.println(JSON.toJSONString(majiangPlayView));
	}
}
