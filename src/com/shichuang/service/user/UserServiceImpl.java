package com.shichuang.service.user;


import java.util.List;

import com.shichuang.dao.user.UserDao;
import com.shichuang.dao.user.UserDaoImpl;
import com.shichuang.domain.User;

/**
 *
 *
 */
public class UserServiceImpl implements UserService{
	private UserDao dao=new UserDaoImpl();
	/**
	 * 增加用户信息
	 *
	 */
	public boolean add( User user)throws Exception{
		return dao.add(user)>0?true:false;
	}

	/**
	 * 通过userCode获取User

	 */
	public User getLoginUser(String userCode)throws Exception{
		return dao.getLoginUser(userCode);
	}

	/**
	 * 通过条件查询-userList
	 *
	 */
	public List<User> getUserList( String userName, int userRole, int currentPageNo, int pageSize)throws Exception{
		return dao.getUserList(userName,userRole,currentPageNo,pageSize);
	}
	/**
	 * 通过条件查询-用户表记录数
	 *
	 */
	public int getUserCount( String userName, int userRole)throws Exception {
		return dao.getUserCount(userName, userRole);
	}

	/**
	 * 通过userId删除user
	 * @param delId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteUserById( Integer delId)throws Exception{
		return dao.deleteUserById(delId)>0?true:false;
	}


	/**
	 * 通过userId获取user
	 */
	public User getUserById( String id)throws Exception{
		return  dao.getUserById(id);
	}

	/**
	 * 修改用户信息
	 *
	 */
	public boolean modify(User user)throws Exception{
		return dao.modify(user)>0?true:false;
	}


	/**
	 * 修改当前用户密码
	 *
	 */
	public boolean updatePwd( int id, String pwd)throws Exception{
		return dao.updatePwd(id,pwd)>0?true:false;
	}
	@Override
	public User login(String userCode, String userPassword) {
		User user=dao.getLoginUser(userCode);

		//匹配密码
		if(null != user){
			if(!user.getUserPassword().equals(userPassword))
				user = null;
		}

		return user;
	}
	@Override
	public User selectUserCodeExist(String userCode) {
		return dao.getLoginUser(userCode);
	}
}
