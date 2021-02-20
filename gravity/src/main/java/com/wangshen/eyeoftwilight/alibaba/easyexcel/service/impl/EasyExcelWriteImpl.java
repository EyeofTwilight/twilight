package com.wangshen.eyeoftwilight.alibaba.easyexcel.service.impl;



import com.wangshen.eyeoftwilight.alibaba.easyexcel.service.EasyExcelService;
import com.wangshen.eyeoftwilight.alibaba.easyexcel.util.EasyExcelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * easyexcel 导出
 *
 * @author 往事，优雅而已
 * @date 2019-07-27
 */
public class EasyExcelWriteImpl implements EasyExcelService
{
	public static void main(String[] args)
	{
		new EasyExcelWriteImpl().writeV2007();
	}

	/**
	 * 无模板生成excel
	 */
	@Override
	public void writeV2007()
	{

		String filePathName = "v2007";

		// 列标头
		List<List<String>> head = new ArrayList<>();
		List<String> headCol1 = new ArrayList<>();
		List<String> headCol2 = new ArrayList<>();
		List<String> headCol3 = new ArrayList<>();
		headCol1.add("第一列");
		headCol2.add("第二列");
		headCol3.add("第三列");
		head.add(headCol1);
		head.add(headCol2);
		head.add(headCol3);

		// 3.组装数据
		List<List<Object>> data = new ArrayList<>();
		for (int i = 0; i < 100; i++)
		{
			// 一个item指一行数据
			List<Object> item = new ArrayList<>();
			item.add("item0" + i);
			item.add("item1" + i);
			item.add("item2" + i);
			data.add(item);
		}

		EasyExcelUtil.writeV2007(filePathName, null, null, data);
	}
}
