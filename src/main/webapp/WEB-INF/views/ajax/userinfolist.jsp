<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.cas3.com/pagetooltaglib" prefix="pagetool"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<thead>
			<tr class="text-c">
				<th width="25">
				<th width="40">ID</th>
				<th width="100">昵称</th>
				<th width="60">头像</th>
				<th width="50">注册时间</th>
				<th width="30">性别</th>
				<th width="120">房卡数</th>
				<th width="30">已消费房卡数</th>
				<th width="50">最后登录IP </th>
				<th width="50">最后登录时间 </th>
				<th width="30">登录次数</th>
				<th width="30">状态 </th>
				<th width="150">操作</th>
			</tr>
		</thead>
		<tbody>
		
		
<c:forEach items="${newuserInfoList}" var="eachVar">

	<tr class="text-c">
	
		<td><input type="checkbox" value="1" name="" onchange="checkLineChange(this,'${eachVar.id}')"></td>
		<td>${eachVar.id}</td>
		<td>${eachVar.nickName}</td>
		<td>
			<img style="height: 30px;"  alt="" src="${eachVar.headImgurl}"/>
		</td>
		<td>${eachVar.createTimeStr}</td>
		
		<td class="td-status">
			
				<c:if test="${eachVar.sex == 1}">
					<span class="label label-success radius">男</span>
				</c:if>
				<c:if test="${eachVar.sex == 2}">
					<span class="label radius">
					女
					</span>
				</c:if>
			
		</td>
		
		<td  style="position: relative;">
			<div class="f-14 td-manage" style="position: relative;">${eachVar.roomCartNum}<a style="text-decoration:none;margin-left: 20px;" onClick="changeRNum('${eachVar.id}')" href="javascript:;" title="修改">修改</a></div> 
		</td>
		
		
		<td>${eachVar.roomCartNumUsed}</td>
		<td>${eachVar.lastLoginIp}</td>
		<td>${eachVar.lastLoginTimeStr}</td>
		<td>${eachVar.loginTimes}</td>

		<td class="td-status">
			
				<c:if test="${eachVar.state == 1}">
					<span class="label label-success radius">已启用</span>
				</c:if>
				<c:if test="${eachVar.state == 0}">
					<span class="label radius">
					已冻结
					</span>
				</c:if>
			
		</td>
		<td class="td-manage">
			<c:if test="${eachVar.state == 1}">
				<a style="text-decoration:none" onClick="admin_stop(this,'${eachVar.id}')" href="javascript:;" title="停用"><i class="Hui-iconfont">&#xe631;</i></a>
			</c:if>
			<c:if test="${eachVar.state == 0}">
				<a style="text-decoration:none" onClick="admin_start(this,'${eachVar.id}')" href="javascript:;" title="启用"><i class="Hui-iconfont">&#xe615;</i></a> 
			</c:if>
		</td>
	</tr>

</c:forEach>
</tbody>
	<pagetool:pg  curPage="${currentPage}" totalPage="${pageCount}" jsAction="changeItemPage"/>
		
	