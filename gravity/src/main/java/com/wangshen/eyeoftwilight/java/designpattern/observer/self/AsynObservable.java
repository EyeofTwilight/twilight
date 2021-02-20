package com.wangshen.eyeoftwilight.java.designpattern.observer.self;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.*;

/**
 * 异步的可观察对象
 *
 * @author Administrator
 * @see java.util.Observable
 */
public class AsynObservable
{
	/**
	 * 观察者 key为类型
	 */
	private Map<String, List<MyObserver>> obsMap = new ConcurrentHashMap<>(16);

	/**
	 * 获取计算机有几个核
	 */
	private int processors = Runtime.getRuntime().availableProcessors();

	/**
	 * 创建线程池:
	 * 参数：
	 * 核心线程数：计算机内核数
	 * 最大线程数：计算机内核数*5
	 * 空闲时间：60s，超过60s超过核心线程数的空闲线程被杀死
	 * 任务队列长度：200
	 * 线程池工厂：使用了jdk默认工厂
	 * handler（队列满时的任务拒绝策略）：让提交任务的线程去执行
	 */
	private ExecutorService executorService = new ThreadPoolExecutor(processors, processors * 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(200), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

	private static volatile AsynObservable asynObservable;

	/**
	 * 获取单例对象
	 *
	 * @return
	 */
	public static AsynObservable getInstance()
	{
		if (asynObservable == null)
		{
			synchronized (AsynObservable.class)
			{
				if (asynObservable == null)
				{
					asynObservable = new AsynObservable();
				}
			}
		}
		return asynObservable;
	}

	/**
	 * 添加观察者
	 *
	 * @param o
	 */
	public void addObserver(MyObserver o)
	{
		if (o == null)
		{
			throw new NullPointerException("添加观察者失败! 观察者不能为null");
		}
		String messageType = o.getMessageType();
		if (messageType == null)
		{
			throw new IllegalArgumentException("添加观察者失败! 订阅消息类型不能为null");
		}
		// 真正的添加
		List<MyObserver> obs = obsMap.get(messageType);
		if (obs == null)
		{
			obs = new Vector<>();
			obs.add(o);
			obsMap.put(messageType, obs);
		}
		else
		{
			obs.add(o);
		}
	}

	/**
	 * 移除观察者
	 *
	 * @param o
	 */
	public void deleteObserver(MyObserver o)
	{
		if (o != null && o.getMessageType() != null)
		{
			List<MyObserver> obs = obsMap.get(o.getMessageType());
			if (obs != null)
			{
				obs.remove(o);
			}
		}
	}

	/**
	 * 异步通知指定类型的观察者
	 *
	 * @param messageType 消息类型
	 * @param arg         消息内容
	 */
	public void notifyObservers(String messageType, Object arg)
	{
		List<MyObserver> obs = obsMap.get(messageType);
		if (obs == null)
		{
			return;
		}
		obs.forEach(ob -> this.executorService.submit(() ->
		{
			try
			{
				ob.update(this, arg);
			}
			catch (Exception e)
			{
				System.out.println("通知观察者失败: " + e.getMessage());
			}
		}));
	}

	/**
	 * 同步通知指定类型的观察者
	 *
	 * @param arg 消息内容
	 */
	public void notifyObserversSyn(String messageType, Object arg)
	{
		obsMap.get(messageType).forEach(ob -> ob.update(this, arg));
	}
}
