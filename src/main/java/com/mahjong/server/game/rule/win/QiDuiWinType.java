package com.mahjong.server.game.rule.win;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.mahjong.server.game.object.WinInfo;
import com.mahjong.server.game.rule.PlayRule;
import com.mahjong.server.game.rule.RuleInfo;

public class QiDuiWinType extends NormalWinType {

	@Override
	public boolean canWin(WinInfo winInfo, RuleInfo ruleInfo) {
		if(!ruleInfo.getPlayRules().contains(PlayRule.QI_XIAO_DUI)){
			return false;
		}
		int duiZi = 0;
		// tempMap存放牌值的张数<牌值,张数>
		Map<Byte, Integer> tempMap = new HashMap<Byte, Integer>();
		for (byte value : winInfo.getAliveTile().getPai()) {
			if (tempMap.containsKey(value)) {
				tempMap.put(value, tempMap.get(value) + 1);
			} else {
				tempMap.put(value, 1);
			}
		}
		// 将可能成为将牌的牌值存放到jiangPAI中
		for (Entry<Byte, Integer> e : tempMap.entrySet()) {
			if (e.getValue() == 2) {
				duiZi++;
			}
		}
		if (duiZi == 7) {
			return true;
		} else {
			int aloneAliveTileSize = winInfo.getAliveTile().getPai().length - duiZi * 2;
			int huiNum = winInfo.getHuiTile().getPai().length;
			if (huiNum > 0 && aloneAliveTileSize == huiNum&&winInfo.getTileGroups().isEmpty()) {
				return true;
			} else {
				return false;
			}
		}
	}

}
