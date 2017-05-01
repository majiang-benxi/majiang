package com.mahjong.server.netty.handler;

import java.net.InetSocketAddress;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.netty.model.AuthReqModel;
import com.mahjong.server.netty.model.AuthRespModel;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;
import com.mahjong.server.service.DBService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 登陆认证
 *
 */
@Component
public class HeartBeatHandler extends SimpleChannelInboundHandler<ProtocolModel> {
	@Autowired
	private DBService dbService;
	@Override
	public void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
		if (protocolModel.getCommandId() == EventEnum.AUTH_REQ.getValue()) {
			if (protocolModel.getBody() == null) {
				ctx.close();
			} else {
				AuthReqModel authModel = JSON.parseObject(new String(protocolModel.getBody(), "UTF-8"),
						new TypeReference<AuthReqModel>() {
						});
				InetSocketAddress socketAddr = (InetSocketAddress) ctx.channel().remoteAddress();

				String weixinId = authModel.getWeiXinId();
				UserInfo userInfo = null;
				if (ClientSession.sessionMap.get(weixinId) == null) {
					userInfo = dbService.selectUserInfoByWeiXinMark(weixinId);
					if (userInfo==null) {
						Date now = new Date();
						userInfo = new UserInfo();
						userInfo.setCreateTime(now);
						userInfo.setHeadImgurl(authModel.getHeadImgUrl());
						userInfo.setLastLoginIp(socketAddr.getAddress().getHostAddress());
						userInfo.setLastLoginTime(now);
						userInfo.setNickName(authModel.getNickName());
						userInfo.setRoomCartNum(0);
						userInfo.setSex((byte) authModel.getSex());
						userInfo.setState((byte) 1);
						userInfo.setWeixinMark(weixinId);
						dbService.insertUserInfo(userInfo);
					}
					ClientSession.sessionMap.put(weixinId, ctx);
				}
				int fangKaSize = userInfo.getRoomCartNum();
				RoomContext playingRoom = HouseContext.weixinIdToRoom.get(weixinId);
				AuthRespModel authRespModel = new AuthRespModel(true, fangKaSize, playingRoom.getRoomNum());
				// 回写ACK
				protocolModel.setCommandId(EventEnum.AUTH_RESP.getValue());
				protocolModel.setBody(JSON.toJSONString(authRespModel).getBytes("UTF-8"));
				ctx.writeAndFlush(protocolModel);
			}
		} else {
			ctx.fireChannelRead(protocolModel);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}
}