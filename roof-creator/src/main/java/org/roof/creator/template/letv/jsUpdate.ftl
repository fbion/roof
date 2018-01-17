$(function() {
	<#list fields as field>
	<#if (field.fieldType == "Date")>
	ROOF.Utils.datepicker("${alias}_${field.fieldName}");
	</#if>
	</#list>

	$('#${sysName}_${alias}_form').validate({
		rules : {
		<#list fields as field>
			'${field.fieldName}' : {
			<#if ((field.fieldType == "Long")||(field.fieldType == "Integer")||(field.fieldType == "Double"))>
				number : true,
			</#if>
				required : true
			}<#if (field_index != (fields?size-1))>, </#if>
		</#list>
		},
		messages : {
			<#list fields as field>
			'${field.fieldName}' : {
				required : "${field.fieldDisplay}必填"
			}<#if (field_index != (fields?size-1))>, </#if>
			</#list>
		},
		errorPlacement : com.letv.errorpacement
	});
	$("#${sysName}_${alias}_form").ajaxForm({
		'type' : 'post',
		'cache' : false,
		'dataType' : 'json',
		'clearForm' : true,
		'success' : function(d) {
			alert(d.message);
			$("#${sysName}_${alias}_update_close_btn").click();
		},
		'error' : function(d) {
			alert(d.statusText);
		}
	});

	$("#${sysName}_${alias}_detail_close_btn").click(function() {
		reloadFun();		
		return false;
	});

	$("#${sysName}_${alias}_update_close_btn").click(function() {
		reloadFun();		
		return false;
	});
});