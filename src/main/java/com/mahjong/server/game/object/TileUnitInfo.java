package com.mahjong.server.game.object;

public class TileUnitInfo {
	private TileUnitType tileUnitType;
	private Tile tile;

	public TileUnitInfo(TileUnitType tileUnitType, Tile tile) {
		super();
		this.tileUnitType = tileUnitType;
		this.tile = tile;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public TileUnitType getTileUnitType() {
		return tileUnitType;
	}

	public void setTileUnitType(TileUnitType tileUnitType) {
		this.tileUnitType = tileUnitType;
	}

}
