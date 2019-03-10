package com.hwua.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.hwua.dao.IUserDao;
import com.hwua.entity.User;
import com.hwua.util.C3P0Utils;

public class UserDaoImpl implements IUserDao {

	@Override
	public User query(User user) throws SQLException {
		String sql = "select id,name,pwd,email from users where name = ? and pwd=?";
		Object[] params = { user.getName(), user.getPwd() };
		QueryRunner qr = new QueryRunner();
		user = qr.query(C3P0Utils.getCurrConnection(), sql, new BeanHandler<>(User.class), params);
		return user;
	}

	@Override
	public int save(User user) throws SQLException {
		String sql = "insert into users values(null,?,?,?)";
		Object[] params = { user.getName(), user.getPwd(), user.getEmail() };
		QueryRunner qr = new QueryRunner();
		return qr.update(C3P0Utils.getCurrConnection(), sql, params);
	}

	@Override
	public List<User> query() throws SQLException {
		String sql = "select id,name,pwd,email from users";
		Object[] params = null;
		QueryRunner qr = new QueryRunner();
		return qr.query(C3P0Utils.getCurrConnection(), sql, new BeanListHandler<>(User.class), params);
	}

	@Override
	public User query(int sendid) throws SQLException {
		String sql = "select id,name,pwd,email from users where id=?";
		Object[] params = { sendid };
		QueryRunner qr = new QueryRunner();
		User user = qr.query(C3P0Utils.getCurrConnection(), sql, new BeanHandler<>(User.class), params);
		return user;
	}

	@Override
	public User query(String name) throws SQLException {
		String sql = "select id,name,pwd,email from users where name=?";
		Object[] params = { name };
		QueryRunner qr = new QueryRunner();
		User user = qr.query(C3P0Utils.getCurrConnection(), sql, new BeanHandler<>(User.class), params);
		return user;
	}

}
