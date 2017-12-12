package com.zhongyang.java.zyfyback.service;

import java.util.List;

import com.zhongyang.java.zyfyback.pojo.CorporationUser;
/**
 * 
* @Title: CorporationUserService.java 
* @Package com.zhongyang.java.service 
* @Description:企业信息service接口
* @author 苏忠贺   
* @date 2016年1月25日 下午5:36:31 
* @version V1.0
 */
public interface CorporationUserService {
	
    /**
     * 
    * @Title: queryAllCorporationUser 
    * @Description:根据条件查询企业信息
    * @return List<CorporationUser>    返回类型 
    * @throws
     */
    public List<CorporationUser> queryAllCorporationUser(CorporationUser corporationUser);
}
