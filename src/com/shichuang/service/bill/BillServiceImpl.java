package com.shichuang.service.bill;

import com.shichuang.dao.bill.BillDao;
import com.shichuang.dao.bill.BillDaoImpl;
import com.shichuang.domain.Bill;


import java.util.List;



public class BillServiceImpl implements BillService {

	private BillDao dao=new BillDaoImpl();
	/**
	 * 增加订单
	 */
	public boolean add( Bill bill){
		return dao.add(bill)>0?true:false;
	}
	/**
	 * 通过查询条件获取供应商列表-模糊查询-getBillList
	 *
	 */
	public List<Bill> getBillList( Bill bill){
		return dao.getBillList(bill);
	}

	/**
	 * 通过delId删除Bill
	 */
	public boolean deleteBillById(String delId){
		return  dao.deleteBillById(delId)>0?true:false;
	}


	/**
	 * 通过billId获取Bill
	 *
	 */
	public Bill getBillById( String id){
		return dao.getBillById(id);
	}

	/**
	 * 修改订单信息
	 *
	 */
	public boolean modify(Bill bill){
		return dao.modify(bill)>0?true:false;
	}

	/**
	 * 根据供应商ID查询订单数量
	 *
	 */
	public int getBillCountByProviderId(String providerId){
		return dao.getBillCountByProviderId(providerId);
	}

}
