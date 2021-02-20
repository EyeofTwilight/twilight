package com.wangbaiwan.gravity.java.designpattern.observer.jdk9;

import java.util.Iterator;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

/**
 * 源自： https://www.youtube.com/watch?v=_stAxdjx8qk
 *
 * @author Administrator
 */
public class SimplePublisher implements Flow.Publisher<Integer>
{
	/**
	 * 用于存储向下游(订阅者)发送的所有数据
	 */
	private final Iterator<Integer> iterator;

	public SimplePublisher(int count)
	{
		this.iterator = IntStream.rangeClosed(1, count).iterator();
	}

	@Override
	public void subscribe(Flow.Subscriber<? super Integer> subscriber)
	{
		subscriber.onSubscribe(new SimpleSubscription(subscriber));
	}

	/**
	 * Flow.Subscription 用于
	 * 1.处理请求、背压和处理下游和其他
	 * 2.取消
	 */
	private class SimpleSubscription implements Flow.Subscription
	{
		private final Flow.Subscriber<? super Integer> subscriber;
		private AtomicBoolean terminated = new AtomicBoolean(false);

		public SimpleSubscription(Flow.Subscriber<? super Integer> subscriber)
		{
			this.subscriber = subscriber;
		}

		/**
		 * @param n 向下游(订阅者)发送的元素数量
		 */
		@Override
		public void request(long n)
		{
			if (n <= 0)
			{
				this.subscriber.onError(new IllegalArgumentException());
			}

			for (long demand = n; demand > 0 && iterator.hasNext() && !this.terminated.get(); --demand)
			{
				this.subscriber.onNext(iterator.next());
			}

			/**
			 * 当订阅者调用cancel() 则也不应就绪走onComplete()方法
			 * 当向下游的所有数据都已发送完成,说明整个是终止的
			 */
			if (!this.terminated.getAndSet(true))
			{
				this.subscriber.onComplete();
			}
		}

		@Override
		public void cancel()
		{
			this.terminated.set(true);
		}
	}

	/**
	 * 测试自己写的Publisher
	 */
	@org.junit.Test
	public void testMyselfPublisher()
	{
		SimplePublisher simplePublisher = new SimplePublisher(10);
		simplePublisher.subscribe(new Flow.Subscriber<>()
		{
			@Override
			public void onSubscribe(Flow.Subscription subscription)
			{
				subscription.request(10);
			}

			@Override
			public void onNext(Integer item)
			{
				System.out.println("onNext: " + item);
			}

			@Override
			public void onError(Throwable throwable)
			{
				System.out.println("onError:" + throwable);
			}

			@Override
			public void onComplete()
			{
				System.out.println("onComplete");
			}
		});
	}
}
