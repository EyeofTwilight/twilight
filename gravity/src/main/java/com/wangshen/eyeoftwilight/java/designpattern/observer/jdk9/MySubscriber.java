package com.wangshen.eyeoftwilight.java.designpattern.observer.jdk9;

import java.util.concurrent.Flow;

/**
 * @author Administrator
 */
public class MySubscriber<T> implements Flow.Subscriber<T>
{
	private Flow.Subscription subscription;

	private int count;

	/**
	 * 对于给定的订阅，在调用任何其他Subscriber方法之前调用此方法
	 *
	 * @param subscription
	 */
	@Override
	public void onSubscribe(Flow.Subscription subscription)
	{
		this.subscription = subscription;
		// requesting data from publisher
		// 关于request中的数量问题，可参考： https://www.kdocs.cn/p/50845266767?from=docs&source=docsWeb
		subscription.request(1);
		System.out.println("onSubscribe requested 1 item");
	}

	/**
	 * 得到的数据都会经过next方法
	 * <p>
	 * 订阅下一个项目调用此方法
	 *
	 * @param item
	 */
	@Override
	public void onNext(T item)
	{
		++count;
		System.out.println("next:" + item);
		if (count == 2)
		{
			subscription.cancel();
			subscription.request(1);
		}
		else
		{
			subscription.request(1);
		}

		// 请求下一个数据,若不加这句,只会onSubscribe中的subscription.request(1)获取到一个数据

		// 也可在此cancel 表示不再接收Publisher推送的消息
		//		subscription.cancel();
	}

	/**
	 * 在Publisher或Subscriber遇到不可恢复的错误时调用此方法，
	 * 之后Subscription不会再调用Subscriber其他的方法
	 *
	 * @param throwable
	 */
	@Override
	public void onError(Throwable throwable)
	{
		throwable.printStackTrace();
	}

	/**
	 * 已完成，不会再调用Subscriber的其他方法
	 */
	@Override
	public void onComplete()
	{
		System.out.println("completed");
	}

	/**
	 * 获取接收到的Publisher推送的元素个数
	 *
	 * @return
	 */
	public int getCount()
	{
		return count;
	}
}
