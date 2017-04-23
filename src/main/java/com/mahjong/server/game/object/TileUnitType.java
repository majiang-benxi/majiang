package com.mahjong.server.game.object;

/**
 * 牌的单元的类型。
 * 
 */
public interface TileUnitType {

	/**
	 * 返回一个单元中有几张牌。
	 */
	int size();

	/**
	 * 判断指定牌集合是否是合法的单元。
	 */
	boolean isLegalTile(Tile tile);
}