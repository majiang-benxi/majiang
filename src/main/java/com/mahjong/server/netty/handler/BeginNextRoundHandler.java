package com.mahjong.server.netty.handler;

import static com.mahjong.server.game.action.standard.StandardActionType.WIN;

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
				RoomRecord roomRecord = new RoomRecord();
				
				if (userInfo == null) {
					
					logger.info("未注册或者未在线，无法加入房间,weixinId="+weixinId);
					
				} else {
					
					if (roomContex != null) {
						roomContex.getAgreeNextRoundNum().incrementAndGet();
					} else {
						logger.info("房间信息有误,weixinId="+weixinId);
					}
				}
				
				if(roomContex.getAgreeNextRoundNum().get()==4){
					
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
						
						roomContex.getRemaiRound().decrementAndGet();
						
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