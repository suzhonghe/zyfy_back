package com.zhongyang.java.zyfyback.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.ProjectDao;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Project;
import com.zhongyang.java.zyfyback.returndata.ProjectDea;
import com.zhongyang.java.zyfyback.service.ProjectService;

/**
 * 
* @Title: LoanRequestServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description: 项目管理实现类 
* @author 苏忠贺   
* @date 2015年12月2日 上午9:03:27 
* @version V1.0
 */
@Service
public class ProjectServiceImpl implements ProjectService{
	@Autowired
	private ProjectDao projectDao;
	
	@Override
	public int addOneProject(Project project){
		return projectDao.insertProject(project);
	}
	
	@Override
	public Project queryProjectByParams(Project project){
		return projectDao.selectProjectByParams(project);
	}
	
	@Override
	public void modifyProject(Project project){
		projectDao.updateProjectByParams(project);
	}

	@Override
	public List<ProjectDea> queryAllProjects(Page<ProjectDea> page){
		return projectDao.selectAllProjects(page);
	}

	@Override
	public int failedLoanUpdatePaoject(Project project) {
		return projectDao.failedLoanUpdatePaoject(project);
	}
	
	
}
