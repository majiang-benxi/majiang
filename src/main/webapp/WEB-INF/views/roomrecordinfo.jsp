<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<LINK rel="Bookmark" href="/favicon.ico" >
<LINK rel="Shortcut Icon" href="/favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>用户列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 用户管理 <span class="c-gray en">&gt;</span> 用户列表 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" id="frontuserRef" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<div class="text-c"> 
	房间号：<input type="text" class="input-text" style="width:250px" placeholder="房间号" id="roomnum" name="">
	日期范围：
		<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="datemin" class="input-text Wdate" style="width:120px;">
		-
		<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="datemax" class="input-text Wdate" style="width:120px;">
		<button type="submit" class="btn btn-success" onclick="searchRecord()"    id="" name=""><i class="Hui-iconfont">&#xe665;</i> 搜素</button>
	</div>

	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			
			</span>  </div>

	<table class="table table-border table-bordered table-bg" id="itemlistul">
		
	</table>
	
</div>
<script type="text/javascript" src="/lib/jquery/1.9.1/jquery.min.js"></script>  
<script type="text/javascript" src="/lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="/lib/laypage/1.2/laypage.js"></script> 
<script type="text/javascript" src="/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="/js/H-ui.js"></script> 
<script type="text/javascript" src="/js/H-ui.admin.js"></script> 
<script type="text/javascript">


var roomnum;
var datemin;
var datemax;
var eachPageCount = 10;
var curPage = 1;

var changeItemPage = function(curPageP){
	
	if(curPageP!=null&&curPageP!=''){
		curPage = curPageP;
	}
	
	var queryStr = "";
	if(datemin==null || datemin=='' || datemin=='undefined'){
		datemin="";
	}
	if(datemax==null || datemax=='' || datemax=='undefined'){
		datemax="";
	}
	if(roomnum==null || roomnum=='' || roomnum=='undefined'){
		roomnum="";
	}
	
	 $.ajax({
			type: "get",
			url : "/mvc/room/getRoomRecordInfoList?roomnum="+roomnum+"&datemin="+datemin+"&datemax="+datemax+"&eachPageCount="+eachPageCount+"&curPage="+curPage,
			dataType:'html',
			data: '', 
			async:true,
			success: function(data){
				data = data.replace(/(^\s*)|(\s*$)/g,"");
				document.getElementById("itemlistul").innerHTML=data;
			} 
		});
};

$(document).ready(function(){
	changeItemPage('');
});


function searchRecord(){
	
	roomnum = document.getElementById("roomnum").value;
	datemin = document.getElementById("datemin").value;
	datemax = document.getElementById("datemax").value;
	curPage = 1;
	
	changeItemPage('');
	
}


</script>
</body>
</html>