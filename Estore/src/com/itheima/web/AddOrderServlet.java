package com.itheima.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Product;
import com.itheima.domain.User;
import com.itheima.factory.BasicFactory;
import com.itheima.service.OrderService;

public class AddOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OrderService service = BasicFactory.getFactory().getService(OrderService.class);
		try {
			//1.��������Ϣ����Order bean��
			Order order = new Order();
			//--�������
			order.setId(UUID.randomUUID().toString());
			
			//--֧��״̬
			order.setPaystate(0);
			
			//--�ջ���ַ
			BeanUtils.populate(order, request.getParameterMap());
			
			//--���/����������Ϣ����order��
			Map<Product,Integer> cartmap = (Map<Product, Integer>) request.getSession().getAttribute("cartmap");
			double money = 0;
			List <OrderItem> list = new ArrayList<OrderItem>();
			for(Map.Entry<Product, Integer> entry : cartmap.entrySet()){
				money += entry.getKey().getPrice() * entry.getValue();
				
				OrderItem item = new OrderItem();
				item.setOrder_id(order.getId());
				item.setProduct_id(entry.getKey().getId());
				item.setBuynum(entry.getValue());
				list.add(item);
			}
			order.setMoney(money);
			order.setList(list);
			
			//--�ͻ����
			User user = (User) request.getSession().getAttribute("user");
			order.setUser_id(user.getId());
			
			//2.����Service����Ӷ����ķ���
			service.addOrder(order);
			
			//3.��չ��ﳵ
			cartmap.clear();
			
			//4.�ص���ҳ
			response.getWriter().write("�������ɳɹ�!��ȥ֧��!");
			response.setHeader("refresh", "3;url=/index.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
