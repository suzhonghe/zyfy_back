package com.zhongyang.java.bankmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zhongyang.java.bankmanager.entity.UntredtedApplication;

/**
 * 
 *@package com.zhongyang.java.bankmanager.service
 *@filename UntredtedApplicationService.java
 *@date 2017年8月8日下午5:54:46
 *@author suzh
 */
@Service
public interface UntredtedApplicationService {
   
	public int addUntredtedApplication(UntredtedApplication untredtedApplication)throws Exception;
    
	public List<UntredtedApplication> queryUntredtedApplicationByParams(UntredtedApplication untredtedApplication);
	
	public int modifyUntredtedApplicationByParams(UntredtedApplication untredtedApplication)throws Exception;

}
