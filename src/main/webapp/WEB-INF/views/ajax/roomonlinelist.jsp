<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.cas3.com/pagetooltaglib" prefix="pagetool"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<thead>
			<tr class="text-c">
				<th width="100">房间号</th>
				<th width="100">创建时间</th>
				<th width="30">房间状态</th>
				<th width="100">规则串</th>
				<th width="80">东(ID/昵称)</th>
				<th width="80">南(ID/昵称)</th>
				<th width="80">西(ID/昵称)</th>
				<th width="80">北(ID/昵称)</th>
				<th width="80">操作</th>
			</tr>
		</thead>
		<tbody>
		
		
<c:forEach items="${newInfoList}" var="eachVar">

	<tr class="text-c">
		<td>${eachVar.roomNum}</td>
		<td>${eachVar.roomCreateTime}</td>
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
		
		<td>${eachVar.gameRuleStr}</td>
		<td>${eachVar.eastUid}（${eachVar.eastUName}）</td>
		<td>${eachVar.southUid}（${eachVar.southUName}）</td>
		<td>${eachVar.westUid}（${eachVar.westUName}）</td>
		<td>${eachVar.northUid}（${eachVar.northUName}）</td>
		
		<td class="td-manage">
			<c:if test="${eachVar.roomState == 1}">
				<a style="text-decoration:none" onClick="killRoom(this,'${eachVar.roomNum}')" href="javascript:;" title="解散"><i class="Hui-iconfont"><span class="label label-success radius">解散</span></i></a>
			</c:if>
		</td>
		
	</tr>

</c:forEach>
</tbody>
	<pagetool:pg  curPage="${currentPage}" totalPage="${pageCount}" jsAction="changeItemPage"/>
		
	