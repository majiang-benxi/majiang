package com.mahjong.server.game.object;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个玩家的牌。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class PlayerTiles {
	/**
	 * 手中的牌。
	 */
	protected Tile aliveTiles = new Tile();
	/**
	 * 吃碰杠。
	 */
	protected List<TileGroupType> tileGroups = new ArrayList<TileGroupType>();

	public PlayerTiles() {
		super();
	}

	public Tile getAliveTiles() {
		return aliveTiles;
	}

	public void setAliveTiles(Tile aliveTiles) {
		this.aliveTiles = aliveTiles;
	}

	public List<TileGroupType> getTileGroups() {
		return tileGroups;
	}

	public void setTileGroups(List<TileGroupType> tileGroups) {
		this.tileGroups = tileGroups;
	}

}