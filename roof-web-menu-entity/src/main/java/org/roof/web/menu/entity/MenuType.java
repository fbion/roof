package org.roof.web.menu.entity;

public enum MenuType {

	Front("前台", "1"), BackGround("后台", "2"), Mobile("手机", "3");

	private String display;
	private String code;

	private MenuType(String display, String code) {
		this.display = display;
		this.code = code;
	}

	public String getDisplay() {
		return display;
	}

	public String getCode() {
		return code;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
