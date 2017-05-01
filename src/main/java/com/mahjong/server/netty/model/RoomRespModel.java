package com.mahjong.server.netty.model;

import com.mahjong.server.vo.MajiangPlayView;

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
