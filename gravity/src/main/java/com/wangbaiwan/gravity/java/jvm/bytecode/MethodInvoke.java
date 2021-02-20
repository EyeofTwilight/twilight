package com.wangbaiwan.gravity.java.jvm.bytecode;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * @author Administrator
 */
public class MethodInvoke
{
	class GrandFather
	{
		void thinking()
		{
			System.out.println("I an grandfather");
		}
	}

	class Father extends GrandFather
	{
		@Override
		void thinking()
		{
			System.out.println("I am father");
		}
	}

	class Son extends Father
	{
		@Override
		void thinking()
		{
			MethodType methodType = MethodType.methodType(void.class);
			try
			{
				MethodHandle methodHandle = MethodHandles.lookup().findSpecial(GrandFather.class, "thinking", methodType, getClass());
				methodHandle.invoke(this);
			}
			catch (Throwable e)
			{
				e.printStackTrace();
			}
		}
	}

	class Son2 extends Father
	{
		@Override
		void thinking()
		{
			MethodType methodType = MethodType.methodType(void.class);
			try
			{
				// 查看MethodHandles.Lookup类的代码，将会发现需要进行哪些访问保护，在该API实现时是预留了后门的。
				// 访问保护是通过一个allowedModes的参数来控制，而且这个参数可以被设置成“TRUSTED”来绕开所有的保护措施。
				// 尽管这个参数只是在Java类库本身使用，没有开放给外部设置，但我们通过反射可以轻易打破这种限制。
				Field lookupImpl = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
				lookupImpl.setAccessible(true);
				MethodHandle methodHandle = ((MethodHandles.Lookup) lookupImpl.get(null)).findSpecial(GrandFather.class, "thinking", methodType, GrandFather.class);
				methodHandle.invoke(this);
			}
			catch (Throwable e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args)
	{
		(new MethodInvoke().new Son()).thinking();
		(new MethodInvoke().new Son2()).thinking();
	}
}
