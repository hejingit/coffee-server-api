<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>登 录</title>
</head>
<jsp:include page="../basic/easyui.jsp"></jsp:include>
<body>
<form name="loginForm" action="webLogin" method="post">
<table>
  <tr>
    <td>用户名：</td>
    <td><input id="username" name="username" class="easyui-textbox"/></td>
  </tr>
  <tr>
    <td>密&nbsp;&nbsp;码：</td>
    <td><input id="password" name="password" class="easyui-passwordbox"/></td>
  </tr>
  <tr>
    <td></td>
	<td><a href="#" onclick="login()" class="easyui-linkbutton" iconCls="icon-search">登&nbsp;&nbsp;录</a></td>
  </tr>
  <tr>
    <td colspan="2"><div id="msg"></div></td>
  </tr>
</table>
</form>
</body>
<script type="text/javascript">
function login(){
	var username = $("#username").val().trim();
	var password = $("#password").val().trim();
	if(username && password){
		$.post("webLogin", {username: username, password: password}, function(data){
			//data = JSON.parse(data);
			data = toJson(data);
			if(data.msg) {
				$("#msg").text(data.msg);
			}else if(data.cookie) {
				setCookie('coffee_login_cookie', data.cookie, 1);
				location.href = "layout";
			}
		});
	} else {
		$("#msg").text("用户名或密码不能为空！");
	}
}
</script>
</html>