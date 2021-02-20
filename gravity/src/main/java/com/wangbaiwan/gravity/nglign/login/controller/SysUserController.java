package com.wangbaiwan.gravity.nglign.login.controller;

import com.wangbaiwan.gravity.nglign.login.domain.SysUserDO;
import com.wangbaiwan.gravity.nglign.login.service.SysUserService;
import com.wangbaiwan.gravity.nglign.login.vo.SysUserVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (SysUser)表控制层
 *
 * @author makejava
 * @since 2019-08-30 22:37:48
 */
@RestController
@RequestMapping("/twilight/sysUser")
public class SysUserController
{
	/**
	 * 服务对象
	 */
	@Resource
	private SysUserService sysUserService;

	/**
	 * 通过主键查询单条数据
	 *
	 * @param id 主键
	 * @return 单条数据
	 * @apiNote 通过主键查询单条数据
	 */
	@GetMapping("/selectOne/{id}")
	public SysUserDO selectOne(@PathVariable Long id)
	{
		return this.sysUserService.get(id);
	}

	/**
	 * 条件查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public List<SysUserVO> list(Map<String, Object> params)
	{
		List<SysUserVO> list = sysUserService.list(params);
		return list;
	}
}