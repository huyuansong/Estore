package com.itheima.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Product;
import com.itheima.factory.BasicFactory;
import com.itheima.service.ProdService;

public class AddCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProdService service = BasicFactory.getFactory().getService(ProdService.class);
		//1.����id���ҳ�Ҫ�������Ʒ
		String id = request.getParameter("id");
		Product prod = service.findProdById(id);
		//2.��cartmap����������Ʒ,���֮ǰû�������Ʒ,����Ӳ�����������Ϊ1,����Ѿ��й������Ʒ,����+1
		if(prod==null){
			throw new RuntimeException("�Ҳ�������Ʒ!");
		}else{
			Map<Product,Integer> cartmap = (Map<Product, Integer>) request.getSession().getAttribute("cartmap");
			cartmap.put(prod, cartmap.containsKey(prod)?cartmap.get(prod)+1 : 1);
		}
		//3.�ض��򵽹��ﳵҳ�����չʾ
		response.sendRedirect("/cart.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
