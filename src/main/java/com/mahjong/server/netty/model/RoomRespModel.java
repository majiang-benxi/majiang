package com.mahjong.server.netty.model;

public class RoomRespModel extends MajiangPlayView {
	private boolean result;
	private String msg;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
