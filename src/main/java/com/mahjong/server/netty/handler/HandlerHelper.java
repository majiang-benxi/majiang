package com.mahjong.server.netty.handler;

import static com.mahjong.server.game.action.standard.StandardActionType.BUGANG;
import static com.mahjong.server.game.action.standard.StandardActionType.CHI;
import static com.mahjong.server.game.action.standard.StandardActionType.PENG;
import static com.mahjong.server.game.action.standard.StandardActionType.WIN;
import static com.mahjong.server.game.action.standard.StandardActionType.ZIPAI;
import static com.mahjong.server.game.object.TileGroupType.BUGANG_GROUP;
import static com.mahjong.server.game.object.TileGroupType.CHI_GROUP;
import static com.mahjong.server.game.object.TileGroupType.HU_GROUP;
import static com.mahjong.server.game.object.TileGroupType.PENG_GROUP;
import static com.mahjong.server.game.object.TileGroupType.XUAN_FENG_GANG_DNXB_GROUP;
import static com.mahjong.server.game.object.TileGroupType.XUAN_FENG_GANG_ZFB_GROUP;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.exception.IllegalActionException;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.action.standard.CpgActionType;
import com.mahjong.server.game.action.standard.DrawActionType;
import com.mahjong.server.game.action.standard.DrawBottomActionType;
import com.mahjong.server.game.action.standard.WinActionType;
import com.mahjong.server.game.action.standard.ZiPaiActionType;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.enums.PlayerLocation.Relation;
import com.mahjong.server.game.object.DisCardActionAndLocation;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileGroupType;
import com.mahjong.server.netty.model.AskChoiceRespModel;
import com.mahjong.server.netty.model.DiscardReqModel;
import com.mahjong.server.netty.model.DiscardRespModel;
import com.mahjong.server.netty.model.DrawCardRespModel;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;

import io.netty.channel.ChannelHandlerContext;

public class HandlerHelper {
	/**
	 * 将消息周知给所有玩家
	 * 
	 * @param roomContex
	 * @param ignoreWinXinId,需要忽略的玩家id
	 * @param protocolModel
	 */
	public static void noticeMsg2Players(RoomContext roomContex, String ignoreWinXinId, ProtocolModel protocolModel) {
		for (PlayerInfo entry : roomContex.getGameContext().getTable().getPlayerInfos().values()) {
			UserInfo user = entry.getUserInfo();
			if (user != null && StringUtils.isNotBlank(ignoreWinXinId) && user.getWeixinMark().equals(ignoreWinXinId)) {
				continue;
			} else {
				String weixinId = user.getWeixinMark();
				ChannelHandlerContext userCtx = ClientSession.sessionMap.get(weixinId);
				userCtx.writeAndFlush(protocolModel);
			}
		}
	}

