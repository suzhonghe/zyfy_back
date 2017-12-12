package com.zhongyang.java.zyfyback.biz;

import com.zhongyang.java.zyfyback.pojo.CorporationUser;
import com.zhongyang.java.zyfyback.returndata.CorporationReturn;
/**
 * 
* @Title: CorporationUserBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:企业信息业务处理接口 
* @author 苏忠贺   
* @date 2016年1月25日 下午5:40:07 
* @version V1.0
 */
public interface CorporationUserBiz {
	
    /**
     * 
    * @Title: queryAllCorporationUser 
    * @Description:根据企业类型查询企业名字
    * @return List<CorporationUser>    返回类型 
    * @throws
     */
    public CorporationReturn queryAllCorporationUser(CorporationUser corporationUser);
}
