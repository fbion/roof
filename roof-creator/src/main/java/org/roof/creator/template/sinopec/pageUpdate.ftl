<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>修改${tableDisplay}信息</title>
<%@include file="/crm/sinopec_crm_head_boot.jsp"%>
<script type="text/javascript" src="${includeBase}/crm/${sysName}/${alias}/${alias}_update.js"></script>
</head>
<body>
<div class="conte">
		<form id="${sysName}_${alias}_form" action="${includeBase}/${actionName}Action/update.json" method="post">
		<%@include file="${alias}_form.jsp"%>
  
  <div class="container text-center form-group">
    <div class="col-xs-6 col-md-2 col-md-offset-4"><input type="submit" value="提交" class="btn btn-primary"></div>
    <div class="col-xs-6 col-md-2"><a id="${sysName}_${alias}_update_close_btn" href="javascript:void(0)" class="btn btn-default">取消</a></div>
  </div>
  </form>
</div>
</body>
</html>