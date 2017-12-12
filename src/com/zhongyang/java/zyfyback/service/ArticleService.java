package com.zhongyang.java.zyfyback.service;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Article;
import com.zhongyang.java.zyfyback.returndata.ArticleVO;

/**
 * 
* @Title: ArticleService.java 
* @Package com.zhongyang.java.service 
* @Description:业务接口
* @author 苏忠贺   
* @date 2016年3月3日 下午2:34:25 
* @version V1.0
 */
public interface ArticleService {
	
	public int addArticle(Article article);
	
	public int modifyByParams(Article article);
	
	public void deleteByParams(Article article);
	
	public List<Article> queryByParams(Page<ArticleVO> page);
	
	public Article getArticleById(String id);
	
	//调整文章顺序之前先删除原有序号
	public void updateAllArtIndex(String columnId);
	
	public int batchUpdate(List<Article>list);
	
	public int addIndex(Article article);
}
