package com.wangshen.eyeoftwilight.java.designpattern.observer.jdk8.test;

import java.util.Observable;
import java.util.Observer;


/**
 * 发送短信的 观察者
 *
 * @author Administrator
 */
public class SmsObserver implements Observer
{
	@Override
	public void update(Observable o, Object arg)
	{
		System.out.println("当前接受者为：SmsObserver，观察对象为：" + o + "; 传递参数为：" + arg);
	}
}
