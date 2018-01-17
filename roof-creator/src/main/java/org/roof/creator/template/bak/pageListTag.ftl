<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>${tableDisplay}管理</title>
<%@include file="/crm/sinopec_crm_head.jsp"%>
<script type="text/javascript" src="${includeBase}/crm/${sysName}/${alias}/${alias}_list.js"></script>
</head>
<#assign key = primaryKey[0] />
<body>
	<div class="main">
		<div class="big_tit">${tableDisplay}列表</div>
		<div class="data_cont">
			<form id="${sysName}_${alias}_search_form" method="post" action="${includeBase}/${actionName}Action/list.action">
				<%@include file="/crm/sinopec_page_bar_cond.jsp"%>
				<div class="search_hd">
					<span class="fr"> 
					<input type="submit" value="查询" class="btn cx mr5">
					</span> 
					<#list fields as field>
					<span class="mr8">${(field.fieldDisplay)!''}
					<input id="${alias}_${field.fieldName}" name="${field.fieldName}" type="text" class="tt" 
					<#if (field.fieldType == "Date")> readonly value="<fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd" />"
					<#else> value="${el$}{${alias}.${field.fieldName} }" </#if> >
					</span> 
					</#list>
				</div>
				</form>
				<div class="do_mid">
					<ul class="act_do">
						<li><a class="ico_do d01" id="${sysName}_${alias}_detail_btn" href="javascript:void(0)">查看</a></li>
						<li><a class="ico_do d02" id="${sysName}_${alias}_create_btn" href="javascript:void(0)">新建</a></li>
						<li><a class="ico_do d03" id="${sysName}_${alias}_update_btn" href="javascript:void(0)">修改</a></li>
						<li><a class="ico_do d04" id="${sysName}_${alias}_delete_btn" href="javascript:void(0)">删除</a></li>
					</ul>
				</div>
				<div class="table_box">
					<table id="${sysName}_${alias}_table" class="tb_cont">
						<tr>
							<th class="w50">选择</th>
							<#list fields as field>
							<#if field.fieldName != key>
							<th class="w100">${(field.fieldDisplay)!''}</th>
							</#if>
							</#list>
						</tr>
						<c:forEach var="${alias}" items="${el$}{page.dataList }" varStatus="status">
							<tr id="maintr">
								<td>
								<input type="checkbox" name="${key}" class="ck" value="${el$}{${alias}.${key} }"> 
								<#if (drdsId ??)>
								<input type="text" style="display: none;" name="${drdsId}" class="ck" value="${el$}{${alias}.${drdsId} }">
								</#if>
								</td>
								<#list fields as field>
								<#if field.fieldName != key>
									<#if field.dbType == "datetime">
									<td><fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<#elseif field.dbType == "date">
									<td><fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd" /></td>
									<#else>
									<td>${el$}{${alias}.${field.fieldName} }</td>
									</#if>
								</#if>
								</#list>
							</tr>
						</c:forEach>
					</table>
				</div>

				<%@include file="/crm/sinopec_page_bar.jsp"%>
				
				</div>
				
				<div class="data_cont">
			      <div class="tab_top" id="taglibs"> <a href="#" class="arrow_rt"></a>
			        <ul class="tt_c">
			          <li class="cur" id="li1" ><a href="#">页签1</a></li>
			          <li id="li2" ><a href="#">页签2</a></li>
			          <li id="li3" ><a href="#">页签3</a></li>
			        </ul>
			      </div>
			      
			      <div id="tabdiv1" class="tablibs" >
			      	<iframe src="" name="main1" style="width:100%;height: 300px;" frameborder="no" border="0" id="main1"></iframe>
			      </div>
			      
			      <div id="tabdiv2" class="tablibs" style="display:none">
			      	<iframe src="" name="main2" style="width:100%;height: 300px;" frameborder="no" border="0" id="main2"></iframe>
			      </div>
			      
			      <div id="tabdiv3" class="tablibs" style="display:none">
			      	<iframe src="" name="main3" style="width:100%;height: 300px;" frameborder="no" border="0" id="main3"></iframe>
			      </div>
				
				
				
				
				
				
		</div>
	</div>

</body>
</html>
