package com.zhongyang.java.zyfyback.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.system.authority.Authorities;
import com.zhongyang.java.system.authority.FireAuthority;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.DESTextCipher;
import com.zhongyang.java.system.uitl.DateUtil;
import com.zhongyang.java.zyfyback.biz.StatisticBiz;
import com.zhongyang.java.zyfyback.params.ManagerParams;
import com.zhongyang.java.zyfyback.params.StaticParams;
import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.pojo.Organization;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.returndata.InviteStatisticVo;
import com.zhongyang.java.zyfyback.returndata.MarkStatisticVo;
import com.zhongyang.java.zyfyback.returndata.ReturnManagerData;
import com.zhongyang.java.zyfyback.returndata.StaticReturn;
import com.zhongyang.java.zyfyback.service.OrganizationService;
import com.zhongyang.java.zyfyback.service.UserService;
@CrossOrigin
@Controller
public class StatisticController extends BaseController {
	
	@Autowired
	private StatisticBiz statisticBiz;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserService userService;

	//查询
	@FireAuthority(authorities=Authorities.MARKSTATIC)
	@RequestMapping(value="/back/statistic/queryStatisticByOrg",method=RequestMethod.POST)//已修改完成
	public @ResponseBody StaticReturn queryStatisticByOrg(@RequestBody StaticParams staticParams,HttpServletRequest request){
		
		return statisticBiz.getMarkStatisticByOrg(request,staticParams);
	}
	
	//投资明细（根据参数区分查询的是 含员工还是邀请人）
	@FireAuthority(authorities=Authorities.MARKSTATIC)
	@RequestMapping(value="/back/statistic/queryUserStatisticDetail",method=RequestMethod.POST)
	public @ResponseBody StaticReturn queryUserStatisticDetail(@RequestBody StaticParams staticParams){			
		return statisticBiz.getAllInviteUser(staticParams);			
	}
	
	
	
	//导出excel
	@FireAuthority(authorities=Authorities.MARKSTATIC)
	@RequestMapping("/back/statistic/uploadInviteExcel")
	@ResponseBody
	public ResponseEntity<byte[]> getMarkStatisticData(HttpServletRequest request) {
		try {
			Employee employee = (Employee)request.getSession().getAttribute("zycfLoginEmp");
			if(employee==null){
				return null;
			}
			Page<MarkStatisticVo> page = new Page<MarkStatisticVo>();
			page.setPageNo(1);
			page.setPageSize(Integer.MAX_VALUE);
			Organization organization = new Organization();
			List<String> result = new ArrayList<String>();
			Map<String, Object> map = new HashMap<String, Object>();
			if (employee.getLoginName() != null && "wangjx".equals(employee.getLoginName())) {
				if (request.getParameter("organizationId") != null
						&& !"".equals(request.getParameter("organizationId").toString())) {
					organization.setId(request.getParameter("organizationId").toString());
					List<String> list = getRecursion(result, organization);
					map.put("list", list);
				} else {
					organization.setLevel(0);
					List<Organization> orgList = organizationService.queryAllOrganizations(organization);
					List<String> list = getRecursion(result, orgList.get(0));
					map.put("list", list);
				}
			} else {
				if (employee.getOrgId() != null && !"".equals(employee.getOrgId())) {
					organization.setId(employee.getOrgId());
					List<String> list1 = getRecursion(result, organization);
					if (request.getParameter("organizationId") != null
							&& !"".equals(request.getParameter("organizationId").toString()) && list1.contains(request.getParameter("organizationId"))) {
						organization.setId(request.getParameter("organizationId").toString());
						List<String> results = new ArrayList<String>();
						List<String> list = getRecursion(results, organization);
						map.put("list", list);
					}else{
						map.put("list", list1);
					}
				} else {
					organization.setLevel(0);
					List<Organization> orgList = organizationService.queryAllOrganizations(organization);
					List<String> list = getRecursion(result, orgList.get(0));
					if (request.getParameter("organizationId") != null
							&& !"".equals(request.getParameter("organizationId").toString()) && list.contains(request.getParameter("organizationId"))) {
						organization.setId(request.getParameter("organizationId").toString());
						organization.setLevel(null);
						List<String> results = new ArrayList<String>();
						List<String> list1 = getRecursion(results, organization);
						map.put("list", list1);
					}else{
						map.put("list", list);
					}
					
				}
			}
			
			
			if (request.getParameter("startTime") != null && !"".equals(request.getParameter("startTime"))) {
				map.put("startTime", request.getParameter("startTime"));
			} else {
				map.put("startTime", DateUtil.getFirstDayOfMonth());
			}
			if (request.getParameter("endTime") != null && !"".equals(request.getParameter("endTime"))) {
				map.put("endTime", request.getParameter("endTime"));
			} else {
				map.put("endTime", DateUtil.getLastDayOfMonth());
			}

			page.setParams(map);
			return statisticBiz.exportMarkStatisticData(request, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	List<String> getRecursion(List<String> result, Organization organization) {
		try {
			organization = organizationService.queryOrganizationByParams(organization);
			result.add(organization.getId());
			if (organization.getChildren().size() != 0) {
				for (Organization org : organization.getChildren()) {
					getRecursion(result, org);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	//邀请明细导出
	@FireAuthority(authorities=Authorities.MARKSTATIC)
	@RequestMapping("/back/statistic/uploadUserInviteDetail")
	@ResponseBody
	public ResponseEntity<byte[]> getUserInviteDetail(HttpServletRequest request) {
		try {
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			Page<InviteStatisticVo> page = new Page<InviteStatisticVo>();
			page.setPageNo(1);
			page.setPageSize(Integer.MAX_VALUE);

			Map<String, Object> map = new HashMap<String, Object>();
			if (request.getParameter("mobile") != null && !"".equals(request.getParameter("mobile"))) {
				String mobile = cipher.encrypt(request.getParameter("mobile").toString());
				User user=new User();
				user.setMobile(mobile);
				user=userService.queryUserByParams(user);
				if (request.getParameter("type") != null && "INVITE".equals(request.getParameter("type"))) {
					map.put("referralId", user.getId());
				} else {
					map.put("id", user.getId());
				}
			}
			if (request.getParameter("startTime") != null && !"".equals(request.getParameter("startTime"))) {
				map.put("startTime", request.getParameter("startTime"));
			} else {
				map.put("startTime", DateUtil.getFirstDayOfMonth());
			}
			if (request.getParameter("endTime") != null && !"".equals(request.getParameter("endTime"))) {
				map.put("endTime", request.getParameter("endTime"));
			} else {
				map.put("endTime", DateUtil.getLastDayOfMonth());
			}
			page.setParams(map);
			return statisticBiz.exportInviteUserData(request, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/back/statistic/queryManagerData",method=RequestMethod.POST)
	public @ResponseBody ReturnManagerData queryManagerData(@RequestBody ManagerParams params){
		return statisticBiz.queryManagerData(params.getDate());
	}
}
