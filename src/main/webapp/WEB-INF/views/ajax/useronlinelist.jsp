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
				<th width="30">所在房间</th>
			</tr>
		</thead>
		<tbody>
		
		
<c:forEach items="${newInfoList}" var="eachVar">

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
		
		<td>${eachVar.roomCartNum}</td>
		<td>${eachVar.roomCartNumUsed}</td>
		<td>${eachVar.lastLoginIp}</td>
		<td>${eachVar.lastLoginTimeStr}</td>
		<td>${eachVar.loginTimes}</td>
		<td>${eachVar.currRoom}</td>

	</tr>

</c:forEach>
</tbody>
	<pagetool:pg  curPage="${currentPage}" totalPage="${pageCount}" jsAction="changeItemPage"/>
		
	