package com.miniproject.helper;

public class AdminMessage {
	private String type;
	private String content;
	
	
	public AdminMessage() {}
	
	public AdminMessage(String content,
			String type) {
		this.content = content;
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
