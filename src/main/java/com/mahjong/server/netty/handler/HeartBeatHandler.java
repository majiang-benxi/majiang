package com.mahjong.server.netty.handler;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.game.object.PlayerInfo;
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
					
					RoomContext roomContext = HouseContext.weixinIdToRoom.get(weixinId);
					if(roomContext!=null){
					
						PlayerInfo playerInfo = roomContext.getGameContext().getTable().getPlayerInfosByWeixinId(weixinId);
						if(!CollectionUtils.isEmpty(playerInfo.getLastProtocolModel())){
							for(ProtocolModel protocolModel1 : playerInfo.getLastProtocolModel()){
								ctx.writeAndFlush(protocolModel1);
							}
						}
						playerInfo.setLastProtocolModel(new ArrayList<ProtocolModel>());
						
					}
				}
				
				
				Date now = new Date();
				ClientSession.sessionHeartBeatTimeMap.put(weixinId, now);
			}
		}
		ctx.fireChannelRead(protocolModel);// 此处要加这个不然后续handler无法处理
		
	}
	

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}
}