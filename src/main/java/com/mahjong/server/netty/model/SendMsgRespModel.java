package com.mahjong.server.netty.model;

/**
 * 消息
 */
public class SendMsgRespModel {
	
	private String mesgFrom;
	private String msgtype;//1：文字，2：语音
	private String msg;

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

	public String getMesgFrom() {
		return mesgFrom;
	}

	public void setMesgFrom(String mesgFrom) {
		this.mesgFrom = mesgFrom;
	}

}
