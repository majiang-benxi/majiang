package com.mahjong.server.game.object;

import java.util.ArrayList;

import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.enums.PlayerLocation;

/**
 * 麻将桌上一个玩家的信息，包括玩家对象、牌，以及其他信息。
 * @author warter
 */
public class PlayerInfo extends PlayerTiles implements Cloneable {
	/**
	 * 玩家。
	 */
	private UserInfo userInfo;
	
	private PlayerLocation userLocation;

	/**
	 * 最后摸的牌。
	 */
	private Tile lastDrawedTile = null;
	/**
	 * 已经打出的牌。
	 */
	private Tile discardedTiles = null;
	/**
	 * 是否胡牌。
	 */
	private boolean isHu = false;
	
	private int curScore;// 分数

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
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

	public boolean isTing() {
		return isHu;
	}

	public void setTing(boolean isHu) {
		this.isHu = isHu;
	}

	/**
	 * 清空玩家的牌，回到初始状态。
	 */
	public void clear() {
		aliveTiles.setPai(null);
		lastDrawedTile = null;
		discardedTiles.setPai(null);
		tileGroups.clear();
		isHu = false;
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

		c.tileGroups = new ArrayList<TileGroup>(tileGroups);
		return c;
	}

	public int getCurScore() {
		return curScore;
	}

	public void setCurScore(int curScore) {
		this.curScore = curScore;
	}

	public int getUserLocation() {
		return userLocation.getCode();
	}

	public void setUserLocation(int userLocation) {
		this.userLocation = PlayerLocation.fromCode(userLocation);
	}
	
}
