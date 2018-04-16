package com.itheima.service;

import com.itheima.annotation.Tran;
import com.itheima.domain.User;

public interface UserService  extends Service{

	/**
	 * ע���û�
	 * @param user ��װ���û����ݵ�userbean
	 */
	@Tran
	void regist(User user);

	/**
	 * �����û��ķ���
	 * @param activecode ������
	 */
	void acitveUser(String activecode);

	/**
	 * �����û�����������û�
	 * @param username
	 * @param password
	 */
	User getUserByNameAndPsw(String username, String password);

	/**
	 * �����û����Ƿ��Ѿ�����
	 * @param username
	 * @return
	 */
	boolean hasName(String username);

}
