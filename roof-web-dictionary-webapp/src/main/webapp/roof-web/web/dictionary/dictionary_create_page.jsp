<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${basePath}/common/js/fort_awesome/css/font-awesome.min.css" />
<!--[if IE 7]>
  <link rel="stylesheet" href="${basePath}/common/js/fort_awesome/css/font-awesome-ie7.min.css" />
 <![endif]-->
<%@include file="/roof-web/head.jsp"%>
<script type="text/javascript"
	src="${basePath }/roof-web/web/dictionary/js/dictionary_create_page.js"></script>
<title></title>
</head>
<body>
	<div class="ui-table-toolbar">
		<p class="yleft padding20 gray14">
			<b>字典新增</b>
		</p>
		<ul class="yright">
			<li><a id="saveBtn" href="#"><i class="icon-save icon-large"></i>
					保存</a></li>
		</ul>
	</div>
	<form id="mainForm"
		action="${basePath }/dictionaryAction/create.action" method="post">
		<input name="parentId" type="hidden" value="${dictionary.id }" />
		<table border="0" cellpadding="0" cellspacing="1" class="ui-table"
			width="100%">
			<tr>
				<td class="ui-table-header2" style="text-align: center;" width="20%">类型:</td>
				<td><input type="text" readonly="readonly" name="type"
					style="width: 300px;" value="${dictionary.val }" /></td>
			</tr>
			<tr>
				<td class="ui-table-header2" style="text-align: center;" width="20%">值
					:</td>
				<td><input type="text" name="val" style="width: 300px;"
					value="" /></td>
			</tr>
			<tr>
				<td class="ui-table-header2" style="text-align: center;" width="20%">文本:</td>
				<td><input type="text" name="text" style="width: 300px;"
					value="" /></td>
			</tr>
		</table>
	</form>
</body>
</html>