package com.wangshen.eyeoftwilight.java.designpattern.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB
 *
 * @author Administrator
 */
public class UserLoginMethodInterceptor implements MethodInterceptor
{
	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable
	{
		System.out.println(o.getClass().getSuperclass().getName());
		System.out.println("before");
		// 注意是invokeSuper 而不是invoke, 否则会内存溢出
		Object result = methodProxy.invokeSuper(o, objects);
		System.out.println("after");
		return result;
	}
}
