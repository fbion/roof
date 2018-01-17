ROOF.Utils.ns("com.sinopec");
com.sinopec.errorpacement = function errorpacement(error, element) {
	element.attr("title", "");
	element.tooltip({
		tooltipClass : "errorTooltipClass",
		position : {
			my : "left top",
			at : "right+5 top-5"
		},
		content : error.html()
	});
}