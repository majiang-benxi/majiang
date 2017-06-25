package com.mahjong.server.netty.handler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

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
import com.mahjong.server.game.enums.RoomStatus;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.netty.model.EnterRoomRespModel;
import com.mahjong.server.netty.model.KillRoomNoticeRespModel;
import com.mahjong.server.netty.model.KillRoomReqModel;
import com.mahjong.server.netty.model.KillRoomRespModel;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;
import com.mahjong.server.service.DBService;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 解散房间
 *
 */
@Sharable
@Component
public class KillRoomHandler extends SimpleChannelInboundHandler<ProtocolModel> {

	@Autowired
	private DBService dbService;
	
	private static final Logger logger = LoggerFactory.getLogger(HistoryRecordHandler.class);
	@Override
	public void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
		if (protocolModel.getCommandId() == EventEnum.KILL_ROOM_REQ.getValue()) {
			if (protocolModel.getBody() == null) {
				ctx.close();
			} else {
				KillRoomReqModel killRoomReqModel = JSON.parseObject(protocolModel.getBody(),
						new TypeReference<KillRoomReqModel>() {
						});
				
				String weixinId = killRoomReqModel.getWeiXinId();
				Boolean isAggree = killRoomReqModel.isAggree();
				UserInfo userInfo = HouseContext.weixinIdToUserInfo.get(weixinId);
				
				if (userInfo != null && ctx!=null) {
					
					RoomContext roomContex = HouseContext.weixinIdToRoom.get(weixinId);
					
					if(roomContex.getRoomStatus().getCode() == RoomStatus.WAIT_USERS.getCode()){
						if(roomContex.getCreatorWeiXinId().equals(weixinId)){
							roomContex.setAgreeKillRoomNum(new AtomicInteger(3));
						}else{
							
							roomContex.getGameContext().getTable().removePlayerInfos(weixinId);
							
							// 通知其他三家
							ProtocolModel enterRoomProtocolModel = new ProtocolModel();
							enterRoomProtocolModel.setCommandId(EventEnum.NEW_ENTER_RESP.getValue());
							EnterRoomRespModel newEnterRoomRespModel = new EnterRoomRespModel(weixinId, true, "新人加入", roomContex);
							enterRoomProtocolModel.setBody(JSON.toJSONString(newEnterRoomRespModel));
							HandlerHelper.noticeMsg2Players(roomContex, weixinId, enterRoomProtocolModel);
							
							UserRoomRecord userRoomRecord = new UserRoomRecord();
							
							PlayerInfo playerInfo = roomContex.getGameContext().getTable().getPlayerInfosByWeixinId(weixinId);
							
							if(playerInfo.getUserLocation().intValue() == PlayerLocation.NORTH.getCode()){
								userRoomRecord.setUserDirection((byte) 4);
								
							}else if(playerInfo.getUserLocation().intValue() == PlayerLocation.WEST.getCode()){
								userRoomRecord.setUserDirection((byte) 3);
								
							}else if(playerInfo.getUserLocation().intValue() == PlayerLocation.SOUTH.getCode()){
								userRoomRecord.setUserDirection((byte) 2);
								
							}
							
							Date now = new Date();
							
							ChannelHandlerContext userCtx = ClientSession.sessionMap.get(playerInfo.getUserInfo().getWeixinMark());
							
							InetSocketAddress socketAddr = (InetSocketAddress) userCtx.channel().remoteAddress();
							
							userRoomRecord.setHuTimes(0);
							userRoomRecord.setLoseTimes(0);
							userRoomRecord.setOperateCause("解散房间，离开");
							userRoomRecord.setOperateType((byte) 2);
							userRoomRecord.setOperateTime(now);
							
							userRoomRecord.setRoomNum(roomContex.getRoomNum());
							
							userRoomRecord.setRoomRecordId(roomContex.getRoomRecordID());
							
							userRoomRecord.setUserId(playerInfo.getUserInfo().getId());
							userRoomRecord.setUserIp(socketAddr.getAddress().getHostAddress());
							userRoomRecord.setWinTimes(0);
							
							Integer userRoomRecordId = dbService.insertUserRoomRecordInfo(userRoomRecord);
							
							
						}
						
					}
					
					if(roomContex.getAgreeKillRoomNum().intValue()==0){
						// 通知其他三家
						for(Entry<PlayerLocation, PlayerInfo>  ent : roomContex.getGameContext().getTable().getPlayerInfos().entrySet()){
							
							PlayerInfo playerIn = ent.getValue();
							
							if(playerIn.getUserInfo().getWeixinMark().equals(weixinId)){
								continue;
							}
							
							ProtocolModel newProtocolModel = new ProtocolModel();
							newProtocolModel.setCommandId(EventEnum.KILL_ROOM_NOTICE_RESP.getValue());
							
							KillRoomNoticeRespModel killRoomNoticeRespModel = new KillRoomNoticeRespModel();
							killRoomNoticeRespModel.setNickName(userInfo.getNickName());
							
							newProtocolModel.setBody(JSON.toJSONString(killRoomNoticeRespModel));
							
							ChannelHandlerContext userCtx = ClientSession.sessionMap.get(playerIn.getUserInfo().getWeixinMark());
							userCtx.writeAndFlush(newProtocolModel);
							
							logger.error("解散房间返回数据：weixinId="+playerIn.getUserInfo().getWeixinMark()+"，数据："+JSONObject.toJSONString(protocolModel));
							
						}
						
					}
					if(isAggree){
						roomContex.getAgreeKillRoomNum().incrementAndGet();
					}else{
						roomContex.getDisagreeUserNames().add(userInfo.getNickName());
						roomContex.getDisAgreeKillRoomNum().incrementAndGet();
					}
					
					if((roomContex.getAgreeKillRoomNum().intValue()+roomContex.getDisAgreeKillRoomNum().intValue()) == 4){
						
						KillRoomRespModel killRoomRespModel = new KillRoomRespModel();
						
						if(roomContex.getDisAgreeKillRoomNum().intValue()==0){
							
							killRoomRespModel.setResult(true);
							killRoomRespModel.setMsg("解散成功！");
							
						}else{
							
							killRoomRespModel.setResult(false);
							killRoomRespModel.setMsg("解散失败！");
							killRoomRespModel.setUnAgreeNickNames(roomContex.getDisagreeUserNames());
							
						}
						
						protocolModel.setCommandId(EventEnum.KILL_ROOM_RESP.getValue());
						protocolModel.setBody(JSON.toJSONString(killRoomRespModel));
						
						for(Entry<PlayerLocation, PlayerInfo>  ent : roomContex.getGameContext().getTable().getPlayerInfos().entrySet()){
							
							PlayerInfo playerIn = ent.getValue();
							
							ChannelHandlerContext userCtx = ClientSession.sessionMap.get(playerIn.getUserInfo().getWeixinMark());
							userCtx.writeAndFlush(protocolModel);
						
							logger.error("解散房间返回数据："+JSONObject.toJSONString(protocolModel));
						}
						
						
						if(roomContex.getDisAgreeKillRoomNum().intValue()==0){
							
							HouseContext.rommList.remove(roomContex.getRoomNum());
							
							HouseContext.playRoomNum.decrementAndGet();
							HouseContext.playUserNum.addAndGet(-4);
							
							for(Entry<PlayerLocation, PlayerInfo>  ent : roomContex.getGameContext().getTable().getPlayerInfos().entrySet()){
								PlayerInfo playerInfo = ent.getValue();
								HouseContext.weixinIdToRoom.remove(playerInfo.getUserInfo().getWeixinMark());
								
								UserRoomRecord userRoomRecord = new UserRoomRecord();
								
								if(playerInfo.getUserLocation().intValue() == PlayerLocation.NORTH.getCode()){
									userRoomRecord.setUserDirection((byte) 4);
									
								}else if(playerInfo.getUserLocation().intValue() == PlayerLocation.WEST.getCode()){
									userRoomRecord.setUserDirection((byte) 3);
									
								}else if(playerInfo.getUserLocation().intValue() == PlayerLocation.SOUTH.getCode()){
									userRoomRecord.setUserDirection((byte) 2);
									
								}
								
								Date now = new Date();
								
								
								ChannelHandlerContext userCtx = ClientSession.sessionMap.get(playerInfo.getUserInfo().getWeixinMark());
								
								InetSocketAddress socketAddr = (InetSocketAddress) userCtx.channel().remoteAddress();
								
								userRoomRecord.setHuTimes(0);
								userRoomRecord.setLoseTimes(0);
								userRoomRecord.setOperateCause("解散房间，离开");
								userRoomRecord.setOperateType((byte) 2);
								userRoomRecord.setOperateTime(now);
								
								userRoomRecord.setRoomNum(roomContex.getRoomNum());
								
								userRoomRecord.setRoomRecordId(roomContex.getRoomRecordID());
								
								userRoomRecord.setUserId(playerInfo.getUserInfo().getId());
								userRoomRecord.setUserIp(socketAddr.getAddress().getHostAddress());
								userRoomRecord.setWinTimes(0);
								
								Integer userRoomRecordId = dbService.insertUserRoomRecordInfo(userRoomRecord);
								
							}
							
							RoomRecord roomRecord = new RoomRecord();
							roomRecord.setId(roomContex.getRoomRecordID());
							roomRecord.setRoomState((byte) 3);
							boolean flg = dbService.updateRoomRecordInfoByPrimaryKey(roomRecord);
							
						}else{
							
							/**
							 * 清空本次记录
							 */
							roomContex.setAgreeKillRoomNum(new AtomicInteger(0));
							roomContex.setDisAgreeKillRoomNum(new AtomicInteger(0));
							roomContex.setDisagreeUserNames(new ArrayList<String>());
						}
						
					}
						
				}else{
					
					logger.error("用户不存在，或者不在线，weixinId="+weixinId);
					
					KillRoomRespModel killRoomRespModel = new KillRoomRespModel();
					killRoomRespModel.setResult(false);
					killRoomRespModel.setMsg("当前用户不存在，没有权限解散！");
					
					protocolModel.setCommandId(EventEnum.KILL_ROOM_RESP.getValue());
					protocolModel.setBody(JSON.toJSONString(killRoomRespModel));
					
					ctx.writeAndFlush(protocolModel);
					
					logger.error("解散房间返回数据："+JSONObject.toJSONString(protocolModel));
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