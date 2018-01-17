<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var treeObj;
	jQuery(document).ready(function() {
		$("#src_select_btn").button().click(function(event) {
			$("#src_select_dialog").dialog("open");
		});

		$("#src_delete_btn").button().click(function(event) {
			$("input[name='module.id']").val("");
			$("input[name='module.name']").val("");
		});

		$("#src_select_dialog").dialog({
			autoOpen : false,
			width : 800,
			height : 600,
			modal : true,
			resizable : false,
			buttons : {
				"确定" : function() {
					var nodes = treeObj.getSelectedNodes();
					if (nodes.length > 0) {
						$("input[name='module.id']").val(nodes[0].id);
						$("input[name='module.name']").val(nodes[0].name);
					}
					$(this).dialog("close");
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			}
		});

		var setting = {
			async : {
				enable : true,
				url : ROOF.Utils.projectName() + "/resourceAction/read.json",
				autoParam : [ "id" ],
				otherParam : {
					"roleId" : $('input[name="roles.id"]').val()
				}
			},
			data : {
				key : {
					title : "title"
				}
			},
			view : {
				selectedMulti : false
			}
		};
		treeObj = $.fn.zTree.init($('.ztree'), setting);
	});
</script>
<table border="0" cellpadding="0" cellspacing="1" class="ui-table"
	width="100%">
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">名称:
			<input name="id" type="hidden" value="${menu.id }" /><input
			type="hidden" name="leaf" value="${menu.leaf }" />
		</td>
		<td><input type="text" name="name" style="width: 300px;"
			value="${menu.name }" /></td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">目标:</td>
		<td><select style="width: 300px;" name="target">
				<option value="_mainFrame">_mainFrame</option>
				<option value="_blank">_blank</option>
				<option value="_self">_self</option>
				<option value="_parent">_parent</option>
				<option value="_top">_top</option>
		</select></td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">菜单类型:</td>
		<td><select style="width: 300px;" name="menuType">
				<option value="">请选择</option>
				<c:forEach var="type" items="${menuTypes }" varStatus="status">
					<option value="${type }"
						<c:if test="${type == menu.menuType }"> selected </c:if>>${type.display }</option>
				</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">图标:</td>
		<td><input type="text" style="width: 300px;" name="icon"
			value="${menu.icon }" /></td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">序列:</td>
		<td><input type="text" style="width: 300px;" name="seq"
			value="${menu.seq }" /></td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">脚本:</td>
		<td><textarea name="script" cols="80" rows="10">${menu.script }</textarea>
		</td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">关联资源:</td>
		<td><input type="hidden" name="module.id"
			value="${menu.module.id }" /><input type="text"
			style="width: 300px; cursor: pointer;" name="module.name"
			value="${menu.module.name }" readonly="readonly"
			title="${menu.module.path }" />&nbsp;&nbsp;<a id="src_select_btn"
			class="btn" href="#"> <i class="icon-search"></i>
		</a>&nbsp;&nbsp;<a id="src_delete_btn" class="btn" href="#"> <i
				class="icon-remove"></i>
		</a></td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">上级节点:</td>
		<td><input type="hidden" name="parent.id"
			value="${menu.parent.id }" /><input type="text"
			style="width: 300px;" value="${menu.parent.name }"
			readonly="readonly" /></td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">等级:</td>
		<td><input type="text" style="width: 300px;" name="lvl"
			value="${menu.lvl }" readonly="readonly" /></td>
	</tr>
</table>

<div id="src_select_dialog" title="请选择关联资源">
	<div class="ztree" style="height: 80%; overflow: auto;"></div>
</div>