package com.shichuang.dao.provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.StringUtils;
import com.shichuang.domain.Provider;
import com.shichuang.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class ProviderDaoImpl implements ProviderDao {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	@Override
	public int add( Provider provider)
	{

			String sql = "insert into smbms_provider (proCode,proName,proDesc," +
					"proContact,proPhone,proAddress,proFax,createdBy,creationDate) " +
					"values(?,?,?,?,?,?,?,?,?)";
			Object[] params = {provider.getProCode(),provider.getProName(),provider.getProDesc(),
								provider.getProContact(),provider.getProPhone(),provider.getProAddress(),
								provider.getProFax(),provider.getCreatedBy(),provider.getCreationDate()};
		return  template.update(sql,params);
	}

	@Override
	public List<Provider> getProviderList( String proName,String proCode)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from smbms_provider where 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(!StringUtils.isNullOrEmpty(proName)){
			sql.append(" and proName like ?");
			list.add("%"+proName+"%");
		}
		if(!StringUtils.isNullOrEmpty(proCode)){
			sql.append(" and proCode like ?");
			list.add("%"+proCode+"%");
		}
		Object[] params = list.toArray();
		System.out.println("sql ----> " + sql.toString());
		return template.query(sql.toString(), new BeanPropertyRowMapper<Provider>(Provider.class),params);
	}

	@Override
	public int deleteProviderById( String delId)
	{
		String sql = "delete from smbms_provider where id=?";
		Object[] params = {delId};
		return  template.update(sql, delId);
	}

	@Override
	public Provider getProviderById( String id)
	{
			String sql = "select * from smbms_provider where id=?";
			Object[] params = {id};
			return template.queryForObject(sql, new BeanPropertyRowMapper<Provider>(Provider.class), id);
	}

	@Override
	public int modify( Provider provider)
	{

			String sql = "update smbms_provider set proName=?,proDesc=?,proContact=?," +
					"proPhone=?,proAddress=?,proFax=?,modifyBy=?,modifyDate=? where id = ? ";
			Object[] params = {provider.getProName(),provider.getProDesc(),provider.getProContact(),provider.getProPhone(),provider.getProAddress(),
					provider.getProFax(),provider.getModifyBy(),provider.getModifyDate(),provider.getId()};
			return template.update(sql,params);
	}

}
