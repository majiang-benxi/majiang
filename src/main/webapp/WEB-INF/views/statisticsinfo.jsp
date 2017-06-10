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
	</div>

	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			
			</span>  </div>

	<table class="table table-border table-bordered table-bg" id="itemlistul">
		<thead>
			<tr class="text-c">
				<th width="100">名称</th>
				<th width="100">数据</th>
			</tr>
		</thead>
		<tbody>
			<tr class="text-c">
				<td>在线房间数</td>
				<td>${onlineRoomNum}</td>
			</tr>
			<tr class="text-c">
				<td>游戏进行房间数</td>
				<td>${playRoomNum}</td>
			</tr>
			<tr class="text-c">
				<td>游戏等待房间数</td>
				<td>${waitRoomNum}</td>
			</tr>
			<tr class="text-c">
				<td>在线总人数</td>
				<td>${onlineUserNum}</td>
			</tr>
			<tr class="text-c">
				<td>游戏中人数</td>
				<td>${playUserNum}</td>
			</tr>
			<tr class="text-c">
				<td>等待中人数</td>
				<td>${waitUserNum}</td>
			</tr>
		</tbody>
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


</script>
</body>
</html>