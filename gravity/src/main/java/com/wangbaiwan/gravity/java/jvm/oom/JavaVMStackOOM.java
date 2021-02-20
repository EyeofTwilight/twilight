package com.wangbaiwan.gravity.java.jvm.oom;

/**
 * VM Args: -Xss2M 设置每个线程的堆栈大
 * 次代码，请在32位系统中运行
 *
 * @author wangshen
 */
public class JavaVMStackOOM
{
	private void dontStop()
	{
		while (true)
		{
		}
	}

	public void stackLeakByThread()
	{
		while (true)
		{
			Thread thread = new Thread(() -> dontStop());
			thread.start();
		}
	}

	public static void main(String[] args)
	{
		JavaVMStackOOM javaVMStackOOM = new JavaVMStackOOM();
		javaVMStackOOM.stackLeakByThread();
	}
}
