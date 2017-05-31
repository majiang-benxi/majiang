<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.cas3.com/pagetooltaglib" prefix="pagetool"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<thead>
			<tr class="text-c">
				<th width="40">ID</th>
				<th width="40">用户ID</th>
				<th width="100">房间号</th>
				<th width="60">用户方位</th>
				<th width="50">记录类型</th>
				<th width="100">记录时间</th>
				<th width="120">记录产生原因</th>
				<th width="80">赢的次数</th>
				<th width="80">输的次数</th>
				<th width="80">胡牌次数</th>
			</tr>
		</thead>
		<tbody>
		
		
<c:forEach items="${newInfoList}" var="eachVar">

	<tr class="text-c">
		<td>${eachVar.id}</td>
		<td>${eachVar.userId}</td>
		<td>${eachVar.roomNum}</td>
		
		<td class="td-status">
			
				<c:if test="${eachVar.userDirection == 1}">
					<span class=" ">东</span>
				</c:if>
				<c:if test="${eachVar.userDirection == 2}">
					<span class=" ">
					南
					</span>
				</c:if>
				<c:if test="${eachVar.userDirection == 3}">
					<span class="  ">
					西
					</span>
				</c:if>
				<c:if test="${eachVar.userDirection == 4}">
					<span class=" ">
					北
					</span>
				</c:if>
			
		</td>
		<td class="td-status">
			
				<c:if test="${eachVar.operateType == 1}">
					<span class="label label-success radius">加入</span>
				</c:if>
				<c:if test="${eachVar.operateType == 2}">
					<span class="label radius">
					离开
					</span>
				</c:if>
			
		</td>
		<td>${eachVar.operateTimeStr}</td>
		<td>${eachVar.operateCause}</td>
		<td>${eachVar.winTimes}</td>
		<td>${eachVar.loseTimes}</td>
		<td>${eachVar.huTimes}</td>
		
		
	</tr>

</c:forEach>
</tbody>
	<pagetool:pg  curPage="${currentPage}" totalPage="${pageCount}" jsAction="changeItemPage"/>
		
	