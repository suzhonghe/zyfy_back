package com.zhongyang.java.zyfyback.pojo;

import java.io.Serializable;

public class Role implements Serializable {
	
    private String id;
    
    private String description;
    
    private String r_name;
    
    private String r_priviliges;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getR_name() {
		return r_name;
	}

	public void setR_name(String r_name) {
		this.r_name = r_name;
	}

	public String getR_priviliges() {
		return r_priviliges;
	}

	public void setR_priviliges(String r_priviliges) {
		this.r_priviliges = r_priviliges;
	}
}