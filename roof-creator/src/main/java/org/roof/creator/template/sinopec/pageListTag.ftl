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
<%@include file="/crm/sinopec_crm_head_boot.jsp"%>
<script type="text/javascript" src="${includeBase}/crm/${sysName}/${alias}/${alias}_list.js"></script>
</head>
<#assign key = primaryKey[0] />
<body>
<div class="wrap">
  <!--右侧内容-->
  <div class="col-xs-12 main">
    <div class="panel panel-primary">
      <div class="panel-heading">${tableDisplay}列表</div>
      <div class="panel-body">
      <form id="${sysName}_${alias}_search_form" method="post" action="${includeBase}/${actionName}Action/list.action">
      <%@include file="/crm/sinopec_page_bar_cond.jsp"%>
        <div class="section row">
          <div class="col-md-10 col-xs-12 noPadding">
          <#list fields as field>
            <div class="col-md-3 col-xs-12 noPadding">
              <div class="form-group">
                <label class="col-xs-4 noPadding">${(field.fieldDisplay)!''}:</label>
                <div class="col-xs-8 noPadding">
                  <input id="${alias}_${field.fieldName}" name="${field.fieldName}" type="text" class="form-control input-sm"
					<#if (field.fieldType == "Date")> readonly value="<fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd" />"
					<#else> value="${el$}{${alias}.${field.fieldName} }" </#if> >
                </div>
              </div>
            </div>
            </#list>
          </div>
          <div class="col-md-2 col-xs-12 noPadding">
            <div class="form-group">
              <input type="submit" value="查询" class="btn btn-primary btn-sm ">
              <input type="button" value="重置" id="reset" class="btn btn-primary btn-sm ">
            </div>
          </div>
        </div>
        </form>
        
        <div class="section row">
          <div class=" well well-sm" style="margin-bottom:0; background:#ffffff; border:0;"> 
          <a id="${sysName}_${alias}_detail_btn" class="btn btn-sm btn-primary" href="javascript:void(0)"> <span class="glyphicon glyphicon-list-alt"></span> 查看 </a> 
          <a id="${sysName}_${alias}_create_btn" class="btn btn-sm btn-primary" href="javascript:void(0)"> <span class="glyphicon glyphicon-plus"></span> 新建 </a> 
          <a id="${sysName}_${alias}_update_btn" class="btn btn-sm btn-primary" href="javascript:void(0)"> <span class="glyphicon glyphicon-edit"></span> 修改 </a> 
          <a id="${sysName}_${alias}_delete_btn"  class="btn btn-sm btn-primary" href="javascript:void(0)"> <span class="glyphicon glyphicon-remove"></span> 删除 </a> 
          </div>
        </div>
        <div class="table-responsive">
          <table class="table table-hover table-bordered" id="${sysName}_${alias}_table">
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
                <td>
                <input type="checkbox" name="${key}" class="ck" value="${el$}{${alias}.${key} }" ${el$}{status.count==1?'checked="true"':'' }> 
                <#if (drdsId ??)>
								<input type="hidden" name="${drdsId}" class="ck" value="${el$}{${alias}.${drdsId} }">
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
            </tbody>
          </table>
        </div>
        <%@include file="/crm/sinopec_page_bar_boot.jsp"%>
      </div>
    </div>
    
    
    
    
    
    
    
    
    <div class="panel panel-default">
    <div class="panel-heading" id="taglibs">
      <ul class="nav nav-tabs">
        <li class="active"> <a href="javascript:void(0)">页签1</a> </li>
        <li> <a href="javascript:void(0)">页签2</a> </li>
        <li> <a href="javascript:void(0)">页签3</a> </li>
      </ul>
    </div>
	<div  name="tagdivs">
	<!--建议页签使用的页面单独重新写一个，并且去除页签页面中最上方的 div class="col-xs-12 main" 的class格式  第二个div的样式改为div class="panel"-->
		<iframe src="" name="main1" style="width:100%;height: 300px;" frameborder="no" border="0" id="main1"></iframe>
	</div>
	<div  name="tagdivs" style="display:none"> 
	<iframe src="" name="main1" style="width:100%;height: 300px;" frameborder="no" border="0" id="main1"></iframe>
	</div>
	<div  name="tagdivs" style="display:none">
	<iframe src="" name="main1" style="width:100%;height: 300px;" frameborder="no" border="0" id="main1"></iframe>
	</div>
	</div>
	    
    
    
    
  </div>
</div>
</body>
</html>