(function($) {
    $.fn.menu = function(b) {
        var c,
        item,
        httpAdress;
        b = jQuery.extend({
            Speed: 220,
            autostart: 1,
            autohide: 1
        },
        b);
        c = $(this);
        item = c.children(".panel-heading");
        function _item() {
            var a = $(this);
			if(!(a.children("span").attr("class")=="pull-right glyphicon glyphicon-menu-up")){
				if (b.autohide) {
					a.parent().parent().find(".glyphicon-menu-up").parent().next(".noPadding").slideUp(b.Speed / 1.2, 
					function() {
						$(this).parent().parent().children(".panel-primary").children(".panel-heading").children("span").removeClass("glyphicon-menu-up").addClass("glyphicon-menu-down");
					})
				}
				a.parent().children(".noPadding").slideDown(b.Speed, 
				function() {
					a.children("span").removeClass("glyphicon-menu-down").addClass("glyphicon-menu-up");
				})
			}else{
				if (b.autohide) {
					a.parent().parent().find(".glyphicon-menu-up").parent().next(".noPadding").slideUp(b.Speed / 1.2, 
					function() {
						$(this).parent().parent().children(".panel-primary").children(".panel-heading").children("span").removeClass("glyphicon-menu-up").addClass("glyphicon-menu-down");
					})
				}
			}
            
        }
        item.unbind('click').click(_item);
        if (b.autostart) {
            c.children(".panel-heading").children("a").each(function() {
                if (this.href == httpAdress) {
                    $(this).parent("li").parent("ul").slideDown(b.Speed, 
                    function() {
                        $(this).parent("li").children("span").addClass("cur")
                    })
                }
            })
        }
    }
})(jQuery);
