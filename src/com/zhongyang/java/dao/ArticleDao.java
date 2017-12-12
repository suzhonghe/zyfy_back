package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Article;
import com.zhongyang.java.zyfyback.returndata.ArticleVO;

/**
 * 
* @Title: ArticleDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 
* @author 苏忠贺   
* @date 2016年3月3日 下午1:34:23 
* @version V1.0
 */
public interface ArticleDao {

	public int insertArticle(Article article);
	
	public int updateByParams(Article article);
	
	public void deleteByParams(Article article);
	
	public List<Article> selectByParams(Page<ArticleVO> page);
	
	public Article getArticleById(String id);
	//调整文章顺序之前先删除原有序号
	public void updateAllArtIndex(String columnId);
	
	public int batchUpdate(List<Article>list);
	
	public int addIndex(Article article);
}
