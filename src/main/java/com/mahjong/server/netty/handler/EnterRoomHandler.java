package com.mahjong.server.netty.handler;

import static com.mahjong.server.game.action.standard.StandardActionType.WIN;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.exception.IllegalActionException;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.standard.DealActionType;
import com.mahjong.server.game.action.standard.WinActionType;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.enums.RoomStatus;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.netty.model.EnterRoomReqModel;
import com.mahjong.server.netty.model.EnterRoomRespModel;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;
import com.mahjong.server.service.DBService;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 登陆认证
 *
 */
@Sharable
@Component
public class EnterRoomHandler extends SimpleChannelInboundHandler<ProtocolModel> {
	@Autowired
	private DBService dbService;

	@Override
	public void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
		if (protocolModel.getCommandId() == EventEnum.ROOM_ENTER_REQ.getValue()) {
			if (protocolModel.getBody() == null) {
				ctx.close();
			} else {
				EnterRoomReqModel enterRoomReqModel = JSON.parseObject(protocolModel.getBody(),
						new TypeReference<EnterRoomReqModel>() {
						});

				String weixinId = enterRoomReqModel.getWeiXinId();
				Integer roomId = enterRoomReqModel.getRoomId();
				EnterRoomRespModel enterRoomRespModel = null;
				UserInfo userInfo = dbService.selectUserInfoByWeiXinMark(weixinId);
				boolean flag = false;
				if (userInfo == null) {
					enterRoomRespModel = new EnterRoomRespModel(weixinId, false, "您未注册，无法加入房间，请见注册！", null);
				} else {
					RoomContext roomContex = HouseContext.weixinIdToRoom.get(weixinId);
					if (roomContex == null) {

						if ((roomContex = HouseContext.rommList.get(roomId)) != null) {
							flag = roomContex.joinRoom(userInfo);
							if (flag) {
								HouseContext.weixinIdToRoom.put(weixinId, roomContex);
								// 通知其他三家
								ProtocolModel enterRoomProtocolModel = new ProtocolModel();
								enterRoomProtocolModel.setCommandId(EventEnum.NEW_ENTER_RESP.getValue());
								roomContex.setRoomStatus(RoomStatus.WAIT_USERS);
								EnterRoomRespModel newEnterRoomRespModel = new EnterRoomRespModel(weixinId, true,
										"新人加入", roomContex);
								enterRoomProtocolModel.setBody(JSON.toJSONString(newEnterRoomRespModel));
								HandlerHelper.noticeMsg2Players(roomContex, null, enterRoomProtocolModel);
								boolean hashDealTile = dealTile2AllPlayersCheck(roomContex);
								if (hashDealTile) {// 通知所有玩家已经发牌
									for (Entry<PlayerLocation, PlayerInfo> entry : roomContex.getGameContext()
											.getTable().getPlayerInfos()
											.entrySet()) {
										ProtocolModel dealTileProtocolModel = new ProtocolModel();
										dealTileProtocolModel.setCommandId(EventEnum.DEAL_TILE_RESP.getValue());
										roomContex.setRoomStatus(RoomStatus.PLAYING);
										String playWinXinId = entry.getValue().getUserInfo().getWeixinMark();
										EnterRoomRespModel dealTileRoomRespModel = new EnterRoomRespModel(playWinXinId,
												true,
												"发牌", roomContex, entry.getKey());// 创建每个方位的牌响应信息
										dealTileProtocolModel.setBody(JSON.toJSONString(dealTileRoomRespModel));
										ChannelHandlerContext userCtx = ClientSession.sessionMap.get(playWinXinId);
										userCtx.writeAndFlush(dealTileProtocolModel);
									}

									WinActionType winActionType = new WinActionType();
									boolean winFirst = winActionType.isLegalAction(roomContex.getGameContext(),
											roomContex.getGameContext().getZhuangLocation(), new Action(WIN));
									if (winFirst) {
										winActionType.doAction(roomContex.getGameContext(),
												roomContex.getGameContext().getZhuangLocation(), new Action(WIN));
										ProtocolModel winProtocolModel = new ProtocolModel();
										winProtocolModel.setCommandId(EventEnum.WIN_TILE_RESP.getValue());
										roomContex.setRoomStatus(RoomStatus.PLAYING);
										EnterRoomRespModel winTileRoomRespModel = new EnterRoomRespModel(null, true,
												"庄家天胡", roomContex);
										winProtocolModel.setBody(JSON.toJSONString(winTileRoomRespModel));
										HandlerHelper.noticeMsg2Players(roomContex, null, winProtocolModel);
									}
								}
								return;
							} else {
								enterRoomRespModel = new EnterRoomRespModel(weixinId, false, "加入房间失败，房间已满！", null);
							}
						} else {
							enterRoomRespModel = new EnterRoomRespModel(weixinId, false, "加入房间失败，房间不存在！", null);
						}

					} else {
						enterRoomRespModel = new EnterRoomRespModel(weixinId, true, "重新加入房间", roomContex);
					}
				}
				protocolModel.setCommandId(EventEnum.ROOM_ENTER_RESP.getValue());
				protocolModel.setBody(JSON.toJSONString(enterRoomRespModel));
				ctx.writeAndFlush(protocolModel);
			}
		} else {
			ctx.fireChannelRead(protocolModel);
		}
	}

	private boolean dealTile2AllPlayersCheck(RoomContext roomContex) {
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

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}

}