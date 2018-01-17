ROOF.SelectableTable = ROOF.Class({
	target : null,
	checkbox : false,
	initialize : function(table, checkbox) {
		this.target = table;
		if (checkbox) {
			this.checkbox = checkbox;
		}
		table.find('tr:gt(0)').live("click",function() {
			if (!checkbox) {
				table.find('input:checked:not(:disabled)').removeAttr('checked');
				table.find('tr:gt(0)').not($(this)).find('td').css('background', '#FFFFFF');
			}
			var $checkbox = $(this).find(':checkbox:not(:disabled)');
			if ($checkbox.attr("checked")) {
				$(this).find('td').css('background', '#FFFFFF');
				$checkbox.removeAttr("checked");
			} else {
				$(this).find('td').css('background', '#E7EEF7');
				$checkbox.attr("checked", "checked");
			}
		});
	},
	getSeleted : function() {
		if (!this.checkbox) {
			return this.target.find(':checked:not(:disabled)').val();
		}
		var result = new Array();
		this.target.find(':checked:not(:disabled)').each(function() {
			result.push($(this).val());
		});
		return result;
	},
	getSelectedTr : function() {
		var result = new Array();
		this.target.find(':checked:not(:disabled)').each(function() {
			result.push($(this).parent().parent().clone(true));
		});
		return result;
	},
	getSelectedTrNoClone : function() {
		var result = new Array();
		this.target.find(':checked:not(:disabled)').each(function() {
			result.push($(this).parent().parent());
		});
		return result;
	}
});