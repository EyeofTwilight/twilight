package com.wangbaiwan.gravity.java.jvm.oom;

import java.util.HashSet;
import java.util.Set;

/**
 * HotSpot从JDK 7开始逐步“去永久代”的计划，并在JDK 8中完全使用元空间来代替永久代
 *
 * @author Administrator
 */
public class RuntimeConstantPoolOOM
{
	/**
	 * JDK6 运行 VM Args: -XX:PermSize=6M -XX:MaxPermSize=6M
	 * JDK7及以上 VM Args:-Xmx6M
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		Set<String> set = new HashSet<>();
		short i = 0;
		while (true)
		{
			set.add(String.valueOf(i++).intern());
		}
	}

	/**
	 * 关于java这个字符串，有趣的测试
	 */
	public void aa()
	{
		// true
		String str1 = new StringBuilder("计算").append("软件").toString();
		System.out.println(String.valueOf(str1.intern() == str1));

		// false
		// 在加载sun.misc.Version类时字符串java回进入常量池
		String str2 = new StringBuilder("ja").append("va").toString();
		System.out.println(str2.intern() == str2);
	}
}
