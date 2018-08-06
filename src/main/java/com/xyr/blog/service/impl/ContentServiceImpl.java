package com.xyr.blog.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.xyr.blog.dao.ContentVORepository;
import com.xyr.blog.entity.Vo.ContentVo;
import com.xyr.blog.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ContentServiceImpl.class);

	@Autowired
	private ContentVORepository contentVORepository;

	@Override
	public ContentVo findUserById(Integer id) {
		// TODO Auto-generated method stub
		return contentVORepository.findOne(id);
	}

	@Override
	public PageInfo<ContentVo> getContents(Integer p, Integer limit) {
		LOGGER.debug("Enter getContents method");
		int page = p, size = limit;
		Sort sort = new Sort(Direction.DESC, "cid");
		Pageable pageable = new PageRequest(page, size, sort);
		Page<ContentVo> data = contentVORepository.findAll(pageable);
		List<ContentVo> result = data.getContent();
		PageInfo<ContentVo> pageInfo = new PageInfo<>(result);
		LOGGER.debug("Exit getContents method");
		return pageInfo;
	}

}
