package com.mahjong.server.game.object;

public enum GetScoreType {
	
	nor_hu(1,"普通胡"),
	qingyise(2,"清一色"),
	qidui(3,"七对"),
	qingyise_qidui(4,"清一色七对"),
	xiaohu(5,"削胡"),
	zhaqiang(6,"炸枪胡"),
	piaohu(7,"飘胡"),
	qionghu(8,"穷胡"),
	huipai(9,"会牌"),
	qiangpai(10,"枪牌"),
	bugang(11,"补杠"),
	angang(12,"暗杠"),
	xuanfenggang(13,"旋风杠");
	
	private int code;
	private String name;
	
	
	private GetScoreType(int code,String name){
		this.code = code;
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
