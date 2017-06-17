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
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.GameResult;
import com.mahjong.server.game.object.GetScoreType;
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
public class ToWinHandler extends SimpleChannelInboundHandler<ProtocolModel> {
	
	private static final Logger logger = LoggerFactory.getLogger(ToWinHandler.class);
	
	@Autowired
	private DBService dbService;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel)
			throws Exception {
		if (protocolModel.getCommandId() == EventEnum.DISCARD_ONE_CARD_REQ.getValue()) {
			if (protocolModel.getBody() == null) {
				ctx.close();
			} else {
				DiscardReqModel discardReqModel = JSON.parseObject(protocolModel.getBody(),
						new TypeReference<DiscardReqModel>() {
						});
				
				if(discardReqModel.getTileGroupType()==TileGroupType.HU_GROUP.getCode()){
					
					String weixinId = discardReqModel.getWeiXinId();
					RoomContext playingRoom = HouseContext.weixinIdToRoom.get(weixinId);
					
					UserInfo userInfo = HouseContext.weixinIdToUserInfo.get(weixinId);
					
					if (userInfo != null && ctx!=null) {
					
						CurrentRecordRespModel currentRecordRespModel = new CurrentRecordRespModel();
						
						List<ScoreRecordVO> playScordRecords = new ArrayList<ScoreRecordVO>();
						
						GameResult gameResult = playingRoom.getGameContext().getGameResult();
						
						PlayerInfo winPlayerInfo = null; 
						
						
						for(Entry<PlayerLocation, PlayerInfo> playerInfoEnt : gameResult.getPlayerInfos().entrySet()){
							
							PlayerInfo playerInfo = playerInfoEnt.getValue();
							
							UserRoomRecord winuserRoomRec = dbService.selectUserRoomRecordInfoByID(playerInfo.getUserRoomRecordInfoID());
							
							ScoreRecordVO scoreRecordVO = new ScoreRecordVO();
							scoreRecordVO.setNickName(playerInfo.getUserInfo().getNickName());
							scoreRecordVO.setTotalScore(playerInfo.getCurScore()-1000);
							
							if(playerInfo.getUserInfo().getWeixinMark().equals(weixinId)){
								
								winPlayerInfo = playerInfo;
								updateUserRoomRecordInfo(winuserRoomRec,1,1,null);
								scoreRecordVO.setHuTimes(winuserRoomRec.getHuTimes()+1);
								scoreRecordVO.setLocation(playerInfo.getUserLocation());
								scoreRecordVO.setLoseTimes(winuserRoomRec.getLoseTimes());
								scoreRecordVO.setWinTimes(scoreRecordVO.getWinTimes()+1);
								scoreRecordVO.setWeixinId(playerInfo.getUserInfo().getWeixinMark());
								
							}else{
								if(playerInfo.getGatherScoreTypes().contains(GetScoreType.dianpao)){
									
									updateUserRoomRecordInfo(winuserRoomRec,0,0,-1);
									scoreRecordVO.setHuTimes(winuserRoomRec.getHuTimes());
									scoreRecordVO.setLocation(playerInfo.getUserLocation());
									scoreRecordVO.setLoseTimes(winuserRoomRec.getLoseTimes()+1);
									scoreRecordVO.setWinTimes(scoreRecordVO.getWinTimes());
									scoreRecordVO.setWeixinId(playerInfo.getUserInfo().getWeixinMark());
								}
							}
							
							
							String  getScoreTypes = HandlerHelper.getScoreTypesStr(playerInfo.getGatherScoreTypes());
							scoreRecordVO.setWinActionTypes(getScoreTypes);
							
							UserActionScore userActionScore = new UserActionScore();
							userActionScore.setActionScore(playerInfo.getCurScore());
							userActionScore.setWinActionTypes(getScoreTypes);
							userActionScore.setRoomRecordId(playingRoom.getRoomRecordID());
							userActionScore.setCreateTime(new Date());
							userActionScore.setRoundNum(playingRoom.getRoomNum());
							userActionScore.setUserId(playerInfo.getUserInfo().getId());
							
							dbService.insertUserActionScoreInfo(userActionScore);
							
							playScordRecords.add(scoreRecordVO);
							
						}
						
						currentRecordRespModel.setPlayScordRecords(playScordRecords);
						
						//赢牌不是庄，剩余局数减一
						if(gameResult.getZhuangLocation().getCode()!=winPlayerInfo.getUserLocation()){
							playingRoom.getRemaiRound().decrementAndGet();
						}
						
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
							gameContext.setZhuangLocation(PlayerLocation.fromCode(winPlayerInfo.getUserLocation()));
							
							MahjongTable table = new MahjongTable();
							table.init();
							
							MahjongTable mahjongTable = gameContext.getTable();
							table.setPlayerInfos(mahjongTable.getPlayerInfos());
							
							gameContext.setTable(table);
							
							roomContex.setAgreeNextRoundNum(new AtomicInteger(0));
							
							
						}
						
						for(Entry<PlayerLocation, PlayerInfo> playerInfoEnt : playingRoom.getGameContext().getTable().getPlayerInfos().entrySet()){
							
							String eachWeiXinId  = playerInfoEnt.getValue().getUserInfo().getWeixinMark();
							ChannelHandlerContext eachctx = ClientSession.sessionMap.get(eachWeiXinId);
							// 回写ACK
							protocolModel.setCommandId(EventEnum.THIS_RECORD_SCORE_RESP.getValue());
							protocolModel.setBody(JSON.toJSONString(currentRecordRespModel));
							eachctx.writeAndFlush(protocolModel);
							
							logger.error("胡返回数据：weixinId="+eachWeiXinId+",数据："+JSONObject.toJSONString(protocolModel));
							
						}
					}
				}
					
			}
		} else {
			ctx.fireChannelRead(protocolModel);
		}
	}
	
	
	private boolean updateUserRoomRecordInfo(UserRoomRecord winuserRoomRec, Integer huNum ,Integer winNum ,Integer loseNum){
		
		UserRoomRecord winuserRoomRecForUpdate = new UserRoomRecord();
		winuserRoomRecForUpdate.setId(winuserRoomRec.getId());
		
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
