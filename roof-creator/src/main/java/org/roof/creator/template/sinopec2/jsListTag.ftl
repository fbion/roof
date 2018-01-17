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
		var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName() + "/${actionName}Action/detail_page.action?${key}=" + ${key} <#if (drdsId ??)> + "&${drdsId}=" + ${drdsId}</#if> , "${tableDisplay}管理",width, height, true);
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
		var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName() + "/${actionName}Action/update_page.action?${key}=" + ${key} <#if (drdsId ??)> + "&${drdsId}=" + ${drdsId}</#if> , "${tableDisplay}管理",width, height, true);
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
	
	
	/**
	 * 页签切换功能
	 * 页签标签的父div id 命名为 taglibs
	 * 页签数据的div 将name设置为tagdivs
	 * 
	 */
	$("#taglibs li").each(function (i) {
		$(this).live('click', function() { 
			//记录当前点击的页签序号
			tag_index = i;
			//对div进行隐藏和显示，页签css进行修改
			//var idx = i+1;
			$("div[name='tagdivs']").hide();
			$("#taglibs li").attr("class","");
			//0629 更新 增加li以及div的扩展性 使页签切换不需读取li和div的id
			$(this).attr("class","active");
			$("div[name='tagdivs']").eq(i).show();
			
			//取对应页签的对应记录的key 
			var tag_key = tag_key_list[i];
			//keyid不为0，即非初始化页面的状态下才考虑load数据
			if(key_id!=0){
				//页签对应的记录key与页面的keyid不相等的情况下，才重新load数据
				if(tag_key!=key_id){
					
					loadTag();
					
				}
			}
			
		});
		
    });
	
	/**
	 * 主页面数据列表 行点击绑定
	 * 需要将数据列表的tr的id命名为maintr
	 * 
	 */
	$("tr#maintr").each(function (i) {
		$(this).live('click', function() {
			//获取被点击行的主键id 将会根据这个主键id去load下方的页签元素 目前获取的是第一列的值 如需修改，修改第一个eq的index即可
			var click_id = $(this).children().eq(0).children().eq(0).val();
			
			//如果被点击的主键id和页面记录的主键id相等那么就不做操作
			if(click_id==key_id){
				return false;
			}else{
				//否则就开始刷新页签
				
				//绑定点击的id到页面记录的keyid，记录当前点击的keyid
				key_id = click_id;
				//load页签的iframe
				loadTag();
			}
			
		});
    });
	
	
});


//主键id，记录当前点击的主键id，默认为0
var key_id = 0;

//页签id，记录当前显示的页签index 默认为0
var tag_index = 0;

//各链接定义数组  按照顺序进行定义（解析时将会按照顺序index进行解析取数） 填入对应action地址
var action_list = new Array(
		"/sc/orderItemAction/list.action?order_id=",
		"/sc/custodyDeliveAction/list.action?order_id=",
		"/sc/orderItemAction/list.action?order_id=");

//页签数据记录数组，用于记录页签对应的keyid，默认为0，数组元素个数与页签个数相等即可
var tag_key_list = new Array("0","0","0");

function loadTag(){
	//根据页面当前页签的index获取action
	var the_action = action_list[tag_index];
	//采取此方法可以不必注意div和iframe的id以及name 如果需要精确查找，可以使用精确标明法
	$("div[name='tagdivs']").eq(tag_index).children().eq(0).attr("src",basePathConst+the_action+key_id);

	//load过数据之后，将key_id记录到对应的taglist中
	tag_key_list[tag_index]=key_id;
	
	//给父iframe动态赋高度
	$("div[name='tagdivs']").eq(tag_index).children().eq(0).load(function(){
	    var mainheight = $(this).contents().find("body").height()+30;
	    $(this).height(mainheight);
	});
	
}
