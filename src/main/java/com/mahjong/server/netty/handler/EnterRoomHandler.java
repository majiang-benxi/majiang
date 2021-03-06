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
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mahjong.server.entity.RoomRecord;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.entity.UserRoomRecord;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.enums.RoomStatus;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.netty.model.EnterRoomReqModel;
import com.mahjong.server.netty.model.EnterRoomRespModel;
import com.mahjong.server.netty.model.ProtocolModel;
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
public class EnterRoomHandler extends SimpleChannelInboundHandler<ProtocolModel> {
	
	private static final Logger logger = LoggerFactory.getLogger(EnterRoomHandler.class);
	
	@Autowired
	private DBService dbService;

	@Override
	public void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
		
		if (protocolModel.getCommandId() == EventEnum.ROOM_ENTER_REQ.getValue()) {
			if (protocolModel.getBody() == null) {
				ctx.close();
			} else {
				EnterRoomReqModel enterRoomReqModel = JSON.parseObject(protocolModel.getBody(),
						new TypeReference<EnterRoomReqModel>() {
						});

				String weixinId = enterRoomReqModel.getWeiXinId();
				Integer roomId = enterRoomReqModel.getRoomId();
				EnterRoomRespModel enterRoomRespModel = null;
				UserInfo userInfo = HouseContext.weixinIdToUserInfo.get(weixinId);
				PlayerInfo playerInfo = null;
				
				RoomContext roomContex = HouseContext.weixinIdToRoom.get(weixinId);
				RoomRecord roomRecord = new RoomRecord();
				
				boolean flag = false;
				
				if (userInfo == null) {
					
					enterRoomRespModel = new EnterRoomRespModel(weixinId, false, "您未注册或者未在线，无法加入房间，请注册！", null);
					logger.info("未注册或者未在线，无法加入房间,weixinId="+weixinId);
					
				} else {
					
					if (roomContex == null) {
							
						if ((roomContex = HouseContext.rommList.get(roomId)) != null) {
								
							if(roomContex.getRoomStatus().getCode()!=RoomStatus.WAIT_USERS.getCode()){
								enterRoomRespModel = new EnterRoomRespModel(weixinId, false, "房间人数已满或已结束，无法加入房间！", null);
								logger.info("房间人数已满，无法加入房间，weixinId="+weixinId);
							}else{
								
								playerInfo = roomContex.joinRoom(userInfo);
								
								if (playerInfo!=null) {
									
									HouseContext.waitUserNum.incrementAndGet();
									HouseContext.weixinIdToRoom.put(weixinId, roomContex);
									
									
									UserRoomRecord userRoomRecord = new UserRoomRecord();
									
									roomRecord.setId(roomContex.getRoomRecordID());
									
									if(playerInfo.getUserLocation().intValue() == PlayerLocation.NORTH.getCode()){
										roomRecord.setNorthUid(userInfo.getId());
										userRoomRecord.setUserDirection((byte) 4);
										
									}else if(playerInfo.getUserLocation().intValue() == PlayerLocation.WEST.getCode()){
										roomRecord.setWestUid(userInfo.getId());
										userRoomRecord.setUserDirection((byte) 3);
										
									}else if(playerInfo.getUserLocation().intValue() == PlayerLocation.SOUTH.getCode()){
										roomRecord.setSouthUid(userInfo.getId());
										userRoomRecord.setUserDirection((byte) 2);
										
									}
									
									
									Date now = new Date();
									
									InetSocketAddress socketAddr = (InetSocketAddress) ctx.channel().remoteAddress();
									
									userRoomRecord.setHuTimes(0);
									userRoomRecord.setLoseTimes(0);
									userRoomRecord.setOperateCause("加入房间");
									userRoomRecord.setOperateType((byte) 1);
									userRoomRecord.setOperateTime(now);
									
									userRoomRecord.setRoomNum(roomContex.getRoomNum());
									
									userRoomRecord.setRoomRecordId(roomContex.getRoomRecordID());
									
									userRoomRecord.setUserId(userInfo.getId());
									userRoomRecord.setUserIp(socketAddr.getAddress().getHostAddress());
									userRoomRecord.setWinTimes(0);
									
									Integer userRoomRecordId = dbService.insertUserRoomRecordInfo(userRoomRecord);
									
									playerInfo.setUserRoomRecordInfoID(userRoomRecordId);
									
									roomContex.setRoomStatus(RoomStatus.WAIT_USERS);
									
									// 通知其他三家
									ProtocolModel enterRoomProtocolModel = new ProtocolModel();
									enterRoomProtocolModel.setCommandId(EventEnum.NEW_ENTER_RESP.getValue());
									EnterRoomRespModel newEnterRoomRespModel = new EnterRoomRespModel(weixinId, true, "新人加入", roomContex);
									enterRoomProtocolModel.setBody(JSON.toJSONString(newEnterRoomRespModel,SerializerFeature.DisableCircularReferenceDetect));
									HandlerHelper.noticeMsg2Players(roomContex, weixinId, enterRoomProtocolModel);
									
									flag = true;
									
									enterRoomRespModel = new EnterRoomRespModel(weixinId, true, "加入成功", roomContex);
									
								} else {
									enterRoomRespModel = new EnterRoomRespModel(weixinId, false, "加入房间失败，房间已满！", null);
								}
							}
							
						} else {
							
							enterRoomRespModel = new EnterRoomRespModel(weixinId, false, "加入房间失败，房间不存在！", null);
							
						}
	
					} else {
						
						enterRoomRespModel = new EnterRoomRespModel(weixinId, true, "重新加入房间", roomContex);
						logger.info("重新加入房间,weixinId="+weixinId);
						
						playerInfo = roomContex.getGameContext().getTable().getPlayerInfosByWeixinId(weixinId);
						
						
					}
				}
				protocolModel.setCommandId(EventEnum.ROOM_ENTER_RESP.getValue());
				protocolModel.setBody(JSON.toJSONString(enterRoomRespModel,SerializerFeature.DisableCircularReferenceDetect));
				
				HandlerHelper.noticeMsg2Player(ctx, playerInfo, protocolModel);
				
				logger.error("进入房间返回数据："+JSONObject.toJSONString(protocolModel));
				
				if(flag){
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
	
	private boolean updateUserRoomRecordInfo(Integer userRoomRecordId, Integer huNum ,Integer winNum ,Integer loseNum){
		UserRoomRecord winuserRoomRec = dbService.selectUserRoomRecordInfoByID(userRoomRecordId);
		
		UserRoomRecord winuserRoomRecForUpdate = new UserRoomRecord();
		winuserRoomRecForUpdate.setId(userRoomRecordId);
		
		if(huNum != null && huNum>0){
			
			int temp = winuserRoomRec.getHuTimes()==null?0:winuserRoomRec.getHuTimes();
			
			winuserRoomRecForUpdate.setHuTimes(temp+huNum);
		}
		
		if(winNum != null && winNum>0){
			int temp = winuserRoomRec.getWinTimes()==null?0:winuserRoomRec.getWinTimes();
			winuserRoomRecForUpdate.setWinTimes(temp+winNum);
		}
		
		if(loseNum != null && loseNum>0){
			int temp = winuserRoomRec.getLoseTimes()==null?0:winuserRoomRec.getLoseTimes();
			winuserRoomRecForUpdate.setLoseTimes(temp+loseNum);
		}
		
		dbService.updateUserRoomRecordInfoPrimaryKey(winuserRoomRecForUpdate);
		return true;
	}

}