package com.wangshen.eyeoftwilight.java.jvm.bytecode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理的简单示例
 *
 * @author
 */
public class DynamicProxyTest
{
	interface IHello
	{
		void sayHello();
	}

	static class Hello implements IHello
	{
		@Override
		public void sayHello()
		{
			System.out.println("Hello World!");
		}
	}

	static class DynamicProxy implements InvocationHandler
	{
		// 动态代理，必须要持有被代理对象
		private Object target;

		Object bind(Object target)
		{
			this.target = target;
			return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
		{
			System.out.println("Welcome ");
			return method.invoke(target, args);
		}
	}

	public static void main(String[] args)
	{
		System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
		IHello iHello = (IHello) new DynamicProxy().bind(new Hello());
		iHello.sayHello();
	}
}
