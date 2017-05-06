package com.mahjong.server.netty.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.entity.UserActionScore;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.entity.UserRoomRecord;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.TileGroup;
import com.mahjong.server.game.object.TileGroupType;
import com.mahjong.server.netty.model.CurrentRecordRespModel;
import com.mahjong.server.netty.model.DiscardReqModel;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;
import com.mahjong.server.service.DBService;
import com.mahjong.server.vo.ScoreRecordVO;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Component
public class ToWinHandler extends SimpleChannelInboundHandler<ProtocolModel> {
	
	@Autowired
	private DBService dbService;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel)
			throws Exception {
		if (protocolModel.getCommandId() == EventEnum.DISCARD_ONE_CARD_REQ.getValue()) {
			if (protocolModel.getBody() == null) {
				ctx.close();
			} else {
				DiscardReqModel discardReqModel = JSON.parseObject(new String(protocolModel.getBody(), "UTF-8"),
						new TypeReference<DiscardReqModel>() {
						});
				
				if(discardReqModel.getTileGroupType()==TileGroupType.HU_GROUP.getCode()){
					
					String weixinId = discardReqModel.getWeiXinId();
					RoomContext playingRoom = HouseContext.weixinIdToRoom.get(weixinId);
					
					CurrentRecordRespModel currentRecordRespModel = new CurrentRecordRespModel();
					
					List<ScoreRecordVO> playScordRecords = new ArrayList<ScoreRecordVO>();
					
					for(Entry<PlayerLocation, PlayerInfo> playerInfoEnt : playingRoom.getGameContext().getTable().getPlayerInfos().entrySet()){
						
						PlayerInfo playerInfo = playerInfoEnt.getValue();
						
						String eachWeiXinId  = playerInfo.getUserInfo().getWeixinMark();
						
						UserInfo userInfo = dbService.selectUserInfoByWeiXinMark(eachWeiXinId);
						UserRoomRecord userRoomRecord = null;
						List<UserRoomRecord> userRoomRecordLists = dbService.selectLatestUserRoomRecordInfo(userInfo.getId(),1);
						if(CollectionUtils.isNotEmpty(userRoomRecordLists)){
							userRoomRecord = userRoomRecordLists.get(0);
						}
						
						List<UserActionScore> userActionScores = new ArrayList<UserActionScore>();
						
						for(TileGroup tileGroup :	playerInfo.getTileGroups()){
							UserActionScore userActionScore = new UserActionScore();
							userActionScore.setActionScore(tileGroup.getTileGroupTypeScore());
							userActionScore.setActionType(tileGroup.getType().getCode());
							userActionScore.setUserRoomRecordId(userRoomRecord.getId());
							dbService.insertUserActionScoreInfo(userActionScore);
							userActionScores.add(userActionScore);
						}
						
						
						ScoreRecordVO scoreRecordVO = new ScoreRecordVO();
						scoreRecordVO.setNickName(playerInfoEnt.getValue().getUserInfo().getNickName());
						scoreRecordVO.setTotalScore(playerInfoEnt.getValue().getCurScore());
						
						scoreRecordVO.setUserActionScores(userActionScores);
						
						playScordRecords.add(scoreRecordVO);
						
						
						
					}
					currentRecordRespModel.setPlayScordRecords(playScordRecords);
					
					
					
					for(Entry<PlayerLocation, PlayerInfo> playerInfoEnt : playingRoom.getGameContext().getTable().getPlayerInfos().entrySet()){
						String eachWeiXinId  = playerInfoEnt.getValue().getUserInfo().getWeixinMark();
						ChannelHandlerContext eachctx = ClientSession.sessionMap.get(eachWeiXinId);
						// 回写ACK
						protocolModel.setCommandId(EventEnum.THIS_RECORD_SCORE_RESP.getValue());
						protocolModel.setBody(JSON.toJSONString(currentRecordRespModel).getBytes("UTF-8"));
						eachctx.writeAndFlush(protocolModel);
						
					}
				}
				
			}
		} else {
			ctx.fireChannelRead(protocolModel);
		}
	}
}
