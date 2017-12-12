package com.zhongyang.java.zyfyback.params;

import java.io.Serializable;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.returndata.InviteStatisticVo;
import com.zhongyang.java.zyfyback.returndata.MarkStatisticVo;

/**
 *@package com.zhongyang.java.zyfyback.params
 *@filename StaticParams.java
 *@date 2017年8月21日上午11:20:44
 *@author suzh
 */
public class StaticParams implements Serializable{
	
	Page<MarkStatisticVo> markPage;
	
	Page<InviteStatisticVo> invitePage;

	public Page<MarkStatisticVo> getMarkPage() {
		return markPage;
	}

	public void setMarkPage(Page<MarkStatisticVo> markPage) {
		this.markPage = markPage;
	}

	public Page<InviteStatisticVo> getInvitePage() {
		return invitePage;
	}

	public void setInvitePage(Page<InviteStatisticVo> invitePage) {
		this.invitePage = invitePage;
	}
	}
