package com.mahjong.server.game.object;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个玩家的牌。
 * @author warter
 */
public class PlayerTiles  {
	/**
	 * 手中的牌。
	 */
	protected Tile aliveTiles = new Tile();
	/**
	 * 吃碰杠。
	 */
	protected List<TileGroup> tileGroups = new ArrayList<TileGroup>();

	public PlayerTiles() {
		super();
	}

	public Tile getAliveTiles() {
		return aliveTiles;
	}

	public void setAliveTiles(Tile aliveTiles) {
		this.aliveTiles = aliveTiles;
	}

	public List<TileGroup> getTileGroups() {
		return tileGroups;
	}

	public void setTileGroups(List<TileGroup> tileGroups) {
		this.tileGroups = tileGroups;
	}
}