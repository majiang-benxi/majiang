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
	 * 补杠
	 */
	BUGANG_GROUP(GANGZI),
	/**
	 * 暗杠
	 */
	ANGANG_GROUP(GANGZI),
	/**
	 * 补花
	 */
	BUHUA_GROUP(HUA_UNIT);

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