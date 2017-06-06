package com.mahjong.server.netty.handler;

import java.net.InetSocketAddress;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.entity.RoomRecord;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.entity.UserRoomRecord;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.netty.model.CreateRoomReqModel;
import com.mahjong.server.netty.model.CreateRoomRespModel;
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
public class CreateRoomHandler extends SimpleChannelInboundHandler<ProtocolModel> {
	
	private static final Logger logger = LoggerFactory.getLogger(CreateRoomHandler.class);
	

	@Autowired
	private DBService dbService;
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
		if (protocolModel.getCommandId() == EventEnum.CREATE_ROOM_REQ.getValue()) {
			if (protocolModel.getBody() == null) {
				ctx.close();
			} else {
				CreateRoomReqModel createRoomReqModel = JSON.parseObject(protocolModel.getBody(),
						new TypeReference<CreateRoomReqModel>() {
						});
				
				
				CreateRoomRespModel createRoomRespModel = null;
				
				String weixinId = createRoomReqModel.getWeiXinId();
				UserInfo userInfo = HouseContext.weixinIdToUserInfo.get(weixinId);
				ctx = ClientSession.sessionMap.get(weixinId);
				
				if (userInfo == null || ctx==null) {
					logger.error("用户不存在，或者不在线，weixinId="+weixinId);
					createRoomRespModel = new CreateRoomRespModel(weixinId, false,null);
				}else{
					
					String ruleStrategy = createRoomReqModel.getRuleStrategy();
					int fangKaStrategy = createRoomReqModel.getFangKaStrategy();
					
					RoomContext roomContex = null;
					
					//用户不在任何房间
					if((roomContex = HouseContext.weixinIdToRoom.get(weixinId))==null){
						
						roomContex =  HouseContext.addRoom( userInfo, ruleStrategy, fangKaStrategy);
						HouseContext.weixinIdToRoom.put(weixinId, roomContex);
						
						HouseContext.onlineRoomNum.incrementAndGet();
						HouseContext.waitRoomNum.incrementAndGet();
						HouseContext.waitUserNum.incrementAndGet();
						
						logger.info("用户创建房间，weixinId="+weixinId);
						
						RoomRecord roomRecord = new RoomRecord();
						
						Date now = new Date();
						
						roomRecord.setCreateTime(now);
						roomRecord.setCreatorId(userInfo.getId());
						roomRecord.setEastUid(userInfo.getId());
						roomRecord.setRoomNum(roomContex.getRoomNum());
						roomRecord.setRoomRule(ruleStrategy);
						roomRecord.setRoomState((byte) 2);
						
						Integer roomRecId = dbService.insertRoomRecordInfo(roomRecord);
						
						roomContex.setRoomID(roomRecId);
						
						UserRoomRecord userRoomRecord = new UserRoomRecord();
						
						InetSocketAddress socketAddr = (InetSocketAddress) ctx.channel().remoteAddress();
						
						userRoomRecord.setHuTimes(0);
						userRoomRecord.setLoseTimes(0);
						userRoomRecord.setOperateCause("创建房间加入");
						userRoomRecord.setOperateTime(now);
						userRoomRecord.setOperateType((byte) 1);
						userRoomRecord.setRoomNum(roomContex.getRoomNum());
						userRoomRecord.setRoomRecordId(roomRecId);
						userRoomRecord.setUserDirection((byte) 1);
						userRoomRecord.setUserId(userInfo.getId());
						userRoomRecord.setUserIp(socketAddr.getAddress().getHostAddress());
						userRoomRecord.setWinTimes(0);
						
						Integer uroomRecId = dbService.insertUserRoomRecordInfo(userRoomRecord);
						
						PlayerInfo playerInfo = roomContex.getGameContext().getTable().getPlayerByLocation(PlayerLocation.EAST);
						playerInfo.setUserRoomRecordInfoID(uroomRecId);
						
						
					}else{
						
						logger.error("用户已经在房间中，weixinId="+weixinId+",房间号："+roomContex.getRoomNum());
						
					}
					createRoomRespModel = new CreateRoomRespModel(weixinId, true,roomContex);
				}
				
				
				protocolModel.setCommandId(EventEnum.CREATE_ROOM_RESP.getValue());
				protocolModel.setBody(JSON.toJSONString(createRoomRespModel));
				ctx.writeAndFlush(protocolModel);
				logger.error("创建房间返回数据："+JSONObject.toJSONString(protocolModel));
				
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