package com.wangbaiwan.gravity.java.designpattern.observer.self.test;

import com.wangbaiwan.gravity.java.designpattern.observer.self.AsynObservable;
import com.wangbaiwan.gravity.java.designpattern.observer.self.MyObserver;
import org.springframework.stereotype.Service;

/**
 * 发送微信通知的观察者
 * <p>
 * 若想在数据库修改开关后，实现实时是否发送消息，
 * 则需要在update方法中，获取实时值，然后判断
 *
 * @author Administrator
 */
@Service
public class WxMyObserver implements MyObserver
{
	private String messageType = "afterOrderPay";

	static
	{
		// true: 可以从配置文件获取数据库中获取的配置,如：是否发送微信消息
		if (true)
		{
			AsynObservable.getInstance().addObserver(new WxMyObserver());
		}
	}

	/**
	 * 获取观察的类型
	 *
	 * @return
	 */
	@Override
	public String getMessageType()
	{
		return messageType;
	}

	/**
	 * 接收到消息后具体处理
	 *
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(AsynObservable o, Object arg)
	{
		System.out.println("当前线程:" + Thread.currentThread().getName() + ",当前接收者为：WxMyObserver，观察对象为：" + o + "; 传递参数为：" + arg);
	}
}
