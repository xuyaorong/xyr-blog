package com.xyr.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.xyr.blog.dao.ContentVORepository;
import com.xyr.blog.entity.Vo.ContentVo;
import com.xyr.blog.service.ContentService;

public class ContentServiceImpl implements ContentService{
	
	@Autowired
	private ContentVORepository contentVORepository;
	@Override
	public ContentVo findUserById(Integer id) {
		// TODO Auto-generated method stub
		return contentVORepository.findOne(1);
	}

}
