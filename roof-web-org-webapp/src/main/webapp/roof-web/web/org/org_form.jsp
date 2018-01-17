<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<table border="0" cellpadding="0" cellspacing="1" class="ui-table"
	width="100%">
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">组织名:
			<input name="id" type="hidden" value="${org.id }" />
		</td>
		<td><input type="text" name="name" style="width: 300px;"
			value="${org.name }" /></td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">上级节点:</td>
		<td><input type="text" style="width: 300px;"
			value="${org.parent_id }" readonly="readonly" /></td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">等级:</td>
		<td><input type="text" style="width: 300px;" name="lvl"
			value="${org.lvl }" readonly="readonly" /></td>
	</tr>
</table>