package com.mahjong.server.netty.handler;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.model.SendMsgReqModel;
import com.mahjong.server.netty.model.SendMsgRespModel;
import com.mahjong.server.netty.session.ClientSession;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 发送消息
 *
 */
@Sharable
@Component
public class SendMessageHandler extends SimpleChannelInboundHandler<ProtocolModel> {
	
	private static final Logger logger = LoggerFactory.getLogger(SendMessageHandler.class);
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
			if (protocolModel.getCommandId() == EventEnum.SEND_MESG_REQ.getValue()) {
				if (protocolModel.getBody() == null) {
					ctx.close();
				} else {
					SendMsgReqModel sendMsgReqModel = JSON.parseObject(protocolModel.getBody(),
							new TypeReference<SendMsgReqModel>() {
							});
					
					String weixinId = sendMsgReqModel.getWeiXinId();
					String msgtype = sendMsgReqModel.getMsgtype();
					String msg = sendMsgReqModel.getMsg();
					
					UserInfo userInfo = HouseContext.weixinIdToUserInfo.get(weixinId);
					if (userInfo != null) {
						RoomContext roomContex = HouseContext.weixinIdToRoom.get(weixinId);	
						
						if(roomContex==null){
							logger.error("用户没有在任何房间，发消息无效，weixinId="+weixinId+",userInfo="+userInfo.getNickName());
							return;
						}
						
							
						// 通知其他三家
						SendMsgRespModel sendMsgRespModel = new SendMsgRespModel();
						sendMsgRespModel.setMesgFrom(userInfo.getNickName());
						sendMsgRespModel.setMsg(msg);
						sendMsgRespModel.setMsgtype(msgtype);
						
						for(Entry<PlayerLocation, PlayerInfo>  ent : roomContex.getGameContext().getTable().getPlayerInfos().entrySet()){
							
							PlayerInfo playerIn = ent.getValue();
							if(playerIn.getUserInfo().getWeixinMark().equals(weixinId)){
								continue;
							}
							
							ProtocolModel newProtocolModel = new ProtocolModel();
							newProtocolModel.setCommandId(EventEnum.SEND_MESG_RESP.getValue());
							newProtocolModel.setBody(JSON.toJSONString(sendMsgRespModel));
							
							ChannelHandlerContext userCtx = ClientSession.sessionMap.get(weixinId);
							userCtx.writeAndFlush(newProtocolModel);
							
							logger.error("获取消息返回数据："+JSONObject.toJSONString(newProtocolModel));
							
						}
				} else {
					logger.error("用户不存在，或者不在线，weixinId="+weixinId);
				}
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