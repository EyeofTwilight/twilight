package com.wangbaiwan.gravity.nglign.login.dao;


import com.wangbaiwan.gravity.nglign.login.domain.SysUserDO;
import com.wangbaiwan.gravity.nglign.login.vo.SysUserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * (SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2019-08-30 22:37:48
 */
@Mapper
public interface SysUserDao
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
	 * @return 影响行数
	 */
	int save(SysUserDO sysUser);

	/**
	 * 修改数据
	 *
	 * @param sysUser 实例对象
	 * @return 影响行数
	 */
	int update(SysUserDO sysUser);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 影响行数
	 */
	int deleteById(Long id);
}