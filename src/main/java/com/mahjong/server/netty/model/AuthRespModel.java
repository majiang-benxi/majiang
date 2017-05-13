package com.mahjong.server.netty.model;

public class AuthRespModel {
	private boolean auth;
	private int fangKaSize;
	private Integer playingRoomId;// 登录掉线的情况下，此值不为空，其他情况为空。

	public AuthRespModel() {

	}
	public AuthRespModel(boolean auth, int fangKaSize, Integer playingRoomId) {
		this.auth = auth;
		this.fangKaSize = fangKaSize;
		this.playingRoomId = playingRoomId;
	}

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public int getFangKaSize() {
		return fangKaSize;
	}

	public void setFangKaSize(int fangKaSize) {
		this.fangKaSize = fangKaSize;
	}

	public Integer getPlayingRoomId() {
		return playingRoomId;
	}

	public void setPlayingRoomId(Integer playingRoomId) {
		this.playingRoomId = playingRoomId;
	}

}
