package com.mahjong.server.netty.handler;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.game.object.TileGroupType;
import com.mahjong.server.netty.model.DiscardReqModel;
import com.mahjong.server.netty.model.DiscardRespModel;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Component
public class MahjongLogicHandler extends SimpleChannelInboundHandler<ProtocolModel> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel)
			throws Exception {
		if (protocolModel.getCommandId() == EventEnum.DISCARD_ONE_CARD_REQ.getValue()) {
			if (protocolModel.getBody() == null) {
				ctx.close();
			} else {
				DiscardReqModel discardReqModel = JSON.parseObject(protocolModel.getBody(),
						new TypeReference<DiscardReqModel>() {
						});

				if(discardReqModel.getTileGroupType()!=TileGroupType.HU_GROUP.getCode()){
					
					
					String weixinId = discardReqModel.getWeiXinId();
					ctx = ClientSession.sessionMap.get(weixinId);
					
					//TODO 战绩
					
					RoomContext playingRoom = HouseContext.weixinIdToRoom.get(weixinId);
					DiscardRespModel authRespModel = new DiscardRespModel();
					// 回写ACK
					protocolModel.setCommandId(EventEnum.DISCARD_ONE_CARD_RESP.getValue());
					protocolModel.setBody(JSON.toJSONString(authRespModel));
					ctx.writeAndFlush(protocolModel);
					
				}
			}
		} else {
			ctx.fireChannelRead(protocolModel);
		}
	}
}
