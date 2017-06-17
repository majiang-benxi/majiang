package com.mahjong.server.netty.handler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.mahjong.server.entity.UserActionScore;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.entity.UserRoomRecord;
import com.mahjong.server.exception.IllegalActionException;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.action.standard.DiscardActionType;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.enums.PlayerLocation.Relation;
import com.mahjong.server.game.object.DisCardActionAndLocation;
import com.mahjong.server.game.object.DiscardContext;
import com.mahjong.server.game.object.GameResult;
import com.mahjong.server.game.object.MahjongTable;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.TileGroupType;
import com.mahjong.server.netty.model.CurrentRecordRespModel;
import com.mahjong.server.netty.model.DiscardReqModel;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;
import com.mahjong.server.service.DBService;
import com.mahjong.server.vo.ScoreRecordVO;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
@Component
public class MahjongLogicHandler extends SimpleChannelInboundHandler<ProtocolModel> {
	
	private static final Logger logger = LoggerFactory.getLogger(EnterRoomHandler.class);

	@Autowired
	private DBService dbService;
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
		if (protocolModel.getCommandId() == EventEnum.DISCARD_ONE_CARD_REQ.getValue()) {
			if (protocolModel.getBody() == null) {
				ctx.close();
			} else {
				DiscardReqModel discardReqModel = JSON.parseObject(protocolModel.getBody(),
						new TypeReference<DiscardReqModel>() {
						});
				String weixinId = discardReqModel.getWeiXinId();
				ctx = ClientSession.sessionMap.get(weixinId);
				RoomContext roomContext = HouseContext.weixinIdToRoom.get(weixinId);
				PlayerLocation discardPlayLocation = null;
				
				for (Entry<PlayerLocation, PlayerInfo> entry : roomContext.getGameContext().getTable().getPlayerInfos()
						.entrySet()) {
					if (weixinId.equals(entry.getValue().getUserInfo().getWeixinMark())) {
						discardPlayLocation = entry.getKey();
						break;
					}
				}
				roomContext.getGameContext().getTable().printAllPlayTiles();

				try {
					if (discardReqModel.getTileGroupType() == TileGroupType.ONE_GROUP.getCode()) {
						// 剩余玩家吃碰杠胡检测,如果其他玩家可以吃碰杠胡的时候，按照优先级逐个通知处理
						DiscardActionType discardActionType = new DiscardActionType();
						discardActionType.doAction(roomContext.getGameContext(), discardPlayLocation,
								new Action(discardActionType, discardReqModel.getTile()));
						List<DisCardActionAndLocation> disCardActionAndLocations = HandlerHelper
								.getActionAfterDiscardTile(roomContext, discardReqModel.getTile(), discardPlayLocation);
						if (disCardActionAndLocations.isEmpty()) {// 所有下家对这个打的牌没有可以操作的动作后就继续给下家发牌
							HandlerHelper.drawTile2Player(roomContext,
									discardPlayLocation.getLocationOf(Relation.NEXT));
						} else {// 按照权重询问第一个玩家
							int remainVoter = HandlerHelper.groupByActionByLocation(disCardActionAndLocations).keys()
									.size();
							roomContext.getGameContext()
									.setDiscardContext(new DiscardContext(disCardActionAndLocations, remainVoter));
							HandlerHelper.askChoice2Player(roomContext, disCardActionAndLocations);
						}
					} else {
						HandlerHelper.processDiscardResp(roomContext, discardPlayLocation, discardReqModel);
					}
					
					if(roomContext.getGameContext().isHuangzhuang()){
						
						RoomContext playingRoom = HouseContext.weixinIdToRoom.get(weixinId);
						
						UserInfo userInfo = HouseContext.weixinIdToUserInfo.get(weixinId);
						
						if (userInfo != null && ctx!=null) {
						
							CurrentRecordRespModel currentRecordRespModel = new CurrentRecordRespModel();
							
							List<ScoreRecordVO> playScordRecords = new ArrayList<ScoreRecordVO>();
							
							GameResult gameResult = playingRoom.getGameContext().getGameResult();
							
							for(Entry<PlayerLocation, PlayerInfo> playerInfoEnt : gameResult.getPlayerInfos().entrySet()){
								
								PlayerInfo playerInfo = playerInfoEnt.getValue();
								
								String  getScoreTypes = HandlerHelper.getScoreTypesStr(playerInfo.getGatherScoreTypes());
								
								UserActionScore userActionScore = new UserActionScore();
								userActionScore.setActionScore(playerInfo.getCurScore());
								userActionScore.setWinActionTypes(getScoreTypes);
								userActionScore.setRoomRecordId(playingRoom.getRoomRecordID());
								userActionScore.setCreateTime(new Date());
								userActionScore.setRoundNum(playingRoom.getRoomNum());
								userActionScore.setUserId(playerInfo.getUserInfo().getId());
								
								dbService.insertUserActionScoreInfo(userActionScore);
								
								ScoreRecordVO scoreRecordVO = new ScoreRecordVO();
								scoreRecordVO.setNickName(playerInfo.getUserInfo().getNickName());
								scoreRecordVO.setTotalScore(playerInfo.getCurScore());
								scoreRecordVO.setWinActionTypes(getScoreTypes);
								
								playScordRecords.add(scoreRecordVO);
								
							}
							
							currentRecordRespModel.setPlayScordRecords(playScordRecords);
							
							//黄庄算一局
							playingRoom.getRemaiRound().decrementAndGet();
							
							if(playingRoom.getRemaiRound().get()==0){
								
								RoomContext roomContex = HouseContext.weixinIdToRoom.get(weixinId);
								
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
									
									InetSocketAddress socketAddr = (InetSocketAddress) ctx.channel().remoteAddress();
									
									userRoomRecord.setHuTimes(0);
									userRoomRecord.setLoseTimes(0);
									userRoomRecord.setOperateCause("游戏结束，离开");
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
								roomRecord.setRoomState((byte) 4);
								roomRecord.setEndTime(new Date());
								boolean flg = dbService.updateRoomRecordInfoByPrimaryKey(roomRecord);
								
							}else{
								
								RoomContext roomContex = HouseContext.weixinIdToRoom.get(weixinId);
								GameContext gameContext = roomContex.getGameContext();
								
								gameContext.setDiscardContext(null);
								gameContext.setGameResult(null);
								gameContext.setHuangzhuang(false);
								gameContext.setLocalDoneActions(new ArrayList<ActionAndLocation>());
								
								MahjongTable table = new MahjongTable();
								table.init();
								
								MahjongTable mahjongTable = gameContext.getTable();
								table.setPlayerInfos(mahjongTable.getPlayerInfos());
								
								gameContext.setTable(table);
								
								roomContex.setAgreeNextRoundNum(new AtomicInteger(0));
							}
						}
					}
					
				} catch (IllegalActionException e) {
					roomContext.getGameContext().getTable().printAllPlayTiles();
					ProtocolModel illegalProtocolModel = new ProtocolModel();
					illegalProtocolModel.setCommandId(EventEnum.ILLEGAL_ACTION_RESP.getValue());
					illegalProtocolModel.setBody(null);
					ctx.writeAndFlush(illegalProtocolModel);
					
					logger.error("主逻辑返回数据："+JSONObject.toJSONString(illegalProtocolModel));
				}
			}

		} else {
			ctx.fireChannelRead(protocolModel);
		}
	}
}
