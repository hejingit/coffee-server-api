<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>用户管理</title>
</head>
<jsp:include page="../basic/easyui.jsp"></jsp:include>
<body style="overflow:hidden;padding: 0;margin: 0;border: 0;">
<div id="cc" class="easyui-layout" style="width:100%;height:100%;">
    <!-- <div data-options="region:'north',title:'North Title',split:true" style="height:100px;"></div> -->
    <div data-options="region:'west',split:true" style="width:200px;">
	    <a id="btn" href="#" target="centerDiv"></a>
	    <div id="aa1" class="easyui-accordion" data-options="border:false" style="height:400px;">
		    <div title="用户管理" data-options="" style="overflow:auto;padding:10px;">
				<a autoClick="true" href="#" onclick="showCenterDiv(this,'userListEntry');" class="easyui-linkbutton">用户列表</a>
				<a href="#" onclick="showCenterDiv(this,'addUserEntry')" class="easyui-linkbutton">添加用户</a>
		    </div>
		    <div title="辅助工具" data-options="" style="padding:10px;">
				<a href="#" onclick="showCenterDiv(this,'desUtilsEntry')" class="easyui-linkbutton">DES工具</a>
				<a href="#" onclick="flush()" class="easyui-linkbutton">刷新Redis</a>
		    </div>
		    <div title="系统管理" data-options="" style="padding:10px;">
				<a href="#" onclick="logout()" class="easyui-linkbutton">退&nbsp;&nbsp;出</a>
		    </div>
		</div>
	</div>
    <div data-options="region:'center',title:'center title'">
    	<iframe frameborder="0" name="centerDiv" scrolling="yes" style="height:100%;visibility:inherit; width:100%;z-index:1;"></iframe>
    </div>
</div>
</body>
<script type="text/javascript">
function showCenterDiv(obj, url){
	$("#cc").layout('panel','center').panel('setTitle',$(obj).text());
	$("#btn").attr('href', url);
	$("#btn")[0].click();
}
function flush(){
	var r=confirm("你确定要刷新Redis数据吗？\r\n警告：在未出现数据异常的情况下，请勿频繁使用！");
	if (r) {
		$.post("flushRedis", function(data) {
			if(data.toString() == 'true'){
				alert("刷新成功！");
			} else {
				alert("刷新失败，请重试！");
			}
		});
	}
}
function logout(){
	var r=confirm("你确定要退出吗？");
	if (r) {
		delCookie('coffee_login_cookie');
		window.parent.location.href="webLoginEntry";
	}
}
$(function(){
	$("[autoClick='true']").click();
});
</script>
</html>