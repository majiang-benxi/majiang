package com.mahjong.server.netty.model;

public class CreateRoomReqModel  extends RequestBaseMode{
	
	private String ruleStrategy;
	private int fangKaStrategy;
	
	public String getRuleStrategy() {
		return ruleStrategy;
	}

	public void setRuleStrategy(String ruleStrategy) {
		this.ruleStrategy = ruleStrategy;
	}

	public int getFangKaStrategy() {
		return fangKaStrategy;
	}

	public void setFangKaStrategy(int fangKaStrategy) {
		this.fangKaStrategy = fangKaStrategy;
	}

}
