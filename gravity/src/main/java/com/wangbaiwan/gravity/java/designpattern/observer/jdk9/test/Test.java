package com.wangbaiwan.gravity.java.designpattern.observer.jdk9.test;

import com.wangbaiwan.gravity.java.designpattern.observer.jdk9.MySubscriber;
import com.wangbaiwan.gravity.java.designpattern.observer.jdk9.MySubscriberSum;

import java.util.Arrays;
import java.util.concurrent.SubmissionPublisher;

/**
 * Publisher 和 Subscriber 中也可添加一个或多个java.concurrent.Flow.Processor
 * 可参照： https://www.journaldev.com/20723/java-9-reactive-streams
 *
 * @author Administrator
 */
public class Test
{
	public static void main(String[] args) throws InterruptedException
	{
		SubmissionPublisher publisher = new SubmissionPublisher();

		// 注册订阅者
		MySubscriber mySubscriber = new MySubscriber();
		publisher.subscribe(mySubscriber);

		String[] arr = {"哇哈哈", "可乐", "康师傅", "农夫山泉", "脉动"};
		// 发布数据
		Arrays.stream(arr).forEach(publisher::submit);

		while (arr.length != mySubscriber.getCount())
		{
			// 因为是异步的流处理,不加sleep,可能什么都看不到
			// 也可以在Subscribe中加入同步策略
			// We should always close publisher to avoid any memory leaks
			Thread.sleep(10);
		}

		// 关闭,没有这个，则Subscribe.onComplete不会调用
		publisher.close();
	}

	/**
	 * relative Streams 中的变化监测
	 *
	 * @throws InterruptedException
	 */
	@org.junit.Test
	public void testMySubscriberSum() throws InterruptedException
	{
		SubmissionPublisher publisher2 = new SubmissionPublisher();

		// 注册订阅者
		MySubscriberSum<Integer> mySubscriberSum = new MySubscriberSum();
		publisher2.subscribe(mySubscriberSum);

		Integer[] arr = {1, 2, 3, 4, 5};
		// 发布数据
		Arrays.stream(arr).forEach(publisher2::submit);
		while (arr.length != mySubscriberSum.getCount())
		{
			// 因为是异步的流处理,不加sleep,可能什么都看不到
			// 也可以在Subscribe中加入同步策略
			// We should always close publisher to avoid any memory leaks
			Thread.sleep(10);
		}
		arr[1] = 10;
		Thread.sleep(2000);
		System.out.println(mySubscriberSum.getSum());

		// 关闭,没有这个，则Subscribe.onComplete不会调用
		publisher2.close();
	}
}
