package com.mahjong.server.netty.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.netty.model.EnterRoomReqModel;
import com.mahjong.server.netty.model.EnterRoomRespModel;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.service.DBService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 登陆认证
 *
 */
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
				EnterRoomReqModel enterRoomReqModel = JSON.parseObject(new String(protocolModel.getBody(), "UTF-8"),
						new TypeReference<EnterRoomReqModel>() {
						});
				
				String weixinId = enterRoomReqModel.getWeixinId();
				Integer roomId = enterRoomReqModel.getRoomId();
				
				UserInfo userInfo = dbService.selectUserInfoByWeiXinMark(weixinId);
				
				EnterRoomRespModel createRoomRespModel = null;
				RoomContext roomContex = null;
				if((roomContex = HouseContext.weixinIdToRoom.get(weixinId))==null){
					
					if((roomContex = HouseContext.rommList.get(roomId))!=null){
						boolean flag = roomContex.joinRoom(userInfo);
						if(flag){
							HouseContext.weixinIdToRoom.put(weixinId, roomContex);
							createRoomRespModel = new EnterRoomRespModel(false,"恭喜您，加入房间成功！",roomContex);
						}else{
							createRoomRespModel = new EnterRoomRespModel(false,"加入房间失败，房间已满！",null);
						}
					}else{
						createRoomRespModel = new EnterRoomRespModel(false,"加入房间失败，房间不存在！",null);
					}
					
				}
				
				protocolModel.setCommandId(EventEnum.ROOM_ENTER_RESP.getValue());
				protocolModel.setBody(JSON.toJSONString(createRoomRespModel).getBytes("UTF-8"));
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