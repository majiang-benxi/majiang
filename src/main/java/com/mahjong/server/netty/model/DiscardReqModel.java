package com.mahjong.server.netty.model;

import com.mahjong.server.game.object.Tile;

/**
 * 
 * @author wumiao 出牌
 */
public class DiscardReqModel {
	private String weixinId;
	private Tile tile;
	private int tileGroupType;// 打牌的类型。单张，吃碰杠

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

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
