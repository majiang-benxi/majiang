package com.mahjong.server.netty.model;

/**
 * 解散房间通知消息体
 */
public class KillRoomNoticeRespModel {
	private String nickName;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
