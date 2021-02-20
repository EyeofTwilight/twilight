package com.wangbaiwan.gravity.java.jvm.bytecode;

public class FieldHasNoPolymorphic
{
	// 1.0 乘以2的负53次幂
	//  static 表示不用实例化，也可访问该字段，一般时公共方法
	// 若方法不是static修饰，也可以访问static修饰的字段
	// 可参照Random的multiplier字段查看
	private static final double DOUBLE_UNIT = 0x1.0p-53;

	public void aa()
	{
		System.out.println(DOUBLE_UNIT);
	}

	static class Father
	{
		public int money = 1;

		public Father()
		{
			money = 2;
			showMeTheMoney();
		}

		public void showMeTheMoney()
		{
			System.out.println("I am Father , i have $" + money);
		}
	}

	static class Son extends Father
	{
		public int money = 3;

		public Son()
		{
			money = 4;
			showMeTheMoney();
		}

		@Override
		public void showMeTheMoney()
		{
			System.out.println("I am son, I have $" + money);
		}
	}

	public static void main(String[] args)
	{
		Father gay = new Son();
		System.out.println("The gay have $" + gay.money);
		System.out.println(DOUBLE_UNIT);
	}
}
