package com.wangbaiwan.gravity.google.thumbnailator;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

/**
 * 谷歌缩略图生成器
 *
 * @author Administrator
 */
public class ThumbnailatorDemo
{
	public static void main(String[] args)
	{
		String original = "C:\\Users\\Administrator\\Desktop\\微信图片_20191202195925.jpg";
		try
		{
			Thumbnails.of(original).scale(1L).outputQuality(0.25).toFile(original);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
