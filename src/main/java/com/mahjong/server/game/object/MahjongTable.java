package com.mahjong.server.game.object;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.mahjong.server.game.action.PlayerLocation;

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
	private final Map<PlayerLocation, PlayerView> playerViews = new HashMap<PlayerLocation, PlayerView>();

	public void init() {
		tileWall = new Tile();
		playerInfos = new EnumMap<PlayerLocation, PlayerInfo>(PlayerLocation.class);
		for (PlayerLocation location : PlayerLocation.values()) {
			playerInfos.put(location, new PlayerInfo());
		}
	}

	/**
	 * 初始化，准备开始一局。即清空玩家的牌、洗牌
	 */
	public void readyForGame(Collection<Tile> allTiles) {
		for (PlayerInfo playerInfo : playerInfos.values()) {
			playerInfo.clear();
		}
		tileWall.setPai(Tile.getOneBoxMahjong());
		Collections.shuffle(Arrays.asList(tileWall.getPai()));
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

	public Player getPlayerByLocation(PlayerLocation location) {
		PlayerInfo info = playerInfos.get(location);
		return info == null ? null : info.getPlayer();
	}

	public void setPlayer(PlayerLocation location, Player player) {
		PlayerInfo playerInfo = playerInfos.get(location);
		if (playerInfo == null) {
			playerInfo = new PlayerInfo();
			playerInfos.put(location, playerInfo);
		}
		playerInfo.setPlayer(player);
	}


	/**
	 * 获取指定位置的玩家视图。
	 */
	public PlayerView getPlayerView(PlayerLocation location) {
		PlayerView view = playerViews.get(location);
		if (view == null) { // 不需要加锁，因为多创建了也没事
			view = new PlayerView(location);
			playerViews.put(location, view);
		}
		return view;
	}

	/**
	 * 一个位置的玩家的视图
	 * 
	 */
	public class PlayerView {

		private final PlayerLocation myLocation;

		private PlayerView(PlayerLocation myLocation) {
			this.myLocation = myLocation;
		}

		/**
		 * 返回此视图的玩家位置。
		 */
		public PlayerLocation getMyLocation() {
			return myLocation;
		}

		/**
		 * 返回指定位置的玩家名称。
		 */
		public String getPlayerName(PlayerLocation location) {
			Player player = getPlayerByLocation(location);
			return player != null ? player.getName() : null;
		}

		/**
		 * 返回牌墙中的剩余牌数。
		 */
		public int getTileWallSize() {
			return MahjongTable.this.getTileWallSize();
		}



		/**
		 * 返回所有玩家已经打出的牌。
		 */
		public Map<PlayerLocation, PlayerInfo.PlayerView> getPlayerInfoView() {
			Map<PlayerLocation, PlayerInfo.PlayerView>result=new HashMap<PlayerLocation, PlayerInfo.PlayerView>();
			for (Entry<PlayerLocation, PlayerInfo> entry : playerInfos.entrySet()) {
				result.put(entry.getKey(), entry.getValue().getOtherPlayerView());
			}
			return result;
		}

	}

}
