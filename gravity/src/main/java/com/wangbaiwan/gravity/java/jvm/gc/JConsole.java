package com.wangbaiwan.gravity.java.jvm.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xms100M -Xmx100M -XX:+UseSerialGC
 *
 * @author eyeoftwilight
 */
public class JConsole
{
	static class OOMObject
	{
		public byte[] placeholder = new byte[64 * 1024];
	}

	public static void fillHeap(int number) throws InterruptedException
	{
		List<OOMObject> list = new ArrayList<>();
		for (int i = 0; i < number; i++)
		{
			// 稍作延时，令监视曲线的变换更加明显
			Thread.sleep(50);
			list.add(new OOMObject());
		}
		System.gc();
	}

	public static void main(String[] args) throws InterruptedException
	{
		fillHeap(1000);
	}
}
