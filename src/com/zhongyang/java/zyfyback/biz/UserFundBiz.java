package com.zhongyang.java.zyfyback.biz;

import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.returndata.UserFundReturn;

/**
 *@package com.zhongyang.java.zyfyback.biz
 *@filename UserFundBiz.java
 *@date 2017年7月21日上午9:07:09
 *@author suzh
 */
public interface UserFundBiz {

	public UserFundReturn selectUserFundDetail(User user);
}
