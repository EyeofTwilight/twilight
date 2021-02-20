package com.wangshen.eyeoftwilight.java.jvm.gc;

/**
 * 引用计数算法 -- HotSpot VM 并不是采用此算法来进行GC
 * 在对象中添加一个引用计数器，每当有一个地方引用它时，计数器值就加一；
 * 当引用失效时，计数器值就减一；
 * 任何时刻计数器为零的对象就是不可能再被使用的。
 * <p>
 * -XX:+PrintGC 输出GC日志
 * -XX:+PrintGCDetails 输出GC的详细日志
 * -XX:+PrintGCTimeStamps 输出GC的时间戳（以基准时间的形式）
 * -XX:+PrintGCDateStamps 输出GC的时间戳（以日期的形式，如 2013-05-04T21:53:59.234+0800）
 * -XX:+PrintHeapAtGC 在进行GC的前后打印出堆的信息
 * -Xloggc:../logs/gc.log 日志文件的输出路径
 *
 * @author eyeoftwilight
 */
public class ReferenceCountingGC
{
	public Object instanceObj;

	private static final int _1MB = 1024 * 1024;

	/**
	 * 用于占点内存，以便能在GC日志中看清楚是否回收过
	 */
	private byte[] bigSize = new byte[2 * _1MB];

	public static void main(String[] args)
	{
		ReferenceCountingGC referenceCountingGC1 = new ReferenceCountingGC();
		ReferenceCountingGC referenceCountingGC2 = new ReferenceCountingGC();

		// 相互循环引用
		referenceCountingGC1.instanceObj = referenceCountingGC2;
		referenceCountingGC2.instanceObj = referenceCountingGC1;

		referenceCountingGC1 = null;
		referenceCountingGC2 = null;

		// 手动触发GC
		System.gc();
	}
}
