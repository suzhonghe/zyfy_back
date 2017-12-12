package com.zhongyang.java.system.authority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.returndata.EmployeeReturn;

public class AuthorityAspect implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		FireAuthority fireAuthority = AnnotationUtils.findAnnotation(
				invocation.getMethod(), FireAuthority.class);

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getResponse();

		String url = request.getServletPath();
		Employee emp = (Employee) request.getSession().getAttribute("zycfLoginEmp");

		if (emp == null && url.contains("/back/") && !url.contains("/back/util") && !url.contains("/isLogin")
				&& !url.contains("/empLogin")&&!url.contains("uploadPhoto")) {
			EmployeeReturn er=new EmployeeReturn();
			Message message = new Message();
			message.setCode(SystemEnum.USER_NOLOGIN.value());
			message.setMessage("您没有登录");
			er.setMessage(message);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.setContentType("text/json");
			response.getWriter().write(JSON.toJSON(er).toString());
			return null;
		}
		
		if (fireAuthority != null) {

			String priviliges = (String)session.getAttribute("emp_auths");
			
			for(Authorities au: fireAuthority.authorities()){
				
				if(AuthorityHelper.checkAuthority(au.getIndex(), priviliges))
					return invocation.proceed();
				else{
					EmployeeReturn er=new EmployeeReturn();
					Message message = new Message();
					message.setCode(SystemEnum.AUTHENTICATION_FAIL.value());
					message.setMessage("用户未授权");
					er.setMessage(message);
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json");
					response.setContentType("text/json");
					response.getWriter().write(JSON.toJSON(er).toString());
					return null;
				}
			}
			
			return invocation.proceed();
		}
		return invocation.proceed();
	}

}
