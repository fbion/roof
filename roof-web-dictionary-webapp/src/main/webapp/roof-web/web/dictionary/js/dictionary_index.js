var treeObj = null;
var firstAsyncSuccessFlag = 0;
$(document).ready(
		function() {
			$('iframe').height(jQuery(window).height() - 40);
			var setting = {
				async : {
					enable : true,
					url : ROOF.Utils.projectName()
							+ "/dictionaryAction/read.json",
					autoParam : [ "val=type" ]
				},
				view : {
					selectedMulti : false
				},
				callback : {
					onClick : function(event, treeId, node) {
						$('iframe').attr(
								"src",
								ROOF.Utils.projectName()
										+ "/dictionaryAction/detail.action?id="
										+ node.id);
					},
					onAsyncSuccess : function() {
						if (firstAsyncSuccessFlag == 0) {
							var nodes = treeObj.getNodes();
							treeObj.expandNode(nodes[0], true);
							treeObj.selectNode(nodes[0]);
							firstAsyncSuccessFlag = 1;
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