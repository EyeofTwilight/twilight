package com.wangshen.eyeoftwilight.java.designpattern.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author Administrator
 */
public class CGLibTest
{
	public static void main(String[] args)
	{
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(UserLoginImpl.class);
		enhancer.setCallback(new UserLoginMethodInterceptor());
		UserLoginImpl proxyLogin = (UserLoginImpl) enhancer.create();
		proxyLogin.login();
	}
}
