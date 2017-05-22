package com.mahjong.server.netty.handler;

import static com.mahjong.server.game.action.standard.StandardActionType.BUGANG;
import static com.mahjong.server.game.action.standard.StandardActionType.CHI;
import static com.mahjong.server.game.action.standard.StandardActionType.PENG;
import static com.mahjong.server.game.action.standard.StandardActionType.WIN;
import static com.mahjong.server.game.object.TileGroupType.BUGANG_GROUP;
import static com.mahjong.server.game.object.TileGroupType.CHI_GROUP;
import static com.mahjong.server.game.object.TileGroupType.PENG_GROUP;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.action.standard.CpgActionType;
import com.mahjong.server.game.action.standard.WinActionType;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.enums.PlayerLocation.Relation;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.Tile;
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
	public static List<ActionAndLocation> getActionAfterDiscardTile(RoomContext roomContext, Tile tile,
			PlayerLocation discardPlayLocation) {
		 WinActionType winActionType=new WinActionType();
		List<PlayerLocation> playerLocations = discardPlayLocation.getOrderedNextLocations();
		List<ActionAndLocation> canDoActionAndLocation = new ArrayList<ActionAndLocation>();
		//胡检测
		for (PlayerLocation playerLocation : playerLocations) {
			boolean canWin = winActionType.canDo(roomContext.getGameContext(), playerLocation);
			if (canWin) {
				canDoActionAndLocation.add(new ActionAndLocation(new Action(WIN, tile), playerLocation));
			}
		}
		// 杠检测
		for (PlayerLocation playerLocation : playerLocations) {
			CpgActionType cpgActionType = new CpgActionType(BUGANG_GROUP, null);
			boolean canGang = cpgActionType.canDo(roomContext.getGameContext(), playerLocation);
			if (canGang) {
				canDoActionAndLocation.add(new ActionAndLocation(new Action(BUGANG, tile), playerLocation));
			}
		}
		// 碰检测
		for (PlayerLocation playerLocation : playerLocations) {
			CpgActionType cpgActionType = new CpgActionType(PENG_GROUP, null);
			boolean canPeng = cpgActionType.canDo(roomContext.getGameContext(), playerLocation);
			if (canPeng) {
				canDoActionAndLocation.add(new ActionAndLocation(new Action(PENG, tile), playerLocation));
				}
		}
		// 吃检测
		CpgActionType cpgActionType = new CpgActionType(CHI_GROUP, null);
		boolean canChi = cpgActionType.canDo(roomContext.getGameContext(),
				discardPlayLocation.getLocationOf(Relation.NEXT));
		if (canChi) {
			canDoActionAndLocation
					.add(new ActionAndLocation(new Action(CHI, tile),
							discardPlayLocation.getLocationOf(Relation.NEXT)));
		}
		return canDoActionAndLocation;
	}
}