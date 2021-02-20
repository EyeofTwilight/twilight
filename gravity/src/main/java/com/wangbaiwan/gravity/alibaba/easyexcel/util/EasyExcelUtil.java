package com.wangbaiwan.gravity.alibaba.easyexcel.util;


import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * easyexcel 导出
 *
 * @author 往事，优雅而已
 * @date 2019-07-27
 */
public class EasyExcelUtil
{

	/**
	 * 无模板生成excel
	 * 类型：.xlsx
	 *
	 * @param fileName    要生成的文件名称,无需添加后缀
	 * @param columnWidth 列宽,下标从0开始,为null时,设置自适应宽度
	 *                    Map<Integer, Integer> columnWidth = new HashMap<>();
	 *                    columnWidth.put(0, 10000);
	 *                    columnWidth.put(1, 40000);
	 *                    columnWidth.put(2, 10000);
	 * @param head        列名，为null时，不会生成列名字
	 *                    List<List<String>> head = new ArrayList<>();
	 *                    List<String> headCol1 = new ArrayList<>();
	 *                    List<String> headCol2 = new ArrayList<>();
	 *                    headCol1.add("第一列");
	 *                    headCol2.add("第二列");
	 *                    head.add(headCol1);
	 *                    head.add(headCol1);
	 *                    sheet1.setHead(head);
	 * @param data        要写入excel中的数据
	 *                    List<List<Object>> data = new ArrayList<>();
	 *                    for (int i = 0; i < 100; i++)
	 *                    {
	 *                    // 一个item指一行数据
	 *                    List<Object> item = new ArrayList<>();
	 *                    item.add("item0" + i);
	 *                    item.add("item1" + i);
	 *                    item.add("item2" + i);
	 *                    data.add(item);
	 *                    }
	 */
	public static void writeV2007(String fileName, Map<Integer, Integer> columnWidth, List<List<String>> head, List<List<Object>> data)
	{
		OutputStream out = null;
		try
		{
			// 创建文件路径
			String classpath = ResourceUtils.getURL("").getPath();
			File filePath = new File(classpath + "/easyexcel/");
			if (!filePath.exists())
			{
				filePath.mkdirs();
			}

			// 创建文件
			File file = new File(classpath + "/easyexcel/" + fileName + ".xlsx");
			if (!filePath.exists())
			{
				file.createNewFile();
			}
			out = new FileOutputStream(file);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		/* 根据设置把数据写入excel */
		ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);

		//写第一个sheet, sheet1
		Sheet sheet1 = new Sheet(1, 3);
		sheet1.setSheetName("第一个sheet");

		// 1.设置列宽 设置每列的宽度
		if (columnWidth == null)
		{
			// 设置自适应宽度
			sheet1.setAutoWidth(Boolean.TRUE);
		}
		else
		{
			sheet1.setColumnWidthMap(columnWidth);
		}

		// 2.设置列标头
		sheet1.setHead(head);

		// 3.组装数据

		// 4.写入excel
		writer.write1(data, sheet1);

		// 5.关闭资源
		writer.finish();
		try
		{
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
