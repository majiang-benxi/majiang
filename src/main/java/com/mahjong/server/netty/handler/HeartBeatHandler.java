package com.mahjong.server.netty.handler;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.model.RequestBaseMode;
import com.mahjong.server.netty.session.ClientSession;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 登陆认证
 *
 */
@Component
public class HeartBeatHandler extends SimpleChannelInboundHandler<ProtocolModel> {
	@Override
	public void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
		
		if (protocolModel.getBody() != null) {
			RequestBaseMode requestBaseMode = JSON.parseObject(protocolModel.getBody(),
					new TypeReference<RequestBaseMode>() {
					});
			String weixinId = requestBaseMode.getWeiXinId();
			if(weixinId!=null&&ClientSession.sessionMap.get(weixinId) == null) {
				ClientSession.sessionMap.put(weixinId, ctx);
			}
			Date now = new Date();
			ClientSession.sessionHeartBeatTimeMap.put(weixinId, now);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}
}