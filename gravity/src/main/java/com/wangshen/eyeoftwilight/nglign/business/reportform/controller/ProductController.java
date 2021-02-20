package com.wangshen.eyeoftwilight.nglign.business.reportform.controller;

import com.wangshen.eyeoftwilight.nglign.business.reportform.service.ProductService;
import com.wangshen.eyeoftwilight.nglign.common.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品报表
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/product")
public class ProductController
{
	@Autowired
	private ProductService productService;

	/**
	 * 获取商品的图表数据
	 *
	 * @return
	 */
	@RequestMapping("/chart")
	public R chart()
	{
		return productService.chart();
	}

	/**
	 * 获取商品标签
	 *
	 * @return
	 */
	@RequestMapping("/tags")
	public R tags()
	{
		return productService.chart();
	}
}
