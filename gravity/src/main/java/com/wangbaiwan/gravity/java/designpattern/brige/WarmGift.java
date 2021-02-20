package com.wangbaiwan.gravity.java.designpattern.brige;

public class WarmGift extends Gift
{
	private GiftImpl giftImpl;
	public WarmGift(GiftImpl giftImpl)
	{
		this.giftImpl = giftImpl;
	}
}
