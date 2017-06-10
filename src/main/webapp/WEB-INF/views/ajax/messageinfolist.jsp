<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.cas3.com/pagetooltaglib" prefix="pagetool"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<thead>
			<tr class="text-c">
				<th width="25">
				<th width="30">ID</th>
				<th width="40">类型</th>
				<th width="60">发放位置</th>
				<th width="80">标题</th>
				<th width="200">内容</th>
				<th width="60">创建时间</th>
				<th width="30">轮播间隔(s)</th>
				<th width="50">消息状态</th>
				<th width="150">操作</th>
			</tr>
		</thead>
		<tbody>
		
		
<c:forEach items="${newInfoList}" var="eachVar">

	<tr class="text-c">
	
		<td><input type="checkbox" value="1" name="" onchange="checkLineChange(this,'${eachVar.id}')"></td>
		
		<td>${eachVar.id}</td>
		<td class="td-status">
			
				<c:if test="${eachVar.mesType == 1}">
					<span >公告</span>
				</c:if>
				<c:if test="${eachVar.mesType == 2}">
					<span >
					广播
					</span>
				</c:if>
			
		</td>
		
		<td class="td-status">
				<c:if test="${eachVar.mesType == 1}">
					<span >--</span>
				</c:if>
				<c:if test="${eachVar.mesType == 2}">
						<c:if test="${eachVar.mesPosition == 1}">
							<span >大厅</span>
						</c:if>
						<c:if test="${eachVar.mesPosition == 2}">
							<span >
							房间
							</span>
						</c:if>
						<c:if test="${eachVar.mesPosition == 3}">
							<span >
							大厅房间同时
							</span>
						</c:if>
				</c:if>
		
		
		</td>
		
		<td>${eachVar.mesTitle}</td>
		<td>${eachVar.messageContent}</td>
		<td>${eachVar.createTimeStr}</td>
		<td>${eachVar.intervalTime}</td>
		
		<td class="td-status">
			
				<c:if test="${eachVar.state == 0}">
					<span>未发送</span>
				</c:if>
				<c:if test="${eachVar.state == 1}">
					<span >
					已发送
					</span>
				</c:if>
				<c:if test="${eachVar.state == 2}">
					<span >
					删除
					</span>
				</c:if>
			
		</td>
		
		<td class="td-manage">
			<c:if test="${eachVar.state != 2}">
				<a style="text-decoration:none" onClick="admin_stop(this,'${eachVar.id}')" href="javascript:;" title="删除"><i class="Hui-iconfont"><span class="label label-success radius">删除</span></i></a>
			</c:if>
			
		</td>
	</tr>

</c:forEach>
</tbody>
	<pagetool:pg  curPage="${currentPage}" totalPage="${pageCount}" jsAction="changeItemPage"/>
		
	