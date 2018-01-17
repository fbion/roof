<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<#assign key = primaryKey[0] />
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/evaluation_head.jsp"%>
		<script type="text/javascript">
		<#if type != "detail" >
		$(document).ready(function(){
			<#list fields as field>
			<#if (field.dbType == "DATE")>
			$.createGooCalendar("${alias}_${field.fieldName}",ROOF.Utils.getCalendarProperty());
			</#if>
			</#list>
		
			$("#${alias}_${type}Form").validate({
			rules : {
			<#list fields as field>
			<#if field.fieldName != key>
			<#assign isForeign = false />
				<#list relations as relation>
				<#if (relation.foreignCol == field.fieldName)>
				<#assign isForeign = true />
				"${alias}.${relation.alias}.${relation.primaryCol}" : {
					required : true
				}<#if (field_index != (fields?size-1))>, </#if>
				</#if>
				</#list>
				<#if (!(isForeign))>
				<#if (field.dbType == "NUMBER")>
				"${alias}.${field.fieldName}" : {
					number : true,
					required : true
				}<#if (field_index != (fields?size-1))>, </#if>
				<#else>
				"${alias}.${field.fieldName}" : {
					required : true
				}<#if (field_index != (fields?size-1))>, </#if>
				</#if>
				</#if>
			</#if>
			</#list>
			}
			});
			$("#${alias}_${type}Form").ajaxForm({
				url : '${actionName}Action!${type}.ajax',
				type : 'post',
				cache : false,
				dataType : 'json',
				error : function() {
					alert('Ajax信息加载出错,请重试');
				},
				success : function(replay) {
					alert(replay.message);
				}
			});
		});
		function ${type}${alias?cap_first}(){
			$("#${alias}_${type}Form").submit();
		}
		</#if>
		function ${alias}Back(){
			ROOF.Utils.back("${alias}_${type}","${alias}.${key}");
		}
		</script>
	</head>

	<body>
	<input type="hidden" id="${alias}_${type}_paramString" value="${el$}{${alias}_paramString }" />
	<form id="${alias}_${type}Form" name="${alias}_${type}Form" method="post" action="${includeBase}/${actionName}Action!list.action">
		<input type="hidden" id="${alias}_${key}" name="${alias}.${key}" value="${el$}{${alias}.${key} }" />
		<table class="personTable">
			<#list fields as field>
			<#assign isForeign = false />
			<#if field.fieldName != key>
			<tr>
				<td class="yacc" width="16%">
					${(field.fieldDisplay)!''}<font color="red">*</font>：
				</td>
				<td colspan="6">
				<#if type != "detail" >
				<#list relations as relation>
				<#if (relation.foreignCol == field.fieldName)>
				<#assign isForeign = true />
					<input id="${alias}_${relation.alias}_${relation.primaryCol}" class="ipt inputRstW" size="20" name="${alias}.${relation.alias}.${relation.primaryCol}"
						style="width: 520px;" type="text" value="${el$}{${alias}.${relation.alias}.${relation.primaryCol} }" />
				</#if>
				</#list>
				<#if (!(isForeign))>
				<#if (field.dbType == "DATE")>
					<input readonly id="${alias}_${field.fieldName}" class="ipt inputRstW" size="20" name="${alias}.${field.fieldName}" 
						style="width: 520px;" type="text" value="<fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd HH:mm:ss" />" />
				<#else>
					<input id="${alias}_${field.fieldName}" class="ipt inputRstW" size="20" name="${alias}.${field.fieldName}"
						style="width: 520px;" type="text" value="${el$}{${alias}.${field.fieldName} }" />
				</#if>
				</#if>
				<#else>
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
				</#if>
				</td>
			</tr>
			</#if>
			</#list>
			<tr>
				<td colspan="7">
					<div class="guestNewsBtn_div">
						<#if type != "detail" >
						<input class="orangeSub" id="${alias}_${type}Form_Submit" onclick="${type}${alias?cap_first}()" type="button" value="提交" />
						<input class="orangeSub" id="${alias}_${type}Form_Reset" type="reset" value="重置" />
						</#if>
						<input class="orangeSub" id="${alias}_${type}Form_Back" onclick="${alias}Back()" type="button" value="返回" />
					</div>
				</td>
			</tr>
		</table>
		</form>
	</body>
</html>
