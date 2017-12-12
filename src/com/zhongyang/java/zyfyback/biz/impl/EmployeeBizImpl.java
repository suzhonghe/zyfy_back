package com.zhongyang.java.zyfyback.biz.impl;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.authority.AuthorityHelper;
import com.zhongyang.java.system.authority.MStringUtils;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.DESTextCipher;
import com.zhongyang.java.system.uitl.FormatUtils;
import com.zhongyang.java.system.uitl.GetUUID;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.system.uitl.Password;
import com.zhongyang.java.system.uitl.TextCipher;
import com.zhongyang.java.system.uitl.Validator;
import com.zhongyang.java.zyfyback.biz.EmployeeBiz;
import com.zhongyang.java.zyfyback.params.EmployeeParams;
import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.pojo.EmployeeRole;
import com.zhongyang.java.zyfyback.pojo.Organization;
import com.zhongyang.java.zyfyback.pojo.Role;
import com.zhongyang.java.zyfyback.returndata.EmployeeReturn;
import com.zhongyang.java.zyfyback.service.EmployeeRoleService;
import com.zhongyang.java.zyfyback.service.EmployeeService;
import com.zhongyang.java.zyfyback.service.OrganizationService;
import com.zhongyang.java.zyfyback.service.RoleService;

@Service
public class EmployeeBizImpl implements EmployeeBiz {

	private static Logger logger = Logger.getLogger(EmployeeBizImpl.class);

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private EmployeeRoleService employeeRoleService;

	@Autowired
	private OrganizationService organizationService;

