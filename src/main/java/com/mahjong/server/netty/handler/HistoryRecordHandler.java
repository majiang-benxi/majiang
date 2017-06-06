package com.mahjong.server.netty.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.netty.model.CreateRoomRespModel;
import com.mahjong.server.netty.model.HistoryRecordReqModel;
import com.mahjong.server.netty.model.HistoryRecordRespModel;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;
import com.mahjong.server.service.DBService;
import com.mahjong.server.vo.UserLatestPlayRecord;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 登陆认证
 *
 */
@Sharable

@Component
public class HistoryRecordHandler extends SimpleChannelInboundHandler<ProtocolModel> {
	
	private static final Logger logger = LoggerFactory.getLogger(HistoryRecordHandler.class);
	
	@Autowired
	private DBService dbService;
	@Override
	public void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
		
		if (protocolModel.getCommandId() == EventEnum.HISTORY_RECORD_REQ.getValue()) {
			if (protocolModel.getBody() == null) {
				ctx.close();
			} else {
				HistoryRecordReqModel historyRecordReqModel = JSON.parseObject(protocolModel.getBody(),
						new TypeReference<HistoryRecordReqModel>() {
						});
				
				HistoryRecordRespModel historyRecordRespModel = new HistoryRecordRespModel();
				
				String weixinId = historyRecordReqModel.getWeiXinId();
				
				UserInfo userInfo = HouseContext.weixinIdToUserInfo.get(weixinId);
				
				if (userInfo == null || ctx==null) {
					logger.error("用户不存在，或者不在线，weixinId="+weixinId);
				}else{
				
					ctx = ClientSession.sessionMap.get(weixinId);
				
					List<UserLatestPlayRecord>  userLatestRecord = dbService.selectUserLatestPlayRecord(userInfo.getId(), 10);
					historyRecordRespModel.setUserLatestPlayRecord(userLatestRecord);
					
				}
				
				protocolModel.setCommandId(EventEnum.HISTORY_RECORD_REQ.getValue());
				protocolModel.setBody(JSON.toJSONString(historyRecordRespModel));
				ctx.writeAndFlush(protocolModel);
				
				logger.error("历史记录返回数据："+JSONObject.toJSONString(protocolModel));
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