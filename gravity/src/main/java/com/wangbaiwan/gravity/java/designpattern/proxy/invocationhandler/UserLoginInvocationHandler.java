package com.wangbaiwan.gravity.java.designpattern.proxy.invocationhandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UserLoginInvocationHandler implements InvocationHandler
{
	private UserLogin userLogin;

	public UserLoginInvocationHandler(UserLogin userLogin)
	{
		this.userLogin = userLogin;
	}

	/**
	 * @param proxy  为动态生成的代理对象, 一般无用
	 * @param method
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		System.out.println("开始");
		Object o = method.invoke(userLogin, args);
		System.out.println("结束");
		return o;
	}
}

