package com.mahjong.server.netty.handler;

import java.net.InetSocketAddress;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
	
	private static final Logger logger = LoggerFactory.getLogger(AuthHandler.class);
	
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
				RoomContext playingRoom = null;
				AuthRespModel authRespModel = null;
				
				//用户初次注册，或者是登陆
				
				ChannelHandlerContext ctxx = ClientSession.sessionMap.get(weixinId);
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
					
					userInfo.setRoomCartNumUsed(0);
					userInfo.setLoginTimes(1);
					
					int res = dbService.insertUserInfo(userInfo);
					
					logger.info("新用户注册："+authModel.getNickName()+",weixinId="+weixinId+",res="+res);
					
					int fangKaSize = userInfo.getRoomCartNum();
					authRespModel = new AuthRespModel(true, fangKaSize, playingRoom == null ? 0 : playingRoom.getRoomNum());
							
						
				}else{
					
					if(userInfo.getState()!=1){
						authRespModel = new AuthRespModel(false, 0,  null );
					}else{
						
						UserInfo updateUserInfo = new UserInfo();
						
						updateUserInfo.setHeadImgurl(authModel.getHeadImgUrl());
						updateUserInfo.setLastLoginIp(socketAddr.getAddress().getHostAddress());
						updateUserInfo.setLastLoginTime(now);
						updateUserInfo.setNickName(authModel.getNickName());
						updateUserInfo.setSex((byte) authModel.getSex());
						updateUserInfo.setId(userInfo.getId());
						updateUserInfo.setState((byte) 1);
						updateUserInfo.setWeixinMark(weixinId);
						
						updateUserInfo.setLoginTimes(userInfo.getLoginTimes()+1);
						
						Boolean res = dbService.updateUserInfoById(updateUserInfo);
						
						logger.info("用户登陆："+authModel.getNickName()+",weixinId="+weixinId+",res="+res);
						
						
						userInfo.setHeadImgurl(authModel.getHeadImgUrl());
						userInfo.setLastLoginIp(socketAddr.getAddress().getHostAddress());
						userInfo.setLastLoginTime(now);
						userInfo.setNickName(authModel.getNickName());
						userInfo.setSex((byte) authModel.getSex());
						userInfo.setState((byte) 1);
						userInfo.setWeixinMark(weixinId);
						
						userInfo.setLoginTimes(userInfo.getLoginTimes()+1);
					
						int fangKaSize = userInfo.getRoomCartNum();
						
						if(ctxx != null){
							
							playingRoom = HouseContext.weixinIdToRoom.get(weixinId);
							logger.info("用户掉线超时重连："+authModel.getNickName()+",weixinId="+weixinId);
							
						}else{
							
							logger.info("用户登录："+authModel.getNickName()+",weixinId="+weixinId);
							
						}
						
						authRespModel = new AuthRespModel(true, fangKaSize, playingRoom == null ? 0 : playingRoom.getRoomNum());
					}
				
				}
				
				if(authRespModel.isAuth()){
					
					ClientSession.sessionMap.put(weixinId, ctx);
					HouseContext.weixinIdToUserInfo.put(weixinId, userInfo);
					
					Integer unum = HouseContext.weixinIdToUserInfo.size();
					
					if(unum>HouseContext.onlineUserNum.get()){
						HouseContext.onlineUserNum.incrementAndGet();
					}
					
				}
				
				// 回写ACK
				protocolModel.setCommandId(EventEnum.AUTH_RESP.getValue());
				protocolModel.setBody(JSON.toJSONString(authRespModel,SerializerFeature.DisableCircularReferenceDetect));
				ctx.writeAndFlush(protocolModel);
				logger.error("返回数据："+JSONObject.toJSONString(protocolModel,SerializerFeature.DisableCircularReferenceDetect));
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