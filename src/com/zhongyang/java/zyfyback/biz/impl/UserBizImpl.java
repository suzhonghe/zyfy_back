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

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.LoanStatus;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.DESTextCipher;
import com.zhongyang.java.system.uitl.FormatUtils;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.system.uitl.ShortMessage;
import com.zhongyang.java.system.uitl.Validator;
import com.zhongyang.java.zyfyback.biz.UserBiz;
import com.zhongyang.java.zyfyback.params.UserParams;
import com.zhongyang.java.zyfyback.pojo.Loan;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.returndata.UserDetail;
import com.zhongyang.java.zyfyback.returndata.UserReturn;
import com.zhongyang.java.zyfyback.service.LoanService;
import com.zhongyang.java.zyfyback.service.UserService;

/**
 *@package com.zhongyang.java.zyfyback.biz.impl
 *@filename UserBizImpl.java
 *@date 20172017年6月28日上午10:15:06
 *@author suzh
 */
@Service
public class UserBizImpl implements UserBiz {

	private static Logger logger=Logger.getLogger(UserBizImpl.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoanService loanService;
	
	@Override
	public UserReturn searchUserByParams(UserParams params) {
		UserReturn userRes=new UserReturn();
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		Message message=new Message();
		try {
			if(params==null){
				message.setCode(SystemEnum.PARAMS_ERROR.value());
				message.setMessage("未接收到请求参数");
				userRes.setMessage(message);//4001
				return userRes;
			}
			if(params.getUser()!=null&&params.getUser().getLoginName()!=null){
				if(Validator.isMobile(params.getUser().getLoginName())){
					params.getUser().setMobile(cipher.encrypt(params.getUser().getLoginName()));
					params.getUser().setLoginName(null);
				}
			}
			
			User res = userService.queryUserByParams(params.getUser());
			System.out.println(res);
			if(res!=null){
				if(res.getMobile()!=null)
					res.setMobile(cipher.decrypt(res.getMobile()));
				if(res.getIdCode()!=null)
				 res.setIdCode(cipher.decrypt(res.getIdCode()));
			}
			
			userRes.setUser(res);
			message.setCode(SystemEnum.OPRARION_SUCCESS.value());//1000
			message.setMessage("查询成功");
			userRes.setMessage(message);
			
		} catch (GeneralSecurityException e) {
			message.setCode(SystemEnum.DATA_SECURITY_EXCEPTION.value());//9002
			message.setMessage("数据加密异常");
			userRes.setMessage(message);
		}
		return userRes;
	}


	@Override
	public UserReturn searchUserList(UserParams params) {
		UserReturn userReturn=new UserReturn();
		try{
			if(params==null||params.getPage()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数传递错误");
			String strStartTime = params.getPage().getStrStartTime();
			if(strStartTime!=null&&!"".equals(strStartTime))
				params.getPage().setStartTime(FormatUtils.millisFormat(strStartTime+" 00:00:00"));
			
			String strEndTime = params.getPage().getStrEndTime();
			if(strEndTime!=null&&!"".equals(strEndTime))
				params.getPage().setEndTime(FormatUtils.millisFormat(strEndTime+" 23:59:59"));
			
			DESTextCipher cipher=DESTextCipher.getDesTextCipher();
			if(params.getPage().getParams().containsKey("condition")){
				String condition = params.getPage().getParams().get("condition").toString();
				if(Validator.isMobile(condition))
					try {
						params.getPage().getParams().put("mobile", cipher.encrypt(condition));
					} catch (GeneralSecurityException e) {
						e.printStackTrace();
						logger.info("解密异常");
						logger.info(e.fillInStackTrace());
						throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"手机号加密异常");
					}
				else
					params.getPage().getParams().put("userName", condition);
			}
			
			List<User> res = userService.queryUserByPage(params.getPage());
			
			Date date=new Date();
			long nowTime = date.getTime(); 
			for (User user : res) {
				user.setStrRegisterDate(FormatUtils.millisDateFormat(user.getRegisterDate()));
				user.setStrLastLoginDate(FormatUtils.millisDateFormat(user.getLastLoginDate()));
				
				if(user.getAllowTime()!=null){
					long allowTime = user.getAllowTime().getTime();
					if(allowTime-nowTime<0)
						user.setIsLock(false);
					else
						user.setIsLock(true);
				}else
					user.setIsLock(false);
				
				try {
					String mobile = cipher.decrypt(user.getMobile());
					user.setMobile(mobile.substring(0, 3)+"****"+mobile.substring(mobile.length()-4, mobile.length()));
				
					String refMobile = cipher.decrypt(user.getRefMobile());
					user.setRefMobile(refMobile.substring(0, 3)+"****"+refMobile.substring(refMobile.length()-4, refMobile.length()));
					
					if(user.getIdCode()!=null)
						user.setIdCode(cipher.decrypt(user.getIdCode()));
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					logger.info("解密异常");
					logger.info(e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"手机号解密异常");
				}
				
			}
			params.getPage().setResults(res);
			userReturn.setPage(params.getPage());
			userReturn.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"用户列表查询成功"));
			
		}catch(UException e){
			logger.info("用户列表查询失败");
			logger.info(e.fillInStackTrace());
			userReturn.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return userReturn;
	}

public static void main(String[] args) throws GeneralSecurityException {
	DESTextCipher cipher=DESTextCipher.getDesTextCipher();
	System.out.println(cipher.encrypt("13971700365"));
}

