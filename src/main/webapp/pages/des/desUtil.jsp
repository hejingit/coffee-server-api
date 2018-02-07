<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<html>
<head>
</head>
<jsp:include page="../basic/easyui.jsp"></jsp:include>
<body>
原文：<input id="before" class="easyui-textbox" data-options="multiline:true" style="height:100px;width: 90%;"/><br/><br/>
密码：<input id="key" class="easyui-textbox" style="width: 400px;"/>
<a href="#" onclick="encrypt()" class="easyui-linkbutton" iconCls="icon-add">↓加密↓</a>
<a href="#" onclick="decrypt()" class="easyui-linkbutton" iconCls="icon-remove">↑解密↑</a><br/><br/>
密文：<input id="after" class="easyui-textbox" data-options="multiline:true" style="height:100px;width: 90%;"/><br/><br/>
<div id="msg"></div>
</body>
<script type="text/javascript">
function encrypt(){
	var text = $("#before").val().trim();
	var key = $("#key").val().trim();
	if(text){
		$("#after").textbox('setValue', '');
		$.post("encrypt", {text:encodeURI(text),key:key},function(data){
			$("#after").textbox('setValue', data);
		});
	}
}
function decrypt(){
	var text = $("#after").val().trim();
	var key = $("#key").val().trim();
	if(text){
		$("#before").textbox('setValue', '');
		$.post("decrypt", {text:text,key:key},function(data){
			$("#before").textbox('setValue', data);
		});
	}
}
</script>
</html>