package com.mahjong.server.game.rule.win;

import static com.mahjong.server.game.rule.PlayRule.XIAO;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.mahjong.server.game.object.WinInfo;
import com.mahjong.server.game.rule.RuleInfo;

/**
 * 选了削的四张一样的就算胡牌
 * 
 * @author wumiao
 *
 */
public class XiaoHuWinType extends NormalWinType {

	@Override
	public boolean canWin(WinInfo winInfo, RuleInfo ruleInfo) {
		if (ruleInfo.getPlayRules().contains(XIAO)) {
			Map<Byte, Integer> tempMap = new HashMap<Byte, Integer>();
			for (byte value : winInfo.getAliveTile().getPai()) {
				if (tempMap.containsKey(value)) {
					tempMap.put(value, tempMap.get(value) + 1);
				} else {
					tempMap.put(value, 1);
				}
			}
			for (Entry<Byte, Integer> e : tempMap.entrySet()) {
				if (e.getValue() == 4 || e.getValue() == 3 && e.getKey() == winInfo.getFanhui()) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	@Override
	public boolean isPiaoHU(WinInfo winInfo) {
		return false;
	}

	@Override
	public boolean maybeQiongHu(WinInfo winInfo) {
		return false;
	}

}
