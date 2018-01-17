<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/roof-web/head.jsp"%>
<script type="text/javascript"
	src="${basePath }/common/js/ROOF.Class.js"></script>
<script type="text/javascript"
	src="${basePath }/roof-web/web/js/ROOF.SelectableTable.js"></script>
<link rel="stylesheet"
	href="${basePath}/common/js/fort_awesome/css/font-awesome.min.css" />
<!--[if IE 7]>
  <link rel="stylesheet" href="${basePath}/common/js/fort_awesome/css/font-awesome-ie7.min.css" />
 <![endif]-->
<title></title>
<script type="text/javascript">
	$(document).ready(function() {
		var table = new ROOF.SelectableTable($('table'));
		$('#deleteBtn').click(function() {
			var cacheName = table.getSeleted();
			if (cacheName) {
				$.post($(this).attr('href'), {
					'cacheName' : cacheName
				}, function(d) {
					ROOF.Utils.alert(d.message);
				}, 'json');
			} else {
				ROOF.Utils.alert('请选择一行记录!');
			}
			return false;
		});
	});
</script>
</head>
<body>

	<div class="ui-table-toolbar">
		<p class="yleft padding20 gray14">
			<b>缓存管理</b>
		</p>
		<ul class="yright">
			<li><a id="deleteBtn"
				href="${basePath }/cacheAction/delete.json"><i
					class="icon-trash icon-large"></i>清除</a></li>
		</ul>
	</div>
	<table border="0" cellpadding="0" cellspacing="1" class="ui-table"
		width="100%">
		<tr>
			<td class="ui-table-header2" style="text-align: center;" width="5%"></td>
			<td class="ui-table-header2" style="text-align: center;" width="5%">序号</td>
			<td class="ui-table-header2" style="text-align: center;" width="90%">名称</td>
		</tr>
		<c:forEach var="cacheName" items="${cacheNames }" varStatus="status">
			<tr>
				<td><input type="checkbox" value="${cacheName }" /></td>
				<td style="text-align: center;">${status.index + 1 }</td>
				</td>
				<td style="text-align: center;">${cacheName }</td>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>