package com.wangbaiwan.gravity.java.io;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author eyeoftwilight
 * @date 2020年6月20日
 */
@Slf4j
public class NettyIO
{
	/**
	 * 测试的话
	 * Windows telnet localhost 9000
	 * Linux nc localhost 9000
	 * 当输入消息时，会看到代码的控制台有打印
	 *
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException
	{
		NioEventLoopGroup parent = new NioEventLoopGroup(5);
		NioEventLoopGroup children = new NioEventLoopGroup(5);
		ServerBootstrap boot = new ServerBootstrap();

		// parent 若只bind一个端口,无论nThreads有多大，只有一个用来接收新的连接,其他的不使用
		// child只用来读写,parent会把接收到的连接交给child处理
		boot.group(parent, children).channel(NioServerSocketChannel.class).option(ChannelOption.TCP_NODELAY, false)
				// 处理read/write
				.childHandler(new ChannelInitializer<SocketChannel>()
				              {
					              @Override
					              protected void initChannel(SocketChannel ch)
					              {
						              ChannelPipeline pipeline = ch.pipeline();
						              pipeline.addLast(new MyBound());
					              }
				              }

				).bind(9000)
				// 阻塞当前线程到程序启动起来
				.sync().channel().closeFuture()
				// 阻塞当前线程到服务停止
				.sync();

	}

	static class MyBound extends ChannelInboundHandlerAdapter
	{
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
		{
			log.info("读取开始......,ctx:" + ctx);
			ByteBuf result = (ByteBuf) msg;
			byte[] result1 = new byte[result.readableBytes()];
			result.readBytes(result1);
			String resultStr = new String(result1);
			log.info("客户端消息: " + resultStr);
			// 若加上release在连接释放时，会报错:
			// Failed to release a message: PooledUnsafeDirectByteBuf(freed)
			// io.netty.util.IllegalReferenceCountException: refCnt: 0, decrement: 1
			//	result.release();
			ctx.write(msg);
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
		{
			log.info("读取结束......,ctx:" + ctx);
		}

		/**
		 * Do nothing by default, sub-classes may override this method.
		 *
		 * @param ctx
		 */
		@Override
		public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
		{
			log.info("断开连接,ctx:" + ctx);
		}
	}
}
