package com.zhongyang.java.system.http;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.GlobalConstants;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.returndata.EmployeeReturn;

public class LoginFilter implements Filter {
	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Pattern.compile(GlobalConstants.EXCLUDE_DIRECTORY_REGEX);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		String url = httpServletRequest.getServletPath();
		Employee emp = (Employee) httpServletRequest.getSession().getAttribute("zycfLoginEmp");

		if (emp == null && url.contains("/back/") && !url.contains("/back/util") && !url.contains("/isLogin")
				&& !url.contains("/empLogin")&&!url.contains("uploadPhoto")) {
			EmployeeReturn er=new EmployeeReturn();
			Message message = new Message();
			message.setCode(SystemEnum.USER_NOLOGIN.value());
			message.setMessage("您没有登录");
			er.setMessage(message);
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.setContentType("application/json");
			httpServletResponse.getWriter().write(JSON.toJSON(er).toString());
			return;
		}
		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {

	}

}