	/**
	 * 获取打出一张牌之后其他玩家可以做的操作
	 * 
	 * @param roomContext
	 * @param tile
	 * @param weixinId
	 * @return
	 */
	public static List<DisCardActionAndLocation> getActionAfterDiscardTile(RoomContext roomContext, Tile tile,
			PlayerLocation discardPlayLocation) {
		WinActionType winActionType = new WinActionType();
		List<PlayerLocation> playerLocations = discardPlayLocation.getOrderedNextLocations();
		List<DisCardActionAndLocation> disCardActionAndLocation = new ArrayList<DisCardActionAndLocation>();
		// 胡检测
		for (PlayerLocation playerLocation : playerLocations) {
			if (playerLocation == discardPlayLocation) {
				continue;
			}
			boolean canWin = winActionType.canDo(roomContext.getGameContext(), playerLocation);
			if (canWin) {
				disCardActionAndLocation.add(new DisCardActionAndLocation(new ActionAndLocation(new Action(WIN, tile), playerLocation),HU_GROUP.getCode()));
			}
		}
		// 杠检测
		for (PlayerLocation playerLocation : playerLocations) {
			if (playerLocation == discardPlayLocation) {
				continue;
			}
			CpgActionType cpgActionType = new CpgActionType(BUGANG_GROUP, null);
			boolean canGang = cpgActionType.canDo(roomContext.getGameContext(), playerLocation);
			if (canGang) {
				disCardActionAndLocation.add(new DisCardActionAndLocation(
						new ActionAndLocation(new Action(BUGANG, tile), playerLocation), BUGANG_GROUP.getCode()));
			}
		}
		// 碰检测
		for (PlayerLocation playerLocation : playerLocations) {
			if (playerLocation == discardPlayLocation) {
				continue;
			}
			CpgActionType cpgActionType = new CpgActionType(PENG_GROUP, null);
			boolean canPeng = cpgActionType.canDo(roomContext.getGameContext(), playerLocation);
			if (canPeng) {
				disCardActionAndLocation.add(new DisCardActionAndLocation(new ActionAndLocation(new Action(PENG, tile), playerLocation),PENG_GROUP.getCode()));
			}
		}
		// 吃检测
		CpgActionType cpgActionType = new CpgActionType(CHI_GROUP, null);
		boolean canChi = cpgActionType.canDo(roomContext.getGameContext(),
				discardPlayLocation.getLocationOf(Relation.NEXT));
		if (canChi) {
			disCardActionAndLocation.add(
					new DisCardActionAndLocation(new ActionAndLocation(new Action(CHI, tile), discardPlayLocation.getLocationOf(Relation.NEXT)),CHI_GROUP.getCode()));
		}
		return disCardActionAndLocation;
	}

	public static void askChoice2Player(RoomContext roomContext, List<DisCardActionAndLocation> actionAndLocations) {
		ProtocolModel canDoProtocolModel = new ProtocolModel();
		Multimap<PlayerLocation, Action> multiMap = groupByActionByLocation(actionAndLocations);
		for (PlayerLocation playerLocation : multiMap.keys()) {
			AskChoiceRespModel askChoiceRespModel = new AskChoiceRespModel(
					new ArrayList<Action>(multiMap.get(playerLocation)));
			canDoProtocolModel.setCommandId(EventEnum.ASK_CHOICE_RESP.getValue());
			canDoProtocolModel.setBody(JSON.toJSONString(askChoiceRespModel));
			String weixinMarkId = roomContext.getGameContext().getTable()
					.getPlayerByLocation(playerLocation).getUserInfo().getWeixinMark();
			ChannelHandlerContext ctx = ClientSession.sessionMap.get(weixinMarkId);
			ctx.writeAndFlush(canDoProtocolModel);
		}
	}

	public static Multimap<PlayerLocation, Action> groupByActionByLocation(
			List<DisCardActionAndLocation> actionAndLocations) {
		Multimap<PlayerLocation, Action> result = ArrayListMultimap.create();
		for (DisCardActionAndLocation actionAndLocation : actionAndLocations) {
			result.put(actionAndLocation.getActionAndLocation().getLocation(),
					actionAndLocation.getActionAndLocation().getAction());
		}
		return result;
	}

	public static void drawTile2Player(RoomContext roomContext, PlayerLocation playerLocation)
			throws IllegalActionException {
		DrawActionType drawActionType = new DrawActionType();
			drawActionType.doAction(roomContext.getGameContext(), playerLocation, new Action(drawActionType));
			ProtocolModel drawTileProtocolModel = new ProtocolModel();
		DrawCardRespModel drawCardRespModel = new DrawCardRespModel(roomContext, playerLocation);
			drawTileProtocolModel.setCommandId(EventEnum.DRAW_TILE_RESP.getValue());
			drawTileProtocolModel.setBody(JSON.toJSONString(drawCardRespModel));
			HandlerHelper.noticeMsg2Players(roomContext, null, drawTileProtocolModel);
	}

