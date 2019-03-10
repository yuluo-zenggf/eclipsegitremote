package com.hwua.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.hwua.entity.Message;
import com.hwua.entity.PageEntity;
import com.hwua.entity.User;
import com.hwua.service.IMessageService;
import com.hwua.service.IUserService;
import com.hwua.service.impl.MessageServiceImpl;
import com.hwua.service.impl.UserServiceImpl;
import com.hwua.util.StringUtil;

@WebServlet("/msg.do")
public class MessageServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");// 解决post请求参数的乱码问题
		String param = req.getParameter("param");
		IMessageService msgService = new MessageServiceImpl();// 创建业务层对象
		IUserService us = new UserServiceImpl();
		if (param.equals("querybyloginid")) {
			User user = (User) req.getSession(false).getAttribute("user");
			PageEntity pageEntity = new PageEntity();
			int pageNo = Integer.parseInt(req.getParameter("pageNo"));// 得到当前页
			int pageSize = Integer.parseInt(req.getParameter("pageSize"));// 每页显示记录数
			Long totalRecords = msgService.queryMsgCount(user.getId());
			pageEntity.setPageNo(pageNo);
			pageEntity.setPageSize(pageSize);
			pageEntity.setTotalRecords(totalRecords);
			// 第一页 start 0 2
			// 第二页 2 2
			// 第三页 4 2
			List<Message> messages = msgService.queryMessageByLoginUser(user.getId(), (pageNo - 1) * pageSize,
					pageSize);
			pageEntity.setMsgList(messages);
			resp.setContentType("application/json;charset=utf-8");
			String json = JSON.toJSONString(pageEntity, true);// 把实体类转换成json格式的字符串
			System.out.println(json);
			resp.getWriter().write(json);
			// 把业务层返回的数据放到作用域中,并转发给页面进行显示
			/*
			 * req.setAttribute("data", pageEntity);
			 * req.getRequestDispatcher("/view/main.jsp").forward(req, resp);
			 */
		} else if (param.equals("showmsgbyid")) {
			String id = req.getParameter("id");
			Message msg = msgService.queryMessageById(id);
			int sendid = msg.getSendid();
			User sendUser = us.queryUserBySendid(sendid);
			msg.setSendUser(sendUser);
			req.setAttribute("msg", msg);
			req.getRequestDispatcher("/view/readMsg.jsp").forward(req, resp);

		} else if (param.equals("send")) {
			User user = (User) req.getSession(false).getAttribute("user");
			int sendid = user.getId();// 发送者id
			String title = StringUtil.repaceStr(req.getParameter("title"));
			String msgcontent = StringUtil.repaceStr(req.getParameter("content"));
			int receiveid = Integer.parseInt(req.getParameter("toUser"));
			int state = 1;
			String msg_create_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			Message msg = new Message(sendid, title, msgcontent, state, receiveid, msg_create_date);
			int res = msgService.sendMessage(msg);
			if (res > 0) {
				resp.sendRedirect(req.getContextPath() + "/user.do?param=queryallusers");
			}

		} else if (param.equals("delete")) {
			String id = req.getParameter("id");
			int res = msgService.deleteMsgById(Integer.parseInt(id));
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			if (res > 0) {
				out.write("success");
				/*
				 * resp.sendRedirect(req.getContextPath() +
				 * "/msg.do?param=querybyloginid&pageNo=1");
				 */
			}else {
				out.write("failure");
			}
			
			out.close();

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
