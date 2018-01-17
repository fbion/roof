<#assign key = primaryKey[0] />
$(function() {
	<#list fields as field>
		<#if (field.fieldType == "Date")>
		ROOF.Utils.datepicker("${alias}_${field.fieldName}");
		</#if>
	</#list>
	
	var height = 380;// 高度按元素需要变更
	var width = 925;
	
	$("#reset").click(function() {
		ROOF.Utils.emptyForm($('#${sysName}_${alias}_search_form'));
		return false;
	});
	
	$("#serchBtn").click(function(){
		search();
		return false;
	});

	var table = new ROOF.SelectableTable($('#${sysName}_${alias}_table'));
	var page = new com.letv.PageBar($('#${sysName}_${alias}_search_form'));
	$("#${sysName}_${alias}_create_btn").click(function() {
		var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName() + "/${actionName}Action/create_page.action", "${tableDisplay}管理", width, height, true, true);
		return false;
	});
	$("#${sysName}_${alias}_detail_btn").click(function() {
		var trs = table.getSelectedTrNoClone();
		if (trs.length == 0 || trs.length > 1) {
			alert("请选择一行记录");
			return false;
		}
		var ${key} = trs[0].find(":input[name='${key}']").val();
		<#if (drdsId ??)>
		var ${drdsId} = trs[0].find(":input[name='${drdsId}']").val();
		</#if>
		var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName() + "/${actionName}Action/detail_page.action?${key}=" + ${key} <#if (drdsId ??)> + "&${drdsId}=" + ${drdsId}</#if> , "${tableDisplay}管理", width, height, true);
		return false;
	});
	$("#${sysName}_${alias}_update_btn").click(function() {
		var trs = table.getSelectedTrNoClone();
		if (trs.length == 0 || trs.length > 1) {
			alert("请选择一行记录");
			return false;
		}
		var ${key} = trs[0].find(":input[name='${key}']").val();
		<#if (drdsId ??)>
		var ${drdsId} = trs[0].find(":input[name='${drdsId}']").val();
		</#if>
		var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName() + "/${actionName}Action/update_page.action?${key}=" + ${key} <#if (drdsId ??)> + "&${drdsId}=" + ${drdsId}</#if> , "${tableDisplay}管理", width, height, true);
		return false;
	});
	$("#${sysName}_${alias}_delete_btn").click(function() {
		var trs = table.getSelectedTrNoClone();
		if (trs.length == 0 || trs.length > 1) {
			alert("请选择一行记录");
			return false;
		}
		if(!confirm("确定删除该条记录吗？")){
			return false;
		}
		var ${key} = trs[0].find(":input[name='${key}']").val();
		<#if (drdsId ??)>
		var ${drdsId} = trs[0].find(":input[name='${drdsId}']").val();
		</#if>
		$.ajax({
		    async: false,
		    url : basePathConst+"/${actionName}Action/delete.action",
			data: {"${key}":${key}<#if (drdsId ??)>,"${drdsId}":${drdsId}</#if>},
			    type: 'post',
			    dataType: 'json',
			    cache: false,
			    beforeSubmit: function(formData){
		   	},
			beforeSend: function(){
			},
		  	complete: function(){
		   	},
			error: function(){
			    alert('Ajax信息加载出错，请重试！');
			},
			success: function(d){
				alert(d.message);
				window.location.reload();
			}
		});
	});
	
});