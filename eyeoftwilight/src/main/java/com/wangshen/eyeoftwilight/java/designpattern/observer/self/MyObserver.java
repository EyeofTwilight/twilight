package com.wangshen.eyeoftwilight.java.designpattern.observer.self;

/**
 * 自定义观察者
 *
 * @author eyeoftwilight
 * @see java.util.Observer
 */
public interface MyObserver
{
	/**
	 * 获取观察的类型
	 *
	 * @return 订阅类型
	 */
	String getMessageType();

	/**
	 * 接收到消息后具体处理
	 *
	 * @param o
	 * @param arg
	 */
	void update(AsynObservable o, Object arg);
}
