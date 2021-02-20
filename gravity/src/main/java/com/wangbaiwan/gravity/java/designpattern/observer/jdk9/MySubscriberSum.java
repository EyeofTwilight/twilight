package com.wangbaiwan.gravity.java.designpattern.observer.jdk9;

import java.util.concurrent.Flow;

/**
 * 想实现响应式编程：即
 * int a = 1;
 * int b = 2;
 * int c = a + b;
 * b = 10;
 * <p>
 * 当b变化时,现结果c也是变化的,这就是响应式编程。类似excel中的公式
 * <p>
 * 但具体怎么实现还没知道办法
 *
 * @author Administrator
 */
public class MySubscriberSum<T> implements Flow.Subscriber<T>
{
	private Flow.Subscription subscription;

	private int count;

	private int sum;

	/**
	 * 对于给定的订阅，在调用任何其他Subscriber方法之前调用此方法
	 *
	 * @param subscription
	 */
	@Override
	public void onSubscribe(Flow.Subscription subscription)
	{
		System.out.println("onSubscribe");
		this.subscription = subscription;
		// requesting data from publisher

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
		sum += ((Integer) item).intValue();
		System.out.println("next:" + item);
		// 请求下一个数据,若不加这句,只会onSubscribe中的subscription.request(1)获取到一个数据
		subscription.request(1);
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

	public int getSum()
	{
		return sum;
	}
}
