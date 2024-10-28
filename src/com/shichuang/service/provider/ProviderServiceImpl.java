package com.shichuang.service.provider;


import java.util.List;


import com.shichuang.dao.provider.ProviderDao;
import com.shichuang.dao.provider.ProviderDaoImpl;
import com.shichuang.domain.Provider;

public class ProviderServiceImpl implements ProviderService {
	private ProviderDao dao=new ProviderDaoImpl();
	/**
	 * 增加供应商
	 *
	 */
	public boolean add(Provider provider){
		return dao.add(provider)>0?true:false;
	}


	/**
	 * 通过供应商名称、编码获取供应商列表-模糊查询-providerList
	 *
	 */
	public List<Provider> getProviderList(String proName, String proCode){
		return dao.getProviderList(proName,proCode);
	}

	/**
	 * 通过proId删除Provider

	 */
	public int deleteProviderById(String delId){
		return dao.deleteProviderById(delId);
	}


	/**
	 * 通过proId获取Provider
	 */
	public Provider getProviderById( String id){
		return dao.getProviderById(id);
	}

	/**
	 * 修改用户信息
	 *
	 */
	public boolean modify( Provider provider){
		return dao.modify(provider)>0?true:false;
	}

}
