package com.mahjong.server.netty.handler;

import static com.mahjong.server.game.action.standard.StandardActionType.ANGANG;
import static com.mahjong.server.game.action.standard.StandardActionType.WIN;
import static com.mahjong.server.game.action.standard.StandardActionType.ZIPAI;
import static com.mahjong.server.game.object.TileGroupType.HU_GROUP;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.exception.IllegalActionException;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.standard.AngangActionType;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.DrawTileContext;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileGroupType;
import com.mahjong.server.netty.model.DiscardReqModel;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;
import com.mahjong.server.service.DBService;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
@Sharable
@Component
public class DrawLogicHandler  extends SimpleChannelInboundHandler<ProtocolModel> {

	private static final Logger logger = LoggerFactory.getLogger(EnterRoomHandler.class);

	@Autowired
	private DBService dbService;
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
		if (protocolModel.getCommandId() == EventEnum.DRAW_CHOOSE_CLIENT_RESP.getValue()) {
			if (protocolModel.getBody() == null) {
				ctx.close();
			} else {
				DiscardReqModel discardReqModel = JSON.parseObject(protocolModel.getBody(),
						new TypeReference<DiscardReqModel>() {
						});
				String weixinId = discardReqModel.getWeiXinId();
				ctx = ClientSession.sessionMap.get(weixinId);
				RoomContext roomContext = HouseContext.weixinIdToRoom.get(weixinId);
				PlayerLocation playLocation = null;
				
				for (Entry<PlayerLocation, PlayerInfo> entry : roomContext.getGameContext().getTable().getPlayerInfos()
						.entrySet()) {
					if (weixinId.equals(entry.getValue().getUserInfo().getWeixinMark())) {
						playLocation = entry.getKey();
						break;
					}
				}
				roomContext.getGameContext().getTable().printAllPlayTiles();

				try {
					DrawTileContext drawTileContext=	roomContext.getGameContext().getTable().getPlayerByLocation(playLocation).getDrawTileContext();
					if(drawTileContext==null||drawTileContext.getCanDoActions().isEmpty()){
						throw new IllegalActionException(roomContext.getGameContext(),playLocation,null);
					}
					if(discardReqModel.getTileGroupType()==TileGroupType.HU_GROUP.getCode()){
						HandlerHelper.huProcess2Players(roomContext, HU_GROUP,  new Action(WIN, roomContext.getGameContext().getTable().getPlayerByLocation(playLocation).getLastDrawedTile()),playLocation);//自摸胡
						HuProcessHelper.dealHu(dbService,discardReqModel,  protocolModel, ctx);
					}else{
						if(discardReqModel.getTileGroupType()==TileGroupType.XUAN_FENG_GANG_DNXB_GROUP.getCode()||discardReqModel.getTileGroupType()==TileGroupType.XUAN_FENG_GANG_ZFB_GROUP.getCode()){
							if(drawTileContext.containsAction(ZIPAI)){
								HandlerHelper.xfgProcess2Players(roomContext, discardReqModel.getTileGroupType(), new Action(ZIPAI, discardReqModel.getTile()), playLocation);
							}
						}else if(discardReqModel.getTileGroupType()==TileGroupType.ANGANG_GROUP.getCode()){
							HandlerHelper.anGangProcess2Players(roomContext, playLocation, discardReqModel.getTile());
						}else if(discardReqModel.getTileGroupType()==TileGroupType.PASS_GROUP.getCode()){
							drawTileContext.resetForNextUse(playLocation);
							//do nothing ,waiting palyer discard tile
						}else{
							throw new IllegalActionException(roomContext.getGameContext(),playLocation,null);
						}
					}
				} catch (IllegalActionException e) {
					roomContext.getGameContext().getTable().printAllPlayTiles();
					ProtocolModel illegalProtocolModel = new ProtocolModel();
					illegalProtocolModel.setCommandId(EventEnum.ILLEGAL_ACTION_RESP.getValue());
					illegalProtocolModel.setBody(null);
					ctx.writeAndFlush(illegalProtocolModel);
					
					logger.error("DrawLogicHandler主逻辑返回数据："+JSONObject.toJSONString(illegalProtocolModel));
				}
			}

		} else {
			ctx.fireChannelRead(protocolModel);
		}
	}
}

