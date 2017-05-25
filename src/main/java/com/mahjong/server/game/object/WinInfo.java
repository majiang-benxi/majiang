package com.mahjong.server.game.object;

import java.util.List;

public class WinInfo extends PlayerInfo {
	private Tile winTile;// 用来存储玩家所有的牌，包括活牌和打过的牌，以及手上的会牌
	private Tile aliveTile;// 用来存储玩家所有活牌
	private List<TileGroup> dropTileGroups;// 用来存储玩家吃碰杠的牌
	private boolean ziMo;
	private boolean firstTileCheck;// 发完牌之后的第一次检测
	private Tile huiTile;// 存储所有的会牌
	private byte fanhui;// 翻出来的这个会牌
	private HuType huType;

	/**
	 * 
	 * @param playerTiles
	 * @param fanhui
	 * @param ziMo
	 * @param isZhuang
	 * @return 构建完毕之后不要更改这个里面的任何字段的信息
	 */
	public static final WinInfo fromPlayerTiles(PlayerTiles playerTiles, byte fanhui, Boolean ziMo) {
		WinInfo winInfo = new WinInfo();
		winInfo.fanhui = fanhui;
		winInfo.dropTileGroups = playerTiles.getTileGroups();
		Tile allWinTile = playerTiles.getAliveTiles().clone();
		for (TileGroup tileGroup : playerTiles.getTileGroups()) {
			allWinTile = Tile.addTile(allWinTile, tileGroup.getTile());
		}
		winInfo.winTile = allWinTile;
		winInfo.huiTile = Tile.getOwnHuiPai(allWinTile, fanhui);
		playerTiles.getAliveTiles().removeAll(winInfo.getHuiTile());// 把会牌移走
		winInfo.aliveTile = playerTiles.getAliveTiles();
		winInfo.tileGroups = playerTiles.getTileGroups();
		winInfo.ziMo = ziMo;
		return winInfo;
	}

	public boolean isFirstTileCheck() {
		return firstTileCheck;
	}

	public boolean isZiMo() {
		return ziMo;
	}

	public Tile getHuiTile() {
		return huiTile;
	}

	public Tile getAliveTile() {
		return aliveTile;
	}


	public List<TileGroup> getDropTileGroups() {
		return dropTileGroups;
	}

	public byte getFanhui() {
		return fanhui;
	}

	public Tile getWinTile() {
		return winTile;
	}

	public HuType getHuType() {
		return huType;
	}

	public void setHuType(HuType huType) {
		this.huType = huType;
	}

}