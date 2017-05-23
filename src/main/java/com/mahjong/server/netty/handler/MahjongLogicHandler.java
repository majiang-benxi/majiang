package com.mahjong.server.netty.handler;

import static com.mahjong.server.game.action.standard.StandardActionType.BUGANG;
import static com.mahjong.server.game.action.standard.StandardActionType.CHI;
import static com.mahjong.server.game.action.standard.StandardActionType.PENG;
import static com.mahjong.server.game.action.standard.StandardActionType.WIN;
import static com.mahjong.server.game.object.TileGroupType.PENG_GROUP;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.action.standard.DiscardActionType;
import com.mahjong.server.game.action.standard.WinActionType;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.enums.PlayerLocation.Relation;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.TileGroupType;
import com.mahjong.server.netty.model.DiscardReqModel;
import com.mahjong.server.netty.model.DiscardRespModel;
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
						HandlerHelper.drawTile2Player(roomContext, discardPlayLocation.getLocationOf(Relation.NEXT));
					} else {// 按照权重询问第一个玩家
						roomContext.getGameContext().setNeedPassOrDoAction(actionAndLocations);
						ActionAndLocation actionAndLocation = actionAndLocations.get(0);
						HandlerHelper.askChoice2Player(roomContext, actionAndLocation);
					}
				}else if(discardReqModel.getTileGroupType()==TileGroupType.CHI_GROUP.getCode())	{
					HandlerHelper.cpgProcess2Players(roomContext, PENG_GROUP,
							new Action(CHI, discardReqModel.getTile()), discardPlayLocation);
				}else if(discardReqModel.getTileGroupType()==TileGroupType.PENG_GROUP.getCode())	{
					HandlerHelper.cpgProcess2Players(roomContext, PENG_GROUP,
							new Action(PENG, discardReqModel.getTile()), discardPlayLocation);
				}else if(discardReqModel.getTileGroupType()==TileGroupType.BUGANG_GROUP.getCode())	{
					HandlerHelper.cpgProcess2Players(roomContext, PENG_GROUP,
							new Action(BUGANG, discardReqModel.getTile()), discardPlayLocation);
				}else if(discardReqModel.getTileGroupType()==TileGroupType.HU_GROUP.getCode())	{
					List<ActionAndLocation> needPassOrDoActions = roomContext.getGameContext().getNeedPassOrDoAction();
					if (!needPassOrDoActions.isEmpty()) {
						needPassOrDoActions.clear();
					}
					WinActionType winActionType = new WinActionType();
					winActionType.doAction(roomContext.getGameContext(), discardPlayLocation,
							new Action(WIN, discardReqModel.getTile()));
					ProtocolModel winProtocolModel = new ProtocolModel();
					DiscardRespModel discardRespModel = new DiscardRespModel(roomContext);
					winProtocolModel.setCommandId(EventEnum.DISCARD_ONE_CARD_RESP.getValue());
					winProtocolModel.setBody(JSON.toJSONString(discardRespModel));
					HandlerHelper.noticeMsg2Players(roomContext, null, winProtocolModel);
				}else if(discardReqModel.getTileGroupType()==TileGroupType.PASS_GROUP.getCode())	{
					List<ActionAndLocation>needPassOrDoActions=	roomContext.getGameContext().getNeedPassOrDoAction();
					needPassOrDoActions.remove(0);//移除询问
					ActionAndLocation actionAndLocation = needPassOrDoActions.get(0);
					if (actionAndLocation != null) {
						HandlerHelper.askChoice2Player(roomContext, actionAndLocation);
					} else {
						// 执行发牌
						ActionAndLocation lastActionAndLocation = roomContext.getGameContext()
								.getLastActionAndLocation();
						HandlerHelper.drawTile2Player(roomContext,
								lastActionAndLocation.getLocation().getLocationOf(Relation.NEXT));
					}
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
