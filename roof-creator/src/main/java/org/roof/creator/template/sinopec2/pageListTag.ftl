<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>${tableDisplay}管理</title>
<%@include file="/web_common/web_gcr_head_boot.jsp"%>
<script type="text/javascript" src="${includeBase}/crm/${sysName}/${alias}/${alias}_list.js"></script>
</head>
<#assign key = primaryKey[0] />
<body>
	<!-- 增删改成模块 start -->
	<div class="panel panel-default">
		<div class="panel-heading">${tableDisplay}列表</div>
		<div class="panel-body">
			<form id="${sysName}_${alias}_search_form" class="form-inline" method="post" action="${includeBase}/${actionName}Action/list.action">
				<%@include file="/web_common/web_page_bar_cond_default.jsp"%>
				<div class="row text-center">
					<div class="col-md-10 col-sm-12 col-xs-12">
						<#list fields as field>
						<div class="col-md-4 col-sm-6 col-xs-12 noPadding">
							<div class="form-group">
								<label class="col-xs-4 noPadding">${(field.fieldDisplay)!''}:</label>
								<div class="col-xs-8 noPadding">
									<input id="${alias}_${field.fieldName}" <#if (field.len ??)>maxlength="${field.len?c}" </#if>name="${field.fieldName}" type="text" class="form-control input-sm"
									<#if (field.fieldType == "Date")>readonly value="<fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd" />"
									<#else>value="${el$}{${alias}.${field.fieldName} }" </#if> >
								</div>
							</div>
						</div>
						</#list>
					</div>
					<div class="col-md-2 col-xs-12 btn-group">
						<div class="form-group">
							<button id="serchBtn" type="button" class="btn btn-primary btn-sm">查询</button>
							<button id="reset" type="button" class="btn btn-primary btn-sm">重置</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- 表格 -->
	<div class="panel panel-default">
		<div class="panel-heading btn-group" style="width:100%;">
			<button id="${sysName}_${alias}_detail_btn" type="button" class="btn btn-default btn-sm">
				<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>查看详情
			</button>
			<button id="${sysName}_${alias}_create_btn" type="button" class="btn btn-default btn-sm">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新建
			</button>
			<button id="${sysName}_${alias}_delete_btn" type="button" class="btn btn-default btn-sm">
				<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除
			</button>
			<button id="${sysName}_${alias}_update_btn" type="button" class="btn btn-default btn-sm">
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
			</button>
		</div>
		<div class="table-panel-body">
			<!-- Table -->
			<table id="${sysName}_${alias}_table" class="table table-bordered table-hover" >
				<thead>
					<tr class='active'>
						<th>选择</th> 
						<#list fields as field> 
						<#if field.fieldName != key>
						<th>${(field.fieldDisplay)!''}</th> 
						</#if> 
						</#list>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="${alias}" items="${el$}{page.dataList }" varStatus="status">
					<tr id="maintr">
						<td><input type="checkbox" name="${key}" class="ck" value="${el$}{${alias}.${key} }"> <#if (drdsId ??)> 
						<input type="hidden" name="${drdsId}" class="ck" value="${el$}{${alias}.${drdsId} }"> </#if></td> 
						<#list fields as field> 
						<#if field.fieldName != key> 
						<#if field.dbType == "datetime">
						<td><fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd HH:mm:ss" /></td> 
						<#elseif field.dbType == "date">
						<td><fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd" /></td> 
						<#else>
						<td>${el$}{${alias}.${field.fieldName}}</td> 
						</#if> 
						</#if>
						</#list>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<%@include file="/web_common/web_page_bar_boot.jsp"%>
	</div>
	<!-- 增删改成模块 end -->

	<!-- 页签模块 start -->
	<div class="panel panel-default bg-white" id="taglibs">
		<ul class="nav nav-tabs tab-list" role="tablist">
			<li role="presentation" class="active"><a href="javascript:void(0)">页签1</a></li>
			<li role="presentation"><a href="javascript:void(0)">页签2</a></li>
			<li role="presentation"><a href="javascript:void(0)">页签3</a></li>
		</ul>
		<div name="tagdivs" class="panel-body">
			<!--建议页签使用的页面单独重新写一个，并且去除页签页面中最上方的 div class="col-xs-12 main" 的class格式  第二个div的样式改为div class="panel"-->
			<iframe src="" name="main1" style="width: 100%; height: 300px;" frameborder="no" border="0" id="main1"></iframe>
		</div>
		<div name="tagdivs" class="panel-body" style="display: none">
			<iframe src="" name="main1" style="width: 100%; height: 300px;" frameborder="no" border="0" id="main1"></iframe>
		</div>
		<div name="tagdivs" class="panel-body" style="display: none">
			<iframe src="" name="main1" style="width: 100%; height: 300px;" frameborder="no" border="0" id="main1"></iframe>
		</div>
	</div>
	<!-- 页签模块 end -->

</body>
</html>