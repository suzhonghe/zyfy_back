package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;
import java.util.List;

import com.zhongyang.java.system.uitl.Message;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename CmsReturn.java
 *@date 2017年7月27日下午2:51:58
 *@author suzh
 */
public class CmsReturn implements Serializable{
	
	private Message message;

	private List<CmsColumnVO>columnvos;
	
	
	public List<CmsColumnVO> getColumnvos() {
		return columnvos;
	}

	public void setColumnvos(List<CmsColumnVO> columnvos) {
		this.columnvos = columnvos;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	

}
