/*
 * jQuery Mobile Framework : plugin to provide a date and time picker.
 * Copyright (c) JTSage
 * CC 3.0 Attribution.  May be relicensed without permission/notifcation.
 * https://github.com/jtsage/jquery-mobile-datebox
 *
 * Translation by: J.T.Sage <jtsage@gmail.com>
 *
 */

jQuery.extend(jQuery.mobile.datebox.prototype.options.lang, {
	'en' : {
		setDateButtonLabel : "设置日期",
		setTimeButtonLabel : "设置时间",
		setDurationButtonLabel : "Set Duration",
		calTodayButtonLabel : "选择今天",
		titleDateDialogLabel : "选择日期",
		titleTimeDialogLabel : "选择时间",
		daysOfWeek : [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ],
		daysOfWeekShort : [ "日", "一", "二", "三", "四", "五", "六" ],
		monthsOfYear : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
		monthsOfYearShort : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
		durationLabel : [ "日", "时", "分", "秒" ],
		durationDays : [ "Day", "Days" ],
		tooltip : "打开日历",
		nextMonth : "下一月",
		prevMonth : "上一月",
		timeFormat : 12,
		headerFormat : '%Y年 %B %-d日 %A',
		dateFieldOrder : [ 'y', 'm', 'd' ],
		timeFieldOrder : [ 'h', 'i', 'a' ],
		slideFieldOrder : [ 'y', 'm', 'd' ],
		dateFormat : "%Y-%-m-%-d",
		useArabicIndic : false,
		isRTL : false,
		calStartDay : 0,
		clearButton : "Clear",
		durationOrder : [ 'd', 'h', 'i', 's' ],
		meridiem : [ "AM", "PM" ],
		timeOutput : "%l:%M %p",
		durationFormat : "%Dd %DA, %Dl:%DM:%DS",
		calDateListLabel : "其他日期"
	}
});
jQuery.extend(jQuery.mobile.datebox.prototype.options, {
	useLang : 'en'
});
