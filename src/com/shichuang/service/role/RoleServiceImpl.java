package com.shichuang.service.role;


import java.util.List;


import com.shichuang.dao.role.RoleDao;
import com.shichuang.dao.role.RoleDaoImpl;
import com.shichuang.domain.Role;

public class RoleServiceImpl implements RoleService{
	private RoleDao dao=new RoleDaoImpl();

	public List<Role> getRoleList()throws Exception{
		return dao.getRoleList();
	}
	
}
