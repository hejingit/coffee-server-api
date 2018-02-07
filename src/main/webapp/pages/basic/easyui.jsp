<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<link rel="stylesheet" type="text/css" href="../static/jquery-easyui-1.5.2/themes/material/easyui.css">
<link rel="stylesheet" type="text/css" href="../static/jquery-easyui-1.5.2/themes/icon.css">
<script type="text/javascript" src="../static/jquery-easyui-1.5.2/jquery.min.js"></script>
<script type="text/javascript" src="../static/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../static/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">

function setCookie(c_name,value,expiredays){
	var exdate=new Date();
	exdate.setDate(exdate.getDate()+expiredays);
	document.cookie=c_name+ "=" +escape(value)+
	((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
}

function getCookie(name){
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg)){
		return unescape(arr[2]);
	}
	return null;
}

function delCookie(name){
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval=getCookie(name);
	if(cval!=null) {
		document.cookie= name + "="+cval+";expires="+exp.toGMTString();
	}
}

if (typeof String.prototype.startsWith != 'function') {
  String.prototype.startsWith = function (prefix){
    return this.slice(0, prefix.length) === prefix;
  };
}

String.prototype.trim=function(){
   return this.replace(/(^\s*)|(\s*$)/g, "");
}

function toJson(data){
	return eval("(" + data + ")");
}

</script>