package com.zhongyang.java.zyfyback.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhongyang.java.zyfyback.biz.UtilBiz;
@CrossOrigin
@Controller
public class UtilController extends BaseController {

	@Autowired
	private UtilBiz utilBiz;

	/**
	 * 
	 * @Title: getVerificationImg @Description:获取图片验证码 @return void 返回类型 @throws
	 */

	@RequestMapping(value = "/back/util/imgValalidate", method = RequestMethod.GET)
	public void getVerificationImg(HttpServletRequest req, HttpServletResponse resp) {
		utilBiz.getVerificationImg(req, resp);
	}
}
