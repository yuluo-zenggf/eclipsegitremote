package com.hwua.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hwua.dao.IUserDao;
import com.hwua.dao.impl.UserDaoImpl;
import com.hwua.entity.User;
import com.hwua.service.IUserService;
import com.hwua.service.impl.UserServiceImpl;

/**
 * Servlet implementation class LoginRegisterServlet
 */
@WebServlet("/LR.do")
public class LoginRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserDao userDao;

	public LoginRegisterServlet() {
		userDao = new UserDaoImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String param = request.getParameter("param");
		IUserService us = new UserServiceImpl();
		if (param.equals("login")) {
			String name = request.getParameter("name");
			String pwd = request.getParameter("pwd");
			User user = new User(name, pwd);

			user = us.login(user);
			if (user != null) {
				request.getSession().setAttribute("user", user);
				response.sendRedirect(request.getContextPath() + "/view/main.jsp");
			} else {
				request.getSession().setAttribute("info", "用户名或密码出错");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
		} else if (param.equals("register")) {
			// 获取表单的数据
			request.setCharacterEncoding("utf-8");
			String name = request.getParameter("name");
			String pwd = request.getParameter("pwd");
			String email = request.getParameter("email");
			String code = request.getParameter("code");
			// 输入的验证码和生成的验证码进行比对
			HttpSession session = request.getSession(false);
			String scode = (String) session.getAttribute("code");// 从session中取出验证码
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			if (!code.equalsIgnoreCase(scode)) {
				out.write("code error");
			} else {
				User user = new User(name, pwd, email);
				// 调用业务成插入一条件数据
				boolean flag = us.register(user);
				if (flag) {
					out.write("success");
				} else {
					out.write("failure");
				}
			}
			out.close();

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
