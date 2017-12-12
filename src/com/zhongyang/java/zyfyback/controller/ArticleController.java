package com.zhongyang.java.zyfyback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.system.authority.Authorities;
import com.zhongyang.java.system.authority.FireAuthority;
import com.zhongyang.java.zyfyback.biz.ArticleBiz;
import com.zhongyang.java.zyfyback.params.ArticleParams;
import com.zhongyang.java.zyfyback.returndata.ArticleReturn;

/**
 * 
* @Title: ArticleController.java 
* @Package com.zhongyang.java.controller 
* @Description:CMS文章控制器
* @author 苏忠贺   
* @date 2016年3月3日 下午4:04:24 
* @version V1.0
 */
@Controller
public class ArticleController extends BaseController{
	
	@Autowired
	private ArticleBiz articleBiz;
	
	@FireAuthority(authorities=Authorities.ARTICLEADD)
	@RequestMapping(value="/back/article/addArticle",method=RequestMethod.POST)
	public @ResponseBody ArticleReturn addArticle(@RequestBody ArticleParams params){
		return articleBiz.addArticle(params.getArticle());
	}
	
	@FireAuthority(authorities=Authorities.ARTICLEUPD)
	@RequestMapping(value="/back/article/modifyArticleByParams",method=RequestMethod.POST)
	public @ResponseBody ArticleReturn modifyByParams(@RequestBody ArticleParams params){
		return articleBiz.modifyByParams(params.getArticle());
	}
	
	@FireAuthority(authorities=Authorities.ARTICLEDEL)
	@RequestMapping(value="/back/article/deleteArticleByParams",method=RequestMethod.POST)
	public @ResponseBody ArticleReturn deleteByParams(@RequestBody ArticleParams params){
		return articleBiz.deleteByParams(params.getArticle());
	}
	
	@FireAuthority(authorities=Authorities.ARTICLELIST)
	@RequestMapping(value="/back/article/queryArticleByParams",method=RequestMethod.POST)
	public @ResponseBody ArticleReturn queryByParams(@RequestBody ArticleParams params){
		return articleBiz.queryByParams(params.getPage());
	}
	
	//文章位置移动
	@FireAuthority(authorities=Authorities.ARTICLEUPD)
	@RequestMapping(value="/back/article/posDisplace",method=RequestMethod.POST)
	public @ResponseBody ArticleReturn changePlace(@RequestBody ArticleParams params){
		return articleBiz.changePlace(params.getStr());
	}
	
	@FireAuthority(authorities=Authorities.ARTICLEDETAIL)
	@RequestMapping(value="/back/article/getArticleById",method=RequestMethod.POST)
	@ResponseBody
	public ArticleReturn getArticleById(@RequestBody ArticleParams params){
		return articleBiz.getArticleById(params.getArticle().getId());
	}
	
}
