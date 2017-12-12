package com.zhongyang.java.zyfyback.biz;

import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.params.TimerParams;

public interface TimerBiz {
	/**
	 * 
	* @Title: timerBidPublished 
	* @Description:定时发标 
	* @return Message    返回类型 
	* @throws
	 */
	public Message timerBidPublished(TimerParams params);
}
