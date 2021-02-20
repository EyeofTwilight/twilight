package com.wangbaiwan.gravity.java.designpattern.brige;

public class BrigeClinet
{
	public static void main(String[] args)
	{
		// 使抽象和实现两个维度同时发展
		Gift gift = new WarmGift(new Flow());
		give(gift);
	}

	private static void give(Gift gift)
	{
		System.out.println("gift is: " + gift);
	}
}
