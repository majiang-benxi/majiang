package com.mahjong.server.game.rule.win;

import com.mahjong.server.game.object.HuType;
import com.mahjong.server.game.object.WinInfo;
import com.mahjong.server.game.rule.RuleInfo;

public enum StandardHuType implements HuType {

	XIAO_HU {

		@Override
		public boolean canHU(WinInfo winInfo, RuleInfo ruleInfo) {
			return getBaseWinType().canWin(winInfo, ruleInfo);

		}

		@Override
		public int getZhuangScore(WinInfo winInfo, RuleInfo ruleInfo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getXianScore(WinInfo winInfo, RuleInfo ruleInfo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getDianPaoScore(WinInfo winInfo, RuleInfo ruleInfo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public WinType getBaseWinType() {
			return new XiaoHuWinType();
		}


	},
	ZHA_QIANG_HU {

		@Override
		public boolean canHU(WinInfo winInfo, RuleInfo ruleInfo) {
			return getBaseWinType().canWin(winInfo, ruleInfo);
		}

		@Override
		public int getZhuangScore(WinInfo winInfo, RuleInfo ruleInfo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getXianScore(WinInfo winInfo, RuleInfo ruleInfo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getDianPaoScore(WinInfo winInfo, RuleInfo ruleInfo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public WinType getBaseWinType() {
			// TODO Auto-generated method stub
			return new ZhaQiangHuWinType();
		}

	},
	QIDUI_HU {

		@Override
		public boolean canHU(WinInfo winInfo, RuleInfo ruleInfo) {
			return getBaseWinType().canWin(winInfo, ruleInfo);
		}

		@Override
		public int getZhuangScore(WinInfo winInfo, RuleInfo ruleInfo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getXianScore(WinInfo winInfo, RuleInfo ruleInfo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getDianPaoScore(WinInfo winInfo, RuleInfo ruleInfo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public WinType getBaseWinType() {
			return new QiDuiWinType();
		}

	},
	QING_Yi_SE_HU {

		@Override
		public boolean canHU(WinInfo winInfo, RuleInfo ruleInfo) {
			return getBaseWinType().canWin(winInfo, ruleInfo);
		}

		@Override
		public int getZhuangScore(WinInfo winInfo, RuleInfo ruleInfo) {
			winInfo.getSpecialPaiScore();
			return 0;
		}

		@Override
		public int getXianScore(WinInfo winInfo, RuleInfo ruleInfo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getDianPaoScore(WinInfo winInfo, RuleInfo ruleInfo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public WinType getBaseWinType() {
			return new QingYiSeWinType();
		}

	},
	QI_YI_SE_QI_DUI_HU {

		@Override
		public boolean canHU(WinInfo winInfo, RuleInfo ruleInfo) {
			return getBaseWinType().canWin(winInfo, ruleInfo);
		}

		@Override
		public int getZhuangScore(WinInfo winInfo, RuleInfo ruleInfo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getXianScore(WinInfo winInfo, RuleInfo ruleInfo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getDianPaoScore(WinInfo winInfo, RuleInfo ruleInfo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public WinType getBaseWinType() {
			return new QingYiSeQiDuiWinType();
		}

	},
	NORMAL_HU {

		@Override
		public boolean canHU(WinInfo winInfo, RuleInfo ruleInfo) {
			return getBaseWinType().canWin(winInfo, ruleInfo);
		}

		@Override
		public WinType getBaseWinType() {
			return new NormalWinType();
		}

		@Override
		public int getZhuangScore(WinInfo winInfo, RuleInfo ruleInfo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getXianScore(WinInfo winInfo, RuleInfo ruleInfo) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getDianPaoScore(WinInfo winInfo, RuleInfo ruleInfo) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	;

}
