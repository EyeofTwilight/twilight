package com.wangshen.eyeoftwilight.java.designpattern.proxy.invocationhandler;

import java.lang.reflect.Proxy;

/**
 * 动态代理测试
 * <p>
 * 必须有接口
 *
 * @author Administrator
 */
public class ProxyTest
{
	public static void main(String[] args)
	{
		// 开启保存生成的代理类
		System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");

		UserLogin userLogin = new UserLoginImpl();
		UserLogin userLoginProxy = (UserLogin) Proxy.newProxyInstance(UserLogin.class.getClassLoader(),
				new Class[]{UserLogin.class},
				new UserLoginInvocationHandler(userLogin));
		userLoginProxy.login();
	}
}
