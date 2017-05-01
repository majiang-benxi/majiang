package com.mahjong.server.game.rule;

public enum FangKa {
	ONE_SIXTEEN(1, "一张房卡16局"), TWO_THIRTY_TWO(2, "2张房卡32局");
	private int code;
	private String desc;

	FangKa(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static FangKa fromCode(int fangkaCode){
		for(FangKa fk : FangKa.values()){
			if(fk.getCode()==fangkaCode){
				return fk;
			}
		};
		return ONE_SIXTEEN;
	}

}
