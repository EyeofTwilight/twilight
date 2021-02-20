package com.wangbaiwan.gravity.java.jvm.gc;

/**
 * 在IDEA中执行main方法，程序启动IDEA也会使用用HashMap等对象用于数据存储，导致测试不准确。所以，一定要在命令行测试。
 * PS + PS MarkSweep 为JDK1.8默认的垃圾收集器
 * PS：Parallel Scavenge收集器 作用于Young Generation,使用标记-复制算法(准确说是：Apple式回收算法)
 * PS MarkSweep： 收集器 作用Old Generation,是Serial Old收集器的使用标记-清除算法版本
 * <p>
 * Allocation Failure：表明引起每次GC的原因是Young Generation中没有足够的空间存储新的数据了
 *
 * @author eyeoftwilight
 */
public class PSPO
{
	private static final int _1MB = 1024 * 1024;

	/**
	 * -Xmn10M 表示Young Generation 占10M
	 * VM options: -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		byte[] allocation1, allocation2, allocation3, allocation4;
		allocation1 = new byte[2 * _1MB];
		allocation2 = new byte[2 * _1MB];
		allocation3 = new byte[2 * _1MB];
		allocation4 = new byte[4 * _1MB];
	}

	/**
	 * VM Options:
	 * -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
	 */
	public void testTenuringThreshold()
	{
		byte[] allocation1, allocation2, allocation3;
		allocation1 = new byte[_1MB / 4];
		allocation2 = new byte[4 * _1MB];
		allocation3 = new byte[4 * _1MB];
		allocation3 = null;
		allocation3 = new byte[4 * _1MB];
	}

	/**
	 * 如果Survivor空间中相同年龄所有对象大小总和大于等于Survivor空间的一半，
	 * 年龄大于或等于该年龄的对象就可以直接进入老年代，无需等到-XX:MaxTenuringThreshold中要求的年龄
	 * <p>
	 * VM Options: -Xms20M -Xmx20M -Xmn10M  -XX:+PrintGCDetails -XX:+UseSerialGC -XX:SurvivorRatio=8
	 */
	public void testTenuringThreshold2()
	{
		byte[] allocation1, allocation2, allocation3, allocation4;
		allocation1 = new byte[_1MB / 4];
		allocation2 = new byte[_1MB / 4];
		allocation3 = new byte[4 * _1MB];
		allocation4 = new byte[4 * _1MB];
		allocation4 = null;
		allocation3 = new byte[4 * _1MB];
	}

	/**
	 * 空间分配担保
	 * JDK 6 Update 24后HandlePromotionFailure参数不再使用，
	 * 只要老年代的连续空间大于新生代对象总和大小或者历次晋升的平均大小，
	 * 就会进行Manor GC，否则Full GC
	 *
	 * <p>
	 * VM Options: -Xms20M -Xmx20M -Xmn10M -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+UseSerialGC -XX:-HandlePromotionFailure
	 */
	public void testHandlePromotion()
	{
		byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6, allocation7;
		allocation1 = new byte[2 * _1MB];
		allocation2 = new byte[2 * _1MB];
		allocation3 = new byte[2 * _1MB];
		allocation1 = null;
		allocation4 = new byte[2 * _1MB];
		allocation5 = new byte[2 * _1MB];
		allocation6 = new byte[2 * _1MB];
		allocation4 = null;
		allocation5 = null;
		allocation6 = null;
		allocation7 = new byte[2 * _1MB];
	}
}
