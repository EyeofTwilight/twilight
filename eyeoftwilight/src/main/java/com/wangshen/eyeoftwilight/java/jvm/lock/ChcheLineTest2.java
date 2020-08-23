package com.wangshen.eyeoftwilight.java.jvm.lock;

public class ChcheLineTest2
{
	private static final int _1MB = 1024 * 1024;

	public static void main(String[] args)
	{
		byte[] allo1, allo2, allo3, allo4;
		allo1 = new byte[2 * _1MB];
		allo2 = new byte[2 * _1MB];
		allo3 = new byte[2 * _1MB];
		allo4 = new byte[4 * _1MB];
	}

	// 若无元素可取出，会打断当前线程


	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
	}
}
