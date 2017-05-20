package com.mahjong.server.game.rule.win;

import com.mahjong.server.game.object.WinInfo;
import com.mahjong.server.game.rule.RuleInfo;

/**
 * 和牌类型。
 * 
 */
public interface WinType {

	/**
	 * 判断根据此和牌类型是否可以和牌
	 * 
	 * @param winInfo
	 *            和牌信息
	 * @return 是否可以和牌
	 */
	public boolean canWin(WinInfo winInfo, RuleInfo ruleInfo);

	/**
	 * 是否是飘胡。可以构成清一色票，七对飘等玩法。
	 * 
	 * @return
	 */

	public boolean isPiaoHU(WinInfo winInfo);

	/**
	 * 判断是否可能成为穷胡（无会无枪），注意调用此方法不一定代表可以胡牌，一般是胡牌之后在调用此方法判断此种胡法是否也是穷胡
	 * 
	 */
	public boolean maybeQiongHu(WinInfo winInfo);

}
