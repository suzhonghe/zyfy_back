package com.zhongyang.java.system.enumtype;

/**
 * 
 *@package com.zhongyang.java.system.enumtype
 *@filename SystemEnum.java
 *@date 20172017年6月30日下午2:14:49
 *@author suzh
 */
public enum SystemEnum {
	/**
	 * 操作成功
	 */
	OPRARION_SUCCESS(1000),
	/**
	 * 操作失败
	 */
	OPRARION_FAILED(1001),
	
	
	/**
	 * 用户名或密码错误
	 */
	USER_PASSWORD_VAILD_FAILURE(2000),
	/**
	 * 用户不存在  
	 */
	USER_NOT_EXISTS(2001),
	
	/**
	 * 没有登录
	 */
	USER_NOLOGIN(2002),

	/**
	 *用户名不能更改
	 */
	USERNAME_CANT_CHANGE(2003),

	/**
	 * 没有开通第三方账户
	 */
	NOREGISTERED_UMPACCOUNT(2004),
	/**
	 * 实名注册失败
	 */
    REAL_NAME_AUTHENTICATION_FAILED(2005),
    /**
	 * 手机号已经被绑定
	 */
    PHONE_NUMBER_HAS_BEEN_BOUND(2006),
    /**
	 * 登录失败
	 */
	LOGIN_ERROR(2007),
    /**
     * 没有实名认证
     */
    UN_AUTHENTICATION(2008),
    /**
     * 没有权限
     */
    AUTHENTICATION_FAIL(2009),
    /**
     * 账户被锁定
     */
    USER_LOCK(2010),
    /**
     * 禁止员工操作
     */
    EMP_FORBID_OPRATION(2011),
    /**
     * 员工用户名已存在
     */
    EMP_LOGINNAME_EXISTS(2012),
    /**
     * 员工手机号已注册
     */
    EMP_MOBILE_EXISTS(2013),
    /**
     * 短信次数查过规定次数
     */
    MESSAGE_NUM_OUT(3001),
    /**
     * 短信获取超时
     */
    MESSAGE_TIME_OUT(3002),
    /**
     * 短信验证码失效
     */
    MESSAGE_LOSE_EFFICACY(3003),
    /**
     * 短信验证码错误
     */
    MESSAGE_ERROR(3004),
    /**
     * 参数错误
     */
    PARAMS_ERROR(4001),
    /**
     * 连接异常
     */
    NET_CONNET_EXCEPTION(5000),
	/**
	 * 数据库连接失败
	 */
	SERVER_REFUSED(9001),
	/**
	 * 数据加密异常
	 */
	DATA_SECURITY_EXCEPTION(9002),
	/**
	 * 文件读写错误
	 */
	FILE_READ_WRITE_EXCEPTION(9003),
	/**
	 * 数据库异常
	 */
	DATA_REFUSED(9004),
	/**
	 * 存款准备失败
	 */
	PREPARE_DEPOSIT_FAILED(8001),
	/**
	 * 调用第三方失败
	 */
	UMPAT_SIG__FAILED(8002),
	
	/**
	 * 未知异常,请与管理员联系
	 */
	UNKNOW_EXCEPTION(9999);
   	
	private final Integer value;  
	    
	  /**
	 * @param value
	 */
	private SystemEnum(Integer value){  
	      this.value=value;  
	  }  
	    
	  /**
	 * @return
	 */
	public Integer value(){  
	      return value;  
	  }  
}
