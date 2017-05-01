package com.mahjong.server.netty.model;

/**
 * 发消息
 */
public class SendMsgReqModel {
	
	private String weixinId;
	private String msgtype;//1：文字，2：语音
	private String msg;

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


}
