package com.wangshen.eyeoftwilight.java.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author
 */
public class SocketMultiple
{
	private ServerSocketChannel server = null;
	private Selector selector = null;
	int port = 900;

	public void initServer()
	{
		try
		{
			server = ServerSocketChannel.open();
			server.configureBlocking(false);
			server.bind(new InetSocketAddress(port));

			// 如果在epoll模型下,open -> epoll_create ->fd3
			// select poll epoll 优先选择epoll,也可以指定
			selector = Selector.open();

			// 如果选择的是：select poll :jvm里开辟一个数组把文件描述符存放进去
			// 如果选择的是epoll: epoll_ctl
			server.register(selector, SelectionKey.OP_ACCEPT);

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void start()
	{
		this.initServer();
		System.out.println("服务器启动了...");
		while (true)
		{
			Set<SelectionKey> keys = selector.keys();
			System.out.println("keys size:" + keys.size());

			while (true)
			{
				try
				{
					// 调用多路复用器
					// select poll => 内核的select(fd4) poll(fd4)
					// epoll => 内核的epoll_wait() 参数可以带时间
					while ((selector.select(500) > 0))
					{
						// 返回有状态的file descriptor集合
						Set<SelectionKey> selectionKeys = selector.selectedKeys();
						Iterator<SelectionKey> iterator = selectionKeys.iterator();

						// 多路复用器只能给状态，我们还是得一个个处理它们的read/write
						if (iterator.hasNext())
						{
							SelectionKey key = iterator.next();
							iterator.remove();
							// whether this key's channel is ready to accept a new socket connection
							if (key.isAcceptable())
							{
								this.acceptHandler(key);
							}
							else if (key.isReadable())
							{

							}
						}
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * 处理要接收新socket的连接
	 *
	 * @param key
	 */
	public void acceptHandler(SelectionKey key)
	{
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
		try
		{
			// 调用accept接收客户端,假设底层返回的file descriptor为 7
			SocketChannel client = serverSocketChannel.accept();
			// 设置成非阻塞
			client.configureBlocking(false);
			ByteBuffer buffer = ByteBuffer.allocate(8192);

			// 把客户端注册到JDK封装的多路复用器Selector中
			// select poll:会在JVM中开辟一个数组,把fd7放进去,以便下次的使用
			// epoll: 内核中对应epoll_ctl(fd3,ADD,fd7,EPOLLIN),把新的客户端fd7添加到内核中去
			client.register(selector, SelectionKey.OP_READ, buffer);
			System.out.println("新客户端：" + client.getRemoteAddress());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		new SocketMultiple().start();
	}

}
