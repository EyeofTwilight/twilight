package com.wangshen.eyeoftwilight.java.designpattern.observer.jdk8;

import java.util.Observable;

/**
 * 订单付完款后 可观察对象
 *
 * @author Administrator
 */
public class AfterOrderPayObservableImpl extends Observable
{

	/**
	 * 继承的java.util.Observable中此方法为protect修饰，只能同一包中的访问
	 * 这里设置成可公共访问
	 */
	@Override
	public synchronized void setChanged()
	{
		super.setChanged();
	}
}
