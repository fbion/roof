<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<#assign key = primaryKey[0] />
<input type="hidden" id="${alias}_${key}" name="${key}" value="${el$}{${alias}.${key} }" />
  <div class="nav text-center" style="background:#60a1ee; color:#ffffff; padding:10px;">${tableDisplay}信息
  <span class="pull-right"> 
  <i class="glyphicon glyphicon-remove"></i> </span> </div>
  
  <div class="panel panel-default">
    <div class="panel-heading">基本信息</div>
    <div class="panel-body">
    <#list fields as field>
	<#if field.fieldName != key>
      <div class="col-md-3 col-xs-12 noPadding">
        <div class="form-group">
          <label class="col-xs-4 noPadding">${(field.fieldDisplay)!''}<span class='red'>*</span> : </label>
          <div class="col-xs-8 noPadding">
            <#if field.dbType == "datetime">
			<input type="text" id="${alias}_${field.fieldName}" readonly name="${field.fieldName}" value="<fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd HH:mm:ss" />" class="form-control input-sm">
			<#elseif field.dbType == "date">
			<input type="text" id="${alias}_${field.fieldName}" readonly name="${field.fieldName}" value="<fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd" />" class="form-control input-sm">
			<#else>
			<input type="text" id="${alias}_${field.fieldName}" <#if (field.len ??)>maxlength="${field.len?c}" </#if>name="${field.fieldName}" value="${el$}{${alias}.${field.fieldName} }" class="form-control input-sm">
			</#if>
          </div>
        </div>
      </div>
      </#if>
	</#list>	
    </div>
  </div>