<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${basePath}/common/js/fort_awesome/css/font-awesome.min.css" />
<!--[if IE 7]>
  <link rel="stylesheet" href="${basePath}/common/js/fort_awesome/css/font-awesome-ie7.min.css" />
 <![endif]-->
<%@include file="/roof-web/head.jsp"%>
<script type="text/javascript" src="${basePath }/roof-web/web/org/js/org_detail.js"></script>
<title></title>
</head>
<body>
	<div class="ui-table-toolbar">
		<p class="yleft padding20 gray14">
			<b>组织信息</b>
		</p>
		<ul class="yright">
			<li><a href="${basePath }/orgAction/create_page.action?parentId=${org.id}"><i class="icon-plus icon-large"></i>
					新增</a></li>
			<c:if test="${org.leaf }">
				<li><a id="delSrcBtn" href="${basePath }/orgAction/delete.action?id=${org.id }"><i
						class="icon-trash icon-large"></i> 删除</a>
				</li>
			</c:if>
			<li><a id="saveBtn" href="#"><i class="icon-save icon-large"></i> 保存</a></li>
		</ul>
	</div>
	<form id="mainForm" action="${basePath }/orgAction/update.action" method="post">
		<%@include file="org_form.jsp"%>
	</form>

</body>
</html>