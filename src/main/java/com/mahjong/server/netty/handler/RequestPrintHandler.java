package com.mahjong.server.netty.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.mahjong.server.netty.model.ProtocolModel;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 登陆认证
 *
 */
@Sharable
@Component
public class RequestPrintHandler extends SimpleChannelInboundHandler<ProtocolModel> {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestPrintHandler.class);
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
		logger.error("请求参数："+JSONObject.toJSONString(protocolModel));
		ctx.fireChannelRead(protocolModel);// 此处要加这个不然后续handler无法处理
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}
}