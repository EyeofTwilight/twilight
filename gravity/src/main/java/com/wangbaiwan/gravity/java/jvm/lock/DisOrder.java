package com.wangbaiwan.gravity.java.jvm.lock;

public class DisOrder
{
	private static final int _1MB = 1024 * 1024;

	/**
	 * -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		byte[] allo1, allo2, allo3, allo4;
		allo1 = new byte[_1MB / 4];
		allo2 = new byte[4 * _1MB];
		allo3 = new byte[4 * _1MB];
		allo3 = null;
		allo3 = new byte[4 * _1MB];
	}
}
