package com.xyr.blog.entity.Bo;

import java.io.Serializable;

/**
 * 后台统计对象
 */
public class StatisticsBo implements Serializable {

	private Long articles;

	public Long getArticles() {
		return articles;
	}

	public void setArticles(Long articles) {
		this.articles = articles;
	}

	@Override
	public String toString() {
		return "StatisticsBo [articles=" + articles + "]";
	}

}
