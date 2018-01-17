<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<#assign key = primaryKey[0] />
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/evaluation_head.jsp"%>
		<script type="text/javascript" src="${includeBase}/${sysName}/${alias}/js/${alias}_index.js"></script>
	</head>

	<body>
	<form id="${alias}Form" name="${alias}Form" method="post" action="${includeBase}/${actionName}Action!list.action">
		<table class="personTable" id="${alias}_condForm">
			<#assign rows = 3 />
			<#assign remainder = 0 />
			<#list fields as field>
			<#assign isForeign = false />
			<#if field.fieldName != key>
			<#if (field_index % rows == 0) >
			<#assign remainder = fields?size % rows  />
			<tr>
			</#if>
			<td width="8%">${(field.fieldDisplay)!''}：</td>
			<td>
				<#list relations as relation>
				<#if (relation.foreignCol == field.fieldName)>
				<#assign isForeign = true />
				<input type="text" id="${alias}_${relation.alias}_${relation.primaryCol}" name="${alias}.${relation.alias}.${relation.primaryCol}" size="20" class="ipt" style="color:#666; font-size:16px;" value='${el$}{${alias}.${relation.alias}.${relation.primaryCol} }'/>
				</#if>
				</#list>
				<#if (!(isForeign))>
				 <input type="text" id="${alias}_${field.fieldName}" name="${alias}.${field.fieldName}" size="20" class="ipt" style="color:#666; font-size:16px;" <#if (field.dbType == "DATE")>readonly value='<fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd" />'<#else>value='${el$}{${alias}.${field.fieldName} }'</#if>/>
				</#if>
			</td>
			<#if (field_index % rows == (rows-1)) >
			</tr>
			</#if>
			</#if>
			</#list>
			<#list (remainder+1)..rows as i >
			<td width="8%">&nbsp;</td><td>&nbsp;</td>
			</#list>
			<#if remainder != (rows-1)></tr></#if>
			<tr>
				<td colspan="10">
					<input id="${alias}_list" align="middle" class="orangeSub" type="button" value="查询" />
					<input id="${alias}_empty" align="middle" class="orangeSub" type="button" value="清空" title="清空查询条件" />
					<input id="${alias}_create" align="middle" class="orangeSub" type="button" value="新增" />
					<input id="${alias}_update" align="middle" class="orangeSub" type="button" value="修改" />
					<input id="${alias}_delete" align="middle" class="orangeSub" type="button" value="删除" />
					<input id="${alias}_detail" align="middle" class="orangeSub" type="button" value="查看" />
				</td>
			</tr>
			</table>
			<table class="personTable">
			<tr>
				<th>
					<input type="checkbox" id="${alias}List_${key}_checkbox" />
				</th>
			<#list fields as field>
				<#if field.fieldName != key>
				<th>
					${(field.fieldDisplay)!''}
				</th>
				</#if> 
			</#list>
			</tr>
			
			<c:forEach var="${alias}" varStatus="status" items="${el$}{page.dataList }">
			<tr>
				<td align="center">
					<input name="${alias}List.${key}" id="${alias}_id_${el$}{status.index + 1 }" type="checkbox" value="${el$}{${alias}.${key} }">
				</td>
				<#list fields as field>
				<#assign isForeign = false />
				<#if field.fieldName != key>
				<td align="center">
				<#list relations as relation>
				<#if (relation.foreignCol == field.fieldName)>
				<#assign isForeign = true />
					${el$}{${alias}.${relation.alias}.${relation.primaryCol} }
				</#if>
				</#list>
				<#if (!(isForeign))>
				<#if (field.dbType == "DATE")>
					<fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd HH:mm:ss" />
				<#else>
					${el$}{${alias}.${field.fieldName} }
				</#if>
				</#if>
				</td>
				</#if>
				</#list>
			</tr>
			</c:forEach>
		</table>
		<c:set var="pageForm" value="${alias}Form" />
		<%@include file="/evaluation_page.jsp"%>
		</form>
	</body>
</html>
