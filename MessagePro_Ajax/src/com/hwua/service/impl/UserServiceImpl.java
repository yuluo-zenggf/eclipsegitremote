package com.hwua.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.hwua.dao.IUserDao;
import com.hwua.dao.impl.UserDaoImpl;
import com.hwua.entity.User;
import com.hwua.service.IUserService;
import com.hwua.util.C3P0Utils;

public class UserServiceImpl implements IUserService {
	private IUserDao userDao;

	public UserServiceImpl() {
		userDao = new UserDaoImpl();
	}

	@Override
	public User login(User user) {
		try {
			C3P0Utils.startTransaction();
			user = userDao.query(user);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				C3P0Utils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				C3P0Utils.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	@Override
	public boolean register(User user) {
		int res = 0;
		try {
			C3P0Utils.startTransaction();
			res = userDao.save(user);
		} catch (SQLException e) {
			try {
				C3P0Utils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				C3P0Utils.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res > 0 ? true : false;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> uList = null;
		try {
			C3P0Utils.startTransaction();
			uList = userDao.query();
			try {
				C3P0Utils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				C3P0Utils.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return uList;
	}

	@Override
	public User queryUserBySendid(int sendid) {
		User user = null;
		try {
			C3P0Utils.startTransaction();
			user = userDao.query(sendid);
			try {
				C3P0Utils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				C3P0Utils.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	@Override
	public boolean queryUserByName(String name) {
		User user = null;
		try {
			C3P0Utils.startTransaction();
			user = userDao.query(name);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				C3P0Utils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				C3P0Utils.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user == null ? false : true;
	}

}
