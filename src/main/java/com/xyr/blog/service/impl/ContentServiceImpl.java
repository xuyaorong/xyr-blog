package com.xyr.blog.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.xyr.blog.exception.TipException;
import com.xyr.blog.service.ContentService;
import com.xyr.blog.utils.Tools;

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

	@Override
	public ContentVo getContents(String id) {
		if (StringUtils.isNotBlank(id)) {
			if (Tools.isNumber(id)) {
                ContentVo contentVo = contentVORepository.findByCid(Integer.valueOf(id));
                if (contentVo != null) {
                    contentVo.setHits(contentVo.getHits() + 1);
                    contentVORepository.save(contentVo);
                }
                return contentVo;
            }else {
            	List<ContentVo> contentVos = contentVORepository.findBySlug(id);
            	if (contentVos.size() != 1) {
                    throw new TipException("query content by id and return is not one");
                }
            	if (contentVos.get(0) != null) {
            		contentVos.get(0).setHits(contentVos.get(0).getHits() + 1);
                    contentVORepository.save(contentVos.get(0));
                }
                return contentVos.get(0);
            }
		}
		return null;
	}

}
