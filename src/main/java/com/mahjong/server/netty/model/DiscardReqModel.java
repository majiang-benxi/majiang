package com.mahjong.server.netty.model;

import com.mahjong.server.game.object.Tile;

/**
 * 
 * @author wumiao 出牌
 */
public class DiscardReqModel  extends RequestBaseMode{
	private Tile tile;
	private int tileGroupType;// 打牌的类型。单张，吃碰杠

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public int getTileGroupType() {
		return tileGroupType;
	}

	public void setTileGroupType(int tileGroupType) {
		this.tileGroupType = tileGroupType;
	}

}
