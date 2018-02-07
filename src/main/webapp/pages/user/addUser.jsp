<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<html>
<head>
</head>
<jsp:include page="../basic/easyui.jsp"></jsp:include>
<body>
<form id="addForm" action="addUser">
用户名：<input id="username" name="username" class="easyui-textbox"/>&nbsp;&nbsp;用户编码：<input id="orgid" name="orgid" class="easyui-textbox"/>
<a href="#" onclick="addUser()" class="easyui-linkbutton" iconCls="icon-add">添&nbsp;&nbsp;加</a></td>
<br/><br/>
<div id="msg"></div>
</form>
</body>
<script type="text/javascript">
function addUser(){
	var username = $("#username").val().trim();
	var orgid = $("#orgid").val().trim();
	if(username){
		$.post("addUser", {username:username,orgid:orgid},function(data){
			$("#msg").text(data);
		});
		//$("#addForm").submit();
	}
}
</script>
</html>