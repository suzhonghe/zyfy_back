package com.zhongyang.java.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zhongyang.java.bankmanager.entity.UntredtedApplication;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年1月14日 下午5:00:43
* 类说明
*/
@Service
public interface UntredtedApplicationDao {

	public int insertUntredtedApplication(UntredtedApplication untredtedApplication);

	public List<UntredtedApplication> selectUntredtedApplicationByParams(UntredtedApplication untredtedApplication);
	
	public int updateUntredtedApplicationByParams(UntredtedApplication untredtedApplication);
}
