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
	<form action="" method="post" class="form form-horizontal" id="form-admin-add">
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>用户名：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text" value="${manageUser.uname}" placeholder="" id="user-name" name="username" datatype="*2-16" nullmsg="用户名不能为空">
			</div>
			<div class="col-4"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>昵称：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text" value="${manageUser.nickName}" placeholder="" id="nick-name" name="nickname" datatype="*2-16" nullmsg="昵称能为空">
			</div>
			<div class="col-4"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>初始密码：</label>
			<div class="formControls col-5">
				<input type="text" placeholder="密码" autocomplete="off" value="" class="input-text" id="user-passwd" name="userpasswd" datatype="*6-20" nullmsg="密码不能为空">
			</div>
			<div class="col-4"> </div>
		</div>

		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>手机：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text" value="${manageUser.mobile}" placeholder="" id="user-tel" name="usertel"  datatype="m" nullmsg="手机不能为空">
			</div>
			<div class="col-4"> </div>
		</div>

		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>角色：</label>
			<div class="formControls col-5"> <span class="select-box" style="width:150px;">
				<select class="select" name="adminrole" size="1" id="adminrole" onchange="adminroleChecked()">
					<option value="1">会员</option>
					<option value="2">管理员</option>
				</select>
				</span> </div>
		</div>
		
		<div class="row cl" id="user-card-num-div" style="display:none;">
			<label class="form-label col-3">房卡数：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text" value="${manageUser.cardHold}" placeholder="" id="user-card-num" name="usercardnum">
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

function adminroleChecked(){
	var ocj=$("#adminrole option:selected")
	if(ocj.val()==1){
		document .getElementById("user-card-num-div").style.display="block";
	}else{
		document .getElementById("user-card-num-div").style.display="none";
	}
}

var toedit = 0;
var userId=0;

var muserLevel = '${manageUser.userLevel}';

if(muserLevel=='1' || muserLevel=='2'){
	
	toedit = 1;
	userId = '${manageUser.id}';
	$("#adminrole").val(muserLevel);
	
}


$(document).ready(function(){
	adminroleChecked();
});

function submitProxy(){
	
	var username = document.getElementById("user-name").value;
	var nickname = document.getElementById("nick-name").value;
	var userpasswd = document.getElementById("user-passwd").value;
	var usertel = document.getElementById("user-tel").value;
	var usercardnum = document.getElementById("user-card-num").value;
	var userLevel = document.getElementById("adminrole").value;
	
	if(username!=''&&username!=null
		&&nickname!=''&&nickname!=null
		&&userpasswd!=''&&userpasswd!=null
		&&usertel!=''&&usertel!=null
		&&userLevel!=''&&userLevel!=null
	){
		
		$.ajax({
		    url:'/mvc/user/addAdminUser',
		    type:'POST', //GET
		    async:true,    //或false,是否异步
		    data:{
		    	username:username,nickname:nickname,userpasswd:userpasswd,usertel:usertel,usercardnum:usercardnum,userLevel:userLevel,toedit:toedit,userId:userId
		    },
		    timeout:2000,    //超时时间
		    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
		    success:function(data,textStatus,jqXHR){
		    	
		    	parent.changeItemPage('');
		    	var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
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
	
	$("#form-admin-add").Validform({
		tiptype:2,
		callback:function(form){
			submitProxy();
		}
	});
});
</script>
</body>
</html>