package com.zhongyang.java.zyfyback.service;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Project;
import com.zhongyang.java.zyfyback.returndata.ProjectDea;

/**
 * 
* @Title: LoanRequestBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:  项目管理业务接口
* @author 苏忠贺   
* @date 2015年12月2日 上午8:52:29 
* @version V1.0
 */
public interface ProjectService {
	/**
	 * 
	* @Title: addOneProject 
	* @Description:添加一条项目记录 
	* @return int    返回类型 
	* @throws
	 */
	public int addOneProject(Project project);
	/**
	 * 
	* @Title: queryProjectByParams 
	* @Description:根据参数查询项目
	* @return Project    返回类型 
	* @throws
	 */
	public Project queryProjectByParams(Project project);
	/**
	 * 
	* @Title: modifyProject 
	* @Description:根据id修改项目 
	* @return int    返回类型 
	* @throws
	 */
	public void modifyProject(Project project);
	
	/**
	 * 
	* @Title: queryAllProjects 
	* @Description:查询所有项目
	* @return List<Project>    返回类型 
	* @throws
	 */
	public List<ProjectDea> queryAllProjects(Page<ProjectDea> page);
	
	public int failedLoanUpdatePaoject(Project project);
	
}
