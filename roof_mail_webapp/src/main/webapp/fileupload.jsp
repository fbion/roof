<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传</title>
<%@include file="/head_boot.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#file_upload_form").ajaxForm(
				{
					'type' : 'post',
					'cache' : false,
					'dataType' : 'json',
					'clearForm' : true,
					'success' : function(d) {
						var src = ROOF.Utils.projectName()
								+ "/fileAction/get/" + d.data.name
								+ ".action";
						$("body").append("<img alt='' src='"+src+"' width='500' height='500'>");
						$("body").append("<p >"+ src + "</p>");
					},
					'error' : function(d) {
					}
				});
		$("#file_upload_form_base64").ajaxForm(
				{
					'type' : 'post',
					'cache' : false,
					'dataType' : 'json',
					'clearForm' : true,
					'success' : function(d) {
						var src = ROOF.Utils.projectName()
								+ "/fileAction/get/" + d.data.webPath
								+ ".action";
						$("body").append("<img alt='' src='"+src+"'>");
					},
					'error' : function(d) {
					}
				});
	});
</script>
</head>
<body>
	<form id="file_upload_form" action="${basePath }/fileAction/upload.json" method="post" enctype="multipart/form-data">
		<input type="file" name="file" /> <input type="submit" value="提交" />
	</form>
<!-- <form id="file_upload_form_base64" action="${basePath }/fileAction/uploadBase64.json" method="post" enctype="multipart/form-data">
		<input type="text" name="file" /> <input type="submit" value="提交" />
	</form> -->	

</body>
</html>