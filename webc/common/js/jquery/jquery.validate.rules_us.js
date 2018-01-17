// 复杂密码规则
jQuery.validator.addMethod("isComplexPassword", function(value, element) {
	if (value.length < 6) {// 少于6位的密码
		return false;
	}
	if (!isNaN(value)) {// 全是数字
		return false;
	}
	var reg = /^[a-zA-Z]+$/;
	if (reg.test(value)) { // 全是字母
		return false;
	}
	return true;
}, "<font color='red'></font>Please input the number and character string with a length greater than 6 digits");

// 身份证校验规则
jQuery.validator.addMethod("isChinaIDCard", function(value, element) {
	return !value || isChinaIDCard(value);
}, "<font color='red'></font>ID number is incorrect");

//整数校验规则
jQuery.validator.addMethod("integer", function(value, element) {
	var rule = /^-?[0-9]\d*$/;
	return !value || (rule.test(value));
}, "<font color='red'></font>Please enter an integer");

// 金额校验规则
jQuery.validator.addMethod("money", function(value, element) {
	var money = /^-?\d+(\.\d{0,2})?$/;
	return this.optional(element) || (money.test(value));
}, "<font color='red'></font>Amount is not correct");

// 手机号码验证
jQuery.validator.addMethod("mobile", function(value, element) {
	var length = value.length;
	var mobile = /^((1[0-9]{1}[0-9]{1})+\d{8})$/
	return this.optional(element) || (length == 11 && mobile.test(value));
}, "Cell phone number format error");

// 电话号码验证
jQuery.validator.addMethod("phone", function(value, element) {
	var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
	return this.optional(element) || (tel.test(value));
}, "Telephone number format error");

// 邮政编码验证
jQuery.validator.addMethod("zipCode", function(value, element) {
	var tel = /^[0-9]{6}$/;
	return this.optional(element) || (tel.test(value));
}, "Encoding post error");

// QQ号码验证
jQuery.validator.addMethod("qq", function(value, element) {
	var tel = /^[1-9]\d{4,9}$/;
	return this.optional(element) || (tel.test(value));
}, "QQ number format error");

//验证邮箱
jQuery.validator.addMethod("isEmail", function(value, element) {
	var tel = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return this.optional(element) || (tel.test(value));
}, "Mailbox format error");

// IP地址验证
jQuery.validator.addMethod("ip", function(value, element) {
	var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
	return this.optional(element)
			|| (ip.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));
}, "Ip address format error");

// 字母和数字的验证
jQuery.validator.addMethod("chrnum", function(value, element) {
	var chrnum = /^([a-zA-Z0-9]+)$/;
	return this.optional(element) || (chrnum.test(value));
}, "Can only enter numbers and letters (characters A-Z, a-z, 0-9)");

// 中文的验证
jQuery.validator.addMethod("chinese", function(value, element) {
	var chinese = /^[\u4e00-\u9fa5]+$/;
	return this.optional(element) || (chinese.test(value));
}, "Can only enter Chinese");

// 下拉框验证
$.validator.addMethod("selectNone", function(value, element) {
	return value == "Please select";
}, "Must choose one");

// 字节长度验证
jQuery.validator.addMethod("byteRangeLength", function(value, element, param) {
	var length = value.length;
	for ( var i = 0; i < value.length; i++) {
		if (value.charCodeAt(i) > 127) {
			length++;
		}
	}
	return this.optional(element) || (length >= param[0] && length <= param[1]);
}, $.validator.format("Please make sure that the value of the input is between {0}-{1} bytes (one of which is 2 bytes)"));

// 字符验证
jQuery.validator.addMethod("stringCheck", function(value, element) {
	return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);
}, "Can only be included in Chinese characters, English letters, numbers, and underscores");

// 中文字两个字节
jQuery.validator.addMethod("byteRangeLength", function(value, element, param) {
	var length = value.length;
	for ( var i = 0; i < value.length; i++) {
		if (value.charCodeAt(i) > 127) {
			length++;
		}
	}
	return this.optional(element) || (length >= param[0] && length <= param[1]);
}, "Please make sure that the value of the input is between 3-15 bytes (one of which is 2 bytes)");

// 身份证号码验证
jQuery.validator.addMethod("isIdCardNo", function(value, element) {
	return this.optional(element) || isIdCardNo(value);
}, "Please enter your ID number");

// 手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
	var length = value.length;
	var mobile = /^((1[0-9]{1}[0-9]{1})+\d{8})$/
	return this.optional(element) || (length == 11 && mobile.test(value));
}, "Please fill in your mobile phone number");

// 电话号码验证
jQuery.validator.addMethod("isTel", function(value, element) {
	var tel = /^\d{3,4}-?\d{7,9}$/; // 电话号码格式010-12345678
	return this.optional(element) || (tel.test(value));
}, "Please fill in your phone number");

// 联系电话(手机/电话皆可)验证
jQuery.validator.addMethod("isPhone", function(value, element) {
	var length = value.length;
	var mobile = /^((1[0-9]{1}[0-9]{1})+\d{8})$/;
	var tel = /^\d{3,4}-?\d{7,9}$/;
	return this.optional(element) || (tel.test(value) || mobile.test(value));

}, "Please fill in your contact phone");

// 邮政编码验证
jQuery.validator.addMethod("isZipCode", function(value, element) {
	var tel = /^[0-9]{6}$/;
	return this.optional(element) || (tel.test(value));
}, "Please fill in your post encoding");


//工商注册号
jQuery.validator.addMethod("isIndustrialAndCommercialRegistrationNumber", function(value, element) {
	var tel = /^[0-9]{15}$/;
	return this.optional(element) || (tel.test(value));
}, "Please fill in your industrial and commercial registration number correctly");

//税号
jQuery.validator.addMethod("isTariff", function(value, element) {
	var tel = /^[a-zA-Z0-9]{6,9}$/;
	return this.optional(element) || (tel.test(value));
}, "Please fill in your correct number");

//组织机构代码
jQuery.validator.addMethod("isOrganizationCode", function(value, element) {
	var tel = /^[a-zA-Z0-9]{9}$/;
	return this.optional(element) || (tel.test(value));
}, "Please fill in your organization code");