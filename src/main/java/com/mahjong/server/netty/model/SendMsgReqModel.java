package com.mahjong.server.netty.model;

import com.alibaba.fastjson.JSON;

/**
 * 发消息
 */
public class SendMsgReqModel  extends RequestBaseMode{
	
	private String msgtype;
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

}
