package com.zhongyang.java.zyfyback.biz;

import com.zhongyang.java.zyfyback.params.ProjectParams;
import com.zhongyang.java.zyfyback.returndata.ProjectReturn;

/**
 * 
* @Title: LoanRequestBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:  项目管理业务处理接口
* @author 苏忠贺   
* @date 2015年12月2日 上午8:52:29 
* @version V1.0
 */
public interface ProjectBiz {
	/**
	 * 
	* @Title: addProject 
	* @Description:添加项目
	* @return Message    返回类型 
	* @throws
	 */
	public ProjectReturn addProject(ProjectParams params);
	
	/**
	 * 
	* @Title: queryUserByLoginName 
	* @Description: 根据登录名查询用户 
	* @return User    返回类型 
	* @throws
	 */
	/*public User queryUserByLoginName(String loginName);*/
	
	/**
	 * 
	* @Title: searchProjectByParams 
	* @Description:根据参数查询项目
	* @return ProjectReturn    返回类型 
	* @throws
	 */
	public ProjectReturn searchProjectByParams(ProjectParams params);
	/**
	 * 
	* @Title: queryAllProjects 
	* @Description:查询所有项目
	* @return ProjectReturn    返回类型 
	* @throws
	 */
	public ProjectReturn queryAllProjects(ProjectParams params);
	/**
	 * 
	* @Title: modifyProject 
	* @Description:编辑项目
	* @return ProjectReturn    返回类型 
	* @throws
	 */
	public ProjectReturn modifyProject(ProjectParams params);

}
