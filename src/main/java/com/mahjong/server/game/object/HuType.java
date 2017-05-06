package com.mahjong.server.game.object;

import com.mahjong.server.game.rule.RuleInfo;

public interface HuType {
	/**
	 * 
	 * @param winTile判断胡牌的时候所有的牌
	 * @return
	 */
	public boolean canHU(WinInfo winInfo, RuleInfo ruleInfo);

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
