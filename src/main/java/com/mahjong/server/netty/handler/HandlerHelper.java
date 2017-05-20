package com.mahjong.server.netty.handler;

import org.apache.commons.lang.StringUtils;

import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;

import io.netty.channel.ChannelHandlerContext;

public class HandlerHelper {
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
}
