package com.mahjong.server.netty.model;

/**
 * 协议包
 * 
 * @author 粱桂钊
 * @since
 *        <p>
 *        更新时间: 2016年7月31日 v0.1
 *        </p>
 *        <p>
 *        版本内容: 创建
 *        </p>
 */
public class ProtocolModel {
	public static final short CUR_VERSION = 1;

	// 协议版本
	private short version;
	// 操作类型
	private int commandId;
	// 设备类型
	private String deviceType;
	// 协议包体
	private byte[] body;


	public short getVersion() {
		return version;
	}

	public void setVersion(short version) {
		this.version = version;
	}

	public int getCommandId() {
		return commandId;
	}

	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

}