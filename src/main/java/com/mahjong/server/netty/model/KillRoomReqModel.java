package com.mahjong.server.netty.model;

import com.alibaba.fastjson.JSON;

/**
 * 解散房间请求
 */
public class KillRoomReqModel  extends RequestBaseMode{
	private boolean aggree;

	public boolean isAggree() {
		return aggree;
	}

	public void setAggree(boolean aggree) {
		this.aggree = aggree;
	}
}
