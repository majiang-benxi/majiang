package com.mahjong.server.netty.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahjong.server.netty.codec.WebSocketProtocolCodec;
import com.mahjong.server.netty.handler.AuthHandler;
import com.mahjong.server.netty.handler.CreateRoomHandler;
import com.mahjong.server.netty.handler.EnterRoomHandler;
import com.mahjong.server.netty.handler.HeartBeatHandler;
import com.mahjong.server.netty.handler.HistoryRecordHandler;
import com.mahjong.server.netty.handler.KillRoomHandler;
import com.mahjong.server.netty.handler.MahjongLogicHandler;
import com.mahjong.server.netty.handler.SendMessageHandler;
import com.mahjong.server.netty.handler.ToWinHandler;
import com.mahjong.server.netty.handler.UpdateHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

@Component
public class WebSocketChannelInitializer extends ChannelInitializer<NioSocketChannel> {

	@Autowired
	private WebSocketProtocolCodec webSocketProtocolCodec;
	@Autowired
	private AuthHandler authHandler;
	@Autowired
	private MahjongLogicHandler mahjongLogicHandler;
	@Autowired
	private CreateRoomHandler createRoomHandler;
	@Autowired
	private EnterRoomHandler enterRoomHandler;
	@Autowired
	private HeartBeatHandler heartBeatHandler;
	@Autowired
	private HistoryRecordHandler historyRecordHandler;
	@Autowired
	private SendMessageHandler sendMessageHandler;
	@Autowired
	private ToWinHandler toWinHandler;
	@Autowired
	private UpdateHandler updateHandler;
	@Autowired
	private KillRoomHandler killRoomHandler;
	protected void initChannel(NioSocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// 编解码 http请求
		pipeline.addLast(new HttpServerCodec());
		// 写文件内容
		pipeline.addLast(new ChunkedWriteHandler());
		// 聚合解码 HttpRequest/HttpContent/LastHttpContent到FullHttpRequest
		// 保证接收的 Http请求的完整性
		pipeline.addLast(new HttpObjectAggregator(64 * 1024));
		// 处理其他的 WebSocketFrame
		pipeline.addLast(new WebSocketServerProtocolHandler("/mj"));
		// 处理 TextWebSocketFrame
		pipeline.addLast(webSocketProtocolCodec);
		pipeline.addLast(authHandler);
		pipeline.addLast(heartBeatHandler);
		pipeline.addLast(mahjongLogicHandler);
		pipeline.addLast(createRoomHandler);
		pipeline.addLast(enterRoomHandler);
		pipeline.addLast(killRoomHandler);
		pipeline.addLast(sendMessageHandler);
		pipeline.addLast(toWinHandler);
		pipeline.addLast(updateHandler);
		pipeline.addLast(historyRecordHandler);
		// pipeline.addLast(new ReadTimeoutHandler(10));// 控制读取数据的时候的超时，10秒超时
		// pipeline.addLast(new WriteTimeoutHandler(10));// 控制数据输出的时候的超时，10秒超时
	}
}
