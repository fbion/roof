<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<#assign key = primaryKey[0] />
<input type="hidden" id="${alias}_${key}" name="${key}" value="${el$}{${alias}.${key} }" />

<div class="row text-center form-wrapper">
	<div class="row">
		<#list fields as field>
		<#if field.fieldName != key>
		<div class="col-xs-2">
			<b>${(field.fieldDisplay)!''}</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<#if field.dbType == "datetime">
			<input type="text" id="${alias}_${field.fieldName}" readonly name="${field.fieldName}" value="<fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd HH:mm:ss" />" class="form-control input-sm">
			<#elseif field.dbType == "date">
			<input type="text" id="${alias}_${field.fieldName}" readonly name="${field.fieldName}" value="<fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd" />" class="form-control input-sm">
			<#else>
			<input type="text" id="${alias}_${field.fieldName}" <#if (field.len ??)>maxlength="${field.len?c}" </#if>name="${field.fieldName}" value="${el$}{${alias}.${field.fieldName} }" class="form-control input-sm">
			</#if>
		</div>
		</#if>
		</#list>
	</div>
</div>