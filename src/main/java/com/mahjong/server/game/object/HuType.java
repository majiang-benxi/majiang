package com.mahjong.server.game.object;

import com.mahjong.server.game.rule.RuleInfo;
import com.mahjong.server.game.rule.win.WinType;

public interface HuType {
	/**
	 * 
	 * @param winTile判断胡牌的时候所有的牌
	 * @return
	 */
	public boolean canHU(WinInfo winInfo, RuleInfo ruleInfo);

	public WinType getBaseWinType();

	/**
	 * 庄家得分
	 * 
	 * @return
	 */
	public int getZhuangScore(WinInfo winInfo, RuleInfo ruleInfo);

	/**
	 * 闲家得分
	 * 
	 * @return
	 */
	public int getXianScore(WinInfo winInfo, RuleInfo ruleInfo);

	/**
	 * 点炮的得分
	 * 
	 * @return
	 */
	public int getDianPaoScore(WinInfo winInfo, RuleInfo ruleInfo);

}
