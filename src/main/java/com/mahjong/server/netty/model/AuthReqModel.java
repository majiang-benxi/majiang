package com.mahjong.server.netty.model;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;

public class AuthReqModel {

	private String weiXinId;
    
	private String nickName;

	private String loginIp;

	private String headImgUrl;

	private int sex;


	public String getWeiXinId() {
		return weiXinId;
	}

	public void setWeiXinId(String weiXinId) {
		this.weiXinId = weiXinId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		AuthReqModel authReqModel = new AuthReqModel();
		authReqModel.setHeadImgUrl("http://www.baidu.com");
		authReqModel.setLoginIp("11.12.22.11");
		authReqModel.setNickName("shuige");
		authReqModel.setWeiXinId("13342dfdfsdcdfsd");
		System.out.println(JSON.toJSONString(authReqModel));
		ProtocolModel protocolModel = new ProtocolModel();
		protocolModel.setCommandId(1);
		protocolModel.setDeviceType(1);
		protocolModel.setVersion(2.1f);
		protocolModel.setBody(JSON.toJSONString(authReqModel).getBytes("UTF-8"));
		System.out.println(JSON.toJSONString(protocolModel));

	}
}