	public static void cpgProcess2Players(RoomContext roomContext, TileGroupType tileGroupType,
			Action action, PlayerLocation discardPlayLocation) throws IllegalActionException {
		CpgActionType cpgActionType = new CpgActionType(tileGroupType);
		cpgActionType.doAction(roomContext.getGameContext(), discardPlayLocation, action);
		if (tileGroupType == TileGroupType.BUGANG_GROUP) {
			DrawBottomActionType drawBottomActionType = new DrawBottomActionType();
			drawBottomActionType.doAction(roomContext.getGameContext(), discardPlayLocation, new Action(BUGANG));
		}
		ProtocolModel cpgProtocolModel = new ProtocolModel();
		DiscardRespModel discardRespModel = new DiscardRespModel(roomContext, discardPlayLocation);
		cpgProtocolModel.setCommandId(EventEnum.DISCARD_ONE_CARD_RESP.getValue());
		cpgProtocolModel.setBody(JSON.toJSONString(discardRespModel));
		HandlerHelper.noticeMsg2Players(roomContext, null, cpgProtocolModel);

	}

	public static void xfgProcess2Players(RoomContext roomContext, TileGroupType xuanFengGangGroup, Action action,
			PlayerLocation discardPlayLocation) throws IllegalActionException {
		ZiPaiActionType ziPaiActionType = new ZiPaiActionType();
		ziPaiActionType.doAction(roomContext.getGameContext(), discardPlayLocation, action);
		if (xuanFengGangGroup == TileGroupType.XUAN_FENG_GANG_ZFB_GROUP) {
			DrawBottomActionType drawBottomActionType = new DrawBottomActionType();
			drawBottomActionType.doAction(roomContext.getGameContext(), discardPlayLocation, new Action(BUGANG));
		}
		ProtocolModel xfgProtocolModel = new ProtocolModel();
		DiscardRespModel discardRespModel = new DiscardRespModel(roomContext, discardPlayLocation);
		xfgProtocolModel.setCommandId(EventEnum.DISCARD_ONE_CARD_RESP.getValue());
		xfgProtocolModel.setBody(JSON.toJSONString(discardRespModel));
		HandlerHelper.noticeMsg2Players(roomContext, null, xfgProtocolModel);

	}

	public static void processDiscardResp(RoomContext roomContext, PlayerLocation discardPlayLocation,
			DiscardReqModel discardReqModel) throws IllegalActionException {
		List<DisCardActionAndLocation> needPassOrDoActions = roomContext.getGameContext().getDiscardContext()
				.getNeedPassOrDoAction();
		if (!needPassOrDoActions.isEmpty()) {
			Multimap<PlayerLocation, Action> multimap=groupByActionByLocation(needPassOrDoActions);
			DisCardActionAndLocation actionAndLocation=needPassOrDoActions.get(0);
			if(actionAndLocation.getActionAndLocation().getLocation()==discardPlayLocation){
				if(multimap.keys().size()==1){
					doDiscardResp(roomContext);
				}else{
					if(actionAndLocation.getTileGroupType()==discardReqModel.getTileGroupType()){
						doDiscardResp(roomContext);
					}else{
						removeNotChooseButHighLevelAction(needPassOrDoActions, new DisCardActionAndLocation(
								new ActionAndLocation(null, discardPlayLocation), discardReqModel.getTileGroupType()));
					}
				}
			} else {
				removeNotChooseButHighLevelAction(needPassOrDoActions, actionAndLocation);
			}
			roomContext.getGameContext().getDiscardContext().releaseRemainVoter();
			if (roomContext.getGameContext().getDiscardContext().getRemainVoter() == 0) {
				doDiscardResp(roomContext);
			}
		} else {
			HandlerHelper.drawTile2Player(roomContext,
					discardPlayLocation.getLocationOf(Relation.NEXT));
		}
		
	}

	private static void removeNotChooseButHighLevelAction(List<DisCardActionAndLocation> needPassOrDoActions,
			DisCardActionAndLocation actionAndLocation) {
		int currentIndex = needPassOrDoActions.size();
		for (int i = 0; i < needPassOrDoActions.size(); i++) {
			if (needPassOrDoActions.get(i).getActionAndLocation().getLocation() != actionAndLocation
					.getActionAndLocation().getLocation()) {
				continue;
			} else {
				if (needPassOrDoActions.get(i).getTileGroupType() == actionAndLocation.getTileGroupType()) {
					currentIndex = i;
				}else{
					if (i < currentIndex) {
						needPassOrDoActions.remove(i);// 移除优先级高的，用户却没有选择的
					}
				}
			}
		}

	}

