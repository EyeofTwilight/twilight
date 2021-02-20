package com.wangbaiwan.gravity.java.jvm.volatile1;

/**
 * @author Administrator
 */
public class VolatileDemo
{
	public static volatile int race = 0;

	public static void increase()
	{
		race++;
	}

	private static final int THREADS_COUNT = 20;

	public static void main(String[] args)
	{
		Thread[] threads = new Thread[THREADS_COUNT];
		for (Thread thread : threads)
		{
			thread = new Thread(() ->
			{
				for (int i = 0; i < 10_000; i++)
				{
					increase();
				}
			});
			thread.start();
		}

		while (Thread.activeCount() > 2)
		{
			Thread.yield();
		}
		System.out.println(race);
	}
}