	@Override
	public EmployeeReturn empLogin(HttpServletRequest req, EmployeeParams params) {
		EmployeeReturn er = new EmployeeReturn();
		
		try {
			logger.info("登录sessionId："+req.getSession().getId());
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			if (params == null || params.getEmpLoginParams() == null) 
				throw new UException(SystemEnum.PARAMS_ERROR,"参数接收异常");
			
			if(params.getEmpLoginParams().getCode()==null||"".equals(params.getEmpLoginParams().getCode()))
				throw new UException(SystemEnum.PARAMS_ERROR,"验证码不能为空");
			
			String code=(String)req.getSession().getAttribute("code");
			if(code==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"验证码已失效，请重新获取");
			
			if(!code.equalsIgnoreCase(params.getEmpLoginParams().getCode()))
				throw new UException(SystemEnum.PARAMS_ERROR,"验证码错误");
			
			Employee emp = new Employee();
			if (Validator.isMobile(params.getEmpLoginParams().getLoginName())) {
				try {
					emp.setMobile(cipher.encrypt(params.getEmpLoginParams().getLoginName()));
				} catch (GeneralSecurityException e) {
					logger.info("数据加密异常");
					logger.info(e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据加密异常");
				}
			} else {
				emp.setLoginName(params.getEmpLoginParams().getLoginName());

			}
			Employee employee = employeeService.queryEmployeeByParams(emp);
			if (employee != null) {
				if (!employee.getEnabled()) 
					throw new UException(SystemEnum.EMP_FORBID_OPRATION,"禁止登录");

				String salt = employee.getSalt();
				String password = Password.getPassphrase(salt, params.getEmpLoginParams().getPassphrase());

				if (employee.getPassphrase().equals(password)) {
					params.getEmpLoginParams().setLoginCounter(0);

					employee.setPassphrase(null);
					employee.setSalt(null);

					req.getSession().setAttribute("zycfLoginEmp", employee);

					emp.setLastLoginDate(new Date());
					emp.setId(employee.getId());
					emp.setMobile(null);
					emp.setLoginName(null);
					employeeService.modifyEmployeeByParams(emp);

					req.getSession().setAttribute("emp_auths", this.getEmpAuthorities(employee.getId()));
					req.getSession().removeAttribute("code");
					er.setEmp(employee);
					er.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "登陆成功"));
					

				} else
					throw new UException(SystemEnum.USER_PASSWORD_VAILD_FAILURE,"用户名和密码不匹配");
				
			} else 
				throw new UException(SystemEnum.USER_NOT_EXISTS,"用户不存在");
		} catch (UException e) {
			logger.info("---------------登录异常--------------");
			logger.info(e, e.fillInStackTrace());
			er.setMessage(new Message(e.getCode().value(), e.getMessage()));

		}
		return er;

	}

	public String getEmpAuthorities(String empId) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("empId", empId);

		List<Role> list = roleService.queryEmployeeRole(map);

		return AuthorityHelper.unifyAuthorities(list);
	}

	@Override
	@Transactional
	public EmployeeReturn addEmployee(HttpServletRequest request, EmployeeParams params) {
		EmployeeReturn er = new EmployeeReturn();
		Message message = new Message();
		try {
			if (params == null || params.getEmp() == null || params.getEmp().getIdnumber() == null
					|| params.getEmp().getIdnumber().length() < 18 || params.getEmp().getMobile() == null
					|| params.getEmp().getMobile().length() < 11) {
				message.setCode(SystemEnum.PARAMS_ERROR.value());
				message.setMessage("参数接收异常");
				er.setMessage(message);
				return er;
			}
			
			if(!Validator.isMobile(params.getEmp().getMobile())){
				message.setCode(SystemEnum.PARAMS_ERROR.value());
				message.setMessage("手机号格式错误");
				er.setMessage(message);
				return er;
			}
			Employee selectemp = new Employee();
			selectemp.setLoginName(params.getEmp().getLoginName());
			Employee res1 = employeeService.queryEmployeeByParams(selectemp);
			if (res1 != null) {
				er.setMessage(new Message(SystemEnum.EMP_LOGINNAME_EXISTS.value(), "员工用户名已存在"));
				return er;
			}

			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			selectemp.setLoginName(null);
			selectemp.setMobile(cipher.encrypt(params.getEmp().getMobile()));
			Employee res2 = employeeService.queryEmployeeByParams(selectemp);
			if (res2 != null) {
				er.setMessage(new Message(SystemEnum.EMP_MOBILE_EXISTS.value(), "手机号已存在"));
				return er;
			}

			String salt = Password.getSalt(null);
			String defaultPassword = "111111";
			String password = Password.getPassphrase(salt, defaultPassword);
			params.getEmp().setRegisterDate(new Date());
			params.getEmp().setLastLoginDate(new Date());
			params.getEmp().setSalt(salt);
			params.getEmp().setPassphrase(password);
			params.getEmp().setEnabled(true);
			params.getEmp().setId(GetUUID.getUniqueKey());
			params.getEmp().setIdnumber(cipher.encrypt(params.getEmp().getIdnumber()));
			params.getEmp().setMobile(cipher.encrypt(params.getEmp().getMobile()));
			int count = employeeService.addEmployee(params.getEmp());
			if (count > 0) {
				er.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "添加成功"));
				return er;
			} else {
				er.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "添加失败"));
				return er;
			}

		} catch (GeneralSecurityException | RuntimeException e) {
			logger.info("---------添加员工异常------");
			logger.info(e, e.fillInStackTrace());
			message.setCode(SystemEnum.UNKNOW_EXCEPTION.value());
			message.setMessage("添加员工未知异常，请联系维护人员");
			er.setMessage(message);
			return er;
		}
	}

	@Override
	@Transactional
	public EmployeeReturn empModifyInfo(HttpServletRequest request, EmployeeParams params) {
		EmployeeReturn er = new EmployeeReturn();
		try{
			if (params == null || params.getEmp() == null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数接收异常");

			Employee res=null;
			Employee emp = params.getEmp();
			if (emp.getId() == null && emp.getMobile() == null && emp.getLoginName() == null
					&& emp.getPassphrase() != null) {
				logger.info("修改用户登录密码");
				Employee loginEmp = (Employee) request.getSession().getAttribute("zycfLoginEmp");
				if(loginEmp==null)
					throw new UException(SystemEnum.USER_NOLOGIN,"没有登录");
				
				emp.setId(loginEmp.getId());
				
				res = employeeService.queryEmployeeByParams(emp);
				String passphrase = Password.getPassphrase(res.getSalt(), emp.getPassphrase());
				if(!res.getPassphrase().equals(passphrase))
					throw new UException(SystemEnum.PARAMS_ERROR,"原密码输入错误");
				
			}
			TextCipher cipher = DESTextCipher.getDesTextCipher();
			if (emp.getNewPassWord() != null && !"".equals(emp.getNewPassWord())) {
				emp.setSalt(Password.getSalt(null));
				emp.setPassphrase(Password.getPassphrase(emp.getSalt(), emp.getNewPassWord()));
			}
		
			
			try {
				if (emp.getIdnumber() != null && !"".equals(emp.getIdnumber()))
					emp.setIdnumber(cipher.encrypt(emp.getIdnumber()));
				if (emp.getMobile() != null && !"".equals(emp.getMobile()))
					emp.setMobile(cipher.encrypt(emp.getMobile()));
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
				logger.info("数据加密异常");
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据加密异常");
			}
			
			employeeService.modifyEmployeeByParams(emp);
			er.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "更新成功"));

		} catch (UException e) {
			logger.info("员工信息修改异常");
			e.printStackTrace();
			er.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return er;
	}

	@Override
	public EmployeeReturn getEmpList(HttpServletRequest request, Page<Employee> page) {
		EmployeeReturn returnData = new EmployeeReturn();

		TextCipher decipher = DESTextCipher.getDesTextCipher();
		try {
			String orgId = (String) page.getParams().get("orgId");
			Organization organization = new Organization();
			List<Employee> list = new ArrayList<Employee>();
			if (orgId==null||"".equals(orgId)) {
				list = employeeService.queryAllEmployees(page);
			} else {
				organization.setId(orgId);
				Organization queryOrganization = organizationService.queryOrganizationByParams(organization);
				List<Employee> result = new ArrayList<Employee>();
				List<Employee> re = getRecursion(result, queryOrganization);

				int end = page.getPageNo() * page.getPageSize();
				int start = page.getPageNo() * page.getPageSize() - page.getPageSize();

				// 计算分页页数与总记录数
				page.setTotalRecord(re.size());
				page.setTotalPage(re.size() % page.getPageSize() == 0 ? re.size() / page.getPageSize()
						: re.size() / page.getPageSize() + 1);

				for (int i = 0; i < re.size(); i++) {
					if (start <= i && i < end) {
						list.add(re.get(i));
					}
				}

			}
			List<Role> roles = roleService.queryAllRoles();
			returnData.setRoles(roles);
			
			for (Employee emp : list) {
				emp.setIdnumber(MStringUtils.decrypt(decipher.decrypt(emp.getIdnumber()), 7, 15, '*'));
				emp.setMobile(MStringUtils.decrypt(decipher.decrypt(emp.getMobile()), 4, 8, '*'));
				emp.setLastDate(FormatUtils.millisDateFormat(emp.getLastLoginDate()));
				emp.setRegDate(FormatUtils.millisDateFormat(emp.getRegisterDate()));

			}
			page.setResults(list);
			returnData.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "操作成功"));
			returnData.setPage(page);
		} catch (Exception e) {
			e.printStackTrace();
			returnData.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "操作失败"));
		}
		return returnData;
	}

	List<Employee> getRecursion(List<Employee> result, Organization organization) {

		Page<Employee> page = new Page<Employee>();
		page.setPageNo(1);
		page.setPageSize(Integer.MAX_VALUE);
		page.getParams().put("orgId", organization.getId());
		List<Employee> list = employeeService.queryAllEmployees(page);
		if (list.size() != 0)
			result.addAll(list);

		if (organization.getChildren().size() != 0) {
			for (Organization org : organization.getChildren()) {
				getRecursion(result, org);
			}
		}
		return result;
	}

	@Override
	@Transactional
	public EmployeeReturn dispatchEmpRole(EmployeeParams params) {
		EmployeeReturn er = new EmployeeReturn();
		if (params == null || params.getEmp() == null) {
			er.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "参数接收异常"));
			return er;
		}

		String empid = params.getEmp().getId();
		if (empid == null || "".equals(empid)) {
			er.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "用户不存在"));
			return er;
		}
		
		EmployeeRole empRole = new EmployeeRole();
		empRole.setEmpId(empid);
		int result = 0;
		List<String> roleIds = params.getRoleIds();
		if(roleIds == null || roleIds.size()==0)
			employeeRoleService.deleteEmpRoleByParams(empRole);
		else{
			employeeRoleService.deleteEmpRoleByParams(empRole);
			for (String roleId : roleIds) {
				empRole.setRoleId(roleId);
				EmployeeRole queryEmpRole = employeeRoleService.queryEmployeeRoleByParams(empRole);
				if (queryEmpRole == null) {
					result = employeeRoleService.addEmployeeRole(empRole);
				}
			}
		}
		if(params.getEmp().getOrgId()!=null){
			Employee emp = new Employee();
			emp.setOrgId(params.getEmp().getOrgId());
			emp.setId(empid);
			employeeService.modifyEmployeeByParams(emp);
		}
		
		if (result > 0) {
			er.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "权限分配或隶属机构修改成功"));
			return er;
		} else {
			er.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "隶属机构修改成功"));
			return er;
		}
	}

	@Override
	public EmployeeReturn isLogin(HttpServletRequest request) {
		EmployeeReturn er=new EmployeeReturn();
		try{
			Employee emp=(Employee)request.getSession().getAttribute("zycfLoginEmp");
			if(emp==null)
				throw new UException(SystemEnum.USER_NOLOGIN,"没有登录");
			
			er.setEmp(emp);
			er.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"用户已登录"));
			
		}catch(UException e){
			er.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		
		return er;
	}

}
