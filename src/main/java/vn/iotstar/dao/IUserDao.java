package vn.iotstar.dao;

import vn.iotstar.entity.User;

public interface IUserDao {

	void insert(User user);

	void update(User user);

	User findById(int userId);

	User findByEmail(String email);
}
