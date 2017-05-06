package com.mahjong.server.game.object;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.mahjong.server.game.rule.RuleInfo;

public enum StandardHuType implements HuType {

	QING_Yi_SE_NORMAL {

	@Override
		public boolean canHU(WinInfo winInfo, RuleInfo ruleInfo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
		public int getZhuangScore(boolean zimo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getXianScore() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getDianPaoScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	},
	XIAO_HU {

		@Override
		public boolean canHU(WinInfo winInfo, RuleInfo ruleInfo) {
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
				if (e.getValue() == 4 || e.getValue() == 3 && e.getKey() == winInfo.getFanhui()) {
					return true;
				}
			}
			return false;
		}

		@Override
		public int getZhuangScore(boolean zimo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getXianScore() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getDianPaoScore() {
			// TODO Auto-generated method stub
			return 0;
		}

};

}
