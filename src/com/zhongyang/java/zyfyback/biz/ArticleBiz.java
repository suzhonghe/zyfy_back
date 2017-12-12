package com.zhongyang.java.zyfyback.biz;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Article;
import com.zhongyang.java.zyfyback.returndata.ArticleReturn;
import com.zhongyang.java.zyfyback.returndata.ArticleVO;

/**
 * 
* @Title: ArticleBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:文章业务处理接口
* @author 苏忠贺   
* @date 2016年3月3日 下午3:08:54 
* @version V1.0
 */
public interface ArticleBiz {
	
	public ArticleReturn addArticle(Article article);
	
	public ArticleReturn modifyByParams(Article article);
	
	public ArticleReturn deleteByParams(Article article);
	
	public ArticleReturn queryByParams(Page<ArticleVO> page);
	
	/**
	 * 文章位置移动
	 * @param str
	 * @return
	 */
	public ArticleReturn changePlace(String[] str);
	
	public ArticleReturn getArticleById(String id);

}
