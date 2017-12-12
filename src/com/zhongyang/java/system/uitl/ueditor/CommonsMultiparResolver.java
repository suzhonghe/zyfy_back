package com.zhongyang.java.system.uitl.ueditor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 *@package com.zhongyang.java.system.uitl.ueditor
 *@filename CommonsMultiparResolver.java
 *@date 2017年7月28日上午11:25:16
 *@author suzh
 */
public class CommonsMultiparResolver extends CommonsMultipartResolver{

	
	@Override
	public boolean isMultipart(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		if(requestURI.indexOf("/sys/ueditor/exec")>0){
			System.out.println("CommonsMultipartResolver"+"拦截");
			return false;
		}
		System.out.println("/sys/ueditor/exec"+"放行");
		return super.isMultipart(request);
	}

}
