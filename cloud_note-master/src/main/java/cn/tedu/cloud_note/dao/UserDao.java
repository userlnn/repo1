package cn.tedu.cloud_note.dao;

import cn.tedu.cloud_note.entity.User;

public interface UserDao {
	//��¼����
	public User findByName(String name);
	//ע�᷽��
	public  void save(User user);
}
