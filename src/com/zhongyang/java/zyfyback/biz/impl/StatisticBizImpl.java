package com.zhongyang.java.zyfyback.biz.impl;

import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.DESTextCipher;
import com.zhongyang.java.system.uitl.DateUtil;
import com.zhongyang.java.system.uitl.FormatUtils;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.system.uitl.UploadExcelUtil;
import com.zhongyang.java.zyfyback.biz.StatisticBiz;
import com.zhongyang.java.zyfyback.params.StaticParams;
import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.pojo.Organization;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.returndata.InviteStatisticVo;
import com.zhongyang.java.zyfyback.returndata.ManagerData;
import com.zhongyang.java.zyfyback.returndata.MarkStatisticVo;
import com.zhongyang.java.zyfyback.returndata.ReturnManagerData;
import com.zhongyang.java.zyfyback.returndata.StaticReturn;
import com.zhongyang.java.zyfyback.service.LoanService;
import com.zhongyang.java.zyfyback.service.OrganizationService;
import com.zhongyang.java.zyfyback.service.StatisticService;
import com.zhongyang.java.zyfyback.service.UserService;

@Service
public class StatisticBizImpl implements StatisticBiz {
	
	@Autowired
	private StatisticService statisticService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private LoanService loanService;

	// 查询总额
	@Override
	public StaticReturn getMarkStatisticByOrg(HttpServletRequest request, StaticParams staticParams) {
		StaticReturn sr = new StaticReturn();
		try {
			Employee employee = (Employee) request.getSession().getAttribute("zycfLoginEmp");
			if (employee == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");

			if (staticParams == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数错误");

			Page<MarkStatisticVo> page = staticParams.getMarkPage();

			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			Organization organization = new Organization();
			List<String> result = new ArrayList<String>();
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			System.out.println(System.currentTimeMillis() + "查询开始时间");
			if (page.getParams().get("organizationId") != null && !"".equals(page.getParams().get("organizationId"))) {
				String orgId = (String) page.getParams().get("organizationId");
				if (employee.getOrgId() != null && !"".equals(employee.getOrgId())) {
					Organization org = new Organization();
					org.setId(employee.getOrgId());
					List<String> orgs = new ArrayList<String>();
					orgs = getRecursion(orgs, org);
					boolean flag = false;
					for (String str : orgs) {
						if (str.equals(orgId)) {
							flag = true;
						}
					}
					if (!flag) {
						sr.setMarkStaticVo(page);
						sr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "查询成功"));
						return sr;
					}
				}

			}
			if (employee.getLoginName() != null && "wangjx".equals(employee.getLoginName())) {
				// 王军霞可以查询所有机构的业绩
				if (page.getParams().get("organizationId") != null
						&& !"".equals(page.getParams().get("organizationId").toString())) {
					organization.setId(page.getParams().get("organizationId").toString());
					List<String> list = getRecursion(result, organization);
					map.put("list", list);
				} else {
					organization.setLevel(0);
					List<Organization> orgList = organizationService.queryAllOrganizations(organization);
					List<String> list = getRecursion(result, orgList.get(0));
					map.put("list", list);
				}
			} else {
				// 不是王军霞只能查询本机构及其下属机构的业绩
				if (employee.getOrgId() != null && !"".equals(employee.getOrgId())) {
					organization.setId(employee.getOrgId());
					List<String> list1 = getRecursion(result, organization);
					if (page.getParams().get("organizationId") != null
							&& !"".equals(page.getParams().get("organizationId").toString())
							&& list1.contains(page.getParams().get("organizationId"))) {
						organization.setId(page.getParams().get("organizationId").toString());
						List<String> results = new ArrayList<String>();
						List<String> list = getRecursion(results, organization);
						map.put("list", list);
					} else {
						map.put("list", list1);
					}
				} else {
					organization.setLevel(0);
					List<Organization> orgList = organizationService.queryAllOrganizations(organization);
					List<String> list = getRecursion(result, orgList.get(0));
					if (page.getParams().get("organizationId") != null
							&& !"".equals(page.getParams().get("organizationId").toString())
							&& list.contains(page.getParams().get("organizationId"))) {
						organization.setId(page.getParams().get("organizationId").toString());
						organization.setLevel(null);
						List<String> results = new ArrayList<String>();
						List<String> list1 = getRecursion(results, organization);
						map.put("list", list1);
					} else {
						map.put("list", list);
					}

				}
			}

			if (page.getStrStartTime() != null && !"".equals(page.getStrStartTime())) {
				map.put("startTime", page.getStrStartTime());
			} else {
				map.put("startTime", DateUtil.getFirstDayOfMonth());
			}
			if (page.getStrEndTime() != null && !"".equals(page.getStrEndTime())) {
				map.put("endTime", page.getStrEndTime());
			} else {
				map.put("endTime", DateUtil.getLastDayOfMonth());
			}
			map.put("status", page.getParams().get("status"));
			page.setParams(map);
			params.put("list", map.get("list"));
			params.put("startTime", map.get("startTime"));
			params.put("endTime", map.get("endTime"));

			// 员工邀请投资总计
			List<MarkStatisticVo> list2 = statisticService.getEmployStatistic(params);

			List<MarkStatisticVo> list = statisticService.getMarkStatisticByOrg(page);

			BigDecimal totalAmount = new BigDecimal("0.00");
			BigDecimal totalYearAmount = new BigDecimal("0.00");

			map.put("totalAmount", totalAmount);
			map.put("totalYearAmount", totalYearAmount);
			page.setParams(map);
			for (MarkStatisticVo vo : list) {
				for (MarkStatisticVo v : list2) {
					if (vo.getMobile().equals(v.getMobile())) {
						if (vo.getTotalAmount() == null || vo.getYearAmount() == null) {
							vo.setTotalAmount(new BigDecimal("0.00"));
							vo.setYearAmount(new BigDecimal("0.00"));
						}
						vo.setTotalAmount(vo.getTotalAmount().add(v.getTotalAmount()));
						vo.setYearAmount(vo.getYearAmount().add(v.getYearAmount()));
					}
				}
				try {
					vo.setMobile(cipher.decrypt(vo.getMobile()));
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION, "数据解析异常");
				}
			}
			page.setResults(list);
			sr.setMarkStaticVo(page);
			sr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "查询成功"));
		} catch (UException e) {
			e.printStackTrace();
			sr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return sr;
	}

	// 查询明细
	@Override
	public StaticReturn getAllInviteUser(StaticParams staticParams) {
		StaticReturn sr = new StaticReturn();
		try {
			if (staticParams == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数错误");

			Page<InviteStatisticVo> page = staticParams.getInvitePage();

			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			Map<String, Object> map = new HashMap<String, Object>();
			
			if (page.getStrStartTime() != null && !"".equals(page.getStrStartTime())) {
				map.put("startTime", page.getStrStartTime());
			} else {
				map.put("startTime", DateUtil.getFirstDayOfMonth());
			}
			if (page.getStrEndTime() != null && !"".equals(page.getStrEndTime())) {
				map.put("endTime", page.getStrEndTime());
			} else {
				map.put("endTime", DateUtil.getLastDayOfMonth());
			}
			if (page.getParams().get("mobile") != null && !"".equals(page.getParams().get("mobile"))) {
				String mobile = null;
				try {
					mobile = cipher.encrypt(page.getParams().get("mobile").toString());
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION, "数据加密异常");
				}
				User user = new User();
				user.setMobile(mobile);
				user = userService.queryUserByParams(user);
				if (page.getParams().get("type") != null && "INVITE".equals(page.getParams().get("type"))) {
					map.put("referralId", user.getId());
				} else {
					map.put("id", user.getId());
				}
			} else {
				return null;
			}
			page.setParams(map);
			List<InviteStatisticVo> list = statisticService.getAllInviteUser(page);
			for (InviteStatisticVo vo : list) {
				String mobile = null;
				try {
					mobile = cipher.decrypt(vo.getMobile());
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION, "数据解密异常");
				}
				String stamb = mobile.substring(0, 3) + "***" + mobile.substring(mobile.length() - 4, mobile.length());
				vo.setMobile(stamb);
			}
			page.setResults(list);
			sr.setInviteStatisticVo(page);
			sr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "查询成功"));
		} catch (UException e) {
			e.printStackTrace();
			sr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return sr;
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

	// 明细导出
	@Override
	public ResponseEntity<byte[]> exportInviteUserData(HttpServletRequest request, Page<InviteStatisticVo> page) {
		try {
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			List<String[]> headNames = new ArrayList<String[]>();
			headNames.add(new String[] { "邀请人姓名", "手机号", "投资金额(元)", "投资项目", "项目周期(月)", "项目利率(%)", "投资时间", "投标状态",
					"年化投资金额(元)" });
			List<String[]> fieldNames = new ArrayList<String[]>();
			fieldNames.add(new String[] { "inviteName", "mobile", "investAmount", "title", "months", "rate",
					"submitTime", "status", "yearAmount" });
			List<InviteStatisticVo> list = statisticService.getAllInviteUser(page);
			for (InviteStatisticVo vo : list) {
				if (0 != vo.getAddRate()) {
					String rates = vo.getRate() + "+" + String.valueOf(vo.getAddRate());
					vo.setRate(rates);
				}
				String cc = cipher.decrypt(vo.getMobile());
				String stamb = cc.substring(0, 3) + "***" + cc.substring(cc.length() - 4, cc.length());
				vo.setMobile(stamb);
			}
			String name = "公司员工邀请信息明细";
			ResponseEntity<byte[]> responseEntity;
			responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, list, name);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "员工邀请信息导出失败!");
		}
	}

	// 总额导出
	@Override
	public ResponseEntity<byte[]> exportMarkStatisticData(HttpServletRequest request, Page<MarkStatisticVo> page) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			List<String[]> headNames = new ArrayList<String[]>();
			headNames.add(new String[] { "员工姓名", "手机号", "机构名称", "总投资金额(元)", "总年化投资金额(元)" });
			List<String[]> fieldNames = new ArrayList<String[]>();
			fieldNames.add(new String[] { "staffName", "mobile", "orgName", "totalAmount", "yearAmount" });
			params.put("list", page.getParams().get("list"));
			params.put("startTime", page.getParams().get("startTime"));
			params.put("endTime", page.getParams().get("endTime"));
			List<MarkStatisticVo> list2 = statisticService.getEmployStatistic(params);
			List<MarkStatisticVo> list = statisticService.getMarkStatisticByOrg(page);
			for (MarkStatisticVo vo : list) {
				for (MarkStatisticVo v : list2) {
					if (vo.getMobile().equals(v.getMobile())) {
						if (vo.getTotalAmount() == null || vo.getYearAmount() == null) {
							vo.setTotalAmount(new BigDecimal("0.00"));
							vo.setYearAmount(new BigDecimal("0.00"));
						}
						vo.setTotalAmount(vo.getTotalAmount().add(v.getTotalAmount()));
						vo.setYearAmount(vo.getYearAmount().add(v.getYearAmount()));
					}
				}
				vo.setMobile(cipher.decrypt(vo.getMobile()));
			}
			String name = "公司员工绩效信息统计";
			ResponseEntity<byte[]> responseEntity;
			responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, list, name);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "员工绩效信息导出失败!");
		}
	}

	@Override
	public ReturnManagerData queryManagerData(String date) {
		ReturnManagerData rmd=new ReturnManagerData();
		try{
			
			Calendar cal = Calendar.getInstance();
	        //设置年份
	        cal.set(Calendar.YEAR,Integer.valueOf(date.substring(0, 4)));
	        //设置月份
	        cal.set(Calendar.MONTH, Integer.valueOf(date.substring(date.length()-2, date.length()))-1);
	        //获取某月最大天数
	        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        //设置日历中月份的最大天数
	        cal.set(Calendar.DAY_OF_MONTH, lastDay);
	        
	        cal.set(Calendar.HOUR_OF_DAY,23);
	        cal.set(Calendar.MINUTE,59);
	        cal.set(Calendar.SECOND,59);
	        
	        Date d=cal.getTime();
			
	        ManagerData data = loanService.queryLoanAmount(d);
			ManagerData managerData= data==null?new ManagerData():data;
	
			Integer loanPerson = loanService.queryLoanPerson(d);
			managerData.setLoanPerson(loanPerson);
			managerData.setInPerson(loanService.queryInPerson(d));
			
			Integer allDays = loanService.queryLimitDays(d);
			managerData.setLimitDays(allDays==null?0.0:(new BigDecimal(allDays).divide(new BigDecimal(loanPerson),4,BigDecimal.ROUND_HALF_UP).doubleValue()));
			Double rate = loanService.queryRate(d);
			managerData.setRate(new BigDecimal(rate==null?0:rate).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue());
			managerData.setLegonAmount(managerData.getLoanAmount()==null?new BigDecimal(0):managerData.getLoanAmount().divide(new BigDecimal(loanPerson),6,BigDecimal.ROUND_HALF_UP));
			
			managerData.setLoanAmount(managerData.getLoanAmount()==null?new BigDecimal(0):managerData.getLoanAmount());
			managerData.setFeeAmount(managerData.getFeeAmount()==null?new BigDecimal(0):managerData.getFeeAmount());
			List<ManagerData> dateAmount=new ArrayList<ManagerData>();
			for (int i = 1; i <= 6; i++) {
				//设置年份
	            cal.set(Calendar.YEAR,Integer.valueOf(date.substring(0, 4)));
	            //设置月份
	            cal.set(Calendar.MONTH, Integer.valueOf(date.substring(date.length()-2, date.length()))-1);
	        	cal.add(Calendar.MONTH,i);//日期加i个月
		        //设置日历中月份的最大天数
		        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		        String end=FormatUtils.simpleFormat(cal.getTime());
		        System.out.println(end);
		        //设置日历中月份的最小天数
		        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		        String start=FormatUtils.simpleFormat(cal.getTime());
		        
		        ManagerData res = loanService.queryAmountByDate(d, start, end);
		        res.setMonth(FormatUtils.getMonth(cal.getTime()));
		        dateAmount.add(res);
			}
			rmd.setData(dateAmount);
			
			rmd.setManagerData(managerData);
			rmd.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
		}catch(UException e){
			rmd.setMessage(new Message(SystemEnum.UNKNOW_EXCEPTION.value(),"未知异常"));
		}
		return rmd;
	}
	public static void main(String[] args) {
		String date="2017-09";
		Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,Integer.valueOf(date.substring(0, 4)));
        //设置月份
        cal.set(Calendar.MONTH, Integer.valueOf(date.substring(date.length()-2, date.length()))-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,59);
        
        //设置日历中月份的最大天数
        for (int i = 1; i <= 6; i++) {
        	//设置年份
            cal.set(Calendar.YEAR,Integer.valueOf(date.substring(0, 4)));
            //设置月份
            cal.set(Calendar.MONTH, Integer.valueOf(date.substring(date.length()-2, date.length()))-1);
        	cal.add(Calendar.MONTH,i);//日期加i个月
	        //设置日历中月份的最大天数
	        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	        String end=FormatUtils.simpleFormat(cal.getTime());
	        System.out.println(end);
	        //设置日历中月份的最小天数
	        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
	        String start=FormatUtils.simpleFormat(cal.getTime());
	       
	        System.out.println(start);
	        System.out.println("============");
//	        System.out.println(FormatUtils.getMonth(cal.getTime()));
		}
	}
}
