<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.cas3.com/pagetooltaglib" prefix="pagetool"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<thead>
			<tr class="text-c">
				<th width="100">用户ID</th>
				<th width="100">房间号</th>
				<th width="100">时间</th>
				<th width="100">局数</th>
				<th width="100">得分</th>
			</tr>
		</thead>
		<tbody>
		
		
<c:forEach items="${newInfoList}" var="eachVar">

	<tr class="text-c">
		<td>${eachVar.userId}</td>
		<td>${eachVar.roomNum}</td>
		<td>${eachVar.roomCreateTimeStr}</td>
		<td>${eachVar.roundNum}</td>
		<td>${eachVar.scoreNum}</td>
	</tr>

</c:forEach>
</tbody>
	<pagetool:pg  curPage="${currentPage}" totalPage="${pageCount}" jsAction="changeItemPage"/>
		
	