package com.zhongyang.java.zyfyback.biz.impl;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.system.authority.AuthType;
import com.zhongyang.java.system.authority.AuthorityHelper;
import com.zhongyang.java.system.authority.MStringUtils;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.DESTextCipher;
import com.zhongyang.java.system.uitl.FormatUtils;
import com.zhongyang.java.system.uitl.GetUUID;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.system.uitl.TextCipher;
import com.zhongyang.java.zyfyback.biz.RoleBiz;
import com.zhongyang.java.zyfyback.params.RoleParams;
import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.pojo.Role;
import com.zhongyang.java.zyfyback.pojo.SysAuthorities;
import com.zhongyang.java.zyfyback.returndata.EmployeeRoleReturn;
import com.zhongyang.java.zyfyback.returndata.RoleAuthInit;
import com.zhongyang.java.zyfyback.service.EmployeeService;
import com.zhongyang.java.zyfyback.service.RoleService;
import com.zhongyang.java.zyfyback.service.SysAuthoritiesService;

@Service
public class RoleBizImpl implements RoleBiz {

	private static Logger logger = Logger.getLogger(RoleBizImpl.class);

	@Autowired
	private RoleService roleService;

	@Autowired
	private SysAuthoritiesService sysAuthoritiesService;

	@Autowired
	private EmployeeService employeeService;

	@Override
	public RoleAuthInit roleAuthInit() {
		RoleAuthInit res = new RoleAuthInit();
		Message message = new Message();
		List<Role> roles = roleService.queryAllRoles();

		// 默认显示第一个角色
		Map<String, List<SysAuthorities>> auth = this.getRoleAuthorities(roles.get(0));

		// 第一个角色下对应的员工
		List<Employee> emps;
		try {
			emps = this.getRoleEmp(roles.get(0));
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			logger.info("解密异常");
			message.setCode(SystemEnum.DATA_SECURITY_EXCEPTION.value());
			message.setMessage("数据解密异常");
			res.setMessage(message);
			return res;
		}
		res.setAuth(auth);
		res.setRoles(roles);
		res.setEmps(emps);
		message.setCode(SystemEnum.OPRARION_SUCCESS.value());
		message.setMessage("操作成功");
		res.setMessage(message);
		return res;
	}

	/**
	 * 
	 * @Title: getRoleAuthorities @Description:查询角色与其对应的权限 @return
	 *         Map<String,List<SysAuthorities>> 返回类型 @throws
	 */

	public Map<String, List<SysAuthorities>> getRoleAuthorities(Role role) {
		if (role.getId() == null || role.getId() == "") {
			// 如果角色为空则返回全部的权限
			return this.selectAllSysAuthorities();
		}

		// 角色不是空，查询角色所拥有的权限
		role = roleService.queryRoleByParams(role);

		List<SysAuthorities> list = sysAuthoritiesService.queryAllSysAuthorities();

		Map<String, List<SysAuthorities>> map = new HashMap<String, List<SysAuthorities>>();

		for (SysAuthorities auth : list) {

			String cato = AuthType.valueOf(auth.getPriType()).getDescription();

			auth.setCato(cato);
			if (AuthorityHelper.checkAuthority(auth.getPriPindex(), role.getR_priviliges()))
				auth.setFlag(true);

			if (map.containsKey(cato))
				map.get(cato).add(auth);
			else {
				List<SysAuthorities> l = new ArrayList<SysAuthorities>();
				l.add(auth);
				map.put(cato, l);
			}
		}

		return map;
	}

	/**
	 * 
	 * @Title: selectAllSysAuthorities @Description:查询所有的系统权限 @return
	 *         Map<String,List<SysAuthorities>> 返回类型 @throws
	 */
	private Map<String, List<SysAuthorities>> selectAllSysAuthorities() {
		List<SysAuthorities> list = sysAuthoritiesService.queryAllSysAuthorities();

		Map<String, List<SysAuthorities>> map = new HashMap<String, List<SysAuthorities>>();

		for (SysAuthorities auth : list) {
			String cato = AuthType.valueOf(auth.getPriType()).getDescription();// 权限分类描述
			if (map.containsKey(cato))
				map.get(cato).add(auth);
			else {
				List<SysAuthorities> l = new ArrayList<SysAuthorities>();
				l.add(auth);
				map.put(cato, l);
			}
		}

		return map;
	}

	public List<Employee> getRoleEmp(Role role) throws GeneralSecurityException {
		List<Employee> list = employeeService.queryEmployeeByRole(role);
		TextCipher cipher = DESTextCipher.getDesTextCipher();
		for (Employee employee : list) {
			String mobile = cipher.decrypt(employee.getMobile());
			employee.setMobile(mobile);
		}
		return list;
	}

	@Override
	@Transactional
	public RoleAuthInit addRole(RoleParams params) {
		RoleAuthInit rai=new RoleAuthInit();
		Role role = params.getRole();
		if (role.getR_name() == null || role.getR_name().equalsIgnoreCase("")){
			rai.setMessage(new Message(SystemEnum.PARAMS_ERROR.value(), "角色名称不能为空"));
			return rai;
		}
			

		Role resRole = roleService.queryRoleByParams(role);
		if (resRole != null){
			rai.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "角色已存在"));
			return rai;
		}
			

		role.setId(GetUUID.getUniqueKey());
		role.setR_priviliges(AuthorityHelper._RAW);
		int re = roleService.addRole(role);

		if (re == 1)
			rai.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "添加角色成功"));
		else
			rai.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "添加角色失败"));
		
		return rai;

	}

	@Override
	@Transactional
	public RoleAuthInit modifyRoleAuths(RoleParams params) {
		RoleAuthInit rai=new RoleAuthInit();
		Role role = params.getRole();
		role.setR_priviliges(AuthorityHelper.makeAuthority(params.getPriviliges()));

		roleService.modifyRoleByParams(role);
		rai.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "更新成功"));
		return rai;

	}

	@Override
	public RoleAuthInit getRoleAuthsDetails(Role role) {
		RoleAuthInit res = new RoleAuthInit();
		res.setAuth(this.getRoleAuthorities(role));
		try {
			res.setEmps(this.getRoleEmp(role));
			res.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "查询角色详情成功"));
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			res.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "数据解密失败"));
		}
		return res;
	}

	@Override
	public EmployeeRoleReturn getEmployeeRoles(RoleParams params) {
		EmployeeRoleReturn ret = new EmployeeRoleReturn();
		Employee emp = new Employee();
		emp.setId(params.getEmpId());
		Employee employee = employeeService.queryEmployeeByParams(emp);
		TextCipher decipher = DESTextCipher.getDesTextCipher();
		if (employee != null) {
			try {
				employee.setIdnumber(MStringUtils.decrypt(decipher.decrypt(employee.getIdnumber()), 7, 15, '*'));
				employee.setMobile(MStringUtils.decrypt(decipher.decrypt(employee.getMobile()), 4, 8, '@'));

				employee.setLastDate(FormatUtils.millisDateFormat(employee.getLastLoginDate()));
				employee.setRegDate(FormatUtils.millisDateFormat(employee.getRegisterDate()));
			} catch (Exception e) {
				logger.info("-----------------数据查询失败-----------------");
				logger.info(e, e.fillInStackTrace());
				ret.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "操作成功"));
				return ret;
			}
		}
		ret.setEmp(employee);
		ret.setRoles(roleService.queryAllRoles());
		Map<String, String> map = new HashMap<String, String>();
		map.put("empId", employee.getId());
		ret.setEmpRoles(roleService.queryEmployeeRole(map));
		ret.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "操作成功"));
		return ret;
	}
}
