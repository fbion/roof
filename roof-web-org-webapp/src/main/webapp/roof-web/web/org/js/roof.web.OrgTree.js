ROOF.Utils.ns('roof.web');
roof.web.OrgTree = ROOF.Class({
	treeObj : null,
	setting : {
		async : {
			enable : true,
			url : ROOF.Utils.projectName() + "/orgAction/read.json",
			autoParam : [ "id" ]
		},
		view : {
			selectedMulti : false
		}
	},
	initialize : function(target, callback, url) {
		this.setting.callback = callback;
		if (url) {
			this.setting.async.url = url;
		}
		this.treeObj = $.fn.zTree.init(target, this.setting);
		return this;
	},
	getTreeObj : function() {
		return this.treeObj;
	},
	getSeletedNode : function() {
		var nodes = treeObj.getSelectedNodes();
		if (nodes.length > 0) {
			return nodes[0];
		}
		return null;
	},
	reAsyncChildNodes : function() {
		var nodes = treeObj.getSelectedNodes();
		if (nodes.length > 0) {
			nodes[0].isParent = true;
			treeObj.reAsyncChildNodes(nodes[0], "refresh");
		}
	},
	reAsyncParentChildNodes : function() {
		var nodes = treeObj.getSelectedNodes();
		if (nodes.length > 0) {
			treeObj.reAsyncChildNodes(nodes[0].getParentNode(), "refresh");
		}
	}
});