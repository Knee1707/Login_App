package vn.iotstar.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.iotstar.config.JPAConfig;
import vn.iotstar.dao.IUserDao;
import vn.iotstar.entity.User;

public class UserDao implements IUserDao {

	@Override
	public void insert(User user) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			enma.persist(user);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			throw e;
		} finally {
			enma.close();
		}
	}

	@Override
	public void update(User user) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			enma.merge(user);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			throw e;
		} finally {
			enma.close();
		}
	}

	@Override
	public User findById(int userId) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			return enma.find(User.class, userId);
		} finally {
			enma.close();
		}
	}

	@Override
	public User findByEmail(String email) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT u FROM User u WHERE u.email = :email";
			TypedQuery<User> query = enma.createQuery(jpql, User.class);
			query.setParameter("email", email);
			return query.getResultStream().findFirst().orElse(null);
		} finally {
			enma.close();
		}
	}
}
