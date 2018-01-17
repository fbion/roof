jQuery.extend(jQuery.validator.messages, {
	required : "Non null field",
	remote : "Please correct this field",
	email : "Please enter email in the correct format",
	url : "Please enter a valid URL.",
	date : "Please enter a valid date",
	dateISO : "Please enter a valid date (ISO).",
	number : "Please enter a valid number.",
	digits : "Can only enter a positive integer",
	creditcard : "Please enter a valid credit card number.",
	equalTo : "Please enter the same value again",
	accept : "Enter a string with a valid suffix.",
	maxlength : jQuery.validator.format("Please enter a string with a maximum length of {0}"),
	minlength : jQuery.validator.format("Please enter a string with a minimum length of {0}"),
	rangelength : jQuery.validator.format("Please enter a string between {0} and {1}"),
	range : jQuery.validator.format("Please enter a value between {0} and {1}"),
	max : jQuery.validator.format("Please enter a maximum value for {0}"),
	min : jQuery.validator.format("Please enter a value for the minimum value of {0}")
});