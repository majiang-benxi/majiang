package com.mahjong.server.netty.handler;

import static com.mahjong.server.game.action.standard.StandardActionType.WIN;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

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
import com.mahjong.server.exception.IllegalActionException;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.standard.DealActionType;
import com.mahjong.server.game.action.standard.WinActionType;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.enums.RoomStatus;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.netty.model.EnterRoomReqModel;
import com.mahjong.server.netty.model.EnterRoomRespModel;
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
									enterRoomProtocolModel.setBody(JSON.toJSONString(newEnterRoomRespModel));
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
						
					}
				}
				protocolModel.setCommandId(EventEnum.ROOM_ENTER_RESP.getValue());
				protocolModel.setBody(JSON.toJSONString(enterRoomRespModel));
				ctx.writeAndFlush(protocolModel);
				
				logger.error("进入房间返回数据："+JSONObject.toJSONString(protocolModel));
				
				if(flag){
					boolean hashDealTile = dealTile2AllPlayersCheck(roomContex);
					if (hashDealTile) {// 通知所有玩家已经发牌
						
						roomRecord.setRoomState((byte) 2);
						
						boolean flg = dbService.updateRoomRecordInfoByPrimaryKey(roomRecord);
						
						logger.error("更新房间信息，flg="+flg+",roomRecord="+JSONObject.toJSONString(roomRecord));
						
						HouseContext.playRoomNum.incrementAndGet();
						HouseContext.waitRoomNum.decrementAndGet();
						
						HouseContext.playUserNum.addAndGet(4);
						HouseContext.waitUserNum.addAndGet(-4);
						
						roomContex.setRoomStatus(RoomStatus.PLAYING);
						
						for (Entry<PlayerLocation, PlayerInfo> entry : roomContex.getGameContext().getTable().getPlayerInfos().entrySet()) {
							
							ProtocolModel dealTileProtocolModel = new ProtocolModel();
							dealTileProtocolModel.setCommandId(EventEnum.DEAL_TILE_RESP.getValue());
							
							String playWinXinId = entry.getValue().getUserInfo().getWeixinMark();
							EnterRoomRespModel dealTileRoomRespModel = new EnterRoomRespModel(playWinXinId,	true, "发牌", roomContex, entry.getKey());// 创建每个方位的牌响应信息
							dealTileProtocolModel.setBody(JSON.toJSONString(dealTileRoomRespModel));
							
							ChannelHandlerContext userCtx = ClientSession.sessionMap.get(playWinXinId);
							
							userCtx.writeAndFlush(dealTileProtocolModel);
							logger.error("hashDealTile返回数据："+JSONObject.toJSONString(dealTileProtocolModel));
						}
						roomContex.getGameContext().getTable().printAllPlayTiles();

						WinActionType winActionType = new WinActionType();
						boolean winFirst = winActionType.isLegalAction(roomContex.getGameContext(),	roomContex.getGameContext().getZhuangLocation(), new Action(WIN));
						
						if (winFirst) {
							
							PlayerInfo zhuangWinPlayerInfo = roomContex.getGameContext().getTable().getPlayerByLocation(roomContex.getGameContext().getZhuangLocation());
							
							updateUserRoomRecordInfo(zhuangWinPlayerInfo.getUserRoomRecordInfoID(),1,1,null);
							
							winActionType.doAction(roomContex.getGameContext(),	roomContex.getGameContext().getZhuangLocation(), new Action(WIN));
							ProtocolModel winProtocolModel = new ProtocolModel();
							winProtocolModel.setCommandId(EventEnum.WIN_ONE_TIME_RESP.getValue());
							roomContex.setRoomStatus(RoomStatus.PLAYING);
							EnterRoomRespModel winTileRoomRespModel = new EnterRoomRespModel(null, true, "庄家天胡", roomContex);
							winProtocolModel.setBody(JSON.toJSONString(winTileRoomRespModel));
							
							HandlerHelper.noticeMsg2Players(roomContex, null, winProtocolModel);
							
						}
						
					}
					
				}
			}
				
		} else {
			ctx.fireChannelRead(protocolModel);
		}
	}

	private boolean dealTile2AllPlayersCheck(RoomContext roomContex) {
		Map<PlayerLocation, PlayerInfo> playerInfos = roomContex.getGameContext().getTable().getPlayerInfos();
		boolean fourUserNumReady = true;
		for (PlayerInfo playerInfo : playerInfos.values()) {
			if (playerInfo.getUserInfo() == null) {
				fourUserNumReady = false;
				return false;
			}
		}
		if (fourUserNumReady) {
			roomContex.getGameContext().getTable().readyForGame();
			DealActionType dealActionType = new DealActionType();
			try {
				dealActionType.doAction(roomContex.getGameContext(), PlayerLocation.EAST, null);
			} catch (IllegalActionException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
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
			winuserRoomRecForUpdate.setHuTimes(winuserRoomRec.getHuTimes()+huNum);
		}
		
		if(winNum != null && winNum>0){
			winuserRoomRecForUpdate.setWinTimes(winuserRoomRec.getWinTimes()+winNum);
		}
		
		if(loseNum != null && loseNum>0){
			winuserRoomRecForUpdate.setLoseTimes(winuserRoomRec.getLoseTimes()+loseNum);
		}
		
		dbService.updateUserRoomRecordInfoPrimaryKey(winuserRoomRecForUpdate);
		return true;
	}

}