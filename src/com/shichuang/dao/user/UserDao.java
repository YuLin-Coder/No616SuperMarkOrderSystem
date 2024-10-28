package com.shichuang.dao.user;

import java.sql.Connection;
import java.util.List;

import com.shichuang.domain.User;

public interface UserDao {
	/**
	 * 增加用户信息
	 *
	 */
	public int add( User user);

	/**
	 * 通过userCode获取User

	 */
	public User getLoginUser(String userCode);

	/**
	 * 通过条件查询-userList
	 *
	 */
	public List<User> getUserList( String userName, int userRole, int currentPageNo, int pageSize);
	/**
	 * 通过条件查询-用户表记录数
	 *
	 */
	public int getUserCount( String userName, int userRole);
	
	/**
	 * 通过userId删除user
	 * @param delId
	 * @return
	 * @throws Exception
	 */
	public int deleteUserById( Integer delId);

	
	/**
	 * 通过userId获取user
	 */
	public User getUserById( String id);
	
	/**
	 * 修改用户信息
	 *
	 */
	public int modify(User user);
	
	
	/**
	 * 修改当前用户密码
	 *
	 */
	public int updatePwd( int id, String pwd);

	
}
