package com.wangbaiwan.gravity.java.designpattern.observer.self.test;


import com.wangbaiwan.gravity.java.designpattern.observer.self.AsynObservable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 异步观察者模式 测试类
 * <p>
 * 必须在Spring环境中测试,
 * 否则SmsMyObserver等类的static代码块还没有执行，没有添加观察者,也就不会通知
 *
 * @author eyeoftwilight
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AsyncObserverTest
{
	@Test
	public void test()
	{
		AsynObservable.getInstance().notifyObservers("afterOrderPay", "异步通知成功");
	}
}
