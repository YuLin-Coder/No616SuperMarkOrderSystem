package com.shichuang.dao.role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.shichuang.domain.Role;
import com.shichuang.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class RoleDaoImpl implements RoleDao{
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	@Override
	public List<Role> getRoleList() {
		// TODO Auto-generated method stub
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Role> roleList = new ArrayList<Role>();
		String sql = "select * from smbms_role";
		return template.query(sql.toString(), new BeanPropertyRowMapper<Role>(Role.class));

	}

}
