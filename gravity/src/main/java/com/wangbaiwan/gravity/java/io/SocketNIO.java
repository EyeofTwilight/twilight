package com.wangbaiwan.gravity.java.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

public class SocketNIO
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		List<SocketChannel> clients = new LinkedList<>();
		ServerSocketChannel ss = ServerSocketChannel.open();
		ss.bind(new InetSocketAddress(9090));
		// 重点 OS NONBLOCKING
		ss.configureBlocking(false);

		while (true)
		{
			Thread.sleep(1000);
			// accept 即最终会调用OS的系统调用,没有客户端连接进来，有返回值吗？
			// 在BIO的时候一直卡着,但在NIO时,不卡着,OS层返回-1,java层返回null
			// 如果有客户端连接,accept返回值是这个客户端的fd文件描述符
			SocketChannel client = ss.accept();
			if (client == null)
			{
				System.out.println("client....null.....");
			}
			else
			{
				// 配置当前连接的客户端是否阻塞
				// 如:想从连接中读取信息? 没读到java层返回null,读到java层返回值
				client.configureBlocking(false);
				int port = client.socket().getPort();
				System.out.println("client port .." + port);
				clients.add(client);
			}

			ByteBuffer buffer = ByteBuffer.allocate(4096);
			for (SocketChannel c : clients)
			{
				int num = c.read(buffer);
				if (num > 0)
				{
					buffer.flip();
					byte[] aa = new byte[buffer.limit()];
					buffer.get(aa);
					String b = new String(aa);
					System.out.println(c.socket().getPort() + ":" + b);
					buffer.clear();
				}
			}
		}
	}
}
