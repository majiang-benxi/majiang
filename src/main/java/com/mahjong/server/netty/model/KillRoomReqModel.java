package com.mahjong.server.netty.model;

public class KillRoomReqModel {
	private String weixinId;
	private boolean aggree;
	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public boolean isAggree() {
		return aggree;
	}

	public void setAggree(boolean aggree) {
		this.aggree = aggree;
	}

}
