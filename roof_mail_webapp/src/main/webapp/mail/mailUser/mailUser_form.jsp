<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<input type="hidden" id="mailUser_id" name="id" value="${mailUser.id }" />
<div class="nav text-center"
	style="background: #60a1ee; color: #ffffff; padding: 10px;">
	邮件用户信息 <span class="pull-right"> <i
		class="glyphicon glyphicon-remove"></i>
	</span>
</div>

<div class="panel panel-default">
	<div class="panel-body">
		<div class="col-md-3 col-xs-12 noPadding">
			<div class="form-group">
				<label class="col-xs-4 noPadding">用户名<span class='red'>*</span>
					:
				</label>
				<div class="col-xs-8 noPadding">
					<input type="text" id="mailUser_username" maxlength="255"
						name="username" value="${mailUser.username }"
						class="form-control input-sm">
				</div>
			</div>
		</div>
		<div class="col-md-3 col-xs-12 noPadding">
			<div class="form-group">
				<label class="col-xs-4 noPadding">邮箱<span class='red'>*</span>
					:
				</label>
				<div class="col-xs-8 noPadding">
					<input type="text" id="mailUser_mail" maxlength="255" name="mail"
						value="${mailUser.mail }" class="form-control input-sm">
				</div>
			</div>
		</div>
		<div class="col-md-3 col-xs-12 noPadding">
			<div class="form-group">
				<label class="col-xs-4 noPadding">订阅:</label>
				<div class="col-xs-8 noPadding">
					<select d="mailUser_enabled" name="enabled"
						class="form-control input-sm">
						<c:choose>
							<c:when test="${mailUser.enabled == true }">
								<option value="true" selected="selected">是</option>
							</c:when>
							<c:otherwise>
								<option value="true">是</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${mailUser.enabled == false }">
								<option value="false" selected="selected">否</option>
							</c:when>
							<c:otherwise>
								<option value="false">否</option>
							</c:otherwise>
						</c:choose>
					</select>
				</div>
			</div>
		</div>
	</div>
</div>