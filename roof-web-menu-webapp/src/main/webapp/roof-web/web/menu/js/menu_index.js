var treeObj = null;
var firstAsyncSuccessFlag = 0;
$(document).ready(
		function() {
			$('iframe').height(jQuery(window).height() - 40);
			var setting = {
				async : {
					enable : true,
					url : ROOF.Utils.projectName() + "/menuAction/read.json",
					autoParam : [ "id" ]
				},
				view : {
					selectedMulti : false
				},
				edit : {
					enable : true,
					showRemoveBtn : false,
					showRenameBtn : false,
					drag : {
						isCopy : false,
						isMove : true,
						prev : false,
						next : false
					}
				},
				data : {
					key : {
						title : "title"
					}
				},
				callback : {
					onClick : function(event, treeId, node) {
						$('iframe').attr(
								"src",
								ROOF.Utils.projectName()
										+ "/menuAction/detail.action?id="
										+ node.id);
					},
					onAsyncSuccess : function() {
						if (firstAsyncSuccessFlag == 0) {
							var nodes = treeObj.getNodes();
							treeObj.expandNode(nodes[0], true);
							treeObj.selectNode(nodes[0]);
							firstAsyncSuccessFlag = 1;
						}
					},
					beforeDrop : function(treeId, treeNodes, targetNode,
							moveType, isCopy) {
						if (confirm("是否移动选中节点到[" + targetNode.name + "]下!")) {
							$.post(ROOF.Utils.projectName()
									+ "/menuAction/moveTo.json", {
								'id' : treeNodes[0].id,
								'parentId' : targetNode.id
							}, function(d) {
								ROOF.Utils.alert(d.message);
							}, 'json');
							return true;
						} else {
							return false;
						}
					}
				}
			};
			treeObj = $.fn.zTree.init($('.ztree'), setting);
		});

function getSeletedNode() {
	var nodes = treeObj.getSelectedNodes();
	if (nodes.length > 0) {
		return nodes[0];
	}
	return null;
}

function reAsyncChildNodes() {
	var nodes = treeObj.getSelectedNodes();
	if (nodes.length > 0) {
		nodes[0].isParent = true;
		treeObj.reAsyncChildNodes(nodes[0], "refresh");
	}
}

function reAsyncParentChildNodes() {
	var nodes = treeObj.getSelectedNodes();
	if (nodes.length > 0) {
		treeObj.reAsyncChildNodes(nodes[0].getParentNode(), "refresh");
	}
}