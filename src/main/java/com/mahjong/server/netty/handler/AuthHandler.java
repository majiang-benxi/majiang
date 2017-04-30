package com.mahjong.server.netty.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.netty.model.AuthReqModel;
import com.mahjong.server.netty.model.AuthRespModel;
import com.mahjong.server.netty.model.EventEnum;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;
import com.mahjong.server.netty.session.UserRoomSession;
import com.mahjong.server.service.UserService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 登陆认证
 *
 */
@Component
public class AuthHandler extends SimpleChannelInboundHandler<ProtocolModel> {
	@Autowired
	private UserService userService;
	@Override
	public void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
		if (protocolModel.getCommandId() == EventEnum.AUTH_REQ.getValue()) {
			if (protocolModel.getBody() == null) {
				ctx.close();
			} else {
				AuthReqModel authModel = JSON.parseObject(new String(protocolModel.getBody(), "UTF-8"),
						new TypeReference<AuthReqModel>() {
						});

				String weixinId = authModel.getWeiXinId();
				if (ClientSession.sessionMap.get(weixinId) == null) {
					Boolean isExist = userService.isUserRegister(weixinId);
					if (!isExist) {
						userService.addUser(authModel);
					}
					ClientSession.sessionMap.put(weixinId, ctx);
				}
				int fangKaSize = userService.getFangKaNum(weixinId);
				Integer playingRoomId = UserRoomSession.sessionMap.get(weixinId);
				AuthRespModel authRespModel = new AuthRespModel(true, fangKaSize, playingRoomId);
				// 回写ACK
				protocolModel.setCommandId(EventEnum.AUTH_RESP.getValue());
				protocolModel.setBody(JSON.toJSONString(authRespModel).getBytes());
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