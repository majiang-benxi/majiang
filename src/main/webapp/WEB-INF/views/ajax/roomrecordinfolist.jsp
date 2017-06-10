<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.cas3.com/pagetooltaglib" prefix="pagetool"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<thead>
			<tr class="text-c">
				<th width="40">ID</th>
				<th width="100">房间号</th>
				<th width="100">规则串</th>
				<th width="60">创建者ID</th>
				<th width="100">创建时间</th>
				<th width="100">结束时间</th>
				<th width="30">房间状态</th>
				<th width="80">东</th>
				<th width="80">南</th>
				<th width="80">西</th>
				<th width="80">北</th>
			</tr>
		</thead>
		<tbody>
		
		
<c:forEach items="${newInfoList}" var="eachVar">

	<tr class="text-c">
		<td>${eachVar.id}</td>
		<td>${eachVar.roomNum}</td>
		<td>${eachVar.roomRule}</td>
		<td>${eachVar.creatorId}</td>
		<td>${eachVar.createTimeStr}</td>
		<td>${eachVar.endTimeStr}</td>
		
		<td class="td-status">
			
				<c:if test="${eachVar.roomState == 2}">
					<span class=" ">进行中</span>
				</c:if>
				<c:if test="${eachVar.roomState == 0}">
					<span class=" ">
					等待
					</span>
				</c:if>
				<c:if test="${eachVar.roomState == 1}">
					<span class="  ">
					待确认
					</span>
				</c:if>
				<c:if test="${eachVar.roomState == 3}">
					<span class=" ">
					结束
					</span>
				</c:if>
			
		</td>
		
		<td>${eachVar.eastUid}</td>
		<td>${eachVar.southUid}</td>
		<td>${eachVar.westUid}</td>
		<td>${eachVar.northUid}</td>
		
	</tr>

</c:forEach>
</tbody>
	<pagetool:pg  curPage="${currentPage}" totalPage="${pageCount}" jsAction="changeItemPage"/>
		
	