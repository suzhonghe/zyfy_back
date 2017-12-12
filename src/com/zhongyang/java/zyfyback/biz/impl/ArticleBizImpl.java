package com.zhongyang.java.zyfyback.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.biz.ArticleBiz;
import com.zhongyang.java.zyfyback.pojo.Article;
import com.zhongyang.java.zyfyback.returndata.ArticleReturn;
import com.zhongyang.java.zyfyback.returndata.ArticleVO;
import com.zhongyang.java.zyfyback.service.ArticleService;

@Service
public class ArticleBizImpl implements ArticleBiz {

	@Autowired
	private ArticleService articleService;

	@Override
	@Transactional
	public ArticleReturn addArticle(Article article) {
		ArticleReturn ar = new ArticleReturn();
		article.setId(UUID.randomUUID().toString().toUpperCase());
		article.setSubmitTime(new Date());
		article.setIndex(1);
		
		articleService.addIndex(article);
		articleService.addArticle(article);
		ar.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "文章发布成功"));
		return ar;

	}

	@Override
	@Transactional
	public ArticleReturn modifyByParams(Article article) {
		ArticleReturn ar = new ArticleReturn();

		Article art = articleService.getArticleById(article.getId());
		if (art != null && !art.getColumnId().equals(article.getColumnId())) {
			article.setIndex(50);
		}
		articleService.modifyByParams(article);
		ar.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "文章修改成功"));
		return ar;

	}

	@Override
	@Transactional
	public ArticleReturn deleteByParams(Article article) {
		ArticleReturn ar = new ArticleReturn();

		if (article.getId() != null) {
			articleService.deleteByParams(article);
		}
		ar.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "文章删除成功"));
		return ar;
	}

	@Override
	public ArticleReturn queryByParams(Page<ArticleVO> page) {
		ArticleReturn ar = new ArticleReturn();

		List<ArticleVO> articleVOs = new ArrayList<ArticleVO>();
		List<Article> articles = articleService.queryByParams(page);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (Article art : articles) {
			ArticleVO articleVO = new ArticleVO();
			articleVO.setTitle(art.getTitle());
			articleVO.setId(art.getId());
			articleVO.setColumnName(art.getColumnName());
			articleVO.setSubmitTime(sdf.format(art.getSubmitTime()));
			articleVOs.add(articleVO);
		}
		page.setResults(articleVOs);
		ar.setPageArticleVo(page);
		ar.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "文章列表查询成功"));
		return ar;
	}

	@Override
	@Transactional
	public ArticleReturn changePlace(String[] str) {
		ArticleReturn ar = new ArticleReturn();

		Article arts = articleService.getArticleById(str[0]);
		articleService.updateAllArtIndex(arts.getColumnId());
		List<Article>articles=new ArrayList<Article>();
		
		for (int i = 0; i < str.length; i++) {
			Article art = new Article();
			art.setId(str[i].trim());
			art.setIndex((i + 1));
			articles.add(art);
		}
		articleService.batchUpdate(articles);
		ar.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "文章位置修改成功"));
		return ar;

	}

	@Override
	public ArticleReturn getArticleById(String id) {
		ArticleReturn ar = new ArticleReturn();

		Article res = articleService.getArticleById(id);
		ar.setArticle(res);
		ar.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "查询成功"));
		return ar;
	}

}
