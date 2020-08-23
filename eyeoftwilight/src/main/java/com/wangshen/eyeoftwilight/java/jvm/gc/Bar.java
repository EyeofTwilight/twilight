package com.wangshen.eyeoftwilight.java.jvm.gc;

/**
 * VM options:
 * -XX:UseParallelGC -XX:+UnlockDiagnosticVMOptions -XX:+TraceClassLoading -XX:+LogCompilation
 * -XX:LogFile=/home/wangshen/Files/Log/logfile.log -XX:+PrintAssembly -XX:+TraceClassLoading
 * -Xcomp -XX:CompileCommand=dontinline,*Bar.sum -XX:CompileCommand=compileonly,*Bar.sum
 * <p>
 * 使用JITWatch查看时要反汇编整个文件，而不是其中的一个方法
 */
// -XX:+UseParallelGC -XX:+UnlockDiagnosticVMOptions -XX:+TraceClassLoading -XX:+LogCompilation -XX:LogFile=/home/wangshen/Files/Log/logfile.log -XX:+PrintAssembly -XX:+TraceClassLoading -Xcomp -XX:CompileCommand=dontinline,*Bar.sum -XX:CompileCommand=compileonly,*Bar.sum
public class Bar
{
	int a = 1;
	static int b = 2;

	public int sum(int c)
	{
		return a + b + c;
	}

	public static void main(String[] args)
	{
		new Bar().sum(3);
	}
}
