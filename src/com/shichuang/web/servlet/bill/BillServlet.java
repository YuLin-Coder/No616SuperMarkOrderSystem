package com.shichuang.web.servlet.bill;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.shichuang.domain.Bill;
import com.shichuang.domain.Provider;
import com.shichuang.domain.User;
import com.shichuang.service.bill.BillService;
import com.shichuang.service.bill.BillServiceImpl;
import com.shichuang.service.provider.ProviderService;
import com.shichuang.service.provider.ProviderServiceImpl;
import com.shichuang.util.Constants;

@WebServlet("/bill")
public class BillServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*String totalPrice = request.getParameter("totalPrice");
		//23.234   45
		BigDecimal totalPriceBigDecimal = 
				//设置规则，小数点保留两位，多出部分，ROUND_DOWN 舍弃
				//ROUND_HALF_UP 四舍五入(5入) ROUND_UP 进位 
				//ROUND_HALF_DOWN 四舍五入（5不入）
				new BigDecimal(totalPrice).setScale(2,BigDecimal.ROUND_DOWN);*/
		
		String method = request.getParameter("method");
		try{
			if(method != null && method.equals("query")){
				this.query(request,response);
			}else if(method != null && method.equals("add")){
				this.add(request,response);
			}else if(method != null && method.equals("view")){
				this.getBillById(request,response,"jsp/billview.jsp");
			}else if(method != null && method.equals("modify")){
				this.getBillById(request,response,"jsp/billmodify.jsp");
			}else if(method != null && method.equals("modifysave")){
				this.modify(request,response);
			}else if(method != null && method.equals("delbill")){
				this.delBill(request,response);
			}else if(method != null && method.equals("getproviderlist")){
				this.getProviderlist(request,response);
			}
		}catch (Exception ex){
			ex.getStackTrace();
		}

		
	}
	
	private void getProviderlist(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		System.out.println("getproviderlist ========================= ");
		
		List<Provider> providerList = new ArrayList<Provider>();
		ProviderService providerService = new ProviderServiceImpl();
		providerList = providerService.getProviderList("","");
		//把providerList转换成json对象输出
		response.setContentType("application/json");
		PrintWriter outPrintWriter = response.getWriter();
		outPrintWriter.write(JSONArray.toJSONString(providerList));
		outPrintWriter.flush();
		outPrintWriter.close();
	}
	private void getBillById(HttpServletRequest request, HttpServletResponse response,String url)
			throws Exception {
		String id = request.getParameter("billid");
		if(!StringUtils.isNullOrEmpty(id)){
			BillService billService = new BillServiceImpl();
			Bill bill = null;
			bill = billService.getBillById(id);
			request.setAttribute("bill", bill);
			request.getRequestDispatcher(url).forward(request, response);
		}
	}
	
	private void modify(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("modify===============");
		String id = request.getParameter("id");
		String productName = request.getParameter("productName");
		String productDesc = request.getParameter("productDesc");
		String productUnit = request.getParameter("productUnit");
		String productCount = request.getParameter("productCount");
		String totalPrice = request.getParameter("totalPrice");
		String providerId = request.getParameter("providerId");
		String isPayment = request.getParameter("isPayment");
		
		Bill bill = new Bill();
		bill.setId(Integer.valueOf(id));
		bill.setProductName(productName);
		bill.setProductDesc(productDesc);
		bill.setProductUnit(productUnit);
		bill.setProductCount(new BigDecimal(productCount).setScale(2,BigDecimal.ROUND_DOWN));
		bill.setIsPayment(Integer.parseInt(isPayment));
		bill.setTotalPrice(new BigDecimal(totalPrice).setScale(2,BigDecimal.ROUND_DOWN));
		bill.setProviderId(Integer.parseInt(providerId));
		
		bill.setModifyBy(((User)request.getSession().getAttribute(Constants.USER_SESSION)).getId());
		bill.setModifyDate(new Date());
		boolean flag = false;
		BillService billService = new BillServiceImpl();
		flag = billService.modify(bill);
		System.out.println(flag);
		if(flag){
			response.sendRedirect(request.getContextPath()+"/bill?method=query");
		}else{
			request.getRequestDispatcher("jsp/billmodify.jsp").forward(request, response);
		}
	}
	private void delBill(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("billid");
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(!StringUtils.isNullOrEmpty(id)){
			BillService billService = new BillServiceImpl();
			boolean flag = billService.deleteBillById(id);
			if(flag){//删除成功
				resultMap.put("delResult", "true");
			}else{//删除失败
				resultMap.put("delResult", "false");
			}
		}else{
			resultMap.put("delResult", "notexit");
		}
		//把resultMap转换成json对象输出
		response.setContentType("application/json");
		PrintWriter outPrintWriter = response.getWriter();
		outPrintWriter.write(JSONArray.toJSONString(resultMap));
		outPrintWriter.flush();
		outPrintWriter.close();
	}
	private void add(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String billCode = request.getParameter("billCode");
		String productName = request.getParameter("productName");
		String productDesc = request.getParameter("productDesc");
		String productUnit = request.getParameter("productUnit");
		
		String productCount = request.getParameter("productCount");
		String totalPrice = request.getParameter("totalPrice");
		String providerId = request.getParameter("providerId");
		String isPayment = request.getParameter("isPayment");
		
		Bill bill = new Bill();
		bill.setBillCode(billCode);
		bill.setProductName(productName);
		bill.setProductDesc(productDesc);
		bill.setProductUnit(productUnit);
		bill.setProductCount(new BigDecimal(productCount).setScale(2,BigDecimal.ROUND_DOWN));
		bill.setIsPayment(Integer.parseInt(isPayment));
		bill.setTotalPrice(new BigDecimal(totalPrice).setScale(2,BigDecimal.ROUND_DOWN));
		bill.setProviderId(Integer.parseInt(providerId));
		bill.setCreatedBy(((User)request.getSession().getAttribute(Constants.USER_SESSION)).getId());
		bill.setCreationDate(new Date());
		boolean flag = false;
		BillService billService = new BillServiceImpl();
		flag = billService.add(bill);
		System.out.println("add flag -- > " + flag);
		if(flag){
			response.sendRedirect(request.getContextPath()+"/bill?method=query");
		}else{
			request.getRequestDispatcher("/jsp/billadd.jsp").forward(request, response);
		}
		
	}
	private void query(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		List<Provider> providerList = new ArrayList<Provider>();
		ProviderService providerService = new ProviderServiceImpl();
		providerList = providerService.getProviderList("","");
		request.setAttribute("providerList", providerList);
		
		String queryProductName = request.getParameter("queryProductName");
		String queryProviderId = request.getParameter("queryProviderId");
		String queryIsPayment = request.getParameter("queryIsPayment");
		if(StringUtils.isNullOrEmpty(queryProductName)){
			queryProductName = "";
		}
		
		List<Bill> billList = new ArrayList<Bill>();
		BillService billService = new BillServiceImpl();
		Bill bill = new Bill();
		if(StringUtils.isNullOrEmpty(queryIsPayment)){
			bill.setIsPayment(0);
		}else{
			bill.setIsPayment(Integer.parseInt(queryIsPayment));
		}
		
		if(StringUtils.isNullOrEmpty(queryProviderId)){
			bill.setProviderId(0);
		}else{
			bill.setProviderId(Integer.parseInt(queryProviderId));
		}
		bill.setProductName(queryProductName);
		billList = billService.getBillList(bill);
		request.setAttribute("billList", billList);
		request.setAttribute("queryProductName", queryProductName);
		request.setAttribute("queryProviderId", queryProviderId);
		request.setAttribute("queryIsPayment", queryIsPayment);
		request.getRequestDispatcher("jsp/billlist.jsp").forward(request, response);
		
	}
	



}
