package com.wangshen.eyeoftwilight.java.jvm.gc;

/**
 * Java自带的集成多功能工具箱JHSDB
 * 由于JHSDB本身堆压缩指针的支持存在
 * <p>
 * VM Options: -Xmx10M -XX:+UseSerialGC -XX:-UseCompressedOops
 *
 * @author eyeoftwilight
 */
public class JHsdb
{
	static class Test
	{
		static ObjectHolder staticObj = new ObjectHolder();
		ObjectHolder instanceObj = new ObjectHolder();

		void foo()
		{
			ObjectHolder localObj = new ObjectHolder();
			System.out.println("done");
		}
	}

	private static class ObjectHolder {}

	public static void main(String[] args)
	{
		Test test = new Test();
		test.foo();
	}
}
