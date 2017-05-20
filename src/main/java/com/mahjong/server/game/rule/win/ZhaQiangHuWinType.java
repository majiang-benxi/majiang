package com.mahjong.server.game.rule.win;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.WinInfo;
import com.mahjong.server.game.rule.RuleInfo;

public class ZhaQiangHuWinType extends NormalWinType {

	@Override
	public boolean canWin(WinInfo winInfo, RuleInfo ruleInfo) {
		Map<Byte, Integer> tempMap = new HashMap<Byte, Integer>();
		for (byte value : winInfo.getAliveTile().getPai()) {
			if (tempMap.containsKey(value)) {
				tempMap.put(value, tempMap.get(value) + 1);
			} else {
				tempMap.put(value, 1);
			}
		}
		for (Entry<Byte, Integer> e : tempMap.entrySet()) {
			if (e.getValue() == 4 && e.getKey() == Tile.QIANG) {
				return true;
			}
		}
		return false;

	}

}
