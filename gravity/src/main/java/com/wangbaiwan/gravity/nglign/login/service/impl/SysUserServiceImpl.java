package com.wangbaiwan.gravity.nglign.login.service.impl;

import com.wangbaiwan.gravity.nglign.login.dao.SysUserDao;
import com.wangbaiwan.gravity.nglign.login.domain.SysUserDO;
import com.wangbaiwan.gravity.nglign.login.service.SysUserService;
import com.wangbaiwan.gravity.nglign.login.vo.SysUserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (SysUser)表服务实现类
 *
 * @author makejava
 * @since 2019-08-30 22:37:48
 */
@Service
public class SysUserServiceImpl implements SysUserService
{
	@Resource
	private SysUserDao sysUserDao;

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	public SysUserDO get(Long id)
	{
		return sysUserDao.get(id);
	}

	/**
	 * 条件查询
	 *
	 * @param params
	 * @return
	 */
	@Override
	public List<SysUserVO> list(SysUserVO params)
	{
		return sysUserDao.list(params);
	}

	/**
	 * 新增数据
	 *
	 * @param sysUser 实例对象
	 * @return 实例对象
	 */
	@Override
	public SysUserDO save(SysUserDO sysUser)
	{
		sysUserDao.save(sysUser);
		return sysUser;
	}

	/**
	 * 修改数据
	 *
	 * @param sysUser 实例对象
	 * @return 实例对象
	 */
	@Override
	public int update(SysUserDO sysUser)
	{
		return sysUserDao.update(sysUser);
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@Override
	public boolean deleteById(Long id)
	{
		return this.sysUserDao.deleteById(id) > 0;
	}

	/**
	 * 根据userId获取用户
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public SysUserVO getUserInfoByUserId(String userId)
	{
		SysUserVO params = new SysUserVO();
		params.setUserId(userId);
		List<SysUserVO> list = sysUserDao.list(params);
		if (list == null || list.size() == 0)
		{
			return null;
		}
		return list.get(0);
	}
}