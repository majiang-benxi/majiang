package com.mahjong.server.netty.handler;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.enums.RoomStatus;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.netty.model.EnterRoomReqModel;
import com.mahjong.server.netty.model.EnterRoomRespModel;
import com.mahjong.server.netty.model.KillRoomNoticeRespModel;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;
import com.mahjong.server.service.DBService;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 登陆认证
 *
 */
@Sharable
@Component
public class BeginNextRoundHandler extends SimpleChannelInboundHandler<ProtocolModel> {
	
	private static final Logger logger = LoggerFactory.getLogger(BeginNextRoundHandler.class);
	
	@Autowired
	private DBService dbService;

	@Override
	public void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
		
		if (protocolModel.getCommandId() == EventEnum.BEGIN_NEXT_REQ.getValue()) {
			if (protocolModel.getBody() == null) {
				ctx.close();
			} else {
				
				EnterRoomReqModel enterRoomReqModel = JSON.parseObject(protocolModel.getBody(),
						new TypeReference<EnterRoomReqModel>() {
						});

				String weixinId = enterRoomReqModel.getWeiXinId();
				UserInfo userInfo = HouseContext.weixinIdToUserInfo.get(weixinId);
				
				RoomContext roomContex = HouseContext.weixinIdToRoom.get(weixinId);
				
				if (userInfo == null) {
					
					logger.info("未注册或者未在线，无法加入房间,weixinId="+weixinId);
					
				} else {
					
					
					
					if (roomContex != null) {
						
						
						PlayerInfo playerInfo = roomContex.getGameContext().getTable().getPlayerInfosByWeixinId(weixinId);
						playerInfo.setAgreeNextRound(true);
						roomContex.setRoomStatus(RoomStatus.WAIT_FOR_READY);
						
						if(roomContex.getAgreeNextRoundNum().get()!=4){
							// 通知其他三家
							ProtocolModel enterRoomProtocolModel = new ProtocolModel();
							enterRoomProtocolModel.setCommandId(EventEnum.BEGIN_NEXT_NOTICE_RESP.getValue());
							EnterRoomRespModel newEnterRoomRespModel = new EnterRoomRespModel(weixinId, true, "玩家准备", roomContex);
							enterRoomProtocolModel.setBody(JSON.toJSONString(newEnterRoomRespModel,SerializerFeature.DisableCircularReferenceDetect));
							HandlerHelper.noticeMsg2Players(roomContex, weixinId, enterRoomProtocolModel);
								
						    logger.error("玩家准备：weixinId="+weixinId+"，数据："+JSONObject.toJSONString(protocolModel));
								
						}
						roomContex.getAgreeNextRoundNum().incrementAndGet();
					} else {
						logger.info("房间信息有误,weixinId="+weixinId);
					}
				}
				
				if(roomContex.getAgreeNextRoundNum().get()==4){
					
					for(Entry<PlayerLocation, PlayerInfo>  ent : roomContex.getGameContext().getTable().getPlayerInfos().entrySet()){
						PlayerInfo playerInfo = ent.getValue();
						playerInfo.setAgreeNextRound(false);
					}
					
					roomContex.setRoomStatus(RoomStatus.PLAYING);
					HandlerHelper.dealTile2AllPlayers(roomContex,dbService);
				}
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