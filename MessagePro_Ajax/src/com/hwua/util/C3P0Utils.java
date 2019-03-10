package com.hwua.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.*;

public class C3P0Utils {
	// 创建一个管理连接池的(数据源)对象
	private static ComboPooledDataSource cpds = new ComboPooledDataSource();
	private static ThreadLocal<Connection> tl = new ThreadLocal<>();

	/**
	 * 获取数据源的方法
	 * 
	 * @return
	 */
	public static ComboPooledDataSource getCpds() {
		return cpds;
	}

	/**
	 * 从连接池中获取一个连接对象
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		try {
			return cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 从连接池中获取一个连接对象
	 * 
	 * @return Connection
	 */
	public static Connection getCurrConnection() throws SQLException {
		Connection conn = tl.get();// 从当前线程中取到的绑定的Connection对象数据
		if (conn == null) {
			conn = cpds.getConnection();
			// 从连接池获取一个连接对象
			tl.set(conn);// 把获取到的连接对象绑定到当前线程上
		}
		return conn;// 返回的是绑定在线程上的那个连接对象
	}

	/**
	 * 开启事务的方法
	 */
	public static void startTransaction() throws SQLException {
		Connection conn = getCurrConnection();
		conn.setAutoCommit(false);// 手动开启事务
	}
	
	/**
	 * 回滚事务
	 * @throws SQLException
	 */
	public static void rollback() throws SQLException{
		Connection conn = getCurrConnection();
		conn.rollback();
	}
	
	/**
	 * 提交事务
	 * @throws SQLException
	 */
	public static void commit() throws SQLException {
		Connection conn = getCurrConnection();
		conn.commit();//提交事务
		conn.close();//把连接关闭
		tl.remove();//移除当前线程上绑定的数据
	}
	
	

}
