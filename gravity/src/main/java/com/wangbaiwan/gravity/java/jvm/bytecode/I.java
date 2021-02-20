package com.wangbaiwan.gravity.java.jvm.bytecode;

/**
 * @author shangmi
 */
public class I
{
	public static void main(String[] args)
	{
		int i = 0;
		for (int j = 0; j < 10; j++)
		{
			i = (i++);
		}
		System.out.println(i);
	}
}
