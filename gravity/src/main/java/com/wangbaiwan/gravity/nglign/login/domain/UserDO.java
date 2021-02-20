package com.wangbaiwan.gravity.nglign.login.domain;

import lombok.Data;

/**
 * 用户表
 *
 * @author eyeoftwilight
 */
@Data
public class UserDO
{
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 账户是否可用
	 */
	private Boolean enable;
}
