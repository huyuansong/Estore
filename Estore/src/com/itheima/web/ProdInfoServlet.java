package com.itheima.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Product;
import com.itheima.factory.BasicFactory;
import com.itheima.service.ProdService;

public class ProdInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProdService service = BasicFactory.getFactory().getService(ProdService.class);
		//1.��ȡҪ��ѯ����Ʒid
		String id = request.getParameter("id");
		//2.����Service�еķ�����ѯ�ƶ�id����Ʒ
		Product prod = service.findProdById(id);
		//3.���鵽����Ʒ����request������ҳ����ʾ
		if(prod==null){
			throw new RuntimeException("�Ҳ�������Ʒ!!!");
		}else{
			request.setAttribute("prod", prod);
			request.getRequestDispatcher("/prodInfo.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
