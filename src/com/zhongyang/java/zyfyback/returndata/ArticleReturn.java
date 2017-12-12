package com.zhongyang.java.zyfyback.returndata;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.pojo.Article;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename ArticleReturn.java
 *@date 2017年7月28日下午1:48:06
 *@author suzh
 */
public class ArticleReturn {
	
	private Message message;
	
	private Page<ArticleVO> pageArticleVo;
	
	private Article article;
	
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Page<ArticleVO> getPageArticleVo() {
		return pageArticleVo;
	}

	public void setPageArticleVo(Page<ArticleVO> pageArticleVo) {
		this.pageArticleVo = pageArticleVo;
	}
	
}
