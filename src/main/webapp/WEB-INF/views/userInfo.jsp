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
	用户ID：<input type="text" class="input-text" style="width:250px" placeholder="用户ID" id="userId" name="">
	日期范围：
		<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="datemin" class="input-text Wdate" style="width:120px;">
		-
		<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="datemax" class="input-text Wdate" style="width:120px;">
		<input type="text" class="input-text" style="width:250px" placeholder="输入用户名" id="searchUname" name="">
		<button type="submit" class="btn btn-success" onclick="searchUser()"    id="" name=""><i class="Hui-iconfont">&#xe665;</i> 搜用户</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius">
				<i class="Hui-iconfont">&#xe6e2;</i> 批量冻结
			</a> 
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


var uid;
var datemin;
var datemax;
var searchUname;
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
	if(searchUname==null || searchUname=='' || searchUname=='undefined'){
		searchUname="";
	}
	if(uid==null || uid=='' || uid=='undefined'){
		uid="";
	}
	
	 $.ajax({
			type: "get",
			url : "/mvc/frontuser/getUserInfoList?uid="+uid+"&datemin="+datemin+"&datemax="+datemax+"&searchUname="+searchUname+"&eachPageCount="+eachPageCount+"&curPage="+curPage+"&t="+new Date(),
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


function searchUser(){
	
	uid = document.getElementById("userId").value;
	datemin = document.getElementById("datemin").value;
	datemax = document.getElementById("datemax").value;
	searchUname = document.getElementById("searchUname").value;
	curPage = 1;
	
	changeItemPage('');
	
}



var checkedUserIds = "";

function checkLineChange(obj,userId){
	if(obj.checked ){
		checkedUserIds += (","+userId);
	}else{
		regExp = new RegExp((checkedUserIds,","+userId)); 
		checkedUserIds = checkedUserIds.replace(regExp, ""); 
	}
}

function datadel(){
	
	if(checkedUserIds==null||checkedUserIds==''){
		alert("请选择要删除的行!");
		return ;
	}
	
	$.ajax({
	    url:'/mvc/frontuser/editUserInfo',
	    type:'POST', //GET
	    async:true,    //或false,是否异步
	    data:{
	    	checkedUserIds:checkedUserIds,tostate:0
	    },
	    timeout:2000,    //超时时间
	    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
	    success:function(data,textStatus,jqXHR){
	    	changeItemPage('');
	    },
	    complete:function(){
	        console.log('结束')
	    }
	})
	
	
	if(obj.checked ){
		checkedUserIds += (","+userId);
	}else{
		regExp = new RegExp((checkedUserIds,","+userId)); 
		checkedUserIds = checkedUserIds.replace(regExp, ""); 
	}
}

/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
/*管理员-增加*/
function admin_add(title,url,w,h){
	layer_show(title,url,w,h);
}
/*管理员-删除*/
function admin_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		
		$(obj).parents("tr").remove();
		layer.msg('已删除!',{icon:1,time:1000});
	});
}
/*管理员-编辑*/
function admin_edit(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*管理员-停用*/
function admin_stop(obj,id){
	layer.confirm('确认要停用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		
		$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_start(this,id)" href="javascript:;" title="启用" style="text-decoration:none"><i class="Hui-iconfont">&#xe615;</i></a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-default radius">已禁用</span>');
		$(obj).remove();
		
		$.ajax({
		    url:'/mvc/frontuser/editUserInfo',
		    type:'POST', //GET
		    async:true,    //或false,是否异步
		    data:{
		    	checkedUserIds:id,tostate:0
		    },
		    timeout:2000,    //超时时间
		    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
		    success:function(data,textStatus,jqXHR){
		    	changeItemPage('');
		    },
		    complete:function(){
		        console.log('结束')
		    }
		});
		
		
		layer.msg('已停用!',{icon: 5,time:1000});
	});
}

/*管理员-启用*/
function admin_start(obj,id){
	layer.confirm('确认要启用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
			
		$.ajax({
		    url:'/mvc/frontuser/editUserInfo',
		    type:'POST', //GET
		    async:true,    //或false,是否异步
		    data:{
		    	checkedUserIds:id,tostate:1
		    },
		    timeout:2000,    //超时时间
		    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
		    success:function(data,textStatus,jqXHR){
		    	changeItemPage('');
		    },
		    complete:function(){
		        console.log('结束')
		    }
		})
		
		
		$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_stop(this,id)" href="javascript:;" title="停用" style="text-decoration:none"><i class="Hui-iconfont">&#xe631;</i></a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已启用</span>');
		$(obj).remove();
		layer.msg('已启用!', {icon: 6,time:1000});
	});
}





var layerindexnum;


function changeRNum(usid) {
    layer.open({
        type: 1,
        content:"<div class='formControls col-5'><input id='roomcartEditNum' class='form-control' type='text' style='height: 25px; margin-top: 20px; width: 71px; margin-left: 44%;' name='awardKind' value=''/>" +
        "<button  type='button' style='text-decoration:none;margin-left: 64%;margin-top: 25%;' class='btn btn-block btn-success btn-lg' onclick='changeUserRoomCartNum("+usid+");'>提交</button></div>",
        success: function(layero, index){
        	layerindexnum = index;
        }
    });
}

function changeUserRoomCartNum(usid){
	var roomcartEditNum = document.getElementById("roomcartEditNum").value;
	
	$.ajax({
	    url:'/mvc/frontuser/editUserInfo',
	    type:'POST', //GET
	    async:true,    //或false,是否异步
	    data:{
	    	checkedUserIds:usid,roomcartEditNum:roomcartEditNum
	    },
	    timeout:2000,    //超时时间
	    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
	    success:function(data,textStatus,jqXHR){
	    	
	    	if(data.result=='fail')	{
	    		alert(data.msg);
	    	}
	    	
	    	layer.close(layerindexnum);
	    	changeItemPage('');
	    },
	    complete:function(){
	        console.log('结束')
	    }
	});
	
}


</script>
</body>
</html>