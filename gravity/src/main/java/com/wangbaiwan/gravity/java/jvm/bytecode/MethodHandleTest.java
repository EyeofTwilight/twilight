package com.wangbaiwan.gravity.java.jvm.bytecode;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * JSP 292 MethodHandle基础用法演示
 *
 * @author eyeoftwilight
 */
public class MethodHandleTest
{
	static class ClassA
	{
		public void println(String s)
		{
			System.out.println(s);
		}
	}

	private static MethodHandle getPrintlnMH(Object receiver) throws NoSuchMethodException, IllegalAccessException
	{
		// MethodType：代表方法类型，包含方法的返回值(第一个参数)，和具体参数(第二个及以后的参数)
		MethodType methodType = MethodType.methodType(void.class, String.class);
		// lookup(): 在指定类中查找符合给定的方法名称、类型，并且符合调用权限的方法句柄。
		// 因为这里调用的是一个虚方法，按照Java语言的规则，方法第一个参数是隐式的，代表该方法的接收者
		// 也即this指向的对象，这个参数以前放在参数列表中进行传递，现在提供bindTo()方法来完成这件事。
		return MethodHandles.lookup().findVirtual(receiver.getClass(), "println", methodType).bindTo(receiver);
	}

	public static void main(String[] args) throws Throwable
	{
		Object object = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();
		getPrintlnMH(object).invokeExact("wangshen");
	}
}
