package com.hwua.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.hwua.dao.IMessageDao;
import com.hwua.dao.impl.MessageDaoImpl;
import com.hwua.entity.Message;
import com.hwua.service.IMessageService;
import com.hwua.util.C3P0Utils;

public class MessageServiceImpl implements IMessageService {
	private IMessageDao msgDao = null;

	public MessageServiceImpl() {
		msgDao = new MessageDaoImpl();
	}

	
	@Override
	public List<Message> queryMessageByLoginUser(int loginid, int start, int pageSize) {
		List<Message> msgList = null;
		try {
			C3P0Utils.startTransaction();
			msgList = msgDao.query(loginid, start, pageSize);

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

		return msgList;

	}

	@Override
	public Message queryMessageById(String id) {
		Message message = null;
		try {
			C3P0Utils.startTransaction();
			message = msgDao.query(id);
			message.setState(0);
			msgDao.update(message);
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
		return message;
	}

	@Override
	public int sendMessage(Message msg) {
		int res = 0;
		try {
			C3P0Utils.startTransaction();
			res = msgDao.save(msg);
			
		} catch (SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
			try {
				C3P0Utils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				C3P0Utils.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	@Override
	public int deleteMsgById(int id) {
		int res = 0;
		try {
			C3P0Utils.startTransaction();
			res = msgDao.delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				C3P0Utils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				C3P0Utils.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	@Override
	public Long queryMsgCount(int loginid) {
		Long count = 0l;
		try {
			C3P0Utils.startTransaction();
			count = msgDao.queryCount(loginid);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				C3P0Utils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				C3P0Utils.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return count;
	}

}
