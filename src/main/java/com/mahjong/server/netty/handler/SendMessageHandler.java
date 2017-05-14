package com.mahjong.server.netty.handler;

import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
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
import com.mahjong.server.service.DBService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 发送消息
 *
 */
@Component
public class SendMessageHandler extends SimpleChannelInboundHandler<ProtocolModel> {
	@Autowired
	private DBService dbService;
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
				
				UserInfo userInfo = dbService.selectUserInfoByWeiXinMark(weixinId);
				if (userInfo != null) {
				RoomContext roomContex = HouseContext.weixinIdToRoom.get(weixinId);					
					
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
					
				}
				} else {
					System.out.println("user not exist ,ignore");
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