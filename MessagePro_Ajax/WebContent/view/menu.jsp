<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="menu">
				<span>当前用户：<a href="${pageContext.request.contextPath}/view/main.jsp">${sessionScope.user.name}</a></span> <span><a
					href="${pageContext.request.contextPath}/user.do?param=queryallusers">发短消息</a></span> <span><a href="${pageContext.request.contextPath}/user.do?param=logout">退出</a></span>
			</div>
			