package com.zhongyang.java.zyfyback.params;

import java.io.Serializable;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Article;
import com.zhongyang.java.zyfyback.returndata.ArticleVO;

/**
 *@package com.zhongyang.java.zyfyback.params
 *@filename ArticleParams.java
 *@date 2017年7月28日下午1:59:17
 *@author suzh
 */
public class ArticleParams implements Serializable{
	
	private Article article;

	private Page<ArticleVO> page;
	
	private String[] str;
		
	public String[] getStr() {
		return str;
	}

	public void setStr(String[] str) {
		this.str = str;
	}

	public Page<ArticleVO> getPage() {
		return page;
	}

	public void setPage(Page<ArticleVO> page) {
		this.page = page;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
 
}
