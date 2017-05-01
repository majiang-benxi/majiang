package com.mahjong.server.game.object;

import com.mahjong.server.game.context.GameContext;

public class WinInfo extends PlayerInfo {
	private Tile winTile;
	private boolean ziMo;
	private boolean firstTileCheck;// 发完牌之后的第一次检测
	private Tile huiTile;
	private GameContext.PlayerView contextView;


	// 检查WinType和FanType的时候填入的结果，用于：
	// (1)检查FanType时利用WinType的parse结果
	// (2)检查前先看是否已经有结果，避免重复检查

	public static WinInfo fromPlayerTiles(PlayerTiles playerTiles, Tile winTile,Tile huiTile, Boolean ziMo) {
		WinInfo winInfo = new WinInfo();
		winInfo.setAliveTiles(playerTiles.getAliveTiles());
		winInfo.setTileGroups(playerTiles.getTileGroups());
		winInfo.setWinTile(winTile);
		winInfo.setHuiTile(huiTile);
		winInfo.setZiMo(ziMo);
		return winInfo;
	}

	public Tile getWinTile() {
		return winTile;
	}

	public void setWinTile(Tile winTile) {
		this.winTile = winTile;
	}


	public void setContextView(GameContext.PlayerView contextView) {
		this.contextView = contextView;
	}

	public GameContext.PlayerView getContextView() {
		return contextView;
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

}