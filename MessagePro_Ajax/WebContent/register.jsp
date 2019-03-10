<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>海文 在线短信平台</title>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath }/css/sms.css" />
<style type="text/css">
.error {
	color: red;
}

.alert {
	color: green;
}
</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/scripts/jquery.js"></script>

<script type="text/javascript">
	function changeCode() {
		var codeImg = document.getElementById("codeImg");
		codeImg.src = "${pageContext.request.contextPath }/code?timestamp="
				+ new Date().getTime();

	}

	/*
	   验证
	 */
	var $name, $pwd, $affirm, $email, $code, $info;
	function validate() {
		var reg = /^[0-9A-Za-z]{3,}@[0-9A-Za-z]{2,}\.(com|cn|com\.cn)$/;
		$info.removeClass().html("");
		if ($name.val() == "") {
			$info.addClass("error").html("用户名不能为空");
			return false;
		}
		if ($pwd.val() == "") {
			$info.addClass("error").html("密码不能为空");
			return false;
		}
		if ($affirm.val() == "") {
			$info.addClass("error").html("确认不能为空");
			return false;
		}
		if ($pwd.val() != $affirm.val()) {
			$info.addClass("error").html("两次密码不一致");
			return false;
		}
		if ($email.val() == "") {
			$info.addClass("error").html("email不能空");
			return false;
		}

		if (!reg.test($email.val())) {
			$info.addClass("error").html("email格式不正确");
			return false;
		}

		if ($code.val() == "") {
			$info.addClass("error").html("验证码不能空");
			return false;
		}

		return true;

	}

	/*当页面加载完毕后要执行的代码  */
	$(function() {
		$name = $(".name");
		$pwd = $(".pwd");
		$affirm = $(".affirm");
		$email = $(".email");
		$code = $(".code");
		$info = $(".info");

		$("#register").click(function() {
			if (validate()) {
				//发送ajax请求
				$.post("${pageContext.request.contextPath }/LR.do", {
					"param" : "register",
					"name":$name.val(),
					"pwd":$pwd.val(),
					"email":$email.val(),
					"code":$code.val()
				}, function(data) {
					if(data=="code error"){
						$info.removeClass().addClass("error").html("验证码不对,重新输入");
					}else if(data=="success"){
						$info.removeClass().addClass("alert").html("注册成功");
					}else{
						$info.removeClass().addClass("error").html("注册失败,联系管理员");
					}
				})
			} else {
				//alert("failure");
			}
		});

		$name.blur(function() {
			$.get("${pageContext.request.contextPath }/user.do", {
				"param" : "queryByName",
				"name" : $name.val()
			}, function(data) {
				if (data == "true") {
					$info.removeClass().addClass("error").html("此用户名已经注册，不可用");
				} else {
					$info.removeClass().addClass("alert").html("此用户名可用");
				}
			});
		});
	})
</script>
</head>

<body>
	<div id="regTitle" class="png"></div>
	<div id="regForm" class="userForm png">

		<!-- <form action="doRegister" method="get"> -->
		<dl>
			<div class="info"></div>
			<dt>用 户 名：</dt>
			<dd>
				<input type="text" name="name" class="name" />
			</dd>
			<dt>密 码：</dt>
			<dd>
				<input type="password" name="pwd" class="pwd" />
			</dd>
			<dt>确认密码：</dt>
			<dd>
				<input type="password" name="affirm" class="affirm" />
			</dd>
			<dt>邮 箱：</dt>
			<dd>
				<input type="text" name="email" class="email" />
			</dd>
			<dt>验证码：</dt>
			<dd>
				<input type="text" name="code" class="code" />
			</dd>
			<dt>&nbsp;&nbsp;&nbsp;&nbsp;</dt>
			<dd>
				<img src="${pageContext.request.contextPath }/code" id="codeImg"
					style="vertical-align: middle;"> <a
					href="javascript:changeCode()">换一张</a>
			</dd>
		</dl>
		<div class="buttons">
			<input class="btn-reg png" type="button" name="register" value=" "
				id="register" /><input class="btn-reset png" type="reset"
				name="reset" value=" " />
		</div>
		<div class="goback">
			<a href="index.jsp" class="png">返回登录页</a>
		</div>
		<!-- </form> -->
	</div>
</body>
</html>
