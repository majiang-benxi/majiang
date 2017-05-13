package majiang;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public class SocketTest {
	public static void main(String[] args) throws Exception {
		new EchoClient().start();
}
}
	class EchoClient {
		public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group) // 注册线程池
					.channel(NioSocketChannel.class) // 使用NioSocketChannel来作为连接用的channel类
					.remoteAddress(new InetSocketAddress("127.0.0.1", 8888)) // 绑定连接端口和host信息
					.handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							System.out.println("connected...");
							ch.pipeline().addLast(new EchoClientHandler());
						}
					});
			System.out.println("created..");

			ChannelFuture cf = b.connect().sync(); // 异步连接服务器
			System.out.println("connected..."); // 连接完成

			cf.channel().closeFuture().sync(); // 异步等待关闭连接channel
			System.out.println("closed.."); // 关闭完成
		} finally {
			group.shutdownGracefully().sync(); // 释放线程池资源
		}
	}
	}
	class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			System.out.println("client channelActive..");
			ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8)); // 必须有flush
		}

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
			System.out.println("client channelRead..");
			ByteBuf buf = msg.readBytes(msg.readableBytes());
			System.out.println("Client received:" + ByteBufUtil.hexDump(buf) + "; The value is:"
					+ buf.toString(Charset.forName("utf-8")));
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			cause.printStackTrace();
			ctx.close();
		}

	}

