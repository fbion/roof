<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>修改t_mail_user信息</title>
<%@include file="/head_boot.jsp"%>
<script type="text/javascript" src="${basePath}/mail/mailUser/mailUser_update.js"></script>
</head>
<body>
<div class="conte">
		<form id="mailuser_mailUser_form" action="${basePath}/mail/mailuserAction/update.json" method="post">
		<%@include file="mailUser_form.jsp"%>
  
  <div class="container text-center form-group">
    <div class="col-xs-6 col-md-2 col-md-offset-4"><input type="submit" value="提交" class="btn btn-primary btn-block"></div>
    <div class="col-xs-6 col-md-2"><a id="mailuser_mailUser_update_close_btn" href="javascript:void(0)" class="btn btn-default btn-block">取消</a></div>
  </div>
  </form>
</div>
</body>
</html>