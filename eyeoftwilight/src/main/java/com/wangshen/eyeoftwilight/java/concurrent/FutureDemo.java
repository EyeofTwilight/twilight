package com.wangshen.eyeoftwilight.java.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 处理有返回值的异步调用
 *
 * @author Administrator
 */
public class FutureDemo
{
	private ExecutorService executorService = new ThreadPoolExecutor(100, 200, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

	public static void main(String[] args) throws ExecutionException, InterruptedException
	{
		new FutureDemo().aaa();
	}

	public void aaa() throws InterruptedException, ExecutionException
	{
		Callable<Map<String, Object>> callable = () ->
		{
			Map<String, Object> map = new HashMap<>(16);
			map.put("result", 123);
			return map;
		};
		FutureTask<Map<String, Object>> future = new FutureTask<>(callable);
		executorService.submit(future);
		Thread.sleep(1000);
		if (future.isDone())
		{
			System.out.println(future.get());
		}
	}
}
