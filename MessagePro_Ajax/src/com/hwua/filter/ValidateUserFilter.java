package com.hwua.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns= {"/view/*","/msg.do"})
public class ValidateUserFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)resp;
		    //判断用户是否登录成功
		HttpSession session = request.getSession(false);
		//没有登陆成功
		if(session==null ||session.getAttribute("user")==null) {
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}else {
			chain.doFilter(req, resp);//放行
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
