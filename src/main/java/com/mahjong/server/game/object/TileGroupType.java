package com.mahjong.server.game.object;
import static com.mahjong.server.game.object.StandardTileUnitType.GANGZI;
import static com.mahjong.server.game.object.StandardTileUnitType.KEZI;
import static com.mahjong.server.game.object.StandardTileUnitType.SHUNZI;
import static com.mahjong.server.game.object.StandardTileUnitType.ZI_PAI;;

/**
 * 牌组类型。
 * 
 */
public enum TileGroupType {
	/**
	 * 一张牌
	 */
	ONE_GROUP(null),

	/**
	 * 吃
	 */
	CHI_GROUP(SHUNZI),
	/**
	 * 碰
	 */
	PENG_GROUP(KEZI),
	/**
	 * 补杠
	 */
	BUGANG_GROUP(GANGZI),
	/**
	 * 暗杠
	 */
	ANGANG_GROUP(GANGZI),
	/**
	 * 削
	 */
	XIAO(GANGZI),

	/**
	 * 补花
	 */
	ZIPAI_GROUP(ZI_PAI);
	/**
	 * 胡牌
	 */

	private final TileUnitType unitType;

	private TileGroupType(TileUnitType unitType) {
		this.unitType = unitType;
	}

	public TileUnitType getUnitType() {
		return unitType;
	}

	/**
	 * 返回一个单元中有几张牌。
	 */
	public int size() {
		return unitType.size();
	}

	/**
	 * 判断指定牌集合是否是合法的牌组。
	 */
	public boolean isLegalTile(Tile tile) {
		return unitType.isLegalTile(tile);
	}

}