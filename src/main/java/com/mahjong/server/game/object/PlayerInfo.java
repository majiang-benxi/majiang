package com.mahjong.server.game.object;

import java.util.ArrayList;
import java.util.List;

/**
 * 麻将桌上一个玩家的信息，包括玩家对象、牌，以及其他信息。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class PlayerInfo extends PlayerTiles implements Cloneable {
	/**
	 * 玩家。
	 */
	private Player player = null;
	/**
	 * 最后摸的牌。
	 */
	private Tile lastDrawedTile = null;
	/**
	 * 已经打出的牌。
	 */
	private Tile discardedTiles = null;
	/**
	 * 是否听和。
	 */
	private boolean isTing = false;
	/**
	 * 其他玩家能看到的本玩家的视图
	 */
	private PlayerView otherPlayerView;
	private int score;// 分数
	// TODO add用户信息

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Tile getLastDrawedTile() {
		return lastDrawedTile;
	}

	public void setLastDrawedTile(Tile lastDrawedTile) {
		this.lastDrawedTile = lastDrawedTile;
	}


	public Tile getDiscardedTiles() {
		return discardedTiles;
	}

	public void setDiscardedTiles(Tile discardedTiles) {
		this.discardedTiles = discardedTiles;
	}

	public void setOtherPlayerView(PlayerView otherPlayerView) {
		this.otherPlayerView = otherPlayerView;
	}

	public boolean isTing() {
		return isTing;
	}

	public void setTing(boolean isTing) {
		this.isTing = isTing;
	}

	/**
	 * 清空玩家的牌，回到初始状态。
	 */
	public void clear() {
		aliveTiles.setPai(null);
		lastDrawedTile = null;
		discardedTiles.setPai(null);
		tileGroups.clear();
		isTing = false;
	}

	public PlayerInfo clone() {
		PlayerInfo c;
		try {
			c = (PlayerInfo) super.clone();
			c.aliveTiles = (Tile) super.clone();
			c.discardedTiles = (Tile) super.clone();
		} catch (CloneNotSupportedException e) {
			// 不可能，因为PlayerInfo,Tile已经实现了Cloneable
			throw new RuntimeException(e);
		}
		// deep copy

		c.tileGroups = new ArrayList<TileGroupType>(tileGroups);
		return c;
	}


	/**
	 * 获取其他玩家的视图。
	 */
	public PlayerView getOtherPlayerView() {
		if (otherPlayerView == null) { // 不需要加锁，因为多创建了也没事
			otherPlayerView = new PlayerView();
		}
		return otherPlayerView;
	}

	/**
	 * 一个位置的玩家的视图。需要限制一些权限。
	 * 
	 * @author blovemaple <blovemaple2010(at)gmail.com>
	 */
	public class PlayerView {

		/**
		 * 返回玩家名称。
		 */
		public String getPlayerName() {
			Player player = getPlayer();
			return player != null ? getPlayer().getName() : null;
		}

		/**
		 * 返回手中的牌数。
		 */
		public int getAliveTileSize() {
			return getAliveTiles().getPai().length;
		}

		public Tile getDiscardedTiles() {
			return discardedTiles;
		}

		public List<TileGroupType> getTileGroupType() {
			return tileGroups; // FIXME 会看到暗杠
		}

		public boolean isTing() {
			return isTing;
		}

	}

}
