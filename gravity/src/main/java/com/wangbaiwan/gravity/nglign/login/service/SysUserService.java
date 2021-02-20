package com.wangbaiwan.gravity.nglign.login.service;


import com.wangbaiwan.gravity.nglign.login.domain.SysUserDO;
import com.wangbaiwan.gravity.nglign.login.vo.SysUserVO;

import java.util.List;
import java.util.Map;

/**
 * (SysUser)表服务接口
 *
 * @author makejava
 * @since 2019-08-30 22:37:48
 */
public interface SysUserService
{

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	SysUserDO get(Long id);

	/**
	 * 条件查询
	 *
	 * @param params
	 * @return
	 */
	List<SysUserVO> list(SysUserVO params);

	/**
	 * 新增数据
	 *
	 * @param sysUser 实例对象
	 * @return 实例对象
	 */
	SysUserDO save(SysUserDO sysUser);

	/**
	 * 修改数据
	 *
	 * @param sysUser 实例对象
	 * @return 实例对象
	 */
	int update(SysUserDO sysUser);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	boolean deleteById(Long id);

	/**
	 * 根据userId获取用户
	 *
	 * @param userId
	 * @return
	 */
	SysUserVO getUserInfoByUserId(String userId);
}