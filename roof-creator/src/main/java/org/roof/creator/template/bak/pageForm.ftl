<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<#assign key = primaryKey[0] />
<div class="inner">
	<input type="hidden" id="${alias}_${key}" name="id" value="${el$}{${alias}.${key} }" />
	<div class="ct_hd">
		<h3>基本信息</h3>
	</div>
	<ul class="ac_li">
	<#list fields as field>
	<#if field.fieldName != key>
		<li>
			<div class="txt">
				${(field.fieldDisplay)!''}<span class="red">*</span>：
			</div>
			<#if field.dbType == "datetime">
			<input type="text" id="${alias}_${field.fieldName}" readonly name="${field.fieldName}" value="<fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd HH:mm:ss" />" class="tt">
			<#elseif field.dbType == "date">
			<input type="text" id="${alias}_${field.fieldName}" readonly name="${field.fieldName}" value="<fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd" />" class="tt">
			<#else>
			<input type="text" id="${alias}_${field.fieldName}" name="${field.fieldName}" value="${el$}{${alias}.${field.fieldName} }" class="tt">
			</#if>
		</li>
	</#if>
	</#list>	
	</ul>
</div>