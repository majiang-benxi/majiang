package com.mahjong.server.netty.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mahjong.server.entity.UpdateInfo;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.netty.model.DownLoadReqModel;
import com.mahjong.server.netty.model.DownLoadRespModel;
import com.mahjong.server.netty.model.ProtocolModel;
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
public class UpdateHandler extends SimpleChannelInboundHandler<ProtocolModel> {
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateHandler.class);
	
	@Autowired
	private DBService dbService;
	@Override
	public void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
		if (protocolModel.getCommandId() == EventEnum.UPDATE_REQ.getValue()) {
			if (protocolModel.getBody() == null) {
				ctx.close();
			} else {
				
				DownLoadReqModel downModel = JSON.parseObject(protocolModel.getBody(),
						new TypeReference<DownLoadReqModel>() {
				});
				
				float version = downModel.getAppVersion();
				
				logger.info("更新请求：DeviceType="+protocolModel.getDeviceType()+",version="+version);
				
				UpdateInfo updateInfo = dbService.selectUpdateInfoByDeviceType(protocolModel.getDeviceType(),version);
				
				if(updateInfo!=null){
					DownLoadRespModel downLoadRespModel = new DownLoadRespModel();
					downLoadRespModel.setUrl(updateInfo.getDownUrl());
					protocolModel.setBody(JSON.toJSONString(downLoadRespModel));
				}
				protocolModel.setCommandId(EventEnum.UPDATE_RESP.getValue());
				ctx.writeAndFlush(protocolModel);
				
				logger.error("胡返回数据："+JSONObject.toJSONString(protocolModel));
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