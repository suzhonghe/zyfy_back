package com.zhongyang.java.zyfyback.biz;

import javax.servlet.http.HttpServletRequest;

import com.zhongyang.java.zyfyback.params.ConTractParams;
import com.zhongyang.java.zyfyback.returndata.ConTractReturn;

public interface ContractBiz {

	public ConTractReturn generateContracts(HttpServletRequest request,ConTractParams params);
	public ConTractReturn uncontractLoans(ConTractParams params);
}
