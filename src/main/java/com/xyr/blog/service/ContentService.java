package com.xyr.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.github.pagehelper.PageInfo;
import com.xyr.blog.entity.Vo.ContentVo;

public interface ContentService {

	public ContentVo findUserById(Integer id);

	PageInfo<ContentVo> getContents(Integer p, Integer limit);

	ContentVo getContents(String id);
}
