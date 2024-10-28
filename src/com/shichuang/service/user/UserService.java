package com.shichuang.service.user;

import java.util.List;


import com.shichuang.domain.User;

public interface UserService {
	/**
	 * 增加用户信息
	 *
	 */
	public boolean add( User user)throws Exception;

	/**
	 * 通过userCode获取User

	 */
	public User getLoginUser(String userCode)throws Exception;

	/**
	 * 通过条件查询-userList
	 *
	 */
	public List<User> getUserList( String userName, int userRole, int currentPageNo, int pageSize)throws Exception;
	/**
	 * 通过条件查询-用户表记录数
	 *
	 */
	public int getUserCount( String userName, int userRole)throws Exception;

	/**
	 * 通过userId删除user
	 * @param delId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteUserById( Integer delId)throws Exception;


	/**
	 * 通过userId获取user
	 */
	public User getUserById( String id)throws Exception;

	/**
	 * 修改用户信息
	 *
	 */
	public boolean modify(User user)throws Exception;


	/**
	 * 修改当前用户密码
	 *
	 */
	public boolean updatePwd( int id, String pwd)throws Exception;

	public User login(String userCode,String userPassword);

	public User selectUserCodeExist(String userCode);
}
