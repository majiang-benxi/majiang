package com.mahjong.server.game.object;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个玩家的牌。
 */
public class PlayerTiles {
	/**
	 * 手中的牌。
	 */
	protected Tile aliveTiles = new Tile();
	/**
	 * 吃碰杠。
	 */
	protected List<TileGroup> tileGroups = new ArrayList<TileGroup>();
	/**
	 * 可以打出的牌的组合
	 */
	protected List<TileGroup> canPassTileGroups = new ArrayList<TileGroup>();

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

	public List<TileGroup> getCanPassTileGroups() {
		return canPassTileGroups;
	}

	public void setCanPassTileGroups(List<TileGroup> canPassTileGroups) {
		this.canPassTileGroups = canPassTileGroups;
	}

}