package com.xyr.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyr.blog.dao.ContentVORepository;
import com.xyr.blog.entity.Bo.StatisticsBo;
import com.xyr.blog.service.StatisticService;

@Service
public class StatisticServiceImpl implements StatisticService {

	@Autowired
	private ContentVORepository contentVORepository;

	@Override
	public StatisticsBo getStatistics() {
		// TODO Auto-generated method stub
		StatisticsBo statisticsBo=new StatisticsBo();
		long count=contentVORepository.findCount();
		statisticsBo.setArticles(count);
		return statisticsBo;
	}

}
