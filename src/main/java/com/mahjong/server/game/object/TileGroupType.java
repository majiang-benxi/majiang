package com.mahjong.server.game.object;
import static com.mahjong.server.game.object.StandardTileUnitType.GANGZI;
import static com.mahjong.server.game.object.StandardTileUnitType.HUA_UNIT;
import static com.mahjong.server.game.object.StandardTileUnitType.KEZI;
import static com.mahjong.server.game.object.StandardTileUnitType.SHUNZI;

/**
 * 牌组类型。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
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
	 * 直杠
	 */
	ZHIGANG_GROUP(GANGZI),
	/**
	 * 补杠【TODO 跟直杠玩法一样。删掉一个】
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
	BUHUA_GROUP(HUA_UNIT);
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