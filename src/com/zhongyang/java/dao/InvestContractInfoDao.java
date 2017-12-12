package com.zhongyang.java.dao;

import com.zhongyang.java.zyfyback.pojo.InvestContractInfo;

public interface InvestContractInfoDao {

    int deleteByPrimaryKey(Integer id);

    int insert(InvestContractInfo record);

    int insertSelective(InvestContractInfo record);
    
    InvestContractInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InvestContractInfo record);

    int updateByPrimaryKey(InvestContractInfo record);
}