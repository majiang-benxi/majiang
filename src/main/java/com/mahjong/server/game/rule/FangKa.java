package com.mahjong.server.game.rule;

public enum FangKa {
	ONE_SIXTEEN(1, "一张房卡16局"), TWO_THIRTY_TWO(2, "2张房卡32局");
	private int size;
	private String desc;

	FangKa(int size, String desc) {
		this.size = size;
		this.desc = desc;
	}
}
