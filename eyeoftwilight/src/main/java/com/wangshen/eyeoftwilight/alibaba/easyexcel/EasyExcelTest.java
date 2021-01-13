package com.wangshen.eyeoftwilight.alibaba.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
public class EasyExcelTest
{

	public static void main(String[] args) throws IOException
	{
		// 方法1 如果写到同一个sheet
		String fileName = "C:\\Users\\Administrator\\Desktop\\新建文件夹 (2)\\" + "测试" + ".xlsx";
		File file = new File(fileName);
		File parentFile = file.getParentFile();
		if (!parentFile.exists())
		{
			parentFile.mkdirs();
		}
		if (!file.exists())
		{
			file.createNewFile();
		}
		ExcelWriter excelWriter = null;
		try
		{
			// 这里 需要指定写用哪个class去写
			excelWriter = EasyExcel.write(fileName).head(head()).build();
			// 这里注意 如果同一个sheet只要创建一次
			WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
			// 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来
			for (int i = 0; i < 5; i++)
			{
				// 分页去数据库查询数据 这里可以去数据库查询每一页的数据
				List<List<Object>> data = dataList();
				excelWriter.write(data, writeSheet);
			}
		}
		finally
		{
			// 千万别忘记finish 会帮忙关闭流
			if (excelWriter != null)
			{
				excelWriter.finish();
			}
		}

	}

	private static List<List<String>> head()
	{
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> head0 = new ArrayList<String>();
		head0.add("字符串" + System.currentTimeMillis());
		List<String> head1 = new ArrayList<String>();
		head1.add("数字" + System.currentTimeMillis());
		List<String> head2 = new ArrayList<String>();
		head2.add("日期" + System.currentTimeMillis());
		list.add(head0);
		list.add(head1);
		list.add(head2);
		return list;
	}

	private static List<List<Object>> dataList()
	{
		List<List<Object>> list = new ArrayList<>();
		for (int i = 0; i < 10; i++)
		{
			List<Object> data = new ArrayList<>();
			data.add("字符串" + i);
			data.add(new Date());
			data.add(0.56);
			list.add(data);
		}
		return list;
	}
}
