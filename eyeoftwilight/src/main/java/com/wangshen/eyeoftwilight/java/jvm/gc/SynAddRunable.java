package com.wangshen.eyeoftwilight.java.jvm.gc;

/**
 * 死锁
 * 在JConsole -> 线程 面板中出现死锁标记
 *
 * @author eyeoftwilight
 */
public class SynAddRunable implements Runnable
{
	int a, b;

	public SynAddRunable(int a, int b)
	{
		this.a = a;
		this.b = b;
	}

	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 * <p>
	 * 造成死锁的原因是：Integer.valueOf() 方法处于减少对象创建次数和节省内存的考虑，
	 * 会对数值为-128~127之间的Integer对象进行缓存，如果valueOf()方法传入的参数在这个
	 * 范围内，就直接返回缓存中的对象。也就是说代码中尽管调用了2000次Integer.valueOf()方法，
	 * 但一共只返回了两个不同的Integer对象。
	 * 线程切换时，会出现线程A等待线程B持有的Integer.valueOf(1),线程B又在等待被线程A只有的
	 * Integer.valueOf(2),结果都跑步下去。
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run()
	{
		synchronized (Integer.valueOf(a))
		{
			synchronized (Integer.valueOf(b))
			{
				System.out.println(a + b);
			}
		}
	}

	public static void main(String[] args)
	{
		for (int i = 0; i < 100; i++)
		{
			new Thread(new SynAddRunable(1, 2)).start();
			new Thread(new SynAddRunable(2, 1)).start();
		}
	}
}
