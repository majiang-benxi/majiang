<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.cas3.com/pagetooltaglib" prefix="pagetool"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<thead>
			<tr class="text-c">
				<th width="25">
				<th width="40">ID</th>
				<th width="150">登录名</th>
				<th width="150">昵称</th>
				<th width="130">加入时间</th>
				<th width="120">手机</th>
				<th width="30">角色</th>
				<th width="100">房卡数</th>
				<th width="30">状态</th>
				<th width="150">操作</th>
			</tr>
		</thead>
		<tbody>
			
<c:forEach items="${manageUserList}" var="eachVar">

	<tr class="text-c">
	
		<td><input type="checkbox" value="1" name="" onchange="checkLineChange(this,'${eachVar.id}')"></td>
		<td>${eachVar.id}</td>
		<td>${eachVar.uname}</td>
		<td>${eachVar.nickName}</td>
		<td>${eachVar.createTimeStr}</td>
		<td>${eachVar.mobile}</td>
		<td>
			<c:if test="${eachVar.userLevel == 1}">
				会员
			</c:if>
			<c:if test="${eachVar.userLevel == 2}">
				管理员
			</c:if>
		</td>
		<td  style="position: relative;">
			<c:if test="${eachVar.userLevel == 1}">
				 <div class="f-14 td-manage" style="position: relative;">${eachVar.cardHold}<a style="text-decoration:none;margin-left: 20px;" onClick="changeRNum('${eachVar.id}')" href="javascript:;" title="修改">修改</a></div> 
			</c:if>
			<c:if test="${eachVar.userLevel == 2}">
				--
			</c:if>
		</td>
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
			
			<a title="编辑" href="javascript:;" onclick="admin_edit('管理员编辑','/mvc/user/toEditAdminUser?uid=${eachVar.id}','1','800','500')" class="ml-5" style="text-decoration:none;margin-left: 10px;">
			<i class="Hui-iconfont">&#xe6df;</i></a> 
		</td>
	</tr>

</c:forEach>
</tbody>
	<pagetool:pg  curPage="${currentPage}" totalPage="${pageCount}" jsAction="changeItemPage"/>
		
	