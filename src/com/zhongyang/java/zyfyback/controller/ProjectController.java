package com.zhongyang.java.zyfyback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.system.authority.Authorities;
import com.zhongyang.java.system.authority.FireAuthority;
import com.zhongyang.java.zyfyback.biz.ProjectBiz;
import com.zhongyang.java.zyfyback.params.ProjectParams;
import com.zhongyang.java.zyfyback.returndata.ProjectReturn;

/**
 * 
* @Title: LoanRequestController.java 
* @Package com.zhongyang.java.controller 
* @Description: 项目管理控制器 
* @author 苏忠贺   
* @date 2015年12月2日 上午9:15:47 
* @version V1.0
 */
@CrossOrigin
@Controller
public class ProjectController extends BaseController{
	
	@Autowired
	private ProjectBiz projectBiz;
	
	/**
	 * 
	* @Title: projectApply 
	* @Description: 项目申请 
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.PRJADD)
	@RequestMapping(value="/back/project/projectApply", method = RequestMethod.POST)
	public @ResponseBody ProjectReturn projectApply(@RequestBody ProjectParams params){
		return 	projectBiz.addProject(params);
	}
		
	/**
	 * 
	* @Title: searchProjectByParams 
	* @Description:根据参数查询项目
	* @return Project    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.PRJBYID)
	@RequestMapping(value="/back/project/searchProjectByParams", method = RequestMethod.POST)
	public @ResponseBody ProjectReturn searchProjectByParams(@RequestBody ProjectParams params){
		return projectBiz.searchProjectByParams(params);
	}
	/**
	 * 
	* @Title: queryAllProjects 
	* @Description: 查询所有项目 
	* @return ProjectReturn    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.PRJLIST)
	@RequestMapping(value="back/project/queryAllProjects", method = RequestMethod.POST)
	public @ResponseBody ProjectReturn queryAllProjects(@RequestBody ProjectParams params){
		return projectBiz.queryAllProjects(params);
	}
	/**
	 * 
	* @Title: modifyProject 
	* @Description:编辑项目
	* @return ProjectReturn    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.PRJUPD)
	@RequestMapping(value="/back/project/modifyProject", method = RequestMethod.POST)
	public @ResponseBody ProjectReturn modifyProject(@RequestBody ProjectParams params){
		return projectBiz.modifyProject(params);
	}
}