	@Override
	public UserReturn modifyUser(UserParams params) {
		UserReturn ur=new UserReturn();
		try{
			if(params==null||params.getUser()==null||params.getUser().getId()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数错误");
			if(params.getUser().getUserType()!=null&&params.getUser().getUserType()==1){
				Loan loan=new Loan();
				loan.setLoanUserId(params.getUser().getId());
				loan.setStatus(LoanStatus.SETTLED);
				List<Loan> res = loanService.queryLoanByParams(loan);
				if(res!=null&&res.size()>0)
					throw new UException(SystemEnum.PARAMS_ERROR,"您有借款没有还清，不能变更为投资人");
			}	
			params.getUser().setLastModifyDate(new Date());
			userService.modifyUserByParams(params.getUser());
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"用户编辑成功"));
		}catch(UException e){
			logger.info("用户编辑失败");
			logger.info(e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}
	
	@Override
	public UserReturn modifyReferral(String userMobile, String refMobile) {
		UserReturn ur=new UserReturn();
		try{
			if(!Validator.isMobile(userMobile))
				throw new UException(SystemEnum.PARAMS_ERROR,"客户手机号格式错误");
			
			if(!Validator.isMobile(refMobile))
				throw new UException(SystemEnum.PARAMS_ERROR,"推荐人手机号格式错误");
			User user=new User();
			User ref=new User();
			DESTextCipher cipher=DESTextCipher.getDesTextCipher();
			try {
				ref.setMobile(cipher.encrypt(refMobile));
				user.setMobile(cipher.encrypt(userMobile));
			} catch (GeneralSecurityException e) {
				logger.info("手机号加密异常");
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"手机号加密异常");
			}
			User queryUser = userService.queryUserByParams(user);
			User refUser = userService.queryUserByParams(ref);
			
			if(queryUser==null)
				throw new UException(SystemEnum.USER_NOT_EXISTS,"客户不存在");
			if(refUser==null)
				throw new UException(SystemEnum.USER_NOT_EXISTS,"推荐人不存在");
			
			User updateUser=new User();
			updateUser.setId(queryUser.getId());
			updateUser.setReferralId(refUser.getId());
			userService.modifyUserByParams(updateUser);
			
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"变更成功"));
		}catch(UException e){
			logger.info("单个客户推荐人变更失败");
			logger.info(e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}


	@Override
	public UserReturn batchModifyReferral(String oriMobile, String nowMobile) {
		UserReturn ur=new UserReturn();
		try{
			if(!Validator.isMobile(oriMobile))
				throw new UException(SystemEnum.PARAMS_ERROR,"原推荐人手机号格式错误");
			if(!Validator.isMobile(nowMobile))
				throw new UException(SystemEnum.PARAMS_ERROR,"现推荐人手机号格式错误");
			User ori=new User();
			User ref=new User();
			DESTextCipher cipher=DESTextCipher.getDesTextCipher();
			try {
				ref.setMobile(cipher.encrypt(nowMobile));
				ori.setMobile(cipher.encrypt(oriMobile));
			} catch (GeneralSecurityException e) {
				logger.info("手机号加密异常");
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"手机号加密异常");
			}
			User oriUser = userService.queryUserByParams(ori);
			User refUser = userService.queryUserByParams(ref);
			
			if(oriUser==null)
				throw new UException(SystemEnum.USER_NOT_EXISTS,"原推荐人不存在");
			if(refUser==null)
				throw new UException(SystemEnum.USER_NOT_EXISTS,"现推荐人不存在");
			
			User updateUser=new User();
			updateUser.setReferralId(refUser.getId());
			updateUser.setOriReferralId(oriUser.getId());
			userService.modifyUserByParams(updateUser);
			
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"变更成功"));
		}catch(UException e){
			logger.info("批量客户推荐人变更失败");
			logger.info(e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}


	@Override
	public UserReturn searchUserDetail(User user) {
		UserReturn ur=new UserReturn();
		try{
			
			if(user==null||user.getId()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数错误");

			UserDetail userDetail = userService.queryUserDetailByParams(user);
			
			DESTextCipher cipher=DESTextCipher.getDesTextCipher();
			try {
				if(userDetail!=null){
					if(userDetail.getCardNo()!=null)
						userDetail.setCardNo(cipher.decrypt(userDetail.getCardNo()));
					
					if(userDetail.getMobile()!=null)
						userDetail.setMobile(cipher.decrypt(userDetail.getMobile()));
					
					if(userDetail.getRefferral()!=null)
						userDetail.setRefferral(cipher.decrypt(userDetail.getRefferral()));
				
					if(userDetail.getIdCode()!=null)
						userDetail.setIdCode(cipher.decrypt(userDetail.getIdCode()));
				}
				
				
			} catch (GeneralSecurityException e) {
				logger.info("数据解密异常");
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据解密异常");
			}
			ur.setUserDetail(userDetail);
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
		}catch(UException e){
			logger.info("客户详情查询异常");
			logger.info(e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		
		return ur;
	}


	@Override
	public UserReturn getUserMobiles(HttpServletRequest req, UserParams params) {
		UserReturn ur=new UserReturn();
		
		try {
			if(params==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数接收异常");
			
			List<String> mobiles = new ArrayList<String>();
			Map<String,Object>map=new HashMap<String,Object>();
			
			map.put("startTime", params.getStartTime());
			map.put("endTime", params.getEndTime());
			
			List<User> userMobiles = userService.getUserMobiles(map);
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
            for(int i = 0;i< userMobiles.size();i++){
            	String mobile=null;
				try {
					mobile = cipher.decrypt(userMobiles.get(i).getMobile());
					mobiles.add(mobile);
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据解密异常");
				}
                
            }
            
            ur.setMobiles(mobiles);
            ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
			
		} catch (UException e) {
			e.printStackTrace();
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}


	@Override
	public UserReturn queryBirthDateUsers(UserParams params) {
		UserReturn ur=new UserReturn();
		List<String> mobiles=new ArrayList<String>();
		Page<User>page=new Page<User>();
		page.setPageSize(Integer.MAX_VALUE);
		try {
			if(params==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数接收异常");
			
			if("".equals(params.getStartTime()))
				page.getParams().put("startTime","01-01");
			else
				page.getParams().put("startTime", params.getStartTime().substring(5, 10));
			
			if(params.getEndTime()!=null&&!"".equals(params.getEndTime()))
				page.getParams().put("endTime", params.getEndTime().substring(5, 10));
			
			
			List<User> users = userService.queryUserBirthdayByPage(page);
			
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			for (User user : users) {
				try {
					String mobile = cipher.decrypt(user.getMobile());
					mobiles.add(mobile);
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据解密异常");
				}
				
			}
			ur.setMobiles(mobiles);
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
		} catch (UException e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}
	@Override
	public UserReturn queryInvestUsers(UserParams params) {
		UserReturn ur=new UserReturn();
		List<String> mobiles=new ArrayList<String>();
		Page<User>page=new Page<User>();
		page.setPageSize(Integer.MAX_VALUE);
		try {
			if(params==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数接收异常");
			
			if("".equals(params.getStartTime())){
				page.getParams().put("startTime","1970-01-01");
			}else{
				page.getParams().put("startTime", params.getStartTime());
			}
			if(params.getEndTime()!=null&&!"".equals(params.getEndTime())){
				page.getParams().put("endTime", params.getEndTime());
			}
			List<User> users = userService.queryInvestUsers(page);			
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			for (User user : users) {
				try {
					String mobile = cipher.decrypt(user.getMobile());
					mobiles.add(mobile);
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据加密异常");
				}
				
			}
			ur.setMobiles(mobiles);
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
		} catch (UException e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}


	@Override
	public UserReturn sensMessageToUser(UserParams params) {
		UserReturn ur=new UserReturn();
		try{
			if(params==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数接收异常");
				
			Map<String,Object> mobileAndMsg = new HashMap<>();
			if("".equals(params))
				throw new UException(SystemEnum.PARAMS_ERROR,"手机号不能为空");
			
			mobileAndMsg.put("mobiles", params.getMobiles());
			mobileAndMsg.put("info", params.getMessage());
			if(!"".equals(params.getSendTime()) && params.getSendTime() !=null)
				mobileAndMsg.put("stime", params.getSendTime());
			
			String codeMsg=ShortMessage.getShortMessage().sendToUserMsg(mobileAndMsg);
			
			if(codeMsg.length()==18)
				ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"发送成功"));
		}catch(UException e){
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}
}
