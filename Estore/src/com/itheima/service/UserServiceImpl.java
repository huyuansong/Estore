package com.itheima.service;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.factory.BasicFactory;

public class UserServiceImpl implements UserService {
	private UserDao dao = BasicFactory.getFactory().getDao(UserDao.class);
	public void regist(User user) {
		try{
			//1.У���û����Ƿ��Ѿ�����
			if(dao.findUserByName(user.getUsername())!=null){
				throw new RuntimeException("�û����Ѿ�����!!");
			}
			//2.����dao�еķ�������û������ݿ�
			user.setRole("user");
			user.setState(0);
			user.setActivecode(UUID.randomUUID().toString());
			dao.addUser(user);
			
			//3.���ͼ����ʼ�
		
			Properties prop = new Properties();
			prop.setProperty("mail.transport.protocol", "smtp");
			prop.setProperty("mail.smtp.host", "localhost");
			prop.setProperty("mail.smtp.auth", "true");
			prop.setProperty("mail.debug", "true");
			Session session = Session.getInstance(prop);
			
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("aa@itheima.com"));
			msg.setRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));
			msg.setSubject(user.getUsername()+",����estore�ļ����ʼ�");
			msg.setText(user.getUsername()+",����������Ӽ����˻�,������ܵ���븴�Ƶ��������ַ������:http://www.estore.com/ActiveServlet?activecode="+user.getActivecode());
		
			Transport trans = session.getTransport();
			trans.connect("aa", "123");
			trans.sendMessage(msg, msg.getAllRecipients());
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	public void acitveUser(String activecode) {
		//1.����dao���ݼ���������û�
		User user = dao.findUserByActivecode(activecode);
		//2.����Ҳ�����ʾ��������Ч
		if(user == null){
			throw new RuntimeException("�����벻��ȷ!!!!");
		}
		//3.����û��Ѿ������,��ʾ��Ҫ�ظ�����
		if(user.getState() == 1){
			throw new RuntimeException("���û��Ѿ������!��Ҫ�ظ�����!!");
		}
		//4.���û����Ǽ������Ѿ���ʱ,����ʾ,��ɾ�����û�
		if(System.currentTimeMillis() - user.getUpdatetime().getTime()>1000*3600*24){
			dao.delUser(user.getId());
			throw new RuntimeException("�������Ѿ���ʱ,������ע�Ტ��24Сʱ�ڼ���!");
		}
		//5.����dao���޸��û�����״̬�ķ���
		dao.updateState(user.getId(),1);
	}
	public User getUserByNameAndPsw(String username, String password) {
		return dao.finUserByNameAndPsw(username,password);
	}
	public boolean hasName(String username) {
		return dao.findUserByName(username)!=null;
	}

}
