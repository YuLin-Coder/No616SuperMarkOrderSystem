package com.shichuang.dao.bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.StringUtils;
import com.shichuang.domain.Bill;
import com.shichuang.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class BillDaoImpl implements BillDao {

	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	@Override
	public int add(Bill bill) {
		String sql = "insert into smbms_bill (billCode,productName,productDesc," +
				"productUnit,productCount,totalPrice,isPayment,providerId,createdBy,creationDate) " +
				"values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {bill.getBillCode(),bill.getProductName(),bill.getProductDesc(),
				bill.getProductUnit(),bill.getProductCount(),bill.getTotalPrice(),bill.getIsPayment(),
				bill.getProviderId(),bill.getCreatedBy(),bill.getCreationDate()};
		return template.update(sql, params);


	}

	@Override
	public List<Bill> getBillList(Bill bill){
		StringBuffer sql = new StringBuffer();
		sql.append("select b.*,p.proName as providerName from smbms_bill b, smbms_provider p where b.providerId = p.id");
		List<Object> list = new ArrayList<Object>();
		if(!StringUtils.isNullOrEmpty(bill.getProductName())){
			sql.append(" and productName like ?");
			list.add("%"+bill.getProductName()+"%");
		}
		if(bill.getProviderId() > 0){
			sql.append(" and providerId = ?");
			list.add(bill.getProviderId());
		}
		if(bill.getIsPayment() > 0){
			sql.append(" and isPayment = ?");
			list.add(bill.getIsPayment());
		}
		Object[] params = list.toArray();
		System.out.println("sql --------- > " + sql.toString());
		List<Bill> listBill=template.query(sql.toString(), new BeanPropertyRowMapper<Bill>(Bill.class),params);
		return listBill;
	}

	@Override
	public int deleteBillById(String delId) {
		String sql = "delete from smbms_bill where id=?";
		Object[] params = {delId};
		return template.update(sql, delId);
	}

	@Override
	public Bill getBillById(String id)  {
		String sql = "select b.*,p.proName as providerName from smbms_bill b, smbms_provider p " +
				"where b.providerId = p.id and b.id=?";
		Object[] params = {id};
		return template.queryForObject(sql, new BeanPropertyRowMapper<Bill>(Bill.class), id);

	}

	@Override
	public int modify(Bill bill) {
		String sql = "update smbms_bill set productName=?," +
				"productDesc=?,productUnit=?,productCount=?,totalPrice=?," +
				"isPayment=?,providerId=?,modifyBy=?,modifyDate=? where id = ? ";
		Object[] params = {bill.getProductName(),bill.getProductDesc(),
				bill.getProductUnit(),bill.getProductCount(),bill.getTotalPrice(),bill.getIsPayment(),
				bill.getProviderId(),bill.getModifyBy(),bill.getModifyDate(),bill.getId()};
		return template.update(sql,params);
	}

	@Override
	public int getBillCountByProviderId(String providerId)  {
		String sql = "select count(1) as billCount from smbms_bill where" +
				"	providerId = ?";
		Object[] params = {providerId};
		return template.queryForObject(sql,Integer.class,params);
	}
}
