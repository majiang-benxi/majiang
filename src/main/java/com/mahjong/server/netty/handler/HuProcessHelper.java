package com.mahjong.server.netty.handler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
import com.mahjong.server.game.enums.PlayerLocation.Relation;
import com.mahjong.server.game.object.GameResult;
import com.mahjong.server.game.object.GetScoreType;
import com.mahjong.server.game.object.MahjongTable;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.TileGroupType;
import com.mahjong.server.netty.model.DiscardReqModel;
import com.mahjong.server.netty.model.DiscardRespModel;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;
import com.mahjong.server.service.DBService;
import com.mahjong.server.vo.ScoreRecordVO;

import io.netty.channel.ChannelHandlerContext;

public class HuProcessHelper {
	private static final Logger logger = LoggerFactory.getLogger(EnterRoomHandler.class);

	public static void dealHu(DBService dbService, DiscardReqModel discardReqModel, ProtocolModel protocolModel,
			ChannelHandlerContext ctx) {

		if (discardReqModel.getTileGroupType() == TileGroupType.HU_GROUP.getCode()) {

			String weixinId = discardReqModel.getWeiXinId();
			RoomContext playingRoom = HouseContext.weixinIdToRoom.get(weixinId);
			UserInfo userInfo = HouseContext.weixinIdToUserInfo.get(weixinId);

			if (userInfo != null && ctx != null) {

				GameResult gameResult = playingRoom.getGameContext().getGameResult();

				PlayerInfo winPlayerInfo = null;

				for (Entry<PlayerLocation, PlayerInfo> playerInfoEnt : gameResult.getPlayerInfos().entrySet()) {

					PlayerInfo playerInfo = playerInfoEnt.getValue();

					playerInfo.setTotalscore(playerInfo.getTotalscore() + (playerInfo.getCurScore() - 1000));

					UserRoomRecord winuserRoomRec = dbService
							.selectUserRoomRecordInfoByID(playerInfo.getUserRoomRecordInfoID());

					if (playerInfo.getUserInfo().getWeixinMark().equals(weixinId)) {

						winPlayerInfo = playerInfo;
						updateUserRoomRecordInfo(dbService, winuserRoomRec, 1, 1, null);

						playerInfo.setHuTimes(winuserRoomRec.getHuTimes() + 1);

					} else {
						if (playerInfo.getGatherScoreTypes().contains(GetScoreType.dianpao)) {

							updateUserRoomRecordInfo(dbService, winuserRoomRec, 0, 0, -1);
							playerInfo.setDianpaotimes(playerInfo.getDianpaotimes() + 1);

						}
					}

					ScoreRecordVO scoreRecordVO = new ScoreRecordVO();
					scoreRecordVO.setRoundScore(playerInfo.getCurScore() - 1000);

					String getScoreTypes = HandlerHelper.getScoreTypesStr(playerInfo.getGatherScoreTypes());
					scoreRecordVO.setWinActionTypes(getScoreTypes);

					UserActionScore userActionScore = new UserActionScore();
					userActionScore.setActionScore(playerInfo.getCurScore() - 1000);
					userActionScore.setWinActionTypes(getScoreTypes);
					userActionScore.setRoomRecordId(playingRoom.getRoomRecordID());
					userActionScore.setCreateTime(new Date());
					int totalroundNum = playingRoom.getGameContext().getGameStrategy().getRuleInfo().getFangKa().getCode()==1?16:32;
					userActionScore.setRoundNum(totalroundNum-playingRoom.getRemaiRound().get());
					userActionScore.setUserId(playerInfo.getUserInfo().getId());

					dbService.insertUserActionScoreInfo(userActionScore);

					playerInfo.setCurScoreRecord(scoreRecordVO);

				}

				// 赢牌不是庄，剩余局数减一
				if (gameResult.getZhuangLocation().getCode() != winPlayerInfo.getUserLocation()) {
					playingRoom.getRemaiRound().decrementAndGet();
				}

				if (playingRoom.getRemaiRound().get() == 0) {

					RoomContext roomContex = HouseContext.weixinIdToRoom.get(weixinId);

					HouseContext.rommList.remove(roomContex.getRoomNum());

					HouseContext.playRoomNum.decrementAndGet();
					HouseContext.playUserNum.addAndGet(-4);

					for (Entry<PlayerLocation, PlayerInfo> ent : roomContex.getGameContext().getTable().getPlayerInfos()
							.entrySet()) {

						PlayerInfo playerInfo = ent.getValue();
						HouseContext.weixinIdToRoom.remove(playerInfo.getUserInfo().getWeixinMark());

						UserRoomRecord userRoomRecord = new UserRoomRecord();

						if (playerInfo.getUserLocation().intValue() == PlayerLocation.NORTH.getCode()) {
							userRoomRecord.setUserDirection((byte) 4);

						} else if (playerInfo.getUserLocation().intValue() == PlayerLocation.WEST.getCode()) {
							userRoomRecord.setUserDirection((byte) 3);

						} else if (playerInfo.getUserLocation().intValue() == PlayerLocation.SOUTH.getCode()) {
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

					for (PlayerInfo entry : roomContex.getGameContext().getTable().getPlayerInfos().values()) {
						UserInfo user = entry.getUserInfo();
						if (user != null) {

							if (entry.getUserLocation() == winPlayerInfo.getUserLocation().intValue()) {
								entry.setLastTileGroupAction(TileGroupType.HU_GROUP.getCode());// 把当前的动作告诉所有玩家
							}

							ProtocolModel winProtocolModel = new ProtocolModel();
							DiscardRespModel discardRespModel = new DiscardRespModel(playingRoom,
									PlayerLocation.fromCode(entry.getUserLocation()), true);
							winProtocolModel.setCommandId(EventEnum.WIN_LAST_TIME_RESP.getValue());
							winProtocolModel.setBody(JSON.toJSONString(discardRespModel,
									SerializerFeature.DisableCircularReferenceDetect));

							String weixinIde = user.getWeixinMark();
							ChannelHandlerContext userCtx = ClientSession.sessionMap.get(weixinIde);
							
							HandlerHelper.noticeMsg2Player(userCtx, entry, winProtocolModel);
							
							logger.error(
									"返回数据：weixinId=" + weixinId + ",数据：" + JSONObject.toJSONString(winProtocolModel));

						}
					}

				} else {

					RoomContext roomContex = HouseContext.weixinIdToRoom.get(weixinId);
					GameContext gameContext = roomContex.getGameContext();
					
					PlayerInfo zhuangplayerInfo = null;

					for (PlayerInfo eplayerInfo : roomContex.getGameContext().getTable().getPlayerInfos().values()) {

						if (eplayerInfo == null) {
							continue;
						}
						
						if(gameContext.getZhuangLocation().getCode() == eplayerInfo.getUserLocation()){
							zhuangplayerInfo = eplayerInfo;
						}

						UserInfo user = eplayerInfo.getUserInfo();
						if (user != null) {

							ProtocolModel winProtocolModel = new ProtocolModel();
							DiscardRespModel discardRespModel = new DiscardRespModel(playingRoom,
									PlayerLocation.fromCode(eplayerInfo.getUserLocation()), true);
							winProtocolModel.setCommandId(EventEnum.WIN_ONE_TIME_RESP.getValue());
							winProtocolModel.setBody(JSON.toJSONString(discardRespModel,
									SerializerFeature.DisableCircularReferenceDetect));

							String weixinIde = user.getWeixinMark();
							ChannelHandlerContext userCtx = ClientSession.sessionMap.get(weixinIde);
							
							HandlerHelper.noticeMsg2Player(userCtx, eplayerInfo, winProtocolModel);
							
							logger.error(
									"返回数据：weixinId=" + weixinId + ",数据：" + JSONObject.toJSONString(winProtocolModel));

						}
					}


					gameContext.setDiscardContext(null);
					gameContext.setGameResult(null);
					gameContext.setHuangzhuang(false);
					gameContext.setLocalDoneActions(new ArrayList<ActionAndLocation>());
					
					if(gameContext.getZhuangLocation().getCode() == winPlayerInfo.getUserLocation()){
						// 玩家坐庄次数加一
						winPlayerInfo.setZhuangTimes(winPlayerInfo.getZhuangTimes() + 1);
						
					}else{
						gameContext.setZhuangLocation(PlayerLocation.fromCode(zhuangplayerInfo.getUserLocation()).getLocationOf(Relation.PREVIOUS));
					}
					

					MahjongTable table = new MahjongTable();
					table.init();

					MahjongTable mahjongTable = gameContext.getTable();

					for (PlayerInfo eplayerInfo : mahjongTable.getPlayerInfos().values()) {

						if (eplayerInfo == null) {
							continue;
						}
						if(gameContext.getZhuangLocation().getCode() == eplayerInfo.getUserLocation()){
							eplayerInfo.setZhuang(true);
						} else {
							eplayerInfo.setZhuang(false);
						}
						eplayerInfo.getDrawTileContext().init();
					}

					table.setPlayerInfos(mahjongTable.getPlayerInfos());

					gameContext.setTable(table);

					roomContex.setAgreeNextRoundNum(new AtomicInteger(0));

				}

			}
		}
	}

	private static boolean updateUserRoomRecordInfo(DBService dbService, UserRoomRecord winuserRoomRec, Integer huNum,
			Integer winNum, Integer loseNum) {

		UserRoomRecord winuserRoomRecForUpdate = new UserRoomRecord();
		winuserRoomRecForUpdate.setId(winuserRoomRec.getId());

		if (huNum != null && huNum != 0) {

			int temp = winuserRoomRec.getHuTimes() == null ? 0 : winuserRoomRec.getHuTimes();

			winuserRoomRecForUpdate.setHuTimes(temp + huNum);
		}

		if (winNum != null && winNum != 0) {
			int temp = winuserRoomRec.getWinTimes() == null ? 0 : winuserRoomRec.getWinTimes();
			winuserRoomRecForUpdate.setWinTimes(temp + winNum);
		}

		if (loseNum != null && loseNum != 0) {
			int temp = winuserRoomRec.getLoseTimes() == null ? 0 : winuserRoomRec.getLoseTimes();
			winuserRoomRecForUpdate.setLoseTimes(temp + loseNum);
		}

		dbService.updateUserRoomRecordInfoPrimaryKey(winuserRoomRecForUpdate);
		return true;
	}
}
