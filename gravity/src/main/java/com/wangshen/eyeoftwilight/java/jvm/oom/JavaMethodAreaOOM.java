package com.wangshen.eyeoftwilight.java.jvm.oom;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * 方法区OOM
 * 方法区的主要职责是用于存放类型的相关信息，如类名、访问修饰符、常量池、字段描述、方法描述等
 * HotSpot从JDK 7开始逐步“去永久代”的计划，并在JDK 8中完全使用元空间来代替永久代
 * JDK6 VM Args: -XX:PermSize=10M -XX:PermSize=10M
 * JDK7及以上 VM Args: -Xms10M -Xmx10M
 *
 * @author wangshen
 */
public class JavaMethodAreaOOM
{
	static class JavaMethodAreaOOMbject
	{
	}

	/**
	 * 使用 CGLib 动态生成指定类的大量代理类。
	 * 从而这些类的相关信息：类名、访问修饰符等填充方法区。
	 * 从而导致方法区OOM
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		while (true)
		{
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(JavaMethodAreaOOMbject.class);
			enhancer.setUseCache(false);
			enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> methodProxy.invokeSuper(o, args));
			enhancer.create();
			System.out.println(enhancer.create());
		}
	}
}
