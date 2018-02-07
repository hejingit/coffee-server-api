<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<html>
<head>
</head>
<jsp:include page="../basic/easyui.jsp"></jsp:include>
<style type="text/css">
a:visited {color: blue} 
</style>
<body>
<div id="toolbar">
<table>
<tr>
	<td>&nbsp;&nbsp;用户名：</td>
	<td><input id="searchUsername" class="easyui-textbox" ></td>
	<td>&nbsp;&nbsp;Token：</td>
	<td><input id="searchToken" class="easyui-textbox" style="width:350px"></td>
    <td><a href="#" onclick="searchUser()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a></td>
</tr>
</table>
</div>
<table id="userList"></table>
</body>
<script type="text/javascript">
var pageSize = parseInt((document.body.clientHeight - 88) / 125) * 5;
pageSize = pageSize > 0 ? pageSize : 5;
var pageList = new Array();
for(var i=0; i<5; i++){
	pageList[i] = pageSize*(i+1);
}
$('#userList').datagrid({
	url: 'userList',
	idField: 'id',
	width: '100%',
	height: '100%',
	fit: true,
	border: false,
    columns: [[
        {field:'id',title:'ID',hidden:true},
        {field:'username',title:'用户名',width:100},
        {field:'token',title:'Token',width:300},
        {field:'sign',title:'Sign',width:200},
        {field:'able',title:'是否可用',width:100, formatter: function(val,row,index){
        	return val ? "是" : "否";
        }},
        {field:'createTime',title:'创建时间',width:200},
        {field:'operate',title:'操作',width:200, formatter: function(val,row,index){
        	return getOperateButtons(row.id, row.username, row.able, index);
        }}
    ]],
    //data: JSON.parse('${users}'),
    fitColumns: true,
    toolbar: '#toolbar',
    singleSelect:true,
    pagination:true,
    pageSize:pageSize,
    pageList:pageList,
    rownumbers:true
});

function getOperateButtons(id, username, able, index){
	var agrs = id+',\''+username+'\','+able+','+index;
	var buttons =  '<a id="btn" href="#" onclick="updateToken('+agrs+')">更新Token</a>&nbsp;&nbsp;';
	if (able) {
		buttons += '<a id="btn" href="#" onclick="ableUser('+agrs+')">禁用账号</a>';
	} else {
		buttons += '<a id="btn" href="#" onclick="ableUser('+agrs+')">激活账号</a>';
	}
	return buttons;
}

function updateToken(id, username, able, index){
	var r=confirm("你确定要更新用户["+username+"]的Token吗？");
	if (r) {
		$.post("updateToken", {id:id}, function(data){
			if(data){
				if(data.trim().startsWith("<html>")){
					window.parent.location.href="webLoginEntry";
					return;
				}
				//data = JSON.parse(data);
				data = toJson(data);
				$('#userList').datagrid('updateRow',{
					index: index,
					row: {
						token: data.token,
						sign: data.sign
					}
				});
			} else {
				alert("操作失败，请重试！");
			}
		});
	}
}

function ableUser(id, username, able, index){
	var r=confirm("你确定要“"+(able?'禁用':'激活')+"”用户["+username+"]吗？");
	if (r) {
		$.post("ableUser", {id:id,able:!able}, function(data){
			if(data){
				if(data.toString().trim().startsWith("<html>")){
					window.parent.location.href="webLoginEntry";
					return;
				}
				$('#userList').datagrid('updateRow',{
					index: index,
					row: {
						able: !able
					}
				});
			} else {
				alert("操作失败，请重试！");
			}
		});
	}
}

function searchUser() {
	var username = $("#searchUsername").textbox("getText");
	var token = $("#searchToken").textbox("getText");
	if(username || token){
		$('#userList').datagrid("load",{
			username:username,
			token:token
		});
		//window.location.href="userList?username="+username+"&token="+token;
	}
}
</script>
</html>