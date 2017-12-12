package com.zhongyang.java.zyfyback.returndata;

import java.util.List;

import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.vo.CorporationUserVo;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename CorporationReturn.java
 *@date 2017年8月31日上午9:14:57
 *@author suzh
 */
public class CorporationReturn {
	
	private Message message;

	private List<CorporationUserVo> corUsers;
	
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<CorporationUserVo> getCorUsers() {
		return corUsers;
	}

	public void setCorUsers(List<CorporationUserVo> corUsers) {
		this.corUsers = corUsers;
	}
	
}
