package com.zhongyang.java.zyfyback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.biz.TimerBiz;
import com.zhongyang.java.zyfyback.params.TimerParams;
/**
 * 定时发标
 *@package com.zhongyang.java.zyfyback.controller
 *@filename TimerTaskController.java
 *@date 2017年8月24日上午9:55:07
 *@author suzh
 */
@Scope("prototype")
@Controller
public class TimerTaskController extends BaseController{
	
	@Autowired
	private TimerBiz timerBiz;
	/**
	 * 
	* @Title: timerBidPublished 
	* @Description:定时发标
	* @return Message    返回类型 
	* @throws
	 */
	@RequestMapping(value="/back/loan/timerBidPublished", method = RequestMethod.POST)
	public @ResponseBody Message timerBidPublished(@RequestBody TimerParams params){
		return timerBiz.timerBidPublished(params);
	}
}
