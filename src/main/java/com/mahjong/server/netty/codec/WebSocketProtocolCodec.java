package com.mahjong.server.netty.codec;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.netty.model.ProtocolModel;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

@Component
@ChannelHandler.Sharable
public class WebSocketProtocolCodec extends MessageToMessageCodec<TextWebSocketFrame, ProtocolModel> {


	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, ProtocolModel protocolModel, List<Object> out)
			throws Exception {
		String msg = JSON.toJSONString(protocolModel);
		System.out.println("encode=" + msg);
		out.add(new TextWebSocketFrame(msg));
	}

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame,
			List<Object> in) throws Exception {
		String text = textWebSocketFrame.text();
		System.out.println("decode=" + text);
		ProtocolModel protocolMsg = JSON.parseObject(text, new TypeReference<ProtocolModel>() {
		});
		if (protocolMsg != null) {
			in.add(protocolMsg);
		}
		
	}
}
