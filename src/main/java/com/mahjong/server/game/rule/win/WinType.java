package com.mahjong.server.game.rule.win;

import java.util.List;

import com.mahjong.server.game.object.HuType;
import com.mahjong.server.game.object.TileUnitType;
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
	 * 最后胡牌的类型
	 * 
	 * @return
	 */
	public HuType getHuType();
	/**
	 * 返回会影响计分的TileUnitType
	 * 
	 * @return
	 */

	public List<TileUnitType> getWinTileUnitType();

	public boolean isPiaoHU();


}
