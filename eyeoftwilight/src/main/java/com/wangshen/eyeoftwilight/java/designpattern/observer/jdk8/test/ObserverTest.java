package com.wangshen.eyeoftwilight.java.designpattern.observer.jdk8.test;


import com.wangshen.eyeoftwilight.java.designpattern.observer.jdk8.AfterOrderPayObservableImpl;

/**
 * @author Administrator
 */
public class ObserverTest
{
	public static void main(String[] args)
	{
		AfterOrderPayObservableImpl observable = new AfterOrderPayObservableImpl();
		observable.addObserver(new SmsObserver());
		observable.addObserver(new WxObserver());
		observable.setChanged();
		observable.notifyObservers("我是你爸爸");
	}
}
