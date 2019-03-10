package com.hwua.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hwua.entity.User;
import com.hwua.service.IUserService;
import com.hwua.service.impl.UserServiceImpl;

@WebServlet("/user.do")
public class UserServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String param = req.getParameter("param");
		IUserService us = new UserServiceImpl();
		if (param.equals("logout")) {
			req.getSession(false).invalidate();// 销毁session
			resp.sendRedirect(req.getContextPath() + "/index.jsp");// 回到登录页面
		} else if (param.equals("queryallusers")) {
			List<User> uList = us.getAllUsers();
			String sendid = req.getParameter("sendid");
			// 代表是回复而不是发送短消息
			if (sendid != null) {
				req.setAttribute("sendid", sendid);
			}
			// 传递一个sendid过去
			req.setAttribute("users", uList);
			req.getRequestDispatcher("/view/newMsg.jsp").forward(req, resp);
		} else if (param.equals("queryByName")) {
			String name = req.getParameter("name");
			boolean bool = us.queryUserByName(name);
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.write(bool+"");
			out.close();
		}  

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
