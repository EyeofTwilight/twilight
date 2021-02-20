package com.wangshen.eyeoftwilight.java.designpattern.observer.jdk8.test;

import java.util.Observable;
import java.util.Observer;

/**
 * 发送微信通知的观察者
 *
 * @author Administrator
 */
public class WxObserver implements Observer
{
	@Override
	public void update(Observable o, Object arg)
	{
		System.out.println("当前接收者为：WxObserver，观察对象为：" + o + "; 传递参数为：" + arg);
	}
}
