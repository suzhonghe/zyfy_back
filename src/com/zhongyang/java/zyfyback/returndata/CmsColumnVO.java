package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;

public class CmsColumnVO implements Serializable{
	
	private String id;
	
	private String name;
	
	private String time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
