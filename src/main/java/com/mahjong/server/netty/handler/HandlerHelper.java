package com.mahjong.server.netty.handler;

import static com.mahjong.server.game.action.standard.StandardActionType.ANGANG;
import static com.mahjong.server.game.action.standard.StandardActionType.BUGANG;
import static com.mahjong.server.game.action.standard.StandardActionType.CHI;
import static com.mahjong.server.game.action.standard.StandardActionType.DRAW_BOTTOM;
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
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mahjong.server.entity.RoomRecord;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.exception.IllegalActionException;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.action.standard.AngangActionType;
import com.mahjong.server.game.action.standard.CpgActionType;
import com.mahjong.server.game.action.standard.DealActionType;
import com.mahjong.server.game.action.standard.DrawActionType;
import com.mahjong.server.game.action.standard.DrawBottomActionType;
import com.mahjong.server.game.action.standard.WinActionType;
import com.mahjong.server.game.action.standard.ZiPaiActionType;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.enums.PlayerLocation.Relation;
import com.mahjong.server.game.enums.RoomStatus;
import com.mahjong.server.game.object.DisCardActionAndLocation;
import com.mahjong.server.game.object.DrawTileContext;
import com.mahjong.server.game.object.GetScoreType;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileGroupType;
import com.mahjong.server.game.rule.PlayRule;
import com.mahjong.server.netty.model.AskChoiceRespModel;
import com.mahjong.server.netty.model.DiscardReqModel;
import com.mahjong.server.netty.model.DiscardRespModel;
import com.mahjong.server.netty.model.DrawCardRespModel;
import com.mahjong.server.netty.model.EnterRoomRespModel;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;
import com.mahjong.server.service.DBService;

import io.netty.channel.ChannelHandlerContext;

public class HandlerHelper {

	private static final Logger logger = LoggerFactory.getLogger(HandlerHelper.class);

	/**
	 * 将消息周知给所有玩家
	 * 
	 * @param roomContexs
	 * @param ignoreWinXinId,需要忽略的玩家id
	 * @param protocolModel
	 */
	public static void noticeMsg2Players(RoomContext roomContex, String ignoreWinXinId, ProtocolModel protocolModel) {
		for (PlayerInfo entry : roomContex.getGameContext().getTable().getPlayerInfos().values()) {
			UserInfo user = entry.getUserInfo();
			if (user != null) {
				if (StringUtils.isNotBlank(ignoreWinXinId) && user.getWeixinMark().equals(ignoreWinXinId)) {
					continue;
				} else {
					String weixinId = user.getWeixinMark();
					ChannelHandlerContext userCtx = ClientSession.sessionMap.get(weixinId);
					userCtx.writeAndFlush(protocolModel);
					logger.error("返回数据：weixinId=" + weixinId + ",数据：" + JSONObject.toJSONString(protocolModel));
				}
			}
		}
	}

