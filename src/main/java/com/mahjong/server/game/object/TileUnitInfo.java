package com.mahjong.server.game.object;

public class TileUnitInfo {
	private TileUnitType tileUnitType;
	private Tile tile;

	public TileUnitInfo() {

	}
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

	public TileUnitInfo clone() {
		TileUnitInfo tileUnitInfo = new TileUnitInfo();
		tileUnitInfo.tile = tile.clone();
		tileUnitInfo.tileUnitType = tileUnitType;// 枚举类型浅拷贝
		return tileUnitInfo;
	}
}
