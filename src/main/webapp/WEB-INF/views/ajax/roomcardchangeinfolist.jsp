<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.cas3.com/pagetooltaglib" prefix="pagetool"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<thead>
			<tr class="text-c">
				<th width="40">ID</th>
				<th width="100">用户ID</th>
				<th width="100">用户昵称</th>
				<th width="60">变化原因</th>
				<th width="100">变化数量</th>
				<th width="100">变化时间</th>
				<th width="30">操作管理员ID</th>
				<th width="30">操作管理员</th>
			</tr>
		</thead>
		<tbody>
		
		
<c:forEach items="${newInfoList}" var="eachVar">

	<tr class="text-c">
		<td>${eachVar.id}</td>
		<td>${eachVar.userId}</td>
		<td>${eachVar.userName}</td>
		<td>${eachVar.changecause}</td>
		<td>${eachVar.changeNum}</td>
		<td>${eachVar.changeTimeStr}</td>
		<td>${eachVar.manageUserId}</td>
		<td>${eachVar.manageName}</td>
	</tr>

</c:forEach>
</tbody>
	<pagetool:pg  curPage="${currentPage}" totalPage="${pageCount}" jsAction="changeItemPage"/>
		
	