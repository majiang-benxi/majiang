package com.mahjong.server.netty.model;

import java.util.List;

/**
 * 解散房间最终消息
 */
public class KillRoomRespModel {
	private boolean result;
	private String msg;
	private List<String> unAgreeNickNames;

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

	public List<String> getUnAgreeNickNames() {
		return unAgreeNickNames;
	}

	public void setUnAgreeNickNames(List<String> unAgreeNickNames) {
		this.unAgreeNickNames = unAgreeNickNames;
	}

}
