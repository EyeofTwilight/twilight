package com.wangshen.eyeoftwilight.java.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketBIO
{
	public static void main(String[] args) throws IOException
	{
		ServerSocket serverSocket = new ServerSocket(8090);
		System.out.println("step1 new ServerSocket(8090)");
		while (true)
		{
			Socket client = serverSocket.accept();
			System.out.println("step2： client " + client.getPort());
			new Thread(new Runnable()
			{
				Socket ss;

				public Runnable setSS(Socket socket)
				{
					ss = socket;
					return this;
				}

				@Override
				public void run()
				{
					InputStream inputStream = null;
					try
					{
						inputStream = client.getInputStream();

						BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
						while (true)
						{
							System.out.println(reader.readLine());
						}
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}.setSS(client)).start();
		}
	}
}
