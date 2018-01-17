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
<script type="text/javascript" src="${basePath }/roof-web/web/menu/js/menu_detail.js"></script>
<script type="text/javascript" src="${basePath }/common/js/zTree/js/jquery.ztree.all-3.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}/common/js/zTree/zTreeStyle/zTreeStyle.css" />
<title></title>
</head>
<body>
	<div class="ui-table-toolbar">
		<p class="yleft padding20 gray14">
			<b>组织信息</b>
		</p>
		<ul class="yright">
			<li><a href="${basePath }/menuAction/create_page.action?parentId=${menu.id}"><i class="icon-plus icon-large"></i>
					新增</a></li>
			<c:if test="${menu.leaf }">
				<li><a id="delSrcBtn" href="${basePath }/menuAction/delete.action?id=${menu.id }"><i
						class="icon-trash icon-large"></i> 删除</a>
				</li>
			</c:if>
			<li><a id="saveBtn" href="#"><i class="icon-save icon-large"></i> 保存</a></li>
		</ul>
	</div>
	<form id="mainForm" action="${basePath }/menuAction/update.action" method="post">
		<%@include file="menu_form.jsp"%>
	</form>

</body>
</html>