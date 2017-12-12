package com.zhongyang.java.dao;
import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Project;
import com.zhongyang.java.zyfyback.returndata.ProjectDea;


/**
 * 
* @Title: LoanRequestDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 项目管理dao接口
* @author 苏忠贺   
* @date 2015年12月1日 下午3:27:25 
* @version V1.0
 */
public interface ProjectDao {
	/**
	 * 
	* @Title: insertOneProject 
	* @Description:插入一条项目记录 
	* @return int    返回类型 
	* @throws
	 */
	public int insertProject(Project project);
	/**
	 * 
	* @Title: selectProjectByParams 
	* @Description:根据参数查询项目 
	* @return Project    返回类型 
	* @throws
	 */
	public Project selectProjectByParams(Project project);
	/**
	 * 
	* @Title: updateProject 
	* @Description:修改项目
	* @return int    返回类型 
	* @throws
	 */
	public int updateProjectByParams(Project project);

	
	/**
	 * 
	* @Title: selectAllProjects 
	* @Description:分页查询所有项目 
	* @return List<Project>    返回类型 
	* @throws
	 */
	public List<ProjectDea> selectAllProjects(Page<ProjectDea> page);
	
	public int failedLoanUpdatePaoject(Project project);
	
}
