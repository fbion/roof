ROOF.Utils.ns("${packagePath}");
<#assign key = primaryKey[0] />
var ${alias} = null;
$(document).ready(function(){
	ROOF.Utils.ajaxcommon();
	<#list fields as field>
	<#if (field.dbType == "DATE")>
	$.createGooCalendar("${alias}_${field.fieldName}",ROOF.Utils.getCalendarProperty("yyyy-MM-dd"));
	</#if>
	</#list>
	
	${alias} = new ${packagePath}.${alias?cap_first}($("#${alias}Form"));
});

${packagePath}.${alias?cap_first} = ROOF.Class({
	elementObj : null,
	initialize : function(elementObj) {
		this.elementObj = elementObj;
		elementObj.find("#${alias}_list").click(this.list${alias?cap_first});
		elementObj.find("#${alias}_empty").click(this.empty${alias?cap_first});
		elementObj.find("#${alias}_create").click(this.create${alias?cap_first});
		elementObj.find("#${alias}_update").click(this.update${alias?cap_first});
		elementObj.find("#${alias}_detail").click(this.detail${alias?cap_first});
		elementObj.find("#${alias}_delete").click(this.delete${alias?cap_first});
		elementObj.find("#${alias}List_${key}_checkbox").click(function(){
			ROOF.Utils.checkAllBox(this,'${alias}List.${key}');
		});
	},
	list${alias?cap_first} : function() {
		$("#${alias}Form").attr("action","${actionName}Action!list.action");
		$("#${alias}Form").submit();
	},
	empty${alias?cap_first} : function() {
		ROOF.Utils.emptyForm($("#${alias}_condForm"));
	},
	create${alias?cap_first} : function() {
		$("#${alias}Form").attr("action","${actionName}Action!create_page.action");
		$("#${alias}Form").submit();
	},
	update${alias?cap_first} : function() {
		var o = ROOF.Utils.getCheckedObj('${alias}List.${key}');
		if(o){
			$("#${alias}Form").attr("action","${actionName}Action!update_page.action?${alias}.${key}="+o);
			$("#${alias}Form").submit();
		}
	},
	detail${alias?cap_first} : function() {
		var o = ROOF.Utils.getCheckedObj('${alias}List.${key}');
		if(o){
			$("#${alias}Form").attr("action","${actionName}Action!detail_page.action?${alias}.${key}="+o);
			$("#${alias}Form").submit();
		}
	},
	delete${alias?cap_first} : function() {
		if(!confirm("确定删除选中数据？")){
			return;
		}
		 $.ajax({
			    async: false,
			    url: '${actionName}Action!delete.ajax',
			    data: $('#${alias}Form').serialize(),
			    type: 'post',
			    dataType: 'json',    
				cache: false,
			    error: function(){
			        alert('Ajax信息加载出错，请重试！');
			    },
			    success: function(replay){
			    	if(replay.state == "success"){
				    	alert(replay.message);
				    	${alias}.list${alias?cap_first}();
			    	}
			}
		})
	}
});