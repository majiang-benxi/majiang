package com.mahjong.server.netty.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.netty.model.HistoryRecordReqModel;
import com.mahjong.server.netty.model.HistoryRecordRespModel;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;
import com.mahjong.server.service.DBService;
import com.mahjong.server.vo.UserLatestPlayRecord;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 登陆认证
 *
 */
@Component
public class HistoryRecordHandler extends SimpleChannelInboundHandler<ProtocolModel> {
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
				
				String weixinId = historyRecordReqModel.getWeiXinId();
				ctx = ClientSession.sessionMap.get(weixinId);
				UserInfo userInfo = dbService.selectUserInfoByWeiXinMark(weixinId);
				
				List<UserLatestPlayRecord>  userLatestRecord = dbService.selectUserLatestPlayRecord(userInfo.getId(), 10);
			
				// 回写ACK
				HistoryRecordRespModel historyRecordRespModel = new HistoryRecordRespModel();
				historyRecordRespModel.setUserLatestPlayRecord(userLatestRecord);
				
				protocolModel.setCommandId(EventEnum.HISTORY_RECORD_REQ.getValue());
				protocolModel.setBody(JSON.toJSONString(historyRecordRespModel));
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