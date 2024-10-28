package com.shichuang.dao.bill;

import com.shichuang.domain.Bill;

import java.util.List;


public interface BillDao {
	/**
	 * 增加订单
	 */
	public int add( Bill bill);


	/**
	 * 通过查询条件获取供应商列表-模糊查询-getBillList
	 *
	 */
	public List<Bill> getBillList( Bill bill);
	
	/**
	 * 通过delId删除Bill
	 */
	public int deleteBillById(String delId);
	
	
	/**
	 * 通过billId获取Bill
	 *
	 */
	public Bill getBillById( String id);
	
	/**
	 * 修改订单信息
	 *
	 */
	public int modify(Bill bill);

	/**
	 * 根据供应商ID查询订单数量
	 *
	 */
	public int getBillCountByProviderId(String providerId);

}
