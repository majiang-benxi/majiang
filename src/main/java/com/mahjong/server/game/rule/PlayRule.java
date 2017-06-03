package com.mahjong.server.game.rule;

public enum PlayRule {
	PAO_PAY_THREE(1, "点炮包三家"), QIONGHU(2, "穷胡"), XUAN_FENG_GANG(3, "旋风杠"), QING_YI_SE(4, "清一色"),PIAO_HU(5,"飘胡"), XIAO(6, "削"), QI_XIAO_DUI(7,"七小对"),GANG(8,
			"杠"),CHUAN_TONG(9,"传统玩法");
	PlayRule(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int id;
	public String name;

	public static PlayRule findPlayRuleById(int id) {
		for (PlayRule playRule : PlayRule.values()) {
			if(playRule.id==id){
				return playRule;
			}
		}
		return null;
	}
}
