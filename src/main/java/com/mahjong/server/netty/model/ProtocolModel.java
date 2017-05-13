package com.mahjong.server.netty.model;

/**
 * 协议包
 */
public class ProtocolModel {
	public static final short CUR_VERSION = 1;

	// 协议版本
	private float version;
	// 操作类型
	private int commandId;
	// 设备类型
	private int deviceType;
	// 协议包体
	private String body;// 前端不支持byte，此处先用string


	public float getVersion() {
		return version;
	}

	public void setVersion(float version) {
		this.version = version;
	}

	public int getCommandId() {
		return commandId;
	}

	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

}