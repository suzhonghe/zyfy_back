package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.Message;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename StaticReturn.java
 *@date 2017年8月18日下午1:41:07
 *@author suzh
 */
public class StaticReturn implements Serializable{
	
	private Message message;
	
	private Page<MarkStatisticVo> markStaticVo;
	
	private Page<InviteStatisticVo>inviteStatisticVo;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Page<MarkStatisticVo> getMarkStaticVo() {
		return markStaticVo;
	}

	public void setMarkStaticVo(Page<MarkStatisticVo> markStaticVo) {
		this.markStaticVo = markStaticVo;
	}

	public Page<InviteStatisticVo> getInviteStatisticVo() {
		return inviteStatisticVo;
	}

	public void setInviteStatisticVo(Page<InviteStatisticVo> inviteStatisticVo) {
		this.inviteStatisticVo = inviteStatisticVo;
	}
	
}
