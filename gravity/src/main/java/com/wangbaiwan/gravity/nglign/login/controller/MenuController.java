package com.wangbaiwan.gravity.nglign.login.controller;


import com.wangbaiwan.gravity.nglign.login.service.MenuService;
import com.wangbaiwan.gravity.nglign.common.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 获取当前角色或人 能看到的菜单数据
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/menu")
public class MenuController
{
	@Autowired
	private MenuService menuService;

	/**
	 * 获取菜单数据
	 *
	 * @return
	 */
	@RequestMapping("/getMenuData")
	public Mono<R> getMenuData()
	{
		return Mono.just(menuService.getMenuData());
	}
}
