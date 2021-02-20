package com.wangbaiwan.gravity.java.designpattern.proxy.invocationhandler;

public class UserLoginImpl implements UserLogin
{
	@Override
	public void login()
	{
		System.out.println("登录成功");
	}
}
