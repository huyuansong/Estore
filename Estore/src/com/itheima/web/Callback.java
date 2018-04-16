package com.itheima.web;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.factory.BasicFactory;
import com.itheima.service.OrderService;
import com.itheima.util.PaymentUtil;

public class Callback extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ûص���������
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		// ���У�� --- �ж��ǲ���֧����˾֪ͨ��
		String hmac = request.getParameter("hmac");
		
		if(PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, ResourceBundle.getBundle("merchantInfo").getString("keyValue"))){
			
			if("1".equals(r9_BType)){
				//������ض������,����ȷ�����֧���ɹ�
				response.getWriter().write("֧���ɹ�!!!");
				response.setHeader("refresh", "3;url=/index.jsp");
				
				OrderService service = BasicFactory.getFactory().getService(OrderService.class);
				service.changePayState(r6_Order,1);
				
				return;
			}
			if("2".equals(r9_BType)){
				//�ױ���Ե�ͨ��֪֧ͨ���ɹ�,�ױ�֪ͨ��,˵�����֧���ɹ�
				//--�޸ĵ�ǰ������֧��״̬Ϊ��֧��
				OrderService service = BasicFactory.getFactory().getService(OrderService.class);
				service.changePayState(r6_Order,1);
				response.getWriter().write("SUCCESS");
			}
			
			
			
		}else{
			throw new RuntimeException("���ݱ��۸Ĺ�!!!!!!!");
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
