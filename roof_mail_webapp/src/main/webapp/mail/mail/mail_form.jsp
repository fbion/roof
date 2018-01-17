<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<input type="hidden" id="mail_id" name="id" value="${mail.id }" />
<div class="nav text-center"
	style="background: #60a1ee; color: #ffffff; padding: 10px;">
	邮件信息 <span class="pull-right"> <i
		class="glyphicon glyphicon-remove"></i>
	</span>
</div>

<div class="panel panel-default">
	<div class="panel-body">
		<div class="col-md-12 col-xs-12 noPadding">
			<div class="form-group">
				<label class="col-xs-2 noPadding">主题<span class='red'>*</span>
					:
				</label>
				<div class="col-xs-10 noPadding">
					<input type="text" id="mail_name" maxlength="255" name="name"
						value="${mail.name }" class="form-control input-sm">
				</div>
			</div>
		</div>
		<div class="col-md-12 col-xs-12 noPadding">
			<div class="form-group">
				<label class="col-xs-2 noPadding">内容<span class='red'>*</span>
					:
				</label>
				<div class="col-xs-10 noPadding">
					<script id="content" name="content" type="text/plain"
						height="height">${mail.content }</script>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript"
	src="${basePath }/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${basePath }/ueditor/ueditor.all.js"></script>
<script type="text/javascript">
	var ue = UE.getEditor('content', {
		height : 800
	});
</script>

