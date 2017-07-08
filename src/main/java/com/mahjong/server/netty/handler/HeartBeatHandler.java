package com.mahjong.server.netty.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.model.RequestBaseMode;
import com.mahjong.server.netty.session.ClientSession;
import com.mahjong.server.service.DBService;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 登陆认证
 *
 */
@Sharable
@Component
public class HeartBeatHandler extends SimpleChannelInboundHandler<ProtocolModel> {
	

	@Autowired
	private DBService dbService;
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
		
		if (protocolModel.getCommandId() != EventEnum.UPDATE_REQ.getValue()) {
			if (protocolModel.getBody() != null) {
				RequestBaseMode requestBaseMode = JSON.parseObject(protocolModel.getBody(),
						new TypeReference<RequestBaseMode>() {
						});
				String weixinId = requestBaseMode.getWeiXinId();
				ClientSession.sessionMap.put(weixinId, ctx);
				
				if(HouseContext.weixinIdToUserInfo.containsKey(weixinId)){
					UserInfo userInfo = dbService.selectUserInfoByWeiXinMark(weixinId);
					HouseContext.weixinIdToUserInfo.put(weixinId, userInfo);
				}
				
				
				Date now = new Date();
				ClientSession.sessionHeartBeatTimeMap.put(weixinId, now);
			}
		}
		ctx.fireChannelRead(protocolModel);// 此处要加这个不然后续handler无法处理
		
	}
	
	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		
		if (evt instanceof IdleStateEvent) {
	        IdleStateEvent e = (IdleStateEvent) evt;
	        if (e.state() == IdleState.READER_IDLE) {
	            ctx.close();
	            System.out.println("READER_IDLE 读超时");
	        } else if (e.state() == IdleState.WRITER_IDLE) {
	            ByteBuf buff = ctx.alloc().buffer();
	            buff.writeBytes("mayi test".getBytes());
	            ctx.writeAndFlush(buff);
	            System.out.println("WRITER_IDLE 写超时");
	        }
	        else {
	            System.out.println("其他超时");
	        }
	    }
		
        ctx.fireUserEventTriggered(evt);
    }
	

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}
}