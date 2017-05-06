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

	// 检查WinType和FanType的时候填入的结果，用于：
	// (1)检查FanType时利用WinType的parse结果
	// (2)检查前先看是否已经有结果，避免重复检查

	public static WinInfo fromPlayerTiles(PlayerTiles playerTiles, Tile winTile,byte fanhui, Boolean ziMo) {
		WinInfo winInfo = new WinInfo();
		winInfo.setFanhui(fanhui);
		winInfo.setDropTileGroups(playerTiles.getTileGroups());
		Tile allWinTile = playerTiles.getAliveTiles().clone();
		for (TileGroup tileGroup : playerTiles.getTileGroups()) {
			allWinTile = Tile.addTile(allWinTile, tileGroup.getTile());
		}
		winInfo.setWinTile(allWinTile);
		winInfo.setHuiTile(Tile.getHuiPai(fanhui));
		playerTiles.getAliveTiles().removeAll(winInfo.getHuiTile());// 把会牌移走
		winInfo.setAliveTiles(playerTiles.getAliveTiles());
		winInfo.setTileGroups(playerTiles.getTileGroups());
		winInfo.setZiMo(ziMo);
		return winInfo;
	}

	public boolean isFirstTileCheck() {
		return firstTileCheck;
	}

	public void setFirstTileCheck(boolean firstTileCheck) {
		this.firstTileCheck = firstTileCheck;
	}

	public void setZiMo(boolean ziMo) {
		this.ziMo = ziMo;
	}

	public boolean isZiMo() {
		return ziMo;
	}

	public Tile getHuiTile() {
		return huiTile;
	}

	public void setHuiTile(Tile huiTile) {
		this.huiTile = huiTile;
	}

	public Tile getAliveTile() {
		return aliveTile;
	}

	public void setAliveTile(Tile aliveTile) {
		this.aliveTile = aliveTile;
	}

	public List<TileGroup> getDropTileGroups() {
		return dropTileGroups;
	}

	public void setDropTileGroups(List<TileGroup> dropTileGroups) {
		this.dropTileGroups = dropTileGroups;
	}

	public byte getFanhui() {
		return fanhui;
	}

	public void setFanhui(byte fanhui) {
		this.fanhui = fanhui;
	}

	public Tile getWinTile() {
		return winTile;
	}

	public void setWinTile(Tile winTile) {
		this.winTile = winTile;
	}

}