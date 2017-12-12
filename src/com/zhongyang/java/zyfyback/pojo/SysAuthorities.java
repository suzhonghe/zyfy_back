package com.zhongyang.java.zyfyback.pojo;

import java.io.Serializable;

/**
 * 
* @Title: SysAuthorities.java 
* @Package com.zhongyang.java.zyfyback.pojo 
* @Description:权限类
* @author 苏忠贺   
* @date 2017年6月7日 下午3:03:32 
* @version V1.0
 */
public class SysAuthorities implements Serializable{
	
    private String sysPrivilige;
    
    private String priName;
    
    private String cato;

	private Integer priPindex;
	
    private String priType;
    
    private boolean flag;

	public String getSysPrivilige() {
		return sysPrivilige;
	}

	public void setSysPrivilige(String sysPrivilige) {
		this.sysPrivilige = sysPrivilige;
	}

	public String getPriName() {
		return priName;
	}

	public void setPriName(String priName) {
		this.priName = priName;
	}

	public String getCato() {
		return cato;
	}

	public void setCato(String cato) {
		this.cato = cato;
	}

	public Integer getPriPindex() {
		return priPindex;
	}

	public void setPriPindex(Integer priPindex) {
		this.priPindex = priPindex;
	}

	public String getPriType() {
		return priType;
	}

	public void setPriType(String priType) {
		this.priType = priType;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}