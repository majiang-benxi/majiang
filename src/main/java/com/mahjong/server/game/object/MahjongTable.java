package com.mahjong.server.game.object;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import com.mahjong.server.game.enums.PlayerLocation;

/**
 * 麻将桌。
 * 
 */
public class MahjongTable {
	/**
	 * 牌墙。从0处摸牌。
	 */
	private Tile tileWall;

	/**
	 * 所有玩家信息。
	 */
	private Map<PlayerLocation, PlayerInfo> playerInfos;
	private byte fanhui;
	
	public void init() {
		tileWall = new Tile(Tile.getOneBoxMahjong());
		playerInfos = new EnumMap<PlayerLocation, PlayerInfo>(PlayerLocation.class);
		for (PlayerLocation location : PlayerLocation.values()) {
			playerInfos.put(location, new PlayerInfo());
		}
	}

	/**
	 * 初始化，准备开始一局。即清空玩家的牌、洗牌
	 */
	public void readyForGame() {
		for (PlayerInfo playerInfo : playerInfos.values()) {
			playerInfo.clear();
		}
		tileWall.setPai(Tile.getOneBoxMahjong());
		Collections.shuffle(Arrays.asList(tileWall.getPai()));
	}

	/**
	 * 从牌墙的头部摸指定数量的牌并返回。
	 */
	public Tile draw(int count) {
		if (count <= 0 || count > tileWall.getPai().length) {
			return null;
		}
		return tileWall.dealTile(count);
	}

	/**
	 * 从牌墙的底部摸指定数量的牌并返回。
	 */
	public Tile drawBottom(int count) {
		if (count <= 0 || count > tileWall.getPai().length) {
			return null;
		}
		return tileWall.dealBottomTile(count);
	}

	/**
	 * 返回牌中的剩余牌数。
	 */
	public int getTileWallSize() {
		return tileWall.getPai().length;
	}



	public Map<PlayerLocation, PlayerInfo> getPlayerInfos() {
		return playerInfos;
	}

	protected void setPlayerInfos(Map<PlayerLocation, PlayerInfo> playerInfos) {
		this.playerInfos = playerInfos;
	}

	public PlayerInfo getPlayerByLocation(PlayerLocation location) {
		PlayerInfo info = playerInfos.get(location);
		return info == null ? null : info;
	}

	public void setPlayer(PlayerLocation location, PlayerInfo player) {
		playerInfos.put(location, player);
	}

	public Tile getTileWall() {
		return tileWall;
	}

	public void setTileWall(Tile tileWall) {
		this.tileWall = tileWall;
	}

	public byte getFanhui() {
		return fanhui;
	}

	public void setFanhui(byte fanhui) {
		this.fanhui = fanhui;
	}

	public void printAllPlayTiles() {
		try {
		for (Entry<PlayerLocation, PlayerInfo> entry : playerInfos.entrySet()) {
				System.out.print(
					"方位:" + entry.getKey().getCode() + " aliveTile: ");
				entry.getValue().getSortAliveTiles().printTile();
		}
			System.out.print("会牌:");
			Tile.getHuiPai(fanhui).printTile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