	public static void noticeMsg2Player(RoomContext roomContex, PlayerInfo playerInfo, ProtocolModel protocolModel) {
		UserInfo user = playerInfo.getUserInfo();
		if (user != null) {
			String weixinId = user.getWeixinMark();
			ChannelHandlerContext userCtx = ClientSession.sessionMap.get(weixinId);
			userCtx.writeAndFlush(protocolModel);
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
				disCardActionAndLocation.add(new DisCardActionAndLocation(
						new ActionAndLocation(new Action(WIN, tile), playerLocation), HU_GROUP.getCode()));
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
				disCardActionAndLocation.add(new DisCardActionAndLocation(
						new ActionAndLocation(new Action(PENG, tile), playerLocation), PENG_GROUP.getCode()));
			}
		}
		// 吃检测
		CpgActionType cpgActionType = new CpgActionType(CHI_GROUP, null);
		boolean canChi = cpgActionType.canDo(roomContext.getGameContext(),
				discardPlayLocation.getLocationOf(Relation.NEXT));
		if (canChi) {
			disCardActionAndLocation.add(new DisCardActionAndLocation(
					new ActionAndLocation(new Action(CHI, tile), discardPlayLocation.getLocationOf(Relation.NEXT)),
					CHI_GROUP.getCode()));
		}
		return disCardActionAndLocation;
	}

	public static void askChoice2Player(RoomContext roomContext, List<DisCardActionAndLocation> actionAndLocations) {
		ProtocolModel canDoProtocolModel = new ProtocolModel();
		Multimap<PlayerLocation, Action> multiMap = groupByActionByLocation(actionAndLocations);
		for (PlayerLocation playerLocation : multiMap.keySet()) {
			AskChoiceRespModel askChoiceRespModel = new AskChoiceRespModel(
					new ArrayList<Action>(multiMap.get(playerLocation)));
			canDoProtocolModel.setCommandId(EventEnum.DISCARD_ASK_CHOICE_RESP.getValue());
			canDoProtocolModel.setBody(JSON.toJSONString(askChoiceRespModel,SerializerFeature.DisableCircularReferenceDetect));
			String weixinMarkId = roomContext.getGameContext().getTable().getPlayerByLocation(playerLocation)
					.getUserInfo().getWeixinMark();
			ChannelHandlerContext ctx = ClientSession.sessionMap.get(weixinMarkId);
			ctx.writeAndFlush(canDoProtocolModel);
			logger.info("返回数据：" + JSONObject.toJSONString(canDoProtocolModel));
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
		int remainTileNum = roomContext.getGameContext().getTable().getRemainderTileNum().get();

		if (remainTileNum == roomContext.getGameContext().getHuangZhuangtTileNum()) {
			for (Entry<PlayerLocation, PlayerInfo> entry : roomContext.getGameContext().getTable().getPlayerInfos()
					.entrySet()) {
				ProtocolModel huangZhuangProtocolModel = new ProtocolModel();
				DiscardRespModel hzdiscardRespModel = new DiscardRespModel(roomContext, entry.getKey(), true);
				huangZhuangProtocolModel.setBody(JSON.toJSONString(hzdiscardRespModel,SerializerFeature.DisableCircularReferenceDetect));
				huangZhuangProtocolModel.setCommandId(EventEnum.HUANG_ZHUANG.getValue());
				HandlerHelper.noticeMsg2Player(roomContext, entry.getValue(), huangZhuangProtocolModel);
			}
			roomContext.getGameContext().setHuangzhuang(true);
			// 本局结束，执行清理操作
		} else {
			DrawActionType drawActionType = new DrawActionType();
			drawActionType.doAction(roomContext.getGameContext(), playerLocation, new Action(drawActionType));
			for (Entry<PlayerLocation, PlayerInfo> entry : roomContext.getGameContext().getTable().getPlayerInfos()
					.entrySet()) {
				ProtocolModel drawTileProtocolModel = new ProtocolModel();
				DrawCardRespModel drawCardRespModel = new DrawCardRespModel(roomContext, entry.getKey());
				drawTileProtocolModel.setCommandId(EventEnum.DRAW_TILE_RESP.getValue());
				drawTileProtocolModel.setBody(JSON.toJSONString(drawCardRespModel,SerializerFeature.DisableCircularReferenceDetect));
				HandlerHelper.noticeMsg2Player(roomContext, entry.getValue(), drawTileProtocolModel);
			}
			doDrawTileResp(roomContext,playerLocation);
		}
	}

	public static void cpgProcess2Players(RoomContext roomContext, TileGroupType tileGroupType, Action action,
			PlayerLocation discardPlayLocation) throws IllegalActionException {
		CpgActionType cpgActionType = new CpgActionType(tileGroupType);
		cpgActionType.doAction(roomContext.getGameContext(), discardPlayLocation, action);
		if (tileGroupType == TileGroupType.BUGANG_GROUP) {
			DrawBottomActionType drawBottomActionType = new DrawBottomActionType();
			drawBottomActionType.doAction(roomContext.getGameContext(), discardPlayLocation, new Action(DRAW_BOTTOM));
			doDrawTileResp(roomContext, discardPlayLocation);
		}
		roomContext.getGameContext().getTable().resetPlayersLastTileGroupAction();//清空当前的动作
		roomContext.getGameContext().getTable().getPlayerByLocation(discardPlayLocation).setLastTileGroupAction(tileGroupType.getCode());//把当前的动作告诉所有玩家
		for (Entry<PlayerLocation, PlayerInfo> entry : roomContext.getGameContext().getTable().getPlayerInfos()
				.entrySet()) {
			ProtocolModel cpgProtocolModel = new ProtocolModel();
			DiscardRespModel discardRespModel = new DiscardRespModel(roomContext, entry.getKey());
			cpgProtocolModel.setCommandId(EventEnum.DISCARD_ONE_CARD_RESP.getValue());
			cpgProtocolModel.setBody(JSON.toJSONString(discardRespModel,SerializerFeature.DisableCircularReferenceDetect));
			HandlerHelper.noticeMsg2Player(roomContext, entry.getValue(), cpgProtocolModel);
		}
		roomContext.getGameContext().getTable().resetPlayersLastTileGroupAction();//清空动作
	}
	
	public static void anGangProcess2Players(RoomContext roomContext,PlayerLocation playLocation,Tile tile) throws IllegalActionException{
		AngangActionType angangActionType=new AngangActionType(TileGroupType.ANGANG_GROUP);
		angangActionType.doAction(roomContext.getGameContext(), playLocation, new Action(ANGANG,tile));
		roomContext.getGameContext().getTable().resetPlayersLastTileGroupAction();//清空当前的动作
		roomContext.getGameContext().getTable().getPlayerByLocation(playLocation).setLastTileGroupAction(TileGroupType.ANGANG_GROUP.getCode());//把当前的动作告诉所有玩家
		for (Entry<PlayerLocation, PlayerInfo> entry : roomContext.getGameContext().getTable().getPlayerInfos()
				.entrySet()) {
		ProtocolModel anGangProtocolModel = new ProtocolModel();
		DiscardRespModel discardRespModel = new DiscardRespModel(roomContext, playLocation);
		anGangProtocolModel.setCommandId(EventEnum.DISCARD_ONE_CARD_RESP.getValue());
		anGangProtocolModel.setBody(JSON.toJSONString(discardRespModel,SerializerFeature.DisableCircularReferenceDetect));
		HandlerHelper.noticeMsg2Player(roomContext, entry.getValue(), anGangProtocolModel);
		}
		roomContext.getGameContext().getTable().resetPlayersLastTileGroupAction();//清空动作
	    doDrawTileResp(roomContext, playLocation);
	}
	
	public static void xfgProcess2Players(RoomContext roomContext, int tileGroupTypeCode, Action action,
			PlayerLocation discardPlayLocation) throws IllegalActionException {
		ZiPaiActionType ziPaiActionType = new ZiPaiActionType();
		ziPaiActionType.doAction(roomContext.getGameContext(), discardPlayLocation, action);
		if (tileGroupTypeCode != TileGroupType.XUAN_FENG_GANG_ZFB_GROUP.getCode()) {
			DrawBottomActionType drawBottomActionType = new DrawBottomActionType();
			drawBottomActionType.doAction(roomContext.getGameContext(), discardPlayLocation, new Action(DRAW_BOTTOM));
			doDrawTileResp(roomContext, discardPlayLocation);
		}
		roomContext.getGameContext().getTable().resetPlayersLastTileGroupAction();//清空当前的动作
		roomContext.getGameContext().getTable().getPlayerByLocation(discardPlayLocation).setLastTileGroupAction(tileGroupTypeCode);//把当前的动作告诉所有玩家
		for (Entry<PlayerLocation, PlayerInfo> entry : roomContext.getGameContext().getTable().getPlayerInfos()
				.entrySet()) {
		ProtocolModel xfgProtocolModel = new ProtocolModel();
		DiscardRespModel discardRespModel = new DiscardRespModel(roomContext, entry.getKey());
		xfgProtocolModel.setCommandId(EventEnum.DISCARD_ONE_CARD_RESP.getValue());
		xfgProtocolModel.setBody(JSON.toJSONString(discardRespModel,SerializerFeature.DisableCircularReferenceDetect));
		HandlerHelper.noticeMsg2Player(roomContext, entry.getValue(), xfgProtocolModel);
		roomContext.getGameContext().getTable().resetPlayersLastTileGroupAction();//清空动作
		doDrawTileResp(roomContext, discardPlayLocation);
		}
	}
	public static void huProcess2Players(RoomContext roomContext, TileGroupType huGroup, Action action,
			PlayerLocation discardPlayLocation) throws IllegalActionException {
		WinActionType winActionType = new WinActionType();
		winActionType.doAction(roomContext.getGameContext(),discardPlayLocation,action);
		for (Entry<PlayerLocation, PlayerInfo> entry : roomContext.getGameContext().getTable().getPlayerInfos()
				.entrySet()) {
			if(entry.getKey()==discardPlayLocation){
				entry.getValue().setLastWinTile(action.getTile());	
			}
		}

	}

	public static void processDiscardResp(RoomContext roomContext, PlayerLocation discardPlayLocation,
			DiscardReqModel discardReqModel) throws IllegalActionException {
		List<DisCardActionAndLocation> needPassOrDoActions = roomContext.getGameContext().getDiscardContext()
				.getNeedPassOrDoAction();
		if (!needPassOrDoActions.isEmpty()) {
			//Multimap<PlayerLocation, Action> multimap = groupByActionByLocation(needPassOrDoActions);
			DisCardActionAndLocation actionAndLocation = needPassOrDoActions.get(0);
			if (actionAndLocation.getActionAndLocation().getLocation() == discardPlayLocation) {// 刚好是第一位
				if (actionAndLocation.getTileGroupType() == discardReqModel.getTileGroupType()) {// 刚好是当前用户选择的，可以直接执行完，然后清理掉context
					doDiscardResp(roomContext, discardReqModel.getTile());//TODO 执行完毕后要发牌
					return;
				} else {// 同时是可以碰和吃，但是玩家选了吃.或者选了过
					removeNotChooseButHighLevelAction(needPassOrDoActions, new DisCardActionAndLocation(
							new ActionAndLocation(null, discardPlayLocation), discardReqModel.getTileGroupType())); // 清理掉这个用户的优先级比当前出牌动作高的牌选项
					roomContext.getGameContext().getDiscardContext().releaseRemainVoter();
				}
			} else {
				removeNotChooseButHighLevelAction(needPassOrDoActions, new DisCardActionAndLocation(
						new ActionAndLocation(null, discardPlayLocation), discardReqModel.getTileGroupType()));// 清理掉比用户选择的优先级高的选项
				roomContext.getGameContext().getDiscardContext().releaseRemainVoter();
			}

			if (roomContext.getGameContext().getDiscardContext().getRemainVoter().get() == 0) {// 此时所有玩家都表决了，但是可能玩家表决的不是自己优先级最高的操作【最高的操作已经被清理了】。
				if (needPassOrDoActions!=null&&needPassOrDoActions.size() != 0) {
					doDiscardResp(roomContext, discardReqModel.getTile());
				}else{
					HandlerHelper.drawTile2Player(roomContext, roomContext.getGameContext().getDiscardContext().getDiscardPlayLocation().getLocationOf(Relation.NEXT));
				}
			}
		} else {
			HandlerHelper.drawTile2Player(roomContext, discardPlayLocation.getLocationOf(Relation.NEXT));
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
				} else {
					if (i <= currentIndex) {
						needPassOrDoActions.remove(i);// 移除优先级高的，用户却没有选择的
					}
				}
			}
		}

	}

	private static void doDiscardResp(RoomContext roomContext, Tile tile) throws IllegalActionException {
		List<DisCardActionAndLocation> needPassOrDoActions = roomContext.getGameContext().getDiscardContext()
				.getNeedPassOrDoAction();
		DisCardActionAndLocation disCardActionAndLocation = needPassOrDoActions.get(0);
		if (disCardActionAndLocation.getTileGroupType() == TileGroupType.CHI_GROUP.getCode()) {
			HandlerHelper.cpgProcess2Players(roomContext, CHI_GROUP, new Action(CHI, tile),
					disCardActionAndLocation.getActionAndLocation().getLocation());
		} else if (disCardActionAndLocation.getTileGroupType() == TileGroupType.PENG_GROUP.getCode()) {
			HandlerHelper.cpgProcess2Players(roomContext, PENG_GROUP, new Action(PENG, tile),
					disCardActionAndLocation.getActionAndLocation().getLocation());
		} else if (disCardActionAndLocation.getTileGroupType() == TileGroupType.BUGANG_GROUP.getCode()) {
			HandlerHelper.cpgProcess2Players(roomContext, BUGANG_GROUP, new Action(BUGANG, tile),
					disCardActionAndLocation.getActionAndLocation().getLocation());
			
		} else if (disCardActionAndLocation.getTileGroupType() == TileGroupType.XUAN_FENG_GANG_ZFB_GROUP.getCode()) {
			HandlerHelper.xfgProcess2Players(roomContext, XUAN_FENG_GANG_ZFB_GROUP.getCode(), new Action(ZIPAI, tile),
					disCardActionAndLocation.getActionAndLocation().getLocation());
		} else if (disCardActionAndLocation.getTileGroupType() == TileGroupType.XUAN_FENG_GANG_DNXB_GROUP.getCode()) {
			HandlerHelper.xfgProcess2Players(roomContext, XUAN_FENG_GANG_DNXB_GROUP.getCode(), new Action(ZIPAI, tile),
					disCardActionAndLocation.getActionAndLocation().getLocation());
		} else if (disCardActionAndLocation.getTileGroupType() == TileGroupType.HU_GROUP.getCode()) {
			HandlerHelper.huProcess2Players(roomContext, HU_GROUP,  new Action(WIN, disCardActionAndLocation.getActionAndLocation().getAction().getTile()),disCardActionAndLocation.getActionAndLocation().getLocation());
			
			return;
			// TODO 战绩
		}
		roomContext.getGameContext().getDiscardContext().clear();
	}

	public static String getScoreTypesStr(List<GetScoreType> gatherScoreTypes) {

		int[] types = new int[15];
		for (GetScoreType getScoreType : gatherScoreTypes) {
			types[getScoreType.ordinal()] = 1;
		}

		String returnStr = "";
		for (int i = 0; i < types.length; i++) {
			returnStr += types[i];
		}

		return returnStr;

	}
	
	private static boolean dealTile2AllPlayersCheck(RoomContext roomContex) {
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
	
	public static void dealTile2AllPlayers(RoomContext roomContex, DBService dbService) throws IllegalActionException{
		boolean hashDealTile = HandlerHelper.dealTile2AllPlayersCheck(roomContex);
		if (hashDealTile) {// 通知所有玩家已经发牌
			RoomRecord roomRecord = new RoomRecord();

			roomRecord.setRoomState((byte) 2);
			
			boolean flg = dbService.updateRoomRecordInfoByPrimaryKey(roomRecord);
			
			logger.error("更新房间信息，flg="+flg+",roomRecord="+JSONObject.toJSONString(roomRecord));
			
			HouseContext.playRoomNum.incrementAndGet();
			HouseContext.waitRoomNum.decrementAndGet();
			
			HouseContext.playUserNum.addAndGet(4);
			HouseContext.waitUserNum.addAndGet(-4);
			
			roomContex.setRoomStatus(RoomStatus.PLAYING);
			
			for (Entry<PlayerLocation, PlayerInfo> entry : roomContex.getGameContext().getTable().getPlayerInfos().entrySet()) {
				
				ProtocolModel dealTileProtocolModel = new ProtocolModel();
				dealTileProtocolModel.setCommandId(EventEnum.DEAL_TILE_RESP.getValue());
				
				String playWinXinId = entry.getValue().getUserInfo().getWeixinMark();
				EnterRoomRespModel dealTileRoomRespModel = new EnterRoomRespModel(playWinXinId,	true, "发牌", roomContex, entry.getKey());// 创建每个方位的牌响应信息
				dealTileProtocolModel.setBody(JSON.toJSONString(dealTileRoomRespModel,SerializerFeature.DisableCircularReferenceDetect));
				
				ChannelHandlerContext userCtx = ClientSession.sessionMap.get(playWinXinId);
				
				userCtx.writeAndFlush(dealTileProtocolModel);
				logger.error("hashDealTile返回数据："+JSONObject.toJSONString(dealTileProtocolModel));
			}
			roomContex.getGameContext().getTable().printAllPlayTiles();
			PlayerLocation zhuangLocation=roomContex.getGameContext().getZhuangLocation();
			doDrawTileResp(roomContex, zhuangLocation);
		}
	}

	public static void doDrawTileResp(RoomContext roomContext,PlayerLocation playerLocation){
		roomContext.getGameContext().getTable().printAllPlayTiles();
		List<Action> canDoActions=new ArrayList<Action>();
		WinActionType winActionType = new WinActionType();
		//胡判断
		boolean canwin = winActionType.isLegalAction(roomContext.getGameContext(),playerLocation, new Action(WIN));			
		if (canwin) {
			canDoActions.add(new Action(WIN));
		}
		boolean canZiPai=false;
		DrawTileContext drawTileContext =	roomContext.getGameContext().getTable().getPlayerByLocation(playerLocation).getDrawTileContext();
		boolean isFirstDrawTile=drawTileContext!=null&&drawTileContext.isFirstDrawTile();
		//开局第一把旋风杠判断【选了规则才会判断】
		if(roomContext.getGameContext().getGameStrategy().getRuleInfo().getPlayRules().contains(PlayRule.XUAN_FENG_GANG)){
			if(isFirstDrawTile){
			ZiPaiActionType ziPaiActionType = new ZiPaiActionType();
		    canZiPai = ziPaiActionType.canDo(roomContext.getGameContext(),playerLocation);
			if (canZiPai) {
				canDoActions.add(new Action(ZIPAI,roomContext.getGameContext().getTable().getPlayerByLocation(playerLocation).getLastDrawedTile()));
			}
		 }
		}else{
			AngangActionType angangActionType=new AngangActionType(TileGroupType.ANGANG_GROUP);
			boolean candoAngang=angangActionType.canDo(roomContext.getGameContext(), playerLocation);
			if(candoAngang){
				canDoActions.add(new Action(ANGANG,roomContext.getGameContext().getTable().getPlayerByLocation(playerLocation).getLastDrawedTile()));
			}
		}
		if(!canDoActions.isEmpty()){
			roomContext.getGameContext().getTable().getPlayerByLocation(playerLocation).setDrawTileContext(new DrawTileContext(canDoActions, canwin, canZiPai, playerLocation, isFirstDrawTile));
			ProtocolModel canDoProtocolModel = new ProtocolModel();
			AskChoiceRespModel askChoiceRespModel = new AskChoiceRespModel(
					new ArrayList<Action>(canDoActions));
			canDoProtocolModel.setCommandId(EventEnum.DRAW_ASK_CHOICE_RESP.getValue());
			canDoProtocolModel.setBody(JSON.toJSONString(askChoiceRespModel,SerializerFeature.DisableCircularReferenceDetect));
			String weixinMarkId = roomContext.getGameContext().getTable().getPlayerByLocation(playerLocation)
					.getUserInfo().getWeixinMark();
			ChannelHandlerContext ctx = ClientSession.sessionMap.get(weixinMarkId);
			ctx.writeAndFlush(canDoProtocolModel);
			logger.info("返回数据：" + JSONObject.toJSONString(canDoProtocolModel));
		}
	}
}