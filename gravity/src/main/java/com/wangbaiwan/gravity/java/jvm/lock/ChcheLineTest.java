package com.wangbaiwan.gravity.java.jvm.lock;

import java.util.concurrent.locks.LockSupport;

public class ChcheLineTest
{
	private static Thread thread1, thread2;

	/**
	 * 用两个线程，一个输出字母一个输出数字，交替输出1A2B3C4D...26Z
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		char[] chars1 = "1234567".toCharArray();
		char[] chars2 = "ABCDEFG".toCharArray();
		//  先使用LockSupport.unpark(thread2);thread2还未睡就先叫醒thread2，
		// thread2执行LockSupport.park()，发现有人叫过它，它就不睡了，接着执行
		// 此例子中未使用到这点
		thread1 = new Thread(() ->
		{
			for (char c : chars1)
			{
				System.out.print(c);
				LockSupport.unpark(thread2); // 叫醒thread2
				LockSupport.park(); // 阻塞当前线程
			}
		});
		thread2 = new Thread(() ->
		{
			for (char c : chars2)
			{
				LockSupport.park();
				System.out.print(c);
				LockSupport.unpark(thread1);
			}
		});
		thread1.start();
		thread2.start();
	}
}
