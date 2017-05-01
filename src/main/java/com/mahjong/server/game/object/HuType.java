package com.mahjong.server.game.object;

import java.util.List;

public interface HuType {
	/**
	 * 
	 * @param winTile判断胡牌的时候所有的牌
	 * @param jiang
	 *            当前的将牌
	 * @param tileUnitInfos
	 *            当前将牌为前提下拆分的TileUnitInfo组合信息。比如顺子，杠及对应牌的组合
	 * @return
	 */
	public boolean canHU(WinInfo winInfo, Byte jiang, List<TileUnitInfo> tileUnitInfos);

	/**
	 * 庄家得分
	 * 
	 * @return
	 */
	public int getZhuangScore(boolean zimo);

	/**
	 * 闲家得分
	 * 
	 * @return
	 */
	public int getXianScore();

	/**
	 * 点炮的得分
	 * 
	 * @return
	 */
	public int getDianPaoScore();

}
