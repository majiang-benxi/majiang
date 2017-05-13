package com.mahjong.server.netty.model;

import com.alibaba.fastjson.JSON;
import com.mahjong.server.game.enums.EventEnum;

public class AuthReqModel extends RequestBaseMode{

	private String nickName;

	private String headImgUrl;

	private int sex;

	public AuthReqModel() {

	}
	public AuthReqModel(String nickName, String headImgUrl, int sex, String weiXinId) {
		this.nickName = nickName;
		this.headImgUrl = headImgUrl;
		this.sex = sex;
		super.setWeiXinId(weiXinId);
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public static void main(String[] args) {
		AuthReqModel model = new AuthReqModel("wumiao", "http://www.baidu.com", 0, "dfdsfdfd32e323232");
		System.out.println(
				JSON.toJSONString(new ProtocolModel(1.0f, EventEnum.AUTH_REQ.getValue(), 1, JSON.toJSONString(model))));
	}
}
