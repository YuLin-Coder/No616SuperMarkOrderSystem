package com.shichuang.dao.provider;

import java.sql.Connection;
import java.util.List;
import com.shichuang.domain.Provider;

public interface ProviderDao {
	
	/**
	 * 增加供应商
	 *
	 */
	public int add(Provider provider);


	/**
	 * 通过供应商名称、编码获取供应商列表-模糊查询-providerList
	 *
	 */
	public List<Provider> getProviderList(String proName, String proCode);
	
	/**
	 * 通过proId删除Provider

	 */
	public int deleteProviderById(String delId);
	
	
	/**
	 * 通过proId获取Provider
	 */
	public Provider getProviderById( String id);
	
	/**
	 * 修改用户信息
	 *
	 */
	public int modify(Provider provider);
	
	
}
