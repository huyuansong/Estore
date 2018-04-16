package com.itheima.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.domain.User;
import com.itheima.factory.BasicFactory;
import com.itheima.service.UserService;
import com.itheima.util.MD5Utils;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService service = BasicFactory.getFactory().getService(UserService.class);
		//1.��ȡ�û�������
		String username = request.getParameter("username");
		String password = MD5Utils.md5(request.getParameter("password"));
		//2.����Service�и����û�����������û��ķ���
		User user = service.getUserByNameAndPsw(username,password);
		if(user == null){
			request.setAttribute("msg", "�û������벻��ȷ!");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		//3.����û�����״̬
		if(user.getState() == 0){
			request.setAttribute("msg", "�û���δ����,�뵽�����н��м���!");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		//4.��¼�û��ض�����ҳ
		request.getSession().setAttribute("user", user);
		
		//--�����ס�û���
		if("true".equals(request.getParameter("remname"))){
			Cookie remnameC = new Cookie("remname",URLEncoder.encode(user.getUsername(),"utf-8"));
			remnameC.setPath("/");
			remnameC.setMaxAge(3600*24*30);
			response.addCookie(remnameC);
		}else{
			Cookie remnameC = new Cookie("remname","");
			remnameC.setPath("/");
			remnameC.setMaxAge(0);
			response.addCookie(remnameC);
		}
		
		//--����30�����Զ���½
		if("true".equals(request.getParameter("autologin"))){
			Cookie autologinC = new Cookie("autologin",URLEncoder.encode(user.getUsername()+":"+user.getPassword(),"utf-8"));
			autologinC.setPath("/");
			autologinC.setMaxAge(3600*24*30);
			response.addCookie(autologinC);
		}
		
		response.sendRedirect("/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
