package com.mahjong.server.netty.handler;

import java.net.InetSocketAddress;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.netty.model.AuthReqModel;
import com.mahjong.server.netty.model.AuthRespModel;
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
public class AuthHandler extends SimpleChannelInboundHandler<ProtocolModel> {
	@Autowired
	private DBService dbService;
	@Override
	public void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
		if (protocolModel.getCommandId() == EventEnum.AUTH_REQ.getValue()) {
			if (protocolModel.getBody() == null) {
				ctx.close();
			} else {
				AuthReqModel authModel = JSON.parseObject(protocolModel.getBody(),
						new TypeReference<AuthReqModel>() {
						});
				InetSocketAddress socketAddr = (InetSocketAddress) ctx.channel().remoteAddress();

				String weixinId = authModel.getWeiXinId();
				UserInfo userInfo = null;
				if (ClientSession.sessionMap.get(weixinId) == null) {
					userInfo = dbService.selectUserInfoByWeiXinMark(weixinId);
					Date now = new Date();
					if (userInfo==null) {
						userInfo = new UserInfo();
						userInfo.setCreateTime(now);
						userInfo.setHeadImgurl(authModel.getHeadImgUrl());
						userInfo.setLastLoginIp(socketAddr.getAddress().getHostAddress());
						userInfo.setLastLoginTime(now);
						userInfo.setNickName(authModel.getNickName());
						userInfo.setRoomCartNum(0);
						userInfo.setSex((byte) authModel.getSex());
						userInfo.setState((byte) 1);
						userInfo.setWeixinMark(weixinId);
						dbService.insertUserInfo(userInfo);
						HouseContext.weixinIdToUserInfo.put(weixinId, userInfo);
					}else{
						UserInfo updateUserInfo = new UserInfo();
						updateUserInfo.setHeadImgurl(authModel.getHeadImgUrl());
						updateUserInfo.setLastLoginIp(socketAddr.getAddress().getHostAddress());
						updateUserInfo.setLastLoginTime(now);
						updateUserInfo.setNickName(authModel.getNickName());
						updateUserInfo.setSex((byte) authModel.getSex());
						updateUserInfo.setWeixinMark(weixinId);
						updateUserInfo.setId(userInfo.getId());
						updateUserInfo.setState((byte) 1);
						dbService.updateUserInfoById(updateUserInfo);
						HouseContext.weixinIdToUserInfo.put(weixinId, updateUserInfo);
					}
					ClientSession.sessionMap.put(weixinId, ctx);
				}
				if (userInfo == null) {
					userInfo = dbService.selectUserInfoByWeiXinMark(weixinId);
				}
				int fangKaSize = userInfo.getRoomCartNum();
				RoomContext playingRoom = HouseContext.weixinIdToRoom.get(weixinId);
				AuthRespModel authRespModel = new AuthRespModel(true, fangKaSize,
						playingRoom == null ? null : playingRoom.getRoomNum());
				// 回写ACK
				protocolModel.setCommandId(EventEnum.AUTH_RESP.getValue());
				protocolModel.setBody(JSON.toJSONString(authRespModel));
				ctx.writeAndFlush(protocolModel);
			}
		} else {
			ctx.fireChannelRead(protocolModel);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}
}