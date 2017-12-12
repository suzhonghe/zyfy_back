package com.zhongyang.java.zyfyback.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.ArticleDao;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Article;
import com.zhongyang.java.zyfyback.returndata.ArticleVO;
import com.zhongyang.java.zyfyback.service.ArticleService;
/**
 * 
* @Title: ArticleServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description:业务实现
* @author 苏忠贺   
* @date 2016年3月3日 下午2:39:18 
* @version V1.0
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;
	
	@Override
	public int addArticle(Article article) {
		return articleDao.insertArticle(article);
	}

	@Override
	public int modifyByParams(Article article){
		return articleDao.updateByParams(article);
	}

	@Override
	public void deleteByParams(Article article){
		articleDao.deleteByParams(article);
	}

	@Override
	public List<Article> queryByParams(Page<ArticleVO> page){
		return articleDao.selectByParams(page);
	}

	@Override
	public Article getArticleById(String id) {
		return articleDao.getArticleById(id);
	}

	@Override
	public void updateAllArtIndex(String columnId) {
		articleDao.updateAllArtIndex(columnId);
		
	}

	@Override
	public int batchUpdate(List<Article> list) {
		return articleDao.batchUpdate(list);
	}

	@Override
	public int addIndex(Article article) {
		return articleDao.addIndex(article);
	}

}
