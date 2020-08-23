package com.wangshen.eyeoftwilight.java.jvm.gc;

import java.util.Map;

/**
 * 服务器所有线程信息
 *
 * @author eyeoftwilight
 */
public class AllStackTrace
{
	public static void main(String[] args)
	{
		Map<Thread, StackTraceElement[]> stackTraces = Thread.getAllStackTraces();
		for (Map.Entry<Thread, StackTraceElement[]> stackTrace : stackTraces.entrySet())
		{
			Thread thread = stackTrace.getKey();
			StackTraceElement[] elements = stackTrace.getValue();
			if (thread.equals(Thread.currentThread()))
			{
				continue;
			}
			System.out.println("线程：" + thread.getName());
			for (StackTraceElement element : elements)
			{
				System.out.println(element);
			}
		}
	}
}
