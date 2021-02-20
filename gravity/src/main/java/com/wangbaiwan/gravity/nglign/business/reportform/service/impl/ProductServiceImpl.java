package com.wangbaiwan.gravity.nglign.business.reportform.service.impl;

import com.wangbaiwan.gravity.nglign.business.reportform.service.ProductService;
import com.wangbaiwan.gravity.nglign.common.vo.R;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Service
public class ProductServiceImpl implements ProductService
{
	/**
	 * 获取商品的图表数据
	 *
	 * @return
	 */
	@Override
	public R chart()
	{
		Map<String, Object> map = new HashMap<>(16);
		map.put("salesTypeData", null);
		return null;
	}
}