	private static void doDiscardResp(RoomContext roomContext) throws IllegalActionException {
		List<DisCardActionAndLocation> needPassOrDoActions = roomContext.getGameContext().getDiscardContext()
				.getNeedPassOrDoAction();
		DisCardActionAndLocation disCardActionAndLocation = needPassOrDoActions.get(0);
		if (disCardActionAndLocation.getTileGroupType() == TileGroupType.CHI_GROUP.getCode()) {
			HandlerHelper.cpgProcess2Players(roomContext, PENG_GROUP,
					new Action(CHI, disCardActionAndLocation.getActionAndLocation().getAction().getTile()),
					disCardActionAndLocation.getActionAndLocation().getLocation());
		} else if (disCardActionAndLocation.getTileGroupType() == TileGroupType.PENG_GROUP.getCode()) {
			HandlerHelper.cpgProcess2Players(roomContext, PENG_GROUP,
					new Action(PENG, disCardActionAndLocation.getActionAndLocation().getAction().getTile()),
					disCardActionAndLocation.getActionAndLocation().getLocation());
		} else if (disCardActionAndLocation.getTileGroupType() == TileGroupType.BUGANG_GROUP.getCode()) {
			HandlerHelper.cpgProcess2Players(roomContext, PENG_GROUP,
					new Action(BUGANG, disCardActionAndLocation.getActionAndLocation().getAction().getTile()),
					disCardActionAndLocation.getActionAndLocation().getLocation());
		} else if (disCardActionAndLocation.getTileGroupType() == TileGroupType.XUAN_FENG_GANG_ZFB_GROUP.getCode()) {
			HandlerHelper.xfgProcess2Players(roomContext, XUAN_FENG_GANG_ZFB_GROUP,
					new Action(ZIPAI, disCardActionAndLocation.getActionAndLocation().getAction().getTile()),
					disCardActionAndLocation.getActionAndLocation().getLocation());
		} else if (disCardActionAndLocation.getTileGroupType() == TileGroupType.XUAN_FENG_GANG_DNXB_GROUP.getCode()) {
			HandlerHelper.xfgProcess2Players(roomContext, XUAN_FENG_GANG_DNXB_GROUP,
					new Action(ZIPAI, disCardActionAndLocation.getActionAndLocation().getAction().getTile()),
					disCardActionAndLocation.getActionAndLocation().getLocation());
		} else if (disCardActionAndLocation.getTileGroupType() == TileGroupType.HU_GROUP.getCode()) {
			roomContext.getGameContext().getDiscardContext().clear();
			WinActionType winActionType = new WinActionType();
			winActionType.doAction(roomContext.getGameContext(),
					disCardActionAndLocation.getActionAndLocation().getLocation(),
					new Action(WIN, disCardActionAndLocation.getActionAndLocation().getAction().getTile()));
			ProtocolModel winProtocolModel = new ProtocolModel();
			DiscardRespModel discardRespModel = new DiscardRespModel(roomContext,
					disCardActionAndLocation.getActionAndLocation().getLocation());
			winProtocolModel.setCommandId(EventEnum.DISCARD_ONE_CARD_RESP.getValue());
			winProtocolModel.setBody(JSON.toJSONString(discardRespModel));
			HandlerHelper.noticeMsg2Players(roomContext, null, winProtocolModel);
			// TODO 战绩
		} else if (disCardActionAndLocation.getTileGroupType() == TileGroupType.PASS_GROUP.getCode()) {
			ActionAndLocation lastActionAndLocation = roomContext.getGameContext().getLastActionAndLocation();
			roomContext.getGameContext().getDiscardContext().clear();
				// 执行发牌
			HandlerHelper.drawTile2Player(roomContext,
						lastActionAndLocation.getLocation().getLocationOf(Relation.NEXT));
			}
		}

}
