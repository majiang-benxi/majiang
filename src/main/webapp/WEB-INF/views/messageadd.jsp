<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="/lib/icheck/icheck.css" rel="stylesheet" type="text/css" />
<link href="/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>添加管理员</title>
</head>
<body>
<div class="pd-20">
	<form action="" method="post" class="form form-horizontal" id="form-message-add">
	
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>消息类型：</label>
			
			<div class="formControls col-5"> <span class="select-box" style="width:150px;">
				<select class="select" name="messageType" size="1" id="messageType" onchange="messageTypeChecked()">
					<option value="1">公告</option>
					<option value="2">广播</option>
				</select>
				</span> </div>
			<div class="col-4"> </div>
		</div>
		<div class="row cl"  id="messagePositiondiv" style="display:none;">
			<label class="form-label col-3"><span class="c-red">*</span>发放位置：</label>
			
			<div class="formControls col-5"> <span class="select-box" style="width:150px;">
				<select class="select" name="messagePosition" size="1" id="messagePosition" >
					<option value="1">大厅</option>
					<option value="2">房间</option>
					<option value="3">同时</option>
				</select>
				</span> </div>
			<div class="col-4"> </div>
		</div>
	
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>标题：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text" value="" placeholder="" id="messageTitle" name="messageTitle" datatype="*2-50" nullmsg="标题不能为空">
			</div>
			<div class="col-4"> </div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>内容：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text" value="" placeholder="" id="messageContent" name="messageContent" datatype="*10-200" nullmsg="内容不能为空">
			</div>
			<div class="col-4"> </div>
		</div>

		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>轮播间隔(s)：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text" value="" placeholder="" id="messageinterval" name="messageinterval" nullmsg="轮播间隔不能为空">
			</div>
			<div class="col-4"> </div>
		</div>

		<div class="row cl">
			<div class="col-9 col-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</div>
<script type="text/javascript" src="/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="/lib/Validform/5.3.2/Validform.min.js"></script> 
<script type="text/javascript" src="/lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="/js/H-ui.js"></script> 
<script type="text/javascript" src="/js/H-ui.admin.js"></script> 
<script type="text/javascript">

function messageTypeChecked(){
	var ocj=$("#messageType option:selected")
	if(ocj.val()==1){
		document .getElementById("messagePositiondiv").style.display="none";
	}else{
		document .getElementById("messagePositiondiv").style.display="block";
	}
}

$(document).ready(function(){
	messageTypeChecked();
});

function submitProxy(){
	
	var messageType = $('#messageType').val();
	var messagePosition = $('#messagePosition').val();
	var messageTitle = document.getElementById("messageTitle").value;
	var messageContent = document.getElementById("messageContent").value;
	var messageinterval = document.getElementById("messageinterval").value;
	
	if(messageType!=''&&messageType!=null
		&&messageTitle!=''&&messageTitle!=null
		&&messageContent!=''&&messageContent!=null
		&&messageinterval!=''&&messageinterval!=null
	){
		
		$.ajax({
		    url:'/mvc/message/addMessageInfo',
		    type:'POST', //GET
		    async:true,    //或false,是否异步
		    data:{
		    	messageType:messageType,messagePosition:messagePosition,messageTitle:messageTitle,messageContent:messageContent,messageinterval:messageinterval
		    },
		    timeout:2000,    //超时时间
		    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
		    success:function(data,textStatus,jqXHR){
		    	
		    	alert(data)
		    	
		    	changeItemPage('');
		    },
		    complete:function(){
		        console.log('结束')
		    }
		})
		
	}else{
		return false;
	}
	
}



$(function(){

	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	$("#form-message-add").Validform({
		tiptype:2,
		callback:function(form){
			submitProxy();
		}
	});
});
</script>
</body>
</html>