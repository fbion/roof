var treeObj = null;
var tree = null;
var firstAsyncSuccessFlag = 0;
$(document).ready(function() {
	$('iframe').height(jQuery(window).height() - 40);
	tree = new roof.web.OrgTree($('.ztree'), {
				onClick : function(event, treeId, node) {
					$('iframe').attr("src",
							ROOF.Utils.projectName() + "/orgAction/detail.action?id=" + node.id);
				},
				onAsyncSuccess : function() {
					if (firstAsyncSuccessFlag == 0) {
						var nodes = treeObj.getNodes();
						treeObj.expandNode(nodes[0], true);
						treeObj.selectNode(nodes[0]);
						firstAsyncSuccessFlag = 1;
					}
				}
			});
	treeObj = tree.getTreeObj();
});

function getSeletedNode() {
	tree.getSeletedNode();
}

function reAsyncChildNodes() {
	tree.reAsyncChildNodes();
}

function reAsyncParentChildNodes() {
	tree.reAsyncParentChildNodes();
}