package com.zhongyang.java.zyfyback.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.system.authority.Authorities;
import com.zhongyang.java.system.authority.FireAuthority;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.biz.EmployeeBiz;
import com.zhongyang.java.zyfyback.params.EmployeeParams;
import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.returndata.EmployeeReturn;

@CrossOrigin
@Controller
public class EmployeeController extends BaseController{

	@Autowired
	private EmployeeBiz employeeBiz;
	
	/**
	 * 
	* @Title: addEmployee 
	* @Description:员工添加
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.EMPADD)
	@RequestMapping(value="/back/emp/addEmployee",method=RequestMethod.POST)
	public  @ResponseBody EmployeeReturn addEmployee(HttpServletRequest request,@RequestBody EmployeeParams params){
		return employeeBiz.addEmployee(request, params);
	}
	
	/**
	 * 
	* @Title: empLogin 
	* @Description:员工登录 
	* @return Message    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/back/emp/empLogin",method=RequestMethod.POST)
	public @ResponseBody EmployeeReturn empLogin(HttpServletRequest req, @RequestBody EmployeeParams params) {
		return employeeBiz.empLogin(req, params);
	}
	
	/**
	 * 
	* @Title: empModifyInfo 
	* @Description:员工信息修改
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.EMPUPD)
	@RequestMapping(value="/back/emp/empModifyInfo",method=RequestMethod.POST)
	public @ResponseBody EmployeeReturn empModifyInfo(HttpServletRequest request,@RequestBody EmployeeParams params){
		return employeeBiz.empModifyInfo(request,params);
	}
	
	@RequestMapping(value="/back/emp/emplogout",method=RequestMethod.POST)
	public @ResponseBody EmployeeReturn empLogout(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		session.removeAttribute("zycfLoginEmp");
		session.removeAttribute("emp_auths");
		EmployeeReturn er=new EmployeeReturn();
		er.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"退出成功"));
		return er;
	}
	
	/**
	 * 
	* @Title: getEmployees 
	* @Description:员工列表
	* @return EmoloyeeReturn    返回类型 
	* @throws
	 */
	
	@FireAuthority(authorities=Authorities.EMPLIST)
	@RequestMapping(value="/back/emp/empList",method=RequestMethod.POST)
	public @ResponseBody EmployeeReturn getEmployees(HttpServletRequest request, @RequestBody Page<Employee> page){
		return employeeBiz.getEmpList(request, page);
	}
	
	/**
	 * 
	* @Title: dispatchEmpRole 
	* @Description:员工分配角色及更改组织关系
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.EMPUPD)
	@RequestMapping(value="/back/emp/dispatchEmpRole",method=RequestMethod.POST)
	public @ResponseBody EmployeeReturn dispatchEmpRole(@RequestBody EmployeeParams params){
		return employeeBiz.dispatchEmpRole(params);
	}
	
	/**
	 * 获取员工登录状态
	 *@date 下午3:30:10
	 *@param request
	 *@return
	 *@author suzh
	 */

	@RequestMapping(value="/back/emp/isLogin",method=RequestMethod.POST)
	public @ResponseBody EmployeeReturn isLogin(HttpServletRequest request){
		return employeeBiz.isLogin(request);
	}
}
