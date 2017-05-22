package com.mahjong.server.netty.handler;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.action.standard.DiscardActionType;
import com.mahjong.server.game.action.standard.DrawActionType;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.enums.PlayerLocation.Relation;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.TileGroupType;
import com.mahjong.server.netty.model.DiscardReqModel;
import com.mahjong.server.netty.model.DiscardRespModel;
import com.mahjong.server.netty.model.DrawCardRespModel;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
@Component
public class MahjongLogicHandler extends SimpleChannelInboundHandler<ProtocolModel> {
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
				if(discardReqModel.getTileGroupType()==TileGroupType.ONE_GROUP.getCode()){
					// 剩余玩家吃碰杠胡检测,如果其他玩家可以吃碰杠胡的时候，按照优先级逐个通知处理
					DiscardActionType discardActionType = new DiscardActionType();
					discardActionType.doAction(roomContext.getGameContext(), discardPlayLocation,
							new Action(discardActionType, discardReqModel.getTile()));
					List<ActionAndLocation> actionAndLocations = HandlerHelper.getActionAfterDiscardTile(roomContext,
							discardReqModel.getTile(), discardPlayLocation);
					if (actionAndLocations.isEmpty()) {// 所有下家对这个打的牌没有可以操作的动作后就继续给下家发牌
						DrawActionType drawActionType=new DrawActionType();
						drawActionType.doAction(roomContext.getGameContext(),
								discardPlayLocation.getLocationOf(Relation.NEXT), new Action(discardActionType));
						ProtocolModel drawTileProtocolModel = new ProtocolModel();

						DrawCardRespModel drawCardRespModel=new DrawCardRespModel(discardPlayLocation.getLocationOf(Relation.NEXT));
						drawTileProtocolModel.setCommandId(EventEnum.DRAW_TILE_RESP.getValue());
						drawTileProtocolModel.setBody(JSON.toJSONString(drawCardRespModel));
						HandlerHelper.noticeMsg2Players(roomContext, null, drawTileProtocolModel);
					}
				}else if(discardReqModel.getTileGroupType()==TileGroupType.CHI_GROUP.getCode())	{
					
				}else if(discardReqModel.getTileGroupType()==TileGroupType.PENG_GROUP.getCode())	{
					
				}else if(discardReqModel.getTileGroupType()==TileGroupType.BUGANG_GROUP.getCode())	{
					
				}else if(discardReqModel.getTileGroupType()==TileGroupType.HU_GROUP.getCode())	{
					
				}
				
					//TODO 战绩
					
					DiscardRespModel discardRespModel = new DiscardRespModel();
					// 回写ACK
					protocolModel.setCommandId(EventEnum.DISCARD_ONE_CARD_RESP.getValue());
					protocolModel.setBody(JSON.toJSONString(discardRespModel));
					ctx.writeAndFlush(protocolModel);

			}
		} else {
			ctx.fireChannelRead(protocolModel);
		}
	}
}
